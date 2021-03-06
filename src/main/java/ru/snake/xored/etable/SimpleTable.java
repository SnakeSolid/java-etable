package ru.snake.xored.etable;

import ru.snake.xored.etable.error.CellError;
import ru.snake.xored.etable.expression.Expression;
import ru.snake.xored.etable.value.CellValue;
import ru.snake.xored.etable.value.EmptyValue;
import ru.snake.xored.etable.value.ErrorValue;
import ru.snake.xored.etable.value.IntegerValue;
import ru.snake.xored.etable.value.StringValue;

public class SimpleTable implements Table {

	private final int columns;
	private final int rows;

	private final Cell[][] cells;

	public SimpleTable(int columns, int rows) {
		if (columns < 1 || columns >= 26) {
			throw new IllegalArgumentException(
					"Column count should be in range from 1 to 24");
		}

		if (rows < 1 || rows >= 10) {
			throw new IllegalArgumentException(
					"Row count should be in range from 1 to 9");
		}

		this.columns = columns;
		this.rows = rows;
		this.cells = makeCells(columns, rows);
	}

	private Cell[][] makeCells(int columns, int rows) {
		Cell[][] cells = new Cell[columns][rows];

		for (int j = 0; j < cells.length; j++) {
			for (int i = 0; i < cells[j].length; i++) {
				cells[j][i] = new Cell();
			}
		}

		return cells;
	}

	@Override
	public int getColumns() {
		return this.columns;
	}

	public int getRows() {
		return this.rows;
	}

	@Override
	public Cell getCell(CellReference reference) {
		checkBounds(reference);

		return this.cells[reference.getColumn()][reference.getRow()];
	}

	@Override
	public boolean hasValue(CellReference reference) {
		return getCell(reference).hasValue();
	}

	@Override
	public CellValue getValue(CellReference reference) {
		return getCell(reference).getValue();
	}

	@Override
	public boolean hasExpression(CellReference reference) {
		return getCell(reference).hasExpression();
	}

	@Override
	public Expression getExpression(CellReference reference) {
		return getCell(reference).getExpression();
	}

	@Override
	public void setEmpty(CellReference reference) {
		setValue(reference, new EmptyValue());
	}

	@Override
	public void setValue(CellReference reference, int value) {
		setValue(reference, new IntegerValue(value));
	}

	@Override
	public void setValue(CellReference reference, String value) {
		setValue(reference, new StringValue(value));
	}

	@Override
	public void setError(CellReference reference, CellError error) {
		setValue(reference, new ErrorValue(error));
	}

	@Override
	public void setValue(CellReference reference, CellValue value) {
		getCell(reference).setValue(value);
	}

	@Override
	public void setExpression(CellReference reference, Expression expression) {
		getCell(reference).setExpression(expression);
	}

	@Override
	public void removeExpression(CellReference reference) {
		getCell(reference).setExpression(null);
	}

	/**
	 * Check that referenced cell inside the table
	 * 
	 * @param reference
	 */
	private void checkBounds(CellReference reference) {
		if (reference.getColumn() >= this.columns) {
			throw new IllegalArgumentException(
					"Column index should be less than " + this.columns);
		}

		if (reference.getRow() >= this.rows) {
			throw new IllegalArgumentException(
					"Row index should be less than " + this.rows);
		}
	}

}
