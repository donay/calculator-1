package ch.warti.calc.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.warti.calc.CalculatorException;

public class Lexer {

	private enum STATE {
		WHITESPACE, NUMBER, OPERATOR, PARENTHESE, NUMBER_IN_DECIMAL
	};

	private List<Token> tokens = new ArrayList<Token>();
	private StringBuilder accumulator = new StringBuilder();
	private String input;
	private STATE state;
	private int inputState = 0;
	private char currentChar = ' ';

	public Lexer(String input) {
		this.input = input;
		tokenize();
	}

	public List<Token> getTokens() {
		return tokens;
	}

	private void tokenize() {
		state = STATE.WHITESPACE;
		while (!isLastChar()) {
			currentChar = getCurrentChar();

			switch (state) {
			case WHITESPACE:
				whitespace();
				break;
			case NUMBER:
				number();
				break;
			case NUMBER_IN_DECIMAL:
				numberInDecimal();
				break;
			case OPERATOR:
				operator();
				break;
			case PARENTHESE:
				parenthese();
				break;
			}
		}

	}

	private void whitespace() {
		if (isWhitespace(currentChar)) {
			inputState++;
		} else if (isNumber(currentChar)) {
			state = STATE.NUMBER;
		} else if (isOperator(currentChar)) {
			state = STATE.OPERATOR;
		} else if (isParenthese(currentChar)) {
			state = STATE.PARENTHESE;
		} else {
			throw new CalculatorException("Invalid input: " + currentChar);
		}
	}

	private void number() {
		if (isDelimiter(currentChar)) {
			accumulator.append(".");
			state = STATE.NUMBER_IN_DECIMAL;
			inputState++;
		} else if (isNumber(currentChar)) {
			accumulator.append(currentChar);
			inputState++;
		}
		if ( (!isNumber(currentChar) && !isDelimiter(currentChar)) || isLastChar()) {
			addToken(TokenType.NUMBER);
		}

	}

	private void numberInDecimal() {
		if (isNumber(currentChar)) {
			accumulator.append(currentChar);
			inputState++;
		} else if (isDelimiter(currentChar)) {
			throw new CalculatorException("Can't create number: " + accumulator.toString() + currentChar); 
		}
		if ( (!isNumber(currentChar)) || isLastChar()) {
			addToken(TokenType.NUMBER);
		}
	}
	
	private void operator() {
		if (currentChar==':') currentChar='/';
		
		accumulator.append(currentChar);
		addToken(TokenType.OPERATOR);
		inputState++;
	}

	private void parenthese() {
		accumulator.append(currentChar);
		addToken((currentChar == '(' ? TokenType.PAREN_OPEN : TokenType.PAREN_CLOSE));
		inputState++;
	}

	private void addToken(TokenType type) {
		tokens.add(new Token(accumulator.toString(), type));
		accumulator.setLength(0);
		state = STATE.WHITESPACE;
	}

	private boolean isLastChar() {
		return input.length() == inputState;
	}

	private char getCurrentChar() {
		return input.charAt(inputState);

	}

	private boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}


	private boolean isNumber(char c) {
		return (Character.isDigit(c));
	}

	private boolean isDelimiter(char c) {
		return (c == ',' || c == '.');
	}

	private boolean isOperator(char c) {
		return (c == '+' || c == '-' || c == '*' || c == ':' || c == '/');
	}

	private boolean isParenthese(char c) {
		return (c == '(' || c == ')');
	}

	@Override
	public String toString() {
		return "[" + toTokenString(tokens.iterator()) + "]";
	}

	private String toTokenString(Iterator<Token> iterator) {
		String value = iterator.next().getValue();
		if (iterator.hasNext()) {
			return value + ", " + toTokenString(iterator);
		} else {
			return value;
		}
	}
}
