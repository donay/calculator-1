package ch.warti.calc.parser;

import java.util.ArrayList;
import java.util.List;

public class Expression implements Node {

	private Node expression;
	private Operator operator;
	private Node expression2;
	private List<Expression> childExpression = new ArrayList<Expression>();
	
	public Expression(Node expr, Operator operator, Node expr2) {
		this.expression = expr;
		this.operator = operator; 
		this.expression2 = expr2;
	}

	@Override
	public Object getValue() {
		return this;
	}
	
	@Override
	public String toString() {
		return "(" + expression.getValue() + " " + operator.getValue() + " " + expression2.getValue() + ")";
	}
}
