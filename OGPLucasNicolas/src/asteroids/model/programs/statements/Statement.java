package asteroids.model.programs.statements;

import java.util.List;
import java.util.Map;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.programs.expressions.BinaryExpression;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.UnaryExpression;
import asteroids.part3.programs.SourceLocation;

/**
 * A class of statements.
 * 
 * @author Nicolas Desmedt & Lucas Desard
 */
public abstract class Statement implements Cloneable {
	
	protected Statement(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
	private final SourceLocation sourceLocation;

	public Program getProgram() {
		return this.program;
	}
	
	public void setProgram(Program program) {
		this.program = program;
		if (this instanceof Sequence) {
			List<Statement> statementsList = ((Sequence)this).getStatementsList();
			for (Statement statement : statementsList) {
				statement.setProgram(program);
			}
		}
	}
	
	private Program program;

	public Ship getShip() {
		return this.getProgram().getShip();
	}
	
	@Override
	public abstract String toString();

	public abstract void executeStatement(Map<String, Expression<?>> variables);
	
	public void connectExpression(Expression<?> expression) {
		expression.setProgram(this.getProgram());
		expression.setShip(this.getShip());
		expression.setStatement(this);
		if (expression instanceof UnaryExpression){
			connectExpression(((UnaryExpression<?>)expression).getExpression());
		}
		if (expression instanceof BinaryExpression){
			connectExpression(((BinaryExpression<?>)expression).getExpressionLhs());
			connectExpression(((BinaryExpression<?>)expression).getExpressionRhs());
		}
	}

}
