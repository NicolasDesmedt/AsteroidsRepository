package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for a minorPlanet in space involving the mass, all the additional
 * properties are defined by the superclass.
 * 
 * @invar	The radius of each minor planet must be a valid radius for any minor planet.
 * 			| isValidRadius(getRadius())
 * 
 * @version	1.0
 * @author 	Lucas Desard and Nicolas Desmedt
 * 			
 * Course studies: 2nd Bachelor Engineering: Computer science/Electrical Engineering
 * Code Repository: https://github.com/NicolasDesmedt/RepositoryLucasNicolas
 */	
public abstract class MinorPlanet extends Entity{
	
	/**
	 * Initialize this new minorPlanet with given position, velocity, radius and maxSpeed.
	 * 
	 * @param 	x
	 * 			The x-coordinate of the position of this new minorPlanet (in km).
	 * @param 	y
	 * 			The y-coordinate of the position of this new minorPlanet (in km).
	 * @param 	xVelocity
	 * 			The movement per unit time of this new minorPlanet in the x direction (in km/s).
	 * @param 	yVelocity
	 * 			The movement per unit time of this new minorPlanet in the y direction (in km/s).
	 * @param 	radius
	 * 			The radius of this new circle-shaped minorPlanet (in km). 
	 * 			This radius does not change during the program's execution.
	 * @param 	mass
	 * 			The mass of the given new ship.
	 * @param 	maxSpeed
	 * 			The maximum speed of an minorPlanet.
	 * @post	The new position of this new minorPlanet is equal to the given position.
	 * 			| new.getPosition() == double[] {x,y}
	 * @post	The new velocity of this new minorPlanet is equal to the given velocity.
	 * 			| new.getVelocity() == double[] {xVelocity,yVelocity}
	 * @post	The new radius of this new minorPlanet is equal to the given radius.
	 * 			| new.getRadius == radius 
	 * @post	The new mass of this new minorPlanet is equal to the given mass.
	 * 			| new.getMass == mass
	 * @post	The new maxSpeed of this new minorPlanet is equal to the given maxSpeed,
	 * 			if no maxSpeed is given, it is equal to the SPEED_OF_LIGHT.
	 * 			| new.getMaxspeed == maxSpeed 
	 * @throws 	IllegalArgumentException
	 * 			The given position is not a valid position for a minor planet.
	 * 			| (! isValidPosition(position))
	 * @throws 	IllegalArgumentException
	 * 			The given radius is not a valid radius for any minor planet.
	 * 			| (! isValidRadius(radius))
	 */
	public MinorPlanet(double x, double y, double xVelocity, double yVelocity, double radius, double mass) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, mass, SPEED_OF_LIGHT);
		
	}
	
	public MinorPlanet(double x, double y, double xVelocity, double yVelocity, double radius, double mass, double maxSpeed)
			throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, maxSpeed);
		this.setMass();
	}
	
	/**
	 * A variable registering the minimal radius of a minor planet.
	 */
	public final static double minMinorPlanetRadius = 5;
	
	/**
	 * Return the minimal mass of this minor planet.
	 */
	protected abstract void setMass();
	
	/**
     * Checks if this minor planet can have the given radius as its radius.
     *
     * @param  radius
     *         The radius to check.
     * @return True if and only if the given radius bigger is than the minimal minor planet
     * 		   radius an is not infinite or NaN.
     *       	| @see implementation
     */
	@Raw
	public boolean isValidRadius(double radius){
		if ( (radius >= minMinorPlanetRadius) && (!Double.isInfinite(radius)) && (!Double.isNaN(radius)) ) {
			return true;
		}
		else{
			return false;
		}
	}
}
