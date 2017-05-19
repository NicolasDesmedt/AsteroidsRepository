package asteroids.model.programs.statements;

import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class ThrustOn extends ActionStatement {

	protected ThrustOn(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "thrust_on;";
	}
	
	@Override
	public void executeStatement(Map<String, Expression<?>> variables) {
		super.executeStatement(variables);
		if (!this.getProgram().isPutOnHold()) {
			this.getShip().setThrust(true);
		}
	}
	

}
