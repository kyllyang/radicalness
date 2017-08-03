package org.kyll.cdm.core;

import com.longtop.efmp.cdg.srv.bs.ICdgSrvBS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import javax.sql.DataSource;

/**
 * User: Kyll
 * Date: 2017-07-29 22:58
 */
@SpringBootApplication
public class Application {
	@Bean
	@ConfigurationProperties(prefix = "datasource.oracle")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public JdbcTemplate jdbcTemplate(){
		return new JdbcTemplate(dataSource());
	}

	@Bean
	public HttpComponentsHttpInvokerRequestExecutor httpComponentsHttpInvokerRequestExecutor() {
		HttpComponentsHttpInvokerRequestExecutor httpComponentsHttpInvokerRequestExecutor = new HttpComponentsHttpInvokerRequestExecutor();
		httpComponentsHttpInvokerRequestExecutor.setConnectTimeout(300000);
		httpComponentsHttpInvokerRequestExecutor.setReadTimeout(300000);
		return httpComponentsHttpInvokerRequestExecutor;
	}

	@Bean("cdgService")
	public HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean(HttpComponentsHttpInvokerRequestExecutor httpComponentsHttpInvokerRequestExecutor) {
		HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
		httpInvokerProxyFactoryBean.setServiceUrl("");
		httpInvokerProxyFactoryBean.setServiceInterface(ICdgSrvBS.class);
		httpInvokerProxyFactoryBean.setHttpInvokerRequestExecutor(httpComponentsHttpInvokerRequestExecutor);
		return httpInvokerProxyFactoryBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
