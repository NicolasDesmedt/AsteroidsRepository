package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for an asteroid in space involving the mass, all the additional
 * properties are defined by the superclass.
 * 
 * @version	1.0
 * @author 	Lucas Desard and Nicolas Desmedt
 * 			
 * Course studies: 2nd Bachelor Engineering: Computer science/Electrical Engineering
 * Code Repository: https://github.com/NicolasDesmedt/RepositoryLucasNicolas
 */	
public class Planetoid extends MinorPlanet{
	
	/**
	 * Initialize this new planetoid with given position, velocity, radius, totalTraveledDistance and maxSpeed.
	 * 
	 * @param 	x
	 * 			The x-coordinate of the position of this new planetoid (in km).
	 * @param 	y
	 * 			The y-coordinate of the position of this new planetoid (in km).
	 * @param 	xVelocity
	 * 			The movement per unit time of this new planetoid in the x direction (in km/s).
	 * @param 	yVelocity
	 * 			The movement per unit time of this new planetoid in the y direction (in km/s).
	 * @param 	radius
	 * 			The radius of this new circle-shaped planetoid (in km). 
	 * 			This radius does not change during the program's execution.
	 * @param 	totalTraveledDistance
	 * 			The total distance traveled by the planetoid.
	 * @param 	maxSpeed
	 * 			The maximum speed of an planetoid.
	 * @effect	The new planetoid is initialized as an entity with the given position, 
	 * 			velocity, radius and maxSpeed.
	 * 			if no maxSpeed is given, it is equal to the SPEED_OF_LIGHT.
	 * 			| super(x, y, xVelocity, yVelocity, radius, maxSpeed)
	 * @post	The new totalTraveledDistance of this new planetoid is equal to the given totalTraveledDistance.
	 * 			| new.getTotalTraveledDistance == totalTraveledDistance
	 * @throws 	IllegalArgumentException
	 * 			The given position is not a valid position for an asteroid.
	 * 			| (! isValidPosition(position))
	 * @throws 	IllegalArgumentException
	 * 			The given radius is not a valid radius for any asteroid.
	 * 			| (! isValidRadius(radius))
	 */
	public Planetoid(double x, double y, double xVelocity, double yVelocity, double radius, double totalTraveledDistance) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, totalTraveledDistance, SPEED_OF_LIGHT);
		
	}
	
	public Planetoid(double x, double y, double xVelocity, double yVelocity, double radius, double totalTraveledDistance, double maxSpeed)
			throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, maxSpeed);
		setTraveledDistance(totalTraveledDistance);
		setInitialRadius(radius);
		this.move(0);
		if (radius-totalTraveledDistance*0.000001<minMinorPlanetRadius) {
			terminate();
		}
	}
	
	/**
	 * Returns the minimal mass possible for the given planetoid.
	 * @return Returns the minimal mass
	 *         | @see implementation
	 */
	@Raw @Model
	private double calculateMinimalMass(){
		return ((4*Math.PI*Math.pow(this.getRadius(), 3)*planetoidDensity)/3);
	}
	
	/**
	 * Set the mass of this planetoid.
	 * 
	 * @post	The new mass of this planetoid is equal to the mass calculated by the 
	 * 			method calculateMinimalMass().
	 * 			| getMass() == double calculateMinimalMass()
	 */
	@Raw
	protected void setMass(){
			this.mass = calculateMinimalMass();
	}
	
	/**
     * Variable registering the density of all planetoids.
     */
	public final static double planetoidDensity = 9.17E11;
	
	/**
     * Variable registering the total distance traveled by the planetoid.
     */
	public double distanceTraveled = 0;
	
	/**
     * Variable registering the initial radius of the planetoid.
     */
	public double initialRadius;
	
	/**
	 * Set the initial radius of this planetoid to the given radius.
	 * 
	 * @param 	radius
	 * 			The new initial radius for this planetoid.
     * @post	The initial radius of this ship is equal to the given radius.
	 *       	| new.getInitialRadius() == radius
	 */
	@Raw
	public void setInitialRadius(double radius){
		initialRadius = radius;
	}
	
	/**
	 * Return the initial radius of this planetoid.
	 */
	@Basic @Raw
	public double getInitialRadius(){
		return initialRadius;
	}
	
	/**
	 * Move this planetoid a given amount of time, updating the distance
	 * traveled by the planetoid and the radius accordingly.
	 * 
	 * @post	The planet's radius is decreased according to the total distance
	 * 			traveled by the planetoid
	 * 			| new.getRadius() == initialRadius-distanceTraveled*0.000001
	 */
	@Override
    public void move(double duration){
        super.move(duration);
        distanceTraveled += calculateDistanceTraveled(duration);
        double newRadius = (initialRadius-(distanceTraveled*0.000001));
        setRadius(newRadius);
    }
	
	/**
	 * Set the position of this entity to the given position.
	 * 
	 * @param 	position
	 * 			The new position for this entity.
	 * @post	The position of this entity is equal to the given position.
	 *       	| new.getPosition() == position
	 */
	@Raw
	public void setRadius(double radius){
		this.radius = radius;
	}
	
	/**
	 * Set the traveled distance of this entity to the given traveled distance.
	 * 
	 * @param 	traveledDistance
	 * 			The new traveled distance for this planetoid.
	 * @post	The traveled distance of this planetoid is equal to the given traveled distance.
	 *       	| new.getTraveledDistance() == traveledDistance
	 */
	@Raw
	public void setTraveledDistance(double traveledDistance){
		distanceTraveled = traveledDistance;
	}
	
	/**
	 * Return the distance traveled by this planetoid.
	 */
	@Basic @Raw
	public double getTraveledDistance(){
		return distanceTraveled;
	}
	
	/**
	 * Return the distance traveled by a planetoid given a duration.
	 *
	 * @param 	duration
	 * 			The duration the planetoid travels.
	 * @return	Returns the distance traveled
	 *         | @see implementation
	 */
	@Model
	private double calculateDistanceTraveled(double duration){
		double distanceTraveled = duration*Math.sqrt(Math.pow(this.getVelocityX(), 2) + Math.pow(this.getVelocityY(), 2));
		return distanceTraveled;
	}
	
	/**
	 * Handles the collision with with a bullet accordingly
	 * 
	 * @post	...
	 *   	    | new.isTerminated() == true
	 * @post	if the planetoid has a radius >= 30km, is generates 2 asteroids, with half
	 * 			the radius of the planetoid, facing off in a random direction opposite to 
	 * 			each other at 1.5 the speed of the planetoid.
	 * 			| @see implementation
	 */
	public void gotHitByBullet(){
		double planetoidRadius = getRadius();
		boolean hasWorld = hasWorld();
		World world = getWorld();
		super.terminate();
		if (hasWorld && planetoidRadius >= 30){
			double radiusAsteroids = (planetoidRadius/2);
			double directionAsteroid1 = (Math.random()*2*Math.PI);
			double speedAsteroids = (getSpeed(getVelocityX(), getVelocityY())*1.5);
			double planetoidPositionX = getPositionX();
			double planetoidPositionY = getPositionY();
			double differenceX = (Math.cos(directionAsteroid1)*radiusAsteroids);
			double differenceY = (Math.sin(directionAsteroid1)*radiusAsteroids);
			double velocityXAsteroid1 = (Math.cos(directionAsteroid1)*speedAsteroids);
			double velocityYAsteroid1 = (Math.sin(directionAsteroid1)*speedAsteroids);
			Asteroid asteroid1 = new Asteroid((planetoidPositionX+differenceX), (planetoidPositionY+differenceY), velocityXAsteroid1, velocityYAsteroid1, radiusAsteroids);
			Asteroid asteroid2 = new Asteroid((planetoidPositionX-differenceX), (planetoidPositionY-differenceY), -velocityXAsteroid1, -velocityYAsteroid1, radiusAsteroids);
			world.addEntity(asteroid1);
			world.addEntity(asteroid2);
		}
	}
	
}
