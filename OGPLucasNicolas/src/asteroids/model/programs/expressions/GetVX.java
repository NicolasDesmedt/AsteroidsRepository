package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.model.Entity;
import asteroids.part3.programs.SourceLocation;

public class GetVX extends UnaryExpression<Double> {

	public GetVX(Expression<?> expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}

	@Override
	public String operatorToString() {
		return "getvx";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.DOUBLE;
	}

	@Override
	public Double getValue(Map<String, Expression<?>> variables) {
		this.getStatement().connectExpression(this.getExpression());
		assert ((this.getExpression().getType(variables) == Type.ENTITY)
				&& (this.getExpression().getValue(variables) != null));
		return ((Entity)getExpression().getValue(variables)).getVelocityX();
	}

}