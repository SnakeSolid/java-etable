package ru.snake.xored.etable;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import ru.snake.xored.etable.error.CircularCellError;
import ru.snake.xored.etable.expression.Expression;
import ru.snake.xored.etable.value.CellValue;
import ru.snake.xored.etable.value.CellValueType;

/**
 * Single thread expression evaluator. Evaluate all expressions in the table
 * 
 * @author snake
 *
 */
public class SimpleEvaluator {

	private final Table table;

	/**
	 * Constructs evaluator for the table without expressions evaluation
	 * 
	 * @param table
	 */
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
		Set<CellReference> pending = new HashSet<>();

		evaluateExpressionRec(reference, pending);
	}

	/**
	 * Walk expressions by references in depth and check circular dependencies. Evaluate every possible expression in
	 * hierarchical order.
	 * 
	 * @param reference
	 * @param pending
	 *            currently pended evaluation expressions
	 */
	private void evaluateExpressionRec(CellReference reference,
			Set<CellReference> pending) {
		if (!isCellEmpty(reference)) {
			return;
		}

		Expression expression = table.getExpression(reference);

		pending.add(reference);

		for (CellReference referenced : expression.getReferences()) {
			if (pending.contains(referenced)) {
				processCircularError(pending);

				break;
			}

			evaluateExpressionRec(referenced, pending);
		}

		pending.remove(reference);

		if (isCellEmpty(reference)) {
			CellValue value = expression.evaluate(table);

			table.setValue(reference, value);
		}
	}

	/**
	 * Set error value for all cells referenced from the references
	 * 
	 * @param references
	 */
	private void processCircularError(Set<CellReference> references) {
		for (CellReference reference : references) {
			table.setError(reference, new CircularCellError());
		}
	}

	/**
	 * Check that cell has empty value
	 * 
	 * @param reference
	 * @return
	 */
	private boolean isCellEmpty(CellReference reference) {
		CellValue currentValue = table.getValue(reference);

		return currentValue.getType() == CellValueType.EMPTY;
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
