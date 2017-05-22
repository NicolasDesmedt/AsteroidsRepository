package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class Return extends NoActionStatement {

	public Return(SourceLocation sourceLocation, Expression<?> expression) {
		super(sourceLocation);
		this.expression = expression;
	}
	
	private Expression<?> expression;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
