package asteroids.model.programs.statements;

import java.util.Collections;
import java.util.List;
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
		//Statement originalBody = this.getBody();
		if ((Boolean)this.getCondition().getValue(variables)){
			this.getProgram().addToToDoListAtIndex(0,this.getBody());
			this.getProgram().addToToDoListAtIndex(0, null);
			System.out.println(this.getProgram().getToDoList());
//			this.getProgram().addToToDoListAtIndex(0,null);
//			if (this.getFirstTime()) {
//				this.getProgram().addToToDoListInSecond(this.getBody());
//				setFirstTime(false);
//			}else{
//				if (this.getBody() instanceof Sequence) {
//					List<Statement> statementsList = ((Sequence)this.getBody()).getStatementList();
//					List<Statement> shallowCopy = statementsList.subList(0, statementsList.size());
//					Collections.reverse(shallowCopy);
//					for (Statement statement : shallowCopy)
//						this.getProgram().addToToDoListInSecond(statement);
//				}else{ 
//					this.getProgram().addToToDoListInSecond(this.getBody());
//				}
//			}
		}else{
			super.executeStatement(variables);
		}
	}
			
			//for (Statement statement : ((Sequence)this.getBody()).getStatementList())
			
			//this.getProgram().addToToDoListInSecond(originalBody);
//			if (this.getBody() instanceof Sequence) {
//				int nbStatements = ((Sequence)this.getBody()).getStatementList().size();
//				//for (int pos = 1; pos < nbStatements; pos++) {
//				this.getProgram().getToDoList().add(null);
//				//}
//				this.getProgram().addToToDoListAtIndex(nbStatements-1,this);
//				this.getProgram().getToDoList().remove(nbStatements);
//			}else{
//				this.getProgram().addToToDoListAtIndex(2,this);
//			}
//			System.out.println(this.getProgram().getToDoList() + " STOP");
//			System.out.println(this.getFirstTime());
//			if (this.getFirstTime()) {
//				this.getProgram().addToToDoListInSecond(this.getBody());
//				//System.out.println("in FirstTime" + this.getBody() + "STOP");
//				setFirstTime(false);
//			}
			
			//System.out.println("In while " + this + " stop");
			//System.out.println("In while " + this.getBody() + " stop");
//			if (this.getBody() instanceof Sequence) {
//				List<Statement> statementsList = ((Sequence)this.getBody()).getStatementList();
//				List<Statement> shallowCopy = statementsList.subList(0, statementsList.size());
//				Collections.reverse(shallowCopy);
//				for (Statement statement : shallowCopy)
//					this.getProgram().addToToDoListInSecond(statement);
//			}else{
//				this.getProgram().addToToDoListInSecond(this.getBody());
//			}
	//System.out.println(this.getProgram().getToDoList());
	
}
