package ru.snake.xored.etable.error;

public class ValueCellError implements CellError {

	@Override
	public String getIdentifier() {
		return "#E10004";
	}

	@Override
	public String getMessage() {
		return "Invalid cell value";
	}

}
