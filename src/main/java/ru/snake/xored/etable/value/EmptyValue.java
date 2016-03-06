package ru.snake.xored.etable.value;

import ru.snake.xored.etable.error.CellError;

public class EmptyValue implements CellValue {

	@Override
	public CellValueType getType() {
		return CellValueType.EMPTY;
	}

	@Override
	public int getInteger() {
		return 0;
	}

	@Override
	public String getString() {
		return null;
	}

	@Override
	public CellError getError() {
		return null;
	}

	@Override
	public String toString() {
		return "<EMPTY>";
	}

}
