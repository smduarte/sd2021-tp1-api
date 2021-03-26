package tp1.api;

import java.util.Set;
import java.util.List;

public class Spreadsheet {
	String sheetId;
	String sheetURL;

	String owner;
	Set<String> sharedWith;

	int lines, columns;
	
	List<List<String>> rawValues;
}
