package org.kyll.cdm.core.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * User: Kyll
 * Date: 2017-08-02 15:56
 * ECDS 报文规则
 */
public class EcdsMsgRule {
	@Getter @Setter private Long id;
	@Getter @Setter private String code;
	@Getter @Setter private Integer sort;
	@Getter @Setter private String rawXmlTag;
	@Getter @Setter private String rawMsgTag;
	@Getter @Setter private String required;
	@Getter @Setter private String format;
	@Getter @Setter private String description;
	@Getter @Setter private String xmlTag;
	@Getter @Setter private String msgTag;
	@Getter @Setter private String rawFieldName;
	@Getter @Setter private String fieldName;

	public EcdsMsgRule() {
	}
}
