package org.kyll.tax.compiler.service;

import lombok.extern.slf4j.Slf4j;
import org.kyll.tax.compiler.common.Config;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: Kyll
 * Date: 2017-09-19 17:30
 */
@Slf4j
@Component
public class DeployService {
	public void execute() {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(Config.CMD_DEPLOY);
		} catch (IOException e) {
			log.error("weblogic.Deployer 命令执行异常", e);
			System.exit(1);
		}

		try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"))) {
			String line;
			while ((line = in.readLine()) != null) {
				log.info(line);
			}
		} catch (IOException e) {
			log.error("weblogic.Deployer 命令执行异常", e);
			System.exit(1);
		}
	}
}
