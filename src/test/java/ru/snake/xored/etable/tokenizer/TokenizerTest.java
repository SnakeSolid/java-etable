package ru.snake.xored.etable.tokenizer;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TokenizerTest {

	@Test(expected = TokenizerException.class)
	public void shouldThrowException1() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("#");

		tokenizer.nextToken();
	}

	@Test(expected = TokenizerException.class)
	public void shouldThrowException2() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("A");

		tokenizer.nextToken();
	}

	@Test(expected = TokenizerException.class)
	public void shouldThrowException3() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("AA");

		tokenizer.nextToken();
	}

	@Test
	public void shouldParseEmptyString() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("");

		assertEos(tokenizer);
	}

	@Test
	public void shouldParseWhitespace() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("  \t\t  ");

		assertEos(tokenizer);
	}

	@Test
	public void shouldParseNumber() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("123");

		assertNumber(tokenizer, "123");
		assertEos(tokenizer);
	}

	@Test
	public void shouldParseAdd() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("+");

		assertOperation(tokenizer, TokenType.OPERATION_ADD);
		assertEos(tokenizer);
	}

	@Test
	public void shouldParseSub() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("-");

		assertOperation(tokenizer, TokenType.OPERATION_SUB);
		assertEos(tokenizer);
	}

	@Test
	public void shouldParseMul() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("*");

		assertOperation(tokenizer, TokenType.OPERATION_MUL);
		assertEos(tokenizer);
	}

	@Test
	public void shouldParseDiv() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("/");

		assertOperation(tokenizer, TokenType.OPERATION_DIV);
		assertEos(tokenizer);
	}

	@Test
	public void shouldParseUpperReference() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("A1");

		assertReference(tokenizer, "A1");
		assertEos(tokenizer);
	}

	@Test
	public void shouldParseLowerReference() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("a1");

		assertReference(tokenizer, "A1");
		assertEos(tokenizer);
	}

	@Test
	public void shouldParseWhitespaceNumber() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("  123  ");

		assertNumber(tokenizer, "123");
		assertEos(tokenizer);
	}

	@Test
	public void shouldParseWhitespaceOperations() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer(" + - * / ");

		assertOperation(tokenizer, TokenType.OPERATION_ADD);
		assertOperation(tokenizer, TokenType.OPERATION_SUB);
		assertOperation(tokenizer, TokenType.OPERATION_MUL);
		assertOperation(tokenizer, TokenType.OPERATION_DIV);
		assertEos(tokenizer);
	}

	@Test
	public void shouldParseWhitespaceReference() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("   A1   ");

		assertReference(tokenizer, "A1");
		assertEos(tokenizer);
	}

	@Test
	public void shouldParseExpression1() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("A2 + B3");

		assertReference(tokenizer, "A2");
		assertOperation(tokenizer, TokenType.OPERATION_ADD);
		assertReference(tokenizer, "B3");
		assertEos(tokenizer);
	}

	@Test
	public void shouldParseExpression2() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("A2 B3 C4");

		assertReference(tokenizer, "A2");
		assertReference(tokenizer, "B3");
		assertReference(tokenizer, "C4");
		assertEos(tokenizer);
	}

	@Test
	public void shouldParseExpression3() throws TokenizerException {
		Tokenizer tokenizer = new Tokenizer("123 * A2 - B3");

		assertNumber(tokenizer, "123");
		assertOperation(tokenizer, TokenType.OPERATION_MUL);
		assertReference(tokenizer, "A2");
		assertOperation(tokenizer, TokenType.OPERATION_SUB);
		assertReference(tokenizer, "B3");
		assertEos(tokenizer);
	}

	/**
	 * Check that next token is end of stream
	 * 
	 * @param tokenizer
	 * @throws TokenizerException
	 */
	private void assertEos(Tokenizer tokenizer) throws TokenizerException {
		assertThat(tokenizer.nextToken(), equalTo(false));
		assertThat(tokenizer.getTokenType(), equalTo(TokenType.END_OF_STREAM));
		assertThat(tokenizer.getTokenValue(), nullValue());
	}

	/**
	 * Check that next token is a number
	 * 
	 * @param tokenizer
	 * @param value
	 *            expected number
	 * @throws TokenizerException
	 */
	private void assertNumber(Tokenizer tokenizer, String value)
			throws TokenizerException {
		assertThat(tokenizer.nextToken(), equalTo(true));
		assertThat(tokenizer.getTokenType(), equalTo(TokenType.NUMBER));
		assertThat(tokenizer.getTokenValue(), equalTo(value));
	}

	/**
	 * Check that next token is cell reference
	 * 
	 * @param tokenizer
	 * @param value
	 *            expected reference
	 * @throws TokenizerException
	 */
	private void assertReference(Tokenizer tokenizer, String value)
			throws TokenizerException {
		assertThat(tokenizer.nextToken(), equalTo(true));
		assertThat(tokenizer.getTokenType(), equalTo(TokenType.CELL_REFERENCE));
		assertThat(tokenizer.getTokenValue(), equalTo(value));
	}

	/**
	 * Check that next token is operation
	 * 
	 * @param tokenizer
	 * @param operation
	 *            expected operation
	 * @throws TokenizerException
	 */
	private void assertOperation(Tokenizer tokenizer, TokenType operation)
			throws TokenizerException {
		switch (operation) {
		case OPERATION_ADD:
			assertThat(tokenizer.nextToken(), equalTo(true));
			assertThat(tokenizer.getTokenType(),
					equalTo(TokenType.OPERATION_ADD));
			assertThat(tokenizer.getTokenValue(), equalTo("+"));
			break;

		case OPERATION_SUB:
			assertThat(tokenizer.nextToken(), equalTo(true));
			assertThat(tokenizer.getTokenType(),
					equalTo(TokenType.OPERATION_SUB));
			assertThat(tokenizer.getTokenValue(), equalTo("-"));
			break;

		case OPERATION_MUL:
			assertThat(tokenizer.nextToken(), equalTo(true));
			assertThat(tokenizer.getTokenType(),
					equalTo(TokenType.OPERATION_MUL));
			assertThat(tokenizer.getTokenValue(), equalTo("*"));
			break;

		case OPERATION_DIV:
			assertThat(tokenizer.nextToken(), equalTo(true));
			assertThat(tokenizer.getTokenType(),
					equalTo(TokenType.OPERATION_DIV));
			assertThat(tokenizer.getTokenValue(), equalTo("/"));
			break;

		default:
			break;
		}
	}

}
