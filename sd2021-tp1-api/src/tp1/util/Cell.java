package tp1.util;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.shaded.apache.poi.hssf.record.aggregates.ChartSubstreamRecordAggregate;

public class Cell {

	/**
	 * Translates a cell identifier in the format ColumnLine (e.g., A23 or C12) into a Pair with the indexes
	 * to access the contents of that cell in a List<List<String>> format (where the external List has the lines
	 * and the internal lists have the columns in order. 
	 * @param cellID - String with the textual representation of the cell
	 * @return Pair<Integer,Integer> where the first element of the pair represent the index of the line, and
	 * the second the index of the column.
	 * @throws InvalidCellIdException - if the cellID parameter is in an invalid format.
	 */
	public static final Pair<Integer,Integer> CellId2ListIndexes(String cellID) throws InvalidCellIdException {
		char column = cellID.charAt(0);
		if(column < 'A' || column > 'Z') 
			throw new InvalidCellIdException(cellID + " is not a valid cell name.");
		int line = -1;
		try {
			line = Integer.parseInt(cellID.substring(1));
		} catch (NumberFormatException e) {		}
		if(line <= 0)
			throw new InvalidCellIdException(cellID + " is not a valid cell name.");
		return new ImmutablePair<Integer, Integer>(line-1, (column - 'A'));
	}
	
	
	/**
	 * Translates the indexes for the line and colum in the List<List<String>> encoding of a spreadsheet
	 * to the name of the cell in the format ColumnLine (e.g., F14).
	 * @param lineIndex the index of the list containing line representation in the spreadsheet
	 * @param columnIndex the index within the list encoding a line that represents the position of a column
	 * @return String encoding the cell identifier in the format ColumnLine (e.g., A23 or C12)
	 */
	public static final String ListIndexes2CellId(int lineIndex, int columnIndex) {
		return Character.toString((columnIndex + 'A')) + (lineIndex + 1);
	}
}
