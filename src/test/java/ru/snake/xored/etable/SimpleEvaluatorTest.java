package ru.snake.xored.etable;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import ru.snake.xored.etable.error.TypeCellError;
import ru.snake.xored.etable.parser.Parser;
import ru.snake.xored.etable.parser.ParserException;
import ru.snake.xored.etable.value.CellValue;
import ru.snake.xored.etable.value.CellValueType;
import ru.snake.xored.etable.value.ErrorValue;

public class SimpleEvaluatorTest {

	@Test()
	public void shouldEvaluateExpression1() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference c2 = new CellReference("C2");

		table.setExpression(c2, new Parser("123").parse());

		SimpleEvaluator.evaluate(table);

		assertValue(table, c2, 123);
	}

	@Test()
	public void shouldEvaluateExpression2() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a1 = new CellReference("A1");
		CellReference a2 = new CellReference("A2");
		CellReference a3 = new CellReference("A3");
		CellReference b1 = new CellReference("B1");

		table.setValue(a1, 123);
		table.setValue(a2, 456);
		table.setValue(a3, 789);
		table.setExpression(b1, new Parser("a1+a2+a3").parse());

		SimpleEvaluator.evaluate(table);

		assertValue(table, b1, 123 + 456 + 789);
	}

	@Test()
	public void shouldEvaluateExpression3() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a1 = new CellReference("A1");
		CellReference a2 = new CellReference("A2");
		CellReference a3 = new CellReference("A3");
		CellReference b1 = new CellReference("B1");
		CellReference b2 = new CellReference("B2");
		CellReference b3 = new CellReference("B3");
		CellReference c1 = new CellReference("C1");

		table.setValue(a1, 123);
		table.setValue(a2, 456);
		table.setValue(a3, 789);
		table.setExpression(b1, new Parser("a1+a2").parse());
		table.setExpression(b2, new Parser("a1+a3").parse());
		table.setExpression(b3, new Parser("a2+a3").parse());
		table.setExpression(c1, new Parser("b1+b2+b3").parse());

		SimpleEvaluator.evaluate(table);

		assertValue(table, b1, (123 + 456));
		assertValue(table, b2, (123 + 789));
		assertValue(table, b3, (456 + 789));
		assertValue(table, c1, (123 + 456) + (123 + 789) + (456 + 789));
	}

	@Test()
	public void shouldEvaluateExpression4() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a1 = new CellReference("A1");
		CellReference a2 = new CellReference("A2");
		CellReference a3 = new CellReference("A3");
		CellReference b1 = new CellReference("B1");
		CellReference b2 = new CellReference("B2");
		CellReference b3 = new CellReference("B3");
		CellReference c1 = new CellReference("C1");
		CellReference c2 = new CellReference("C2");
		CellReference c3 = new CellReference("C3");
		CellReference d1 = new CellReference("D1");

		table.setValue(a1, 123);
		table.setValue(a2, 456);
		table.setValue(a3, 789);
		table.setExpression(b1, new Parser("a1+a2").parse());
		table.setExpression(b2, new Parser("a1+a3").parse());
		table.setExpression(b3, new Parser("a2+a3").parse());
		table.setExpression(c1, new Parser("b1+b2").parse());
		table.setExpression(c2, new Parser("b1+b3").parse());
		table.setExpression(c3, new Parser("b2+b3").parse());
		table.setExpression(d1, new Parser("c1+c2+c3").parse());

		SimpleEvaluator.evaluate(table);

		assertValue(table, b1, (123 + 456));
		assertValue(table, b2, (123 + 789));
		assertValue(table, b3, (456 + 789));
		assertValue(table, c1, (123 + 456) + (123 + 789));
		assertValue(table, c2, (123 + 456) + (456 + 789));
		assertValue(table, c3, (123 + 789) + (456 + 789));
		assertValue(table, d1,
				2 * (123 + 456) + 2 * (123 + 789) + 2 * (456 + 789));
	}

	@Test()
	public void shouldEvaluateExpression5() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a1 = new CellReference("A1");
		CellReference a2 = new CellReference("A2");
		CellReference a3 = new CellReference("A3");

		table.setExpression(a1, new Parser("a2").parse());
		table.setExpression(a2, new Parser("a3").parse());
		table.setExpression(a3, new Parser("a1").parse());

		SimpleEvaluator.evaluate(table);

		assertError(table, a1);
		assertError(table, a2);
		assertError(table, a3);
	}

	@Test()
	public void shouldEvaluateExpression6() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a1 = new CellReference("A1");
		CellReference a2 = new CellReference("A2");
		CellReference a3 = new CellReference("A3");
		CellReference b1 = new CellReference("B1");
		CellReference b2 = new CellReference("B2");
		CellReference b3 = new CellReference("B3");

		table.setExpression(a1, new Parser("a2").parse());
		table.setExpression(a2, new Parser("a3").parse());
		table.setExpression(a3, new Parser("a1").parse());
		table.setExpression(b1, new Parser("a1").parse());
		table.setExpression(b2, new Parser("a2").parse());
		table.setExpression(b3, new Parser("a3").parse());

		SimpleEvaluator.evaluate(table);

		assertError(table, a1);
		assertError(table, a2);
		assertError(table, a3);
		assertError(table, b1);
		assertError(table, b2);
		assertError(table, b3);
	}

	@Test()
	public void shouldEvaluateExpression7() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a1 = new CellReference("A1");
		CellReference a2 = new CellReference("A2");
		CellReference a3 = new CellReference("A3");
		CellReference b1 = new CellReference("B1");
		CellReference b2 = new CellReference("B2");
		CellReference b3 = new CellReference("B3");

		table.setExpression(a1, new Parser("b1").parse());
		table.setExpression(a2, new Parser("b2").parse());
		table.setExpression(a3, new Parser("b3").parse());
		table.setExpression(b1, new Parser("a1").parse());
		table.setExpression(b2, new Parser("a2").parse());
		table.setExpression(b3, new Parser("a3").parse());

		SimpleEvaluator.evaluate(table);

		assertError(table, a1);
		assertError(table, a2);
		assertError(table, a3);
		assertError(table, b1);
		assertError(table, b2);
		assertError(table, b3);
	}

	@Test()
	public void shouldEvaluateExpression8() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a1 = new CellReference("A1");
		CellReference a2 = new CellReference("A2");
		CellReference a3 = new CellReference("A3");
		CellReference b1 = new CellReference("B1");
		CellReference b2 = new CellReference("B2");
		CellReference b3 = new CellReference("B3");
		CellReference c1 = new CellReference("C1");
		CellReference c2 = new CellReference("C2");
		CellReference c3 = new CellReference("C3");

		table.setExpression(a1, new Parser("123").parse());
		table.setExpression(a2, new Parser("456").parse());
		table.setExpression(a3, new Parser("789").parse());
		table.setExpression(b1, new Parser("a1").parse());
		table.setExpression(b2, new Parser("a2").parse());
		table.setExpression(b3, new Parser("a3").parse());
		table.setExpression(c1, new Parser("b1+c2").parse());
		table.setExpression(c2, new Parser("b2+c3").parse());
		table.setExpression(c3, new Parser("b3+c1").parse());

		SimpleEvaluator.evaluate(table);

		assertValue(table, a1, 123);
		assertValue(table, a2, 456);
		assertValue(table, a3, 789);
		assertValue(table, b1, 123);
		assertValue(table, b2, 456);
		assertValue(table, b3, 789);
		assertError(table, c1);
		assertError(table, c2);
		assertError(table, c3);
	}

	@Test()
	public void shouldEvaluateExpression9() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a1 = new CellReference("A1");
		CellReference a2 = new CellReference("A2");
		CellReference a3 = new CellReference("A3");
		CellReference b1 = new CellReference("B1");
		CellReference b2 = new CellReference("B2");
		CellReference b3 = new CellReference("B3");

		table.setValue(a1, 123);
		table.setValue(a2, "TEST");
		table.setValue(a3, 789);
		table.setExpression(b1, new Parser("a1").parse());
		table.setExpression(b2, new Parser("a2").parse());
		table.setExpression(b3, new Parser("a3").parse());

		SimpleEvaluator.evaluate(table);

		assertValue(table, b1, 123);
		assertError(table, b2);
		assertValue(table, b3, 789);
	}

	@Test()
	public void shouldEvaluateExpression10() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a1 = new CellReference("A1");
		CellReference a2 = new CellReference("A2");
		CellReference a3 = new CellReference("A3");
		CellReference b1 = new CellReference("B1");
		CellReference b2 = new CellReference("B2");
		CellReference b3 = new CellReference("B3");

		table.setValue(a1, 123);
		table.setValue(a2, "TEST");
		table.setValue(a3, 789);
		table.setExpression(b1, new Parser("a1+b2").parse());
		table.setExpression(b2, new Parser("a2+b3").parse());
		table.setExpression(b3, new Parser("a3+b1").parse());

		SimpleEvaluator.evaluate(table);

		assertError(table, b1);
		assertError(table, b2);
		assertError(table, b3);
	}

	@Test()
	public void shouldEvaluateExpression11() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a1 = new CellReference("A1");
		CellReference a2 = new CellReference("A2");
		CellReference a3 = new CellReference("A3");
		CellReference b1 = new CellReference("B1");
		CellReference b2 = new CellReference("B2");
		CellReference b3 = new CellReference("B3");

		table.setValue(a1, 123);
		table.setValue(a2, "TEST");
		table.setValue(a3, 789);
		table.setExpression(b1, new Parser("a1+a2").parse());
		table.setExpression(b2, new Parser("a1+a3").parse());
		table.setExpression(b3, new Parser("a2+a3").parse());

		SimpleEvaluator.evaluate(table);

		assertError(table, b1);
		assertValue(table, b2, 123 + 789);
		assertError(table, b3);
	}

	@Test()
	public void shouldEvaluateExpression12() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a1 = new CellReference("A1");
		CellReference a2 = new CellReference("A2");
		CellReference a3 = new CellReference("A3");
		CellReference b1 = new CellReference("B1");
		CellReference b2 = new CellReference("B2");
		CellReference b3 = new CellReference("B3");

		table.setValue(a1, 123);
		table.setValue(a2, new ErrorValue(new TypeCellError()));
		table.setValue(a3, 789);
		table.setExpression(b1, new Parser("a1+a2").parse());
		table.setExpression(b2, new Parser("a1+a3").parse());
		table.setExpression(b3, new Parser("a2+a3").parse());

		SimpleEvaluator.evaluate(table);

		assertError(table, b1);
		assertValue(table, b2, 123 + 789);
		assertError(table, b3);
	}

	/**
	 * Check that value contains expected integer
	 * 
	 * @param table
	 * @param reference
	 * @param expected
	 */
	private void assertValue(Table table, CellReference reference,
			int expected) {
		assertThat(table.hasValue(reference), equalTo(true));

		CellValue value = table.getValue(reference);

		assertThat(value.getType(), equalTo(CellValueType.INTEGER));
		assertThat(value.getInteger(), equalTo(expected));
	}

	/**
	 * Check that value contains error
	 * 
	 * @param table
	 * @param reference
	 */
	private void assertError(Table table, CellReference reference) {
		assertThat(table.hasValue(reference), equalTo(true));

		CellValue value = table.getValue(reference);

		assertThat(value.getType(), equalTo(CellValueType.ERROR));
	}

}
