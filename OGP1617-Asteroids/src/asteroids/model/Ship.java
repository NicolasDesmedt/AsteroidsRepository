package asteroids.model;

import java.lang.Math;

import asteroids.util.ModelException;

/**
 * A class for flying a ship in space involving the position, radius, velocity
 * and orientation of the ship. With facilities to move the ship by accelerating
 * and turning and a facility to predict the time and location of it colliding 
 * with an other ship.
 * 
 * @invar	The position of each ship must be a valid position for any ship.
 * 			| isValidPosition(getPosition())
 * @invar	The velocity of each ship must be a valid velocity for any ship.
 * 			| isValidVelocity(getVelocity())
 * @invar	The radius of each ship must be a valid radius for any ship.
 * 			| isValidRadius(getRadius())
 * @invar	The orientation of each ship must be a valid orientation for any ship.
 * 			| isValidOrientation(getOrientation())
 * 
 * @version	1.0
 * @author 	Lucas Desard and Nicolas Desmedt
 */
public class Ship {
	
	/**
	 * Variable registering the maximum speed, being the speed of light, that applies to all ships.
	 */
	private static final double MAX_SPEED = 300000;
	
	/**
	 * Variable registering the minimum radius that applies to all ships.
	 */
	private static final double MIN_RADIUS = 10;
	
	/**
	 * Variable registering the position of this ship.
	 */
	private double[] position = new double[2];
	
	/**
	 * Variable registering the velocity of this ship.
	 */
	private double[] velocity = new double[2];
	
	/**
	 * Variable registering the orientation of this ship.
	 */
	private double orientation;
	
