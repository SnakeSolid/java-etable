package ru.snake.xored.etable;

/**
 * Reference to table cell
 * 
 * @author snake
 *
 */
public class CellReference implements Comparable<CellReference> {

	private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int MAX_COLUMN = LETTERS.length() - 1;
	private static final int MAX_ROW = 9;

	private final int column;
	private final int row;

	public CellReference(String reference) {
		if (reference.length() != 2) {
			throw new IllegalArgumentException(
					"Reference must be letter and number pair");
		}

		int column = LETTERS.indexOf(reference.charAt(0));
		int row = Integer.parseInt(reference.substring(1));

		if (column < 0 || column >= LETTERS.length()) {
			throw new IllegalArgumentException(
					"Column index should be in range from 0 to " + MAX_COLUMN);
		}

		if (row < 0 || row >= 10) {
			throw new IllegalArgumentException(
					"Row index should be in range from 0 to " + MAX_ROW);
		}

		this.column = column;
		this.row = row;
	}

	public CellReference(int column, int row) {
		if (column < 0 || column >= LETTERS.length()) {
			throw new IllegalArgumentException(
					"Column index should be in range from 0 to " + MAX_COLUMN);
		}

		if (row < 0 || row >= 10) {
			throw new IllegalArgumentException(
					"Row index should be in range from 0 to " + MAX_ROW);
		}

		this.column = column;
		this.row = row;
	}

	/**
	 * Return column
	 * 
	 * @return
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Returns row
	 * 
	 * @return
	 */
	public int getRow() {
		return row;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + column;
		result = prime * result + row;

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof CellReference)) {
			return false;
		}

		CellReference other = (CellReference) obj;

		if (column != other.column) {
			return false;
		}

		if (row != other.row) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(2);

		builder.append(LETTERS.charAt(this.column));
		builder.append(this.row);

		return builder.toString();
	}

	@Override
	public int compareTo(CellReference o) {
		int result = Integer.compare(this.row, o.row);

		if (result == 0) {
			result = Integer.compare(this.column, o.column);
		}

		return result;
	}

}
