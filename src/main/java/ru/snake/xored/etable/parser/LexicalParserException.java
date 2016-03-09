package ru.snake.xored.etable.parser;

import ru.snake.xored.etable.tokenizer.TokenType;

public class LexicalParserException extends ParserException {

	private static final long serialVersionUID = -2894743581441565730L;

	private final TokenType[] expected;
	private final String tokenValue;

	public LexicalParserException(String tokenValue, TokenType... expected) {
		super();

		this.tokenValue = tokenValue;
		this.expected = expected;
	}

	public LexicalParserException(Throwable cause, String tokenValue,
			TokenType... expected) {
		super(cause);

		this.tokenValue = tokenValue;
		this.expected = expected;
	}

	public TokenType[] getExpected() {
		return expected;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	@Override
	public String getMessage() {
		StringBuilder builder = new StringBuilder();
		boolean isFirst = true;

		builder.append("Incorrect token, expected one of");

		for (Object argument : this.expected) {
			if (!isFirst) {
				builder.append(',');

				isFirst = false;
			}

			builder.append(' ');
			builder.append(argument);
		}

		builder.append(" but ");
		builder.append(this.tokenValue);
		builder.append(" found.");

		return builder.toString();
	}

}
