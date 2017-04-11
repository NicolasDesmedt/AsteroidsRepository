package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

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
	 * Variable registering the position of this ship.
	 */
	private double[] position = new double[2];
	
	/**
	 * Return the position of the entity.
	 * 	The position of an entity locates the ship in an unbounded two-dimensional space.
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
	 * any ship.
	 *  
	 * @param  position
	 *         The position to check.
	 * @return True if and only if the position consists of a double containing two real numbers who are not infinite.
	 *         | result == ( (!Double.isNaN(position[0])) && (!Double.isNaN(position[1])) &&
	 *         |			 (!Double.isInfinite(position[0])) && (!Double.isInfinite(position[0])) && (position.length == 2) )
	 */
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
	 * Set the position of this ship to the given position.
	 * 
	 * @param 	position
	 * 			The new position for this ship.
	 * @post	The position of this ship is equal to the given position.
	 *       	| new.getPosition() == position
	 * @throws 	IllegalArgumentException
	 *  		The given position is not a valid position for any ship.
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
	 * Return the velocity of this ship.
	 * 	The velocity of a ship consists of a component in the x direction 
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
	 * any ship.
	 *  
	 * @param  velocity
	 *         The velocity to check.
	 * @return True if and only if the velocity consists of a double containing two real numbers.
	 *         | result == ( (!Double.isNaN(velocity[0])) && (!Double.isNaN(velocity[1])) && (velocity.length == 2) )
	 */
	public static boolean isValidVelocity(double[] velocity){
		if ( (!Double.isNaN(velocity[0])) && (!Double.isNaN(velocity[1])) && (velocity.length == 2) ){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Set the velocity of this ship to the given velocity.
	 * 
	 * @param 	velocity
	 * 			The new velocity for this ship.
	 * @post	If the given velocity does not result in a total speed 
	 * 			which exceeds the maximum speed for all ships, 
	 * 			the velocity of this ship is equal to the given velocity.
	 * 			| if ( getSpeed(velocity) < maxSpeed )
	 * 			|	then new.getVelocity() == velocity
	 * @post	If the given velocity does result in a total speed which 
	 * 			exceeds the maximum speed for all ships and is not infinite,
	 * 			the velocity of this ship is reduced until the total speed
	 * 			equals the maximum speed, without changing the ratio between 
	 * 			the given velocity in the x direction and in the y direction.
	 * 			| if ( getSpeed(velocity) > maxSpeed )
	 * 			| 	then ( (new.getVelocity()[0] == (velocity[0]*maxSpeed/getSpeed(velocity))) 
	 * 			|		 && (new.getVelocity()[1] == (velocity[1]*maxSpeed/getSpeed(velocity))) )
	 * @post	If the given velocity is infinite in the positive 
	 * 			or negative x direction and not infinite in the y direction, 
	 * 			the velocity of this ship is equal to 
	 * 			the maximum speed in that direction.
	 * 			| if ( Double.isInfinite(velocity[0]) && (velocity[0] > 0) )
	 * 			|	then new.getVelocity()[0] == maxSpeed
	 * 			| else if ( Double.isInfinite(velocity[0]) && (velocity[0] < 0) )
	 * 			|	then new.getVelocity()[0] == - maxSpeed ) )
	 * 			| new.getVelocity()[1] == 0
	 * @post	If the given velocity is infinite in the positive 
	 * 			or negative y direction and not infinite in the x direction, 
	 * 			the velocity of this ship is equal to 
	 * 			the maximum speed in that direction.
	 * 			| if ( Double.isInfinite(velocity[1]) && (velocity[1] > 0) )
	 * 			|	then new.getVelocity[1] == maxSpeed
	 * 			| else if ( Double.isInfinite(velocity[1]) && (velocity[1] < 0) )
	 * 			|	then new.getVelocity[1] == - maxSpeed ) )
	 * 			| new.getVelocity[0] == 0
	 * @post	If the given velocity is infinite in both the 
	 * 			negative or positive x and the negative or positive y direction,
	 * 			the velocity of this ship is equal to the maximum speed
	 * 			in the direction PI/4 radians from the axes, with signs of
	 * 			both the velocity in the x direction as in the y direction
	 * 			equal to the signs of the given velocity in their respective directions.
 	 *			| if (Double.isInfinite(velocity[0]) && (Double.isInfinite(velocity[1])))
	 *			|	if (velocity[0] > 0)
	 *			|		then new.getVelocity[0] == maxSpeed/Math.sqrt(2)
	 *		 	|	else
	 *			|		then new.getVelocity[0] == -maxSpeed/Math.sqrt(2)
	 *			|	if (velocity[1] > 0)
	 *			|		then new.getVelocity[1] == maxSpeed/Math.sqrt(2)
	 *		 	|	else
	 *			|		then new.getvelocity[1] == -maxSpeed/Math.sqrt(2)
	 * @post	If the given velocity in the x or y direction is not a number,
	 * 			the velocity of this ship in that direction is zero.
	 * 			| if (Double.isNaN(velocity[0])) {
	 *		    |	then new.getVelocity() == setVelocity({0,velocity[1]})
	 *			| if (Double.isNaN(velocity[1])) {
	 *			|	then new.getVelocity() == setVelocity({velocity[0],0})
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
	 * Variable registering the velocity of this ship.
	 */
	private double[] velocity = new double[2];
	
	/**
	 * Constant reflecting the speed of light.
	 */
	public static final double SPEED_OF_LIGHT = 300000;
	
	/**
	 * Return the total speed of the ship given the velocity 
	 * in the x direction and in the y direction.
	 * 
	 * @param 	velocity
	 * 			The velocity of this ship.
	 * @return	The square root of the sum of the velocity in 
	 * 			the x direction squared with the velocity in the y direction squared.
	 * 			| result == Math.sqrt(Math.pow(velocity[0], 2) + Math.pow(velocity[1], 2))
	 */
	public double getSpeed(double xVelocity, double yVelocity){
		double speed = Math.sqrt(Math.pow(xVelocity, 2) + Math.pow(yVelocity, 2));
		return speed;
	}
	
	/**
	 * Return the radius of this ship.
	 * 	The radius of a ship is the distance between the
	 *  center of the ship and the outer edge of the ship.
	 */
	@Basic @Immutable @Raw
	public double getRadius(){
		return this.radius;
	}
	
	/**
	 * Check whether the given radius is a valid radius for
	 * any ship.
	 * 
	 * @param 	radius
	 * 			The radius to check.
	 * @return	True if and only if the radius is a 
	 * 			real number greater than or equal to the minimum radius.
	 * 			| result == ( (radius >= MIN_RADIUS) && (!Double.isInfinite(radius)) && (!Double.isNaN(radius)) )
	 */
	public abstract boolean isValidRadius(double radius);
	
	/**
	 * Variable registering the radius of this ship.
	 */
	private final double radius;
	
	/**
	 * Return the maximum speed for each ship.
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
	 * Variable registering the radius of this ship.
	 */
	private double maxSpeed;
	
	
	
	public void move(double duration)
			throws IllegalArgumentException{
			if (! isValidDuration(duration)) throw new IllegalArgumentException("The given duration isn't a valid one");
			double newX = (getPosition()[0] + duration*getVelocity()[0]);
			double newY = (getPosition()[1] + duration*getVelocity()[1]);
			setPosition(newX, newY);
		}
		
		/**
		 * Check whether the given duration is a valid
		 * duration for any ship.
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
		
		public double getDistanceBetween(Entity other) throws NullPointerException{
			if (other == null) throw new NullPointerException("The other ship is not effective");
			if (this == other){
				return 0;
			}
			double distance = (Math.sqrt(Math.pow((other.getPositionX() - this.getPositionX()), 2) + Math.pow((other.getPositionY() - this.getPositionY()), 2)) - this.getRadius() - other.getRadius());
			return distance;
		}
		
		/**
		 * Check whether this ship overlaps with a given ship.
		 * 
		 * @param 	other
		 * 			The given ship.
		 * @return	True if and only if the distance between this ship and
		 * 			the given ship is smaller than or equal to zero.
		 * 			| result == (this.getDistanceBetween(ship2) <= 0)
		 * @throws 	NullPointerException
		 * 			The other ship is not effective
		 * 			| other == null
		 */
		public boolean overlap(Entity other) throws NullPointerException{
			if (other == null) throw new NullPointerException("The other ship is not effective");
			if (this.getDistanceBetween(other) <= 0){
				return true;
			}else{
				return false;
			}
		}
		
		/**
		 * Return when (i.e. in how many seconds), if ever, this ship 
		 * and a given ship will collide. This method does not apply to 
		 * ships that overlap. If this ship and the given ship never collide,
		 * return positive infinity.
		 * 
		 * @param 	other
		 * 			The given ship.
		 * @return 	The resulting time to collision is a double greater than zero.
		 * 			| result > 0
		 * @return	No smaller double that specifies the time to collision between
		 * 			this ship and the given ship exists.
		 * 			| for each double < result:
		 * 			| 	this.getDistanceBetween(other) > 0
		 * @throws 	IllegalStateException
		 * 			The ships overlap.
		 * 			| this.overlap(ship2)
		 * @throws 	NullPointerException
		 * 			The other ship is not effective
		 * 			| other == null
		 */
		public double getTimeToCollision(Entity other)
				throws IllegalStateException, NullPointerException{
			if (other == null) throw new NullPointerException("The other ship is not effective");
			if (this.overlap(other)) throw new IllegalStateException("This method does not apply to ships that overlap");
			double diffX = other.getPosition()[0] - this.getPosition()[0];
			double diffY = other.getPosition()[1] - this.getPosition()[1];
			double diffVX = other.getVelocity()[0] - this.getVelocity()[0];
			double diffVY = other.getVelocity()[1] - this.getVelocity()[1];
			double distanceCentersSquared = Math.pow((this.getRadius() + other.getRadius()), 2);
			double varD = (Math.pow((diffX*diffVX + diffY*diffVY), 2) - (Math.pow(diffVX,2) + Math.pow(diffVY,2))*((Math.pow(diffX,2) + Math.pow(diffY,2)) - distanceCentersSquared));
			if ((diffVX*diffX + diffVY*diffY) >= 0 || varD <= 0){
				return Double.POSITIVE_INFINITY;
			}else{
				double timeToCollision = -(((diffVX*diffX + diffVY*diffY) + Math.sqrt(varD))/(Math.pow(diffVX,2) + Math.pow(diffVY,2)));
				return timeToCollision;
			}
		}
		
		/**
		 * Return where, if ever, this ship and the given ship will collide. 
		 * The method shall return null if the ships never collide. 
		 * This method does not apply to ships that overlap.
		 * 
		 * @param 	other
		 * 			The given ship.
		 * @return	Null if this ship never collides with
		 * 			the given ship.
		 * 			| if (getTimeToCollision(ship2) == Double.POSITIVE_INFINITY)
		 * 			|	then result == null
		 * 			Otherwise, the position of the collision between 
		 * 			this ship and the given ship.
		 * 			| else
		 * 			|	result == {collisionX2 + ship2.getRadius()*Math.cos(angleCenters), 
		 * 			|			   collisionY2 + ship2.getRadius()*Math.sin(angleCenters)}
		 * @throws	IllegalStateException
		 * 			The ships overlap.
		 * 			| this.overlap(ship2)
		 * @throws 	NullPointerException
		 * 			The other ship is not effective
		 * 			| other == null
		 */
		public double[] getCollisionPosition(Entity other) throws NullPointerException, IllegalStateException{
			if (other == null) throw new NullPointerException("The other ship is not effective");
			if (this.overlap(other)) throw new IllegalStateException("This method does not apply to ships that overlap");
			
			if (getTimeToCollision(other) == Double.POSITIVE_INFINITY){
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
							angleCenters = (Math.PI/2);
						}else{
							angleCenters = -(Math.PI/2);
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
		
		public double getDistanceBetweenRadii(Entity other) {
			double distance = (Math.sqrt(Math.pow((other.getPositionX() - this.getPositionX()), 2) 
								+ Math.pow((other.getPositionY() - this.getPositionY()), 2)) );
			return distance;
		}
		
		public boolean apparentlyCollide(Entity other) {
			if ( (this.getDistanceBetweenRadii(other) > 0.99*(this.getRadius() + other.getRadius()))
					&& (this.getDistanceBetweenRadii(other) < 1.01*(this.getRadius() + other.getRadius())) )
				return true;
			else return false;
		}
		
//		public void collidesWithBoundary(World world) {
//			if (world.getDistanceToNearestHorizontalBoundary(this) <
//					world.getDistanceToNearestVerticalBoundary(this) )
//				this.setVelocity(getVelocityX(), -getVelocityY());
//			else
//				this.setVelocity(-getVelocityX(), getVelocityY());
//		}
		
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
		protected void setWorld(@Raw World world) throws IllegalArgumentException {
			if ((!world.getAllEntities().contains(this)) || (world == null))
				throw new IllegalArgumentException ("Not a valid world for this entity");
			this.world = world;
		}
		
		public World getWorld() {
			return this.world;
		}
		
		public World world;
		
		/**
		 * Variable registering the mass of this ship.
		 */
		public double mass;
		
		@Raw
		protected void removeFromWorld() throws IllegalArgumentException {
	    	this.world = null;
		}
		
		public void terminate(){
			if (this.getWorld() != null){
				this.removeFromWorld();
			}
			this.isTerminated = true;
		}
		
		public boolean isTerminated(){
			return this.isTerminated;
		}
		
		private boolean isTerminated = false;
		
	}	
