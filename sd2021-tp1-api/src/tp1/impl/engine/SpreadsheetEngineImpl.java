package tp1.impl.engine;


import java.util.regex.Pattern;

import com.gembox.spreadsheet.ExcelCell;
import com.gembox.spreadsheet.ExcelFile;
import com.gembox.spreadsheet.ExcelWorksheet;
import com.gembox.spreadsheet.SpreadsheetInfo;

import tp1.api.engine.AbstractSpreadsheet;
import tp1.api.engine.SpreadsheetEngine;
import tp1.util.CellRange;


public class SpreadsheetEngineImpl implements SpreadsheetEngine {
	
	private static final String ERROR = "#ERROR?";
	private SpreadsheetEngineImpl() {		
	}

	static public SpreadsheetEngine getInstance() {
		return new SpreadsheetEngineImpl();
	}
	
	
	public String[][] computeSpreadsheetValues(AbstractSpreadsheet sheet) {
		ExcelFile workbook = new ExcelFile();
		ExcelWorksheet worksheet = workbook.addWorksheet(sheet.sheetId());

		for (int i = 0; i < sheet.rows(); i++)
			for (int j = 0; j < sheet.columns(); j++) {
				String rawVal = sheet.cellRawValue(i, j);
				ExcelCell cell = worksheet.getCell(i, j);
				setCell(sheet, worksheet, cell, rawVal);
			}

//		try {
//			workbook.save("/tmp/" + sheet.sheetId() + ".xls");
//		} catch( Exception x ) {
//			x.printStackTrace();
//		}
		worksheet.calculate();

		var cells = new String[sheet.rows()][sheet.columns()];
		for (int row = 0; row < sheet.rows(); row++) {
			for (int col = 0; col < sheet.columns(); col++) {
				ExcelCell cell = worksheet.getCell(row, col);
				var value = cell.getValue();
				cells[row][col] = value != null ? value.toString() : ERROR;
			}
		}
		return cells;
	}
	
	enum CellType { EMPTY, BOOLEAN, NUMBER, IMPORTRANGE, TEXT, FORMULA };
	
	static void setCell( AbstractSpreadsheet sheet, ExcelWorksheet worksheet, ExcelCell cell, String rawVal ) {
		CellType type = parseRawValue( rawVal );
		
		switch( type ) {
		case BOOLEAN:
				cell.setValue( Boolean.parseBoolean( rawVal ));
			break;
		case NUMBER:
				cell.setValue( Double.parseDouble(rawVal));
			break;
		case FORMULA:
				cell.setFormula(rawVal);
			break;
		case TEXT:
		case EMPTY:
			cell.setValue(rawVal);
		break;
		case IMPORTRANGE:
			var matcher = IMPORTRANGE_PATTERN.matcher(rawVal);
			if( matcher.matches()) {
				var sheetUrl = matcher.group(1);
				var range = matcher.group(2);
				var values = sheet.getRangeValues(sheetUrl, range);
				if( values != null )
					applyRange( worksheet, cell, new CellRange(range), values);
				else
					cell.setValue(ERROR);
			}
			break;
		};
	}
	
	
	private static void applyRange(ExcelWorksheet worksheet, ExcelCell cell0, CellRange range, String[][] values) {
		int row0 = cell0.getRow().getIndex(), col0 = cell0.getColumn().getIndex();

		for (int r = 0; r < range.rows(); r++)
			for (int c = 0; c < range.cols(); c++) {
				var cell = worksheet.getCell(row0 + r, col0 + c);
				setCell(null, worksheet, cell, values[r][c]);
			}
	}

	static CellType parseRawValue(String rawVal) {
		if (rawVal.length() == 0)
			return CellType.EMPTY;

		rawVal = rawVal.toLowerCase();

		if (rawVal.charAt(0) == '=')
			return rawVal.startsWith(IMPORTRANGE_FORMULA) ? CellType.IMPORTRANGE : CellType.FORMULA;

		if (rawVal.equals("true") || rawVal.equals("false"))
			return CellType.BOOLEAN;

		try {
			Double.parseDouble(rawVal);
			return CellType.NUMBER;
		} catch (Exception x) {
		}
		return CellType.TEXT;
	}
	
	static {
		SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
	}
	
	private static final String URL_REGEX = "(.+)";
	private static final String IMPORTRANGE_FORMULA = "=importrange";
	private static final Pattern IMPORTRANGE_PATTERN = Pattern.compile(String.format("=importrange\\(\"%s\",\"(%s)\"\\)", URL_REGEX, CellRange.RANGE_REGEX));
}
