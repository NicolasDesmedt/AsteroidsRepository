package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

public class Bullet extends Entity{
	
	/**
	 * Create a bullet with given position, velocity, radius, mass and maximum speed.
	 * 
	 * @param 	x
	 * 			The x-coordinate of the position of this new bullet (in km).
	 * @param	y
	 * 			The y-coordinate of the position of this new bullet (in km).
	 * @param 	xVelocity
	 * 			The movement per unit time of this new bullet in the x direction (in km/s).
	 * @param 	yVelocity
	 * 			The movement per unit time of this new bullet in the y direction (in km/s).
	 * @param 	radius
	 * 			The radius of this new circle-shaped bullet (in km).
	 * @param 	mass
	 * 			The mass of the new bullet (in kg).
	 * @param 	maxSpeed
	 * 			The maximum speed of the new bullet (in km/s).
	 * @post	The new position of this new bullet is equal to the given position.
	 * 			| new.getPosition() == double[] {x,y}
	 * @post	If the given velocity does not result in a total speed 
	 * 			which exceeds the maximum speed for this bullet, 
	 * 			the velocity of this bullet is equal to the given velocity.
	 * 			| if ( getSpeed(velocity) < maxSpeed )
	 * 			| new.getVelocity() == double[] {xVelocity,yVelocity}
	 * @post	The new radius of this new bullet is equal to the given radius.
	 * 			| new.getRadius == radius
	 * @post	If the given mass is not a valid mass,
	 * 			the new mass of the bullet is set to its minimum value,
	 * 			else the new mass of this new bullet is equal to the given mass.
	 * 			| if !isValidMass(mass)
	 *          |		mass = this.calculateMinimalMass()
	 * 			| new.getMass == mass
	 * @throws 	IllegalArgumentException
	 * 			The given argument is not a valid argument for a bullet.
	 * 			| (! isValidPosition(position)) ||  (! isValidRadius(radius))
	 */
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, calculateMassBullet(minBulletRadius), SPEED_OF_LIGHT);
		
	}
	
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius, double mass) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, mass, SPEED_OF_LIGHT);
		
	}
	
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius, double mass, double maxSpeed){
		super(x, y, xVelocity, yVelocity, radius, mass, maxSpeed);
	}
	
	/**
	 * Initialize this new bullet in the origin of the axes with zero velocity, with a radius and mass
	 * set to their lowest possible values, and its maxSpeed equal to SPEED_OF_LIGHT.
	 * 
	 * @effect	This new bullet is initialized with the x-coordinate, y-coordinate, 
	 * 			xVelocity and yVelocity being zero, the radius and mass being of minimal value,
	 * 			minBulletRadius and minimal bullet mass, and its maxSpeed equal to SPEED_OF_LIGHT. 
	 * 			| this(0,0,0,0,minBulletRadius);
	 */
	public Bullet(){
		this(0, 0, 0, 0, minBulletRadius);

	}
	
	/**
	 * Terminate this bullet.
	 *
	 * @post   This bullet  is terminated.
	 *       | new.isTerminated()
	 * @post If the given bullet belongs to a ship, ship is set to null
	 * 		 | @see implementation
	 */
	@Raw
	public void terminate() {
		if (this.hasShip()){
			 this.setShip(null);
		}
		super.terminate();
	 }
	
	/**
	 * Returns the minimal mass possible for the given bullet.
	 * @return Returns the minimal mass
	 *         | @see implementation
	 */
	public double calculateMinimalMass(){
		return ((4*Math.PI*Math.pow(this.getRadius(), 3)*minBulletDensity)/3);
	}
	
	/**
     * Checks if this bullet can have the given mass as its mass.
     *
     * @param  mass
     *         The mass to check.
     * @return The mass must be smaller than the MAX_VALUE for a double, bigger or equal than the 
     * 			minimal density and not be NaN.
     *       	| @see implementation
     */
	public boolean isValidMass(double mass){
		if ((getDensity(mass) >= minBulletDensity) && (mass < Double.MAX_VALUE) && (!Double.isNaN(mass))){
			return true;
		}else{
			return false;
		}
	}
	
	/**
     * Variable registering the minimal radius of all bullets.
     */
	
	public final static double minBulletRadius = 1;
	
	/**
     * Variable registering the minimal density of all bullets.
     */
	public final static double minBulletDensity = 7.8E12;
	
	/**
	 * Returns the mass for the given bullet assuming it has the minimal bullet density.
	 * @return Returns the mass assuming it has minBulletDensity
	 *         | @see implementation
	 */
	public static double calculateMassBullet(double radius){
		return ((4*Math.PI*Math.pow(radius, 3)*minBulletDensity)/3);
	}
	
	/**
     * Checks if this bullet can have the given radius as its radius.
     *
     * @param  radius
     *         The radius to check.
     * @return The radius must be bigger than the minBulletRadius and not be infinite or NaN.
     *       	| @see implementation
     */
	public boolean isValidRadius(double radius){
		if ( (radius >= minBulletRadius) && (!Double.isInfinite(radius)) && (!Double.isNaN(radius)) ) {
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Return the ship to which this bullet belongs, if the bullet doesn't belong
	 *  to a ship, it returns null.
	 */
	public Ship getShip(){
		return this.ship;
	}
	
	/**
	 * Returns whether or not the bullet belongs to a ship.
	 */
	public boolean hasShip(){
		return this.ship != null;
	}
	
	/**
	 * Set the ship of this bullet to the given ship.
	 * 
	 * @param 	ship
	 * 			The new ship for this bullet.
     * @post	The ship of this bullet is equal to the given ship.
	 *       	| new.getShip() == ship
	 */
	
	public void setShip(Ship ship){
		this.ship = ship;    
	}
	
	/**
	 * Variable registering the ship to which the bullet belongs.
	 * A newly created bullet doesn't belong to a ship.
	 */
	public Ship ship = null;
	
	
	
	public int getCounterBoundaryCollisions() {
		return this.counterBoundaryCollisions;
	}
	
	public void setCounterBoundaryCollisions(int counter) {
		this.counterBoundaryCollisions = counter;
	}
	
	public int getMaxBoundaryCollisions() {
		return maxBoundaryCollisions;
	}
 
	private final int maxBoundaryCollisions = 3;
	
	private int counterBoundaryCollisions = 0;
	
	public void collidesWithBoundary(World world) {
		if (!this.getWorld().withinBoundaries(this)) {
			this.terminate();
		}else {
			this.setCounterBoundaryCollisions(getCounterBoundaryCollisions() + 1);
			if (getCounterBoundaryCollisions() == getMaxBoundaryCollisions()) {
					this.terminate();
					return;
			}
			else if (world.getDistanceToNearestHorizontalBoundary(this) <
					world.getDistanceToNearestVerticalBoundary(this) )
				this.setVelocity(getVelocityX(), -getVelocityY());
			else
				this.setVelocity(-getVelocityX(), getVelocityY());
		}
	}
	
	public void setSource(Ship ship){
		this.source = ship;
	}
	
	public Ship getSource(){
		return this.source;
	}
		
	public Ship source = null;
	
	public void cancelsOut(Bullet other) {
		this.terminate();
		other.terminate();
	}	
}