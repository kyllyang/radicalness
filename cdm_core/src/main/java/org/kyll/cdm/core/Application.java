package org.kyll.cdm.core;

import com.longtop.efmp.cdg.srv.bs.ICdgSrvBS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

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
	public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setDatabase(Database.ORACLE);
		hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.Oracle10gDialect");
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		hibernateJpaVendorAdapter.setPrepareConnection(true);
		hibernateJpaVendorAdapter.setShowSql(true);

		Properties properties = new Properties();
		properties.setProperty("hibernate.cache.use_second_level_cache", "true");
		properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		properties.setProperty("hibernate.cache.use_query_cache", "true");
		properties.setProperty("hibernate.generate_statistics", "true");

		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		localContainerEntityManagerFactoryBean.setJpaProperties(properties);
		localContainerEntityManagerFactoryBean.setDataSource(dataSource);

		localContainerEntityManagerFactoryBean.afterPropertiesSet();
		return localContainerEntityManagerFactoryBean.getObject();
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
		jpaTransactionManager.setJpaDialect(new HibernateJpaDialect());
		return jpaTransactionManager;
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
