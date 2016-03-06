package ru.snake.xored.etable.expression;

import java.util.Set;

import ru.snake.xored.etable.CellReference;
import ru.snake.xored.etable.Table;
import ru.snake.xored.etable.value.CellValue;

public interface Expression {

	/**
	 * Evaluate expression and return value. This method uses only value of referenced cell to evaluate expression. If
	 * referenced cell does not contain value then result is error. If referenced cell contains another expression then
	 * expression will be ignored.
	 * 
	 * @param table
	 * @return
	 */
	public CellValue evaluate(Table table);

	/**
	 * Return all cell references used in this expression
	 * 
	 * @return
	 */
	public Set<CellReference> getReferences();

}
