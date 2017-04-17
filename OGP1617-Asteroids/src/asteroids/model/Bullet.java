package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of bullets involving a mass, a radius, a source, and a world.
 * And facilities to resolve collisions this bullet is a part of.
 * 
 * @invar	The mass of each bullet must be a valid mass for any bullet.
 * 			| isValidMass(getMass())
 * @invar	The radius of each bullet must be a valid radius for any bullet.
 * 			| isValidRadius(getRadius())
 * 
 * @author 	Nicolas Desmedt and Lucas Desard
 * @version	1.0
 *
 * Course studies: 2nd Bachelor Engineering: Computer science/Electrical Engineering
 * Code Repository: https://github.com/NicolasDesmedt/RepositoryLucasNicolas
 */
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
	@Basic @Raw
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
	@Raw
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
	 * Returns the ship to which this bullet belongs, if the bullet doesn't belong
	 *  to a ship, it returns null.
	 */
	@Basic
	public Ship getShip(){
		return this.ship;
	}
	
	/**
	 * Returns whether or not the bullet belongs to a ship.
	 */
	@Basic
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
 	
	/**
	 * Return the world this bullet belongs to.
	 */
 	public World getWorld(){
 		return this.world;
 	}
 	
 	/**
 	 * Return a boolean indicating whether or not this bullet belongs to a world.
 	 */
 	public boolean hasWorld(){
 		return this.world != null;
	}
  	
 	/**
 	 * Set the world this bullet belongs to to a given world.
 	 * A bullet can only belong to a world or a ship at the same time.
 	 */
 	public void setWorld(World world) throws IllegalArgumentException {
 		if (this.hasShip())
 			this.setShip(null);
 		if (!hasShip() && !hasWorld()){
			this.world = world;
		}else{
			throw new IllegalArgumentException();
 		}
 	}
  	
 	/**
 	 * A variable registering the world of this bullet.
 	 */
 	public World world;
	
	/**
	 * Returns the number of times the bullet has collided with a boundary of the world.
	 */
	public int getCounterBoundaryCollisions() {
		return this.counterBoundaryCollisions;
	}
	
	/**
	 * Increments the counterBoundaryCollisions of bullet with 1.
	 */
	
	public void incrementCounterBoundaryCollisions() {
		this.counterBoundaryCollisions++;
	}
	
	/**
	 * Returns the maximum number of times the bullet can collide with a boundary 
	 * of the world before the bullet dies.
	 */
	@Basic @Raw
	public int getMaxBoundaryCollisions() {
		return maxBoundaryCollisions;
	}
	
	/**
	 * Variable registering the maximum number of times the bullet can collide with a boundary.
	 */
 
	private final int maxBoundaryCollisions = 3;
	
	/**
	 * Variable registering the number of times the bullet has collided with a boundary.
	 */
	
	private int counterBoundaryCollisions = 0;
	
	/**
	 * Terminates the bullet if it isn't within the boundaries of the world.
	 * If the bullet is within the boundaries of the world and hasn't collided
	 * maxBoundaryCollisions times with the boundaries yet, is is handled as a normal
	 * collision with a boundary, else if the bullet has collided maxBoundaryCollisions
	 * times with the boundaries, it is terminated.
	 */
	
	public void collidesWithBoundary(World world) {
		if (!this.getWorld().withinBoundaries(this)) {
			this.terminate();
		}else {
			this.incrementCounterBoundaryCollisions();
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
	
	/**
	 * Set the source of this bullet to the given ship.
	 * 
	 * @param 	ship
	 * 			The source ship for this bullet.
     * @post	The source of this bullet is equal to the given ship.
	 *       	| new.getSource() == ship
	 */
	
	public void setSource(Ship ship){
		this.source = ship;
	}
	
	/**
	 * Returns the source of the bullet, if the bullet isn't fired of a ship
	 * yet, the source is null.
	 */
	@Basic
	public Ship getSource(){
		return this.source;
	}
	
	/**
	 * A variable registering the source of a bullet, if the bullet isn't fired of a ship
	 * yet, the source is null.
	 */
	
	public Ship source = null;
	
	/**
	 * Terminates both bullets
	 */
	
	public void cancelsOut(Bullet other) {
		this.terminate();
		other.terminate();
	}	
}