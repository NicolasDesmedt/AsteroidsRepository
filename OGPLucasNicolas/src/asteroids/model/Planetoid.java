package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

public class Planetoid extends MinorPlanet{
	
	public Planetoid(double x, double y, double xVelocity, double yVelocity, double radius, double totalTraveledDistance) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, totalTraveledDistance, SPEED_OF_LIGHT);
		
	}
	
	public Planetoid(double x, double y, double xVelocity, double yVelocity, double radius, double totalTraveledDistance, double maxSpeed){
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
	@Raw
	public double calculateMinimalMass(){
		return ((4*Math.PI*Math.pow(this.getRadius(), 3)*planetoidDensity)/3);
	}
	
	/**
	 * 
	 */
	@Raw
	public void setMassMinorPlanet(){
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
	
	public void setInitialRadius(double radius){
		initialRadius = radius;
	}
	
	public double getInitialRadius(){
		return initialRadius;
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
	 * @throws 	IllegalArgumentException
	 *  		The given position is not a valid position for any entity.
	 *       	| ! isValidPosition(position)
	 */
	@Raw
	public void setRadius(double radius)
		throws IllegalArgumentException{
		this.radius = radius;
	}
	
	public void setTraveledDistance(double traveledDistance){
		distanceTraveled = traveledDistance;
	}
	
	public double getTraveledDistance(){
		return distanceTraveled;
	}
	
	public double calculateDistanceTraveled(double duration){
		double distanceTraveled = duration*Math.sqrt(Math.pow(this.getVelocityX(), 2) + Math.pow(this.getVelocityY(), 2));
		return distanceTraveled;
	}
	
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
