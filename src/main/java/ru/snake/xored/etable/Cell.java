package ru.snake.xored.etable;

import ru.snake.xored.etable.expression.Expression;
import ru.snake.xored.etable.value.CellValue;
import ru.snake.xored.etable.value.EmptyValue;

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

	public boolean hasValue() {
		return this.value != null;
	}

	public boolean hasExpression() {
		return this.expression != null;
	}

	public CellValue getValue() {
		return this.value;
	};

	public Expression getExpression() {
		return expression;
	}

	public void setValue(CellValue value) {
		this.value = value;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

}
