package asteroids.model;

import java.util.HashMap;

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
	
	/**
	 * A hashmap containing all the entities in the world with their position.
	 */
	private HashMap<double[], Entity> posEntities = new HashMap<>();
	
	/**
	 * 
	 */
	public boolean withinBoundaries(Entity entity) {
		return entity.getPositionX() <= width && entity.getPositionX() >= 0 &&
				entity.getPositionY() <= height && entity.getPositionY() >= 0;
	}
	
	/**
	 * 
	 */
	public double getDistanceToBoundaries(Entity entity) throws IllegalArgumentException {
		if (! withinBoundaries(entity)) throw new IllegalArgumentException("The entity does not find fit in the world.");
		double distance = Math.abs(width - entity.getPositionX());
		if (Math.abs(height - entity.getPositionY()) > distance)
			distance = Math.abs(height - entity.getPositionY());
		return distance;
	}
	
	/**
	 * Returns true if and only if the entity fits in the world, is not a duplicate, does not overlap.
	 * @param entity
	 * @return
	 */
	public boolean canContain(Entity entity) {
		if (entity.getWorld() != null) throw new IllegalArgumentException();
		double yCoord = entity.getPositionY();
		double xCoord = entity.getPositionX();
		double radius = entity.getRadius();
		
	}
	
	/**
	 * Add entity to this world.
	 */
	public void addEntity(Entity entity) {
		
	}
}
