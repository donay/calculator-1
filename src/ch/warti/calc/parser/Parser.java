package ch.warti.calc.parser;

import java.util.List;

import ch.warti.calc.CalculatorException;

public class Parser {
	
	private Tree tree = new Tree();
	
	public Parser(List<Token> tokens) {
		tree.add(getRootNode(tokens));
	}
	
	private Node getRootNode(List<Token> tokens) {
		if (isNumber(tokens)) {
			return new Number(getNumber(tokens)); 
		} else {
			return getExpression(tokens);
		}
			
	}

	private Node getExpression(List<Token> tokens) {
		if (tokens.get(0).getType() == TokenType.PAREN_OPEN && tokens.get(tokens.size() - 1).getType() == TokenType.PAREN_CLOSE) {
			return getExpression(tokens.subList(1, tokens.size() - 1));
		}
		if (isNumber(tokens)) {
			return new Number(getNumber(tokens)); 
		} 
		int pos = getFirstOperatorPos(tokens);
		return new Expression(getExpression(tokens.subList(0, pos)), new Operator(tokens.get(pos)), getExpression(tokens.subList(pos + 1, tokens.size())));
	}


	private int getFirstOperatorPos(List<Token> tokens) {
		if (tokens.get(1).getType() == TokenType.OPERATOR) {
			return 1;
		} else {
			if (tokens.get(0).getType() == TokenType.PAREN_OPEN) {
				return 1 + posLastParenthese(tokens);
			}
		}
		throw new CalculatorException("Not implemented yet");
	}


	private int posLastParenthese(List<Token> tokens) {
		int pos = 0;
		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.get(i).getType() == TokenType.PAREN_CLOSE) {
				pos = i;
			}
		}
		return pos;
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
