package asteroids.model.programs.statements;

import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class Return extends NoActionStatement {

	public Return(SourceLocation sourceLocation, Expression<?> expression) {
		super(sourceLocation);
		this.expression = expression;
	}
	
	private Expression<?> expression;
	
	public Expression<?> getExpression() {
		return this.expression;
	}
	
	@Override
	public String toString() {
		return "return " + getExpression().toString() + ";";
	}
	
	public String getFunctionName() {
		return this.functionName;
	}
	
	private String functionName;
	
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	@Override
	public void executeStatement(Map<String, Expression<?>> variables) {
		assert (this.getFunctionName() != null);
		this.getProgram().addToFunctionsReturn(this.getFunctionName(), this.getExpression());
	}

}
