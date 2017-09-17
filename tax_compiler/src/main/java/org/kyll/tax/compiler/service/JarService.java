package org.kyll.tax.compiler.service;

import lombok.extern.slf4j.Slf4j;
import org.kyll.tax.compiler.common.Config;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: Kyll
 * Date: 2017-09-17 11:10
 */
@Slf4j
@Component
public class JarService {
	public void jarWar() {
		log.info("打包 war 文件");

		Process process = null;
		try {
			process = Runtime.getRuntime().exec(Config.CMD_JAR_WAR, null, new File(Config.TARGET_PATH + "war"));
		} catch (IOException e) {
			log.error("jar war 命令执行异常", e);
			System.exit(1);
		}

		try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"))) {
			String line;
			while ((line = in.readLine()) != null) {
				log.info(line);
			}
		} catch (IOException e) {
			log.error("jar war 命令执行异常", e);
			System.exit(1);
		}
	}

	public void jarEar() {
		log.info("打包 ear 文件");

		Process process = null;
		try {
			process = Runtime.getRuntime().exec(Config.CMD_JAR_EAR, null, new File(Config.TARGET_PATH + "ear"));
		} catch (IOException e) {
			log.error("jar ear 命令执行异常", e);
			System.exit(1);
		}

		try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"))) {
			String line;
			while ((line = in.readLine()) != null) {
				log.info(line);
			}
		} catch (IOException e) {
			log.error("jar ear 命令执行异常", e);
			System.exit(1);
		}
	}
}
