package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

public class Bullet extends Entity{
	
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, calculateMassBullet(minBulletRadius), SPEED_OF_LIGHT);
		
	}
	
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius, double mass) 
			throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, mass, SPEED_OF_LIGHT);
		
	}
	
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius, double mass, double maxSpeed){
		super(x, y, xVelocity, yVelocity, radius, mass, maxSpeed);
	}
	
	public Bullet(){
		this(0, 0, 0, 0, minBulletRadius, calculateMassBullet(minBulletRadius), SPEED_OF_LIGHT);

	}
	
	/**
	 * Terminate this bullet.
	 *
	 * @post   This bullet  is terminated.
	 *       | new.isTerminated()
	 * @post   ...
	 *       | ...
	 */
	@Raw
	 public void terminate() {
		 if (this.hasShip()){
			 this.ship = null;
		 }
		 super.terminate();
	 }
	
	public double calculateMinimalMass(){
		return ((4*Math.PI*Math.pow(this.getRadius(), 3)*bulletDensity)/3);
	}
	 
	public boolean isValidMass(double mass){
		if ((this.getDensity() >= bulletDensity) && (mass < Double.MAX_VALUE) && (!Double.isNaN(mass))){
			return true;
		}else{
			return false;
		}
	}
	
	public final static double minBulletRadius = 1;
	
	public final static double bulletDensity = 7.8E12;
	
	public static double calculateMassBullet(double radius){
		return ((4*Math.PI*Math.pow(radius, 3)*bulletDensity)/3);
	}
	
	public boolean isValidRadius(double radius){
		if (Double.isInfinite(radius) || Double.isNaN(radius)) {
			return false;
		}else{
			if (hasShip() && radius>=minBulletRadius && radius>=0.1*this.getShip().getRadius()){
				return true;
			}else if (!hasShip() && radius>=minBulletRadius){
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
	
	public int getCounterBoundaryCollisions() {
		return this.counterBoundaryCollisions;
	}
	
	public void setCounterBoundaryCollisions(int counter) {
		this.counterBoundaryCollisions = counter;
	}
	
	public int getMaxBoundaryCollisions() {
		return maxBoundaryCollisions;
	}
	
	private final int maxBoundaryCollisions = 3;
	
	private int counterBoundaryCollisions = 0;
	
	public void collidesWithBoundary(World world) {
		this.setCounterBoundaryCollisions(getCounterBoundaryCollisions() + 1);
		if (getCounterBoundaryCollisions() == getMaxBoundaryCollisions()) {
				this.removeFromWorld(world);
				world.removeEntity(this);
				return;
		}
		if (world.getDistanceToNearestHorizontalBoundary(this) <
				world.getDistanceToNearestVerticalBoundary(this) )
			this.setVelocity(getVelocityX(), -getVelocityY());
		else
			this.setVelocity(-getVelocityX(), getVelocityY());
	}
	
	public void setSource(Ship ship){
		this.source = ship;
	}
	
	public Ship getSource(){
		return this.source;
	}
		
	public Ship source = null;
}
