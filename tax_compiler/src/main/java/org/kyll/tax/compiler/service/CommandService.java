package org.kyll.tax.compiler.service;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.kyll.common.Const;
import org.kyll.common.util.DateUtil;
import org.kyll.common.util.StringUtil;
import org.kyll.tax.compiler.common.Config;
import org.kyll.tax.compiler.common.Oper;
import org.kyll.tax.compiler.domain.OperFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: Kyll
 * Date: 2017-09-15 16:10
 */
@Slf4j
@Component
public class CommandService {
	public void execute(List<OperFile> operFileList) {
		mkdir(operFileList.stream().filter(operFile -> Oper.MKDIR == operFile.getOper()).collect(Collectors.toList()));
		copy(operFileList.stream().filter(operFile -> Oper.COPY == operFile.getOper()).collect(Collectors.toList()));
		delete(operFileList.stream().filter(operFile -> Oper.DELETE == operFile.getOper()).collect(Collectors.toList()));
	}

	private void mkdir(List<OperFile> operFileList) {
		for (OperFile operFile : operFileList) {
			for (String targetPath : StringUtil.split(operFile.getTargetPath(), ";")) {
				File file = new File(targetPath);
				if (file.exists()) {
					log.info("目录已存在 " + file);
				} else {
					if (file.mkdirs()) {
						log.info("建立目录 " + file);
					} else {
						log.error("建立目录失败 " + file);
					}
				}
			}
		}
	}

