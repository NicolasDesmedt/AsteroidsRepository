package asteroids.model.programs.statements;

import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.Type;
import asteroids.part3.programs.SourceLocation;

public class Assignment extends NoActionStatement {

	public Assignment(SourceLocation sourceLocation, String variableName, Expression<?> value) {
		super(sourceLocation);
		this.var = variableName;
		this.value = value;
	}
	
	public Expression<?> getValue() {
		return this.value;
	}
	
	private final Expression<?> value;
	
	public String getVar() {
		return this.var;
	}

	private final String var;
	
	@Override
	public String toString() {
		return this.getVar() + " := " + this.getValue().toString();
	}

	@Override
	public void executeStatement(Map<String, Expression<?>> variables) {
		Type typeCheck = null;
		if (variables.containsKey(this.getVar())) {
			Expression<?> value = variables.get(this.getVar());
			typeCheck = value.getType(variables);
			assert(this.getValue().getType(variables) == typeCheck); 
			variables.remove(this.getVar());
			variables.put(this.getVar(), this.getValue());
		}
		else{
			this.getProgram().getVariables().put(this.getVar(), this.getValue());
		}
		System.out.println("assignement uitgevoerd");
	}
	
}
