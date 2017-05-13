package asteroids.model.program.functions;

import java.util.List;

import asteroids.model.program.expressions.Expression;
import asteroids.model.program.statements.Statement;
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

	public void evaluateFunction() {
		// TODO Auto-generated method stub
		
	}

}
