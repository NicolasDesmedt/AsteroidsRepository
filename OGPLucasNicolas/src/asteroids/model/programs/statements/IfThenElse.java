package asteroids.model.programs.statements;

import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.Type;
import asteroids.part3.programs.SourceLocation;

public class IfThenElse extends NoActionStatement{

	public IfThenElse(Expression<Boolean> condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
	}
	
	public Statement getIfBody() {
		return this.ifBody;
	}
	
	private final Statement ifBody;
	
	public Expression<Boolean> getCondition() {
		return this.condition;
	}
	
	private final Expression<Boolean> condition;
	
	public Statement getElseBody() {
		return this.elseBody;
	}
	
	private final Statement elseBody;

	@Override
	public String toString() {
		String onlyIf =  "if " + this.getCondition().toString() + " { " + this.getIfBody().toString() + " }";
		String onlyElse = "";
		if (this.getElseBody() != null) {
			onlyElse = "else { " + this.getElseBody().toString() + "}";
		}
		String result = onlyIf + onlyElse;
		return result;
	}

	@Override
	public void executeStatement(Map<String, Expression<?>> variables) {
		assert this.getCondition().getType(variables) == Type.BOOL;
		connectExpression(this.getCondition());
		this.getIfBody().setProgram(this.getProgram());
		if (this.getElseBody() != null)
			this.getElseBody().setProgram(this.getProgram());
		
		if ((Boolean)this.getCondition().getValue(variables)) {
			this.getProgram().addToToDoListInSecond(this.getIfBody());
		}else{
			if (this.getElseBody() != null)
				this.getProgram().addToToDoListInSecond(this.getElseBody());
		}
	}

}
