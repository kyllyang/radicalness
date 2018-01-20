package org.kyll.tax.cccc.facade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.kyll.common.util.DateUtil;
import org.kyll.common.util.StringUtil;
import org.kyll.tax.cccc.domain.DataMsgYq;
import org.kyll.tax.cccc.service.DataMsgYqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * User: Kyll
 * Date: 2017-10-11 15:55
 */
@Slf4j
@Component
public class CcccFacade {
	@Autowired
	private DataMsgYqService dataMsgYqService;

	public void execute1() {
		List<DataMsgYq> dataMsgYqList = dataMsgYqService.findList();
		for (int i = 0, size = dataMsgYqList.size(); i < size; i++) {
			DataMsgYq dataMsgYq = dataMsgYqList.get(i);

			if ("2".equals(dataMsgYq.getMsgStat().trim())) {
				String tranSeqno = dataMsgYq.getTranSeqno();
				if (!tranSeqno.contains("PYAB09901002017001406") &&
						!tranSeqno.contains("PYAB09901002017001407") &&
						!tranSeqno.contains("PYAB09901002017001408")) {

					Map<String, String> map = new HashMap<>();
					map.put("aa", dataMsgYq.getDataMsg());

					String json = toJson(map);

					if (StringUtil.isNotBlank(json)) {
						System.out.println(i + ": " + tranSeqno + " - " + json);

						System.out.println(post(json));
					}
				}
			}
		}
	}

