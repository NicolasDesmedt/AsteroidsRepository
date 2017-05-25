package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public class Sqrt extends UnaryExpression<Double>{

	public Sqrt(Expression<Double> expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}
	
	@Override
	public String operatorToString() {
		return "sqrt";
	}
	
	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.DOUBLE;
	}

	@Override
	public Double getValue(Map<String, Expression<?>> variables) {
		assert (this.getExpression().getType(variables) == Type.DOUBLE);
		return Math.sqrt((Double)this.getExpression().getValue(variables));
	}
}