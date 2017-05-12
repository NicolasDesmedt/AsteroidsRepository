package asteroids.model.program.statements;

import asteroids.model.Ship;
import asteroids.part3.programs.SourceLocation;

public class Fire extends Statement{
	
	public Fire(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public void execute(Ship ship) {
		super.execute(ship);
		ship.fireBullet();
	}
	
	@Override
	public String toString() {
		return "fire;";
	}

	@Override
	public boolean isMutable() {
		return false;
	}

}
