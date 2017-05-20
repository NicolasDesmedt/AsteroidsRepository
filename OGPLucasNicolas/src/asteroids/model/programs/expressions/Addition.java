package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public class Addition extends BinaryExpression<Double> {

	public Addition(Expression<Double> e1, Expression<Double> e2, SourceLocation sourceLocation) {
		super(e1, e2, sourceLocation);
	}

	@Override
	public String operatorToString() {
		return "+";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.DOUBLE;
	}

	@Override
	public Double getValue(Map<String, Expression<?>> variables) {
		System.out.println("In Addition " + (Double)this.getExpressionLhs().getValue(variables));
		return (Double)this.getExpressionLhs().getValue(variables) + 
				(Double)this.getExpressionRhs().getValue(variables);
	}

}
