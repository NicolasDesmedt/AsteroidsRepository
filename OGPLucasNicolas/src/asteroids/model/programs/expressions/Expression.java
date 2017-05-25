package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.programs.statements.Statement;
import asteroids.part3.programs.SourceLocation;

public abstract class Expression<T> implements Cloneable {

	public Expression(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}
	
	private final SourceLocation sourceLocation;
	
	public abstract boolean isMutable();
	
	@Override
	public Expression<?> clone() {
		try {
			if (isMutable())
				return (Expression<?>) super.clone();
			else
				return this;
		} catch (CloneNotSupportedException exc) {
			throw new AssertionError(exc);
		}
	}

	public Program getProgram() {
		return this.program;
	}
	
	public void setProgram(Program program) {
		this.program = program;
	}
	
	private Program program;
	
	public Ship getShip() {
		return this.ship;
	}
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	private Ship ship;
	
	public Statement getStatement() {
		return this.statement;
	}
	
	public void setStatement(Statement statement) {
		this.statement = statement;
		
	}
	
	private Statement statement;
	
	@Override
	public abstract String toString();
	
	public abstract Type getType(Map<String, Expression<?>> variables);
	
	public abstract T getValue(Map<String, Expression<?>> variables);
}
