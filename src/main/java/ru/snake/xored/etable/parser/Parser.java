package ru.snake.xored.etable.parser;

import ru.snake.xored.etable.CellReference;
import ru.snake.xored.etable.expression.AddExpression;
import ru.snake.xored.etable.expression.ConstantExpression;
import ru.snake.xored.etable.expression.DivExpression;
import ru.snake.xored.etable.expression.Expression;
import ru.snake.xored.etable.expression.MulExpression;
import ru.snake.xored.etable.expression.ReferenceExpression;
import ru.snake.xored.etable.expression.SubExpression;
import ru.snake.xored.etable.tokenizer.TokenType;
import ru.snake.xored.etable.tokenizer.Tokenizer;
import ru.snake.xored.etable.tokenizer.TokenizerException;

public class Parser {

	private final Tokenizer tokenizer;

	public Parser(String text) {
		this.tokenizer = new Tokenizer(text);
	}

	public Expression parse() throws ParserException {
		Expression expression = nextOperand();

		while (nextToken()) {
			TokenType tokenType = this.tokenizer.getTokenType();
			Expression right = nextOperand();

			switch (tokenType) {
			case OPERATION_ADD:
				expression = new AddExpression(expression, right);
				break;

			case OPERATION_SUB:
				expression = new SubExpression(expression, right);
				break;

			case OPERATION_MUL:
				expression = new MulExpression(expression, right);
				break;

			case OPERATION_DIV:
				expression = new DivExpression(expression, right);
				break;

			default:
				throw new LexicalParserException(this.tokenizer.getTokenValue(),
						TokenType.OPERATION_ADD, TokenType.OPERATION_SUB,
						TokenType.OPERATION_MUL, TokenType.OPERATION_DIV);
			}
		}

		return expression;
	}

	private Expression nextOperand() throws ParserException {
		nextToken();

		switch (this.tokenizer.getTokenType()) {
		case NUMBER:
			int value = 0;

			try {
				value = Integer.parseInt(this.tokenizer.getTokenValue());
			} catch (NumberFormatException e) {
				throw new ParserException(e);
			}

			return new ConstantExpression(value);

		case CELL_REFERENCE:
			CellReference reference;

			try {
				reference = new CellReference(this.tokenizer.getTokenValue());
			} catch (IllegalArgumentException e) {
				throw new SyntaxParserException(e);
			}

			return new ReferenceExpression(reference);

		default:
			throw new LexicalParserException(this.tokenizer.getTokenValue(),
					TokenType.NUMBER, TokenType.CELL_REFERENCE);
		}
	}

	private boolean nextToken() throws ParserException {
		try {
			return this.tokenizer.nextToken();
		} catch (TokenizerException e) {
			throw new SyntaxParserException(e);
		}
	}

}
