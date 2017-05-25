package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of bullets involving a mass, a radius, a source and a world.
 * And facilities to resolve collisions this bullet is a part of.
 * 
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
	 * @effect	The new bullet is initialized as an entity with the given position, 
	 * 			velocity, radius and maxSpeed.
	 * 			If no maxSpeed is given, it is equal to the SPEED_OF_LIGHT.
	 * 			| super(x, y, xVelocity, yVelocity, radius, maxSpeed)
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
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius, double maxSpeed)
			throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, maxSpeed);
		this.setMassBullet();
	}
	
	/**
	 * Initialize this new bullet with given position, velocity and radius with
	 * SPEED_OF_LIGHT as its maximum speed.
	 * 
	 * @param 	x
	 * 			The x-coordinate of the position of this new bullet (in km).
	 * @param 	y
	 * 			The y-coordinate of the position of this new bullet (in km).
	 * @param 	xVelocity
	 * 			The movement per unit time of this new bullet in the x direction (in km/s).
	 * @param 	yVelocity
	 * 			The movement per unit time of this new bullet in the y direction (in km/s).
	 * @param 	radius
	 * 			The radius of this new circle-shaped bullet (in km). 
	 * 			This radius does not change during the program's execution.
	 * @effect	This new bullet is initialized as a bullet with the given position, 
	 * 			velocity and radius, and SPEED_OF_LIGHT as it's maximum speed.
	 */
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, SPEED_OF_LIGHT);
		
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
	 * Terminate this bullet and remove it from the ship if it belongs to one.
	 *
	 * @post This bullet is terminated.
	 *       | new.isTerminated()
	 * @post If the given bullet belongs to a ship, remove the bullet from the ship.
	 * 		 | @see implementation
	 * @effect	...
	 * 		 | super.terminate()
	 */
	@Raw @Override
	public void terminate() {
		if (this.hasShip()){
			 this.getShip().removeBulletFromShip(this);
		}
		super.terminate();
	 }
	
	/**
	 * Terminate this bullet.
	 *
	 * @post   This bullet  is terminated.
	 *       | new.isTerminated()
	 * @post If the given bullet belongs to a ship, ship is set to null.
	 * 		 | @see implementation
	 * @effect	...
	 * 		 | super.terminate()
	 */
	public void shipTerminate() {
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
	@Raw @Model
	private double calculateMinimalMass(){
		return ((4*Math.PI*Math.pow(this.getRadius(), 3)*bulletDensity)/3);
	}
	
	/**
	 * Set the mass of this bullet to the minimal mass.
	 * 
	 * @post	The new mass of this bullet is equal to the mass calculated by the 
	 * 			method calculateMinimalMass().
	 * 			| getMass() == double calculateMinimalMass()
	 */
	@Raw
	public void setMassBullet(){
			this.mass = calculateMinimalMass();
	}
	
	/**
     * Variable registering the minimal radius of all bullets.
     */
	public final static double minBulletRadius = 1;
	
	/**
     * Variable registering the density of all bullets.
     */
	public final static double bulletDensity = 7.8E12;
	
	/**
     * Checks if this bullet can have the given radius as its radius.
     *
     * @param  radius
     *         The radius to check.
     * @return True if and only if the given radius is bigger than the minimal bullet radius 
     * 			and isn't infinite or NaN.
     *       	| @see implementation
     */
	@Basic
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
	 * Returns the number of times the bullet has collided with a boundary of the world.
	 */
	@Basic
	public int getCounterBoundaryCollisions() {
		return this.counterBoundaryCollisions;
	}
	
	/**
	 * Increments the counterBoundaryCollisions of bullet with 1.
	 * @post	The counter of the bullet is increased with 1.
	 * 			| counterBoundaryCollisions++
	 */
	@Model
	private void incrementCounterBoundaryCollisions() {
		this.counterBoundaryCollisions++;
	}
	
	/**
	 * Returns the maximum number of times the bullet can collide with a boundary 
	 * of the world before the bullet dies.
	 */
	@Basic @Raw @Immutable
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
	 * @effect	If the maximum number of bounces off the wall has been reached, the 
	 * 			bullet dies.
	 * 			| super.collidesWithBoundary()
	 */
	@Override
	public void collidesWithBoundary(World world) {
		this.incrementCounterBoundaryCollisions();
		if (getCounterBoundaryCollisions() == getMaxBoundaryCollisions()) {
				this.terminate();
				return;
		}else {
			super.collidesWithBoundary(world);
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
	 * yet, the source is equal to null.
	 */
	@Basic @Immutable
	public Ship getSource(){
		return this.source;
	}
	
	/**
	 * A variable registering the source of a bullet, if the bullet isn't fired of a ship
	 * yet, the source is null.
	 */
	public Ship source = null;
	
	/**
	 * Resolve a collision between this bullet and a given entity.
	 * 
	 * @param 	other
	 * 			The entity the bullet collides with.
	 * @effect	If the bullet hits it's source ship, it is removed from the world
	 * 			and reloaded on the ship, if hits a planetoid with a radius >= 30, 
	 * 			the bullet is terminated and the planetoid concieves 2 asteroids facing 
	 * 			off in different directions, else both the bullet and other entity 
	 * 			are terminated.
	 * 			| @see implementation
	 */
	public void hits(Entity other) {
		if (this.getSource() == other){
			this.getSource().getWorld().allEntities.remove(this);
			((Ship)other).loadBulletOnShip(this);
		}else if (other instanceof Planetoid && other.getRadius() >= 30){
			this.terminate();
			((Planetoid)other).gotHitByBullet();
		}else{
			this.terminate();
			other.terminate();
		}
	}
}