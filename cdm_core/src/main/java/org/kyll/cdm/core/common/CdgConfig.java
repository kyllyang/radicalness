package org.kyll.cdm.core.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * User: Kyll
 * Date: 2017-08-03 17:05
 */
@Component
@ConfigurationProperties(prefix = "cdg")
public class CdgConfig {
	private String appCode;
	private String npcCode;
	private String fcCcpcCode;
	private String fcDrctBankCode;
	private String fcBankCode;
	private String nodeCode;

	public CdgConfig() {
	}

	public String getAppCode() {
		return appCode;
	}

	public String getNpcCode() {
		return npcCode;
	}

	public String getFcCcpcCode() {
		return fcCcpcCode;
	}

	public String getFcDrctBankCode() {
		return fcDrctBankCode;
	}

	public String getFcBankCode() {
		return fcBankCode;
	}

	public String getNodeCode() {
		return nodeCode;
	}
}
