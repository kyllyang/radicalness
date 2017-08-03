package org.kyll.cdm.core.entity;

import org.kyll.base.persistence.Entity;

/**
 * User: Kyll
 * Date: 2017-08-02 15:56
 * ECDS 报文规则
 */
public class EcdsMsgRule extends Entity<Long> {
	private String code;
	private Integer sort;
	private String rawXmlTag;
	private String rawMsgTag;
	private String required;
	private String format;
	private String description;
	private String xmlTag;
	private String msgTag;
	private String rawFieldName;
	private String fieldName;

	public EcdsMsgRule() {
	}
}
