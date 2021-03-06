package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public class ReadParameter<T> extends Expression<T> {

	public ReadParameter(String parameterName, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.parameterName = parameterName;
	}

	@SuppressWarnings("unused")
	private String parameterName;
	
	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getValue(Map<String, Expression<?>> variables) {
		// TODO Auto-generated method stub
		return null;
	}

}
