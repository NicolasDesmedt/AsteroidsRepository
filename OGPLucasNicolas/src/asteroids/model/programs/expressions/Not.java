package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public class Not extends UnaryExpression<Boolean>{

	public Not(Expression<Boolean> expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}

	@Override
	public String operatorToString() {
		return "!";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.BOOL;
	}

	@Override
	public Boolean getValue(Map<String, Expression<?>> variables) {
		assert (this.getExpression().getType(variables) == Type.BOOL);
		return !(Boolean)this.getExpression().getValue(variables);
	}

}
