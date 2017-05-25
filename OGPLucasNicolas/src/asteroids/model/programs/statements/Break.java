package asteroids.model.programs.statements;

import java.util.List;
import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class Break extends NoActionStatement {

	public Break(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public String toString() {
		return "break;";
	}
	
	public boolean getWhileIsFound() {
		return this.whileIsFound;
	}
	
	public void setWhileIsFound(boolean bool) {
		this.whileIsFound = bool;
	}
	
	private boolean whileIsFound = false;
	
	@Override
	public void executeStatement(Map<String, Expression<?>> variables) {
		List<Statement> toDoList = this.getProgram().getToDoList();
		int nbStatements = toDoList.size();
		int i = 0;
		assert (nbStatements>1);
		for (int pos = 1; pos <= nbStatements; pos++) {
			if (! getWhileIsFound()) {
				if (this.getProgram().getStatementAt(pos, toDoList) instanceof While) {
					setWhileIsFound(true);
					i += 1;
				}else{
					i += 1;
				}
			}
		}
		assert (getWhileIsFound() == true);
		for (int pos = 1; pos <= i; pos++) {
			this.getProgram().removeFromToDoList(1);
		}
	}

}
