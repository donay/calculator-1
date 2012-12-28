package ch.warti.calc.parser;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import ch.warti.calc.parser.Lexer;

public class ParserTest {
	
	@Test
	public void testSingleNumber() {
		Parser parser = new Parser(new Lexer("1").getTokens());
		assertEquals("(1.0 NULL NULL)", parser.getTree().getNodes().get(0).getValue());
	}
	
	@Test
	public void testParentheseToNumber() {
		Parser parser = new Parser(new Lexer("(((1)))").getTokens());
		assertEquals(1d, parser.getTree().getNodes().get(0).getValue());
	}
	
	@Test
	public void testSimpleAddition() {
		Parser parser = new Parser(new Lexer("1 + 2").getTokens());
		assertEquals("(1.0 + 2.0)", parser.getTree().getNodes().get(0).toString());
	}
	
	@Test
	public void testSimpleAdditionWith3Numbers() {
		Parser parser = new Parser(new Lexer("1 + 2 + 3").getTokens());
		assertEquals("((1.0 + 2.0) + 3.0)", parser.getTree().getNodes().get(0).toString());
	}
	
	@Test
	public void testSimpleAdditionWith3NumbersAndParenthese() {
		Parser parser = new Parser(new Lexer("1 + (2 + 3)").getTokens());
		assertEquals("(1.0 + (2.0 + 3.0))", parser.getTree().getNodes().get(0).toString());
	}
	
	@Test
	public void testSimpleAdditionWith3NumbersAndParentheseFirst() {
		Parser parser = new Parser(new Lexer("(1 + 2) + 3").getTokens());
		assertEquals("((1.0 + 2.0) + 3.0)", parser.getTree().getNodes().get(0).toString());
	}

	@Test	
	public void testSimpleAdditionParenthese() {
		Parser parser = new Parser(new Lexer("(1) + 2").getTokens());
		assertEquals("(1.0 + 2.0)", parser.getTree().getNodes().get(0).toString());
	}
	
 
	@Test
	
	public void testMultipleParentheseAdditionParenthese() {
		Parser parser = new Parser(new Lexer("((1) + 2)").getTokens());
		assertEquals("(1.0 + 2.0)", parser.getTree().getNodes().get(0).toString());
	}
	
	@Test
	public void testComplicated() {
		Parser parser = new Parser(new Lexer("((1 - 2 ) + 2)").getTokens());
		assertEquals("((1.0 - 2.0) + 2.0)", parser.getTree().getNodes().get(0).toString());
	}
	
	@Test
	public void testAdditionWithParens(){
		Parser parser = new Parser(new Lexer("1+(2+3)").getTokens());
		assertEquals("(1.0 + (2.0 + 3.0))", parser.getTree().getNodes().get(0).toString());
	}
	
	@Test
	public void testAdditionWithParensComplex(){
		Parser parser = new Parser(new Lexer("1+(2+3)+4").getTokens());
		assertEquals("((1.0 + (2.0 + 3.0)) + 4.0)", parser.getTree().getNodes().get(0).toString());
	}
	
	@Test
	public void testAdditionAndMultiplicationWithParensComplex(){
		Parser parser = new Parser(new Lexer("1.5*(2+3)+4").getTokens());
		assertEquals("((1.5 * (2.0 + 3.0)) + 4.0)", parser.getTree().getNodes().get(0).toString());
	}
	
	@Test
	public void testAdditionAndMultiplicationWithoutParensComplex(){
		Parser parser = new Parser(new Lexer("2+4*4*5+7*8").getTokens());
		assertEquals("((2.0 + ((4.0 * 4.0) * 5.0)) + (7.0 * 8.0))", parser.getTree().getNodes().get(0).toString());
	}
	
	@Test
	public void testAdditionAndMultiplicationWithParensComplex2(){
		Parser parser = new Parser(new Lexer("2 + ((5*3) * 3)").getTokens());
		assertEquals("(2.0 + ((5.0 * 3.0) * 3.0))", parser.getTree().getNodes().get(0).toString());
	}
	
	
	@Test
	public void testRealBrainFuckerWithSettedParens(){
		Parser parser = new Parser(new Lexer("(22 : 11) + 5 - 3 + 9 - ((1 : 6) * 10) - ((15 * 3) : 5) + 4 - ((1 : 2) * (3 + (6 : 5)))").getTokens());
		assertEquals("((((((((22.0 / 11.0) + 5.0) - 3.0) + 9.0) - ((1.0 / 6.0) * 10.0)) - ((15.0 * 3.0) / 5.0)) + 4.0) - ((1.0 / 2.0) * (3.0 + (6.0 / 5.0))))", parser.getTree().getNodes().get(0).toString());
	}
	
	@Test
	public void testRealBrainFucker(){
		//6.03333
		Parser parser = new Parser(new Lexer("22 : 11 + 5 - 3 + 9 - 1 : 6 * 10 - 15 * 3 : 5 + 4 - 1 : 2 * 3 + 6 : 5").getTokens());
		assertEquals("(((((((((22.0 / 11.0) + 5.0) - 3.0) + 9.0) - ((1.0 / 6.0) * 10.0)) - ((15.0 * 3.0) / 5.0)) + 4.0) - ((1.0 / 2.0) * 3.0)) + (6.0 / 5.0))", parser.getTree().getNodes().get(0).toString());
	}
	
	@Test
	public void testOperationOrderSimple(){
		Parser parser = new Parser(new Lexer("1+1").getTokens());
		assertEquals("2+(3*4)", parser.createOrderOfOperation(new Lexer("2+3*4").getTokens()));
	}
	
	@Test
	public void testOperationOrderSimple2(){
		Parser parser = new Parser(new Lexer("1+1").getTokens());
		assertEquals("2+(33.5*4)", parser.createOrderOfOperation(new Lexer("2+33.5*4").getTokens()));
	}
	
	@Test
	public void testOperationOrderTwoMultiplicationsAndAddition(){
		Parser parser = new Parser(new Lexer("1+1").getTokens());
		assertEquals("2+((4*4)*5)+7", parser.createOrderOfOperation(new Lexer("2+4*4*5+7").getTokens()));
	}
	
	@Test
	public void testOperationOrderTwoMultiplicationsAndAddition2(){
		Parser parser = new Parser(new Lexer("1+1").getTokens());
		assertEquals("2+((((5*3))*3))", parser.createOrderOfOperation(new Lexer("2+((5*3)*3)").getTokens()));
	}
	
	@Test
	public void testOperationOrderManyMultiplicationsAndAddition(){
		Parser parser = new Parser(new Lexer("1+1").getTokens());
		assertEquals("2+((4*4)*5)+(7*8)", parser.createOrderOfOperation(new Lexer("2+4*4*5+7*8").getTokens()));
	}	
	
	@Test
	public void testOperationOrderManyMultiplicationsAndAdditionWithParens(){
		Parser parser = new Parser(new Lexer("1+1").getTokens());
		assertEquals("(((2+4)*4)*5)+(7*8)", parser.createOrderOfOperation(new Lexer("(2+4)*4*5+7*8").getTokens()));
	}
}
