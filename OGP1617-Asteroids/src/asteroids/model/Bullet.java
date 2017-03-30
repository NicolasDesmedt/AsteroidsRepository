package asteroids.model;

public class Bullet extends Entity{
	
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius, double mass) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, mass, SPEED_OF_LIGHT);
		
	}
	
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius, double mass, double maxSpeed){
		super(x, y, xVelocity, yVelocity, radius, mass, maxSpeed);
	}
	
	public Bullet(){
		this(0, 0, 0, 0, MIN_RADIUS_BULLET, calculateMassBullet(MIN_RADIUS_BULLET), SPEED_OF_LIGHT);

	}
	
	public final static double MIN_RADIUS_BULLET = 1;
	
	public final static double BULLET_DENSITY = 7.8E12;
	
	public static double calculateMassBullet(double radius){
		return ((4*Math.PI*Math.pow(radius, 3)*BULLET_DENSITY)/3);
	}
	
	public boolean isValidRadius(double radius){
		if (Double.isInfinite(radius) || Double.isNaN(radius)) {
			return false;
		}else{
			if (hasShip() && radius>=MIN_RADIUS_BULLET && radius>=0.1*this.getShip().getRadius()){
				return true;
			}else if (!hasShip() && radius>=MIN_RADIUS_BULLET){
				return true;
			}else{
				return false;
			}
		}
	}
	
	public Ship getShip(){
		return this.ship;
	}
	
	public boolean hasShip(){
		return this.ship != null;
	}
	
	public void setShip(Ship ship){
		if (!hasShip() && !hasWorld() && this.getRadius() >= 0.1*ship.getRadius()){
			this.ship = ship;
		}else{
			//throw new exception
		}
	}
	
	public Ship ship;
	
	public World getWorld(){
		return this.world;
	}
	
	public boolean hasWorld(){
		return this.world != null;
	}
	
	public void setWorld(World world){
		if (!hasShip() && !hasWorld()){
			this.world = world;
		}else{
			//throw new exception
		}
	}
	
	public World world;
	
}
