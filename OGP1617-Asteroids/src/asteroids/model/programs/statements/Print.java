package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.Type;
import asteroids.part3.programs.SourceLocation;

public class Print extends NoActionStatement {

	protected Print(SourceLocation sourceLocation, Expression<? extends Object> toPrint) {
		super(sourceLocation);
		this.expression = toPrint;
	}

	public Expression<?> getExpression(){
		return expression;
	}
	
	private final Expression<?> expression;
	
	@Override
	public String toString() {
		return "print " + getExpression().toString() + ";";
	}

	@Override
	public void executeStatement() {
		if (this.getExpression().getType() == (Type.BOOL) || 
				this.getExpression().getType() == (Type.DOUBLE) )  {
			System.out.println(this.getExpression());
		}
		else{
			System.out.println(this.getExpression().toString());
		}
		
	}

}
