package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public class Equality extends BinaryExpression<Boolean> {

	public Equality(Expression<?> e1, Expression<?> e2, SourceLocation sourceLocation) {
		super(e1, e2, sourceLocation);
	}

	@Override
	public String operatorToString() {
		return "==";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.BOOL;
	}

	@Override
	public Boolean getValue(Map<String, Expression<?>> variables) {
		return (this.getExpressionLhs() == this.getExpressionRhs());
	}

}
