package asteroids.model.program.statements;

import asteroids.part3.programs.SourceLocation;

public abstract class ActionStatement extends Statement {
	
	
	protected ActionStatement(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public Boolean canBeTimedOut() {
		return canBeTimedOut;
	}
	
	private Boolean canBeTimedOut = true;

}
