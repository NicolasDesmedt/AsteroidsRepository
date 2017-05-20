package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public class Null extends ReadVariable<Entity>{

	public Null(SourceLocation sourceLocation) {
		super("null", sourceLocation);
	}
	
	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public String toString() {
		return "null";
	}

	@Override
	public Entity getValue(Map<String,Expression<?>> variables) {
		return null;
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.ENTITY;
	}

}
