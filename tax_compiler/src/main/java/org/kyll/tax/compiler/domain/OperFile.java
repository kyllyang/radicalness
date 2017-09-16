package org.kyll.tax.compiler.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kyll.tax.compiler.common.Oper;

/**
 * User: Kyll
 * Date: 2017-09-15 14:54
 */
@NoArgsConstructor
public class OperFile {
	@Getter @Setter private Oper oper;
	@Getter @Setter private String path;
	@Getter @Setter private String targetPath;
}
