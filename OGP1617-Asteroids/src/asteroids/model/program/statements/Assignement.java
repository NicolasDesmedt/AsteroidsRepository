package asteroids.model.program.statements;

import asteroids.model.program.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class Assignement extends NoActionStatement {

	protected Assignement(SourceLocation sourceLocation, String var, Expression<? extends Object> value) {
		super(sourceLocation);
		this.var = var;
		this.value = value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}
	
	public Expression<? extends Object> getValue() {
		return this.value;
	}
	
	private final Expression<? extends Object> value;
	
	public String getVar() {
		return this.var;
	}

	private final String var;
	
	@Override
	public String toString() {
		return this.getVar() + " := " + this.getValue();
	}
	
}
