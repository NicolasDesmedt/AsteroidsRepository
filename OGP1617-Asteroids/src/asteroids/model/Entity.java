package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

public abstract class Entity {
	
	protected Entity(double x, double y, double xVelocity, double yVelocity, double radius, double mass)
			throws IllegalArgumentException{
			this(x, y, xVelocity, yVelocity, radius, mass, SPEED_OF_LIGHT);
		}
	
	protected Entity(double x, double y, double xVelocity, double yVelocity, double radius, double mass, double maxSpeed)
		throws IllegalArgumentException{
		this.setMaxSpeed(maxSpeed);
		this.setPosition(x,y);
		this.setVelocity(xVelocity, yVelocity);
		if (!isValidRadius(radius)) throw new IllegalArgumentException("The given radius isn't a valid one");
		this.radius = radius;
		this.setMass(mass);
	}
	
	/**
	 * Variable registering the position of this entity.
	 */
	private double[] position = new double[2];
	
	/**
	 * Return the position of the entity.
	 * 	The position of an entity locates the entity in an unbounded two-dimensional space.
	 */
	@Basic @Raw 
	public double[] getPosition(){
		return position.clone();
	}
	
	/**
	 * Return the xcoordinate.
	 */
	public double getPositionX() {
		return position[0];
	}
	
	/**
	 * Return the ycoordinate.
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
	@Raw
	public static boolean isValidPosition(double[] position){
		if ( (!Double.isNaN(position[0])) && (!Double.isNaN(position[1])) && 
				(!Double.isInfinite(position[0])) && (!Double.isInfinite(position[1])) && (position.length == 2) ) {
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
	 * Return the velocity of this entity.
	 * 	The velocity of a entity consists of a component in the x direction 
	 *  and a component in the y direction who determine the vessel’s movement 
	 *  per time unit in the x and y direction respectively.
	 */
	@Basic @Raw
	public double[] getVelocity(){
		return velocity;
	}
	
	public double getVelocityX() {
		return velocity[0];
	}
	
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
	private final double radius;
	
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
	
	@Raw
	public void setMaxSpeed(double maxSpeed)
		throws IllegalArgumentException{
		if (!isValidMaxSpeed(maxSpeed)) throw new IllegalArgumentException("The given maximum speed isn't a valid one");
		this.maxSpeed = maxSpeed;
	}
	
	/**
	 * Variable registering the radius of this entity.
	 */
	private double maxSpeed;	
	
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
	public static boolean isValidDuration(double duration){
		if ( (!Double.isNaN(duration)) && (!Double.isInfinite(duration)) && (duration >= 0) ) {
			return true;
		}
		else{
			return false;
		}
	}
	
	public double getDistanceBetweenCenters(Entity other) throws NullPointerException{
		if (other == null) throw new NullPointerException("The other entity is not effective");
		double distance = (Math.sqrt(Math.pow((other.getPositionX() - this.getPositionX()), 2) + Math.pow((other.getPositionY() - this.getPositionY()), 2)));
		return distance;
	}
	
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
	@Model
	public boolean overlapFiltered(Entity other) throws NullPointerException{
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
	 * entitys that overlap. If this entity and the given entity never collide,
	 * return positive infinity.
	 * 
	 * @param 	other
	 * 			The given entity.
	 * @return 	The resulting time to collision is a double greater than zero.
	 * 			| result > 0
	 * @return	No smaller double that specifies the time to collision between
	 * 			this entity and the given entity exists.
	 * 			| for each double < result:
	 * 			| 	this.getDistanceBetween(other) > 0
	 * @throws 	IllegalStateException
	 * 			The entitys overlap.
	 * 			| this.overlap(entity2)
	 * @throws 	NullPointerException
	 * 			The other entity is not effective
	 * 			| other == null
	 */
	@Model
	public double getTimeToCollision(Entity other)
			throws IllegalArgumentException, NullPointerException{
		if (other == null) throw new NullPointerException("The other entity is not effective");
		if (this.overlapFiltered(other)) throw new IllegalArgumentException("This method does not apply to entitys that overlap");
		double diffX = other.getPosition()[0] - this.getPosition()[0];
		double diffY = other.getPosition()[1] - this.getPosition()[1];
		double diffVX = other.getVelocity()[0] - this.getVelocity()[0];
		double diffVY = other.getVelocity()[1] - this.getVelocity()[1];
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
	 * The method shall return null if the entitys never collide. 
	 * This method does not apply to entitys that overlap.
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
	 * @throws	IllegalStateException
	 * 			The entitys overlap.
	 * 			| this.overlap(entity2)
	 * @throws 	NullPointerException
	 * 			The other entity is not effective
	 * 			| other == null
	 */
	public double[] getCollisionPosition(Entity other) throws NullPointerException, IllegalStateException{
		if (other == null) throw new NullPointerException("The other entity is not effective");
		if (this.overlapFiltered(other)) throw new IllegalArgumentException("This method does not apply to entitys that overlap");
		
		if (this.getTimeToCollision(other) == Double.POSITIVE_INFINITY){
			return null;
		}else{
			double duration = this.getTimeToCollision(other);
			double collisionX1 = (this.getPosition()[0] + duration*this.getVelocity()[0]);
			double collisionY1 = (this.getPosition()[1] + duration*this.getVelocity()[1]);
			double collisionX2 = (other.getPosition()[0] + duration*other.getVelocity()[0]);
			double collisionY2 = (other.getPosition()[1] + duration*other.getVelocity()[1]);
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
	
	public boolean apparentlyCollide(Entity other) {
		if ( (this.getDistanceBetweenCenters(other) > 0.99*(this.getRadius() + other.getRadius()))
				&& (this.getDistanceBetweenCenters(other) < 1.01*(this.getRadius() + other.getRadius())) )
			return true;
		else return false;
	}
	
	@Basic @Immutable
	public double getMass() {
		return this.mass;
	}
	
	@Raw
	public void setMass(double mass){
		if (!isValidMass(mass)){
			if (isValidMass(this.getMass())){
				return;
			}else{
			this.mass = this.calculateMinimalMass();
			}
		}else{
			this.mass = mass;
		}
	}
	
	@Basic
	public double getDensity(double mass) {
		double density = ((mass*3)/(4*(Math.PI)*Math.pow(this.getRadius(),3)));
		return density;
	}
	
	abstract double calculateMinimalMass();
	
	abstract boolean isValidMass(double mass);
	
	abstract void collidesWithBoundary(World world);

	/**
	 * Set the world of this space object to the given world.
	 * 
	 * @param	world
	 * 			...
	 * @post	...
	 * 			new.getWorld() == world
	 * @throws	IllegalArgumentException
	 * 			( world == null || !world.getAllSpaceObjects().contains(this) )
	 */
	@Raw
	public void setWorld(World world) throws IllegalArgumentException {
		if (world == null)
			throw new IllegalArgumentException ("Not a valid world for this entity");
		this.world = world;
	}
	
	public boolean hasWorld(){
		return this.world != null;
	}
	
	@Model
	public World getWorld() {
		return this.world;
	}
	
	private World world = null;
	
	/**
	 * Variable registering the mass of this entity.
	 */
	public double mass;
	
	@Raw
	protected void removeFromWorld(){
    	this.world = null;
	}
	protected void terminate() {
		if (!this.isTerminated) {
			if (this.getWorld() != null)
				this.getWorld().removeEntity(this);
			this.isTerminated = true;
		}
	}
	
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	private boolean isTerminated = false;
	
}	

