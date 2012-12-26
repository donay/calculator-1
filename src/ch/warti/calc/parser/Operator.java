package ch.warti.calc.parser;

public class Operator implements Node{

	private String value;

	public Operator(Token token) {
		this.value = token.getValue();
	}

	public Object getValue() {
		return value;
	}

}
