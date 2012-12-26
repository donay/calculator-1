package ch.warti.calc.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.warti.calc.parser.Lexer;

public class ParserTest {
	
	@Test
	public void testSingleNumber() {
		Parser parser = new Parser(new Lexer("1").getTokens());
		assertEquals(1d, parser.getTree().getNodes().get(0).getValue());
	}
	
	@Test
	public void testParentheseToNumber() {
		Parser parser = new Parser(new Lexer("(((1)))").getTokens());
		assertEquals(1d, parser.getTree().getNodes().get(0).getValue());
	}
	
	@Test
	public void testSimpleAddition() {
		Parser parser = new Parser(new Lexer("1 + 1").getTokens());
		assertEquals("1.0 + 1.0", parser.getTree().getNodes().get(0).toString());
	}
	
	@Test
	public void testSimpleAdditionParenthese() {
		Parser parser = new Parser(new Lexer("(1) + 1").getTokens());
		assertEquals("1.0 + 1.0", parser.getTree().getNodes().get(0).toString());
	}
}
