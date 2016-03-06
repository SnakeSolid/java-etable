package ru.snake.xored.etable;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import ru.snake.xored.etable.error.CircularCellError;
import ru.snake.xored.etable.expression.Expression;
import ru.snake.xored.etable.value.CellValue;
import ru.snake.xored.etable.value.CellValueType;

public class SimpleEvaluator {

	private final Table table;

	public SimpleEvaluator(Table table) {
		this.table = table;
	}

	/**
	 * Evaluate whole table. Expressions evaluated hierarchically. Method changes the table. Method is synonym for
	 * {@code
	 * new SimpleEvaluator(table).evaluate()
	 * }
	 * 
	 * @param table
	 */
	public static void evaluate(Table table) {
		new SimpleEvaluator(table).evaluate();
	}

	/**
	 * Evaluates all expression in table. If cell expression depends on other expression it will be evaluated too. If
	 * expressions have circular dependencies then error will be set for all cells.
	 * 
	 * @param reference
	 */
	public void evaluate() {
		Set<CellReference> expressionCells = findExpressions();

		prepareCells(expressionCells);

		for (CellReference reference : expressionCells) {
			evaluateExpression(reference);
		}
	}

	/**
	 * Evaluates expression in referenced cell. If cell expression depends on other expression it will be evaluated too.
	 * If expressions have circular dependencies then error will be set for all cells.
	 * 
	 * @param reference
	 */
	private void evaluateExpression(CellReference reference) {
		CellValue currentValue = table.getValue(reference);

		if (currentValue.getType() != CellValueType.EMPTY) {
			return;
		}

		Set<CellReference> visited = new HashSet<>();
		Queue<CellReference> pending = new LinkedList<>();
		Map<CellReference, CellReference> parents = new HashMap<>();
		Deque<CellReference> evaluationQueue = new LinkedList<>();

		pending.add(reference);

		while (!pending.isEmpty()) {
			CellReference currentReference = pending.poll();

			if (table.hasExpression(currentReference)) {
				Expression expression = table.getExpression(currentReference);

				for (CellReference referenced : expression.getReferences()) {
					if (visited.contains(referenced)) {
						processCircularError(parents, currentReference);

						return;
					}

					if (!parents.containsKey(referenced)) {
						visited.add(currentReference);
						pending.add(referenced);
						parents.put(referenced, currentReference);
					}
				}

				evaluationQueue.addLast(currentReference);
			}
		}

		// Walk references in reverse topological order and evaluate
		while (!evaluationQueue.isEmpty()) {
			CellReference currentReference = evaluationQueue.pollLast();
			CellValue value = table.getValue(currentReference);

			// Evaluate expression only if value is empty, else we already evaluated this expression
			if (value.getType() == CellValueType.EMPTY) {
				Expression expression = table.getExpression(currentReference);

				value = expression.evaluate(table);

				table.setValue(currentReference, value);
			}
		}
	}

	/**
	 * Walk by reference hierarchy and set error for all cells
	 * 
	 * @param parents
	 *            map where value reference is parent for key reference
	 * @param currentReference
	 */
	private void processCircularError(Map<CellReference, CellReference> parents,
			CellReference currentReference) {
		CellReference parenReference = currentReference;

		while (parenReference != null) {
			table.setError(parenReference, new CircularCellError());

			parenReference = parents.get(parenReference);
		}
	}

	/**
	 * Set empty value for all references
	 * 
	 * @param expressionCells
	 */
	private void prepareCells(Set<CellReference> expressionCells) {
		for (CellReference reference : expressionCells) {
			table.setEmpty(reference);
		}
	}

	/**
	 * Returns all references to cells with expressions
	 * 
	 * @return
	 */
	private Set<CellReference> findExpressions() {
		Set<CellReference> expressionCells = new TreeSet<>();

		for (int j = 0; j < table.getColumns(); j++) {
			for (int i = 0; i < table.getRows(); i++) {
				CellReference reference = new CellReference(j, i);

				if (table.hasExpression(reference)) {
					expressionCells.add(reference);
				}
			}
		}

		return expressionCells;
	}

}
