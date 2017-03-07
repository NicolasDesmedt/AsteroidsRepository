package asteroids.model;

import java.lang.Math;

public class Ship {
	
	private static final double SPEED_OF_LIGHT = 300000;
	private static final double MIN_RADIUS = 10;

	
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation)
		throws IllegalPositionException{
		this.setPosition(new double[] {x,y});
		this.setVelocity(new double[] {xVelocity,yVelocity});
		this.setRadius(radius);
		this.setOrientation(orientation);
	}
	public Ship(){
		this.setPosition(new double[] {0,0});
		this.setVelocity(new double[] {0,0});
		this.setRadius(MIN_RADIUS);
		this.setOrientation(Math.PI/2);
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
		return this.velocity;
	}
	public void setVelocity(double[] velocity){
		if (Double.isInfinite(velocity[0]) && (Double.isInfinite(velocity[1]))){
			if (velocity[0] > 0){
				velocity[0] = SPEED_OF_LIGHT/Math.sqrt(2);
			} else{
				velocity[0] = -SPEED_OF_LIGHT/Math.sqrt(2);
			}
			if (velocity[1] > 0){
				velocity[1] = SPEED_OF_LIGHT/Math.sqrt(2);
			} else{
				velocity[1] = -SPEED_OF_LIGHT/Math.sqrt(2);
			}
		} else if (Double.isInfinite(velocity[0])){
			if (velocity[0] > 0){
				velocity[0] = SPEED_OF_LIGHT;
			} else{
				velocity[0] = -SPEED_OF_LIGHT;
			}
			velocity[1] = 0;
		} else if (Double.isInfinite(velocity[1])){
			if (velocity[1] > 0){
				velocity[1] = SPEED_OF_LIGHT;
			} else{
				velocity[1] = -SPEED_OF_LIGHT;
			}
			velocity[0] = 0;
		} else if (speed > SPEED_OF_LIGHT){
			this.velocity[0] = (velocity[0]*SPEED_OF_LIGHT)/speed;
			this.velocity[1] = (velocity[1]*SPEED_OF_LIGHT)/speed;
		} 
		else{
			this.velocity = velocity;
		} 
	}
	
	public double[] velocity = new double[2];
	public double speed = computeSpeed(velocity);
	public double orientation;
	
	public double getRadius(){
		return this.radius;
	}

	public void setRadius(double radius)
			throws IllegalRadiusException{
		if (!isValidRadius(radius)) throw new IllegalRadiusException(radius, this);
		this.radius = radius;
		return;
	}
	
	public double radius;
	
	public double getOrientation(){
		return this.orientation;
	}
	public void setOrientation(double orientation){
		this.orientation = orientation;
	}
	
	public double computeSpeed(double[] velocity){
		if (Double.isNaN(velocity[0]) || Double.isNaN(velocity[1])){
			setVelocity(new double[] {0,0});
		}
		double speed = Math.sqrt(Math.pow(this.getVelocity()[0], 2) + Math.pow(this.getVelocity()[1], 2));
		return speed;
	}
	public static boolean isValidRadius(double radius){
		if ((radius < MIN_RADIUS) || (Double.isInfinite(radius)) || (Double.isNaN(radius))) {
			return false;
		}
		else{
			return true;
		}
	}
}
