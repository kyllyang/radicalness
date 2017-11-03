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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

			System.out.println(post(json));
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
			connection.setRequestProperty("Cookie", "JSESSIONID=qyL7Z3GQGdsbJy5DLy5yRhQ31TvVCnqKL2qRx5KTY26hcNcrzQNF!2112758414");
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
			{"PYAB09901002017001405", "205920"},
			{"PYAB09901002017001404", "649680"},
			{"PYAB09901002017001403", "8400"},
			{"PYAB09901002017001402", "110400"},
			{"PYAB09901002017001348", "55320"},
			{"PYAB09901002017001401", "2520"},
			{"PYAB09901002017001400", "1440"},
			{"PYAB09901002017001396", "82680"},
			{"PYAB09901002017001395", "5880"},
			{"PYAB09901002017001394", "2520"},
			{"PYAB09901002017001393", "13560"},
			{"PYAB09901002017001392", "2520"},
			{"PYAB09901002017001390", "8520"},
			{"PYAB09901002017001388", "4920"},
			{"PYAB09901002017001387", "19200"},
			{"PYAB09901002017001385", "9600"},
			{"PYAB09901002017001384", "840"},
			{"PYAB09901002017001383", "5400"},
			{"PYAB09901002017001382", "11400"},
			{"PYAB09901002017001353", "4440"},
			{"PYAB09901002017001380", "35040"},
			{"PYAB09901002017001351", "46920"},
			{"PYAB09901002017001378", "95520"},
			{"PYAB09901002017001377", "61440"},
			{"PYAB09901002017001375", "34320"},
			{"PYAB09901002017001374", "15240"},
			{"PYAB09901002017001373", "8280"},
			{"PYAB09901002017001372", "11280"},
			{"PYAB09901002017001371", "16320"},
			{"PYAB09901002017001354", "720"},
			{"PYAB09901002017001355", "360"},
			{"PYAB09901002017001369", "23040"},
			{"PYAB09901002017001358", "360"},
			{"PYAB09901002017001357", "240"},
			{"PYAB09901002017001356", "480"},
			{"PYAB09901002017001359", "480"},
			{"PYAB09901002017001360", "240"},
			{"PYAB09901002017001361", "480"},
			{"PYAB09901002017001362", "360"},
			{"PYAB09901002017001349", "600"},
			{"PYAB09901002017001363", "360"},
			{"PYAB09901002017001364", "600"},
			{"PYAB09901002017001365", "360"},
			{"PYAB09901002017001350", "480"},
			{"PYAB09901002017001366", "720"},
			{"PYAB09901002017001367", "480"},
			{"PYAB09901002017001399", "82300"},
			{"PYAB09901002017001398", "11100"},
			{"PYAB09901002017001397", "7400"},
			{"PYAB09901002017001368", "7100"},
			{"PYAB09901002017001391", "5200"},
			{"PYAB09901002017001389", "8500"},
			{"PYAB09901002017001386", "1100"},
			{"PYAB09901002017001381", "7900"},
			{"PYAB09901002017001379", "7200"},
			{"PYAB09901002017001352", "29000"},
			{"PYAB09901002017001376", "2600"},
			{"PYAB09901002017001370", "3100"}
	};

	/**
	 * 一汽解放汽车销售有限公司， 注意修改日期为当前日期
	 */
	private static final String MSG = "<?xml version=\"1.0\" encoding=\"GBK\"?><PACKET type=\"REQUEST\" version=\"1.0\" ><BODY><CustomerAndPolicyPlanDto><tran_date>20171031</tran_date>\n" +
			"\t<tran_seqno>${tran_seqno}</tran_seqno>\n" +
			"\t<dtl_seqno>1</dtl_seqno>\n" +
			"\t<acc>1007633957</acc>\n" +
			"\t<curr_type>001</curr_type>\n" +
			"\t<acc_inst>99010000</acc_inst>\n" +
			"\t<itm_no>22212105</itm_no>\n" +
			"\t<dr_cr_flag>2</dr_cr_flag>\n" +
			"\t<amt>${amt}</amt>\n" +
			"\t<tran_code>1</tran_code>\n" +
			"\t<sub_code>1</sub_code>\n" +
			"\t<summ></summ>\n" +
			"\t<tran_system>Xapayment</tran_system>\n" +
			"\t<tran_time>20171031000000</tran_time>\n" +
			"\t<cstm_no>1007633957</cstm_no>\n" +
			"\t<prod_code>603100104</prod_code>\n" +
			"\t<cert_no></cert_no>\n" +
			"\t<entry_type>1</entry_type>\n" +
			"\t<flag>0</flag>\n" +
			"\t<acc_flag>2</acc_flag>\n" +
			"\t<per_acc></per_acc>\n" +
			"\t<per_acc_name></per_acc_name>\n" +
			"\t<per_cstm_id></per_cstm_id>\n" +
			"\t<per_open_inst></per_open_inst>\n" +
			"\t<per_paper_type></per_paper_type>\n" +
			"\t<per_paper_no></per_paper_no>\n" +
			"\t<com_acc>4200222409000001829</com_acc>\n" +
			"\t<com_acc_name>一汽解放汽车销售有限公司</com_acc_name>\n" +
			"\t<com_cstm_id>1007633957</com_cstm_id>\n" +
			"\t<com_open_inst>99000000</com_open_inst>\n" +
			"\t<com_permit_no>91220101123917070P</com_permit_no>\n" +
			"\t<com_org_inst_code></com_org_inst_code>\n" +
			"\t<com_acc_addr>长春市绿园区迎春路617号</com_acc_addr>\n" +
			"\t<com_acc_phone>0431-85902891</com_acc_phone>\n" +
			"\t<com_taxpayer_id>91220101123917070P</com_taxpayer_id>\n" +
			"\t<com_taxpayer_name>一汽解放汽车销售有限公司</com_taxpayer_name>\n" +
			"\t<com_login_type>1</com_login_type>\n" +
			"\t<com_open_acc_bank>工行一汽支行</com_open_acc_bank>\n" +
			"\t<com_open_acc>4200222409000001829</com_open_acc>\n" +
			"\t<attribute1>${tran_seqno}</attribute1>\n" +
			"\t<attribute2></attribute2>\n" +
			"\t<attribute3></attribute3>\n" +
			"\t<attribute4></attribute4>\n" +
			"\t<attribute5></attribute5>\n" +
			"</CustomerAndPolicyPlanDto></BODY></PACKET>\n";
}
