package ru.snake.xored.etable;

import java.io.InputStream;
import java.util.Scanner;

import ru.snake.xored.etable.error.ParseCellError;
import ru.snake.xored.etable.error.ValueCellError;
import ru.snake.xored.etable.expression.Expression;
import ru.snake.xored.etable.parser.Parser;
import ru.snake.xored.etable.parser.ParserException;

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
				String value = this.scanner.next();

				if (value.isEmpty()) {
					// By default all cells are empty
				} else if (value.startsWith(PREFIX_STRING)) {
					String stringValue = value
							.substring(PREFIX_STRING.length());

					table.setValue(reference, stringValue);
				} else if (value.startsWith(PREFIX_EXPRESSION)) {
					String expressionValue = value
							.substring(PREFIX_EXPRESSION.length());

					try {
						Expression expression = new Parser(expressionValue)
								.parse();

						table.setExpression(reference, expression);
					} catch (ParserException e) {
						table.setError(reference, new ParseCellError());
					}
				} else {
					try {
						table.setValue(reference, Integer.parseInt(value));
					} catch (NumberFormatException e) {
						table.setError(reference, new ValueCellError());
					}
				}
			}
		}

		return table;
	}

	@Override
	public void close() throws Exception {
		this.scanner.close();
	}

}
