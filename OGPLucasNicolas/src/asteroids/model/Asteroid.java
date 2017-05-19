package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

public class Asteroid extends MinorPlanet{
	
	public Asteroid(double x, double y, double xVelocity, double yVelocity, double radius) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, SPEED_OF_LIGHT);
		
	}
	
	public Asteroid(double x, double y, double xVelocity, double yVelocity, double radius, double maxSpeed){
		super(x, y, xVelocity, yVelocity, radius, maxSpeed);
	}
	
	/**
	 * Returns the minimal mass possible for the given asteroid.
	 * @return Returns the minimal mass
	 *         | @see implementation
	 */
	@Raw
	public double calculateMinimalMass(){
		return ((4*Math.PI*Math.pow(this.getRadius(), 3)*asteroidDensity)/3);
	}
	
	/**
	 * 
	 */
	@Raw
	public void setMassMinorPlanet(){
			this.mass = calculateMinimalMass();
	}
	
	/**
     * Variable registering the density of all asteroids.
     */
	public final static double asteroidDensity = 2.65E12;
	
}
