package asteroids.model.programs.statements;

import asteroids.part3.programs.SourceLocation;

public abstract class NoActionStatement extends Statement {

	protected NoActionStatement(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public Boolean canBeExecuted() {
		return true;
	}

}
