package asteroids.model;

import java.lang.Math;

import asteroids.util.ModelException;

public class Ship {
	
	private static final double SPEED_OF_LIGHT = 300000;
	private static final double MIN_RADIUS = 10;

	private double[] position = new double[2];
	public double[] velocity = new double[2];
	private double orientation;
	private double radius;
	
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation)
		throws IllegalArgumentException{
		this.setPosition(new double[] {x,y});
		this.setVelocity(new double[] {xVelocity,yVelocity});
		this.setRadius(radius);
		this.setOrientation(orientation);
	}
	public Ship(){
		this(0,0,0,0,MIN_RADIUS,(Math.PI/2));
	}
	
	public double[] getPosition(){
		return this.position;
	}
	
	public void setPosition(double[] position)
		throws IllegalArgumentException{
		if (!isValidPosition(position)) throw new IllegalArgumentException("The given position isn't a valid one");
		this.position = position;
	}
		
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
		double speed = computeSpeed(velocity);
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
	
	public double getRadius(){
		return this.radius;
	}

	public void setRadius(double radius)
			throws IllegalArgumentException{
		if (!isValidRadius(radius)) throw new IllegalArgumentException("The given radius isn't a valid one");
		this.radius = radius;
		return;
	}
	
	public double getOrientation(){
		return this.orientation;
	}
	public void setOrientation(double orientation){
		assert isValidOrientation(orientation);
		this.orientation = orientation;
	}
	
	public boolean isValidOrientation(double orientation){
		return (orientation>=0 && orientation<=2*Math.PI && !Double.isNaN(orientation));
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
	
	public void move(double duration)
		throws IllegalArgumentException{
		if (! isValidDuration(duration)) throw new IllegalArgumentException("The given duration isn't a valid one");
		double newX = (getPosition()[0] + duration*getVelocity()[0]);
		double newY = (getPosition()[1] + duration*getVelocity()[1]);
		double[] newPosition = {newX, newY};
		setPosition(newPosition);
	}
	
	public static boolean isValidDuration(double duration){
		if (Double.isNaN(duration) || Double.isInfinite(duration) || (duration < 0)) {
			return false;
		}
		else{
			return true;
		}
	}
	
	public void turn(double angle){
		assert isValidOrientation(getOrientation() + angle);
		setOrientation(getOrientation() + angle);
	}
	
	public void thrust(double amount){
		if (amount < 0 || Double.isNaN(amount)){
			return;
		}else{
		double newXVelocity = (this.getVelocity()[0] + amount*Math.cos(this.getOrientation()));
		double newYVelocity = (this.getVelocity()[1] + amount*Math.sin(this.getOrientation()));
		double[] newVelocity= {newXVelocity, newYVelocity};
		this.setVelocity(newVelocity);
		}
	}
	
	public double getDistanceBetween(Ship ship2){
		double distance = (Math.sqrt(Math.pow((this.getPosition()[0] - ship2.getPosition()[0]), 2) + Math.pow((this.getPosition()[1] - ship2.getPosition()[1]), 2)) - this.getRadius() - ship2.getRadius());
		return distance;
	}
	
	public boolean overlap(Ship ship2){
		if (this.getDistanceBetween(ship2) < 0){
			return true;
		}else{
			return false;
		}
	}
	
	public double getTimeToCollision(Ship ship2)
			throws IllegalArgumentException{
		if (this.overlap(ship2)) throw new IllegalArgumentException("The ships overlap");
		double diffX = ship2.getPosition()[0] - this.getPosition()[0];
		double diffY = ship2.getPosition()[1] - this.getPosition()[1];
		double diffVX = ship2.getVelocity()[0] - this.getVelocity()[0];
		double diffVY = ship2.getVelocity()[1] - this.getVelocity()[1];
		double distanceCentersSquared = Math.pow((this.getRadius() + ship2.getRadius()), 2);
		double varD = (Math.pow((diffX*diffVX + diffY*diffVY), 2) - (Math.pow(diffVX,2) + Math.pow(diffVY,2))*((Math.pow(diffX,2) + Math.pow(diffY,2)) - distanceCentersSquared));
		if ((diffVX*diffX + diffVY*diffY) >= 0 || varD <= 0){
			return Double.POSITIVE_INFINITY;
		}else{
			double timeToCollision = -(((diffVX*diffX + diffVY*diffY) + Math.sqrt(varD))/(Math.pow(diffVX,2) + Math.pow(diffVY,2)));
			return timeToCollision;
		}
	}
	
	public double[] getCollisionPosition(Ship ship2){
		if (this.overlap(ship2)) throw new IllegalArgumentException("The ships overlap");
		
		if (getTimeToCollision(ship2) == Double.POSITIVE_INFINITY){
			return null;
		}else{
			double duration = this.getTimeToCollision(ship2);
			double collisionX1 = (this.getPosition()[0] + duration*this.getVelocity()[0]);
			double collisionY1 = (this.getPosition()[1] + duration*this.getVelocity()[1]);
			double collisionX2 = (ship2.getPosition()[0] + duration*ship2.getVelocity()[0]);
			double collisionY2 = (ship2.getPosition()[1] + duration*ship2.getVelocity()[1]);
			double diffX = collisionX2 - collisionX1;
			double diffY = collisionY2 - collisionY1;
			double angleCenters = 0;
			if (diffX*diffY >= 0){
				if (diffX == 0){
					if (diffY > 0){
						angleCenters = (Math.PI/2);
					}else{
						angleCenters = -(Math.PI/2);
					}
				}else if ((diffX > 0) || (diffY == 0)){
					angleCenters = Math.atan(diffY/diffX) + Math.PI;
				}else if((diffX < 0) || (diffY == 0)){
					angleCenters = Math.atan(diffY/diffX);
				}
			}else {
				if (diffY > 0){
					angleCenters = Math.atan(diffY/diffX);
				}else if(diffY < 0){
					angleCenters = Math.atan(diffY/diffX) + Math.PI;
				}
			}
			
			double[] collisionPoint = {collisionX2 + ship2.getRadius()*Math.cos(angleCenters), collisionY2 + ship2.getRadius()*Math.sin(angleCenters)};
			return collisionPoint;
			
		}
	}
	
}
