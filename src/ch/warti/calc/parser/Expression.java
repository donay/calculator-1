package ch.warti.calc.parser;

public class Expression implements Node {

	
	
	private Number number;
	private Operator operator;
	private Number number2;

	public Expression(Number number, Operator operator, Number number2) {
		this.number = number;
		this.operator = operator;
		this.number2 = number2;
	}

	@Override
	public Object getValue() {
		return this;
	}
	
	@Override
	public String toString() {
		return number.getValue() + " " + operator.getValue() + " " + number2.getValue();
	}
}
