package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public class LessThan extends BinaryExpression<Boolean>{

	public LessThan(Expression<Double> e1, Expression<Double> e2, SourceLocation sourceLocation) {
		super(e1, e2, sourceLocation);
	}

	@Override
	public String operatorToString() {
		return "<";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.BOOL;
	}

	@Override
	public Boolean getValue(Map<String, Expression<?>> variables) {
		return (Double)this.getExpressionLhs().getValue(variables) < 
				(Double)this.getExpressionRhs().getValue(variables);
	}

}
