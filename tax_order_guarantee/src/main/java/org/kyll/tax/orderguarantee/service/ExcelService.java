package org.kyll.tax.orderguarantee.service;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.kyll.common.util.DateUtil;
import org.kyll.common.util.ExcelUtil;
import org.kyll.common.util.StringUtil;
import org.kyll.tax.orderguarantee.domain.ComAccMsg;
import org.kyll.tax.orderguarantee.domain.OpenBillMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * User: Kyll
 * Date: 2017-09-06 16:38
 */
@Component
public class ExcelService {
	private static Scanner scanner = new Scanner(System.in);

	@Autowired
	private ComAccMsgService comAccMsgService;
	@Autowired
	private OpenBillMsgService openBillMsgService;

	public void read() {
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(new FileInputStream("C:\\Users\\Administrator\\OneDrive\\工作\\融通\\项目\\增值税\\预约保单手工入库\\增值税运维手工开票20170519-长春解放.xlsx"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		List<XSSFSheet> sheetList = new ArrayList<>();
		workbook.sheetIterator().forEachRemaining(sheet -> sheetList.add((XSSFSheet) sheet));

		for (XSSFSheet sheet : sheetList) {
			if (sheet.getPhysicalNumberOfRows() == 1) {
				Document document = null;
				try {
					document = DocumentHelper.parseText(sheet.getRow(0).getCell(0).getStringCellValue());
				} catch (Exception ignored) {
				}

				if (document != null) {
					Node dto = document.selectSingleNode("/PACKET/BODY/CustomerAndPolicyPlanDto");
					String sql = "insert into yl_temp values ("
					+ "'" + dto.selectSingleNode("tran_date").getText() + "',"
					+ "'" + dto.selectSingleNode("tran_seqno").getText() + "',"
					+ "'" + dto.selectSingleNode("dtl_seqno").getText() + "',"
					+ "'" + dto.selectSingleNode("acc").getText() + "',"
					+ "'" + dto.selectSingleNode("curr_type").getText() + "',"
					+ "'" + dto.selectSingleNode("acc_inst").getText() + "',"
					+ "'" + dto.selectSingleNode("itm_no").getText() + "',"
					+ "'" + dto.selectSingleNode("dr_cr_flag").getText() + "',"
					+ "'" + dto.selectSingleNode("amt").getText() + "',"
					+ "'" + dto.selectSingleNode("tran_code").getText() + "',"
					+ "'" + dto.selectSingleNode("sub_code").getText() + "',"
					+ "'" + dto.selectSingleNode("summ").getText() + "',"
					+ "'" + dto.selectSingleNode("tran_system").getText() + "',"
					+ "'" + dto.selectSingleNode("tran_time").getText() + "',"
					+ "'" + dto.selectSingleNode("cstm_no").getText() + "',"
					+ "'" + dto.selectSingleNode("prod_code").getText() + "',"
					+ "'" + dto.selectSingleNode("cert_no").getText() + "',"
					+ "'" + dto.selectSingleNode("entry_type").getText() + "',"
					+ "'" + dto.selectSingleNode("flag").getText() + "',"
					+ "'" + dto.selectSingleNode("acc_flag").getText() + "',"
					+ "'" + dto.selectSingleNode("per_acc").getText() + "',"
					+ "'" + dto.selectSingleNode("per_acc_name").getText() + "',"
					+ "'" + dto.selectSingleNode("per_cstm_id").getText() + "',"
					+ "'" + dto.selectSingleNode("per_open_inst").getText() + "',"
					+ "'" + dto.selectSingleNode("per_paper_type").getText() + "',"
					+ "'" + dto.selectSingleNode("per_paper_no").getText() + "',"
					+ "'" + dto.selectSingleNode("com_acc").getText() + "',"
					+ "'" + dto.selectSingleNode("com_acc_name").getText() + "',"
					+ "'" + dto.selectSingleNode("com_cstm_id").getText() + "',"
					+ "'" + dto.selectSingleNode("com_open_inst").getText() + "',"
					+ "'" + dto.selectSingleNode("com_permit_no").getText() + "',"
					+ "'" + dto.selectSingleNode("com_org_inst_code").getText() + "',"
					+ "'" + dto.selectSingleNode("com_acc_addr").getText() + "',"
					+ "'" + dto.selectSingleNode("com_acc_phone").getText() + "',"
					+ "'" + dto.selectSingleNode("com_taxpayer_id").getText() + "',"
					+ "'" + dto.selectSingleNode("com_taxpayer_name").getText() + "',"
					+ "'" + dto.selectSingleNode("com_login_type").getText() + "',"
					+ "'" + dto.selectSingleNode("com_open_acc_bank").getText() + "',"
					+ "'" + dto.selectSingleNode("com_open_acc").getText() + "',"
					+ "'" + dto.selectSingleNode("attribute1").getText() + "',"
					+ "'" + dto.selectSingleNode("attribute2").getText() + "',"
					+ "'" + dto.selectSingleNode("attribute3").getText() + "',"
					+ "'" + dto.selectSingleNode("attribute4").getText() + "',"
					+ "'" + dto.selectSingleNode("attribute4").getText() + "'"
					+ ");";
					System.out.println(sql);
				}
			}
		}
	}

	public void read(String path) {
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		List<RawData> rawDataList = new ArrayList<>();

		XSSFSheet sheet = getSheet(workbook);
		for (int i = 1, lastRowNum = sheet.getLastRowNum(); i <= lastRowNum; i++) {
			Row row = sheet.getRow(i);

			RawData rawData = new RawData();
			rawData.setComTaxpayerName(ExcelUtil.getCellText(row.getCell(0)));
			rawData.setAmount(new BigDecimal(ExcelUtil.getCellText(row.getCell(1))));
			rawData.setTranSeqno(ExcelUtil.getCellText(row.getCell(2)));

			rawDataList.add(rawData);
		}

		List<String> xmlList = new ArrayList<>();
		for (RawData rawData : rawDataList) {
			System.out.println(rawData);

			ComAccMsg comAccMsg = getComAccMsg(rawData);
			if (comAccMsg == null) {
				System.out.println("ComAccMsg is not exist. SKIP!!!");
				continue;
			}

			xmlList.add(createXml(rawData, comAccMsg));
		}

		xmlList.forEach(System.out::println);
	}

	private String createXml(RawData rawData, ComAccMsg comAccMsg) {
		Date now = DateUtil.now();
		String[] comAccs = comAccMap.get(StringUtil.trim(comAccMsg.getCstmId()));

		if (comAccs == null) {
			System.out.println(comAccMsg);
			return null;
		}

		int dtlSeqno = 0;
		List<OpenBillMsg> openBillMsgList = openBillMsgService.findLike("tranSeqno", rawData.getTranSeqno());
		for (OpenBillMsg openBillMsg : openBillMsgList) {
			String[] ss = StringUtil.split(openBillMsg.getTranSeqno(), "_");
			if (ss.length > 1) {
				int tmpDtlSeqno = Integer.parseInt(ss[ss.length - 1]);
				if (tmpDtlSeqno > dtlSeqno) {
					dtlSeqno = tmpDtlSeqno;
				}
			}
		}
		dtlSeqno++;

		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element packet = document.addElement("PACKET");
		packet.addAttribute("type", "REQUEST").addAttribute("version", "1.0");
		Element dto = packet.addElement("BODY").addElement("CustomerAndPolicyPlanDto");
		dto.addElement("tran_date").addText(DateUtil.formatDateCompact(now));
		dto.addElement("tran_seqno").addText(rawData.getTranSeqno());
		dto.addElement("dtl_seqno").addText(String.valueOf(dtlSeqno));
		dto.addElement("acc").addText(comAccs[0]);
		dto.addElement("curr_type").addText("001");
		dto.addElement("acc_inst").addText("99010000");
		dto.addElement("itm_no").addText("22212105");
		dto.addElement("dr_cr_flag").addText("2");
		dto.addElement("amt").addText(rawData.getAmount().toString());
		dto.addElement("tran_code").addText("1");
		dto.addElement("sub_code").addText("1");
		dto.addElement("summ").addText("");
		dto.addElement("tran_system").addText("Xapayment");
		dto.addElement("tran_time").addText(DateUtil.formatDatetimeCompact(now));
		dto.addElement("cstm_no").addText(comAccs[1]);
		dto.addElement("prod_code").addText(comAccs[2]);
		dto.addElement("cert_no").addText("");
		dto.addElement("entry_type").addText("1");
		dto.addElement("flag").addText("0");
		dto.addElement("acc_flag").addText("2");
		dto.addElement("per_acc").addText("");
		dto.addElement("per_acc_name").addText("");
		dto.addElement("per_cstm_id").addText("");
		dto.addElement("per_open_inst").addText("");
		dto.addElement("per_paper_type").addText("");
		dto.addElement("per_paper_no").addText("");
		dto.addElement("com_acc").addText(StringUtil.toEmptyIf(StringUtil.trim(comAccMsg.getAcc())));
		dto.addElement("com_acc_name").addText(StringUtil.toEmptyIf(StringUtil.trim(comAccMsg.getAccName())));
		dto.addElement("com_cstm_id").addText(StringUtil.toEmptyIf(StringUtil.trim(comAccMsg.getCstmId())));
		dto.addElement("com_open_inst").addText(StringUtil.toEmptyIf(StringUtil.trim(comAccMsg.getOpenInst())));
		dto.addElement("com_permit_no").addText(StringUtil.toEmptyIf(StringUtil.trim(comAccMsg.getPermitNo())));
		dto.addElement("com_org_inst_code").addText(StringUtil.toEmptyIf(StringUtil.trim(comAccMsg.getOrgInstCode())));
		dto.addElement("com_acc_addr").addText(StringUtil.toEmptyIf(StringUtil.trim(comAccMsg.getAccAddr())));
		dto.addElement("com_acc_phone").addText(StringUtil.toEmptyIf(StringUtil.trim(comAccMsg.getAccPhone())));
		dto.addElement("com_taxpayer_id").addText(StringUtil.toEmptyIf(StringUtil.trim(comAccMsg.getTaxpayerId())));
		dto.addElement("com_taxpayer_name").addText(StringUtil.toEmptyIf(StringUtil.trim(comAccMsg.getTaxpayerName())));
		dto.addElement("com_login_type").addText(StringUtil.toEmptyIf(StringUtil.trim(comAccMsg.getLoginType())));
		dto.addElement("com_open_acc_bank").addText(StringUtil.toEmptyIf(StringUtil.trim(comAccMsg.getOpenAccBank())));
		dto.addElement("com_open_acc").addText(StringUtil.toEmptyIf(StringUtil.trim(comAccMsg.getOpenAcc())));
		dto.addElement("attribute1").addText(rawData.getTranSeqno());
		dto.addElement("attribute2").addText("");
		dto.addElement("attribute3").addText("");
		dto.addElement("attribute4").addText("");
		dto.addElement("attribute5").addText("");

		return document.asXML();
	}

	private ComAccMsg getComAccMsg(RawData rawData) {
		List<ComAccMsg> comAccMsgList = comAccMsgService.findListByAccName(rawData.getComTaxpayerName());

		ComAccMsg selectedComAccMsg = null;

		List<Integer> indexList = new ArrayList<>();
		int size = comAccMsgList.size();
		for (int i = 0; i < size; i++) {
			ComAccMsg comAccMsg = comAccMsgList.get(i);
			indexList.add(i + 1);

			System.out.println((i + 1) + " " + comAccMsg);
		}

		if (size == 1) {
			selectedComAccMsg = comAccMsgList.get(0);
		} else if (size > 1) {
			selectedComAccMsg = comAccMsgList.get(getSelectedIndex(indexList));
		}

		return selectedComAccMsg;
	}

	private XSSFSheet getSheet(XSSFWorkbook workbook) {
		List<XSSFSheet> sheetList = new ArrayList<>();
		workbook.sheetIterator().forEachRemaining(sheet -> sheetList.add((XSSFSheet) sheet));

		XSSFSheet selectedSheet = null;
		int selectedIndex = -1;

		List<Integer> indexList = new ArrayList<>();
		for (int i = 0, size = sheetList.size(); i < size; i++) {
			XSSFSheet sheet = sheetList.get(i);

			indexList.add(i + 1);

			int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
			if (selectedSheet == null && physicalNumberOfRows > 0) {
				selectedSheet = sheet;
				selectedIndex = i;
			}

			System.out.println((i + 1) + " Sheet Name: " + sheet.getSheetName() + ", Rows: " + physicalNumberOfRows);

		}
		System.out.println("Choose [" + StringUtil.join(indexList, ",") + "]: ");

		if (selectedSheet == null) {
			selectedSheet = sheetList.get(getSelectedIndex(indexList));
		} else {
			System.out.println("Auto Choose: " + (selectedIndex + 1));
		}

		return selectedSheet;
	}

	private int getSelectedIndex(List<Integer> indexList) {
		String in = scanner.nextLine();

		int index = -1;
		try {
			index = Integer.parseInt(in);
		} catch (Exception ignored) {
		}

		return indexList.contains(index - 1) ? index : getSelectedIndex(indexList);
	}

	private static class RawData {
		@Getter @Setter private String comTaxpayerName;
		@Getter @Setter private BigDecimal amount;
		@Getter @Setter private String tranSeqno;

		public RawData() {
		}

		@Override
		public String toString() {
			return "RawData{" +
					"comTaxpayerName='" + comTaxpayerName + '\'' +
					", amount=" + amount +
					", tranSeqno='" + tranSeqno + '\'' +
					'}';
		}
	}

	private static Map<String, String[]> comAccMap = new HashMap<>();
	static {
		comAccMap.put("1007295187", new String[]{"22001460200059111888", "1007295187", "603100104"});
		comAccMap.put("1008333322", new String[]{"802690200059653", "1008333322", "603100104"});
		comAccMap.put("1007260742", new String[]{"431900423310766", "1007260742", "6031001"});
		comAccMap.put("1007268801", new String[]{"010101201098777888", "1007268801", "6031001"});
		comAccMap.put("1008281757", new String[]{"0710728011015200002367", "1008281757", "603100104"});
		comAccMap.put("1008341371", new String[]{"802690200059653", "1008341371", "6031001"});
		comAccMap.put("1008333534", new String[]{"802690200059653", "1008333534", "6031001"});
		comAccMap.put("1007858464", new String[]{"0710728011015200002367", "1007858464", "603100104"});
		comAccMap.put("1007278727", new String[]{"4200222409200102770", "1007278727", "6031001"});
		comAccMap.put("1008350640", new String[]{"802690200059653", "1008350640", "603100104"});
		comAccMap.put("1008350642", new String[]{"802690200059653", "1008350642", "603100104"});
		comAccMap.put("1007225087", new String[]{"150301040008815", "1007225087", "603100104"});
		comAccMap.put("241", new String[]{"4200222409200055121", "241", "603100104"});
		comAccMap.put("1007286572", new String[]{"4200222409200072076", "1007286572", "6031001"});
		comAccMap.put("1008341371", new String[]{"802690200059653", "1008341371", "603100104"});
		comAccMap.put("1008333534", new String[]{"802690200059653", "1008333534", "603100104"});
		comAccMap.put("1008387967", new String[]{"22001460300055011075", "1008387967", "6031001"});
		comAccMap.put("1007290771", new String[]{"163601044472", "1007290771", "603100104"});
		comAccMap.put("1007224725", new String[]{"4200229119200052187", "1007224725", "603100104"});
		comAccMap.put("1007343929", new String[]{"22001470300055018309", "1007343929", "6031001"});
		comAccMap.put("1007233590", new String[]{"22001370100059888999", "1007233590", "6031001"});
		comAccMap.put("1008303974", new String[]{"802690200059653", "1008303974", "603100104"});
		comAccMap.put("1008350639", new String[]{"802690200059653", "1008350639", "6031001"});
		comAccMap.put("1007344986", new String[]{"22001450100055002465", "1007344986", "6031001"});
		comAccMap.put("1007504747", new String[]{"431900424910777", "1007504747", "603100104"});
		comAccMap.put("1008295515", new String[]{"4200222409200107830", "1008295515", "6031001"});
		comAccMap.put("1007691670", new String[]{"22001460300055000000", "1007691670", "6031001"});
		comAccMap.put("1008426369", new String[]{"0710728011015200002367", "1008426369", "603100104"});
		comAccMap.put("1008350638", new String[]{"802690200059653", "1008350638", "603100104"});
		comAccMap.put("1008350639", new String[]{"802690200059653", "1008350639", "603100104"});
		comAccMap.put("1008363898", new String[]{"37101985310051010358", "1008363898", "603100104"});
		comAccMap.put("1008350640", new String[]{"802690200059653", "1008350640", "6031001"});
		comAccMap.put("1008311696", new String[]{"802690200059653", "1008311696", "6031001"});
		comAccMap.put("1008350641", new String[]{"802690200059653", "1008350641", "6031001"});
		comAccMap.put("1007909362", new String[]{"35970188000113684", "1007909362", "603100104"});
		comAccMap.put("1008284306", new String[]{"1001704009300277257", "1008284306", "603100104"});
		comAccMap.put("1008365121", new String[]{"0710728011015200002367", "1008365121", "603100104"});
		comAccMap.put("1008350633", new String[]{"802690200059653", "1008350633", "603100104"});
		comAccMap.put("1008350634", new String[]{"802690200059653", "1008350634", "603100104"});
		comAccMap.put("1008337063", new String[]{"802690200059653", "1008337063", "603100104"});
		comAccMap.put("1007310851", new String[]{"22001460300055003979", "1007310851", "603100104"});
		comAccMap.put("1008350638", new String[]{"802690200059653", "1008350638", "6031001"});
		comAccMap.put("1008341371", new String[]{"802690200049366", "1008341371", "603100104"});
		comAccMap.put("1729", new String[]{"4200222409000001829", "1729", "603100104"});
		comAccMap.put("1007528971", new String[]{"7760120109000064", "1008352595", "6031001"});
		comAccMap.put("1008285372", new String[]{"0710728011015200002367", "1008285372", "603100104"});
		comAccMap.put("1008350635", new String[]{"802690200059653", "1008350635", "603100104"});
		comAccMap.put("1008350636", new String[]{"802690200059653", "1008350636", "603100104"});
		comAccMap.put("1007267087", new String[]{"7250220109009606", "1007267087", "603100104"});
		comAccMap.put("1008328213", new String[]{"694332636", "1008328213", "6031001"});
		comAccMap.put("1008350641", new String[]{"802690200059653", "1008350641", "603100104"});
		comAccMap.put("1008315416", new String[]{"802690200059653", "1008315416", "603100104"});
		comAccMap.put("1008315416", new String[]{"802690200044778", "1008315416", "603100104"});
		comAccMap.put("1007262894", new String[]{"61030154740001969", "1007262894", "603100104"});
		comAccMap.put("1007304285", new String[]{"22001316400055000065", "1007304285", "603100104"});
		comAccMap.put("1008442504", new String[]{"22001616738055008130", "1008442504", "603100104"});
		comAccMap.put("1008387625", new String[]{"0710728011015200002367", "1008387625", "603100104"});
		comAccMap.put("1008350631", new String[]{"802690200059653", "1008350631", "603100104"});
		comAccMap.put("1008350632", new String[]{"802690200059653", "1008350632", "603100104"});
		comAccMap.put("1008337221", new String[]{"802690200059653", "1008337221", "603100104"});
		comAccMap.put("1007226234", new String[]{"221000680018170154206", "1007226234", "6031001"});
		comAccMap.put("1007292829", new String[]{"07160201040005183", "1007292829", "6031001"});
		comAccMap.put("1007292451", new String[]{"1007292451", "1007292451", "6031002"});
		comAccMap.put("1008350637", new String[]{"802690200059653", "1008350637", "603100104"});
		comAccMap.put("1008311696", new String[]{"802690200059653", "1008311696", "603100104"});
		comAccMap.put("1008275366", new String[]{"802690200059733", "1008275366", "603100104"});
		comAccMap.put("1008350642", new String[]{"802690200058636", "1008350642", "603100104"});
		comAccMap.put("1008336853", new String[]{"802690200059653", "1008336853", "603100104"});
		comAccMap.put("1008325408", new String[]{"802690200059653", "1008325408", "603100104"});
		comAccMap.put("1008342098", new String[]{"4200222409200052539", "1008342098", "603100104"});
		comAccMap.put("1000185801", new String[]{"7250220109000007", "1000185801", "603100104"});
		comAccMap.put("1008357387", new String[]{"157201040295", "1008357387", "603100104"});
	}
}
