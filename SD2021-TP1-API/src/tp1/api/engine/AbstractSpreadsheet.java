package tp1.api.engine;

import java.util.List;

/**
 * 
 * Interface used to feed a spreadsheet to the SpreadsheetEngine and compute its values.
 * 
 * The method getRangeValues() will be called by the engine to resolve "=importrange(...)" formulas
 * 
 * @author smd
 *
 */
public interface AbstractSpreadsheet {
	
	int rows();
	int columns();
	
	String sheetId();
	
	String cellRawValue(int row, int col);
	
	List<String> getRangeValues(String sheetId, String range);
}