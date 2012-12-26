package ch.warti.calc.parser;

public class Number implements Node {
	
	private double value;

	public Number(Token token) {
		this.value = Double.valueOf(token.getValue());
	}
	
	public Object getValue() {
		return value;
	}

}
