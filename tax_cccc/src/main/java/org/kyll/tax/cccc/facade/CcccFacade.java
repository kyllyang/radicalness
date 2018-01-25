package org.kyll.tax.cccc.facade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
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
import org.kyll.tax.cccc.domain.PrintBillMsg;
import org.kyll.tax.cccc.service.DataMsgYqService;
import org.kyll.tax.cccc.service.PrintBillMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	@Autowired
	private PrintBillMsgService printBillMsgService;

	public void execute1() {
		/*List<DataMsgYq> dataMsgYqList = dataMsgYqService.findList();
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
		}*/
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
		for (File file : new File("C:\\Users\\Administrator\\Documents\\work\\temp\\tax_pyab_word").listFiles((dir, name) -> name.endsWith("docx"))) {
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

				/*String totalPno = null; // 合并情况
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
				}*/

				for (int i = beginIndex + 1; i < endIndex; i++) {
					XWPFTableRow row = rowList.get(i);

					String pno = row.getCell(1).getText().trim();
					String amt = row.getCell(2).getText().trim();

					if (StringUtil.isNotBlank(pno) && StringUtil.isNotBlank(amt) && !"0".equals(pno) && !"0".equals(amt)) {
						/*if ("PYAB09901002018000079".equals(pno) || "PYAB09901002018000083".equals(pno)
								|| "PYAB09901002018000004".equals(pno) || "PYAB09901002018000028".equals(pno) || "PYAB09901002018000029".equals(pno)) {
							System.out.println(file + ", " + pno);
						}*/
						if (endIndex - beginIndex > 1) {
							list.add(new String[]{taxpayer, pno, amt});
						}
					}
				}
			}
		}

		/*for (String[] datas : list) {
			System.out.println("instr(t.tran_seqno, '" + datas[1] + "') > 0 or");
		//	System.out.println(datas[1]);
		}

		if (true) {
			return;
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
				map.put("dtl_seqno", "30");
				map.put("acc_inst", "99010000");
				map.put("amt", datas[2]);
				map.put("tran_code", "EX_1");
				map.put("summ", "");
				map.put("tran_time", tranTime);
				map.put("prod_code", "603100104");
				map.put("attribute1", datas[1]);
				map.put("attribute2", "");
				map.put("attribute3", "1");

				msg = replaceInfo(msg, map);
			//	System.out.println(msg);
				send(msg);
			}
		}
	}

	/**
	 * FOR 张朋玉 预约保单入库 excel
	 */
	public void execute9() {
		String tranDate = DateUtil.formatDateCompact(DateUtil.now());
		String tranTime = DateUtil.formatDatetimeCompact(DateUtil.removeHMS(DateUtil.now()));

		for (File file : new File("C:\\Users\\Administrator\\Documents\\work\\temp\\tax_pyab_excel").listFiles()) {
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
				System.out.println(pno);
			//	send(replaceInfo(msg, map));
			}
		}
	}

	public void execute10() {
		String[] strs = {
				"FC0037357",
				"FC0004500",
				"FC0030246",
				"FC0021711",
				"FC0030116",
				"FC0011866",
				"FC0000025",
				"FC0000397",
				"FC0000654",
				"FC0000708",
				"FC0000832",
				"FC0000941",
				"FC0000957",
				"FC0001605",
				"FC0001655",
				"FC0001942",
				"FC0001966",
				"FC0001997",
				"FC0002024",
				"FC0002681",
				"FC0003759",
				"FC0003765",
				"FC0006016",
				"FC0006283",
				"FC0006538",
				"FC0006782",
				"FC0007543",
				"FC0007823",
				"FC0008240",
				"FC0008517",
				"FC0008563",
				"FC0008730",
				"FC0010057",
				"FC0010920",
				"FC0011128",
				"FC0011323",
				"FC0012693",
				"FC0014846",
				"FC0016994",
				"FC0022953",
				"FC0023444",
				"FC0028408",
				"FC0030118",
				"FC0033980",
				"FC0028918",
				"FC0013572",
				"FC0008447",
				"FC0006068",
				"FC0011139",
				"FC0029569",
				"FC0029573",
				"FC0023065",
				"FC0007392",
				"FC0037354",
				"FC0003535",
				"FC0008761",
				"FC0010850",
				"FC0013542",
				"FC5206898",
				"FC5206958",
				"FC5207057",
				"FC5207116",
				"FC0001066",
				"FC0013213",
				"FC0032346",
				"FC5206919",
				"FC5206982",
				"FC5207034",
				"FC0000884",
				"FC0000929",
				"FC0001961",
				"FC0002064",
				"FC0003012",
				"FC0003528",
				"FC0008115",
				"FC0011041",
				"FC0013670",
				"FC0022954",
				"FC0005125",
				"FC0010087",
				"FC0014256",
				"FC0015631",
				"FC0022845",
				"FC0037267",
				"FC0038038",
				"FC0006363",
				"FC0001119",
				"FC0002333",
				"FC0002992",
				"FC0002994",
				"FC0005055",
				"FC0008526",
				"FC0009180",
				"FC0009998",
				"FC0010668",
				"FC0013046",
				"FC0014104",
				"FC0015979",
				"FC0029958",
				"FC0032914",
				"FC0035345",
				"FC0035887",
				"FC5206917",
				"FC5206929",
				"FC5207011",
				"FC5207082",
				"FC0029922",
				"FC0007799",
				"FC0010441",
				"FC0013064",
				"FC0016018",
				"FC0018925",
				"FC0020397",
				"FC0023338",
				"FC0033717",
				"FC0034763",
				"FC0038402",
				"FC5206827",
				"FC5206883",
				"FC5206916",
				"FC5206925",
				"FC5206934",
				"FC5206935",
				"FC5207070",
				"FC0003926",
				"FC0007038",
				"FC0007063",
				"FC0014335",
				"FC0019954",
				"FC0021829",
				"FC0022942",
				"FC0036307",
				"FC0037486",
				"FC5206855",
				"FC0003015",
				"FC0000552",
				"FC0000824",
				"FC0038089",
				"FC0038589",
				"FC0032732",
				"FC5206940",
				"FC5207020",
				"FC5207026",
				"FC5207062",
				"FC5207099",
				"FC0007006",
				"FC0008780",
				"FC0008781",
				"FC0009244",
				"FC0013943",
				"FC0015138",
				"FC0015326",
				"FC0015423",
				"FC0017921",
				"FC0018394",
				"FC0019254",
				"FC0021522",
				"FC0023983",
				"FC0010132",
				"FC0000718",
				"FC0001627",
				"FC0004380",
				"FC0005790",
				"FC0006580",
				"FC0009094",
				"FC0010127",
				"FC0010603",
				"FC0013582",
				"FC0014543",
				"FC0014713",
				"FC0030534",
				"FC0037115",
				"FC5206948",
				"FC5206985",
				"FC5206987",
				"FC0033661",
				"FC0035983",
				"FC0037259",
				"FC5206837",
				"FC5206863",
				"FC5206889",
				"FC5206895",
				"FC5206896",
				"FC5206936",
				"FC5206944",
				"FC5206950",
				"FC5206956",
				"FC5206970",
				"FC5206983",
				"FC5207068",
				"FC5207081",
				"FC5207121",
				"FC5207135",
				"FC5207021",
				"FC0007649",
				"FC0010649",
				"FC0013112",
				"FC0014895",
				"FC0019323",
				"FC0023370",
				"FC0027386",
				"FC0034070",
				"FC5206900",
				"FC5206938",
				"FC5207079",
				"FC0010002",
				"FC0018146",
				"FC0022740",
				"FC0030184",
				"FC5206909",
				"FC5206912",
				"FC5206924",
				"FC5206941",
				"FC5206966",
				"FC5207024",
				"FC0000044",
				"FC0000909",
				"FC0001996",
				"FC0006076",
				"FC0006502",
				"FC0012541",
				"FC0030106",
				"FC0000603",
				"FC0001545",
				"FC0002386",
				"FC0002832",
				"FC0004884",
				"FC0007810",
				"FC0008385",
				"FC0010276",
				"FC0012602",
				"FC0014127",
				"FC0016431",
				"FC0026926",
				"FC0037540",
				"FC0038110",
				"FC0038230",
				"FC0038794",
				"FC0038997",
				"FC0040441",
				"FC0041287",
				"FC5206828",
				"FC5206852",
				"FC5206858",
				"FC5206864",
				"FC5206901",
				"FC5206968",
				"FC5206978",
				"FC5207033",
				"FC5207047",
				"FC5207087",
				"FC5207089",
				"FC5207112",
				"FC5207145",
				"FC0001999",
				"FC0003900",
				"FC0007660",
				"FC0008283",
				"FC0009950",
				"FC0038060",
				"FC0008658",
				"FC0038152",
				"FC0001003",
				"FC0001500",
				"FC0001575",
				"FC0002591",
				"FC0000875",
				"FC0001094",
				"FC0002124",
				"FC0002960",
				"FC0003388",
				"FC0003605",
				"FC0003617",
				"FC0004680",
				"FC0004943",
				"FC0007033",
				"FC0008037",
				"FC0003505",
				"FC0004904",
				"FC0005045",
				"FC0007599",
				"FC0007629",
				"FC0008152",
				"FC0008170",
				"FC0008260",
				"FC0008345",
				"FC0010257",
				"FC0013104",
				"FC0019014",
				"FC0020291",
				"FC0027725",
				"FC0008200",
				"FC0008976",
				"FC0008977",
				"FC0009040",
				"FC0029182",
				"FC0035508",
				"FC5206868",
				"FC5206927",
				"FC5206945",
				"FC5206953",
				"FC5206986",
				"FC5207032",
				"FC0029920",
				"FC0000752",
				"FC0013177",
				"FC0014518",
				"FC0015526",
				"FC0016765",
				"FC0017413",
				"FC0026669",
				"FC0020865",
				"FC0021273",
				"FC0023151",
				"FC0025445",
				"FC0026385",
				"FC0026656",
				"FC0034949",
				"FC0038125",
				"FC5206874",
				"FC5206964",
				"FC5206995",
				"FC5207007",
				"FC0000936",
				"FC0016100",
				"FC0000253",
				"FC0001167",
				"FC0005159"
		};

		StringBuilder result = new StringBuilder();
		for (String s : strs) {
			result.append(s).append("\\|");
		}
		System.out.println(result);
	}

	public void execute11() {
		Map<String, String> accTypeMap = new HashMap<>();
		accTypeMap.put("1", "对私");
		accTypeMap.put("2", "对公");
		Map<String, String> billTypeMap = new HashMap<>();
		billTypeMap.put("1", "普通");
		billTypeMap.put("2", "专用");
		Map<String, String> openModeMap = new HashMap<>();
		openModeMap.put("1", "正常");
		openModeMap.put("4", "合并");
		openModeMap.put("5", "拆分");
		openModeMap.put("6", "红字");
		openModeMap.put("7", "合并拆分");
		openModeMap.put("8", "作废");

		String sql =
				"  from t_tax_print_bill_msg t\n" +
				" where t.open_date < '20180101'\n" +
				"   and t.bill_cert_no <> '0'\n" +
				"   and t.bill_no <> '0'\n" +
				"   and t.open_mode in ('1', '4', '5', '6', '7', '8')\n" +
				"   and exists (select '1'\n" +
				"          from t_tax_open_inst t0\n" +
				"         where t0.acc_inst not in ('FAF000000', 'FAF000001')\n" +
				"           and t0.open_point = t.open_point)\n";
		String countSql = "select count(1) count " + sql;

		List countList = dataMsgYqService.query(countSql);
		int totalRow = ((BigDecimal) countList.get(0)).intValue();

		List<String[]> dataList = new ArrayList<>();
		int pageSize = 1000;
		for (int i = 0, pageCount = totalRow / pageSize + 1; i < pageCount; i++) {
			int start = i * pageSize + 1;
			int end = (i + 1) * pageSize;
			System.out.println("获取数据[" + start + ", " + end + "]");

			/*if (i == 7) {
				break;
			}*/

			for (PrintBillMsg printBillMsg : printBillMsgService.findList(start, end)) {
				List<String> openIdxList = new ArrayList<>();
				for (String openIdx : printBillMsg.getOpenIdx().split("\\|")) {
					if (StringUtil.isNotBlank(openIdx)) {
						openIdxList.add(openIdx);
					}
				}

				List<Object[]> paList = dataMsgYqService.query("select t.tran_seqno, t.cstm_id, t.acc_type, a.acc_name, a.acc_no\n" +
						"  from t_tax_open_bill_msg t\n" +
						"  join (select t.acc_name, t.paper_no acc_no, t.cstm_id, '1' acc_type\n" +
						"          from t_tax_per_acc_msg t\n" +
						"        union all\n" +
						"        select t.acc_name, decode(t.permit_no, null, t.taxpayer_id, t.permit_no) acc_no, t.cstm_id, '2' acc_type\n" +
						"          from t_tax_com_acc_msg t) a\n" +
						"    on t.cstm_id = a.cstm_id\n" +
						" where " + toSqlIn("t.open_idx", openIdxList) + "\n" +
						" order by t.tran_seqno asc");

				for (Object[] paDatas : paList) {
					String p = StringUtil.toEmptyIf(paDatas[0]).trim();
					p = p.substring(p.indexOf("_") + 1);
					int markIndex = p.lastIndexOf("_");
					if (markIndex > -1) {
						if (!p.substring(markIndex + 1).startsWith("0")) {
							p = p.substring(0, markIndex);
						}
					}

					dataList.add(new String[]{p, StringUtil.toEmptyIf(paDatas[3]).trim(), StringUtil.toEmptyIf(paDatas[4]).trim(), accTypeMap.get(StringUtil.toEmptyIf(paDatas[2]).trim()),
							StringUtil.toEmptyIf(printBillMsg.getOpenDate()).trim(), StringUtil.toEmptyIf(printBillMsg.getTotal().toString()).trim(), StringUtil.toEmptyIf(printBillMsg.getBillCertNo()).trim(), StringUtil.toEmptyIf(printBillMsg.getBillNo()).trim(),
							billTypeMap.get(StringUtil.toEmptyIf(printBillMsg.getBillType()).trim()), openModeMap.get(StringUtil.toEmptyIf(printBillMsg.getOpenMode()).trim()),
							StringUtil.toEmptyIf(printBillMsg.getInName()).trim(), StringUtil.toEmptyIf(printBillMsg.getInTaxpayer()).trim(), StringUtil.toEmptyIf(printBillMsg.getInAddrPhone()).trim(), StringUtil.toEmptyIf(printBillMsg.getInBankAcc()).trim()});
				}
			}
		}

		String[] headerNames = {"保单号", "客户名称", "客户证件号", "客户类型", "开票日期", "发票金额", "发票代码", "发票号码", "发票类型", "开票方式", "购货单位名称", "购货单位纳税人识别号", "购货单位地址及电话", "购货单位开户行及账号"};

		int excelSize = 5000;
		for (int i = 0, excelCount = dataList.size() / excelSize + 1; i < excelCount; i++) {
			int start = i * excelSize;
			int end = (i + 1) * excelSize;

			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFFont font = workbook.createFont();
			font.setBold(true);

			XSSFCellStyle headerCellSyle = workbook.createCellStyle();
			headerCellSyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellSyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
			headerCellSyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerCellSyle.setFont(font);

			XSSFSheet sheet = workbook.createSheet();
			sheet.setDefaultColumnWidth(20);

			XSSFRow headerRow = sheet.createRow(sheet.getPhysicalNumberOfRows());
			for (int j = 0, length = headerNames.length; j < length; j++) {
				XSSFCell cell = headerRow.createCell(j);
				cell.setCellStyle(headerCellSyle);
				cell.setCellValue(new XSSFRichTextString(headerNames[j]));
			}

			for (int j = start; j < end; j++) {
				if (j < dataList.size()) {
					String[] datas = dataList.get(j);

					XSSFRow row = sheet.createRow(sheet.getPhysicalNumberOfRows());
					for (int k = 0, length = datas.length; k < length; k++) {
						row.createCell(k).setCellValue(new XSSFRichTextString(datas[k]));
					}
				}
			}

			File file = new File("C:\\Users\\Administrator\\Downloads\\" + (i + 1) + ".xlsx");
			System.out.println(file);

			try {
				workbook.write(new FileOutputStream(file));
			} catch (IOException e) {
				e.printStackTrace();
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
			workbook = new XSSFWorkbook(OPCPackage.open(new File("C:\\Users\\Administrator\\Documents\\work\\temp\\tax_common\\t_tax_data_msg_yq.xlsx")));
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
			connection.setRequestProperty("Cookie", "JSESSIONID=2LLdhnSShQxsD1wCkgSy1wxXrQJSX0B7l0z2Hh0s7fV1Rp07vrMt!1527925976");
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

	private String toSqlIn(String colunmName, List<String> list) {
		List<String> inList = new ArrayList<>();
		for (int i = 0, size = list.size(); i < size; i += 1000) {
			inList.add(colunmName + " in ('" + StringUtil.join(list.subList(i, size > 1000 + i ? 1000 + i : size), "', '") + "')");
		}
		return inList.size() == 1 ? inList.get(0) : "(" + StringUtil.join(inList, " or ") + ")";
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
	private static final String[] P_AS = {
			"PYAB09901002018000054",
			"PYAB09901002018000055",
			"PYAB09901002018000021",
			"PYAB09901002018000057",
			"PYAB09901002018000002",
			"PYAB09901002018000046",
			"PYAB09901002018000047",
			"PYAB09901002018000004",
			"PYAB09901002018000051",
			"PYAB09901002018000003",
			"PYAB09901002018000052",
			"PYAB09901002018000036",
			"PYAB09901002018000037",
			"PYAB09901002018000042",
			"PYAB09901002018000038",
			"PYAB09901002018000043",
			"PYAB09901002018000044",
			"PYAB09901002018000028",
			"PYAB09901002018000029",
			"PYAB09901002018000031",
			"PYAB09901002018000032",
			"PYAB09901002018000019",
			"PYAB09901002018000015",
			"PYAB09901002018000023",
			"PYAB09901002018000016",
			"PYAB09901002018000153",
			"PYAB09901002018000024",
			"PYAB09901002018000025",
			"PYAB09901002018000010",
			"PYAB09901002018000011",
			"PYAB09901002018000160",
			"PYAB09901002018000159",
			"PYAB09901002018000100",
			"PYAB09901002018000151",
			"PYAB09901002018000157",
			"PYAB09901002018000154",
			"PYAB09901002018000155",
			"PYAB09901002018000097",
			"PYAB09901002018000089",
			"PYAB09901002018000107",
			"PYAB09901002018000137",
			"PYAB09901002018000135",
			"PYAB09901002018000162",
			"PYAB09901002018000132",
			"PYAB09901002018000072",
			"PYAB09901002018000148",
			"PYAB09901002018000085",
			"PYAB09901002018000127",
			"PYAB09901002018000103",
			"PYAB09901002018000142",
			"PYAB09901002018000139",
			"PYAB09901002018000079",
			"PYAB09901002018000077",
			"PYAB09901002018000083",
			"PYAB09901002018000007",
			"PYAB09901002018000008",
			"PYAB09901002018000117",
			"PYAB09901002018000121",
			"PYAB09901002018000115",
			"PYAB09901002018000118",
			"PYAB09901002018000126",
			"PYAB09901002018000071",
			"PYAB09901002018000064",
			"PYAB09901002018000087",
			"PYAB09901002018000104",
			"PYAB09901002018000145",
			"PYAB09901002018000080",
			"PYAB09901002018000102",
			"PYAB09901002018000140",
			"PYAB09901002018000006",
			"PYAB09901002018000119",
			"PYAB09901002018000105",
			"PYAB09901002018000070",
			"PYAB09901002018000066",
			"PYAB09901002018000009",
			"PYAB09901002018000123",
			"PYAB09901002018000122",
			"PYAB09901002018000116",
			"PYAB09901002018000125",
			"PYAB09901002018000134",
			"PYAB09901002018000096",
			"PYAB09901002018000065",
			"PYAB09901002018000063",
			"PYAB09901002018000092",
			"PYAB09901002018000091",
			"PYAB09901002018000088",
			"PYAB09901002018000138",
			"PYAB09901002018000136",
			"PYAB09901002018000133",
			"PYAB09901002018000074",
			"PYAB09901002018000073",
			"PYAB09901002018000113",
			"PYAB09901002018000081",
			"PYAB09901002018000144",
			"PYAB09901002018000112",
			"PYAB09901002018000101",
			"PYAB09901002018000161",
			"PYAB09901002018000078",
			"PYAB09901002018000076",
			"PYAB09901002018000005",
			"PYAB09901002018000082",
			"PYAB09901002018000131",
			"PYAB09901002018000130",
			"PYAB09901002018000061",
			"PYAB09901002018000146",
			"PYAB09901002018000150",
			"PYAB09901002018000147",
			"PYAB09901002018000120",
			"PYAB09901002018000141",
			"PYAB09901002018000143",
			"PYAB09901002018000110",
			"PYAB09901002018000106",
			"PYAB09901002018000108",
			"PYAB09901002018000093",
			"PYAB09901002018000094",
			"PYAB09901002018000069",
			"PYAB09901002018000068",
			"PYAB09901002018000124",
			"PYAB09901002018000114",
			"PYAB09901002018000095",
			"PYAB09901002018000067",
			"PYAB09901002018000090",
			"PYAB09901002018000098",
			"PYAB09901002018000149",
			"PYAB09901002018000084",
			"PYAB09901002018000111",
			"PYAB09901002018000109",
			"PYAB09901002018000075",
			"PYAB09901002018000158",
			"PYAB09901002018000056",
			"PYAB09901002018000059",
			"PYAB09901002018000058",
			"PYAB09901002018000060",
			"PYAB09901002018000099",
			"PYAB09901002018000049",
			"PYAB09901002018000048",
			"PYAB09901002018000050",
			"PYAB09901002018000053",
			"PYAB09901002018000040",
			"PYAB09901002018000039",
			"PYAB09901002018000041",
			"PYAB09901002018000045",
			"PYAB09901002018000026",
			"PYAB09901002018000030",
			"PYAB09901002018000027",
			"PYAB09901002018000033",
			"PYAB09901002018000035",
			"PYAB09901002018000034",
			"PYAB09901002018000018",
			"PYAB09901002018000017",
			"PYAB09901002018000022",
			"PYAB09901002018000020",
			"PYAB09901002018000156",
			"PYAB09901002018000152",
			"PYAB09901002018000013",
			"PYAB09901002018000012",
			"PYAB09901002018000014",
			"PYAB09901002018000129",
			"PYAB09901002018000128",
			"PYAB09901002018000086"
	};

	private static final String[] QP_AS = {
			"PYAB09901002018000002",
			"PYAB09901002018000003",
			"PYAB09901002018000004",
			"PYAB09901002018000010",
			"PYAB09901002018000011",
			"PYAB09901002018000015",
			"PYAB09901002018000016",
			"PYAB09901002018000019",
			"PYAB09901002018000021",
			"PYAB09901002018000023",
			"PYAB09901002018000024",
			"PYAB09901002018000025",
			"PYAB09901002018000028",
			"PYAB09901002018000029",
			"PYAB09901002018000031",
			"PYAB09901002018000032",
			"PYAB09901002018000036",
			"PYAB09901002018000037",
			"PYAB09901002018000038",
			"PYAB09901002018000042",
			"PYAB09901002018000043",
			"PYAB09901002018000044",
			"PYAB09901002018000046",
			"PYAB09901002018000047",
			"PYAB09901002018000051",
			"PYAB09901002018000052",
			"PYAB09901002018000054",
			"PYAB09901002018000055",
			"PYAB09901002018000057",
			"PYAB09901002018000100",
			"PYAB09901002018000151",
			"PYAB09901002018000153",
			"PYAB09901002018000154",
			"PYAB09901002018000155",
			"PYAB09901002018000157",
			"PYAB09901002018000159",
			"PYAB09901002018000160"
	};
}
