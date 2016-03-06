package ru.snake.xored.etable.expression;

import java.util.Set;

import ru.snake.xored.etable.CellReference;
import ru.snake.xored.etable.Table;
import ru.snake.xored.etable.value.CellValue;

public interface Expression {

	public CellValue evaluate(Table table);

	public Set<CellReference> getReferences();

}
