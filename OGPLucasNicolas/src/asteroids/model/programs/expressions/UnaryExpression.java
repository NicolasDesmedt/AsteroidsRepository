package asteroids.model.programs.expressions;

import asteroids.part3.programs.SourceLocation;

public abstract class UnaryExpression<T> extends Expression<T>{

	public UnaryExpression(Expression<?> expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.expression = expression;
	}
	
	public Expression<?> getExpression() {
		return this.expression;
	}
	
	private final Expression<?> expression;
	
	public boolean isMutable() {
		if (getExpression().isMutable())
			return true;
		return false;
	}
	
	public abstract String operatorToString();
	
	@Override
	public String toString() {
		String result = "";
		if (getExpression() instanceof BinaryExpression || 
			getExpression() instanceof UnaryExpression){
			result = "(" + getExpression().toString() + ")";
		}else{
			result = getExpression().toString();
		}
		result += operatorToString();
		return result;
	}

}
