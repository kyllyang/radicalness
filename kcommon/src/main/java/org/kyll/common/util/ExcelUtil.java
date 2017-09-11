package org.kyll.common.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.kyll.common.Const;
import org.kyll.common.exception.UnsupportedException;

import java.math.BigDecimal;

/**
 * User: Kyll
 * Date: 2017-09-07 14:36
 */
public class ExcelUtil {
	public static String getCellText(Cell cell) {
		String text;
		CellType cellType = cell.getCellTypeEnum();
		if (CellType._NONE == cellType) {
			text = cell.getStringCellValue();
		} else if (CellType.NUMERIC == cellType) {
			text = new BigDecimal(cell.getNumericCellValue()).toString();
		} else if (CellType.STRING == cellType) {
			text = cell.getStringCellValue();
		} else if (CellType.FORMULA == cellType) {
			text = cell.getCellFormula();
		} else if (CellType.BLANK == cellType) {
			text = cell.getStringCellValue();
		} else if (CellType.BOOLEAN == cellType) {
			text = String.valueOf(cell.getBooleanCellValue());
		} else if (CellType.ERROR == cellType) {
			text = String.valueOf(cell.getErrorCellValue());
		} else {
			throw new RuntimeException(new UnsupportedException(String.format(Const.EC_MSG_UNSUPPORTED, cellType)));
		}
		return text;
	}
}
