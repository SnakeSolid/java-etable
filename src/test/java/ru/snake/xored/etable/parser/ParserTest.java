package ru.snake.xored.etable.parser;

import org.junit.Test;

public class ParserTest {

	@Test(expected = ParserException.class)
	public void shouldThrowException1() throws ParserException {
		Parser parser = new Parser("");

		parser.parse();
	}

	@Test(expected = ParserException.class)
	public void shouldThrowException2() throws ParserException {
		Parser parser = new Parser("+");

		parser.parse();
	}

	@Test(expected = ParserException.class)
	public void shouldThrowException3() throws ParserException {
		Parser parser = new Parser("A2+");

		parser.parse();
	}

	@Test(expected = ParserException.class)
	public void shouldThrowException4() throws ParserException {
		Parser parser = new Parser("123+");

		parser.parse();
	}

	@Test
	public void shouldParseNumber() throws ParserException {
		Parser parser = new Parser("123");

		parser.parse();
	}

	@Test
	public void shouldParseNumberAddNumber() throws ParserException {
		Parser parser = new Parser("123+456");

		parser.parse();
	}

	@Test
	public void shouldParseNumberSubNumber() throws ParserException {
		Parser parser = new Parser("123-456");

		parser.parse();
	}

	@Test
	public void shouldParseNumberMulNumber() throws ParserException {
		Parser parser = new Parser("123*456");

		parser.parse();
	}

	@Test
	public void shouldParseNumberDivNumber() throws ParserException {
		Parser parser = new Parser("123/456");

		parser.parse();
	}

	@Test
	public void shouldParseNumberAddReference() throws ParserException {
		Parser parser = new Parser("123+A2");

		parser.parse();
	}

	@Test
	public void shouldParseReference() throws ParserException {
		Parser parser = new Parser("A2");

		parser.parse();
	}

	@Test
	public void shouldParseReferenceAddNumber() throws ParserException {
		Parser parser = new Parser("A2+123");

		parser.parse();
	}

	@Test
	public void shouldParseReferenceAddReference() throws ParserException {
		Parser parser = new Parser("A2+B3");

		parser.parse();
	}

}
