package asteroids.model.programs.statements;

import asteroids.part3.programs.SourceLocation;

public class Skip extends ActionStatement {

	public Skip(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "skip;";
	}

}
