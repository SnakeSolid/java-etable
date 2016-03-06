package ru.snake.xored.etable.error;

public class CircularCellError implements CellError {

	@Override
	public String getIdentifier() {
		return "#E10002";
	}

	@Override
	public String getMessage() {
		return "Expression has circular dependencies";
	}

}
