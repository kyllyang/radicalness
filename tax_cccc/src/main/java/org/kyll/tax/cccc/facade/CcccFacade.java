package org.kyll.tax.cccc.facade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.kyll.common.util.StringUtil;
import org.kyll.tax.cccc.domain.DataMsgYq;
import org.kyll.tax.cccc.service.DataMsgYqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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

	public void execute2() {
		for (int i = 0, length = DATAS.length; i < length; i++) {
			String[] data = DATAS[i];

			Map<String, String> map = new HashMap<>();
			map.put("aa", MSG.replace("${tran_seqno}", data[0]).replace("${amt}", data[1]));

			String json = toJson(map);
			System.out.println(i + ": " + json);

			long curr = System.currentTimeMillis();
			System.out.println(post(json));
			System.out.println("耗费 " + (System.currentTimeMillis() - curr) + " 毫秒");
		}
	}

	public void execute3() {
		StringBuilder txt = new StringBuilder();

		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\Administrator\\Downloads\\msg.txt"))))) {
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
			msgList.add(st.nextToken().replace("\"\"", "\""));
		}

		for (int i = 0, size = msgList.size(); i < size; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("aa", msgList.get(i));

			String json = toJson(map);
			System.out.println(i + ": " + json);

			/*long curr = System.currentTimeMillis();
			System.out.println(post(json));
			System.out.println("耗费 " + (System.currentTimeMillis() - curr) + " 毫秒");*/
		}
	}

	public void execute4() {
		List<String> list = new ArrayList<>();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\Administrator\\Downloads\\data.txt"))))) {
			String line;
			while ((line = in.readLine()) != null) {
				list.add(line.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int num = 100;
		for (int i = 0, size = list.size(); i < size; i += num) {
			String sql = "select t.open_idx, t.tax_date, t.tran_seqno, t.open_stat from t_tax_open_bill_msg t where （";
			for (int j = i; j < i + num; j++) {
				if (j < size) {
					if (j > i) {
						sql += "or ";
					}
					sql += "instr(t.tran_seqno, '" + list.get(j) + "') > 0 ";
				}
			}
			sql += ") and t.tax_date <= '20170331' ";

			long curr = System.currentTimeMillis();
			List<Object[]> resultList = dataMsgYqService.query(sql);
			System.out.println(i + " 耗费 " + (System.currentTimeMillis() - curr) + " 毫秒");

			for (Object[] objects : resultList) {
				System.out.println(Arrays.toString(objects));
			}
		}
	}

	private static String post(String content) {
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
			connection.setRequestProperty("Cookie", "JSESSIONID=4mQDhWJBJWnN2rJbJ3jLrpplzRWr8hr9sdn2smGQQTCnPFLxypk3!-1112196530");
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
			{"PYAB09901002017001536", "178320"},
			{"PYAB09901002017001537", "559560"},
			{"PYAB09901002017001533", "7680"},
			{"PYAB09901002017001534", "107880"},
			{"PYAB09901002017001535", "43800"},
			{"PYAB09901002017001529", "2520"},
			{"PYAB09901002017001527", "1440"},
			{"PYAB09901002017001523", "79080"},
			{"PYAB09901002017001525", "2400"},
			{"PYAB09901002017001524", "2880"},
			{"PYAB09901002017001519", "13800"},
			{"PYAB09901002017001520", "3240"},
			{"PYAB09901002017001516", "8760"},
			{"PYAB09901002017001511", "4920"},
			{"PYAB09901002017001507", "120"},
			{"PYAB09901002017001506", "7920"},
			{"PYAB09901002017001505", "720"},
			{"PYAB09901002017001500", "6000"},
			{"PYAB09901002017001502", "12000"},
			{"PYAB09901002017001512", "4320"},
			{"PYAB09901002017001514", "31800"},
			{"PYAB09901002017001503", "43080"},
			{"PYAB09901002017001509", "76560"},
			{"PYAB09901002017001504", "63120"},
			{"PYAB09901002017001498", "71280"},
			{"PYAB09901002017001499", "9240"},
			{"PYAB09901002017001501", "6720"},
			{"PYAB09901002017001496", "22440"},
			{"PYAB09901002017001518", "15840"},
			{"PYAB09901002017001482", "600"},
			{"PYAB09901002017001531", "16440"},
			{"PYAB09901002017001484", "360"},
			{"PYAB09901002017001483", "960"},
			{"PYAB09901002017001530", "1080"},
			{"PYAB09901002017001488", "480"},
			{"PYAB09901002017001487", "240"},
			{"PYAB09901002017001486", "720"},
			{"PYAB09901002017001485", "360"},
			{"PYAB09901002017001491", "360"},
			{"PYAB09901002017001490", "360"},
			{"PYAB09901002017001489", "600"},
			{"PYAB09901002017001493", "360"},
			{"PYAB09901002017001492", "720"},
			{"PYAB09901002017001495", "720"},
			{"PYAB09901002017001494", "480"},
			{"PYAB09901002017001532", "145600"},
			{"PYAB09901002017001528", "8400"},
			{"PYAB09901002017001526", "4800"},
			{"PYAB09901002017001522", "5600"},
			{"PYAB09901002017001521", "4600"},
			{"PYAB09901002017001517", "5800"},
			{"PYAB09901002017001513", "5600"},
			{"PYAB09901002017001508", "11800"},
			{"PYAB09901002017001510", "23700"},
			{"PYAB09901002017001497", "1300"},
			{"PYAB09901002017001515", "1500"}
	};

	/**
	 * 一汽解放汽车销售有限公司， 注意修改日期为当前日期
	 * <tran_seqno>${tran_seqno}</tran_seqno>
	 * <amt>${amt}</amt>
	 * <attribute1>${tran_seqno}</attribute1>
	 */
	private static final String MSG = "<?xml version=\"1.0\" encoding=\"GBK\"?><PACKET type=\"REQUEST\" version=\"1.0\" ><BODY><CustomerAndPolicyPlanDto><tran_date>20171122</tran_date>\n" +
			"<tran_seqno>${tran_seqno}</tran_seqno>\n" +
			"<dtl_seqno>1</dtl_seqno>\n" +
			"<acc>4200222409000001829</acc>\n" +
			"<curr_type>001</curr_type>\n" +
			"<acc_inst>99010000</acc_inst>\n" +
			"<itm_no>22212105</itm_no>\n" +
			"<dr_cr_flag>2</dr_cr_flag>\n" +
			"<amt>${amt}</amt>\n" +
			"<tran_code>1</tran_code>\n" +
			"<sub_code>1</sub_code>\n" +
			"<summ></summ>\n" +
			"<tran_system>Xapayment</tran_system>\n" +
			"<tran_time>20171030000000</tran_time>\n" +
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
			"<attribute2>2017-11-22</attribute2>\n" +
			"<attribute3></attribute3>\n" +
			"<attribute4></attribute4>\n" +
			"<attribute5></attribute5>\n" +
			"</CustomerAndPolicyPlanDto></BODY></PACKET>\n";
}
