package asteroids.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;
//import be.kuleuven.cs.som.taglet.*;

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
 * 			
 * Course studies: 2nd Bachelor Engineering: Computer science/Electrical Engineering
 * Code Repository: https://github.com/NicolasDesmedt/RepositoryLucasNicolas
 */
public class Ship extends Entity{
	
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
	 * 			This radius does not change during the program's execution.
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
	 * 			The given position is not a valid position for a ship.
	 * 			| (! isValidPosition(position))
	 * @throws 	IllegalArgumentException
	 * 			The given radius is not a valid radius for any ship.
	 * 			| (! isValidRadius(radius))
	 */
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation, double mass) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, orientation, mass, SPEED_OF_LIGHT);
		
	}
	
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation, double mass, double maxSpeed) 
			throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, mass, maxSpeed);
		this.setOrientation(orientation);
		this.setMass(mass);
		this.thrusterForce = defaultThrusterForce;
		this.loadBullets(15);
		
	}
	
	@Raw
	public void setMass(double mass){
		if (!isValidMass(mass)){
			this.mass = MIN_MASS;
		}else{
			this.mass = mass;		
		}
	}
	
	private final double MIN_MASS = (4*Math.PI*Math.pow(MIN_RADIUS_SHIP, 3)*MIN_DENSITY)/4;
	
	public boolean isValidMass(double mass){
		if ((this.getDensity() > MIN_DENSITY) && (mass < Double.MAX_VALUE) && (!Double.isNaN(mass))){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Return the minimum radius for each ship.
	 */
	
	public static final double MIN_DENSITY = 1.42E12;
	
	/**
	 * Return the minimum radius for each ship.
	 */
	@Basic @Immutable
	public double getDensity() {
		double density = (this.getMass()*3)/(4*(Math.PI)*Math.pow(this.getRadius(),3));
		return density;
	}
	
	/**
	 * Initialize this new ship in the origin of the axes with zero velocity, with a radius 
	 * set to its lowest possible value and with an orientation facing up at an angle of PI/2.
	 * 
	 * @effect	This new ship is initialized with the x-coordinate, y-coordinate, 
	 * 			xVelocity and yVelocity being zero, the radius being of minimal value minRadius,
	 * 			and the orientation of the ship being PI/2.
	 * 			| this(0,0,0,0,minRadius,(Math.PI/2));
	 */

	public Ship(){
		this(0,0,0,0,MIN_RADIUS_SHIP,(Math.PI/2), 0);
	}

	/**
	 * Check whether the given radius is a valid radius for
	 * any ship.
	 * 
	 * @param 	radius
	 * 			The radius to check.
	 * @return	True if and only if the radius is a 
	 * 			real number greater than or equal to the minimum radius.
	 * 			| result == ( (radius >= MIN_RADIUS_SHIP) && (!Double.isInfinite(radius)) && (!Double.isNaN(radius)) )
	 */
	public boolean isValidRadius(double radius){
		if ( (radius >= MIN_RADIUS_SHIP) && (!Double.isInfinite(radius)) && (!Double.isNaN(radius)) ) {
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Return the minimum radius for each ship.
	 */
	@Basic @Immutable
	public static double getMinRadius() {
		return MIN_RADIUS_SHIP;
	}
	/**
	 * Constant reflecting the minimum radius that applies to all ships.
	 */
	private static final double MIN_RADIUS_SHIP = 10;
	
	/**
	 * Return the orientation of this ship.
	 * 	The orientation is the direction in which the ship is faced
	 * 	expressed as an angle in radians. For example, the orientation
	 *  of a ship facing right is 0, a ship facing up is at angle PI/2.
	 */
	@Basic @Raw
	public double getOrientation(){
		return this.orientation;
	}
	
	/**
	 * Check whether the given orientation is a valid orientation for
	 * any ship.
	 * 
	 * @param	orientation
	 * 			The orientation to check.
	 * @return	True if and only if the orientation is a real number between 0 and 2*PI.
	 * 			| result == ( (orientation>=0) && (orientation<=2*Math.PI) && (!Double.isNaN(orientation)))
	 */
	public boolean isValidOrientation(double orientation){
		return ( (orientation>=0) && (orientation<=2*Math.PI) && (!Double.isNaN(orientation)));
	}
	
	/**
	 * Set the orientation of this ship to the given orientation.
	 * 
	 * @param 	orientation
	 * 			The new orientation for this ship.
	 * @pre	  	The given orientation must be a valid orientation for a ship.
     *        	| isValidOrientation(orientation)
     * @post	The orientation of this ship is equal to the given orientation.
	 *       	| new.getOrientation() == orientation
	 */
	@Raw
	public void setOrientation(double orientation){
		assert isValidOrientation(orientation);
		this.orientation = orientation;
	}
	
	/**
	 * Variable registering the orientation of this ship.
	 */
	private double orientation;
	
	/**
	 * Move this ship for a given duration based on its current
	 * position and current velocity to a new position.
	 * 
	 * @param 	duration
	 * 			The duration for how long the ship moves to its new position.
	 * @effect	The new position of this ship is set to the old position of
	 * 			this ship incremented with the product of the given duration
	 * 			and the velocity of the ship.
	 * 			| setPosition( {(getPosition()[0] + duration*getVelocity()[0]),
	 * 			|				(getPosition()[1] + duration*getVelocity()[1])} )
	 * @throws 	IllegalArgumentException
	 * 			The given duration is not a valid duration for any ship.
	 * 			| ! isValidDuration(duration)
	 */
	
	public void turn(double angle){
		assert isValidOrientation(getOrientation() + angle);
		setOrientation(getOrientation() + angle);
	}
	
	/**
	 * Change this ship's velocity based on the current velocity, its orientation, and on a given amount.
	 * 
	 * @param 	amount
	 * 			The variable who determines how much the velocity must change.
	 * @effect	If the given amount is negative or not a number,
	 * 			the velocity does not change.
	 * 			| if (amount < 0 || Double.isNaN(amount))
	 * 			|	then new.getVelocity() == this.getVelocity()
	 * @effect	If the given amount is a positive real number,
	 * 			the new velocity in the x direction of this ship is set to 
	 * 			the old velocity in the x direction of this ship incremented 
	 * 			with the product of the given amount and the cosinus of the 
	 * 			orientation of this ship, while the new velocity in the y direction
	 * 			of this ship is set to the old velocity in the y direction of
	 * 			this ship incremented with the product of the given amount and
	 * 			the sinus of the orientation of this ship.
	 * 			| setVelocity( {(getVelocity()[0] + amount*Math.cos(this.getOrientation())),
	 * 							(getVelocity()[1] + amount*Math.sin(this.getOrientation()))} )
	 */
	public void thrust(double acceleration){
		if (acceleration < 0 || Double.isNaN(acceleration)){
			return;
		}else{
		double newXVelocity = (this.getVelocity()[0] + acceleration*Math.cos(this.getOrientation()));
		double newYVelocity = (this.getVelocity()[1] + acceleration*Math.sin(this.getOrientation()));
		this.setVelocity(newXVelocity, newYVelocity);
		}
	}
	
	public boolean isShipThrusterActive(){
		return isTrusterOn;
	}
	
	private boolean isTrusterOn;
	
	public void thrustOn(){
		isTrusterOn = true;
	}
	
	public void thrustOff(){
		isTrusterOn = false;
	}
	
	public final double defaultThrusterForce = 1.1E21;
	
	public void setThrusterForce(double force){
		if ( (force>=0) && (!Double.isInfinite(force)) && (!Double.isNaN(force))){
			this.thrusterForce = force;
		}else{
			this.thrusterForce = defaultThrusterForce;
		}
	}

	public double getThrusterForce(){
		return this.thrusterForce;
	}
	
	public double thrusterForce;
	
	public double getAcceleration(){
		double acceleration = this.getThrusterForce()/this.getMass();
		return acceleration;
	}
	
	public void loadBullet(){
		loadBullets(1);
	}
	
	public void loadBullets(double amount){
		for(int i=0; i<amount; i++){
			this.bullets.add(newValidBullet());
		}
	}
	public void loadBulletOnShip(Bullet bullet){
		this.bullets.add(bullet);
		this.setMass(this.getMass() + bullet.getMass());
	}

	public void loadBulletOnShip(Set<Bullet> bullets){
		for(Bullet bullet: bullets){
			this.loadBulletOnShip(bullet);
		}
	}
	
	public Bullet newValidBullet(){
		double xPosition = this.getPosition()[0];
		double yPosition = this.getPosition()[1];
		double radius = 0.11*this.getRadius();
		Bullet newValidBullet = new Bullet(xPosition, yPosition, 0, 0, radius);
		newValidBullet.setShip(this);
		return newValidBullet;
	}
	
	public Set<Bullet> bullets = new HashSet<>();
	
	public Set<Bullet> getBulletsOnShip(){
		return this.bullets;
	}
	
	public int getNbBulletsOnShip(){
		return this.bullets.size();
	}
	
	public void removeBulletFromShip(){
		
	}
	
	public void fireBullet(){
		
	}
	

}