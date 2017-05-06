package asteroids.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;
/**
 * A class for flying a ship in space involving the mass, radius
 * and orientation of the ship. With facilities to accelerate the ship by thrusting,
 * to fire of bullets from the ship and to resolve collisions this ship is a part of.
 * 
 * @invar	The mass of each ship must be a valid mass for any ship.
 * 			| isValidMass(getMass())
 * @invar	The orientation of each entity must be a valid orientation for any entity.
 * 			| isValidOrientation(getOrientation())
 * @invar	The radius of each ship must be a valid radius for any ship.
 * 			| isValidRadius(getRadius())
 * 
 * @version	1.0
 * @author 	Lucas Desard and Nicolas Desmedt
 * 
 * TODO: specification for various methods
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
	
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, orientation, 0, SPEED_OF_LIGHT);
		
	}
	
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation, double mass) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, orientation, mass, SPEED_OF_LIGHT);
		
	}
	
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation, double mass, double maxSpeed) 
			throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, maxSpeed);
		this.setOrientation(orientation);
		this.setMass(mass);
		this.thrusterForce = defaultThrusterForce;	
		
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
		this(0,0,0,0,minRadiusShip,(Math.PI/2), 0);
		
	}
	
	@Override
	public void terminate(){
		for(Bullet bullet: bullets){
			bullet.terminate();
		}
		super.terminate();
		
	}
	
	
	public double calculateMinimalMass(){
		return ((4*Math.PI*Math.pow(this.getRadius(), 3)*MIN_DENSITY)/3);
	}
	
	/**
	 * Set the mass of this entity to the given mass.
	 * 
	 * @param 	mass
	 * 			The given mass.
	 * @post	If the given mass is a valid mass, then 
	 * 			the mass of this entity is equal to the given mass.
	 * 			| if isValidMass(mass)
	 * 			|	then (new entity).getMass() == mass;
	 * @post	If the given mass is not a valid mass, then
	 * 			if the original mass is a valid mass, the mass of this entity 
	 * 			stays the same, otherwise the mass of this entity is equal to
	 * 			the minimal mass of an entity.
	 * 			| if !isValidMass(mass)
	 * 			|	then if isValidMass((old entity).getMass())
	 * 			| 		(new entity).getMass() == (old entity).getMass()
	 * 			|	else (new entity).getMass() == this.calculateMinimalMass()
	 */
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
		
	public boolean isValidMass(double mass){
		if ((getDensity(mass) >= MIN_DENSITY) && (mass < Double.MAX_VALUE) && (!Double.isNaN(mass))){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Return the density of this ship with given mass.
	 * 
	 * @param 	mass
	 * 			The given mass.
	 */
	@Basic
	public double getDensity(double mass) {
		double density = ((mass*3)/(4*(Math.PI)*Math.pow(this.getRadius(),3)));
		return density;
	}
	
	public static final double MIN_DENSITY = 1.42E12;

	/**
	 * Check whether the given radius is a valid radius for
	 * any ship.
	 * 
	 * @param 	radius
	 * 			The radius to check.
	 * @return	True if and only if the radius is a 
	 * 			real number greater than or equal to the minimum radius.
	 * 			| result == ( (radius >= minRadiusShip) && (!Double.isInfinite(radius)) && (!Double.isNaN(radius)) )
	 */
	public boolean isValidRadius(double radius){
		if ( (radius >= minRadiusShip) && (!Double.isInfinite(radius)) && (!Double.isNaN(radius)) ) {
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
		return minRadiusShip;
	}
	/**
	 * A constant reflecting the minimum radius that applies to all ships.
	 */
	public static final double minRadiusShip = 10;
	
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
	public void thrust(double time){
		if (time < 0 || Double.isNaN(time)){
			return;
		}else{
		double newXVelocity = (this.getVelocity()[0] + time*Math.cos(this.getOrientation())*this.getAcceleration());
		double newYVelocity = (this.getVelocity()[1] + time*Math.sin(this.getOrientation())*this.getAcceleration());
		this.setVelocity(newXVelocity, newYVelocity);
		}
	}
	
	/**
	 * Return a boolean indicating whether or not the thruster of this ship is active.
	 */
	public boolean isShipThrusterActive(){
		return isTrusterOn;
	}
	
	/**
	 * A boolean registering whether or not the thruster of this ship is on.
	 */
	private boolean isTrusterOn;
	
	/**
	 * Set the thruster to a given boolean.
	 */
	public void setThrust(boolean active){
		if(active){
			isTrusterOn = true;
		}else{
			isTrusterOn = false;

		}
	}
	
	/**
	 * A variable registering the default thruster force.
	 */
	public final static double defaultThrusterForce = 1.1E21;
	
	/**
	 * Set the thruster force of this ship to a given thruster force.
	 * 
	 * @param newThrusterforce
	 */
	public void setThrusterForce(double newThrusterforce){
		if ( (newThrusterforce>=0) && (!Double.isInfinite(newThrusterforce)) && (!Double.isNaN(newThrusterforce))){
			this.thrusterForce = newThrusterforce;
		}else{
			this.thrusterForce = defaultThrusterForce;
		}
	}
	
	/**
	 * Return the thruster force of this ship.
	 * 
	 * @return
	 */
	@Basic
	public double getThrusterForce(){
		return this.thrusterForce;
	}
	
	/**
	 * A variable registering the thruster force of this ship.
	 */
	public double thrusterForce;
	
	/**
	 * Return the acceleration of this ship.
	 * 
	 * @return
	 */
	public double getAcceleration(){
		double acceleration = this.getThrusterForce()/this.getMass();
		return acceleration;
	}
	
	/**
	 * Load a given bullet on this ship. The mass of the bullet gets added
	 * to the mass of the ship.
	 * 
	 * @param bullet
	 */
	public void loadBulletOnShip(Bullet bullet){
		this.bullets.add(bullet);
		this.setMass(this.getMass() + bullet.getMass());
		bullet.removeFromWorld();
		bullet.setShip(this);
		bullet.setPosition(this.getPositionX(), this.getPositionY());
		bullet.setVelocity(0, 0);
	}
	
	/**
	 * Load an array of bullets on this ship.
	 * 
	 * @param bullets
	 */
	public void loadBulletsOnShip(Bullet[] bullets){
		for(Bullet bullet: bullets){
			this.loadBulletOnShip(bullet);
		}
	}
	
	/**
	 * A variable set registering all the bullets on this ship.
	 */
	public Set<Bullet> bullets = new HashSet<>();
	
	/**
	 * Return a set containing all the bullets on this ship.
	 * 
	 * @return
	 */
	@Basic
	public Set<Bullet> getBulletsOnShip(){
		return this.bullets;
	}
	
	/**
	 * Return the amount of bullets on this ship.
	 * 
	 * @return
	 */
	public int getNbBulletsOnShip(){
		return this.bullets.size();
	}
	
	/**
	 * Remove the given bullet from this ship.
	 * 
	 * @param bullet
	 */
	public void removeBulletFromShip(Bullet bullet){
		if (this.getBulletsOnShip().contains(bullet)){
			bullet.setShip(null);
			this.getBulletsOnShip().remove(bullet);
		}
	}
	
	/**
	 * Move this ship a given amount of time, taking the state of the thruster
	 * 	into account.
	 * 
	 * @effect	
	 */
	@Override
    public void move(double duration){
        super.move(duration);
        if(this.isShipThrusterActive()){
			this.thrust(duration);
		}
        double positionX = this.getPositionX();
        double positionY = this.getPositionY();
        for (Bullet bullet: this.bullets) {
            bullet.setPosition(positionX, positionY);
        }
    }
	
	/**
	 * Fire a bullet, the source of that bullet is therefore this ship.
	 * 
	 * @effect	
	 */
	public void fireBullet(){
		if ((this.getNbBulletsOnShip() == 0) || (this.getWorld() == null)){
			return;
		}
		Bullet bullet = this.selectLoadedBullet();
		if (!bullet.isTerminated()){
			this.getWorld().addEntity(bullet);
			this.removeBulletFromShip(bullet);
			this.putInFiringPosition(bullet);
			bullet.setVelocity(bulletSpeed*Math.cos(this.getOrientation()), bulletSpeed*Math.sin(this.getOrientation()));
			bullet.setSource(this);
		}
	}
	
	/**
	 * Put the given bullet in the firing position of this ship.
	 * 	
	 * @param 	bullet
	 */
	public void putInFiringPosition(Bullet bullet){
		double[] newPosition = this.getFiringPosition(bullet);
		bullet.setPosition(newPosition[0], newPosition[1]);
	}
	
	/**
	 * Return the firing position of this ship for the given bullet.
	 * 
	 * @param 	bullet
	 * @return
	 */
	public double[] getFiringPosition(Bullet bullet) {
		double newXPosition = bullet.getPositionX() + (this.getRadius() + bullet.getRadius())*Math.cos(getOrientation());
		double newYPosition = bullet.getPositionY() + (this.getRadius() + bullet.getRadius())*Math.sin(getOrientation());
		double[] position = {newXPosition,newYPosition};
		return position;
	}
	
	/**
	 * Select a loaded bullet from the bullets on this ship.
	 * 
	 * @return
	 */
	public Bullet selectLoadedBullet(){
		for(Bullet bullet: this.getBulletsOnShip()){
			return bullet;
		}
		return null;
	}
	
	/**
	 * A variable registering the bullet speed of any bullet of any ship.
	 */
	public static double bulletSpeed = 250;
	
	/**
	 * Resolve the collision between this ship and an asteroid.
	 * 
	 */
	public void collidesWithAsteroid(Asteroid asteroid) {
		this.terminate();
	}
	
	/**
	 * Resolve the collision between this ship and a planetoid.
	 * 
	 */
	public void collidesWithPlanetoid(Planetoid planetoid) {
		World world = getWorld();
		double newPositionX = Math.random()*world.getWorldWidth();
		double newPositionY = Math.random()*world.getWorldHeight();
		this.setPosition(newPositionX, newPositionY);
		world.evolve(0, null);  //niet zeker of dit juist is
	}
	
}