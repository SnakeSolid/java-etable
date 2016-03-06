package ru.snake.xored.etable.error;

public class TypeCellError implements CellError {

	@Override
	public String getIdentifier() {
		return "#E10001";
	}

	@Override
	public String getMessage() {
		return "Operand has wrong type";
	}

}
