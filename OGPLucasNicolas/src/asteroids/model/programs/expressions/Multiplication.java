package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public class Multiplication extends BinaryExpression<Double>{

	public Multiplication(Expression<Double> e1, Expression<Double> e2, SourceLocation location) {
		super(e1, e2, location);
	}

	@Override
	public String operatorToString() {
		return "*";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.DOUBLE;
	}

	@Override
	public Double getValue(Map<String, Expression<?>> variables) {
		return (Double)this.getExpressionLhs().getValue(variables) 
				* (Double)this.getExpressionRhs().getValue(variables);
	}

}
