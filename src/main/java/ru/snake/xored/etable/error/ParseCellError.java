package ru.snake.xored.etable.error;

public class ParseCellError implements CellError {

	@Override
	public String getIdentifier() {
		return "#E10003";
	}

	@Override
	public String getMessage() {
		return "Invalid expression";
	}

}
