package asteroids.model;

public class IllegalRadiusException 
	extends RuntimeException
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8724496608634897432L;

	public IllegalRadiusException(double radius, Ship ship){
		this.radius = radius;
		this.ship = ship;
		}
	
	public double getRadius(){
		return this.radius;
	}
	
	private final double radius;
	
	public Ship getShip(){
		return this.ship;
	}
	
	private final Ship ship;
}
