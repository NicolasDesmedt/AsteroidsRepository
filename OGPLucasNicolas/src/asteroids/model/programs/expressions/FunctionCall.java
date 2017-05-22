package asteroids.model.programs.expressions;

import java.util.List;
import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public class FunctionCall<T> extends Expression<T> {

	public FunctionCall(String functionName, List<Expression> actualArgs, SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getType(Map variables) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getValue(Map variables) {
		// TODO Auto-generated method stub
		return null;
	}

}
