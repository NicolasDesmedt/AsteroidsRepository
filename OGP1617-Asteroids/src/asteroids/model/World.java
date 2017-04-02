package asteroids.model;

import java.util.HashMap;
import java.util.HashSet;

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
	 * Terminate this world.
	 *
	 * @post   This world  is terminated.
	 *       | new.isTerminated()
	 * @post   ...
	 *       | ...
	 */
	 public void terminate() {
		 this.isTerminated = true;
	 }
	 
	 /**
	  * Return a boolean indicating whether or not this world
	  * is terminated.
	  */
	 @Basic @Raw
	 public boolean isTerminated() {
		 return this.isTerminated;
	 }
	 
	 /**
	  * Variable registering whether this world is terminated.
	  */
	 private boolean isTerminated = false;
	 
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
		if (! withinBoundaries(entity)) throw new IllegalArgumentException("The entity is not located in the world.");
		double distance = Math.abs(width - entity.getPositionX());
		if (Math.abs(height - entity.getPositionY()) < distance)
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
		else
			return true;
	}
	
	public boolean significantOverlap(Entity entity, Entity other) {
		double radiusEntity = entity.getRadius();
		double radiusOther = other.getRadius();
		if (entity.getDistanceBetween(other) <= 0.99*(radiusEntity + radiusOther))
			return true;
		else
			return false;
	}
	
	public boolean overlapBoundaries(Entity entity) {
		double radius = entity.getRadius();
		if (getDistanceToBoundaries(entity) < 0.99*radius)
			return true;
		else 
			return false;
	}
	
	public boolean significantOverlapAllEntities(Entity entity) {
		for (Entity other : posEntities.values()) {
			if (significantOverlap(entity, other))
				return true;
		}
		return false;
			
	}
	
	/**
	 * Add entity to this world. (Defensive)
	 */
	public void addEntity(Entity entity) {
		if (!overlapBoundaries(entity) && canContain(entity) &&
			!significantOverlapAllEntities(entity))
			posEntities.put(entity.getPosition(), entity);
		else throw new IllegalArgumentException("This world can not contain the given entity.");
	}	
	
	/**
	 * Defensive
	 * @param entity
	 */
	public void removeEntity(Entity entity) {
		if (posEntities.containsValue(entity))
			posEntities.remove(entity.getPosition());
		else throw new IllegalArgumentException("This world does not contain the given entity.");
	}
	
	/**
	 * Total
	 * @param x
	 * @param y
	 * @return
	 */
	public Entity getEntityAt(double x, double y) {
		double[] position = {x,y};
		//if (posEntities.containsKey(position)) want returnt null als positie niets inhoudt
		return posEntities.get(position);
	}
	
	public HashSet<Entity> getAllEntities() {
		return new HashSet<Entity>(posEntities.values());
	}
	
	public HashSet<Ship> getAllShips() {
		HashSet<Entity> allEntities = getAllEntities();
		HashSet<Ship> allShips = new HashSet<Ship>();
		for (Entity entity : allEntities)
			if (entity instanceof Ship)
				allShips.add((Ship)entity);
		return allShips;
	}
	
	public HashSet<Bullet> getAllBullets() {
		HashSet<Entity> allEntities = getAllEntities();
		HashSet<Bullet> allBullets = new HashSet<Bullet>();
		for (Entity entity : allEntities)
			if (entity instanceof Bullet)
				allBullets.add((Bullet)entity);
		return allBullets;
	}
	
	/**
	 * A hashmap containing all the entities in the world with their position.
	 */
	private HashMap<double[], Entity> posEntities = new HashMap<>();
	
	public void getFirstCollision
	
	/**
	 * no specification, defensive
	 * @param dt
	 */
	public void evolve(double dt) {
		getFirstCollision
		
	}
}