package asteroids.model.programs.statements;

import java.util.ArrayList;
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
		this.getProgram().addAllToToDoListInSecond(this.getStatementsList());
	}
	
}
