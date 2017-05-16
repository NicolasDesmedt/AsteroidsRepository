package asteroids.model.programs.statements;

import asteroids.model.Ship;
import asteroids.model.programs.Program;
import asteroids.part3.programs.SourceLocation;

/**
 * A class of statements.
 * 
 * @author Nicolas Desmedt & Lucas Desard
 */
public abstract class Statement implements Cloneable {
	
	protected Statement(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
	private final SourceLocation sourceLocation;

	public Program getProgram() {
		return this.program;
	}
	
	public void setProgram(Program program) {
		this.program = program;
	}
	
	private Program program;

	public Ship getShip() {
		return this.getProgram().getShip();
	}
	
	public abstract Boolean canBeExecuted();
	
	@Override
	public abstract String toString();

	public abstract void executeStatement();
	

}
