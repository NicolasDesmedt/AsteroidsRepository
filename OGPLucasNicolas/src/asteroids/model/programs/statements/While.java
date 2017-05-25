package asteroids.model.programs.statements;

import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.Type;
import asteroids.part3.programs.SourceLocation;

public class While extends NoActionStatement{

	public While(Expression<Boolean> condition, Statement body, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.body = body;
		this.condition = condition;
	}
	
	public Statement getBody() {
		return this.body;
	}
	
	private final Statement body;
	
	public Expression<Boolean> getCondition() {
		return this.condition;
	}
	
	private final Expression<Boolean> condition;

	@Override
	public String toString() {
		return "while " + getCondition().toString() + 
				" { " + getBody().toString() + "}";
	}
	
	public boolean getFirstTime() {
		return firstTime;
	}
	
	public void setFirstTime(boolean bool) {
		this.firstTime = bool;
	}
	
	private boolean firstTime = true;

	@Override
	public void executeStatement(Map<String, Expression<?>> variables) {
		assert this.getCondition().getType(variables) == Type.BOOL;
		this.getBody().setProgram(this.getProgram());
		if ((Boolean)this.getCondition().getValue(variables)){
			this.getProgram().addToToDoListAtIndex(0,this.getBody());
			this.getProgram().addToToDoListAtIndex(0, null);
		}else{
			return;
		}
	}
}
