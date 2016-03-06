package ru.snake.xored.etable;

import ru.snake.xored.etable.expression.Expression;
import ru.snake.xored.etable.value.CellValue;
import ru.snake.xored.etable.value.EmptyValue;

/**
 * Table cell class. Contains both value and expression.
 * 
 * @author snake
 *
 */
public class Cell {

	private CellValue value;
	private Expression expression;

	public Cell() {
		this.value = new EmptyValue();
		this.expression = null;
	}

	public Cell(CellValue value) {
		this.value = value;
		this.expression = null;
	}

	public Cell(Expression expression) {
		this.value = null;
		this.expression = expression;
	}

	/**
	 * Returns {@code true} if cell contains value
	 * 
	 * @return
	 */
	public boolean hasValue() {
		return this.value != null;
	}

	/**
	 * Returns {@code true} if cell contains expression
	 * 
	 * @return
	 */
	public boolean hasExpression() {
		return this.expression != null;
	}

	/**
	 * Returns {@code true} cell value
	 * 
	 * @return
	 */
	public CellValue getValue() {
		return this.value;
	}

	/**
	 * Returns {@code true} cell expression
	 * 
	 * @return
	 */
	public Expression getExpression() {
		return expression;
	}

	/**
	 * Sets new cell value. Removes value if value is {@code null}
	 * 
	 * @param value
	 */
	public void setValue(CellValue value) {
		this.value = value;
	}

	/**
	 * Sets new cell expression. Removes expression if expression is {@code null}
	 * 
	 * @param expression
	 */
	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		if (hasExpression()) {
			return "=" + this.expression.toString();
		} else if (hasValue()) {
			switch (this.value.getType()) {
			case EMPTY:
				return this.value.toString();
			case INTEGER:
				return this.value.toString();
			case STRING:
				return "'" + this.value.toString();
			case ERROR:
				return this.value.toString();
			}
		}

		return "<NONE>";
	}

}
