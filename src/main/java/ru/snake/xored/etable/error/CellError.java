package ru.snake.xored.etable.error;

/**
 * Cell error value
 * 
 * @author snake
 *
 */
public interface CellError {

	/**
	 * Error identifier to place in table cell.
	 * 
	 * @return error identifier
	 */
	public String getIdentifier();

	/**
	 * Error full description.
	 * 
	 * @return full error message
	 */
	public String getMessage();

}
