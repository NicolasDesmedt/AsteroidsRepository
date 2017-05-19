package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * An abstract class of entities involving a position, velocity,
 * 	radius, mass, facilities to predict collisions and a world.
 * 
 * @invar	The position of each entity must be a valid position for any entity.
 * 			| isValidPosition(getPosition())
 * @invar	The velocity of each entity must be a valid velocity for any entity.
 * 			| isValidVelocity(getVelocity())
 * @invar	The radius of each entity must be a valid radius for any entity.
 * 			| isValidRadius(getRadius())
 * @invar	The mass of each entity must be a valid mass for any entity.
 * 			| isValidMass(getMass())
 * 
 * @author 	Nicolas Desmedt and Lucas Desard
 * @version	1.0
 *
 * Course studies: 2nd Bachelor Engineering: Computer science/Electrical Engineering
 * Code Repository: https://github.com/NicolasDesmedt/RepositoryLucasNicolas
 */
public abstract class Entity {
	
	protected Entity(double x, double y, double xVelocity, double yVelocity, double radius)
			throws IllegalArgumentException{
			this(x, y, xVelocity, yVelocity, radius, SPEED_OF_LIGHT);
		}
	
	protected Entity(double x, double y, double xVelocity, double yVelocity, double radius, double maxSpeed)
		throws IllegalArgumentException{
		this.setMaxSpeed(maxSpeed);
		this.setPosition(x,y);
		this.setVelocity(xVelocity, yVelocity);
		if (!isValidRadius(radius)) throw new IllegalArgumentException("The given radius isn't a valid one");
		this.radius = radius;
	}
	
	/**
	 * Return the position of this entity.
	 * 	The position of an entity locates the entity in an unbounded two-dimensional space.
	 */
	@Basic @Raw @Model
	public double[] getPosition(){
		return position.clone();
	}
	
	/**
	 * Return the x-coordinate of the position of this entity.
	 */
	public double getPositionX() {
		return position[0];
	}
	
	/**
	 * Return the y-coordinate of the position of this entity.
	 */
	public double getPositionY() {
		return position[1];
	}
	
