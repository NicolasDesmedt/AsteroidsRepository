package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;

public abstract class Expression<T> implements Cloneable {

	public Expression(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}
	
	private final SourceLocation sourceLocation;
	
	/**
	 * Check whether the state of this expression can be changed.
	 * 
	 * @note   This inspector is best avoided in languages fully supporting
	 *         multiple inheritance. Then, mutable expression will inherit
	 *         from an abstract class of mutable expressions introducing a.o.
	 *         a method setValue, and a preliminary definition of cloning.
	 *         An abstract class of immutable expressions might also be defined
	 *         then, introducing a final version of cloning.
	 */
	public abstract boolean isMutable();
	
	/**
	 * Return a clone of this expression.
	 * 
	 * @return The resulting expression is identical to this expression.
	 *       | result.isIdenticalTo(this)
	 * @return The resulting expression is the same as this expression
	 *         if and only if this expression is immutable.
	 *       | (result == this) == (! this.isMutable())
	 */
	@Override
	public Expression<?> clone() {
		try {
			if (isMutable())
				return (Expression<?>) super.clone();
			else
				return this;
		} catch (CloneNotSupportedException exc) {
			throw new AssertionError(exc);
		}
	}
	
	@Override
	public abstract String toString();
	
	public abstract Type getType(Map<String, Expression<?>> variables);
	
	public abstract T getValue(Map<String, Expression<?>> variables);
}
