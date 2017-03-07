package asteroids.model;

import java.lang.Math;

public class Ship {
	
	private static final double SPEED_OF_LIGHT = 300000;
	private static final double MAX_ANGLE = 2 * Math.PI;
	private static final double MIN_ANGLE = 0;
	
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation){
		this.setPosition(x,y);
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
		double[] position = {this.x, this.y};
		return position;
	}
	public void setPosition(double[] position){
		this.position = position;
		return;
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
		double speed = math.sqrt(Math.pow(this.getVelocity()[0], 2) + Math.pow(this.getVelocity()[1], 2));
		return speed;
	}
}
