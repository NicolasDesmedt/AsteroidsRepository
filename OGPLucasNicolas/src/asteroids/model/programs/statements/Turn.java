package asteroids.model.programs.statements;

import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class Turn extends ActionStatement {
		
	public Turn(Expression<Double> angle, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.angle = angle;
	}
	
	public Expression<Double> getAngle() {
		return this.angle;
	}
	
	private Expression<Double> angle;
	
	@Override
	public String toString() {
		return "turn "+ this.getAngle().toString() + ";";
	}

	@Override
	public void executeStatement(Map<String, Expression<?>> variables) {
		connectExpression(this.getAngle());
		super.executeStatement(variables);
		if (!this.getProgram().isPutOnHold()) {
			this.getShip().turn(this.getAngle().getValue(variables));
		}
	}
}
