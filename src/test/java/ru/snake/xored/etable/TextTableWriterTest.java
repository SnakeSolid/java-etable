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
		CellReference a0 = new CellReference("A0");
		CellReference a1 = new CellReference("A1");
		CellReference a2 = new CellReference("A2");
		CellReference b0 = new CellReference("B0");
		CellReference b1 = new CellReference("B1");
		CellReference b2 = new CellReference("B2");
		CellReference c0 = new CellReference("C0");
		CellReference c1 = new CellReference("C1");
		CellReference c2 = new CellReference("C2");
		CellReference d0 = new CellReference("D0");
		CellReference d1 = new CellReference("D1");
		CellReference d2 = new CellReference("D2");

		table.setValue(a0, 12);
		table.setExpression(b0, new Parser("C1").parse());
		table.setValue(c0, 3);
		table.setValue(d0, "Sample");

		table.setExpression(a1, new Parser("A0+B0*C0/5").parse());
		table.setExpression(b1, new Parser("A1*B0").parse());
		table.setExpression(c1, new Parser("B2-C2").parse());
		table.setValue(d1, "Spread");

		table.setValue(a2, "Test");
		table.setExpression(b2, new Parser("4-3").parse());
		table.setValue(c2, 5);
		table.setValue(d2, "Sheet");

		SimpleEvaluator.evaluate(table);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		try (TextTableWriter writer = new TextTableWriter(stream)) {
			writer.write(table);
		}

		assertThat(stream.toString(), equalTo(
				"12\t-4\t3\tSample\n4\t-16\t-4\tSpread\nTest\t1\t5\tSheet\n"));
	}

}
