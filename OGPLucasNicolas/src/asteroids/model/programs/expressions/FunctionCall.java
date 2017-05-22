package asteroids.model.programs.expressions;

import java.util.List;
import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public class FunctionCall<T> extends Expression<T> {

	public FunctionCall(String functionName, List<Expression> actualArgs, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.functionName = functionName;
		this.actualArgs = actualArgs;
	}
	
	public String getFunctionName() {
		return this.functionName;
	}
	
	private String functionName;
	
	public List<Expression> getActualArgs(){
		return this.actualArgs;
	}
	
	private List<Expression> actualArgs;

	@Override
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return this.getFunctionName() + "(" +
				this.getActualArgs() + ")";
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
