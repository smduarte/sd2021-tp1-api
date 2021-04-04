package tp1.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class Cell {

	/**
	 * Translates a cell identifier in the format ColumnLine (e.g., A23 or C12) into a Pair with the indices
	 * to access the contents of that cell in a String[][]. 
	 * @param cellID - String with the textual representation of the cell
	 * @return Pair<Integer,Integer> where the first element of the pair represent the index of the row, and
	 * the second the index of the column.
	 * @throws InvalidCellIdException - if the cellID parameter is in an invalid format.
	 */
	public static final Pair<Integer,Integer> CellId2Indexes(String cellID) throws InvalidCellIdException {
		var m = CELL_PATTERN.matcher( cellID );
		if( m.matches() ) {
			var col = CellRange.col(m.group(1));
			var row = CellRange.row(m.group(2));
			return new ImmutablePair<>(row, col);
		} else
			throw new InvalidCellIdException(cellID + " is not a valid cell name.");			
	}
	
	
	/**
	 * Translates the indices for the row and column in the String[][] encoding of a spreadsheet
	 * to the name of the cell in the format ColumnRow (e.g., F14).
	 * @param rowIndex the row index of the cell
	 * @param columnIndex the column index of the cell
	 * @return String encoding the cell identifier in the format ColumnRow (e.g., A23 or C12)
	 */
	public static final String Indices2CellId(int rowIndex, int columnIndex) {
		var s = Character.toString('A' + (char)(columnIndex % CellRange.BASE));
		while(columnIndex >= CellRange.BASE) {
			columnIndex /= CellRange.BASE;
			s = Character.toString('A' + (char)(columnIndex % CellRange.BASE) - 1) + s;
		};
		return s + (rowIndex+1);
	}
	
	private static final Pattern CELL_PATTERN = Pattern.compile(CellRange.CELL_REGEX);
}