	private void copy(List<OperFile> operFileList) {
		for (OperFile operFile : operFileList) {
			String path = operFile.getPath();
			String targetPath = operFile.getTargetPath();

			if (path.endsWith(".bizx")) {
				log.info("生成文件 " + targetPath);
				generateBiz(path, targetPath);

				log.info("生成文件 .eos");
				generateEos(targetPath);

				log.info("生成文件 .eosComponentType");
				generateEosComponentType(targetPath);
			} else if (path.endsWith(".compositex")) {
				log.info("生成文件 " + targetPath);
				generateComposite(path, targetPath);
			} else {
				log.info("复制文件 " + path + " -> " + targetPath);

				File targetFile = new File(targetPath);
				if (!targetFile.exists()) {
					try {
						if (targetFile.createNewFile()) {
							log.info("文件建立 " + targetFile);
						} else {
							log.info("文件建立失败 " + targetFile);
						}
					} catch (IOException e) {
						log.error("文件建立异常", e);
					}
				}

				try (FileChannel sourceFileChannel = new FileInputStream(new File(path)).getChannel();
				     FileChannel targetFileChannel = new FileOutputStream(targetFile).getChannel()) {
					targetFileChannel.write(sourceFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, sourceFileChannel.size()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void delete(List<OperFile> operFileList) {
		for (OperFile operFile : operFileList) {
			File file = new File(operFile.getTargetPath());
			if (file.delete()) {
				log.info("删除文件 " + file);
			} else {
				log.error("删除文件失败 " + file);
			}
		}
	}

	private void generateBiz(String path, String targetPath) {
		SAXReader saxReader = new SAXReader();
		try (FileWriter fileWriter = new FileWriter(targetPath)) {
			Document document = saxReader.read(new File(path));
			List<Element> elementList = document.selectNodes("/process:tBusinessLogic");
			elementList.forEach(element -> {
				remove(element, "@detailDescription");
				remove(element, "@demonstration");
				remove(element, "@urls");
			});

			elementList = document.selectNodes("/process:tBusinessLogic/nodes");
			elementList.forEach(element -> {
				remove(element, "@displayName");
				remove(element, "@description");
				remove(element, "location");
				remove(element, "size");
			});

			elementList = document.selectNodes("/process:tBusinessLogic/nodes/sourceConnections");
			elementList.forEach(element -> remove(element, "@displayName"));

			elementList = document.selectNodes("/process:tBusinessLogic/process:info");
			elementList.forEach(element -> {
				remove(element, "@author");
				remove(element, "@createTime");
				remove(element, "@date");
				remove(element, "@description");
				remove(element, "@name");
			});

			elementList = document.selectNodes("/process:tBusinessLogic/process:inputs/process:input");
			elementList.forEach(element -> remove(element, "@description"));

			XMLWriter xmlWriter = new XMLWriter(fileWriter, OutputFormat.createPrettyPrint());
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (DocumentException | IOException e) {
			log.error("biz 文件生成异常", e);
		}
	}

	private boolean remove(Element element, String xpath) {
		Node node = element.selectSingleNode(xpath);
		return node != null && element.remove(node);
	}

	private void generateEos(String targetPath) {
		try (FileWriter fileWriter = new FileWriter(new File(targetPath).getParent() + Const.SYMBOL_FILE_SEPARATOR + ".eos")) {
			fileWriter.write("#" + Const.SYMBOL_LINE_SEPARATOR);
			fileWriter.write("#" + DateUtil.now() + Const.SYMBOL_LINE_SEPARATOR);
			fileWriter.write("componentType=com.primeton.component.eosComponent" + Const.SYMBOL_LINE_SEPARATOR);
			fileWriter.write("componentVersion=7.0.0.0" + Const.SYMBOL_LINE_SEPARATOR);
		} catch (IOException e) {
			log.error(".eos 文件生成异常", e);
		}
	}

	private void generateEosComponentType(String targetPath) {
		File parent = new File(targetPath).getParentFile();

		Document document = DocumentHelper.createDocument();
		Element eosComponentTypeInfo = document.addElement("com.primeton.studio.component.core.EosComponentTypeInfo");
		Element description = eosComponentTypeInfo.addElement("description");
		description.addElement("version").addText("6.0");
		description.addElement("displayName").addText(parent.getName());
		description.addElement("author").addText("CC");
		description.addElement("createdTime").addText(DateUtil.formatDatetime(DateUtil.now()));
		description.addElement("description");
		description.addElement("detailDescription");
		description.addElement("demo");
		description.addElement("links");
		eosComponentTypeInfo.addElement("serviceReferences");

		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		outputFormat.setLineSeparator(Const.SYMBOL_LINE_SEPARATOR);

		try (FileWriter fileWriter = new FileWriter(parent.getPath() + Const.SYMBOL_FILE_SEPARATOR + parent.getName() + ".eosComponentType")) {
			XMLWriter xmlWriter = new XMLWriter(fileWriter, outputFormat);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException e) {
			log.error("eosComponentType 文件生成异常", e);
		}
	}

	private void generateComposite(String path, String targetPath) {
		SAXReader saxReader = new SAXReader();
		try (FileWriter fileWriter = new FileWriter(targetPath)) {
			Document document = saxReader.read(new File(path));

			String interfaceName = document.selectSingleNode("/eos:compositeDiagram/nodes/eos:service/eos:interface.java/@interface").getText();
			String serviceName = interfaceName.substring(interfaceName.lastIndexOf(".") + 1);

			Document targetDocument = DocumentHelper.createDocument();
			Element composite = targetDocument.addElement("composite", "http://www.osoa.org/xmlns/sca/1.0");
			composite.addAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
			composite.addAttribute("name", interfaceName + "Composite");
			composite.addAttribute("targetNamespace", "http://www.osoa.org/xmlns/sca/1.0");

			Element service = composite.addElement("service");
			service.addAttribute("name", serviceName);
			service.addAttribute("promote", interfaceName + "Composite." + serviceName + "Composite/" + serviceName);

			Element interfaceJava = service.addElement("interface.java");
			interfaceJava.addAttribute("interface", interfaceName);
			service.addElement("binding.ws");

			Element component = composite.addElement("component");
			component.addAttribute("name", interfaceName + "Composite." + serviceName + "Composite");

			Element implementationJava = component.addElement("implementation.java");
			implementationJava.addAttribute("class", document.selectSingleNode("/eos:compositeDiagram/nodes/eos:implementation.java/@class").getText());

			Element componentService = component.addElement("service");
			componentService.addAttribute("name", serviceName);

			Element componentInterfaceJava = componentService.addElement("interface.java");
			componentInterfaceJava.addAttribute("interface", interfaceName);

			XMLWriter xmlWriter = new XMLWriter(fileWriter, OutputFormat.createPrettyPrint());
			xmlWriter.write(targetDocument);
			xmlWriter.close();

		//	appendScaContribution(interfaceName + "Composite");
		} catch (DocumentException | IOException e) {
			log.error("composite 文件生成异常", e);
		}
	}

	@SuppressWarnings("unchecked")
	private void appendScaContribution(String composite) {
		String path = Config.BIZ_TARGET_PATH + "META-INF\\sca-contribution.xml";
		SAXReader saxReader = new SAXReader();
		try (FileWriter fileWriter = new FileWriter(path)) {
			Document document = saxReader.read(new File(path));

			Element contribution = document.getRootElement();

			List<Element> deployableList = contribution.selectNodes("deployable");
			deployableList.forEach(contribution::remove);

			contribution.addElement("deployable").addAttribute("composite", composite);

			XMLWriter xmlWriter = new XMLWriter(fileWriter, OutputFormat.createPrettyPrint());
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (DocumentException | IOException e) {
			log.error("sca-contribution.xml 文件追加 WS 配置异常", e);
		}
	}
}
