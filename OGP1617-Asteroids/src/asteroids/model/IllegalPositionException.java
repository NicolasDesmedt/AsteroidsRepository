package asteroids.model;

public class IllegalPositionException 
	extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IllegalPositionException(double[] position, Ship ship){
		this.position = position;
		this.ship = ship;
		}
	
	public double[] getPosition(){
		return this.position;
	}
	
	private final double[] position;
	
	public Ship getShip(){
		return this.ship;
	}
	
	private final Ship ship;
}
