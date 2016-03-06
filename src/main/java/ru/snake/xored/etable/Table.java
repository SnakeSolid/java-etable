package ru.snake.xored.etable;

import ru.snake.xored.etable.expression.Expression;
import ru.snake.xored.etable.value.CellValue;

public interface Table {

	/**
	 * Returns number of columns in table
	 * 
	 * @return
	 */
	public int getColumns();

	/**
	 * Returns number of rows in table
	 * 
	 * @return
	 */
	public int getRows();

	/**
	 * Returns table cell by reference
	 * 
	 * @param reference
	 * @return
	 */
	public Cell getCell(CellReference reference);

	/**
	 * Return `true` if table cell has value, else returns `false`
	 * 
	 * @param reference
	 * @return
	 */
	public boolean hasValue(CellReference reference);

	/**
	 * Return cell's value or `null`
	 * 
	 * @param reference
	 * @return
	 */
	public CellValue getValue(CellReference reference);

	/**
	 * Return `true` if table cell has expression, else returns `false`
	 * 
	 * @param reference
	 * @return
	 */
	public boolean hasExpression(CellReference reference);

	/**
	 * Return cell's expression or `null`
	 * 
	 * @param reference
	 * @return
	 */
	public Expression getExpression(CellReference reference);

	/**
	 * Replace cells value to empty value
	 * 
	 * @param reference
	 */
	public void setEmpty(CellReference reference);

	/**
	 * Replace cells value to integer
	 * 
	 * @param reference
	 * @param value
	 */
	public void setValue(CellReference reference, int value);

	/**
	 * Replace cells value to string
	 * 
	 * @param reference
	 * @param value
	 */
	public void setValue(CellReference reference, String value);

	/**
	 * Set cells expression by reference
	 * 
	 * @param reference
	 * @param expression
	 */
	public void setExpression(CellReference reference, Expression expression);

	/**
	 * Remove cells expression by reference
	 * 
	 * @param reference
	 */
	public void removeExpression(CellReference reference);

}
