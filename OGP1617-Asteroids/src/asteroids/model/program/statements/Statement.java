package asteroids.model.program.statements;

import expressions.Expression;

/**
 * A class of statements.
 * 
 * @author Nicolas Desmedt & Lucas Desard
 */
public abstract class Statement implements Cloneable {
	
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
	public Statement clone() {
		try {
			if (isMutable())
				return (Statement) super.clone();
			else
				return this;
		} catch (CloneNotSupportedException exc) {
			throw new AssertionError(exc);
		}
	}

}
