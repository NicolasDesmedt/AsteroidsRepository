package asteroids.model.programs.expressions;

import java.util.List;
import java.util.Map;

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
		if (this.getActualArgs().isEmpty()) {
//			this.getShip().getProgram().addToToDoListInSecond(
//					this.getShip().getProgram().getFunctionsMap().get(getFunctionName()));
//		}
			this.getShip().getProgram().setExecutingStatementsInFunction(true);
			Statement body = this.getShip().getProgram().getFunctionsMap().get(getFunctionName());
			body.setProgram(this.getShip().getProgram());
			body.executeStatement(variables);
			this.getShip().getProgram().setExecutingStatementsInFunction(false);
		}
		System.out.println(this.getShip().getProgram().getFunctionsReturn());
		System.out.println(this.getShip().getProgram().getFunctionsReturn().get(getFunctionName()));
		return (T)this.getShip().getProgram().getFunctionsReturn().get(getFunctionName()).getValue(variables);
	}

}
