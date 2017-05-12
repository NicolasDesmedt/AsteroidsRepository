package asteroids.model.program.expressions;

import asteroids.part3.programs.SourceLocation;

public class Expression {

	public Expression(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}
	
	private final SourceLocation sourceLocation;
}
