package ru.snake.xored.etable.value;

import ru.snake.xored.etable.error.CellError;

public interface CellValue {

	/**
	 * Type of value in cell.
	 * 
	 * @return cell type
	 */
	public CellValueType getType();

	/**
	 * Cell's integer value.
	 * 
	 * @return cell value or 0 if value is not integer.
	 */
	public int getInteger();

	/**
	 * Cell's string value.
	 * 
	 * @return cell value or `null` if value is not string.
	 */
	public String getString();

	/**
	 * Cell's error.
	 * 
	 * @return cell error or `null` if cell does not have an error.
	 */
	public CellError getError();

}
