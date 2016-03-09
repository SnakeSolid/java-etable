package ru.snake.xored.etable.tokenizer;

public class TokenizerException extends Exception {

	private static final long serialVersionUID = -6087463897219056973L;

	private final String text;
	private final int position;
	private final Object[] expected;

	public TokenizerException(String text, int position, Object... expected) {
		super();

		this.text = text;
		this.position = position;
		this.expected = expected;
	}

	public String getText() {
		return text;
	}

	public int getPosition() {
		return position;
	}

	public Object[] getExpected() {
		return expected;
	}

	@Override
	public String getMessage() {
		StringBuilder builder = new StringBuilder();
		boolean isFirst = true;

		builder.append("Incorrect character at position ");
		builder.append(this.position);
		builder.append(", expected one of");

		for (Object argument : expected) {
			if (!isFirst) {
				builder.append(',');

				isFirst = false;
			}

			builder.append(' ');
			builder.append(argument);
		}

		builder.append(" but ");

		if (this.position < this.text.length()) {
			builder.append(this.text.charAt(this.position));
		} else {
			builder.append("end of stream");
		}

		builder.append(" found.");

		return builder.toString();
	}

}
