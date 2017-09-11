package org.kyll.cdm.core;

import com.longtop.efmp.cdg.srv.bs.ICdgSrvBS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * User: Kyll
 * Date: 2017-07-29 22:58
 */
@SpringBootApplication
@EnableTransactionManagement
public class Application {
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
