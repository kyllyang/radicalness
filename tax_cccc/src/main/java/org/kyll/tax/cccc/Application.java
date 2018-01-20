package org.kyll.tax.cccc;

import org.kyll.tax.cccc.facade.CcccFacade;
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
		context.getBean(CcccFacade.class).execute9();
	}
}
