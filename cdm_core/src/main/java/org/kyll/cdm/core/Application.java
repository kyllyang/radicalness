package org.kyll.cdm.core;

import com.longtop.efmp.cdg.srv.bs.ICdgSrvBS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
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
	public HttpComponentsHttpInvokerRequestExecutor httpComponentsHttpInvokerRequestExecutor(@Value("${cdg.connectTimeout}") Integer connectTimeout, @Value("${cdg.readTimeout}") Integer readTimeout) {
		HttpComponentsHttpInvokerRequestExecutor httpComponentsHttpInvokerRequestExecutor = new HttpComponentsHttpInvokerRequestExecutor();
		httpComponentsHttpInvokerRequestExecutor.setConnectTimeout(connectTimeout);
		httpComponentsHttpInvokerRequestExecutor.setReadTimeout(readTimeout);
		return httpComponentsHttpInvokerRequestExecutor;
	}

	@Bean
	public HttpInvokerProxyFactoryBean cdgService(HttpComponentsHttpInvokerRequestExecutor httpComponentsHttpInvokerRequestExecutor, @Value("${cdg.serverUrl}") String serviceUrl) {
		HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
		httpInvokerProxyFactoryBean.setServiceUrl(serviceUrl);
		httpInvokerProxyFactoryBean.setServiceInterface(ICdgSrvBS.class);
		httpInvokerProxyFactoryBean.setHttpInvokerRequestExecutor(httpComponentsHttpInvokerRequestExecutor);
		return httpInvokerProxyFactoryBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
