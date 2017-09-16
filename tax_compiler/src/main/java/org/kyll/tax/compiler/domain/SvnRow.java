package org.kyll.tax.compiler.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User: Kyll
 * Date: 2017-09-15 14:22
 */
@NoArgsConstructor
public class SvnRow {
	@Getter @Setter private String type;
	@Getter @Setter private String path;
}
