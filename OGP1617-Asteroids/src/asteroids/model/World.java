package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * 
 * @author Nicolas
 *
 */
public class World{
	
	/**
	 * 
	 * @param width
	 * @param height
	 */
	public World(double width, double height) {
		if (! ((width >= 0) && (width <= upperBound)) )
			width = upperBound;
		if (! ((height >= 0) && (height <= upperBound)) )
			height = upperBound;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 
	 * @return
	 */
	@Basic @Immutable
	public double getUpperBound() {
		return World.upperBound;
	}
	
	/**
	 * 
	 */
	private static final double upperBound = Double.MAX_VALUE;
	
	/**
	 * 
	 */
	private final double width;
	
	/**
	 * 
	 */
	private final double height;
}
