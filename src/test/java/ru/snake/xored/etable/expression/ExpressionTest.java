package ru.snake.xored.etable.expression;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Test;
import org.mockito.Mockito;

import ru.snake.xored.etable.CellReference;
import ru.snake.xored.etable.Table;
import ru.snake.xored.etable.parser.Parser;
import ru.snake.xored.etable.parser.ParserException;
import ru.snake.xored.etable.value.CellValue;
import ru.snake.xored.etable.value.CellValueType;
import ru.snake.xored.etable.value.IntegerValue;
import ru.snake.xored.etable.value.StringValue;

public class ExpressionTest {

	@Test
	public void shouldReturnNumber() throws ParserException {
		Parser parser = new Parser("123");
		Expression expression = parser.parse();
		Table table = mockTable();

		assertValue(expression.evaluate(table), 123);
	}

	@Test
	public void shouldReturnNumberAddNumber() throws ParserException {
		Parser parser = new Parser("123+456");
		Expression expression = parser.parse();
		Table table = mockTable();

		assertValue(expression.evaluate(table), 123 + 456);
	}

	@Test
	public void shouldReturnNumberSubNumber() throws ParserException {
		Parser parser = new Parser("123-456");
		Expression expression = parser.parse();
		Table table = mockTable();

		assertValue(expression.evaluate(table), 123 - 456);
	}

	@Test
	public void shouldReturnNumberMulNumber() throws ParserException {
		Parser parser = new Parser("123*456");
		Expression expression = parser.parse();
		Table table = mockTable();

		assertValue(expression.evaluate(table), 123 * 456);
	}

	@Test
	public void shouldReturnNumberDivNumber() throws ParserException {
		Parser parser = new Parser("123/456");
		Expression expression = parser.parse();
		Table table = mockTable();

		assertValue(expression.evaluate(table), 123 / 456);
	}

	@Test
	public void shouldReturnNumberAddReference() throws ParserException {
		Parser parser = new Parser("123+A2");
		Expression expression = parser.parse();
		Table table = mockTable();
		mockTableCell(table, "A2", 123);

		assertReferences(expression.getReferences(), "A2");
		assertValue(expression.evaluate(table), 123 + 123);
	}

	@Test
	public void shouldReturnReferenceAddReference() throws ParserException {
		Parser parser = new Parser("A2+B3");
		Expression expression = parser.parse();
		Table table = mockTable();
		mockTableCell(table, "A2", 123);
		mockTableCell(table, "B3", 456);

		assertReferences(expression.getReferences(), "A2", "B3");
		assertValue(expression.evaluate(table), 123 + 456);
	}

	@Test
	public void shouldReturnError1() throws ParserException {
		Parser parser = new Parser("A2");
		Expression expression = parser.parse();
		Table table = mockTable();
		mockTableCell(table, "A2", "TEST");

		assertReferences(expression.getReferences(), "A2");
		assertError(expression.evaluate(table));
	}

	@Test
	public void shouldReturnError2() throws ParserException {
		Parser parser = new Parser("123+A2");
		Expression expression = parser.parse();
		Table table = mockTable();
		mockTableCell(table, "A2", "TEST");

		assertReferences(expression.getReferences(), "A2");
		assertError(expression.evaluate(table));
	}

	@Test
	public void shouldReturnError3() throws ParserException {
		Parser parser = new Parser("A2+123");
		Expression expression = parser.parse();
		Table table = mockTable();
		mockTableCell(table, "A2", "TEST");

		assertReferences(expression.getReferences(), "A2");
		assertError(expression.evaluate(table));
	}

	@Test
	public void shouldReturnError4() throws ParserException {
		Parser parser = new Parser("A2+B3");
		Expression expression = parser.parse();
		Table table = mockTable();
		mockTableCell(table, "A2", "TEST");
		mockTableCell(table, "B3", "TEST");

		assertReferences(expression.getReferences(), "A2", "B3");
		assertError(expression.evaluate(table));
	}

	@Test
	public void shouldReturnValue1() throws ParserException {
		Parser parser = new Parser("123*A2+B3");
		Expression expression = parser.parse();
		Table table = mockTable();
		mockTableCell(table, "A2", 123);
		mockTableCell(table, "B3", 456);

		assertReferences(expression.getReferences(), "A2", "B3");
		assertValue(expression.evaluate(table), (123 * 123) + 456);
	}

	@Test
	public void shouldReturnValue2() throws ParserException {
		Parser parser = new Parser("A2/123+B3");
		Expression expression = parser.parse();
		Table table = mockTable();
		mockTableCell(table, "A2", 123);
		mockTableCell(table, "B3", 456);

		assertReferences(expression.getReferences(), "A2", "B3");
		assertValue(expression.evaluate(table), (123 / 123) + 456);
	}

	@Test
	public void shouldReturnValue3() throws ParserException {
		Parser parser = new Parser("A2-123*B3+C4");
		Expression expression = parser.parse();
		Table table = mockTable();
		mockTableCell(table, "A2", 123);
		mockTableCell(table, "B3", 456);
		mockTableCell(table, "C4", 789);

		assertReferences(expression.getReferences(), "A2", "B3", "C4");
		assertValue(expression.evaluate(table), ((123 - 123) * 456) + 789);
	}

	@Test
	public void shouldReturnValue4() throws ParserException {
		Parser parser = new Parser("123*A2+B3");
		Expression expression = parser.parse();
		Table table = mockTable();
		mockTableCell(table, "A2", 123);
		mockTableCell(table, "B3", 456);

		assertReferences(expression.getReferences(), "A2", "B3");
		assertValue(expression.evaluate(table), (123 * 123) + 456);
	}

	/**
	 * Adds integer value to given cell in the mocked `Table`
	 * 
	 * @param table
	 *            mocked table
	 * @param reference
	 *            reference to cell
	 * @param value
	 */
	private void mockTableCell(Table table, String reference, int value) {
		CellReference cellReference = new CellReference(reference);
		CellValue cellValue = new IntegerValue(value);

		Mockito.when(table.getValue(cellReference)).thenReturn(cellValue);
	}

	/**
	 * Adds string value to given cell in the mocked `Table`
	 * 
	 * @param table
	 *            mocked table
	 * @param reference
	 *            reference to cell
	 * @param value
	 */
	private void mockTableCell(Table table, String reference, String value) {
		CellReference cellReference = new CellReference(reference);
		CellValue cellValue = new StringValue(value);

		Mockito.when(table.getValue(cellReference)).thenReturn(cellValue);
	}

	/**
	 * Creates mock for `Table` interface
	 * 
	 * @return
	 */
	private Table mockTable() {
		Table table = Mockito.mock(Table.class);

		return table;
	}

	/**
	 * Check that value contains expected number
	 * 
	 * @param cellValue
	 * @param value
	 */
	private void assertValue(CellValue cellValue, int value) {
		assertThat(cellValue.getType(), equalTo(CellValueType.INTEGER));
		assertThat(cellValue.getInteger(), equalTo(value));
	}

	/**
	 * Check that value contains error
	 * 
	 * @param cellValue
	 * @param value
	 */
	private void assertError(CellValue cellValue) {
		assertThat(cellValue.getType(), equalTo(CellValueType.ERROR));
		assertThat(cellValue.getError(), notNullValue());
	}

	/**
	 * Check that set contains all references
	 * 
	 * @param references
	 * @param names
	 */
	private void assertReferences(Set<CellReference> references,
			String... names) {
		for (String name : names) {
			assertThat(references, hasItem(new CellReference(name)));
		}
	}

}
