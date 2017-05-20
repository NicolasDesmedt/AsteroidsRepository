package asteroids.model.programs.expressions;

import asteroids.part3.programs.SourceLocation;

public abstract class BinaryExpression<T> extends Expression<T>{

	public BinaryExpression(Expression<?> e1, Expression<?> e2, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public Expression<?> getExpressionLhs() {
		return this.e1;
	}
	
	private final Expression<?> e1;
	
	public Expression<?> getExpressionRhs() {
		return this.e2;
	}
	
	private final Expression<?> e2;
	
	public abstract String operatorToString();
	
	@Override
	public String toString() {
		String result = "";
		if (getExpressionLhs() instanceof BinaryExpression || 
			getExpressionLhs() instanceof UnaryExpression){
			result = "(" + getExpressionLhs().toString() + ")";
		}else{
			result = getExpressionLhs().toString();
		}
		result += operatorToString();
		if (getExpressionRhs() instanceof BinaryExpression ||
			getExpressionRhs() instanceof UnaryExpression) {
			result += "(" + getExpressionRhs().toString() + ")";
		}else{
			result += getExpressionRhs().toString();
		}
		return result;
	}
	
	public boolean isMutable() {
		if (getExpressionLhs().isMutable() || getExpressionRhs().isMutable())
			return true;
		return false;
	}
	


}
