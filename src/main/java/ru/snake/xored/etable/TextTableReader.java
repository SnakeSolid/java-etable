package ru.snake.xored.etable;

import java.io.InputStream;
import java.util.Scanner;

import ru.snake.xored.etable.error.ParseCellError;
import ru.snake.xored.etable.error.ValueCellError;
import ru.snake.xored.etable.expression.Expression;
import ru.snake.xored.etable.parser.Parser;
import ru.snake.xored.etable.parser.ParserException;
import ru.snake.xored.etable.value.ErrorValue;
import ru.snake.xored.etable.value.IntegerValue;
import ru.snake.xored.etable.value.StringValue;

/**
 * Reader from plain text source for table
 * 
 * @author snake
 *
 */
public class TextTableReader implements AutoCloseable {

	private static final String PREFIX_STRING = "'";
	private static final String PREFIX_EXPRESSION = "=";

	private static final String DELIMITER = "[\t\n]";

	private final Scanner scanner;

	/**
	 * Constructs reader from input stream
	 * 
	 * @param stream
	 */
	public TextTableReader(InputStream stream) {
		this.scanner = new Scanner(stream);
		this.scanner.useDelimiter(DELIMITER);
	}

	/**
	 * Reads table from underlying stream
	 * 
	 * @param table
	 */
	public Table read() {
		int rows = this.scanner.nextInt();
		int columns = this.scanner.nextInt();

		Table table = new SimpleTable(columns, rows);

		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < columns; i++) {
				CellReference reference = new CellReference(i, j);
				Cell cell = table.getCell(reference);
				String value = this.scanner.next();

				parseCellValue(cell, value);
			}
		}

		return table;
	}

	/**
	 * Replace cell contents with given value. If value is empty cell value became empty, if value starts with `=` cell
	 * became expression, if value starts with `'` then cell value became string, otherwise cell value is error
	 * 
	 * @param cell
	 *            cell to place value
	 * @param value
	 *            string to parse
	 */
	private void parseCellValue(Cell cell, String value) {
		if (value.isEmpty()) {
			// By default all cells are empty
		} else if (value.startsWith(PREFIX_STRING)) {
			String stringValue = value.substring(PREFIX_STRING.length());

			cell.setValue(new StringValue(stringValue));
		} else if (value.startsWith(PREFIX_EXPRESSION)) {
			String expressionValue = value
					.substring(PREFIX_EXPRESSION.length());

			try {
				Expression expression = new Parser(expressionValue).parse();

				cell.setExpression(expression);
			} catch (ParserException e) {
				cell.setValue(new ErrorValue(new ParseCellError()));
			}
		} else {
			try {
				cell.setValue(new IntegerValue(Integer.parseInt(value)));
			} catch (NumberFormatException e) {
				cell.setValue(new ErrorValue(new ValueCellError()));
			}
		}
	}

	@Override
	public void close() throws Exception {
		this.scanner.close();
	}

}
