package asteroids.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import asteroids.part2.CollisionListener;
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
	
	public double getWorldWidth() {
		return this.width;
	}
	
	public double getWorldHeight() {
		return this.height;
	}
	
	public double[] getWorldSize() {
		double[] worldSize = {getWorldWidth(),getWorldHeight()};
		return worldSize;
	}
	
	/**
	 * Terminate this world.
	 *
	 * @post   This world  is terminated.
	 *       | new.isTerminated()
	 * @post   ...
	 *       | ...
	 */
	 public void terminate() {
		 posEntities.clear();
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
	public double getDistanceToNearestHorizontalBoundary(Entity entity) throws IllegalArgumentException {
		if (! withinBoundaries(entity)) throw new IllegalArgumentException("The entity is not located in the world.");
		double distance = Double.POSITIVE_INFINITY;
		double distanceUp = height - entity.getPositionY();
		double distanceDown = entity.getPositionY();
		if (distanceUp < distanceDown)
			distance = distanceUp;
		else
			distance = distanceDown;
		return distance;
	}
	
	public double getDistanceToNearestVerticalBoundary(Entity entity) {
		double distance = Double.POSITIVE_INFINITY;
		double distanceLeft = width - entity.getPositionX();
		double distanceRight = entity.getPositionX();
		if (distanceLeft < distanceRight)
			distance = distanceLeft;
		else
			distance = distanceRight;
		return distance;
	}
	
	public double getDistanceToBoundaries(Entity entity) {
		if (getDistanceToNearestVerticalBoundary(entity) <
				getDistanceToNearestHorizontalBoundary(entity))
			return getDistanceToNearestVerticalBoundary(entity);
		else
			return getDistanceToNearestHorizontalBoundary(entity);
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

	public void addEntity(Entity entity) throws IllegalArgumentException {
		//posEntities.put(entity.getPosition(), entity);
		if (!overlapBoundaries(entity) && canContain(entity) &&
			!significantOverlapAllEntities(entity)) {
			posEntities.put(entity.getPosition(), entity);
			entity.setWorld(this);
		}
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
	
	
	public double getTimeToCollisionEntityWithVerticalBoundary(Entity entity) {
		double time = Double.POSITIVE_INFINITY;
		double distance = Double.POSITIVE_INFINITY;
		if (entity.getVelocityX() == 0)
			return time;
		else if (this.overlapBoundaries(entity))
			return 0;
		else {
			if (entity.getVelocityX() < 0)
				distance = entity.getPositionX() - entity.getRadius();
			else 
				distance = width - entity.getPositionX() - entity.getRadius();
			return time = Math.abs(distance/entity.getVelocityX());
		}
	}
	
	public double getTimeToCollisionEntityWithHorizontalBoundary(Entity entity) {
		double time = Double.POSITIVE_INFINITY;
		double distance = Double.POSITIVE_INFINITY;
		if (entity.getVelocityY() == 0)
			return time;
		else if (this.overlapBoundaries(entity))
			return 0;
		else {
			if (entity.getVelocityY() < 0)
				distance = entity.getPositionY() - entity.getRadius();
			else 
				distance = height - entity.getPositionY() - entity.getRadius();
			return time = Math.abs(distance/entity.getVelocityY());
		}
	}
	
	public double getTimeToCollisionEntityWithBoundary(Entity entity) { 
		double verticalTime = getTimeToCollisionEntityWithVerticalBoundary(entity);
		double horizontalTime = getTimeToCollisionEntityWithHorizontalBoundary(entity);
		if (verticalTime < horizontalTime)
			return verticalTime;	
		else
			return horizontalTime;
	}
	
	/**
	 * iets toevoegen voor al aan het botsen
	 * @param entity
	 * @return
	 */
	public double[] getPositionCollisionWithBoundary(Entity entity) {
		double xPos = Double.POSITIVE_INFINITY;
		double yPos = Double.POSITIVE_INFINITY;
		double verticalTime = getTimeToCollisionEntityWithVerticalBoundary(entity);
		double horizontalTime = getTimeToCollisionEntityWithHorizontalBoundary(entity);
		if (horizontalTime < verticalTime) {
			if (entity.getVelocityY() < 0)
				yPos = 0;
			else
				yPos = height;
			xPos = entity.getPositionX() + entity.getVelocityX()*verticalTime;
		}
		else {
			if (entity.getVelocityX() < 0)
				xPos = 0;
			else
				xPos = width;
			yPos = entity.getPositionY() + entity.getVelocityY()*verticalTime;
		}
		double[] position = {xPos, yPos};
		return position;
	}
	
	public double getTimeToFirstCollisionEntity(Entity entity) {
		double time = getTimeToCollisionEntityWithBoundary(entity);
		HashSet<Entity> allEntities = getAllEntities();
		for (Entity other : allEntities) {
			if (entity.getTimeToCollision(other) < time)
				time = entity.getTimeToCollision(other);
		}
		return time;	
	}
	
	public double[] getPositionEntityUponCollision(Entity entity) {
		double xPos = Double.POSITIVE_INFINITY;
		double yPos = Double.POSITIVE_INFINITY;
		double time = getTimeToFirstCollisionEntity(entity);
		if (time < Double.POSITIVE_INFINITY) { 
			xPos = xPos + entity.getVelocityX()*time;
			yPos = yPos + entity.getVelocityY()*time;
		}
		double[] position = {xPos, yPos};
		return position;
	}
	
	public double getTimeToFirstCollision() {
		double time = Double.POSITIVE_INFINITY;
		HashSet<Entity> allEntities = getAllEntities();
		for (Entity entity : allEntities) {
			if (getTimeToFirstCollisionEntity(entity) < time)
				time = getTimeToFirstCollisionEntity(entity);
		}
		return time;
	}
	
	public HashSet<Entity> getFirstCollidingEntities() {
		HashSet<Entity> firstCollidingEntities = new HashSet<>();
		double timeToCollision = getTimeToFirstCollision();
		HashSet<Entity> allEntities = getAllEntities();
		for (Entity entity : allEntities) {
			if (getTimeToFirstCollisionEntity(entity) == timeToCollision) {
				firstCollidingEntities.add(entity);								// Na 2 gevonden entities stoppen? (Bij HashTable van meerdere gelijklopende botsingen uitgegaan)
			}
		}
		return firstCollidingEntities;
	}
	
	public double[] getPositionFirstCollision() {
		HashSet<Entity> firstCollidingEntities = getFirstCollidingEntities();
		if (firstCollidingEntities.size() > 2)
			throw new IllegalStateException("Multiple collisions occur at once, there is no single 'position of first collision'");
		if (firstCollidingEntities.size() == 0)
			throw new IllegalStateException("There are no colliding entities");
		Entity entity1 = null;
		Entity entity2 = null;
		for (Entity entity : firstCollidingEntities) {
			if (entity1 == null)
				entity1 = entity;
			else 
				entity2 = entity;
		}
		if (entity2 == null)
			return this.getPositionCollisionWithBoundary(entity2);
		else
			return entity1.getCollisionPosition(entity2);
	}
	
	public Hashtable<Entity, Entity> getCollidingEntities() {
		HashSet<Entity> allEntities = getAllEntities();
		HashSet<Entity> otherEntities = getAllEntities();
		for (Entity entity : allEntities) {
			otherEntities.remove(entity);
			for (Entity other : otherEntities) {
				if (entity.apparentlyCollide(other))
					collidingEntities.put(entity, other);
					allEntities.remove(other);
			if (overlapBoundaries(entity))
				collidingEntities.put(entity, null);
			}
		}
		return collidingEntities;
	}
	
	private Hashtable<Entity, Entity> collidingEntities = new Hashtable<Entity, Entity>();
	
	public void resolveCollisions() {
		Set<Entity> keys = getCollidingEntities().keySet();
		Iterator<Entity> iterator = keys.iterator();
		while (iterator.hasNext()) {
			Entity entity = iterator.next();
			Entity other = getCollidingEntities().get(entity);
			if (other == null)
				if (entity instanceof Ship)
					((Ship)entity).collidesWithBoundary(this);
				else
					((Bullet)entity).collidesWithBoundary(this);
			else if ( (entity instanceof Ship) && (other instanceof Ship) )
					((Ship)entity).collidesWithShip((Ship)other);
			else if ( (entity instanceof Ship) && (other instanceof Bullet) )
					((Ship)entity).getsHitBy((Bullet)other);
			else if ( (entity instanceof Bullet) && (other instanceof Ship) )
					((Ship)other).getsHitBy((Bullet)entity);
			else
				((Bullet)entity).cancelsOut((Bullet)other);
		}
	}
	
	public void updateCollisionListener(CollisionListener collisionListener) throws IllegalStateException {
		double xPos = getPositionFirstCollision()[0];
		double yPos = getPositionFirstCollision()[1];
		HashSet<Entity> firstCollidingEntities = getFirstCollidingEntities();
		if (firstCollidingEntities.size() > 2)
			throw new IllegalStateException("Multiple collisions occur at once, there is no single 'position of first collision'");
		if (firstCollidingEntities.size() == 0)
			throw new IllegalStateException("There are no colliding entities");
		Entity entity1 = null;
		Entity entity2 = null;
		for (Entity entity : firstCollidingEntities) {
			if (entity1 == null)
				entity1 = entity;
			else 
				entity2 = entity;
		}
		if (entity2 == null)
			collisionListener.boundaryCollision(entity1, xPos, yPos);
		else
			collisionListener.objectCollision(entity1, entity2, xPos, yPos);
	}
	
	/**
	 * no specification, defensive
	 * @param dt
	 */
	public void evolve(double dt, CollisionListener collisionListener) {
		HashSet<Entity> allEntities = getAllEntities();
		double time = getTimeToFirstCollision();
		if (time < dt) {
			for (Entity entity : allEntities) {
				entity.move(time);
			}
			//RESOLVE
			if (collisionListener != null)
				updateCollisionListener(collisionListener);
			resolveCollisions();
			//NEXT
			double newTime = dt - time;
			evolve(newTime, collisionListener);
		}
		else
			for (Entity entity : allEntities) {
				entity.move(dt);
			}
	}
	
}
