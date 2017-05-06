package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

public abstract class MinorPlanet extends Entity{
	
	public MinorPlanet(double x, double y, double xVelocity, double yVelocity, double mass, double radius) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, mass, SPEED_OF_LIGHT);
		
	}
	
	public MinorPlanet(double x, double y, double xVelocity, double yVelocity, double radius, double mass, double maxSpeed){
		super(x, y, xVelocity, yVelocity, radius, maxSpeed);
		this.setMassMinorPlanet();
	}
	
	public final static double minMinorPlanetRadius = 5;
	
	/**
	 * Return the minimal mass of this entity.
	 */
	public abstract void setMassMinorPlanet();
	
	/**
     * Checks if this bullet can have the given radius as its radius.
     *
     * @param  radius
     *         The radius to check.
     * @return The radius must be bigger than the minBulletRadius and not be infinite or NaN.
     *       	| @see implementation
     */
	public boolean isValidRadius(double radius){
		if ( (radius >= minMinorPlanetRadius) && (!Double.isInfinite(radius)) && (!Double.isNaN(radius)) ) {
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
}
