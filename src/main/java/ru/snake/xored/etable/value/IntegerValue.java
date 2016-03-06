package ru.snake.xored.etable.value;

import ru.snake.xored.etable.error.CellError;

public class IntegerValue implements CellValue {

	private final int value;

	public IntegerValue(int value) {
		this.value = value;
	}

	@Override
	public CellValueType getType() {
		return CellValueType.INTEGER;
	}

	@Override
	public int getInteger() {
		return this.value;
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
		return String.valueOf(this.value);
	}

}
