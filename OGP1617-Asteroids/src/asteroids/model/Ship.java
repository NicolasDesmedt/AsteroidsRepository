package asteroids.model;

import java.lang.Math;

public class Ship {
	
	private static final double SPEED_OF_LIGHT = 300000;
	private static final double MAX_ANGLE = 2 * Math.PI;
	private static final double MIN_ANGLE = 0;
	
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation)
		throws IllegalPositionException{
		this.setPosition(new double[] {x,y});
		this.setVelocity(xVelocity, yVelocity);
		this.setRadius(radius);
		this.setOrientation(orientation);
	}
	public Ship(){
		this.setPosition(0,0);
		this.setVelocity(0,0);
		this.setRadius(minRadius);
		this.setOrientation(math.PI/2);
	}
	
	public double[] getPosition(){
		return this.position;
	}
	
	public void setPosition(double[] position)
		throws IllegalPositionException{
		if (!isValidPosition(position)) throw new IllegalPositionException(position, this);
		this.position = position;
	}
	
	public double[] position = new double[2];
	
	public static boolean isValidPosition(double[] position){
		if (Double.isNaN(position[0]) || Double.isNaN(position[1]) || Double.isInfinite(position[0]) || Double.isInfinite(position[0]) || (position.length != 2)) {
			return false;
		}
		else{
			return true;
		}
	}
	
	public double[] getVelocity(){
		double[] velocity = {this.xVelocity, this.yVelocity};
		return velocity;
	}
	public void setVelocity(double yVelocity){
		
	}
	
	public double getRadius(){
		return this.radius;
	}
	public void setRadius(double radius){
		this.radius = radius;
		return;
	}
	public double getOrientation(){
		return this.getOrientation();
	}
	public void setOrientation(double orientation){
		this.orientation = orientation;
		return;
	}
	public double computeSpeed(){
		double speed = Math.sqrt(Math.pow(this.getVelocity()[0], 2) + Math.pow(this.getVelocity()[1], 2));
		return speed;
	}
}
