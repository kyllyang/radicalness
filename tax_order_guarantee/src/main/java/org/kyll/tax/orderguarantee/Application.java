package org.kyll.tax.orderguarantee;

import org.kyll.tax.orderguarantee.service.ExcelService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * User: Kyll
 * Date: 2017-07-29 22:58
 */
@SpringBootApplication
public class Application {
	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		ExcelService excelService = context.getBean(ExcelService.class);
		excelService.read("C:\\Users\\Administrator\\OneDrive\\工作\\融通\\项目\\增值税\\预约保单手工入库\\01、7月青岛系统录入结果 - 副本.xlsx");
	//	excelService.read();
	}
}
