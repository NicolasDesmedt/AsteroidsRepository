package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public class BooleanLiteral extends Literals<Boolean>{

	public BooleanLiteral(boolean bool, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.bool = bool;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public String toString() {
		return Boolean.toString(this.getValue());
	}

	public boolean getValue() {
		return this.bool;
	}
	
	private final boolean bool;

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.BOOL;
	}

	@Override
	public Boolean getValue(Map<String, Expression<?>> variables) {
		return this.getValue();
	}

}
