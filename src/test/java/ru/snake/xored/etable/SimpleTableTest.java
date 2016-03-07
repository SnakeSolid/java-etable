package ru.snake.xored.etable;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Test;

import ru.snake.xored.etable.expression.ConstantExpression;
import ru.snake.xored.etable.expression.Expression;
import ru.snake.xored.etable.parser.Parser;
import ru.snake.xored.etable.parser.ParserException;
import ru.snake.xored.etable.value.CellValue;
import ru.snake.xored.etable.value.CellValueType;

public class SimpleTableTest {

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException1() throws ParserException {
		new SimpleTable(-1, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException2() throws ParserException {
		new SimpleTable(0, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException3() throws ParserException {
		new SimpleTable(26, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException4() throws ParserException {
		new SimpleTable(5, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException5() throws ParserException {
		new SimpleTable(5, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException6() throws ParserException {
		new SimpleTable(5, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException7() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference reference = new CellReference(5, 7);

		table.getCell(reference);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException8() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference reference = new CellReference(7, 5);

		table.getCell(reference);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException9() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference reference = new CellReference(7, 7);

		table.getCell(reference);
	}

	@Test()
	public void shouldCreateTable1() throws ParserException {
		Table table = new SimpleTable(1, 1);

		assertSize(table, 1, 1);
		assertEmpty(table);
	}

	@Test()
	public void shouldCreateTable2() throws ParserException {
		Table table = new SimpleTable(5, 5);

		assertSize(table, 5, 5);
		assertEmpty(table);
	}

	@Test()
	public void shouldCreateTable3() throws ParserException {
		Table table = new SimpleTable(25, 9);

		assertSize(table, 25, 9);
		assertEmpty(table);
	}

	@Test()
	public void shouldSetEmptyValue() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference c2 = new CellReference(2, 2);

		table.setValue(c2, 123);
		table.setValue(c2, "TEST");
		table.setEmpty(c2);

		assertEmpty(table, c2);
	}

	@Test()
	public void shouldSetIntegerValue() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference c2 = new CellReference(2, 2);

		table.setValue(c2, 123);

		assertValue(table, c2, 123);
	}

	@Test()
	public void shouldSetStringValue() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference c2 = new CellReference(2, 2);

		table.setValue(c2, "TEST");

		assertValue(table, c2, "TEST");
	}

	@Test()
	public void shouldSetExpression() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference c2 = new CellReference(2, 2);

		table.setExpression(c2, new ConstantExpression(123));

		assertExpression(table, c2, 123);
	}

	@Test()
	public void shouldRemoveExpression() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference c2 = new CellReference(2, 2);

		table.setExpression(c2, new ConstantExpression(123));

		assertExpression(table, c2, 123);

		table.removeExpression(c2);

		assertThat(table.hasExpression(c2), equalTo(false));
	}

	@Test()
	public void shouldEvaluateExpression1() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a2 = new CellReference(0, 2);
		CellReference b3 = new CellReference(1, 3);
		CellReference c4 = new CellReference(2, 4);

		table.setExpression(a2, new Parser("b3+c4").parse());
		table.setValue(b3, 123);
		table.setValue(c4, 456);

		assertExpression(table, a2, 123 + 456);
	}

	@Test()
	public void shouldEvaluateExpression2() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a2 = new CellReference(0, 2);
		CellReference b3 = new CellReference(1, 3);

		table.setExpression(a2, new Parser("b3+123").parse());
		table.setValue(b3, 123);

		assertExpression(table, a2, 123 + 123);
	}

	@Test()
	public void shouldEvaluateExpression3() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a2 = new CellReference(0, 2);

		table.setExpression(a2, new Parser("123").parse());

		assertExpression(table, a2, 123);
	}

	@Test()
	public void shouldEvaluateError1() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a2 = new CellReference(0, 2);
		CellReference b3 = new CellReference(1, 3);
		CellReference c4 = new CellReference(2, 4);

		table.setExpression(a2, new Parser("b3+c4").parse());
		table.setValue(b3, 123);
		table.setValue(c4, "TEST");

		assertExpressionError(table, a2);
	}

	@Test()
	public void shouldEvaluateError2() throws ParserException {
		Table table = new SimpleTable(5, 5);
		CellReference a2 = new CellReference(0, 2);
		CellReference b3 = new CellReference(1, 3);

		table.setExpression(a2, new Parser("b3+c4").parse());
		table.setValue(b3, 123);

		assertExpressionError(table, a2);
	}

	/**
	 * Check that expression is valid and can not be evaluated
	 * 
	 * @param table
	 * @param reference
	 * @param names
	 *            expected expressions references
	 */
	private void assertExpressionError(Table table, CellReference reference,
			String... names) {
		assertThat(table.hasExpression(reference), equalTo(true));

		Expression expression = table.getExpression(reference);

		assertThat(expression, notNullValue());

		Set<CellReference> references = expression.getReferences();

		for (String name : names) {
			assertThat(references, hasItem(new CellReference(name)));
		}

		CellValue value = expression.evaluate(table);

		assertThat(value.getType(), equalTo(CellValueType.ERROR));
	}

	/**
	 * Check that expression is valid and can be evaluated to expected value
	 * 
	 * @param table
	 * @param reference
	 * @param expected
	 * @param names
	 *            expected expressions references
	 */
	private void assertExpression(Table table, CellReference reference,
			int expected, String... names) {
		assertThat(table.hasExpression(reference), equalTo(true));

		Expression expression = table.getExpression(reference);

		assertThat(expression, notNullValue());

		Set<CellReference> references = expression.getReferences();

		for (String name : names) {
			assertThat(references, hasItem(new CellReference(name)));
		}

		CellValue value = expression.evaluate(table);

		assertThat(value.getType(), equalTo(CellValueType.INTEGER));
		assertThat(value.getInteger(), equalTo(expected));
	}

	/**
	 * Check that value is empty
	 * 
	 * @param table
	 * @param reference
	 */
	private void assertEmpty(Table table, CellReference reference) {
		assertThat(table.hasValue(reference), equalTo(true));

		CellValue value = table.getValue(reference);

		assertThat(value.getType(), equalTo(CellValueType.EMPTY));
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
	 * Check that value contains expected string
	 * 
	 * @param table
	 * @param reference
	 * @param expected
	 */
	private void assertValue(Table table, CellReference reference,
			String expected) {
		assertThat(table.hasValue(reference), equalTo(true));

		CellValue value = table.getValue(reference);

		assertThat(value.getType(), equalTo(CellValueType.STRING));
		assertThat(value.getString(), equalTo(expected));
	}

	/**
	 * Check that all table cells have empty values
	 * 
	 * @param table
	 */
	private void assertEmpty(Table table) {
		for (int j = 0; j < table.getColumns(); j++) {
			for (int i = 0; i < table.getRows(); i++) {
				Cell cell = table.getCell(new CellReference(j, i));

				assertThat(cell, notNullValue());
				assertThat(cell.hasValue(), equalTo(true));
				assertThat(cell.hasExpression(), equalTo(false));
				assertThat(cell.getValue().getType(),
						equalTo(CellValueType.EMPTY));
				assertThat(cell.getExpression(), nullValue());
			}
		}
	}

	/**
	 * Checks that table has expected size
	 * 
	 * @param table
	 * @param columns
	 *            expected column count
	 * @param rows
	 *            expected row count
	 */
	private void assertSize(Table table, int columns, int rows) {
		assertThat(table.getColumns(), equalTo(columns));
		assertThat(table.getRows(), equalTo(rows));
	}

}
