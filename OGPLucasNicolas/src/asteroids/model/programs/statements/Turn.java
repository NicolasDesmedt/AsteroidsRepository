package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class Turn extends ActionStatement {
		
	public Turn(Expression<?> angle, SourceLocation sourceLocation) {
		super(sourceLocation);
	}
		
//	@Override
//	public void executeStatement(v) {
//		super.executeStatement();
//		if (!this.getProgram().isPutOnHold()) {
//			//this.getShip().turn();
//		}
//	}
		
	@Override
	public String toString() {
		return "turn;";
	}
}
