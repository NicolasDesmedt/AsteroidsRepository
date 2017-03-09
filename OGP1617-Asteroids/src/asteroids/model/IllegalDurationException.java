package asteroids.model;

public class IllegalDurationException 
	extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6341590722988850121L;

	public IllegalDurationException(double duration, Ship ship){
		this.duration = duration;
		this.ship = ship;
		}
	
	public double getDuration(){
		return this.duration;
	}
	
	private final double duration;
	
	public Ship getShip(){
		return this.ship;
	}
	
	private final Ship ship;
}
