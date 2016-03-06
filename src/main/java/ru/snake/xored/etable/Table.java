package ru.snake.xored.etable;

import ru.snake.xored.etable.expression.Expression;
import ru.snake.xored.etable.value.CellValue;

public interface Table {

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

}
