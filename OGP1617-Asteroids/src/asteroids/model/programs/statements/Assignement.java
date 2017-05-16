package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class Assignement extends NoActionStatement {

	protected Assignement(SourceLocation sourceLocation, String var, Expression<? extends Object> value) {
		super(sourceLocation);
		this.var = var;
		this.value = value;
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

	@Override
	public void executeStatement() {
		// TODO Auto-generated method stub
		
	}
	
}
