package ru.snake.xored.etable.expression;

import java.util.Set;
import java.util.TreeSet;

import ru.snake.xored.etable.CellReference;
import ru.snake.xored.etable.Table;
import ru.snake.xored.etable.error.TypeCellError;
import ru.snake.xored.etable.value.CellValue;
import ru.snake.xored.etable.value.CellValueType;
import ru.snake.xored.etable.value.ErrorValue;

public class ReferenceExpression implements Expression {

	private final CellReference reference;

	public ReferenceExpression(CellReference reference) {
		this.reference = reference;
	}

	@Override
	public CellValue evaluate(Table table) {
		CellValue value = table.getValue(this.reference);

		if (value.getType() != CellValueType.INTEGER) {
			return new ErrorValue(new TypeCellError());
		}

		return value;
	}

	@Override
	public Set<CellReference> getReferences() {
		TreeSet<CellReference> references = new TreeSet<>();

		references.add(this.reference);

		return references;
	}

	@Override
	public String toString() {
		return this.reference.toString();
	}

}
