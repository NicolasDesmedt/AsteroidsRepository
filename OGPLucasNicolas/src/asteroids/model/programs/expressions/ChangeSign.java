package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public class ChangeSign extends UnaryExpression<Double>{

	public ChangeSign(Expression<Double> expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}
	
	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.DOUBLE;
	}

	@Override
	public Double getValue(Map<String, Expression<?>> variables) {
		assert (this.getExpression().getType(variables) == Type.DOUBLE);
		return -(Double)this.getExpression().getValue(variables);
	}

	@Override
	public String operatorToString() {
		return "-";
	}

}
