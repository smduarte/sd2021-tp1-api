package tp1.impl.engine;

import java.util.ArrayList;
import java.util.List;

import com.gembox.spreadsheet.ExcelCell;
import com.gembox.spreadsheet.ExcelFile;
import com.gembox.spreadsheet.ExcelWorksheet;
import com.gembox.spreadsheet.SpreadsheetInfo;

import tp1.api.engine.AbstractSpreadsheet;
import tp1.api.engine.SpreadsheetEngine;


public class SpreadsheetEngineImpl implements SpreadsheetEngine {
	
	private SpreadsheetEngineImpl() {		
	}

	static public SpreadsheetEngine getInstance() {
		return new SpreadsheetEngineImpl();
	}
	
	
	public List<List<String>> computeSpreadsheetValues( AbstractSpreadsheet sheet ) {
        ExcelFile workbook = new ExcelFile();
        ExcelWorksheet worksheet = workbook.addWorksheet( sheet.sheetId());
        
        for( int i = 0; i < sheet.columns(); i++)
        	for( int j = 0; j < sheet.rows(); j++ )
        	{
        		String rawVal = sheet.cellRawValue(i, j);
        		ExcelCell cell = worksheet.getCell(i, j);
        		setCell( sheet, worksheet, cell, rawVal );
        	}
        worksheet.calculate();
        List<List<String>> rows = new ArrayList<>();
        for( int i = 0; i < sheet.columns(); i++)
        {
        	List<String> columns = new ArrayList<>();
        	for( int j = 0; j < sheet.rows(); j++ )
        	{
        		ExcelCell cell = worksheet.getCell(i, j);
        		columns.add( cell.getValue().toString() );
        	}
        	rows.add( columns );
        }        
        return rows;
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
			cell.setValue(rawVal);
		break;
		case IMPORTRANGE:
			setCell( sheet, worksheet, cell, sheet.getRangeValues("", ""));
		case EMPTY:
			break;
		};
	}
	
	
	static CellType parseRawValue( String rawVal ) {
		if( rawVal.length() == 0)
			return CellType.EMPTY;

		rawVal = rawVal.toLowerCase();

		if( rawVal.charAt(0) == '=')
			return rawVal.startsWith(IMPORTRANGE_FORMULA) ? CellType.IMPORTRANGE: CellType.FORMULA;
		
		if( rawVal.equals("true") || rawVal.equals("false"))
			return CellType.BOOLEAN;
		
		try {
			Double.parseDouble(rawVal);
			return CellType.NUMBER;
		} catch( Exception x ) {
		}
		return CellType.TEXT;
	}
	
	static {
		SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
	}
	
	private static final String IMPORTRANGE_FORMULA = "=importrange";

}
