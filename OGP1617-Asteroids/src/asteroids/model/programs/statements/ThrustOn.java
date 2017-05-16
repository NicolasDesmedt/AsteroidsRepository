package asteroids.model.programs.statements;

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
	public void executeStatement() {
		super.executeStatement();
		if (!this.getProgram().isPutOnHold()) {
			this.getShip().setThrust(true);
		}
	}
	

}
