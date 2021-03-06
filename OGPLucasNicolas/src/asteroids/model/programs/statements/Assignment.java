package asteroids.model.programs.statements;

import java.util.Map;

import asteroids.model.programs.expressions.BooleanLiteral;
import asteroids.model.programs.expressions.DoubleLiteral;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.Null;
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
		connectExpression(this.getValue());
		Expression<?> value = null;
		Type typeCheck = null;
		if (variables.containsKey(this.getVar())) {
			value = variables.get(this.getVar());
			typeCheck = value.getType(variables);
			assert(this.getValue().getType(variables) == typeCheck);
		}

		if (getValue().getType(variables)==Type.BOOL) {
			value=new BooleanLiteral((Boolean) getValue().getValue(variables), getValue().getSourceLocation());
		}
		else if (getValue().getType(variables)==Type.DOUBLE) {
			value=new DoubleLiteral((Double) getValue().getValue(variables), getValue().getSourceLocation());
		}else{	
			value=new Null(getValue().getSourceLocation());
		}
		
		this.getProgram().removeFromVariables(getVar());
		this.getProgram().addToVariables(getVar(), value);
		if (!this.getProgram().executingStatementsInFunction()) {
			this.getProgram().addToGlobals(getVar(), value);
		}else{
			this.getProgram().addToLocals(this.getProgram().getCurrentFunction(), getVar(), value);
		}
	}
	
}
