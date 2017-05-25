package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public class ReadVariable<T> extends Expression<T> {
	
	public ReadVariable(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.variableName = variableName;
	}
	
	public String getVariableName() {
		return this.variableName;
	}
	
	private String variableName;

	@SuppressWarnings("unchecked")
	@Override
	public T getValue(Map<String, Expression<?>> variables) {
		try{ T value = (T)this.getProgram().getLocalVarExpr(
				this.getProgram().getCurrentFunction(), this.getVariableName()).getValue(variables);
			return value;
		}catch (NullPointerException | IllegalArgumentException|AssertionError e) {
			T value= (T)variables.get(this.getVariableName()).getValue(variables);
			return value;
			}
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		assert (variables.get(getVariableName()) != null);
		if (variables.get(this.getVariableName()).getValue(variables) instanceof Boolean) {
			return Type.BOOL;
		}
		else if (variables.get(this.getVariableName()).getValue(variables) instanceof Double) {
			return Type.DOUBLE;
		}
		else {	
			return Type.ENTITY;
		}
	}

	@Override
	public String toString() {
		return this.getVariableName();
	}

}
