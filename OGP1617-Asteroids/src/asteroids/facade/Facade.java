
package asteroids.facade;

import java.util.Collection;
import java.util.Set;

import asteroids.model.Bullet;
import asteroids.model.Entity;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part2.CollisionListener;
import asteroids.util.ModelException;

public class Facade implements asteroids.part2.facade.IFacade {

	@Override
	public double[] getShipPosition(Ship ship) throws ModelException {
		return ship.getPosition();
	}
	
	@Override
	public double[] getShipVelocity(Ship ship) throws ModelException {
		return ship.getVelocity();
	}

	@Override
	public double getShipRadius(Ship ship) throws ModelException {
		return ship.getRadius();
	}

	@Override
	public double getShipOrientation(Ship ship) throws ModelException {
		return ship.getOrientation();
	}

	@Override
	public void turn(Ship ship, double angle) throws ModelException {
		ship.turn(angle);
	}

	@Override
	public double getDistanceBetween(Ship ship1, Ship ship2) throws ModelException {
		try{
			return ship1.getDistanceBetween(ship2);
		}catch( IllegalArgumentException e){
			throw new ModelException(e.getMessage());
		}
	}

	@Override
	public boolean overlap(Ship ship1, Ship ship2) throws ModelException {
		try{
			return ship1.overlap(ship2);
		}catch( NullPointerException e){
			throw new ModelException(e.getMessage());
		}
	}

	@Override
	public double getTimeToCollision(Ship ship1, Ship ship2) throws ModelException {
		try{
			return ship1.getTimeToCollision(ship2);
		}catch( IllegalArgumentException e){
			throw new ModelException(e.getMessage());
		}catch( NullPointerException e){
			throw new ModelException(e.getMessage());
		}
	}

	@Override
	public double[] getCollisionPosition(Ship ship1, Ship ship2) throws ModelException {
		try{
			return ship1.getCollisionPosition(ship2);
		}catch( IllegalArgumentException e){
			throw new ModelException(e.getMessage());
		}catch( NullPointerException e){
			throw new ModelException(e.getMessage());
		}
	}

