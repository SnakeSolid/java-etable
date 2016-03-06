package ru.snake.xored.etable.value;

import ru.snake.xored.etable.error.CellError;

public class StringValue implements CellValue {

	private final String value;

	public StringValue(String value) {
		this.value = value;
	}

	@Override
	public CellValueType getType() {
		return CellValueType.STRING;
	}

	@Override
	public int getInteger() {
		return 0;
	}

	@Override
	public String getString() {
		return this.value;
	}

	@Override
	public CellError getError() {
		return null;
	}

	@Override
	public String toString() {
		return this.value;
	}

}