	/**
	 * Check whether the given position is a valid position for
	 * any entity.
	 *  
	 * @param  position
	 *         The position to check.
	 * @return True if and only if the position consists of a double containing two real numbers who are not infinite.
	 *         | result == ( (!Double.isNaN(position[0])) && (!Double.isNaN(position[1])) &&
	 *         |			 (!Double.isInfinite(position[0])) && (!Double.isInfinite(position[0])) && (position.length == 2) )
	 */
	@Raw @Model
	public static boolean isValidPosition(double[] position){
		if ( (!Double.isNaN(position[0])) && (!Double.isNaN(position[1])) && (position.length == 2) ) {
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Set the position of this entity to the given position.
	 * 
	 * @param 	position
	 * 			The new position for this entity.
	 * @post	The position of this entity is equal to the given position.
	 *       	| new.getPosition() == position
	 * @throws 	IllegalArgumentException
	 *  		The given position is not a valid position for any entity.
	 *       	| ! isValidPosition(position)
	 */
	@Raw
	public void setPosition(double x, double y)
		throws IllegalArgumentException{
		double[] position = new double[] {x,y};
		if (!isValidPosition(position)) throw new IllegalArgumentException("The given position isn't a valid one");
		this.position = position;
	}
	
	/**
	 * Variable registering the position of this entity.
	 */
	private double[] position = new double[2];
	
	/**
	 * Return the velocity of this entity.
	 * 	The velocity of a entity consists of a component in the x direction 
	 *  and a component in the y direction who determine the vessel’s movement 
	 *  per time unit in the x and y direction respectively.
	 */
	@Basic @Raw @Model
	public double[] getVelocity(){
		return velocity;
	}
	
	/**
	 * Return the velocity of this entity in the x-direction.
	 */
	@Model
	public double getVelocityX() {
		return velocity[0];
	}
	
	/**
	 * Return the velocity of this entity in the y-direction.
	 */
	@Model
	public double getVelocityY() {
		return velocity[1];
	}
	
	/**
	 * Check whether the given velocity is a valid velocity for
	 * any entity.
	 *  
	 * @param  velocity
	 *         The velocity to check.
	 * @return True if and only if the velocity consists of a double containing two real numbers.
	 *         | result == ( (!Double.isNaN(velocity[0])) && (!Double.isNaN(velocity[1])) && (velocity.length == 2) )
	 */
	@Raw
	public static boolean isValidVelocity(double[] velocity){
		if ( (!Double.isNaN(velocity[0])) && (!Double.isNaN(velocity[1])) && (velocity.length == 2) ){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Set the velocity of this entity to the given velocity.
	 * 
	 * @param 	velocity
	 * 			The new velocity for this entity.
	 * @post	If the given velocity does not exceed the maximum speed
	 * 			for this entity, then the velocity of this entity is equal to 
	 * 			the given velocity.
	 * 			| new.getVelocity() == {xVelocity, yVelocity}
	 * @post	If the given velocity does exceed the maximum speed for this
	 * 			entity, then the velocity is altered until the speed of this 
	 * 			entity equals the maximum speed, keeping the same proportions 
	 * 			between the x-velocity and the y-velocity.
	 * 			| @see implementation
	 * @post	If a given velocity is NaN, then that velocity is set to 0.
	 * 			| @see implementation
	 */
	@Raw
	public void setVelocity(double xVelocity, double yVelocity){
		if (Double.isNaN(yVelocity)) {
			setVelocity(xVelocity,0);
			return;
		}
		if (Double.isNaN(xVelocity)) {
			setVelocity(0,yVelocity);
			return;
		}
		double speed = getSpeed(xVelocity, yVelocity);
		if (Double.isInfinite(xVelocity) && (Double.isInfinite(yVelocity))){
			if (xVelocity > 0){
				this.velocity[0] = maxSpeed/Math.sqrt(2);
			} else{
				this.velocity[0] = -maxSpeed/Math.sqrt(2);
			}
			if (yVelocity > 0){
				this.velocity[1] = maxSpeed/Math.sqrt(2);
			} else{
				this.velocity[1] = -maxSpeed/Math.sqrt(2);
			}
		} else if (Double.isInfinite(xVelocity)){
			if (xVelocity > 0){
				this.velocity[0] = maxSpeed;
			} else{
				this.velocity[0] = -maxSpeed;
			}
			this.velocity[1] = 0;
		} else if (Double.isInfinite(yVelocity)){
			if (yVelocity > 0){
				this.velocity[1] = maxSpeed;
			} else{
				this.velocity[1] = -maxSpeed;
			}
			this.velocity[0] = 0;
		}else if (speed > maxSpeed){
			this.velocity[0] = (xVelocity*maxSpeed)/speed;
			this.velocity[1] = (yVelocity*maxSpeed)/speed;
		} 
		else{
			this.velocity[0] = xVelocity;
			this.velocity[1] = yVelocity;
		}
	}
	
	/**
	 * Variable registering the velocity of this entity.
	 */
	private double[] velocity = new double[2];
	
	/**
	 * Constant reflecting the speed of light.
	 */
	public static final double SPEED_OF_LIGHT = 300000;
	
	/**
	 * Return the total speed of the entity given the velocity 
	 * in the x direction and in the y direction.
	 * 
	 * @param 	velocity
	 * 			The velocity of this entity.
	 * @return	The square root of the sum of the velocity in 
	 * 			the x direction squared with the velocity in the y direction squared.
	 * 			| result == Math.sqrt(Math.pow(velocity[0], 2) + Math.pow(velocity[1], 2))
	 */
	public double getSpeed(double xVelocity, double yVelocity){
		double speed = Math.sqrt(Math.pow(xVelocity, 2) + Math.pow(yVelocity, 2));
		return speed;
	}
	
	/**
	 * Return the radius of this entity.
	 * 	The radius of a entity is the distance between the
	 *  center of the entity and the outer edge of the entity.
	 */
	@Basic @Immutable @Raw
	public double getRadius(){
		return this.radius;
	}
	
	/**
	 * Check whether the given radius is a valid radius for
	 * any entity.
	 * 
	 * @param 	radius
	 * 			The radius to check.
	 * @return	True if and only if the radius is a 
	 * 			real number greater than or equal to the minimum radius.
	 * 			| result == ( (radius >= MIN_RADIUS) && (!Double.isInfinite(radius)) && (!Double.isNaN(radius)) )
	 */
	@Raw
	public abstract boolean isValidRadius(double radius);
	
	/**
	 * Variable registering the radius of this entity.
	 */
	protected double radius;
	
	/**
	 * Return the maximum speed for each entity.
	 */
	@Basic @Immutable @Raw
	public double getMaxSpeed() {
		return this.maxSpeed;
	}

	/**
	 * Check whether the given maximum speed is a valid one for
	 * any entity.
	 *  
	 * @param  maxSpeed
	 *         The maximum speed to check.
	 * @return True if and only if the maximum speed consists of a double between 0 and SPEED_OF_LIGHT.
	 *         | result == ((!Double.isNaN(maxSpeed)) && (0 < maxSpeed) && (maxSpeed< SPEED_OF_LIGHT))
	 */
	@Raw
	public static boolean isValidMaxSpeed(double maxSpeed){
		if ((!Double.isNaN(maxSpeed)) && (0 < maxSpeed) && (maxSpeed<= SPEED_OF_LIGHT)){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Set the maximum speed of this entity to a given speed.
	 * 
	 * @param 	maxSpeed
	 * 			The given maximum speed.
	 * @throws 	IllegalArgumentException
	 * 			The given maximum speed isn't a valid one.
	 * 			| !isValidMaxSpeed(maxSpeed)
	 */
	@Raw
	public void setMaxSpeed(double maxSpeed)
		throws IllegalArgumentException{
		if (!isValidMaxSpeed(maxSpeed)) throw new IllegalArgumentException("The given maximum speed isn't a valid one");
		this.maxSpeed = maxSpeed;
	}
	
	/**
	 * Variable registering the maximum speed of this entity.
	 */
	private double maxSpeed;	
	
	/**
	 * Move this entity for a given duration based on its current
	 * position and current velocity to a new position.
	 * 
	 * @param 	duration
	 * 			The duration for how long the ship moves to its new position.
	 * @effect	The new position of this ship is set to the old position of
	 * 			this ship incremented with the product of the given duration
	 * 			and the velocity of the ship.
	 * 			| setPosition( {(getPositionX() + duration*getVelocityX()),
	 * 			|				(getPositionY() + duration*getVelocityY())} )
	 * @throws 	IllegalArgumentException
	 * 			The given duration is not a valid duration for any ship.
	 * 			| ! isValidDuration(duration)
	 */
	public void move(double duration) throws IllegalArgumentException{
		if (!isValidDuration(duration)) throw new IllegalArgumentException("The given duration " + duration + " isn't a valid one");
		double newX = (getPositionX() + duration*getVelocityX());
		double newY = (getPositionY() + duration*getVelocityY());
		setPosition(newX, newY);
	}
		
	/**
	 * Check whether the given duration is a valid
	 * duration for any entity.
	 * 
	 * @param 	duration
	 * 			The duration to check.
	 * @return	True if and only if the duration is a real number,
	 * 			greater than zero and not infinite.
	 * 			| result == ( (!Double.isNaN(duration)) && (!Double.isInfinite(duration)) && (duration >= 0) )
	 */
	@Model
	public static boolean isValidDuration(double duration){
		if ( (!Double.isNaN(duration)) && (!Double.isInfinite(duration)) && (duration >= 0) ) {
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Return the distance between the centers of this entity and
	 * 	the given entity.
	 * 
	 * @param 	other
	 * 			The given entity.
	 * @return	The distance in km.
	 * 			| @see implementation
	 * @throws 	NullPointerException
	 * 			The other entity is not effective.
	 * 			| other == null
	 */
	public double getDistanceBetweenCenters(Entity other) throws NullPointerException{
		if (other == null) throw new NullPointerException("The other entity is not effective");
		double distance = (Math.sqrt(Math.pow((other.getPositionX() - this.getPositionX()), 2) + Math.pow((other.getPositionY() - this.getPositionY()), 2)));
		return distance;
	}
	
	/**
	 * Return the distance between this entity and the given entity.
	 * 	If the ships overlap this distance will be negative.
	 * 
	 * @param 	other
	 * 			The given entity.
	 * @return	The distance in km.
	 * 			| @see implementation
	 * @return	If the given entity is this entity return 0.
	 * 			| if (this == other)
	 * 			|	result = 0;
	 * @throws	NullPointerException
	 * 			The other entity is not effective.
	 * 			| other = null
	 */
	public double getDistanceBetween(Entity other) throws NullPointerException{
		if (other == null) throw new NullPointerException("The other entity is not effective");
		if (this == other){
			return 0;
		}
		double distance = (Math.sqrt(Math.pow((other.getPositionX() - this.getPositionX()), 2) + Math.pow((other.getPositionY() - this.getPositionY()), 2)) - this.getRadius() - other.getRadius());
		return distance;
	}
	
	/**
	 * Check whether this entity overlaps with a given entity.
	 * 
	 * @param 	other
	 * 			The given entity.
	 * @return	True if and only if the distance between this entity and
	 * 			the given entity is smaller than or equal to zero.
	 * 			| result == (this.getDistanceBetween(entity2) <= 0)
	 * @throws 	NullPointerException
	 * 			The other entity is not effective
	 * 			| other == null
	 */
	public boolean overlap(Entity other) throws NullPointerException{
		if (other == null) throw new NullPointerException("The other entity is not effective");
		if (this.getDistanceBetweenCenters(other) <= 0.99*(this.getRadius()+other.getRadius())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Check whether this entity overlaps with a given other entity that is not 
	 * 	a bullet fired from this ship, or a bullet from the same ship as this entity,
	 * 	or a ship who fired of this entity.
	 * 
	 * @param 	other
	 * 			The given entity.
	 * @return	True if and only if the distance between this entity and
	 * 			the given entity is smaller than or equal to zero, and
	 *			one of the entities is not the source of the other, and they
	 *			don't share the same source, and the entities are not the same entity.
	 * 			| @see implementation
	 * @throws 	NullPointerException
	 * 			The other entity is not effective
	 * 			| other == null
	 */
	@Model
	public boolean overlapFiltered(Entity other) throws NullPointerException {
		if (this == other) {
			return false;
		} else if ((this instanceof Ship) && (other instanceof Bullet) && (((Bullet)other).getShip() == this)){
			return false;
		}else if ((this instanceof Bullet) && (other instanceof Ship) && (((Bullet)this).getShip() == other)){
			return false;
		}else if ((this instanceof Bullet) && (other instanceof Bullet) && (((Bullet)this).getShip() == ((Bullet)other).getShip())){
			return false;
		}else{
			return this.overlap(other);
		}
	}
	
	/**
	 * Return when (i.e. in how many seconds), if ever, this entity 
	 * and a given entity will collide. This method does not apply to 
	 * entities that overlap while they shouldn't. If this entity and the given 
	 * entity never collide, return positive infinity.
	 * 
	 * @param 	other
	 * 			The given entity.
	 * @return 	The resulting time to collision is a double greater than zero.
	 * 			| result > 0
	 * @return	No smaller double that specifies the time to collision between
	 * 			this entity and the given entity exists.
	 * 			| for each double < result:
	 * 			| 	this.getDistanceBetween(other) > 0
	 * @throws 	IllegalArgumentException
	 * 			The entities overlap while they shouldn't.
	 * 			| this.overlapFiltered(entity2)
	 * @throws 	NullPointerException
	 * 			The other entity is not effective
	 * 			| other == null
	 */
	@Model
	public double getTimeToCollision(Entity other)
			throws IllegalArgumentException, NullPointerException{
		if (other == null) throw new NullPointerException("The other entity is not effective");
		if (this.overlapFiltered(other)) throw new IllegalArgumentException("This method does not apply to entitys that overlap");
		if (this.getWorld() != other.getWorld()){
			return Double.POSITIVE_INFINITY;
		}
		double diffX = other.getPositionX() - this.getPositionX();
		double diffY = other.getPositionY() - this.getPositionY();
		double diffVX = other.getVelocityX() - this.getVelocityX();
		double diffVY = other.getVelocityY() - this.getVelocityY();
		double distanceCentersSquared = Math.pow((this.getRadius() + other.getRadius()), 2);
		double varD = (Math.pow((diffX*diffVX + diffY*diffVY), 2) - (Math.pow(diffVX,2) + Math.pow(diffVY,2))*((Math.pow(diffX,2) + Math.pow(diffY,2)) - distanceCentersSquared));
		if ((diffVX*diffX + diffVY*diffY) >= 0 || varD <= 0){
			return Double.POSITIVE_INFINITY;
		}else{
			double timeToCollision = -(diffVX*diffX + diffVY*diffY + Math.sqrt(varD))/((Math.pow(diffVX,2) + Math.pow(diffVY,2)));
			
			return timeToCollision;
		}
	}
	
	/**
	 * Return where, if ever, this entity and the given entity will collide. 
	 * The method shall return null if the entities never collide. 
	 * This method does not apply to entities that overlap while they shouldn't.
	 * 
	 * @param 	other
	 * 			The given entity.
	 * @return	Null if this entity never collides with
	 * 			the given entity.
	 * 			| if (getTimeToCollision(entity2) == Double.POSITIVE_INFINITY)
	 * 			|	then result == null
	 * 			Otherwise, the position of the collision between 
	 * 			this entity and the given entity.
	 * 			| else
	 * 			|	result == {collisionX2 + entity2.getRadius()*Math.cos(angleCenters), 
	 * 			|			   collisionY2 + entity2.getRadius()*Math.sin(angleCenters)}
	 * @throws	IllegalArgumentException
	 * 			The entities overlap while they shouldn't.
	 * 			| this.overlapFiltered(entity2)
	 * @throws 	NullPointerException
	 * 			The other entity is not effective
	 * 			| other == null
	 */
	public double[] getCollisionPosition(Entity other) throws NullPointerException, IllegalArgumentException {
		if (other == null) throw new NullPointerException("The other entity is not effective");
		if (this.overlapFiltered(other)) throw new IllegalArgumentException("This method does not apply to entitys that overlap");
		
		if (this.getTimeToCollision(other) == Double.POSITIVE_INFINITY){
			return null;
		}else{
			double duration = this.getTimeToCollision(other);
			double collisionX1 = (this.getPositionX() + duration*this.getVelocityX());
			double collisionY1 = (this.getPositionY() + duration*this.getVelocityY());
			double collisionX2 = (other.getPositionX() + duration*other.getVelocityX());
			double collisionY2 = (other.getPositionY() + duration*other.getVelocityY());
			double diffX = collisionX2 - collisionX1;
			double diffY = collisionY2 - collisionY1;
			double angleCenters = 0;
			if (diffX*diffY >= 0){
				if (diffX == 0){
					if (diffY > 0){
						angleCenters = -(Math.PI/2);
					}else{
						angleCenters = (Math.PI/2);
					}
				}else if ((diffX > 0) || (diffY == 0)){
					angleCenters = Math.atan(diffY/diffX) + Math.PI;
				}else if((diffX < 0) || (diffY == 0)){
					angleCenters = Math.atan(diffY/diffX);
				}
			}else {
				if (diffY > 0){
					angleCenters = Math.atan(diffY/diffX);
				}else if(diffY < 0){
					angleCenters = Math.atan(diffY/diffX) + Math.PI;
				}
			}
			
			double[] collisionPoint = {collisionX2 + other.getRadius()*Math.cos(angleCenters), collisionY2 + other.getRadius()*Math.sin(angleCenters)};
			return collisionPoint;
		}
	}
	
	/**
	 * Return a boolean indicating whether or not this entity apparently
	 * 	collides with the given entity.
	 * 
	 * @param 	other
	 * 			The given entity.
	 * @return	True if and only if the distance between the centers of
	 * 			the entities is larger then 0.99*(sum of the radii) and 
	 * 			smaller then 1.01*(sum of the radii).
	 * 			| @see implementation
	 */
	public boolean apparentlyCollide(Entity other) {
		if ( (this.getDistanceBetweenCenters(other) > 0.99*(this.getRadius() + other.getRadius()))
				&& (this.getDistanceBetweenCenters(other) < 1.01*(this.getRadius() + other.getRadius())) )
			return true;
		else return false;
	}
	
	/**
	 * Return the mass of this entity.
	 * 	The mass of an entity is weight in kg.
	 */
	@Basic @Immutable @Model
	public double getMass() {
		return this.mass;
	}
	
	/**
	 * Variable registering the mass of this entity.
	 */
	public double mass;
	
	/**
	 * Resolves the collision between this entity and
	 * 	a boundary of the given world.
	 * 
	 * @param 	world
	 * 			The given world.
	 */
	public void collidesWithBoundary(World world){
		if (world.getDistanceToNearestHorizontalBoundary(this) <
				world.getDistanceToNearestVerticalBoundary(this) )
			this.setVelocity(getVelocityX(), -getVelocityY());
		else
			this.setVelocity(-getVelocityX(), getVelocityY());
	}

	/**
	 * Return the world this entity belongs to.
	 * 	Returns null if this entity does not belong to a world.
	 */
	@Model
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Return a boolean indicating whether or not this 
	 * 	entity belongs to a world.
	 * 
	 * @return	True if and only if this entity belongs to a world.
	 * 			| @see implementation
	 */
	@Model
	public boolean hasWorld(){
		return this.world != null;
	}
	
	/**
	 * Set the world of this entity to the given world.
	 * 
	 * @param	world
	 * 			The given world
	 * @post	...
	 * 			new.getWorld() == world
	 * @throws	IllegalArgumentException
	 * 			| (world == null) || (this.hasWorld())
	 */
	@Raw
	public void setWorld(World world) throws IllegalArgumentException {
		if (this.hasWorld())
			throw new IllegalArgumentException ("An entity can be a part of max 1 world at a time");
		this.world = world;
	}
	
	/**
	 * Variable registering the world this entity belongs to.
	 */
	public World world = null;
	
	/**
	 * Remove this entity from its world.
	 * 
	 * @post	The world of this entity is null.
	 * 			| (new entity).getWorld() == null;
	 */
	@Raw
	protected void removeFromWorld(){
    	this.world = null;
	}
	
	/**
	 * Terminate this entity.
	 * 
	 * @post	This entity is terminated.
	 * 			| (new entity).isTerminated()
	 * @post	The world of this entity does not contain
	 * 			the entity anymore.
	 * 			| !(old entity).getWorld().getAllEntities().contains(this)
	 */
	public void terminate() {
		if (!this.isTerminated) {
			if (this.getWorld() != null)
				this.getWorld().removeEntity(this);
			this.isTerminated = true;
		}
	}
	
	/**
	 * Return a boolean indicating whether or not this entity is terminated.
	 */
	@Basic @Raw @Model
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	/**
	 * Variable registering whether or not this entity is terminated.
	 */
	private boolean isTerminated = false;
	
	/**
	 * Resolve a collision between 2 ships or 2 minor planets.
	 * 
	 * @param other
	 */
	public void bouncesOff(Entity other) {
		double diffX = other.getPositionX() - this.getPositionX();
		double diffY = other.getPositionY() - this.getPositionY();
		double diffVX = other.getVelocityX() - this.getVelocityX();
		double diffVY = other.getVelocityY() - this.getVelocityY();
		double sumRadii = this.getRadius() + other.getRadius();
		double massThis = this.getMass();
		double massOther = other.getMass();
		double J = (2*massThis*massOther*(diffVX*diffX + diffVY*diffY))/(sumRadii*(massThis+massOther));
		double Jx = (J*diffX)/sumRadii;
		double Jy = (J*diffY)/sumRadii;
		double newXVelocityThis = this.getVelocityX() + Jx/massThis;
		double newYVelocityThis = this.getVelocityY() + Jy/massThis;
		double newXVelocityOther = other.getVelocityX() - Jx/massOther;
		double newYVelocityOther = other.getVelocityY() - Jy/massOther;
		this.setVelocity(newXVelocityThis, newYVelocityThis);
		other.setVelocity(newXVelocityOther, newYVelocityOther);
	}
	
}	

