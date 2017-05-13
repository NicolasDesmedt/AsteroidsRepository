package asteroids.model.program.statements;

import asteroids.model.Ship;
import asteroids.part3.programs.SourceLocation;

public class ThrustOn extends ActionStatement {

	protected ThrustOn(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public String toString() {
		return "thrust_on;";
	}
	
	@Override
	public void execute(Ship ship) {
		
		ship.fireBullet();
	}
	

}
