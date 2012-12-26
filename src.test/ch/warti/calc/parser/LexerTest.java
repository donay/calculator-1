package ch.warti.calc.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.warti.calc.CalculatorException;
import ch.warti.calc.parser.Lexer;
import ch.warti.calc.parser.TokenType;

public class LexerTest {

	@Test
	public void testSingleNumericToken() {
		Lexer lexer = new Lexer("3");
		assertEquals("[3]", lexer.toString());
	}
	
	@Test
	public void testSingleNumericTokenType() {
		Lexer lexer = new Lexer("3");
		assertEquals(TokenType.NUMBER, lexer.getTokens().get(0).getType());
	}
	
	@Test
	public void testSingleNumericTokenValue() {
		Lexer lexer = new Lexer("3");
		assertEquals("3", lexer.getTokens().get(0).getValue());
	}
	
	@Test
	public void testFloatNumeric() {
		Lexer lexer = new Lexer("3.5");
		assertEquals("3.5", lexer.getTokens().get(0).getValue());
	}
	
	@Test
	public void testInvalidInput() {
		try {
			new Lexer("a");
		} catch (CalculatorException e) {
			assertEquals("Invalid input: a", e.getMessage());
		}
		
	}
	
	@Test
	public void testFloatNumericWrong() {
		try {
			new Lexer("3.5.1");
		} catch (CalculatorException e) {
			assertEquals("Can't create number: 3.5.", e.getMessage());
		}
	}
	
	@Test
	public void testNumericTokenValue35() {
		Lexer lexer = new Lexer("35");
		assertEquals("35", lexer.getTokens().get(0).getValue());
	}
	
	@Test
	public void testNumericTokenValueWithSpace() {
		Lexer lexer = new Lexer(" 35 ");
		assertEquals("35", lexer.getTokens().get(0).getValue());
	}
	
	@Test
	public void testOperatorTokenValue() {
		assertEquals("[3, +, 35]", new Lexer("3+35").toString());
	}
	
	@Test
	public void testParentheseTokenValue() {
		assertEquals("[3, +, (, 35, -, 89, )]", new Lexer("3+(35-89)").toString());
	}
	
	@Test
	public void testToTokenString() {
		assertEquals("[3, +, 35, -, 89, +, 3, *, 12]", new Lexer("3 + 35-89+ 3 * 12").toString());
	}
	
	@Test
	public void testParenthese() {
		assertEquals("[(, (, 1, ), )]", new Lexer("((1))").toString());
	}
}
