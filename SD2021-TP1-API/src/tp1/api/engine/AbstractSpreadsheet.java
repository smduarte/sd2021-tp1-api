package tp1.api.engine;

public interface AbstractSpreadsheet {
	
	int rows();
	int columns();
	
	String sheetId();
	
	String cellRawValue(int row, int col);
	
	String getRangeValues(String sheetId, String range);
}