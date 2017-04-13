package asteroids.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;


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
	public World(double width, double height){
		if (((width < 0) || (width > upperBound)) && ((height < 0) || (height > upperBound))){
			this.width = upperBound;
			this.height = upperBound;
		}else if((width < 0) || (width > upperBound)){
			this.width = upperBound;
			this.height = height;
		}else if((width < 0) || (width > upperBound)){
			this.width = width;
			this.height = upperBound;
		}else{
		this.width = width;
		this.height = height;
		}
		
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
		double[] worldSize = {this.getWorldWidth(),this.getWorldHeight()};
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
		 HashSet<Entity> allEntities = this.getAllEntities();
		 for (Entity entity : allEntities)
			 entity.removeFromWorld();
		 positionsAndEntities.clear();
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
		double xPos = entity.getPositionX();
		double yPos = entity.getPositionY();
		double significantEntityRadius = 0.99*entity.getRadius();
		return ((xPos+significantEntityRadius) <= width && (xPos-significantEntityRadius) >= 0 && (yPos+significantEntityRadius) <= height && (yPos-significantEntityRadius) >= 0);
	}
	
	/**
	 * 
	 */
	public double getDistanceToNearestHorizontalBoundary(Entity entity) throws IllegalArgumentException {
		if (!withinBoundaries(entity)) throw new IllegalArgumentException("The entity is not located in the world.");
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
	
	public boolean overlapsWithOtherEntities(Entity entity) {
		HashSet<Entity> allEntities = getAllEntities();
		for (Entity other : allEntities) {
			if (entity.overlap(other)){
				if ((entity instanceof Ship) && (other instanceof Bullet) && (((Bullet)other).getShip() == entity)){
					
				}else if ((entity instanceof Bullet) && (other instanceof Ship) && (((Bullet)entity).getShip() == other)){
					
				}else if ((entity instanceof Bullet) && (other instanceof Bullet) && (((Bullet)entity).getShip() == ((Bullet)other).getShip())){
				
				}else{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Add entity to this world. (Defensive)
	 */

	public void addEntity(Entity entity) throws IllegalArgumentException {
		if (positionsAndEntities.values().contains(entity))throw new IllegalArgumentException
		("The given entity already belongs to this world.");
		if (entity.getWorld() != null) throw new IllegalArgumentException
			("The given entity already belongs to a world.");
		if (!this.withinBoundaries(entity)) throw new IllegalArgumentException
			("This entity doesn't lie fully within the bounds of that world.");
		if (this.overlapsWithOtherEntities(entity)) throw new IllegalArgumentException
			("The entity overlaps with other entities that is shouldn't overlap with");
		
		positionsAndEntities.put(entity.getPosition(), entity);
		entity.setWorld(this);
	}	
	
	/**
	 * Defensive
	 * @param entity
	 */
	public void removeEntity(Entity entity) {
		if (positionsAndEntities.containsValue(entity)) {
			positionsAndEntities.remove(entity.getPosition());
			entity.removeFromWorld();
		}
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
		if (positionsAndEntities.containsKey(position)){
		return positionsAndEntities.get(position);
		}else{
			return null;
		}
	}
	
	public HashSet<Entity> getAllEntities() {
		return new HashSet<Entity>(positionsAndEntities.values());
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
	private HashMap<double[], Entity> positionsAndEntities = new HashMap<>();
	
	
	public double getTimeCollisionVerticalBoundary(Entity entity) {
		double time = Double.POSITIVE_INFINITY;
		double distance = 0;
		if (entity.getVelocityX() == 0){
			return time;
		}else if (!this.withinBoundaries(entity)){
			return 0;
		}else {
			if (entity.getVelocityX() < 0){
				distance = entity.getPositionX() - entity.getRadius();
			}else{
				distance = width - entity.getPositionX() - entity.getRadius();
			}
			return time = Math.abs(distance/entity.getVelocityX());
		}
	}
	
	public double getTimeCollisionHorizontalBoundary(Entity entity) {
		double time = Double.POSITIVE_INFINITY;
		double distance = 0;
		if (entity.getVelocityY() == 0)
			return time;
		else if (!this.withinBoundaries(entity))
			return 0;
		else {
			if (entity.getVelocityY() < 0)
				distance = entity.getPositionY() - entity.getRadius();
			else 
				distance = height - entity.getPositionY() - entity.getRadius();
			return time = Math.abs(distance/entity.getVelocityY());
		}
	}
	
	public double getTimeCollisionToBoundary(Entity entity) { 
		double verticalTime = getTimeCollisionVerticalBoundary(entity);
		double horizontalTime = getTimeCollisionHorizontalBoundary(entity);
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
		double verticalTime = getTimeCollisionVerticalBoundary(entity);
		double horizontalTime = getTimeCollisionHorizontalBoundary(entity);
		if (horizontalTime < verticalTime) {
			if (entity.getVelocityY() < 0){
				yPos = 0;
			}else{
				yPos = height;
			xPos = entity.getPositionX() + entity.getVelocityX()*verticalTime;
			}
		}
		else {
			if (entity.getVelocityX() < 0){
				xPos = 0;
			}else{
				xPos = width;
			yPos = entity.getPositionY() + entity.getVelocityY()*verticalTime;
			}
		}
		double[] position = {xPos, yPos};
		return position;
	}
	
	public double getTimeToNextCollisionEntity(Entity entity) {
		double time = getTimeCollisionToBoundary(entity);
		HashSet<Entity> allEntities = getAllEntities();
		for (Entity other : allEntities) {
			if (entity == other){
				
			}else if ((entity instanceof Ship) && (other instanceof Bullet) && (((Bullet)other).getShip() == entity)){
				
			}else if ((other instanceof Ship) && (entity instanceof Bullet) && (((Bullet)entity).getShip() == other)){
				
			}else if ((other instanceof Bullet) && (entity instanceof Bullet) && (((Bullet)entity).getShip() == ((Bullet)other).getShip())){
				
			}else if(entity.getTimeToCollision(other) < time){
				time = entity.getTimeToCollision(other);
			}
		}
		return time;	
	}
	
	public double[] getPositionEntityUponCollision(Entity entity) {
		double xPos = Double.POSITIVE_INFINITY;
		double yPos = Double.POSITIVE_INFINITY;
		double time = getTimeToNextCollisionEntity(entity);
		if (time < Double.POSITIVE_INFINITY) { 
			xPos = xPos + entity.getVelocityX()*time;
			yPos = yPos + entity.getVelocityY()*time;
		}
		double[] position = {xPos, yPos};
		return position;
	}
	
	public double getTimeToNextCollision() {
		double time = Double.POSITIVE_INFINITY;
		HashSet<Entity> allEntities = getAllEntities();
		for (Entity entity : allEntities) {
			if (getTimeToNextCollisionEntity(entity) < time)
				time = getTimeToNextCollisionEntity(entity);
		}
		return time;
	}
	
	public Entity[] getFirstCollidingEntities() {
		Entity[] firstCollidingEntities = new Entity[]{null,null};
		double timeToNextCollision = getTimeToNextCollision();
		HashSet<Entity> allEntities = getAllEntities();
		for (Entity entity : allEntities) {
			if (getTimeToNextCollisionEntity(entity) == timeToNextCollision) {
				if (firstCollidingEntities[0] == null){
					firstCollidingEntities[0] = entity;
				}else{
					firstCollidingEntities[1] = entity;
				}
			}
		}
		return firstCollidingEntities;
	}
	
	public double[] getPositionNextCollision() {
		Entity[] firstCollidingEntities = getFirstCollidingEntities();
		if (firstCollidingEntities.length > 2)
			throw new IllegalStateException("Multiple collisions occur at once, there is no single 'position of first collision'");
		if (firstCollidingEntities[0] == null)
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
			return this.getPositionCollisionWithBoundary(entity1);
		else
			return entity1.getCollisionPosition(entity2);
	}
	
	public Hashtable<Entity, Entity> getCollidingEntities() {
		HashSet<Entity> allEntities = getAllEntities();
		HashSet<Entity> otherEntities = getAllEntities();
		for (Entity entity : allEntities) {
			otherEntities.remove(entity);
			for (Entity other : otherEntities) {
				if (entity.apparentlyCollide(other)){
					collidingEntities.put(entity, other);
					allEntities.remove(other);
				}else if (!((entity.getVelocityX()!=0) && (entity.getVelocityY()!=0))){
					collidingEntities.put(entity, null);
				}
			}
		}
		return collidingEntities;
	}
	
	private Hashtable<Entity, Entity> collidingEntities = new Hashtable<Entity, Entity>();
	
	/**
	 * no specification, defensive
	 * @param dt
	 */
	public void evolve(double dt, CollisionListener collisionListener) {
		HashSet<Entity> allEntities = getAllEntities();
		double timeToNextCollision = getTimeToNextCollision();
		if (timeToNextCollision < dt) {
			for (Entity entity : allEntities) {
				entity.move(timeToNextCollision);
			}
			resolveCollisions();
			double newTime = dt - timeToNextCollision;
			evolve(newTime, null);
		}
		else
			for (Entity entity : allEntities) {
				entity.move(dt);
				
			}
	}
	
	public void resolveCollisions() {
		Entity[] firstCollidingEntities = getFirstCollidingEntities();
		Entity entity1 = firstCollidingEntities[0];
		Entity entity2 = firstCollidingEntities[1];
		if (entity2 == null){
			(entity1).collidesWithBoundary(this);
		}else{
			if ( (entity1 instanceof Ship) && (entity2 instanceof Ship) ){
				((Ship)entity1).collidesWithShip((Ship)entity2);
			}
			else if ( (entity1 instanceof Ship) && (entity2 instanceof Bullet) ){
				System.out.print("yes");
				((Ship)entity1).getsHitBy((Bullet)entity2, this);
			}
			else if ( (entity1 instanceof Bullet) && (entity2 instanceof Ship) ){
				System.out.print("yes");
				((Ship)entity2).getsHitBy((Bullet)entity1, this);
			}else{
				((Bullet)entity1).cancelsOut((Bullet)entity2);
			}
		}
	}
	
}
