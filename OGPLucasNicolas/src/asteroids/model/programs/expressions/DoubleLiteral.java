package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;
import be.kuleuven.cs.som.annotate.Value;

@Value
public class DoubleLiteral extends Literals<Double>{

	public DoubleLiteral(double value, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.value = value;
	}
	
	private final double value;

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public String toString() {
		return Double.toString(this.getValue());
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.DOUBLE;
	}

	public Double getValue() {
		return value;
	}
	
	@Override
	public Double getValue(Map<String, Expression<?>> variables) {
		return this.getValue();
	}

}
