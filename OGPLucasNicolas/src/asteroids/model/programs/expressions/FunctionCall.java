package asteroids.model.programs.expressions;

import java.util.List;
import java.util.Map;

import asteroids.model.programs.statements.Sequence;
import asteroids.model.programs.statements.Statement;
import asteroids.part3.programs.SourceLocation;

public class FunctionCall<T> extends Expression<T> {

	public FunctionCall(String functionName, List<Expression<?>> actualArgs, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.functionName = functionName;
		this.actualArgs = actualArgs;
	}
	
	public String getFunctionName() {
		return this.functionName;
	}
	
	private String functionName;
	
	public List<Expression<?>> getActualArgs(){
		return this.actualArgs;
	}
	
	private List<Expression<?>> actualArgs;

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public String toString() {
		return this.getFunctionName() + "(" +
				this.getActualArgs() + ")";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		T valueToCheck = this.getValue(variables);
		if (valueToCheck instanceof Boolean)
			return Type.BOOL;
		else if (valueToCheck instanceof Double)
			return Type.DOUBLE;
		else
			return Type.ENTITY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getValue(Map<String, Expression<?>> variables) {
		assert (this.getProgram().getFunctionsMap().containsKey(getFunctionName()));
		if (this.getActualArgs().isEmpty()) {
			this.getProgram().setExecutingStatementsInFunction(true);
			this.getProgram().setCurrentFunction(this.getFunctionName());
			Statement body = this.getProgram().getFunctionsMap().get(getFunctionName());
			body.setProgram(this.getProgram());
			if (body instanceof Sequence) {
				List<Statement> statementsList = ((Sequence)body).getStatementsList();
				for (Statement statement : statementsList) {
					statement.executeStatement(variables);
				}
			}
			else {
				body.executeStatement(variables);
			}
		}
		this.getProgram().getFunctionsResult().get(getFunctionName()).setProgram(getProgram());
		T result = (T)this.getProgram().getFunctionsResult().get(getFunctionName()).getValue(variables);
		this.getProgram().setExecutingStatementsInFunction(false);
		this.getProgram().setCurrentFunction(null);
		return result;
	}

}
