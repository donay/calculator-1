package ch.warti.calc.parser;

import java.util.List;

import ch.warti.calc.CalculatorException;

public class Parser {
	
	private Tree tree = new Tree();
	
	public Parser(List<Token> tokens) {
		tree.add(getExpression(tokens));
	}

	private Node getExpression(List<Token> tokens) {
		if (isNumber(tokens)) {
			return new Number(getNumber(tokens));
		}
		Node expr = null;
//		getFirstExpression(tokens);
		if (!tokens.get(0).isNumber()) {
			expr = getExpression(tokens);	
		} else {
			expr = new Number(getNumber((tokens)));
		}
		
		return null;
	}

	private boolean isExpression(List<Token> tokens) {
		if (tokens.size() < 3) return false;
		if (!isNumber(tokens)) {
			return false;
		}
		Token number = getNumber(tokens);
		return isNumber(tokens.subList(0, 1)) &&  tokens.get(1).isOperator() && isNumber(tokens.subList(2, tokens.size() -1));
	}

	public Tree getTree() {
		return tree;
	}
	
	private boolean isNumber(List<Token> tokens) {
		if ((tokens.get(0).getType() == TokenType.NUMBER) && tokens.size() == 1) return true;
		if (tokens.get(0).isParensOpen() && tokens.get(tokens.size() -1 ).isParensClose()) {
			return isNumber(tokens.subList(1, tokens.size() - 1));
		}
		return false;
	}
	
	private Token getNumber(List<Token> tokens) {
		if (tokens.get(0).getType() == TokenType.NUMBER) return tokens.get(0);
		if (tokens.get(0).isParensOpen() && tokens.get(tokens.size() -1 ).isParensClose()) {
			return getNumber(tokens.subList(1, tokens.size() - 1));
		}
		throw new CalculatorException("Is not number:" + tokens.toString());
	}
}
