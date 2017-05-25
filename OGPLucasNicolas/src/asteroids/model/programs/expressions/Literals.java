package asteroids.model.programs.expressions;

import asteroids.part3.programs.SourceLocation;

public abstract class Literals<T> extends Expression<T>{

	public Literals(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public boolean isMutable() {
		return false;
	}

}
