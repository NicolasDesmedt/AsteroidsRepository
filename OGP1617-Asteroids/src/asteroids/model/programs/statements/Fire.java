package asteroids.model.programs.statements;

import asteroids.model.Ship;
import asteroids.part3.programs.SourceLocation;

public class Fire extends ActionStatement {
	
	public Fire(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public void executeStatement() {
		super.executeStatement();
		if (!this.getProgram().isPutOnHold()) {
			this.getShip().fireBullet();
		}
	}
	
	@Override
	public String toString() {
		return "fire;";
	}
}
