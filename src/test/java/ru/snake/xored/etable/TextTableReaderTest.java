package ru.snake.xored.etable;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ru.snake.xored.etable.value.CellValue;
import ru.snake.xored.etable.value.CellValueType;

public class TextTableReaderTest {

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException() throws Exception {
		ByteArrayInputStream stream = new ByteArrayInputStream(
				"0\t0\n".getBytes());

		try (TextTableReader reader = new TextTableReader(stream)) {
			reader.read();
		}
	}

	@Test()
	public void shouldWriteTable1() throws Exception {
		Table table;
		ByteArrayInputStream stream = new ByteArrayInputStream(
				"3\t4\n\t\t\t\n\t\t\t\n\t\t\t\n".getBytes());

		try (TextTableReader reader = new TextTableReader(stream)) {
			table = reader.read();
		}

		assertSize(table, 4, 3);
		assertEmpty(table);
	}

	@Test()
	public void shouldWriteTable2() throws Exception {
		Table table;
		ByteArrayInputStream stream = new ByteArrayInputStream(
				"3\t4\n12\t=C2\t3\t'Sample\n=A1+B1*C1/5\t=A2*B1\t=B3-C3\t'Spread\n'Test\t=4-3\t5\t'Sheet\n"
						.getBytes());

		try (TextTableReader reader = new TextTableReader(stream)) {
			table = reader.read();
		}

		assertSize(table, 4, 3);
		assertValue(table, new CellReference("A1"), 12);
		assertEmpty(table, new CellReference("B1"));
		assertValue(table, new CellReference("C1"), 3);
		assertValue(table, new CellReference("D1"), "Sample");
		assertEmpty(table, new CellReference("A2"));
		assertEmpty(table, new CellReference("B2"));
		assertEmpty(table, new CellReference("C2"));
		assertValue(table, new CellReference("D2"), "Spread");
		assertValue(table, new CellReference("A3"), "Test");
		assertEmpty(table, new CellReference("B3"));
		assertValue(table, new CellReference("C3"), 5);
		assertValue(table, new CellReference("D3"), "Sheet");
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
