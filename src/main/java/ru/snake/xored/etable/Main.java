package ru.snake.xored.etable;

public class Main {

	public static void main(String[] args) throws Exception {
		Table table;

		try (TextTableReader reader = new TextTableReader(System.in)) {
			table = reader.read();
		}

		SimpleEvaluator.evaluate(table);

		try (TextTableWriter writer = new TextTableWriter(System.out)) {
			writer.write(table);
		}
	}

}
