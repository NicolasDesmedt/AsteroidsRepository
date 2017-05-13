package asteroids.model.program.statements;

import asteroids.model.program.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class Assignement extends NoActionStatement {

	protected Assignement(SourceLocation sourceLocation, String var, Expression<? extends Object> value) {
		super(sourceLocation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
