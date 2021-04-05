package tp1.util;

import java.util.regex.Pattern;

/**
 * 
 * Utility class to represent and manipulate a range of cells.
 * 
 * @author smduarte
 *
 */
public class CellRange {
	
	public final int topRow, topCol, botRow, botCol;
	
	/**
	 * Takes a range string e.g. "A3:C4" and computes the index coordinates of the top and bottom corner cells
	 * @param range
	 */
	public CellRange( String range ) {
		var matcher = RANGE_PATTERN.matcher(range);
		if( matcher.matches() ) {
			topCol = col( matcher.group(2));
			topRow = row( matcher.group(3));
			botCol = col( matcher.group(5));
			botRow = row( matcher.group(6));
			
		} else
			topRow = topCol = botRow = botCol = -1; // bad range string.
	}
	
	/**
	 * Computes the number of columns this range of cells contains.
	 * @return the number of columns.
	 */
	public int cols() {
		return botCol - topCol + 1;
	}
	
	/**
	 * Computes the number of rows this range of cells contains.
	 * @return the number of rows.
	 */
	public int rows() {
		return botRow - topRow + 1;
	}
	
	/**
	 * Given a 2D array of cell values, extracts the values covered by this range of cells. Does not perform bounds check and it assumes 
	 * the range is completely contained in the input.
	 * @param values - the input values
	 * @return the cell values corresponding to this range.
	 */
	public String[][] extractRangeValuesFrom(String[][] values ) {
		var rangeValues = new String[ rows() ][ cols() ];
		for( int r = 0; r < rows(); r++ )
			for( int c = 0; c < cols(); c++)
				rangeValues[r][c] = values[r + topRow][c + topCol];
		
		return rangeValues;
	}
	
	public String toString() {
		return String.format("(%d, %d), (%d, %d)", topRow, topCol, botRow, botCol);
	}
	
	/**
	 * Converts the string column to its integer index, e.g. C -> 2
	 * @param colStr - the column name
	 * @return - the column index;
	 */
	public static int col(String colStr ) {
		int res = 0, i = colStr.length() - 1;
		for( char c : colStr.toCharArray() )
			res = res * BASE + ((c+i--) - 'A');
		return res;
	}
	
	/**
	 * Converts the string row to its integer index, which start at 0.
	 * @param rowStr - the row string.
	 * @return the row index.
	 */
	public static int row(String rowStr ) {
		return Integer.valueOf( rowStr ) - 1 ;
	}

	static final int BASE = ('Z' - 'A') + 1;
	static String CELL_REGEX = "([A-Z]+)([1-9]+[0-9]*)";	
	public static final String RANGE_REGEX = String.format("(%s):(%s)", CELL_REGEX, CELL_REGEX);
	private static final Pattern RANGE_PATTERN = Pattern.compile( RANGE_REGEX );
}
