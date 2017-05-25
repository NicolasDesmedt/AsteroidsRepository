package asteroids.model.programs.statements;

import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class Fire extends ActionStatement {
	
	public Fire(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public String toString() {
		return "fire;";
	}
	
	@Override
	public void executeStatement(Map<String, Expression<?>> variables) {
		super.executeStatement(variables);
		if (!this.getProgram().isPutOnHold()) {
			this.getShip().fireBullet();
		}
	}
}
