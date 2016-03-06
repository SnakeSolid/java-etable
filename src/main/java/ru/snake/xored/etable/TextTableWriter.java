package ru.snake.xored.etable;

import java.io.OutputStream;
import java.io.PrintStream;

import ru.snake.xored.etable.value.CellValue;

/**
 * Writer to plain text stream for table
 * 
 * @author snake
 *
 */
public class TextTableWriter implements AutoCloseable {

	private final PrintStream stream;

	/**
	 * Constructs writer from output stream
	 * 
	 * @param stream
	 */
	public TextTableWriter(OutputStream stream) {
		this.stream = new PrintStream(stream);
	}

	/**
	 * Constructs writer from print stream
	 * 
	 * @param stream
	 */
	public TextTableWriter(PrintStream stream) {
		this.stream = stream;
	}

	/**
	 * Writes table to underlying stream
	 * 
	 * @param table
	 */
	public void write(Table table) {
		for (int j = 0; j < table.getRows(); j++) {
			boolean isFirst = true;

			for (int i = 0; i < table.getColumns(); i++) {
				if (isFirst) {
					isFirst = false;
				} else {
					this.stream.print('\t');
				}

				CellReference reference = new CellReference(i, j);

				if (table.hasValue(reference)) {
					CellValue value = table.getValue(reference);

					switch (value.getType()) {
					case INTEGER:
						this.stream.print(value.getInteger());
						break;

					case STRING:
						this.stream.print(value.getString());
						break;

					case ERROR:
						this.stream.print(value.getError().getIdentifier());
						break;

					default:
						break;
					}
				}
			}

			this.stream.println();
		}

		this.stream.flush();
	}

	@Override
	public void close() throws Exception {
		this.stream.close();
	}

}
