package ch.warti.calc.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import ch.warti.calc.CalculatorException;

public class Parser {
	
	private Tree tree = new Tree();
	private Stack<Expression> expressionsStack = new Stack<Expression>();
	private Stack<Token> tokensInExpressionStack = new Stack<Token>();
	private Expression rootExpression;
	
	public Parser(List<Token> tokens) {
		rootExpression = new Expression(/*null*/);
		
		createOrderOfOperation(tokens);
		/*for (Token tok : tokens) {
			System.out.print(tok.getValue());
		}*/
		
		
		createNextExpression(tokens, rootExpression);
		rootExpression = eraseDoubleParentheses(rootExpression);
		rootExpression.cleanse();
		tree.add(rootExpression);
		
	}
	
	public String createOrderOfOperation(List<Token> tokens) {
		
		for (int i=0;tokens.size()>i;i++) {
			if (tokens.get(i).getType()==TokenType.OPERATOR) {
				if (tokens.get(i).getValue().equals("*") || tokens.get(i).getValue().equals("/")) {
		
					tokens.add(findParensPosition(tokens, i), new Token("(", TokenType.PAREN_OPEN));
					i+=3;
					tokens.add(i, new Token(")", TokenType.PAREN_CLOSE));
				}
			}
		}
		
		
		String newString = "";
		for (Token tok:tokens) {
			newString+= tok.getValue();
		}
		return newString;
	}
	
	private int findParensPosition(List<Token> tokens, int state) {
		int foundParensClosed = 0;
		for (int currentState=state; currentState>-1; currentState--) {
			if (tokens.get(currentState).getType()==TokenType.PAREN_CLOSE) {
				foundParensClosed++;
			}
			if (tokens.get(currentState).getType()==TokenType.PAREN_OPEN) {
				foundParensClosed--;
				if (foundParensClosed==0) {
					return currentState;
				}				
			}
			if (tokens.get(currentState).getType()==TokenType.OPERATOR && foundParensClosed == 0){
				if (tokens.get(currentState).getValue().equals("+") || tokens.get(currentState).getValue().equals("-")) {
					break;
				}
			}
		}
		return state-1;
	}
	

	private Expression createNextExpression(List<Token> tokens, Expression expr) {
			Token nextToken = tokens.get(0);
			//try {System.out.println("\n expr before: "+expr);}catch(Exception e){}
			switch (nextToken.getType()) {
			case PAREN_OPEN:
				tokens.remove(0);
				//expr = createNextExpression(tokens, new Expression(/*expr*/));
				expr.setFreeExpression(createNextExpression(tokens, new Expression(/*expr*/)));
				break;
			case PAREN_CLOSE:
				//expr/*.getParent()*/.setFreeExpression(expr);
				return expr/*.getParent()*/; 
			case NUMBER:
				expr.setFreeExpression(new Number(nextToken));
				break;
			case OPERATOR:
				expr = expr.setOperator(new Operator(nextToken));
				break;
			}
			
			if (tokens.size()==1) return expr;
			
			tokens.remove(0);
			//try {System.out.println("\n expr after: "+expr);}catch(Exception e){}
			return createNextExpression(tokens, expr); 
	}
	
	private Expression eraseDoubleParentheses(Expression expr) {
		
		if (expr.isOperatorFree() && expr.isExpression1AnExpression()) {
			expr = expr.getExpression1AsExpression();
			return eraseDoubleParentheses(expr);
		}
		
		if (expr.isExpression1AnExpression()){
			eraseDoubleParentheses(expr.getExpression1AsExpression());
		}
		if (expr.isExpression2AnExpression()){
			eraseDoubleParentheses(expr.getExpression2AsExpression());
		}
		
		return expr;		
	}

	
	public Tree getTree() {
		return tree;
	}
	
	/*private boolean isNumber(List<Token> tokens) {
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
	}*/
}
