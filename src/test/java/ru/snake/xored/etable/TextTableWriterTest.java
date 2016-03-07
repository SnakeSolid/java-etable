package ru.snake.xored.etable;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

import ru.snake.xored.etable.parser.Parser;

public class TextTableWriterTest {

	@Test()
	public void shouldWriteTable1() throws Exception {
		Table table = new SimpleTable(4, 3);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		try (TextTableWriter writer = new TextTableWriter(stream)) {
			writer.write(table);
		}

		assertThat(stream.toString(), equalTo("\t\t\t\n\t\t\t\n\t\t\t\n"));
	}

	@Test()
	public void shouldWriteTable2() throws Exception {
		Table table = new SimpleTable(4, 3);
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
		CellReference d2 = new CellReference("D2");
		CellReference d3 = new CellReference("D3");

		table.setValue(a1, 12);
		table.setExpression(b1, new Parser("C2").parse());
		table.setValue(c1, 3);
		table.setValue(d1, "Sample");

		table.setExpression(a2, new Parser("A1+B1*C1/5").parse());
		table.setExpression(b2, new Parser("A2*B1").parse());
		table.setExpression(c2, new Parser("B3-C3").parse());
		table.setValue(d2, "Spread");

		table.setValue(a3, "Test");
		table.setExpression(b3, new Parser("4-3").parse());
		table.setValue(c3, 5);
		table.setValue(d3, "Sheet");

		SimpleEvaluator.evaluate(table);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		try (TextTableWriter writer = new TextTableWriter(stream)) {
			writer.write(table);
		}

		assertThat(stream.toString(), equalTo(
				"12\t-4\t3\tSample\n4\t-16\t-4\tSpread\nTest\t1\t5\tSheet\n"));
	}

}
