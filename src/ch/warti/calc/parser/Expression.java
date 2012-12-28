package ch.warti.calc.parser;

public class Expression implements Node {

	private Node expression1;
	private Operator operator;
	private Node expression2;
	private Expression parent;
	
	public Expression(/*Expression parent*/){
		this.expression1 = null;
		this.operator = null; 
		this.expression2 = null;
		this.parent = parent;
	};
	
	public Expression setFreeExpression(Node expression) {
		if (this.expression1 == null) {setExpression1(expression);
		} else if (this.expression2 == null) {setExpression2(expression);
		} else {
			throw new RuntimeException("Expressions already occupied: " + toString());
		}
		return this;
	}
	
	public boolean isOperatorFree() {
		if (this.operator == null) return true;
		return false;
	}
		
	private void setExpression1(Node expression1) {
		this.expression1 = expression1;
	}
	
	public Expression setOperator(Operator operator) {
		if (isOperatorFree()) {
			this.operator = operator;
			return this;
		} else {
			Expression newExpr = new Expression(/*this*/);
			newExpr.setFreeExpression(this.expression1);
			newExpr.setFreeExpression(this.expression2);
			newExpr.setOperator(this.operator);
			
			this.setExpression1(newExpr);
			this.operator = operator;
			this.setExpression2(null);
			return this;
		}
	}
	
	private void setExpression2(Node expression2) {
		this.expression2 = expression2;
	}

	/*public Expression getParent() {
		return parent;
	}*/
	
	public Expression getExpression1AsExpression() {
		return (Expression) expression1;
	}
	
	public Expression getExpression2AsExpression() {
		return (Expression) expression2;
	}
	
	public Number getExpression1AsNumber() {
		return (Number) expression1;
	}
	
	public Number getExpression2AsNumber() {
		return (Number) expression2;
	}
	
	public boolean isExpression1AnExpression() {
		if (this.expression1!=null && this.expression1.getClass().equals(Expression.class)) return true;
		return false;
	}
	
	public boolean isExpression2AnExpression() {
		if (this.expression2!=null && this.expression2.getClass().equals(Expression.class)) return true;
		return false;
	}
	
	@Override
	public Object getValue() {
		return this;
	}
	
	@Override
	public String toString() {
		return "(" + expression1.getValue() + " " + ((operator!=null) ? operator.getValue() : "NULL") + " " + ((expression2!=null) ? expression2.getValue() : "NULL") + ")";
	}

	public void cleanse() {
		if (!isExpression1AnExpression() && !isExpression2AnExpression()) {
			return;
		}
		
		if (isExpression1AnExpression()) {
			if (this.getExpression1AsExpression().isOperatorFree()) {
				this.expression1 = this.getExpression1AsExpression().getExpression1AsNumber();
			} else {
				this.getExpression1AsExpression().cleanse();
			}
		}
		
		if (isExpression2AnExpression()) {
			if (this.getExpression2AsExpression().isOperatorFree()) {
				this.expression2 = this.getExpression2AsExpression().getExpression2AsNumber();
			} else {
				this.getExpression2AsExpression().cleanse();
			}
		}
	}
}
