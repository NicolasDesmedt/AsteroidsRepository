package asteroids.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import asteroids.part2.CollisionListener;
//import asteroids.util.Util;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of worlds involving a size, facilities for adding and removing entities in this world
 * and facilities to evolve the state of this world a given time.
 * 
 * @invar	...
 * 			| 
 * 
 * @author 	Nicolas Desmedt and Lucas Desard
 * @version 1.0
 * 
 * Course studies: 2nd Bachelor Engineering: Computer science/Electrical Engineering
 * Code Repository: https://github.com/NicolasDesmedt/RepositoryLucasNicolas
 */
public class World{
	
	/**
	 * Create a world with given width and height.
	 * 
	 * @param 	width
	 * 			The width of this new world.
	 * @param	height
	 * 			The height of this new world.
	 * @post	...
	 * 			| new.getWorldWidth() == width
	 * @post	...
	 * 			| new.getWorldHeight() == height
	 * @post	...
	 * 			| new.getAllEntities().isEmpty() == true
	 */
	public World(double width, double height){
		if ((width < 0) || (width > upperBound)) 
			width = upperBound;
		if ((height < 0) || (height > upperBound))
			height = upperBound;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Return the upper bound on the height and the width.
	 */
	@Basic @Immutable
	public static double getUpperBound() {
		return World.upperBound;
	}
	
	/**
	 * Constant reflecting the upper bound on the 
	 * height and the width that applies to all worlds.
	 */
	private static final double upperBound = Double.MAX_VALUE;
	
	/**
	 * Variable registering the width of this world.
	 */
	private final double width;
	
	/**
	 * Variable registering the height of this world.
	 */
	private final double height;
	
	/**
	 * Return the width of this world.
	 * 	The width of a world is expressed in km.
	 */
	@Basic @Immutable
	public double getWorldWidth() {
		return this.width;
	}
	
	/**
	 * Return the height of this world.
	 * 	The height of a world is expressed in km.
	 */
	@Basic @Immutable
	public double getWorldHeight() {
		return this.height;
	}
	
	/**
	 * Return the seize of this world.
	 * 	The seize of a world is described by a width and a height expressed in km.
	 */
	@Immutable
	public double[] getWorldSize() {
		double[] worldSize = {this.getWorldWidth(),this.getWorldHeight()};
		return worldSize;
	}
	
	/**
	 * Terminate this world.
	 *
	 * @post ...
	 *       | new.isTerminated()
	 * @post ...
	 *       | for each entity in old.getAllEntities()
	 *       |	(new entity).getWorld() == null
	 */
	 public void terminate() {
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
	 * Return a boolean indicating whether or not the given entity
	 * if fully within the boundaries of this world.
	 * 
	 * @param	entity
	 * 			The entity to check.
	 * @return	...
	 * 			| @see implementation
	 */
	public boolean withinBoundaries(Entity entity) {
		double xPos = entity.getPositionX();
		double yPos = entity.getPositionY();
		double significantEntityRadius = 0.99*entity.getRadius();
		return ((xPos+significantEntityRadius) <= width && (xPos-significantEntityRadius) >= 0 
				&& (yPos+significantEntityRadius) <= height && (yPos-significantEntityRadius) >= 0);
	}
	
	/**
	 * Return the distance between the center of the given entity
	 * and the nearest boundary of this world parallel with the x-axis.
	 * 
	 * @param	entity
	 * 			The entity whose distance to the nearest 
	 * 			horizontal boundary of this world gets returned.
	 * @return	...
	 * 			| @see implementation
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
	
	/**
	 * Return the distance between the center of the given entity
	 * and the nearest boundary of this world parallel with the y-axis.
	 * 
	 * @param 	entity
	 * 			The entity whose distance to the nearest
	 * 			vertical boundary of this world gets returned.
	 * @return	...
	 * 			| @see implementation
	 */
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
	
	/**
	 * Return the distance between the center of the given entity
	 * and the nearest boundary of this world.
	 * 
	 * @param 	entity
	 * 			The entity whose distance to the nearest 
	 * 			boundary of this world gets returned.
	 * @return	...
	 * 			| @see implementation
	 */
	public double getDistanceToBoundaries(Entity entity) {
		if (getDistanceToNearestVerticalBoundary(entity) <
				getDistanceToNearestHorizontalBoundary(entity))
			return getDistanceToNearestVerticalBoundary(entity);
		else
			return getDistanceToNearestHorizontalBoundary(entity);
	}
	
	public double getDistanceBetweenCoordinates(double[] position1, double[] position2) {
		double x1 = position1[0];
		double x2 = position2[0];
		double y1 = position1[1];
		double y2 = position2[1];
		double distance = (Math.sqrt(Math.pow((x2 - x1), 2)) + Math.pow((y2 - y1), 2));
		return distance;
	}
	
	/**
	 * Returns true if and only if the entity fits in the world, is not a duplicate, does not overlap.
	 * @param entity
	 * @return
	 */

	
	public boolean overlapsWithOtherEntities(Entity entity) {
		for (Entity other : allEntities) {
			if (entity.overlap(other)){
				if (entity == other) {
					return false;
				} else if ((entity instanceof Ship) && (other instanceof Bullet) && (((Bullet)other).getShip() == entity)){
					return false;
				}else if ((entity instanceof Bullet) && (other instanceof Ship) && (((Bullet)entity).getShip() == other)){
					return false;
				}else if ((entity instanceof Bullet) && (other instanceof Bullet) && (((Bullet)entity).getShip() == ((Bullet)other).getShip())){
					return false;
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
		if (allEntities.contains(entity))
			return;
		if (entity.getWorld() != null) throw new IllegalArgumentException
			("The given entity already belongs to a world.");
		if (!this.withinBoundaries(entity)) throw new IllegalArgumentException
			("This entity doesn't lie fully within the bounds of that world.");
		if (this.overlapsWithOtherEntities(entity)) throw new IllegalArgumentException
			("The entity overlaps with other entities that is shouldn't overlap with");
		
		allEntities.add(entity);
		entity.setWorld(this);
	}	
	
	/**
	 * Defensive
	 * @param entity
	 */
	public void removeEntity(Entity entity) {
		if (allEntities.contains(entity)) {
			this.allEntities.remove(entity);
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
		for (Entity entity : allEntities) {
			if (entity.getPosition() == position)
				return entity;
		}
		return null;
	}
	
	public Set<Entity> getAllEntities() {
		return new HashSet<Entity>(allEntities);
	}
	
	public Set<Ship> getAllShips() {
		Set<Ship> allShips = new HashSet<Ship>();
		for (Entity entity : allEntities)
			if (entity instanceof Ship)
				allShips.add((Ship)entity);
		return allShips;
	}
	
	public Set<Bullet> getAllBullets() {
		Set<Bullet> allBullets = new HashSet<Bullet>();
		for (Entity entity : allEntities)
			if (entity instanceof Bullet)
				allBullets.add((Bullet)entity);
		return allBullets;
	}
	
	/**
	 * A hashmap containing all the entities in the world with their position.
	 */
	private HashMap<double[], Entity> positionsAndEntities = new HashMap<>();
	
	private final Set<Entity> allEntities = new HashSet<Entity>();
	
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
		Set<Entity> allEntitiesCopy = getAllEntities();
		allEntitiesCopy.remove(entity);
		for (Entity other : allEntitiesCopy ) {
			
			//}else if ((entity instanceof Ship) && (other instanceof Bullet) && (((Bullet)other).getShip() == entity)){
				
			//}else if ((other instanceof Ship) && (entity instanceof Bullet) && (((Bullet)entity).getShip() == other)){
				
			//}else if ((other instanceof Bullet) && (entity instanceof Bullet) && (((Bullet)entity).getShip() == ((Bullet)other).getShip())){
			//if (entity.overlap(other)) 
			//	return 0;
			
			try {
				if(entity.getTimeToCollision(other) < time)
					time = entity.getTimeToCollision(other);
				} catch(IllegalArgumentException e) {
					System.out.println("time caught");
					throw new IllegalArgumentException("Overlapping entities!");
				}
		}
		if (time < 0)
			time = 0;
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
		try {
			for (Entity entity : allEntities) {
				if (getTimeToNextCollisionEntity(entity) < time)
					time = getTimeToNextCollisionEntity(entity);
			} 
		}catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Alright");
			}
		return time;
	}
	
	public Entity[] getFirstCollidingEntities() {
		Entity[] firstCollidingEntities = new Entity[]{null,null};
		double timeToNextCollision = getTimeToNextCollision();
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
//		else if (entity1.overlap(entity2)) {
//			double diffX = entity2.getPositionX() - entity1.getPositionX();
//			double diffY = entity2.getPositionY() - entity1.getPositionY();
//			double angleCenters = 0;
//			if (diffX*diffY >= 0){
//				if (diffX == 0){
//					if (diffY > 0){
//						angleCenters = (Math.PI/2);
//					}else{
//						angleCenters = -(Math.PI/2);
//					}
//				}else if ((diffX > 0) || (diffY == 0)){
//					angleCenters = Math.atan(diffY/diffX) + Math.PI;
//				}else if((diffX < 0) || (diffY == 0)){
//					angleCenters = Math.atan(diffY/diffX);
//				}
//			}else {
//				if (diffY > 0){
//					angleCenters = Math.atan(diffY/diffX);
//				}else if(diffY < 0){
//					angleCenters = Math.atan(diffY/diffX) + Math.PI;
//				}
//			}
//			double[] collisionPoint = {entity2.getPositionX() + entity2.getRadius()*Math.cos(angleCenters), 
//					entity2.getPositionY() + entity2.getRadius()*Math.sin(angleCenters)};
//			return collisionPoint;
//		}
		else
			return entity1.getCollisionPosition(entity2);
	}
	
	public Hashtable<Entity, Entity> getCollidingEntities() {
		Set<Entity> allEntitiesCopy = getAllEntities();
		Set<Entity> otherAllEntitiesCopy = getAllEntities();
		for (Entity entity : allEntitiesCopy) {
			otherAllEntitiesCopy.remove(entity);
			for (Entity other : otherAllEntitiesCopy) {
				if (entity.apparentlyCollide(other)){
					collidingEntities.put(entity, other);
					allEntitiesCopy.remove(other);
				}else if (!((entity.getVelocityX()!=0) && (entity.getVelocityY()!=0))){
					collidingEntities.put(entity, null);
				}
			}
		}
		return collidingEntities;
	}
	
	public Entity[] getOverlappingEntities() {
		Entity[] overlappingEntities = new Entity[]{null,null};
		Set<Entity> allEntitiesCopy = getAllEntities();
		Set<Entity> otherAllEntitiesCopy = getAllEntities();
		for (Entity entity : allEntitiesCopy) {
			otherAllEntitiesCopy.remove(entity);
			for (Entity other : otherAllEntitiesCopy) {
				if (entity.overlap(other)){
					overlappingEntities[0] = entity;
					overlappingEntities[1] = other;
					return overlappingEntities;	
				}
			}
		}
		return overlappingEntities;
	}
	
	private Hashtable<Entity, Entity> collidingEntities = new Hashtable<Entity, Entity>();
	
	/**
	 * no specification, defensive
	 * @param dt
	 */
	public void evolve(double dt, CollisionListener collisionListener) {
		try {
			double timeToNextCollision = getTimeToNextCollision();
			if (timeToNextCollision < dt) {
				advanceAllEntities(timeToNextCollision);
				if (collisionListener != null)
				  updateCollisionListener(collisionListener);
				resolveCollisions();
				double newTime = dt - timeToNextCollision;
				evolve(newTime, collisionListener);
			} else {
				advanceAllEntities(dt);
			}
		} catch(IllegalArgumentException e) {
			Entity[] overlappingEntities = this.getOverlappingEntities();
			overlappingEntities[0].terminate();
			overlappingEntities[1].terminate();
		}
	}
	
	public void advanceAllEntities (double duration) {
		for (Entity entity : allEntities) {
			entity.move(duration);	
			if (entity instanceof Ship && ((Ship)entity).isShipThrusterActive()) {
				Ship ship = (Ship)entity;
				double velocityToAdd = ship.getThrusterForce()*duration / (ship.getMass()*1000);
				ship.thrust(velocityToAdd); 
			}	
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
				System.out.print("entity1(Ship) collides w entity2(Bullet)");
				((Ship)entity1).getsHitBy((Bullet)entity2);
			}
			else if ( (entity1 instanceof Bullet) && (entity2 instanceof Ship) ){
				System.out.println("entity1(Bullet) collides w entity2(Ship)");
				((Ship)entity2).getsHitBy((Bullet)entity1);
			}else{
				System.out.println("collidingBullets");
				((Bullet)entity1).cancelsOut((Bullet)entity2);
			}
		}
	}
	

 	public void updateCollisionListener(CollisionListener collisionListener) throws IllegalStateException {
 		double xPos = getPositionNextCollision()[0];
 		double yPos = getPositionNextCollision()[1];
 		Entity[] firstCollidingEntities = getFirstCollidingEntities();
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
 			collisionListener.boundaryCollision(entity1, xPos, yPos);
 		else
 			collisionListener.objectCollision(entity1, entity2, xPos, yPos);
 	}
}
