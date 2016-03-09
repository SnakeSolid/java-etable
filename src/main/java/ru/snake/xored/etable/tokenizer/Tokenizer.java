package ru.snake.xored.etable.tokenizer;

public class Tokenizer {

	private static final String TOKEN_EOS = "";

	private static final char TOKEN_ADD = '+';
	private static final char TOKEN_SUB = '-';
	private static final char TOKEN_MUL = '*';
	private static final char TOKEN_DIV = '/';

	private final String text;

	private TokenType tokenType;
	private String tokenValue;
	private int position;

	public Tokenizer(String text) {
		this.text = text;
		this.tokenType = TokenType.END_OF_STREAM;
		this.tokenValue = null;
		this.position = 0;
	}

	public boolean nextToken() throws TokenizerException {
		skipWhiteSpace();

		if (this.position == this.text.length()) {
			this.tokenType = TokenType.END_OF_STREAM;
			this.tokenValue = null;

			return false;
		} else if (isDigit()) {
			int startPosition = this.position;

			while (this.position < this.text.length() && isDigit()) {
				this.position += 1;
			}

			this.tokenType = TokenType.NUMBER;
			this.tokenValue = this.text.substring(startPosition, this.position);

			return true;
		} else if (isAlphabet()) {
			int startPosition = this.position;

			if (this.position < this.text.length() && isAlphabet()) {
				this.position += 1;
			} else {
				throw new TokenizerException(this.text, this.position,
						"[A-Za-z]");
			}

			if (this.position < this.text.length() && isDigit()) {
				this.position += 1;
			} else {
				throw new TokenizerException(this.text, this.position, "[0-9]");
			}

			this.tokenType = TokenType.CELL_REFERENCE;
			this.tokenValue = this.text.substring(startPosition, this.position)
					.toUpperCase();

			return true;
		} else if (isOperation()) {
			char current = currentChar();

			if (current == TOKEN_ADD) {
				this.tokenType = TokenType.OPERATION_ADD;
				this.tokenValue = String.valueOf(TOKEN_ADD);
			} else if (current == TOKEN_SUB) {
				this.tokenType = TokenType.OPERATION_SUB;
				this.tokenValue = String.valueOf(TOKEN_SUB);
			} else if (current == TOKEN_MUL) {
				this.tokenType = TokenType.OPERATION_MUL;
				this.tokenValue = String.valueOf(TOKEN_MUL);
			} else if (current == TOKEN_DIV) {
				this.tokenType = TokenType.OPERATION_DIV;
				this.tokenValue = String.valueOf(TOKEN_DIV);
			} else {
				throw new TokenizerException(this.text, this.position,
						TOKEN_ADD, TOKEN_SUB, TOKEN_MUL, TOKEN_DIV);
			}

			this.position += 1;

			return true;
		} else if (this.position == this.text.length()) {
			this.tokenType = TokenType.END_OF_STREAM;
			this.tokenValue = TOKEN_EOS;

			return true;
		} else {
			throw new TokenizerException(this.text, this.position, "[A-Za-z]",
					"[0-9]", TOKEN_ADD, TOKEN_SUB, TOKEN_MUL, TOKEN_DIV);
		}
	}

	private boolean isOperation() {
		char current = currentChar();

		return current == TOKEN_ADD || current == TOKEN_SUB
				|| current == TOKEN_MUL || current == TOKEN_DIV;
	}

	private boolean isAlphabet() {
		int charType = Character.getType(currentChar());

		return charType == Character.UPPERCASE_LETTER
				|| charType == Character.LOWERCASE_LETTER;
	}

	private boolean isDigit() {
		return Character.isDigit(currentChar());
	}

	private void skipWhiteSpace() {
		while (this.position < this.text.length() && isWhiteSpace()) {
			this.position += 1;
		}
	}

	private boolean isWhiteSpace() {
		return Character.isWhitespace(currentChar());
	}

	private char currentChar() {
		return this.text.charAt(this.position);
	}

	public String getTokenValue() {
		return this.tokenValue;
	}

	public TokenType getTokenType() {
		return this.tokenType;
	}

}
