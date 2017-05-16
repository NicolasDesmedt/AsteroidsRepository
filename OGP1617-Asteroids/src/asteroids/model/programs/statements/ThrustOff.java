package asteroids.model.programs.statements;

import asteroids.part3.programs.SourceLocation;

public class ThrustOff extends ActionStatement {

	protected ThrustOff(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "thrust_off;";
	}
	
	@Override
	public void executeStatement() {
		super.executeStatement();
		if (!this.getProgram().isPutOnHold()) {
			this.getShip().setThrust(false);
		}
	}
	

}
