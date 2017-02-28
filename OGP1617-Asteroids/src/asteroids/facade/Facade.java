package asteroids.facade;

import asteroids.model.Ship;
import asteroids.part1.facade.IFacade;
import asteroids.util.ModelException;

public class Facade implements IFacade {
	
	
	//Offer default constructor

	@Override
	public Ship createShip() throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double orientation)
			throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getShipPosition(Ship ship) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getShipVelocity(Ship ship) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getShipRadius(Ship ship) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getShipOrientation(Ship ship) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void move(Ship ship, double dt) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void thrust(Ship ship, double amount) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turn(Ship ship, double angle) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getDistanceBetween(Ship ship1, Ship ship2) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean overlap(Ship ship1, Ship ship2) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getTimeToCollision(Ship ship1, Ship ship2) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double[] getCollisionPosition(Ship ship1, Ship ship2) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

}
