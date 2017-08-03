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

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getNpcCode() {
		return npcCode;
	}

	public void setNpcCode(String npcCode) {
		this.npcCode = npcCode;
	}

	public String getFcCcpcCode() {
		return fcCcpcCode;
	}

	public void setFcCcpcCode(String fcCcpcCode) {
		this.fcCcpcCode = fcCcpcCode;
	}

	public String getFcDrctBankCode() {
		return fcDrctBankCode;
	}

	public void setFcDrctBankCode(String fcDrctBankCode) {
		this.fcDrctBankCode = fcDrctBankCode;
	}

	public String getFcBankCode() {
		return fcBankCode;
	}

	public void setFcBankCode(String fcBankCode) {
		this.fcBankCode = fcBankCode;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}
}
