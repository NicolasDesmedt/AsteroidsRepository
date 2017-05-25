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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return this.getFunctionName() + "(" +
				this.getActualArgs() + ")";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getValue(Map<String, Expression<?>> variables) {
		assert (this.getShip().getProgram().getFunctionsMap().containsKey(getFunctionName())): "1 FunctionCall";
		if (this.getActualArgs().isEmpty()) {
//			this.getShip().getProgram().addToToDoListInSecond(
//					this.getShip().getProgram().getFunctionsMap().get(getFunctionName()));
//		}
			this.getShip().getProgram().setExecutingStatementsInFunction(true);
			this.getShip().getProgram().setCurrentFunction(this.getFunctionName());
			Statement body = this.getShip().getProgram().getFunctionsMap().get(getFunctionName());
			body.setProgram(this.getShip().getProgram());
			if (body instanceof Sequence) {
				List<Statement> statementsList = ((Sequence)body).getStatementsList();
				for (Statement statement : statementsList) {
					statement.executeStatement(variables);
				}
			}
			else {
				body.executeStatement(variables);
			}
			this.getShip().getProgram().setExecutingStatementsInFunction(false);
		}
		this.getShip().getProgram().getFunctionsReturn().get(getFunctionName()).setProgram(getProgram());
		return (T)this.getShip().getProgram().getFunctionsReturn().get(getFunctionName()).getValue(variables);
	}

}
