package asteroids.model.program.statements;

import asteroids.part3.programs.SourceLocation;

public abstract class NoActionStatement extends Statement {

	protected NoActionStatement(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	public Boolean canBeTimedOut() {
		return canBeTimedOut;
	}
	
	private Boolean canBeTimedOut = false;

}
