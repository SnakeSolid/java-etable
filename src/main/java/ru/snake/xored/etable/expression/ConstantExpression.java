package ru.snake.xored.etable.expression;

import java.util.Collections;
import java.util.Set;

import ru.snake.xored.etable.CellReference;
import ru.snake.xored.etable.Table;
import ru.snake.xored.etable.value.CellValue;
import ru.snake.xored.etable.value.IntegerValue;

public class ConstantExpression implements Expression {

	private final CellValue value;

	public ConstantExpression(int value) {
		this.value = new IntegerValue(value);
	}

	@Override
	public CellValue evaluate(Table table) {
		return this.value;
	}

	@Override
	public Set<CellReference> getReferences() {
		return Collections.emptySet();
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

}
