package asteroids.model.programs.statements;

import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public abstract class ActionStatement extends Statement {

	protected ActionStatement(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	public double getTimeActionStatement() {
		return timeActionStatement;
	}
	
	private double timeActionStatement = 0.2;
	
	public Boolean canBeExecuted() {
		double timeLeft = this.getProgram().getTimeLeft();
		return this.getTimeActionStatement() <= timeLeft;
	}
	
	@Override
	public void executeStatement(Map<String, Expression<?>> variables) {
		if (canBeExecuted()) {
			this.getProgram().substractTime(this.getTimeActionStatement());
		}
		else {
			this.getProgram().putOnHold(true);
		}
	}

}
