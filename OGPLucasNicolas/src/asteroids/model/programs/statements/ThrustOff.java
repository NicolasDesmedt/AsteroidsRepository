package asteroids.model.programs.statements;

import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class ThrustOff extends ActionStatement {

	public ThrustOff(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "thrust_off;";
	}
	
	@Override
	public void executeStatement(Map<String, Expression<?>> variables) {
		super.executeStatement(variables);
		if (!this.getProgram().isPutOnHold()) {
			this.getShip().setThrust(false);
		}
	}
}
