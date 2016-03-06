package ru.snake.xored.etable.parser;

public class ParserException extends Exception {

	private static final long serialVersionUID = 8496198019468007965L;

	public ParserException(String message) {
		super(message);
	}

	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParserException(Throwable cause) {
		super(cause);
	}

}
