package ru.snake.xored.etable.expression;

import java.util.Set;
import java.util.TreeSet;

import ru.snake.xored.etable.CellReference;
import ru.snake.xored.etable.Table;
import ru.snake.xored.etable.error.TypeCellError;
import ru.snake.xored.etable.value.CellValue;
import ru.snake.xored.etable.value.CellValueType;
import ru.snake.xored.etable.value.ErrorValue;
import ru.snake.xored.etable.value.IntegerValue;

public class AddExpression implements Expression {

	private final Expression left;
	private final Expression right;

	public AddExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public CellValue evaluate(Table table) {
		CellValue left = this.left.evaluate(table);
		CellValue right = this.right.evaluate(table);

		if (left.getType() != CellValueType.INTEGER) {
			return new ErrorValue(new TypeCellError());
		} else if ((right.getType() != CellValueType.INTEGER)) {
			return new ErrorValue(new TypeCellError());
		}

		return new IntegerValue(left.getInteger() + right.getInteger());
	}

	@Override
	public Set<CellReference> getReferences() {
		TreeSet<CellReference> references = new TreeSet<>();

		references.addAll(this.left.getReferences());
		references.addAll(this.right.getReferences());

		return references;
	}

	@Override
	public String toString() {
		return this.left.toString() + " + " + this.right.toString();
	}

}
