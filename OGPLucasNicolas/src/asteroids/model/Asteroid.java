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
public class Asteroid extends MinorPlanet{
	
	/**
	 * Initialize this new asteroid with given position, velocity, radius and maxSpeed.
	 * 
	 * @param 	x
	 * 			The x-coordinate of the position of this new asteroid (in km).
	 * @param 	y
	 * 			The y-coordinate of the position of this new asteroid (in km).
	 * @param 	xVelocity
	 * 			The movement per unit time of this new asteroid in the x direction (in km/s).
	 * @param 	yVelocity
	 * 			The movement per unit time of this new asteroid in the y direction (in km/s).
	 * @param 	radius
	 * 			The radius of this new circle-shaped asteroid (in km). 
	 * 			This radius does not change during the program's execution.
	 * @param 	maxSpeed
	 * 			The maximum speed of an asteroid.
	 * @post	The new position of this new ship is equal to the given position.
	 * 			| new.getPosition() == double[] {x,y}
	 * @post	The new velocity of this new ship is equal to the given velocity.
	 * 			| new.getVelocity() == double[] {xVelocity,yVelocity}
	 * @post	The new radius of this new ship is equal to the given radius.
	 * 			| new.getRadius == radius 
	 * @post	The new maxSpeed of this new ship is equal to the given maxSpeed,
	 * 			if no maxSpeed is given, it is equal to the SPEED_OF_LIGHT.
	 * 			| new.getMaxspeed == maxSpeed 
	 */
	public Asteroid(double x, double y, double xVelocity, double yVelocity, double radius) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, SPEED_OF_LIGHT);
		
	}
	
	public Asteroid(double x, double y, double xVelocity, double yVelocity, double radius, double maxSpeed){
		super(x, y, xVelocity, yVelocity, radius, maxSpeed);
	}
	
	/**
	 * Set the mass of this asteroid to the given mass.
	 * 
	 * @param 	mass
	 * 			The given mass.
	 * @post	The new mass of this asteroid is equal to the given mass.
	 * 			| getMass() == double mass
	 */
	@Raw
	public void setMassMinorPlanet(){
			this.mass = calculateMinimalMass();
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
     * Variable registering the density of all asteroids.
     */
	public final static double asteroidDensity = 2.65E12;
	
}
