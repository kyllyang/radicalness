package org.kyll.springbootfirst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

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

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