	public void execute3() {
		StringBuilder txt = new StringBuilder();

		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("F:\\downloads\\报文.txt"))))) {
			String line;
			while ((line = in.readLine()) != null) {
				txt.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> msgList = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(txt.toString(), "|||");
		while (st.hasMoreTokens()) {
			String msg = st.nextToken().replace("\"\"", "\"");
			msg = msg.replace("<dtl_seqno>1</dtl_seqno>", "<dtl_seqno>2</dtl_seqno>");
		//	System.out.println(msg);
			msgList.add(msg);
		}

		for (int i = 0, size = msgList.size(); i < size; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("aa", msgList.get(i));

			String json = toJson(map);
			System.out.println(i + ": " + json);

			long curr = System.currentTimeMillis();
			System.out.println(post(json));
			System.out.println("耗费 " + (System.currentTimeMillis() - curr) + " 毫秒");
		}
	}

	public void execute4() {
		List<String> list = new ArrayList<>();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\Administrator\\Downloads\\data10.txt"))))) {
			String line;
			while ((line = in.readLine()) != null) {
				list.add(line.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Set<String> set = new LinkedHashSet<>();
		for (String line : list) {
			String[] ls = line.split(",");

			/*String ls2 = ls[2].trim();
			int index = ls2.indexOf('_') + 1;
			int lastIndex = ls2.lastIndexOf('_');
			ls2 = ls2.substring(lastIndex + 1).startsWith("0") || lastIndex < index ? ls2.substring(index) : ls2.substring(index, lastIndex);*/

			set.add(ls[0].trim());
		}

		List<String> pList = new ArrayList<>(set);

		int num = 200;
		for (int i = 0, size = pList.size(); i < size; i += num) {
			String sql = "select t.cstm_id, t.tran_seqno, t.amt + t.tax_amt from t_tax_open_bill_msg t where (";
			for (int j = i; j < i + num; j++) {
				if (j < size) {
					if (j > i) {
						sql += "or ";
					}
					sql += "instr(t.tran_seqno, '" + pList.get(j) + "') > 0 ";
				}
			}
			sql += ") and t.acc_inst not in ('FAF000000', 'FAF000001') ";

			long curr = System.currentTimeMillis();
			List<Object[]> resultList = dataMsgYqService.query(sql);
			System.out.println(i + " 耗费 " + (System.currentTimeMillis() - curr) + " 毫秒");

			for (Object[] objects : resultList) {
				System.out.println(Arrays.toString(Stream.of(objects).map((Function<Object, Object>) o -> o.toString().trim()).toArray()));
			}
		}
	}

	/**
	 * 批量投保人变更
	 */
	public void execute5() {
		for (int i = 0, length = DATAS21.length; i < length; i++) {
			String[] data = DATAS21[i];

			Map<String, String> map = new HashMap<>();
			map.put("aa", MSG21.replace("${tran_seqno}", data[2]).replace("${amt}", new BigDecimal(data[0]).add(new BigDecimal(data[1])).toString()));

			String json = toJson(map);
			System.out.println(i + ": " + json);

			long curr = System.currentTimeMillis();
			System.out.println(post(json));
			System.out.println("耗费 " + (System.currentTimeMillis() - curr) + " 毫秒");

		}
		for (int i = 0, length = DATAS22.length; i < length; i++) {
			String[] data = DATAS22[i];

			Map<String, String> map = new HashMap<>();
			map.put("aa", MSG22.replace("${tran_seqno}", data[2]).replace("${amt}", new BigDecimal(data[0]).add(new BigDecimal(data[1])).toString()));

			String json = toJson(map);
			System.out.println(i + ": " + json);

			long curr = System.currentTimeMillis();
			System.out.println(post(json));
			System.out.println("耗费 " + (System.currentTimeMillis() - curr) + " 毫秒");
		}
	}

	/**
	 * 查询 FAFGF 定时任务数据重复项
	 */
	public void execute6() {
		for (File file : new File("F:\\downloads\\tax_etl").listFiles()) {
			Set<String> set = new HashSet<>();
			try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
				int count = 1;
				String line;
				while ((line = in.readLine()) != null) {
					if (!set.add(line)) {
						System.out.println(file.getName() + " " + count);
					}
					count++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void execute7() {
		String line = "5F900F16943F6081E053E4ED090A0295|60097650717F3F2FE053E4ED090AA62C|5F900F16943D6081E053E4ED090A0295|5F900F16943B6081E053E4ED090A0295|60097650717B3F2FE053E4ED090AA62C|5F900F1694396081E053E4ED090A0295|5F900F1694376081E053E4ED090A0295|5F900F1694356081E053E4ED090A0295|6009765071793F2FE053E4ED090AA62C|6009765071773F2FE053E4ED090AA62C|6009765071753F2FE053E4ED090AA62C|6009765071733F2FE053E4ED090AA62C|5F900F1694336081E053E4ED090A0295|6009765071713F2FE053E4ED090AA62C|5F900F1694316081E053E4ED090A0295|60097650716F3F2FE053E4ED090AA62C|60097650716D3F2FE053E4ED090AA62C|5F900F16942F6081E053E4ED090A0295|5FCBB94050B66085E053E4ED090AA1FE|5FCBB94050B46085E053E4ED090AA1FE|5F900F16942B6081E053E4ED090A0295|5F900F1694296081E053E4ED090A0295|6031105E26B53F33E053E4ED090A9224|5F900F1694256081E053E4ED090A0295|5F900F1694216081E053E4ED090A0295|60097650716B3F2FE053E4ED090AA62C|6009765071673F2FE053E4ED090AA62C|6009765071653F2FE053E4ED090AA62C|5FCBB94050B06085E053E4ED090AA1FE|6009765071633F2FE053E4ED090AA62C|6009765071613F2FE053E4ED090AA62C|5FCBB94050AE6085E053E4ED090AA1FE|5FCBB94050AC6085E053E4ED090AA1FE|5FCBB94050AA6085E053E4ED090AA1FE|5FCBB94050A86085E053E4ED090AA1FE|5FCBB94050A66085E053E4ED090AA1FE|5FCBB94050A46085E053E4ED090AA1FE|5FCBB94050A06085E053E4ED090AA1FE|5FCBB940509C6085E053E4ED090AA1FE|5FCBB940509A6085E053E4ED090AA1FE|5FCBB94050986085E053E4ED090AA1FE|5FCBB94050966085E053E4ED090AA1FE|5FCBB94050946085E053E4ED090AA1FE|5FCBB94050926085E053E4ED090AA1FE|60097650715F3F2FE053E4ED090AA62C|60097650715D3F2FE053E4ED090AA62C|5FCBB94050906085E053E4ED090AA1FE|60097650715B3F2FE053E4ED090AA62C|6009765071593F2FE053E4ED090AA62C|5FCBB940508C6085E053E4ED090AA1FE|5FCBB940508A6085E053E4ED090AA1FE|5FCBB94050886085E053E4ED090AA1FE|5FCBB94050866085E053E4ED090AA1FE|5FCBB94050846085E053E4ED090AA1FE|5FCBB94050826085E053E4ED090AA1FE|5FCBB940507E6085E053E4ED090AA1FE|5FCBB940507A6085E053E4ED090AA1FE|5FCBB94050786085E053E4ED090AA1FE|5FCBB94050766085E053E4ED090AA1FE|5FCBB94050746085E053E4ED090AA1FE|6009765071573F2FE053E4ED090AA62C|6009765071553F2FE053E4ED090AA62C|6009765071533F2FE053E4ED090AA62C|5FCBB94050726085E053E4ED090AA1FE|5FCBB94050706085E053E4ED090AA1FE|5F900F1694416081E053E4ED090A0295|6031105E0BF63F33E053E4ED090A9224|";
		for (String no : line.split("\\|")) {
			System.out.println("" + no + "");
		}
	}

	/**
	 * FOR 张朋玉 预约保单入库 word
	 */
	public void execute8() {
		List<String[]> list = new ArrayList<>();
		for (File file : new File("F:\\downloads\\tax_pyab_word").listFiles()) {
			XWPFDocument document;
			try {
				document = new XWPFDocument(new FileInputStream(file));
			} catch (IOException e) {
				System.out.println("读取 word 数据文件失败");
				e.printStackTrace();
				return;
			}

			for (XWPFTable table : document.getTables()) {
				String taxpayer = null;
				int beginIndex = -1;
				int endIndex = -1;
				List<XWPFTableRow> rowList = table.getRows();
				for (int i = 0, size = rowList.size(); i < size; i++) {
					XWPFTableRow row = rowList.get(i);
					if (row.getCell(0).getText().trim().contains("纳税人识别号")) {
						taxpayer = row.getCell(1).getText().trim();
					} else if (row.getCell(0).getText().trim().contains("序号")) {
						beginIndex = i;
					} else if (row.getCell(0).getText().trim().contains("合计")) {
						endIndex = i;
					}
				}

				String totalPno = null;
				String totalAmt = null;
				for (int i = beginIndex + 1; i < endIndex; i++) {
					XWPFTableRow row = rowList.get(i);

					String pno = row.getCell(1).getText().trim();
					String amt = row.getCell(2).getText().trim();

					if (StringUtil.isNotBlank(pno) && StringUtil.isNotBlank(amt) && !"0".equals(pno) && !"0".equals(amt)) {
						if (StringUtil.isBlank(totalPno)) {
							totalPno = pno;
						}

						totalAmt = new BigDecimal(amt).add(StringUtil.isBlank(totalAmt) ? BigDecimal.ZERO : new BigDecimal(totalAmt)).toString();
					}
				}

				if (StringUtil.isNotBlank(totalPno) && StringUtil.isNotBlank(totalAmt)) {
					list.add(new String[]{taxpayer, totalPno, totalAmt});
				}
			}
		}

		/*for (String[] datas : list) {
			System.out.println("instr(t.tran_seqno, '" + datas[1] + "') > 0 or");
		}*/


		String tranDate = DateUtil.formatDateCompact(DateUtil.now());
		String tranTime = DateUtil.formatDatetimeCompact(DateUtil.now());
		for (String[] datas : list) {
			String msg = getMsg(datas[0]);

			if (StringUtil.isBlank(msg)) {
				System.out.println("客户[" + datas[0] + "]不存在");
			} else {
				Map<String, String> map = new HashMap<>();
				map.put("tran_date", tranDate);
				map.put("tran_seqno", datas[1]);
				map.put("dtl_seqno", "2");
				map.put("amt", datas[2]);
				map.put("tran_code", "EX_1");
				map.put("summ", "");
				map.put("tran_time", tranTime);
				map.put("attribute1", datas[1]);
				map.put("attribute2", "");
				map.put("attribute3", "1");

				send(replaceInfo(msg, map));
			}
		}
	}

	/**
	 * FOR 张朋玉 预约保单入库 excel
	 */
	public void execute9() {
		String tranDate = DateUtil.formatDateCompact(DateUtil.now());
		String tranTime = DateUtil.formatDatetimeCompact(DateUtil.removeHMS(DateUtil.now()));

		for (File file : new File("F:\\downloads\\tax_pyab_excel").listFiles()) {
			System.out.println(file);

			XSSFWorkbook workbook;
			try {
				workbook = new XSSFWorkbook(OPCPackage.open(file));
			} catch (IOException | InvalidFormatException e) {
				System.out.println("读取 excel 报文文件失败");
				e.printStackTrace();
				return;
			}

			XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());

			String msg = getMsg(getCellValue(sheet.getRow(3).getCell(2)));

			for (int i = 9, lastRowNum = sheet.getLastRowNum(); i < lastRowNum; i++) {
				XSSFRow row = sheet.getRow(i);

				if (StringUtil.isBlank(getCellValue(row.getCell(1)))) {
					break;
				}

				String pno = getCellValue(row.getCell(2));
				String amt = getCellValue(row.getCell(3));

				Map<String, String> map = new HashMap<>();
				map.put("tran_date", tranDate);
				map.put("tran_seqno", pno);
				map.put("dtl_seqno", "2");
				map.put("amt", amt);
				map.put("tran_code", "EX_1");
				map.put("summ", "");
				map.put("tran_time", tranTime);
				map.put("attribute1", pno);
				map.put("attribute2", "");
				map.put("attribute3", "1");

			//	System.out.println("instr(t.tran_seqno, '" + pno + "') > 0 or");
				send(replaceInfo(msg, map));
			}
		}
	}

	private String getCellValue(XSSFCell cell) {
		String result;
		CellType cellType = cell.getCellTypeEnum();
		if (CellType.NUMERIC == cellType) {
			result = new BigDecimal(String.valueOf(cell.getNumericCellValue())).toString();
		} else if (CellType.STRING == cellType) {
			result = cell.getStringCellValue().trim();
		} else if (CellType.FORMULA == cellType) {
			result = cell.getRichStringCellValue().getString().trim();
		} else {
			result = null;
		}
		return result;
	}

	private String replaceInfo(String msg, Map<String, String> map) {
		Document document;
		try {
			document = DocumentHelper.parseText(msg);
		} catch (DocumentException e) {
			System.out.println("解析报文失败");
			e.printStackTrace();
			return null;
		}

		Node customerAndPolicyPlanDtoNode = document.selectSingleNode("/PACKET/BODY/CustomerAndPolicyPlanDto");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			customerAndPolicyPlanDtoNode.selectSingleNode(entry.getKey()).setText(entry.getValue());
		}

		OutputFormat format = new OutputFormat();
		format.setExpandEmptyElements(true);

		CharArrayWriter caw = new CharArrayWriter();
		XMLWriter xmlWriter = new XMLWriter(caw, format);
		try {
			xmlWriter.write(document);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				xmlWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return caw.toString();
	}

	private String getMsg(String taxpayer) {
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(OPCPackage.open(new File("F:\\downloads\\tax_common\\t_tax_data_msg_yq.xlsx")));
		} catch (IOException | InvalidFormatException e) {
			System.out.println("读取 excel 报文文件失败");
			e.printStackTrace();
			return null;
		}

		String msg = null;
		XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
		for (int i = 1, lastRowNum = sheet.getLastRowNum(); i < lastRowNum; i++) {
			XSSFRow row = sheet.getRow(i);
			if (taxpayer.equals(getCellValue(row.getCell(2)).substring(1))) {
				msg = getCellValue(row.getCell(3));
				break;
			}
		}

		return msg;
	}

	private void send(String msg) {
		Map<String, String> map = new HashMap<>();
		map.put("aa", msg);

		String json = toJson(map);

		long curr = System.currentTimeMillis();
		System.out.println(post(json));
		System.out.println("耗费 " + (System.currentTimeMillis() - curr) + " 毫秒");
	}

	private String post(String content) {
		StringBuilder result = new StringBuilder();

		try {
			URL url = new URL("http://10.9.237.153:7001/default/tax/com.gl.common.cccc.cccc.biz.ext");
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "text/json");
			connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			connection.setRequestProperty("Accept-Language", "zh-Hans-CN, zh-Hans; q=0.5");
			connection.setRequestProperty("Cache-Control", "no-cache");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Length", "11");
			connection.setRequestProperty("Cookie", "JSESSIONID=sy0ThvcLBvCJn1v8d7344JFTSjJ3VtR5LVGP2C1vzLFsySNp6tfQ!1527925976");
			connection.setRequestProperty("DNT", "1");
			connection.setRequestProperty("Host", "10.9.237.153:7001");
			connection.setRequestProperty("Referer", "http://10.9.237.153:7001/default/tax/aatest/cccc.jsp");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; Touch; rv:11.0) like Gecko");
			connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			connection.setDoOutput(true);
			connection.setDoInput(true);

			PrintWriter out = new PrintWriter(connection.getOutputStream());
			out.print(content);
			out.flush();
			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	private String toJson(Object object) {
		String json = null;
		try {
			json = OBJECT_MAPPER.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	}

	private static final String[][] DATAS = {
			{"PYAB09901002017001659", "206400"},
			{"PYAB09901002017001614", "613560"},
			{"PYAB09901002017001613", "12600"},
			{"PYAB09901002017001616", "118080"},
			{"PYAB09901002017001621", "70320"},
			{"PYAB09901002017001622", "3480"},
			{"PYAB09901002017001624", "1080"},
			{"PYAB09901002017001631", "90240"},
			{"PYAB09901002017001630", "360"},
			{"PYAB09901002017001636", "2880"},
			{"PYAB09901002017001635", "18720"},
			{"PYAB09901002017001634", "3240"},
			{"PYAB09901002017001638", "10440"},
			{"PYAB09901002017001643", "5040"},
			{"PYAB09901002017001658", "8760"},
			{"PYAB09901002017001642", "720"},
			{"PYAB09901002017001646", "6240"},
			{"PYAB09901002017001645", "12600"},
			{"PYAB09901002017001641", "6120"},
			{"PYAB09901002017001639", "41760"},
			{"PYAB09901002017001657", "57000"},
			{"PYAB09901002017001648", "128520"},
			{"PYAB09901002017001651", "69120"},
			{"PYAB09901002017001656", "65040"},
			{"PYAB09901002017001652", "13080"},
			{"PYAB09901002017001653", "10440"},
			{"PYAB09901002017001654", "10320"},
			{"PYAB09901002017001649", "23760"},
			{"PYAB09901002017001615", "1440"},
			{"PYAB09901002017001618", "15120"},
			{"PYAB09901002017001611", "840"},
			{"PYAB09901002017001617", "1080"},
			{"PYAB09901002017001620", "1080"},
			{"PYAB09901002017001619", "1080"},
			{"PYAB09901002017001609", "360"},
			{"PYAB09901002017001610", "720"},
			{"PYAB09901002017001608", "240"},
			{"PYAB09901002017001606", "840"},
			{"PYAB09901002017001607", "720"},
			{"PYAB09901002017001625", "1200"},
			{"PYAB09901002017001605", "960"},
			{"PYAB09901002017001628", "1680"},
			{"PYAB09901002017001626", "1560"},
			{"PYAB09901002017001629", "1680"},
			{"PYAB09901002017001612", "204700"},
			{"PYAB09901002017001623", "10200"},
			{"PYAB09901002017001627", "6500"},
			{"PYAB09901002017001632", "12400"},
			{"PYAB09901002017001633", "4800"},
			{"PYAB09901002017001637", "6600"},
			{"PYAB09901002017001640", "7000"},
			{"PYAB09901002017001644", "5200"},
			{"PYAB09901002017001647", "19800"},
			{"PYAB09901002017001650", "2300"},
			{"PYAB09901002017001655", "2600"}
	};

	private static final String[][] DATAS21 = {
			{"77.36", "4.64", "P05079901002017142257"},
			{"77.36", "4.64", "P05079901002017142269"},
			{"77.36", "4.64", "P05079901002017142259"},
			{"77.36", "4.64", "P05079901002017142261"},
			{"77.36", "4.64", "P05079901002017142262"},
			{"77.36", "4.64", "P05079901002017142263"},
			{"77.36", "4.64", "P05079901002017142264"},
			{"77.36", "4.64", "P05079901002017142265"},
			{"77.36", "4.64", "P05079901002017142266"},
			{"77.36", "4.64", "P05079901002017142258"},
			{"77.36", "4.64", "P05079901002017142255"},
			{"77.36", "4.64", "P05079901002017142254"},
			{"77.36", "4.64", "P05079901002017142253"},
			{"77.36", "4.64", "P05079901002017142252"},
			{"77.36", "4.64", "P05079901002017142251"}
	};
	private static final String[][] DATAS22 = {
			{"1813.91", "108.83", "P05419901002017035207"},
			{"1104.48", "66.27",  "P05419901002017035198"},
			{"1104.48", "66.27",  "P05419901002017035194"},
			{"1104.48", "66.27",  "P05419901002017035195"},
			{"1104.48", "66.27",  "P05419901002017035199"},
			{"1104.48", "66.27",  "P05419901002017035193"},
			{"1104.48", "66.27",  "P05419901002017035196"},
			{"1104.48", "66.27",  "P05419901002017035200"},
			{"1104.48", "66.27",  "P05419901002017035201"},
			{"1104.48", "66.27",  "P05419901002017035204"},
			{"1104.48", "66.27",  "P05419901002017035203"},
			{"1104.48", "66.27",  "P05419901002017035202"},
			{"1813.91", "108.83", "P05419901002017035206"},
			{"1104.48", "66.27",  "P05419901002017035205"},
			{"1813.91", "108.83", "P05419901002017035208"}
	};

	/**
	 * 一汽解放汽车销售有限公司， 注意修改日期为当前日期
	 * <tran_seqno>${tran_seqno}</tran_seqno>
	 * <dtl_seqno>数据库里的值 + 1</dtl_seqno>
	 * <amt>${amt}</amt>
	 * <attribute1>${tran_seqno}</attribute1>
	 * <tran_code>EX_1</tran_code>
	 */
	private static final String MSG = "<?xml version=\"1.0\" encoding=\"GBK\"?><PACKET type=\"REQUEST\" version=\"1.0\" ><BODY><CustomerAndPolicyPlanDto><tran_date>20171220</tran_date>\n" +
			"<tran_seqno>${tran_seqno}</tran_seqno>\n" +
			"<dtl_seqno>1</dtl_seqno>\n" +
			"<acc>4200222409000001829</acc>\n" +
			"<curr_type>001</curr_type>\n" +
			"<acc_inst>99010000</acc_inst>\n" +
			"<itm_no>22212105</itm_no>\n" +
			"<dr_cr_flag>2</dr_cr_flag>\n" +
			"<amt>${amt}</amt>\n" +
			"<tran_code>EX_1</tran_code>\n" +
			"<sub_code>1</sub_code>\n" +
			"<summ></summ>\n" +
			"<tran_system>Xapayment</tran_system>\n" +
			"<tran_time>20171220000000</tran_time>\n" +
			"<cstm_no>1007633957</cstm_no>\n" +
			"<prod_code>603100104</prod_code>\n" +
			"<cert_no></cert_no>\n" +
			"<entry_type>1</entry_type>\n" +
			"<flag>0</flag>\n" +
			"<acc_flag>2</acc_flag>\n" +
			"<per_acc></per_acc>\n" +
			"<per_acc_name></per_acc_name>\n" +
			"<per_cstm_id></per_cstm_id>\n" +
			"<per_open_inst></per_open_inst>\n" +
			"<per_paper_type></per_paper_type>\n" +
			"<per_paper_no></per_paper_no>\n" +
			"<com_acc>4200222409000001829</com_acc>\n" +
			"<com_acc_name>1007633957</com_acc_name>\n" +
			"<com_cstm_id>1007633957</com_cstm_id>\n" +
			"<com_open_inst>99000000</com_open_inst>\n" +
			"<com_permit_no>91220101123917070P</com_permit_no>\n" +
			"<com_org_inst_code></com_org_inst_code>\n" +
			"<com_acc_addr>长春市绿园区迎春路617号</com_acc_addr>\n" +
			"<com_acc_phone>0431-85904147</com_acc_phone>\n" +
			"<com_taxpayer_id>220106123917070</com_taxpayer_id>\n" +
			"<com_taxpayer_name>一汽解放汽车销售有限公司</com_taxpayer_name>\n" +
			"<com_login_type>1</com_login_type>\n" +
			"<com_open_acc_bank>工行一汽支行</com_open_acc_bank>\n" +
			"<com_open_acc>4200222409000001829</com_open_acc>\n" +
			"<attribute1>${tran_seqno}</attribute1>\n" +
			"<attribute2>2017-12-20</attribute2>\n" +
			"<attribute3>1</attribute3>\n" +
			"<attribute4></attribute4>\n" +
			"<attribute5></attribute5>\n" +
			"</CustomerAndPolicyPlanDto></BODY></PACKET>\n";

	private static final String MSG21 = "<?xml version=\"1.0\" encoding=\"GBK\"?><PACKET type=\"REQUEST\" version=\"1.0\" ><BODY><CustomerAndPolicyPlanDto><tran_date>20171212</tran_date>\n" +
			"<tran_seqno>${tran_seqno}</tran_seqno>\n" +
			"<dtl_seqno>1</dtl_seqno>\n" +
			"<acc>43190113941011</acc>\n" +
			"<curr_type>001</curr_type>\n" +
			"<acc_inst>99010000</acc_inst>\n" +
			"<itm_no>22212105</itm_no>\n" +
			"<dr_cr_flag>2</dr_cr_flag>\n" +
			"<amt>${amt}</amt>\n" +
			"<tran_code>EX_1</tran_code>\n" +
			"<sub_code>1</sub_code>\n" +
			"<summ>代收代缴车船税金额：0.00元  滞纳金金额：0.00元  合计金额：0.00元  税款属期：201711-201712  保单号：${tran_seqno}</summ>\n" +
			"<tran_system>Xapayment</tran_system>\n" +
			"<tran_time>20171212000000</tran_time>\n" +
			"<cstm_no>43190113941011</cstm_no>\n" +
			"<prod_code>603100102</prod_code>\n" +
			"<cert_no></cert_no>\n" +
			"<entry_type>1</entry_type>\n" +
			"<flag>0</flag>\n" +
			"<acc_flag>2</acc_flag>\n" +
			"<per_acc></per_acc>\n" +
			"<per_acc_name></per_acc_name>\n" +
			"<per_cstm_id></per_cstm_id>\n" +
			"<per_open_inst></per_open_inst>\n" +
			"<per_paper_type></per_paper_type>\n" +
			"<per_paper_no></per_paper_no>\n" +
			"<com_acc>43190113941011</com_acc>\n" +
			"<com_acc_name>吉广控股有限公司</com_acc_name>\n" +
			"<com_cstm_id>43190113941011</com_cstm_id>\n" +
			"<com_open_inst>99000000</com_open_inst>\n" +
			"<com_permit_no>91220101605956021E</com_permit_no>\n" +
			"<com_org_inst_code></com_org_inst_code>\n" +
			"<com_acc_addr>长春净月高新技术产业开发区生态东街3330号创意孵化楼</com_acc_addr>\n" +
			"<com_acc_phone>0431-8802000</com_acc_phone>\n" +
			"<com_taxpayer_id>91220101605956021E</com_taxpayer_id>\n" +
			"<com_taxpayer_name>吉广控股有限公司</com_taxpayer_name>\n" +
			"<com_login_type>1</com_login_type>\n" +
			"<com_open_acc_bank>招商银行长春分行营业部</com_open_acc_bank>\n" +
			"<com_open_acc>43190113941011</com_open_acc>\n" +
			"<attribute1>${tran_seqno}</attribute1>\n" +
			"<attribute2>2017-12-12</attribute2>\n" +
			"<attribute3>1</attribute3>\n" +
			"<attribute4></attribute4>\n" +
			"<attribute5></attribute5>\n" +
			"</CustomerAndPolicyPlanDto></BODY></PACKET>\n";
	private static final String MSG22 = "<?xml version=\"1.0\" encoding=\"GBK\"?><PACKET type=\"REQUEST\" version=\"1.0\" ><BODY><CustomerAndPolicyPlanDto><tran_date>20171212</tran_date>\n" +
			"<tran_seqno>${tran_seqno}</tran_seqno>\n" +
			"<dtl_seqno>1</dtl_seqno>\n" +
			"<acc>43190113941011</acc>\n" +
			"<curr_type>001</curr_type>\n" +
			"<acc_inst>99010000</acc_inst>\n" +
			"<itm_no>22212105</itm_no>\n" +
			"<dr_cr_flag>2</dr_cr_flag>\n" +
			"<amt>${amt}</amt>\n" +
			"<tran_code>EX_1</tran_code>\n" +
			"<sub_code>1</sub_code>\n" +
			"<summ></summ>\n" +
			"<tran_system>Xapayment</tran_system>\n" +
			"<tran_time>20171212000000</tran_time>\n" +
			"<cstm_no>43190113941011</cstm_no>\n" +
			"<prod_code>603100104</prod_code>\n" +
			"<cert_no></cert_no>\n" +
			"<entry_type>1</entry_type>\n" +
			"<flag>0</flag>\n" +
			"<acc_flag>2</acc_flag>\n" +
			"<per_acc></per_acc>\n" +
			"<per_acc_name></per_acc_name>\n" +
			"<per_cstm_id></per_cstm_id>\n" +
			"<per_open_inst></per_open_inst>\n" +
			"<per_paper_type></per_paper_type>\n" +
			"<per_paper_no></per_paper_no>\n" +
			"<com_acc>43190113941011</com_acc>\n" +
			"<com_acc_name>吉广控股有限公司</com_acc_name>\n" +
			"<com_cstm_id>43190113941011</com_cstm_id>\n" +
			"<com_open_inst>99000000</com_open_inst>\n" +
			"<com_permit_no>91220101605956021E</com_permit_no>\n" +
			"<com_org_inst_code></com_org_inst_code>\n" +
			"<com_acc_addr>长春净月高新技术产业开发区生态东街3330号创意孵化楼</com_acc_addr>\n" +
			"<com_acc_phone>0431-8802000</com_acc_phone>\n" +
			"<com_taxpayer_id>91220101605956021E</com_taxpayer_id>\n" +
			"<com_taxpayer_name>吉广控股有限公司</com_taxpayer_name>\n" +
			"<com_login_type>1</com_login_type>\n" +
			"<com_open_acc_bank>招商银行长春分行营业部</com_open_acc_bank>\n" +
			"<com_open_acc>43190113941011</com_open_acc>\n" +
			"<attribute1>${tran_seqno}</attribute1>\n" +
			"<attribute2>2017-12-12</attribute2>\n" +
			"<attribute3>1</attribute3>\n" +
			"<attribute4></attribute4>\n" +
			"<attribute5></attribute5>\n" +
			"</CustomerAndPolicyPlanDto></BODY></PACKET>\n";
}
