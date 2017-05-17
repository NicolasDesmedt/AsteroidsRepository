package asteroids.model.programs.statements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import asteroids.part3.programs.SourceLocation;

public class Sequence extends Statement {

	protected Sequence(List<Statement> statements, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.statementsList = statements;
	}
	
	public List<Statement> getStatementList() {
		return this.statementsList;
	}

	private List<Statement> statementsList = new ArrayList<>();
	
	@Override
	public String toString() {
		String toPrint="";
		for (Statement statement : statementsList)
			toPrint += statement.toString() + "\n";
		return toPrint;
	}
	@Override
	public void executeStatement() {
		for (Statement statement : statementsList) {
			if (!this.getProgram().isPutOnHold()) {
				statement.executeStatement();
				statementsList.remove(statement);
			}
		}
		if (this.getProgram().isPutOnHold()) {
			List<Statement> shallowCopy = statementsList.subList(0, statementsList.size());
			Collections.reverse(shallowCopy);
			for (Statement statement : shallowCopy)
				this.getProgram().addToToDoListInFront(statement);
		}
		
	}
	
}
