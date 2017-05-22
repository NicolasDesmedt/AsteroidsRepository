package asteroids.model.programs;

import java.util.List;

import asteroids.part3.programs.IProgramFactory;
import asteroids.part3.programs.SourceLocation;
import asteroids.util.ModelException;
import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.programs.expressions.*;
import asteroids.model.programs.functions.*;
import asteroids.model.programs.statements.*;


public class ProgramFactory 
	implements IProgramFactory<Expression, Statement, Function, Program> {

	@Override
	public Program createProgram(List<Function> functions, Statement main) {
		return new Program(functions, main);
	}

	@Override
	public Function createFunctionDefinition(String functionName, Statement body, SourceLocation sourceLocation) {
		return new Function(functionName, body, sourceLocation);
		//return null;
	}

	@Override
	public Statement createAssignmentStatement(String variableName, Expression value, SourceLocation sourceLocation) {
		return new Assignment(sourceLocation, variableName, value);
	}

	@Override
	public Statement createWhileStatement(Expression condition, Statement body, SourceLocation sourceLocation) {
		return new While(condition, body, sourceLocation);
	}

	@Override
	public Statement createBreakStatement(SourceLocation sourceLocation) {
		return new Break(sourceLocation);
	}

	@Override
	public Statement createReturnStatement(Expression value, SourceLocation sourceLocation) {
		return new Return(sourceLocation, value);
	}

	@Override
	public Statement createIfStatement(Expression condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) {
		return new IfThenElse(condition, ifBody, elseBody, sourceLocation);
	}

	@Override
	public Statement createPrintStatement(Expression value, SourceLocation sourceLocation) {
		return new Print(sourceLocation, value);
	}

	@Override
	public Statement createSequenceStatement(List<Statement> statements, SourceLocation sourceLocation) {
		return new Sequence(statements, sourceLocation);
	}

	@Override
	public Expression<?> createReadVariableExpression(String variableName, SourceLocation sourceLocation) {
		return new ReadVariable<>(variableName, sourceLocation);
	}

	@Override
	public Expression<?> createReadParameterExpression(String parameterName, SourceLocation sourceLocation) {
		return new ReadParameter<>(parameterName, sourceLocation);
	}

	@Override
	public Expression createFunctionCallExpression(String functionName, List<Expression> actualArgs,
			SourceLocation sourceLocation) {
		return new FunctionCall<>(functionName, actualArgs, sourceLocation);
	}

	@Override
	public Expression<Double> createChangeSignExpression(Expression expression, SourceLocation sourceLocation) {
		return new ChangeSign(expression, sourceLocation);
	}

	@Override
	public Expression<Boolean> createNotExpression(Expression expression, SourceLocation sourceLocation) {
		return new Not(expression, sourceLocation);
	}

	@Override
	public Expression<Double> createDoubleLiteralExpression(double value, SourceLocation location) {
		return new DoubleLiteral(value, location);
	}

	@Override
	public Expression<Entity> createNullExpression(SourceLocation location) {
		return new Null(location);
	}

	@Override
	public Expression<Entity> createSelfExpression(SourceLocation location) {
		return new Self(location);
	}

	@Override
	public Expression<Entity> createShipExpression(SourceLocation location) {
		return new ShipExpression(location);
	}

	@Override
	public Expression<Entity> createAsteroidExpression(SourceLocation location) {
		return new AsteroidExpression(location);
	}

	@Override
	public Expression<Entity> createPlanetoidExpression(SourceLocation location) {
		return new PlanetoidExpression(location);
	}

	@Override
	public Expression<Entity> createBulletExpression(SourceLocation location) {
		return new BulletExpression(location);
	}

	@Override
	public Expression<Entity> createPlanetExpression(SourceLocation location) {
		return new PlanetExpression(location);
	}

	@Override
	public Expression<Entity> createAnyExpression(SourceLocation location) {
		return new Any(location);
	}

	@Override
	public Expression<Double> createGetXExpression(Expression e, SourceLocation location) {
		return new GetX(e, location);
	}

	@Override
	public Expression<Double> createGetYExpression(Expression e, SourceLocation location) {
		return new GetY(e, location);
	}

	@Override
	public Expression<Double> createGetVXExpression(Expression e, SourceLocation location) {
		return new GetVX(e, location);
	}

	@Override
	public Expression<Double> createGetVYExpression(Expression e, SourceLocation location) {
		return new GetVY(e, location);
	}

	@Override
	public Expression<Double> createGetRadiusExpression(Expression e, SourceLocation location) {
		return new GetRadius(e, location);
	}

	@Override
	public Expression<Boolean> createLessThanExpression(Expression e1, Expression e2, SourceLocation location) {
		return new LessThan(e1, e2, location);
	}

	@Override
	public Expression<Boolean> createEqualityExpression(Expression e1, Expression e2, SourceLocation location) {
		return new Equality(e1, e2, location);
	}

	@Override
	public Expression<Double> createAdditionExpression(Expression e1, Expression e2, SourceLocation location) {
		return new Addition(e1, e2, location);
	}

	@Override
	public Expression<Double> createMultiplicationExpression(Expression e1, Expression e2, SourceLocation location) {
		return new Multiplication(e1, e2, location);
	}

	@Override
	public Expression<Double> createSqrtExpression(Expression e, SourceLocation location) {
		return new Sqrt(e, location);
	}

	@Override
	public Expression<Double> createGetDirectionExpression(SourceLocation location) {
		return new GetDirection(location);
	}

	@Override
	public Statement createThrustOnStatement(SourceLocation location) {
		return new ThrustOn(location);
	}

	@Override
	public Statement createThrustOffStatement(SourceLocation location) {
		return new ThrustOff(location);
	}

	@Override
	public Statement createFireStatement(SourceLocation location) {
		return new Fire(location);
	}

	@Override
	public Statement createTurnStatement(Expression angle, SourceLocation location) {
		return new Turn(angle, location);
	}

	@Override
	public Statement createSkipStatement(SourceLocation location) {
		return new Skip(location);
	};

}
