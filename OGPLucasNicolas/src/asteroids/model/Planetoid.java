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
		this.move(0);
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
	
	public double initialRadius = 0;
	
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
        if (initialRadius == 0){
        	initialRadius = radius;
        }
        double newRadius = (initialRadius-(distanceTraveled*0.000001));
        if (newRadius < minMinorPlanetRadius){
        	terminate();
        }else{
            setRadius(newRadius);
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
	
	@Override
	public void terminate(){
		if (hasWorld() && getRadius() >= 30){
			double radiusAsteroids = (getRadius()/2);
			double directionAsteroid1 = (Math.random()*2*Math.PI);
			double speedAsteroids = (getSpeed(getVelocityX(), getVelocityY())*1.5);
			double planetoidPositionX = getPositionX();
			double planetoidPositionY = getPositionY();
			double differenceX = (Math.cos(directionAsteroid1)*radiusAsteroids);
			double differenceY = (Math.sin(directionAsteroid1)*radiusAsteroids);
			double velocityXAsteroid1 = (Math.cos(directionAsteroid1)*speedAsteroids);
			double velocityYAsteroid1 = (Math.sin(directionAsteroid1)*speedAsteroids);
			Asteroid Asteroid1 = new Asteroid((planetoidPositionX+differenceX), (planetoidPositionY+differenceY), velocityXAsteroid1, velocityYAsteroid1, radiusAsteroids);
			Asteroid Asteroid2 = new Asteroid((planetoidPositionX-differenceX), (planetoidPositionY-differenceY), -velocityXAsteroid1, -velocityYAsteroid1, radiusAsteroids);
			getWorld().addEntity(Asteroid1);
			getWorld().addEntity(Asteroid2);

		}
		super.terminate();
		
	}
	
}
