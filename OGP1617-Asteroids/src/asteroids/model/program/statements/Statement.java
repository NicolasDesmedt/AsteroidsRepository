package asteroids.model.program.statements;

import statements.statement;

/**
 * A class of statements.
 * 
 * @author Nicolas Desmedt & Lucas Desard
 */
public abstract class Statement implements Cloneable {
	
	/**
	 * Check whether the state of this statement can be changed.
	 * 
	 * @note   This inspector is best avoided in languages fully supporting
	 *         multiple inheritance. Then, mutable statement will inherit
	 *         from an abstract class of mutable statements introducing a.o.
	 *         a method setValue, and a preliminary definition of cloning.
	 *         An abstract class of immutable statements might also be defined
	 *         then, introducing a final version of cloning.
	 */
	public abstract boolean isMutable();
	
	/**
	 * Return a clone of this statement.
	 * 
	 * @return The resulting statement is identical to this statement.
	 *       | result.isIdenticalTo(this)
	 * @return The resulting statement is the same as this statement
	 *         if and only if this statement is immutable.
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
