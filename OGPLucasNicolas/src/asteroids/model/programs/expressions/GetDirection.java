package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public class GetDirection extends Expression<Double> {

	public GetDirection(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public String toString() {
		return "getdirection";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.DOUBLE;
	}

	@Override
	public Double getValue(Map<String, Expression<?>> variables) {
		return this.getShip().getOrientation();
	}

}