package ru.snake.xored.etable.value;

import ru.snake.xored.etable.error.CellError;

public class ErrorValue implements CellValue {

	private final CellError error;

	public ErrorValue(CellError error) {
		this.error = error;
	}

	@Override
	public CellValueType getType() {
		return CellValueType.ERROR;
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
		return this.error;
	}

	@Override
	public String toString() {
		return this.error.getIdentifier();
	}

}