	@Override
	public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double direction,
			double mass) throws ModelException {
		try{
			return new Ship(x, y, xVelocity, yVelocity, radius, direction, mass);
		}catch( IllegalArgumentException e){
			throw new ModelException(e.getMessage());
		}
	}

	@Override
	public void terminateShip(Ship ship) throws ModelException {
		ship.terminate();
		return;
	}

	@Override
	public boolean isTerminatedShip(Ship ship) throws ModelException {
		return ship.isTerminated();
	}

	@Override
	public double getShipMass(Ship ship) throws ModelException {
		return ship.getMass();
	}

	@Override
	public World getShipWorld(Ship ship) throws ModelException {
		return ship.getWorld();
	}

	@Override
	public boolean isShipThrusterActive(Ship ship) throws ModelException {
		return ship.isShipThrusterActive();
	}

	@Override
	public void setThrusterActive(Ship ship, boolean active) throws ModelException {
		ship.setThrust(active);
		return;
	}

	@Override
	public double getShipAcceleration(Ship ship) throws ModelException {
		return ship.getAcceleration();
	}

	@Override
	public Bullet createBullet(double x, double y, double xVelocity, double yVelocity, double radius)
			throws ModelException {
		try{
			return new Bullet(x, y, xVelocity, yVelocity, radius);
		}catch( IllegalArgumentException e){
			throw new ModelException(e.getMessage());
		}
	}

	@Override
	public void terminateBullet(Bullet bullet) throws ModelException {
		bullet.terminate();
		return;
	}

	@Override
	public boolean isTerminatedBullet(Bullet bullet) throws ModelException {
		return bullet.isTerminated();
	}

	@Override
	public double[] getBulletPosition(Bullet bullet) throws ModelException {
		return bullet.getPosition();
	}

	@Override
	public double[] getBulletVelocity(Bullet bullet) throws ModelException {
		return bullet.getVelocity();
	}

	@Override
	public double getBulletRadius(Bullet bullet) throws ModelException {
		return bullet.getRadius();
	}

	@Override
	public double getBulletMass(Bullet bullet) throws ModelException {
		return bullet.getMass();
	}

	@Override
	public World getBulletWorld(Bullet bullet) throws ModelException {
		return bullet.getWorld();
	}

	@Override
	public Ship getBulletShip(Bullet bullet) throws ModelException {
		return bullet.getShip();
	}

	@Override
	public Ship getBulletSource(Bullet bullet) throws ModelException {
		return bullet.getSource();
	}

	@Override
	public World createWorld(double width, double height) throws ModelException {
		return new World(width,height);
	}

	@Override
	public void terminateWorld(World world) throws ModelException {
		world.terminate();
		return;
	}

	@Override
	public boolean isTerminatedWorld(World world) throws ModelException {
		return world.isTerminated();
	}

	@Override
	public double[] getWorldSize(World world) throws ModelException {
		return world.getWorldSize();
	}

	@Override
	public Set<? extends Ship> getWorldShips(World world) throws ModelException {
		return world.getAllShips();
	}

	@Override
	public Set<? extends Bullet> getWorldBullets(World world) throws ModelException {
		return world.getAllBullets();
	}

	@Override
	public void addShipToWorld(World world, Ship ship) throws ModelException {
		try{
			world.addEntity(ship);
			return;		
		}catch( IllegalArgumentException e){
			throw new ModelException(e.getMessage());
		}
	}

	@Override
	public void removeShipFromWorld(World world, Ship ship) throws ModelException {
		try{
			world.removeEntity(ship);
			return;		
		}catch( IllegalArgumentException e){
			throw new ModelException(e.getMessage());
		}
	}

	@Override
	public void addBulletToWorld(World world, Bullet bullet) throws ModelException {
		try{
			world.addEntity(bullet);
		}catch( IllegalArgumentException e){
			throw new ModelException(e.getMessage());
		}
	}

	@Override
	public void removeBulletFromWorld(World world, Bullet bullet) throws ModelException {
		try{
			world.removeEntity(bullet);
			return;		
		}catch( IllegalArgumentException e){
			throw new ModelException(e.getMessage());
		}
	}

	@Override
	public Set<? extends Bullet> getBulletsOnShip(Ship ship) throws ModelException {
		return ship.getBulletsOnShip();
	}

	@Override
	public int getNbBulletsOnShip(Ship ship) throws ModelException {
		return ship.getNbBulletsOnShip();
	}

	@Override
	public void loadBulletOnShip(Ship ship, Bullet bullet) throws ModelException {
		ship.loadBulletOnShip(bullet);
	}

	@Override
	public void loadBulletsOnShip(Ship ship, Collection<Bullet> bullets) throws ModelException {
		ship.loadBulletsOnShip(bullets.toArray(new Bullet[bullets.size()]));
		return;
	}

	@Override
	public void removeBulletFromShip(Ship ship, Bullet bullet) throws ModelException {
		ship.removeBulletFromShip(bullet);
		return;
	}

	@Override
	public void fireBullet(Ship ship) throws ModelException {
		ship.fireBullet();
		return;
	}

	@Override
	public double getTimeCollisionBoundary(Object object) throws ModelException {
		if (object instanceof Entity) { 
			World world = ((Entity)object).getWorld();
			return world.getTimeCollisionToBoundary((Entity)object);
		}
		else
			throw new IllegalArgumentException("Argument must be of type Entity");
	}

	@Override
	public double[] getPositionCollisionBoundary(Object object) throws ModelException {
		if (object instanceof Entity) {
			World world = ((Entity)object).getWorld();
			return world.getPositionEntityUponCollision((Entity)object);
		}
		else
			throw new ModelException("Argument must be of type Entity");
	}

	@Override
	public double getTimeCollisionEntity(Object entity1, Object entity2) throws ModelException {
		if ((entity1 instanceof Entity) && (entity2 instanceof Entity))
			return ((Entity)entity1).getTimeToCollision((Entity)entity2);
		else
			throw new ModelException("The arguments must be of type Entity");
	}

	@Override
	public double[] getPositionCollisionEntity(Object entity1, Object entity2) throws ModelException {
		if ((entity1 instanceof Entity) && (entity2 instanceof Entity))
			return ((Entity)entity1).getCollisionPosition((Entity)entity2);
		else
			throw new IllegalArgumentException("The arguments must be of type Entity");
	}

	@Override
	public double getTimeNextCollision(World world) throws ModelException {
		return world.getTimeToNextCollision();
	}

	@Override
	public double[] getPositionNextCollision(World world) throws ModelException {
		return world.getPositionNextCollision();
	}

	@Override
	public void evolve(World world, double dt, CollisionListener collisionListener) throws ModelException {
		world.evolve(dt, collisionListener);
		return;
	}

	@Override
	public Object getEntityAt(World world, double x, double y) throws ModelException {
		return world.getEntityAt(x,y);
	}

	@Override
	public Set<? extends Object> getEntities(World world) throws ModelException {
		return world.getAllEntities();
	}

}