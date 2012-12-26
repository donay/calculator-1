package ch.warti.calc.parser;

public class Token {
	
	private String value;
	private TokenType type;
	
	public Token(String value, TokenType type) {
		this.value = value;
		this.type = type;		
	}
	
	public String getValue() {
		return value;
	}
	
	public TokenType getType() {
		return type;
	}

	public boolean isParensOpen() {
		return type == TokenType.PAREN_OPEN;
	}

	public boolean isNumber() {
		return type == TokenType.NUMBER;
	}

	public boolean isParensClose() {
		return type == TokenType.PAREN_CLOSE;
	}

	public boolean isOperator() {
		return type == TokenType.OPERATOR;
	}
	
	@Override
	public String toString() {
		return "value: " + value + " type: " + type; 
	}


}
