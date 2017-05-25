package asteroids.model.programs.functions;

import java.util.List;

import asteroids.model.Program;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.ActionStatement;
import asteroids.model.programs.statements.NoActionStatement;
import asteroids.model.programs.statements.Print;
import asteroids.model.programs.statements.Return;
import asteroids.model.programs.statements.Sequence;
import asteroids.model.programs.statements.Statement;
import asteroids.part3.programs.SourceLocation;

public class Function {
	
	public Function(String name, Statement body, SourceLocation sourceLocation) {
		this.name = name;
		this.body = body;
		this.sourceLocation = sourceLocation;
	}

	public String getName() {
		return name;
	}

	private final String name;
	
	public Statement getBody() {
		return body;
	}

	private final Statement body;
	
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}
	
	private final SourceLocation sourceLocation;
	
	@Override
	public String toString() {
		return "def " + this.getName() + 
				" { " + this.getBody().toString() + " } ";
	}
	
	public Program getProgram() {
		return this.program;
	}
	
	public void setProgram(Program program) {
		this.program = program;
	}
	
	private Program program;
	
	public Statement searchReturnStatement(Statement sequence) {
		Statement statementToReturn = null;
		List<Statement> statementsList = ((Sequence)sequence).getStatementsList();
		for (Statement statement : statementsList) {
			if (statement instanceof Return) {
				statementToReturn = statement;
			}
		}
		return statementToReturn;
	}

	public void evaluateFunction() {
		assert (!this.getProgram().getGlobals().containsKey(this.getName())): "1 Function";
		boolean noIllegalStatementInBlock = true;
		if (this.getBody() instanceof Sequence) {
			List<Statement> statementsList = ((Sequence)this.getBody()).getStatementsList();
			for (Statement statement : statementsList) {
				if ((statement instanceof ActionStatement) || (statement instanceof Print)){
					noIllegalStatementInBlock = false;
				}
			}
		}
		assert (!(this.getBody() instanceof ActionStatement)
				&& (noIllegalStatementInBlock) ): "2 Function";
		this.getProgram().addToFunctionsMap(this.getName(), this.getBody());
		if (this.getBody() instanceof Return) {
			((Return)this.getBody()).setFunctionName(this.getName());
		}
		else{
			assert (this.getBody() instanceof Sequence): "3 Function";
			Statement returnStatement = this.searchReturnStatement(getBody());
			((Return)returnStatement).setFunctionName(getName());
		}
	}

}
