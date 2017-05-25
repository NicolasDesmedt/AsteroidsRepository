package asteroids.model.programs.statements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class Sequence extends Statement {

	public Sequence(List<Statement> statements, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.statementsList = statements;
	}
	
	public List<Statement> getStatementsList() {
		return this.statementsList;
	}

	private List<Statement> statementsList = new ArrayList<>();
	
	@Override
	public String toString() {
		String string="";
		for (Statement statement : statementsList)
			string += statement.toString() + "\n";
		return string;
	}
	@Override
	public void executeStatement(Map<String, Expression<?>> variables) {
		//super.executeStatement(variables);
		//this.getProgram().getToDoList().addAll(1, this.getStatementsList());
		this.getProgram().addAllToToDoListInSecond(this.getStatementsList());
//		List<Statement> statementsList = this.getStatementList();
//		List<Statement> shallowCopy = statementsList.subList(0, statementsList.size());
//		Collections.reverse(shallowCopy);
//		for (Statement statement : shallowCopy)
//			this.getProgram().addToToDoListInSecond(statement);
//		super.executeStatement(variables);
//		for (Statement statement : statementsList) {
//			if (!this.getProgram().isPutOnHold()) {
//				statement.executeStatement(variables);
//				statementsList.remove(statement);
//			}
//		}
//		if (this.getProgram().isPutOnHold()) {
//			List<Statement> shallowCopy = statementsList.subList(0, statementsList.size());
//			Collections.reverse(shallowCopy);
//			for (Statement statement : shallowCopy)
//				this.getProgram().addToToDoListInFront(statement);
//		}
//		
	}
	
}
