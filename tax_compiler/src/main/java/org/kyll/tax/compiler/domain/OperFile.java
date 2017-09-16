package org.kyll.tax.compiler.domain;

import lombok.Getter;
import lombok.Setter;
import org.kyll.tax.compiler.common.Oper;

/**
 * User: Kyll
 * Date: 2017-09-15 14:54
 */
public class OperFile {
	@Getter @Setter private Oper oper;
	@Getter @Setter private String path;

	public OperFile() {
	}
}