	/**
	 * Variable registering the radius of this ship.
	 */
	private double radius;
	
	
	/**
	 * Initialize this new ship with given position, given velocity, given radius and given orientation.
	 * 
	 * @param 	x
	 * 			The x-coordinate of the position of this new ship (in km).
	 * @param 	y
	 * 			The y-coordinate of the position of this new ship (in km).
	 * @param 	xVelocity
	 * 			The movement per unit time of this new ship in the x direction (in km/s).
	 * @param 	yVelocity
	 * 			The movement per unit time of this new ship in the y direction (in km/s).
	 * @param 	radius
	 * 			The radius of this new circle-shaped ship (in km).
	 * @param 	orientation
	 * 			The direction in which this new ship is faced (in radians).
	 * @post	The new position of this new ship is equal to the given position.
	 * 			| new.getPosition() == double[] {x,y}
	 * @post	The new velocity of this new ship is equal to the given velocity.
	 * 			| new.getVelocity() == double[] {xVelocity,yVelocity}
	 * @post	The new radius of this new ship is equal to the given radius.
	 * 			| new.getRadius == radius
	 * @post	The new orientation of this new ship is equal to the given orientation.
	 * 			| new.getOrientation == orientation
	 * @throws 	IllegalArgumentException
	 * 			The given argument is not a valid argument for a ship.
	 * 			| (! isValidPosition(position)) ||  (! isValidRadius(radius)) 
	 */
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation)
		throws IllegalArgumentException{
		this.setPosition(new double[] {x,y});
		this.setVelocity(new double[] {xVelocity,yVelocity});
		this.setRadius(radius);
		this.setOrientation(orientation);
	}
	
	/**
	 * Initialize this new ship in the origin of the axes with zero velocity, with a radius 
	 * set to its lowest possible value and with an orientation facing up at an angle of PI/2.
	 * 
	 * @effect	This new ship is initialized with the x-coordinate, y-coordinate, 
	 * 			xVelocity and yVelocity being zero, the radius being of minimal value MIN_RADIUS,
	 * 			and the orientation of the ship being PI/2.
	 * 			| this(0,0,0,0,MIN_RADIUS,(Math.PI/2));
	 */
	public Ship(){
		this(0,0,0,0,MIN_RADIUS,(Math.PI/2));
	}
	
	/**
	 * Return the position of this ship.
	 * 	The position of a ship locates the ship in an unbounded two-dimensional space.
	 */
	public double[] getPosition(){
		return this.position;
	}
	
	/**
	 * Set the position of this ship to the given position.
	 * 
	 * @param 	position
	 * 			The new position for this ship.
	 * @post	The position of this new ship is equal to the given position.
	 *       	| new.getPosition() == position
	 * @throws 	IllegalArgumentException
	 *  		The given position is not a valid position for any ship.
	 *       	| ! isValidPosition(position)
	 */
	public void setPosition(double[] position)
		throws IllegalArgumentException{
		if (!isValidPosition(position)) throw new IllegalArgumentException("The given position isn't a valid one");
		this.position = position;
	}
	
	/**
	 * Check whether the given position is a valid position for
	 * any ship.
	 *  
	 * @param  position
	 *         The position to check.
	 * @return True if and only if the position consists of a double containing two real numbers who are not infinite.
	 *         | result == ! ((Double.isNaN(position[0]) || Double.isNaN(position[1]) 
	 *         				|| Double.isInfinite(position[0]) || Double.isInfinite(position[0]) || (position.length != 2))
	 */
	public static boolean isValidPosition(double[] position){
		if (Double.isNaN(position[0]) || Double.isNaN(position[1]) || Double.isInfinite(position[0]) || Double.isInfinite(position[0]) || (position.length != 2)) {
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Return the velocity of this ship.
	 * 	The velocity of a ship consists of a component in the x direction 
	 *  and a component in the y direction who determine the vessel’s movement 
	 *  per time unit in the x and y direction respectively.
	 */
	public double[] getVelocity(){
		return this.velocity;
	}
	
	/**
	 * Set the velocity of this ship to the given velocity.
	 * 
	 * @param 	velocity
	 * 			The new velocity for this ship.
	 * @post	If the given velocity does not result in a total speed 
	 * 			which exceeds the maximum speed for all ships, 
	 * 			the velocity of this ship is equal to the given velocity.
	 * 			| if ( getSpeed(velocity) < MAX_SPEED )
	 * 			|	then new.getVelocity() == velocity
	 * @post	
	 */
	public void setVelocity(double[] velocity){
		double speed = getSpeed(velocity);
		if (Double.isInfinite(velocity[0]) && (Double.isInfinite(velocity[1]))){
			if (velocity[0] > 0){
				velocity[0] = MAX_SPEED/Math.sqrt(2);
			} else{
				velocity[0] = -MAX_SPEED/Math.sqrt(2);
			}
			if (velocity[1] > 0){
				velocity[1] = MAX_SPEED/Math.sqrt(2);
			} else{
				velocity[1] = -MAX_SPEED/Math.sqrt(2);
			}
		} else if (Double.isInfinite(velocity[0])){
			if (velocity[0] > 0){
				velocity[0] = MAX_SPEED;
			} else{
				velocity[0] = -MAX_SPEED;
			}
			velocity[1] = 0;
		} else if (Double.isInfinite(velocity[1])){
			if (velocity[1] > 0){
				velocity[1] = MAX_SPEED;
			} else{
				velocity[1] = -MAX_SPEED;
			}
			velocity[0] = 0;
		} else if (speed > MAX_SPEED){
			this.velocity[0] = (velocity[0]*MAX_SPEED)/speed;
			this.velocity[1] = (velocity[1]*MAX_SPEED)/speed;
		} 
		else{
			this.velocity = velocity;
		} 
	}
	
	public static boolean isValidVelocity(double[] velocity){
		if (Double.isNaN(velocity[0]) || Double.isNaN(velocity[1]) || (velocity.length != 2)) {
			return false;
		}
		else{
			return true;
		}
	}
	
	public double getRadius(){
		return this.radius;
	}

	public void setRadius(double radius)
			throws IllegalArgumentException{
		if (!isValidRadius(radius)) throw new IllegalArgumentException("The given radius isn't a valid one");
		this.radius = radius;
		return;
	}
	
	public double getOrientation(){
		return this.orientation;
	}
	public void setOrientation(double orientation){
		assert isValidOrientation(orientation);
		this.orientation = orientation;
	}
	
	public boolean isValidOrientation(double orientation){
		return (orientation>=0 && orientation<=2*Math.PI && !Double.isNaN(orientation));
	}
	
	public double getSpeed(double[] velocity){
		if (Double.isNaN(velocity[0]) || Double.isNaN(velocity[1])){
			setVelocity(new double[] {0,0});
		}
		double speed = Math.sqrt(Math.pow(this.getVelocity()[0], 2) + Math.pow(this.getVelocity()[1], 2));
		return speed;
	}
	
	public static boolean isValidRadius(double radius){
		if ((radius < MIN_RADIUS) || (Double.isInfinite(radius)) || (Double.isNaN(radius))) {
			return false;
		}
		else{
			return true;
		}
	}
	
	public void move(double duration)
		throws IllegalArgumentException{
		if (! isValidDuration(duration)) throw new IllegalArgumentException("The given duration isn't a valid one");
		double newX = (getPosition()[0] + duration*getVelocity()[0]);
		double newY = (getPosition()[1] + duration*getVelocity()[1]);
		double[] newPosition = {newX, newY};
		setPosition(newPosition);
	}
	
	public static boolean isValidDuration(double duration){
		if (Double.isNaN(duration) || Double.isInfinite(duration) || (duration < 0)) {
			return false;
		}
		else{
			return true;
		}
	}
	
	public void turn(double angle){
		assert isValidOrientation(getOrientation() + angle);
		setOrientation(getOrientation() + angle);
	}
	
	public void thrust(double amount){
		if (amount < 0 || Double.isNaN(amount)){
			return;
		}else{
		double newXVelocity = (this.getVelocity()[0] + amount*Math.cos(this.getOrientation()));
		double newYVelocity = (this.getVelocity()[1] + amount*Math.sin(this.getOrientation()));
		double[] newVelocity= {newXVelocity, newYVelocity};
		this.setVelocity(newVelocity);
		}
	}
	
	public double getDistanceBetween(Ship ship2){
		double distance = (Math.sqrt(Math.pow((this.getPosition()[0] - ship2.getPosition()[0]), 2) + Math.pow((this.getPosition()[1] - ship2.getPosition()[1]), 2)) - this.getRadius() - ship2.getRadius());
		return distance;
	}
	
	public boolean overlap(Ship ship2){
		if (this.getDistanceBetween(ship2) < 0){
			return true;
		}else{
			return false;
		}
	}
	
	public double getTimeToCollision(Ship ship2)
			throws IllegalArgumentException{
		if (this.overlap(ship2)) throw new IllegalArgumentException("The ships overlap");
		double diffX = ship2.getPosition()[0] - this.getPosition()[0];
		double diffY = ship2.getPosition()[1] - this.getPosition()[1];
		double diffVX = ship2.getVelocity()[0] - this.getVelocity()[0];
		double diffVY = ship2.getVelocity()[1] - this.getVelocity()[1];
		double distanceCentersSquared = Math.pow((this.getRadius() + ship2.getRadius()), 2);
		double varD = (Math.pow((diffX*diffVX + diffY*diffVY), 2) - (Math.pow(diffVX,2) + Math.pow(diffVY,2))*((Math.pow(diffX,2) + Math.pow(diffY,2)) - distanceCentersSquared));
		if ((diffVX*diffX + diffVY*diffY) >= 0 || varD <= 0){
			return Double.POSITIVE_INFINITY;
		}else{
			double timeToCollision = -(((diffVX*diffX + diffVY*diffY) + Math.sqrt(varD))/(Math.pow(diffVX,2) + Math.pow(diffVY,2)));
			return timeToCollision;
		}
	}
	
	public double[] getCollisionPosition(Ship ship2){
		if (this.overlap(ship2)) throw new IllegalArgumentException("The ships overlap");
		
		if (getTimeToCollision(ship2) == Double.POSITIVE_INFINITY){
			return null;
		}else{
			double duration = this.getTimeToCollision(ship2);
			double collisionX1 = (this.getPosition()[0] + duration*this.getVelocity()[0]);
			double collisionY1 = (this.getPosition()[1] + duration*this.getVelocity()[1]);
			double collisionX2 = (ship2.getPosition()[0] + duration*ship2.getVelocity()[0]);
			double collisionY2 = (ship2.getPosition()[1] + duration*ship2.getVelocity()[1]);
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
			
			double[] collisionPoint = {collisionX2 + ship2.getRadius()*Math.cos(angleCenters), collisionY2 + ship2.getRadius()*Math.sin(angleCenters)};
			return collisionPoint;
			
		}
	}
	
}
