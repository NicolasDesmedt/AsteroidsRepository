package asteroids.model.programs.functions;

import java.util.List;

import asteroids.model.Program;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.Sequence;
import asteroids.model.programs.statements.Statement;
import asteroids.part3.programs.SourceLocation;

public class Function {
	
	public Function(String name, Statement body, SourceLocation sourceLocation) {
		this.name = name;
		this.body = body;
		this.sourceLocation = sourceLocation;
	}

	public String getName() {
		return name;
	}

	private final String name;
	
	public Statement getBody() {
		return body;
	}

	private final Statement body;
	
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}
	
	private final SourceLocation sourceLocation;
	
	@Override
	public String toString() {
		return "def " + this.getName() + 
				" { " + this.getBody().toString() + " } ";
	}
	
	public Program getProgram() {
		return this.program;
	}
	
	public void setProgram(Program program) {
		this.program = program;
	}
	
	private Program program;

	public void evaluateFunction() {
		this.getProgram().addToFunctionsMap(this.getName(), this.getBody());
	}

}
