package asteroids.model.programs.statements;

import asteroids.part3.programs.SourceLocation;

public class Turn extends ActionStatement {
		
	public Turn(double angle, SourceLocation sourceLocation) {
		super(sourceLocation);
	}
		
	@Override
	public void executeStatement() {
		super.executeStatement();
		if (!this.getProgram().isPutOnHold()) {
			//this.getShip().turn();
		}
	}
		
	@Override
	public String toString() {
		return "turn;";
	}
}
