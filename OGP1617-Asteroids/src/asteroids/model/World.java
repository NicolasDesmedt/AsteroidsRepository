package asteroids.model;

import java.util.HashSet;
import java.util.Set;

import asteroids.part2.CollisionListener;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of worlds involving a size, facilities for adding and removing entities in this world
 * and facilities to evolve the state of this world a given time.
 * 
 * @invar	...
 * 			| canHaveAsBoundaryLength(width)
 * @invar	...
 * 			| canHaveAsBoundaryLength(length)
 * @invar	...
 * 			| for each entity in allEntities:
 * 			|	((this.withinBoundaries(entity)) &&
 * 			|	(entity.getWorld() == this) &&
 * 			|	(! this.overlapsWithOtherEntities(entity)))
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
		if (!canHaveAsBoundaryLength(width)) 
			width = upperBound;
		if (!canHaveAsBoundaryLength(height)) 
			height = upperBound;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Return a boolean indicating whether or not a world can have the 
	 * 	given boundary length as the length of its boundary.
	 */
	@Raw
	public static boolean canHaveAsBoundaryLength(double length) {
		if ((length < 0) || (length > upperBound) || (Double.isNaN(length)))
			return false;
		else
			return true;
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
		 allEntities.clear();
		 this.isTerminated = true;
	 }
	 
	 /**
	  * Return a boolean indicating whether or not this world
	  * is terminated.
	  */
	 @Basic @Raw @Model
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
	 @Model
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
	 * @throws	IllegalArgumentException
	 * 			...
	 * 			| (!withinBoundaries(entity))
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
	 * @throws	IllegalArgumentException
	 * 			...
	 * 			| (!withinBoundaries(entity))
	 */
	public double getDistanceToNearestVerticalBoundary(Entity entity) throws IllegalArgumentException{
		if (!withinBoundaries(entity)) throw new IllegalArgumentException("The entity is not located in the world.");
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
	public double getDistanceToBoundaries(Entity entity) throws IllegalArgumentException{
		if (getDistanceToNearestVerticalBoundary(entity) <
				getDistanceToNearestHorizontalBoundary(entity))
			return getDistanceToNearestVerticalBoundary(entity);
		else
			return getDistanceToNearestHorizontalBoundary(entity);
	}
	
	/**
	 * Return the distance between the given positions, in a 2D-field.
	 * 
	 * @param 	position1
	 * 			The first position, a set of coordinates.
	 * @param 	position2
	 * 			The second position, a set of coordinates.
	 * @return	...
	 * 			| @see implementation
	 */
	public double getDistanceBetweenCoordinates(double[] position1, double[] position2) {
		double x1 = position1[0];
		double x2 = position2[0];
		double y1 = position1[1];
		double y2 = position2[1];
		double distance = (Math.sqrt(Math.pow((x2 - x1), 2)) + Math.pow((y2 - y1), 2));
		return distance;
	}

	/**
	 * Return a boolean indicating whether or not in this world the given entity overlaps
	 *  with an other entity, that is not the source of the given entity or a bullet fired 
	 *  from the given entity or a bullet with the same source as the given entity.
	 *  
	 * @param 	entity
	 * 			The entity to check.
	 * @return	...
	 * 			| @see implementation
	 */
	@Raw @Model
	public boolean overlapsWithOtherEntities(Entity entity) {
		for (Entity other : allEntities) {
			if (entity.overlapFiltered(other)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Add the given entity to this world.
	 * 
	 * @param	entity
	 * 			The entity to add to this world.
	 * @post	...
	 * 			| new.getAllEntities().contains(entity)
	 * @post	...
	 * 			| (new entity).getWorld() == this
	 * @post	...
	 * 			| if old.getAllEntities().contains(entity)
	 * 			|	then new == old
	 * @throws	IllegalArgumentException
	 * 			...
	 * 			| (((old entity).getWorld() != null) || (!withinBoundaries(entity)) || (overlapsWithOtherEntities(entity))
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
	 * Remove the given entity from this world
	 * 
	 * @param 	entity
	 * 			The entity to remove from this world.
	 * @post	...
	 * 			| !new.getAllEntities().contains(entity)
	 * @post	...
	 * 			| (new entity).getWorld() == null
	 * @throws	IllegalArgumentException
	 * 			...
	 * 			| !this.getAllEntities().contains(entity)
	 */
	public void removeEntity(Entity entity) {
		if (allEntities.contains(entity)) {
			this.allEntities.remove(entity);
			entity.removeFromWorld();
		}
		else throw new IllegalArgumentException("This world does not contain the given entity.");
	}
	
	/**
	 * Return the entity in this world at the given position.
	 * 
	 * @param 	x
	 * 			The x-coordinate of the given position.
	 * @param 	y
	 * 			The y-coordinate of the given position.
	 * @return	Null if the given position is not occupied.
	 * 			| if (for each entity in allEntities:
	 * 			|	((entity.getPositionX() != x) || (entity.getPositionY() != y)))
	 * 			|		then result == null;
	 * @return	The entity at the given position if the position is occupied.
	 * 			| if (for an entity in allEntities:
	 *			|	((entity.getPositionX() != x) || (entity.getPositionY() != y)))
	 *			|		then result == entity;
	 */
	public Entity getEntityAt(double x, double y) {
		double[] position = {x,y};
		for (Entity entity : allEntities) {
			if ((entity.getPositionX() == position[0]) && (entity.getPositionY() == position[1]))
				return entity;
		}
		return null;
	}
	
	/**
	 * Return a set of all the entities that are in this world.
	 * 
	 * @return	...
	 * 			| for each entity:
	 * 			| 	if (entity.getWorld() == this)
	 * 			|		then result.contains(entity)
	 */
	@Basic @Model
	public Set<Entity> getAllEntities() {
		return new HashSet<Entity>(allEntities);
	}
	
	/**
	 * Return a set of all the ships that are in this world.
	 * 
	 * @return	...
	 * 			| for each entity in allEntities:
	 * 			|	if (entity instanceof Ship)
	 * 			|		then result.contains(entity)
	 */
	public Set<Ship> getAllShips() {
		Set<Ship> allShips = new HashSet<Ship>();
		for (Entity entity : allEntities)
			if (entity instanceof Ship)
				allShips.add((Ship)entity);
		return allShips;
	}
	
	/**
	 * Return a set of all the bullets that are in this world.
	 * 
	 * @return	...
	 * 			| for each entity in allEntities:
	 * 			|	if (entity instanceof Bullet)
	 * 			|		then result.contains(entity)
	 */
	public Set<Bullet> getAllBullets() {
		Set<Bullet> allBullets = new HashSet<Bullet>();
		for (Entity entity : allEntities)
			if (entity instanceof Bullet)
				allBullets.add((Bullet)entity);
		return allBullets;
	}
	
	/**
	 * A variable set registering all the entities in this world.
	 * 
	 * @invar   The set is effective.
	 *        	| allEntities != null
	 * @invar	...
	 * 			| for each entity in allEntities:
	 * 			|	(entity.getWorld() == this) &&
	 * 			|	(this.withinBoundaries(entity)) &&
	 * 			|	(!(this.overlapsWithOtherEntities(entity)))
	 */
	private final Set<Entity> allEntities = new HashSet<Entity>();
	
	/**
	 * Return the time (in seconds) it takes for the given entity to collide
	 * 	with a vertical boundary of this world. The movement of the given entity
	 * 	is not influenced by other entities for this function.
	 * 
	 * @param 	entity
	 * 			The given entity.
	 * @return	...
	 * 			| @see implementation
	 */
	public double getTimeCollisionVerticalBoundary(Entity entity) {
		double time = Double.POSITIVE_INFINITY;
		double distance = 0;
		if (entity.getVelocityX() == 0.0){
			return time;
		}else {
			if (entity.getVelocityX() < 0.0){
				distance = entity.getPositionX() - entity.getRadius();
			}else{
				distance = width - entity.getPositionX() - entity.getRadius();
			}
			return time = Math.abs(distance/entity.getVelocityX());
		}
	}
	
	/**
	 * Return the time (in seconds) it takes for the given entity to collide
	 * 	with a horizontal boundary of this world. The movement of the given entity
	 * 	is not influenced by other entities for this function.
	 * 
	 * @param 	entity
	 * 			The given entity.
	 * @return	...
	 * 			| @see implementation
	 */
	public double getTimeCollisionHorizontalBoundary(Entity entity) {
		double time = Double.POSITIVE_INFINITY;
		double distance = 0;
		if (entity.getVelocityY() == 0.0)
			return time;
		else {
			if (entity.getVelocityY() < 0)
				distance = entity.getPositionY() - entity.getRadius();
			else 
				distance = height - entity.getPositionY() - entity.getRadius();
			return time = Math.abs(distance/entity.getVelocityY());
		}
	}
	
	/**
	 * Return the time (in seconds) it takes for the given entity to collide
	 * 	with a boundary of this world. The movement of the given entity
	 * 	is not influenced by other entities for this function.
	 * 
	 * @param 	entity
	 * 			The given entity.
	 * @return	...
	 * 			| @see implementation
	 */
	public double getTimeCollisionToBoundary(Entity entity) { 
		double verticalTime = getTimeCollisionVerticalBoundary(entity);
		double horizontalTime = getTimeCollisionHorizontalBoundary(entity);
		if (verticalTime < horizontalTime)
			return verticalTime;	
		else
			return horizontalTime;
	}
	
	/**
	 * Return the position where the given entity will collide 
	 * 	with a boundary of this world. The movement of the given entity
	 * 	is not influenced by other entities for this function.
	 * 
	 * @param 	entity
	 * 			The given entity.
	 * @return	...
	 * 			| @see implementation
	 */
	public double[] getPositionCollisionWithBoundary(Entity entity) {
		double xPos = Double.POSITIVE_INFINITY;
		double yPos = Double.POSITIVE_INFINITY;
		double verticalTime = getTimeCollisionVerticalBoundary(entity);
		double horizontalTime = getTimeCollisionHorizontalBoundary(entity);
		double[] position = {xPos, yPos};
		if ( (verticalTime == Double.POSITIVE_INFINITY) && (horizontalTime == Double.POSITIVE_INFINITY) ) {
			return position;
		}
		else if (horizontalTime < verticalTime) {
			if (entity.getVelocityY() < 0){
				yPos = 0;
			}else{
				yPos = height;
			}
			xPos = entity.getPositionX() + entity.getVelocityX()*verticalTime;
		}
		else {
			if (entity.getVelocityX() < 0){
				xPos = 0;
			}else{
				xPos = width;
			}
			yPos = entity.getPositionY() + entity.getVelocityY()*verticalTime;
		}
		position[0] = xPos;
		position[1] = yPos;
		return position;
	}
	
	/**
	 * Return the time (in seconds) it takes for the given entity to 
	 * 	collide with an other entity or with a boundary of this world.
	 * 
	 * @param 	entity
	 * 			The given entity.
	 * @return	...
	 * 			| @see implementation
	 * @throws	IllegalArugmentException
	 * 			...
	 * 			| if (for (other != entity) in allEntities:
	 * 			|		(entity.getTimeToCollision(other)))
	 * 			|			throws IllegalArgumentException
	 */
	@Model
	public double getTimeToNextCollisionEntity(Entity entity) throws IllegalArgumentException {
		double time = this.getTimeCollisionToBoundary(entity);
		Set<Entity> allEntitiesCopy = getAllEntities();
		allEntitiesCopy.remove(entity);
		for (Entity other : allEntitiesCopy ) {
			try {
				if(entity.getTimeToCollision(other) < time)
					time = entity.getTimeToCollision(other);
				} catch(IllegalArgumentException e) {
					throw new IllegalArgumentException("Overlapping entities!");
				}
		}
		if (time < 0)
			time = 0;
		return time;	
	}
	
	/**
	 * Return the time (in seconds) it takes for a collision
	 * 	to happen in this world.
	 * 
	 * @return	...
	 * 			| for each entity in allEntities:
	 * 			|	result <= this.getTimeToNextCollision(entity)
	 */
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
	
	/**
	 * Return an array containing the entities that will collide first 
	 * 	in this world. If the first collision to happen in this world is 
	 * 	a collision between an entity and a boundary of this world,
	 * 	then only that entity gets returned.
	 * 
	 * @return	...
	 * 			| for each entity not in result:
	 * 			| 	this.getTimeToNextCollision(entity) > this.getTimeToNextCollision(result[0])
	 */
	@Model
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
	
	/**
	 * Return the position in this world where the first
	 * 	collision will happen.
	 * 
	 * @return	...
	 * 			| @see implementation
	 * @throws	IllegalStateException
	 * 			...
	 * 			| (this.getFirstCollidingEntities().length > 2) || (firstCollidingEntities[0] == null)
	 */
	public double[] getPositionNextCollision() throws IllegalStateException {
		Entity[] firstCollidingEntities = this.getFirstCollidingEntities();
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
	
	/**
	 * Return an array containing the entities that are overlapping in this world,
	 * not including ships overlapping with their own bullets.
	 * 	There is only one way entities can overlap in the same world:
	 * 	 when a ship fires a bullet and the firing position of the ship
	 * 	 is occupied by an other entity.
	 * 
	 * @return	...
	 * 			| result[0].overlapFiltered(result[1]) == true;
	 */
	@Raw
	public Entity[] getOverlappingEntities() {
		Entity[] overlappingEntities = new Entity[]{null,null};
		Set<Entity> allEntitiesCopy = getAllEntities();
		Set<Entity> otherAllEntitiesCopy = getAllEntities();
		for (Entity entity : allEntitiesCopy) {
			otherAllEntitiesCopy.remove(entity);
			for (Entity other : otherAllEntitiesCopy) {
				if (entity.overlapFiltered(other)){
					overlappingEntities[0] = entity;
					overlappingEntities[1] = other;
					return overlappingEntities;	
				}
			}
		}
		return overlappingEntities;
	}
		
	/**
	 * Evolve the state of this world a given amount of time and 
	 * update the collisionListener on any collisions occurring.
	 */
	@Raw
	public void evolve(double dt, CollisionListener collisionListener) throws IllegalArgumentException {
		if (dt < 0) {
			throw new IllegalArgumentException("The given amount of time must be positive");
		}
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
			if (collisionListener != null)
				  updateCollisionListener(collisionListener);
			Entity[] overlappingEntities = this.getOverlappingEntities();
			overlappingEntities[0].terminate();
			overlappingEntities[1].terminate();
			evolve(dt, collisionListener);
		}
	}
	
	/**
	 * Advance the position of all entities a given duration.
	 * 
	 * @param 	duration
	 * 			The given duration.
	 * @pre		No collisions occur during the given duration.
	 * 			| for each entity in allEntities:
	 * 			|	this.getTimeToNextCollision(entity) >= duration
	 * @effect	...
	 * 			| @see implementation
	 */
	public void advanceAllEntities (double dt) {
		assert this.getTimeToNextCollision() >= dt;
		for (Entity entity : allEntities) {
			entity.move(dt);
		}
	}
	
	/**
	 * Resolve the collision in this world.
	 * 	Ships bounce of each other, bullets kill each other,
	 * 	a colliding bullet and ship kill each other if the source
	 * 	of the bullet is not the ship it collides with, if it is that 
	 * 	ship then the bullet gets loaded on the ship.
	 * 
	 * @effect	...
	 * 			| @see implementation
	 */
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
				((Ship)entity1).getsHitBy((Bullet)entity2);
			}
			else if ( (entity1 instanceof Bullet) && (entity2 instanceof Ship) ){
				((Ship)entity2).getsHitBy((Bullet)entity1);
			}else{
				((Bullet)entity1).cancelsOut((Bullet)entity2);
			}
		}
	}
	
	/**
	 * Update the collision listener on the collision events.
	 * 	The collision listener does not get updated on bullets colliding 
	 * 	with their own ship. Collisions due to bullets overlapping with a ship 
	 * 	upon getting put in firing position count as a normal objectCollision.
	 * 	
	 * @param 	collisionListener
	 * 			The collision listener to update.
	 * @effect	...
	 * 			| @see implementation
	 * @throws 	IllegalStateException
	 * 			...
	 * 			| this.getTimeToNextCollision() > 0
	 */
	@Raw
 	public void updateCollisionListener(CollisionListener collisionListener) throws IllegalStateException {
 		Entity entity1 = null;
 		Entity entity2 = null;
 		Entity[] overlappingEntities = this.getOverlappingEntities();
 		if (overlappingEntities[0] != null) {
 			entity1 = overlappingEntities[0];
 			entity2 = overlappingEntities[1];
 			double xPos = 0;
 			double yPos = 0;
 			if (entity1 instanceof Ship) {
 				xPos = entity1.getPositionX();
 				yPos = entity1.getPositionY();
 			}
 			else {
 				xPos = entity2.getPositionX();
 				yPos = entity2.getPositionY();
 			}
 			collisionListener.objectCollision(entity1, entity2, xPos, yPos);
 			return;
 		}
 		double xPos = getPositionNextCollision()[0];
 		double yPos = getPositionNextCollision()[1];
 		Entity[] firstCollidingEntities = getFirstCollidingEntities();
 		if (firstCollidingEntities[0] == null)
 			throw new IllegalStateException("There are no colliding entities");
 		for (Entity entity : firstCollidingEntities) {
 			if (entity1 == null)
 				entity1 = entity;
 			else 
 				entity2 = entity;
 		}
 		if (entity2 == null) {
 			if (entity1.getWorld().withinBoundaries(entity1))
 				collisionListener.boundaryCollision(entity1, xPos, yPos);
 		}
 		else {
 			if( (entity1 instanceof Bullet) && (entity2 instanceof Ship)) {
 				if (((Bullet)entity1).getSource() == entity2)
 					return;
 			} else if ((entity1 instanceof Ship) && (entity2 instanceof Bullet)) {
 				if (((Bullet)entity2).getSource() == entity1)
 					return;
 			}
 			collisionListener.objectCollision(entity1, entity2, xPos, yPos);
 		}
 	}
}