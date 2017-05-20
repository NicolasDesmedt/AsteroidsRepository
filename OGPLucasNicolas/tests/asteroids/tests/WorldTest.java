package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.*;

import asteroids.model.*;
import asteroids.part2.CollisionListener;

public class WorldTest {
	/**
	 * Variable defining the maximum acceptable distance of the result due to rounding issues.
	 */
	private static final double EPSILON = 0.0001;
	
	@Test
	public void worldConstructor_LegalCase(){
		World world=new World(20, 10);
		double[] size = world.getWorldSize();
		assertEquals(size[0], 20, EPSILON);
		assertEquals(size[1], 10, EPSILON);
	}
	
	@Test
	public void worldConstructor_NaN(){
		World world = new World(Double.NaN, 10);
		double[] size = world.getWorldSize();
		double upperBound = World.getUpperBound();
		assertEquals(size[0],upperBound, EPSILON);
		assertEquals(size[1], 10, EPSILON);
	}
	
	@Test 
	public void withinBoundaries_LegalCase(){
		World world1000x1000 = new World(1000,1000);
		Ship mutableShip1 = new Ship(100,100,20,20,10,0, 5.5E20);
		assertTrue(world1000x1000.withinBoundaries(mutableShip1));
	}
	
	@Test 
	public void withinBoundaries_IllegalCase(){
		World world1000x1000 = new World(1000,1000);
		Ship mutableShip1 = new Ship(100,100,20,20,10,0, 5.5E20);
		mutableShip1.setPosition(9.89, 10);
		assertFalse(world1000x1000.withinBoundaries(mutableShip1));
	}
	
	@Test 
	public void addAndRemoveEntity_LegalCase(){
		World world1000x1000 = new World(1000,1000);
		Ship immutableShip1 = new Ship(70,70,0,0,10,(Math.PI/2));
		world1000x1000.addEntity(immutableShip1);
		assertTrue(world1000x1000.getAllEntities().contains(immutableShip1));
		world1000x1000.removeEntity(immutableShip1);
		assertFalse(world1000x1000.getAllEntities().contains(immutableShip1));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addEntity_OverlappingEntities() {
		World world1000x1000 = new World(1000,1000);
		Ship immutableShip1 = new Ship(70,70,0,0,10,(Math.PI/2));
		Ship immutableOverlappingShip = new Ship(70,65,0,0,10,0);
		world1000x1000.addEntity(immutableShip1);
		assertTrue(world1000x1000.getAllEntities().contains(immutableShip1));
		assertTrue(world1000x1000.overlapsWithOtherEntities(immutableOverlappingShip));
		world1000x1000.addEntity(immutableOverlappingShip);
		assertFalse(world1000x1000.getAllEntities().contains(immutableOverlappingShip));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addEntity_OutsideWorldBoundaries() {
		World world1000x1000 = new World(1000,1000);
		Ship immutableOutOfBoundariesShip = new Ship(1000,0,0,0,10,0);
		world1000x1000.addEntity(immutableOutOfBoundariesShip);
		}
	
	@Test(expected=IllegalArgumentException.class)
	public void addEntity_AlreadyInAWorld(){
		World world1000x1000 = new World(1000,1000);
		Ship immutableShip1 = new Ship(70,70,0,0,10,(Math.PI/2));
		world1000x1000.addEntity(immutableShip1);
		world1000x1000.addEntity(immutableShip1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void removeEntity_NonEffectiveCase(){
		World world1000x1000 = new World(1000,1000);
		world1000x1000.removeEntity(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void removeEntity_NotInWorld(){
		World world100x100 = new World(100,100);
		Ship immutableOutOfBoundariesShip = new Ship(1000,0,0,0,10,0);
		world100x100.removeEntity(immutableOutOfBoundariesShip);
	}	
	
	@Test
	public void getDistanceToBoundaries_LegalCase() {
		World world1000x1000 = new World(1000,1000);
		Ship immutableShip1 = new Ship(70,70,0,0,10,(Math.PI/2));
		double distanceToCheckLegal = world1000x1000.getDistanceToBoundaries(immutableShip1);
		assertEquals(distanceToCheckLegal,70,EPSILON);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getDistanceToBoundaries_IllegalCase() {
		World world1000x1000 = new World(1000,1000);
		Ship immutableOutOfBoundariesShip = new Ship(1000,0,0,0,10,0);
		world1000x1000.getDistanceToBoundaries(immutableOutOfBoundariesShip);
	}
	
	/*
	 * Test number 1 tests a case where provided position contains no entity.
	 * Test number 2 tests a case where the provided position is occupied by an entity.
	 * Test number 3 tests a case where the provided position is out of the given world.
	 */
	@Test
	public void getEntityAt_Tests() {
		World world1000x1000 = new World(1000,1000);
		Ship immutableShip1 = new Ship(70,70,0,0,10,(Math.PI/2));
		world1000x1000.addEntity(immutableShip1);
		double[] legalPositionEmpty = {200.0,300.0};
		double[] legalPositionOccupied = immutableShip1.getPosition();
		double[] illegalPositionOutOfBoundaries = {1200,0};
		Entity empty = world1000x1000.getEntityAt(legalPositionEmpty[0],legalPositionEmpty[1]);
		Entity entity = world1000x1000.getEntityAt(legalPositionOccupied[0],legalPositionOccupied[1]);
		Entity outOfWorld = world1000x1000.getEntityAt
				(illegalPositionOutOfBoundaries[0],illegalPositionOutOfBoundaries[1]);
		assertEquals(null, empty);
		assertEquals(immutableShip1, entity);
		assertEquals(null, outOfWorld);
	}
	
	/*
	 * Test number 1 tests the function getAllEntities().
	 * Test number 2 tests the function getAllShips().
	 * Test number 3 tests the function getAllBullets().
	 * Test number 4 tests getAllBullets() on a particular bullet.
	 */
	@Test
	public void getAllEntities_Tests() {
		World world1000x1000 = new World(1000,1000);
		Ship mutableShip1 = new Ship(100,100,20,20,10,0, 5.5E20);
		Ship mutableShip2 = new Ship(500,500,30,10,10,(Math.PI/2), 10E15);
		Ship mutableShip3 = new Ship(10,300,10,0,10,0,10E15);
		Ship immutableShip1 = new Ship(70,70,0,0,10,(Math.PI/2));
		Ship immutableShip2 = new Ship(700,700,0,0,Ship.minRadiusShip,0);
		Bullet mutableBullet1 = new Bullet(200,200,10,0,Bullet.minBulletRadius);
		Bullet mutableBullet2 = new Bullet(900,900,0,-10,(Bullet.minBulletRadius+1));
		world1000x1000.addEntity(immutableShip1);
		world1000x1000.addEntity(immutableShip2);
		world1000x1000.addEntity(mutableShip1);
		world1000x1000.addEntity(mutableShip2);
		world1000x1000.addEntity(mutableShip3);
		world1000x1000.addEntity(mutableBullet1);
		world1000x1000.addEntity(mutableBullet2);
		
		assertEquals(7,world1000x1000.getAllEntities().size());
		assertEquals(5,world1000x1000.getAllShips().size());
		assertEquals(2,world1000x1000.getAllBullets().size());
		assertTrue(world1000x1000.getAllBullets().contains(mutableBullet1));
	}
	
	/*
	 * Test number 1 tests a ship going diagonal, colliding with both boundaries at the same time.
	 * Test number 2 tests a ship with no velocity.
	 * Test number 3 tests a bullet going downwards.
	 * Test number 4 tests a ship going diagonal, colliding with the vertical boundary.
	 * Test number 5 tests a ship going vertical, for a collision with a vertical boundary.
	 */
	@Test
	public void getTimeCollisionToBoundary_Tests() {
		World world1000x1000 = new World(1000,1000);
		Ship mutableShip1 = new Ship(100,100,20,20,10,0, 5.5E20);
		Ship immutableShip1 = new Ship(70,70,0,0,10,(Math.PI/2));
		Ship mutableShip2 = new Ship(500,500,30,10,10,(Math.PI/2), 10E15);
		Bullet mutableBullet2 = new Bullet(900,900,0,-10,(Bullet.minBulletRadius+1));
		
		assertEquals(890.0/20.0,
				world1000x1000.getTimeCollisionToBoundary(mutableShip1),EPSILON);
		assertEquals(Double.POSITIVE_INFINITY,
				world1000x1000.getTimeCollisionToBoundary(immutableShip1),EPSILON);
		assertEquals(898.0/10.0,
				world1000x1000.getTimeCollisionToBoundary(mutableBullet2),EPSILON);
		assertEquals(490.0/30,
				world1000x1000.getTimeCollisionToBoundary(mutableShip2),EPSILON);
		assertEquals(Double.POSITIVE_INFINITY,
				world1000x1000.getTimeCollisionVerticalBoundary(mutableBullet2),EPSILON);
	}
	
	/*
	 * Tests number 1 & 2 test a ship colliding with a vertical boundary.
	 * Tests number 3 & 4 test a ship with no velocity.
	 */
	@Test
	public void getPositionCollisionWithBoundary_Tests() {
		World world1000x1000 = new World(1000,1000);
		Ship immutableShip1 = new Ship(70,70,0,0,10,(Math.PI/2));
		Ship mutableShip2 = new Ship(500,500,30,10,10,(Math.PI/2), 10E15);
		
		double[] posMutShip2 = world1000x1000.getPositionCollisionWithBoundary(mutableShip2);
		assertEquals(world1000x1000.getWorldWidth(),posMutShip2[0],EPSILON);
		assertEquals((490.0/30.0)*10.0+500.0,posMutShip2[1],EPSILON);
		double[] posImShip1 = world1000x1000.getPositionCollisionWithBoundary(immutableShip1);
		assertEquals(null, posImShip1);
	}
	
	/*
	 * Tests number 1 & 2 test two ships who will collide with each other.
	 * Test number 3 tests a ship who will collide with a boundary first.
	 */
	@Test
	public void getTimeToNextCollisionEntity_Tests() {
		World world1000x1000 = new World(1000,1000);
		Ship mutableShip1 = new Ship(100,100,0,20,10,0, 5.5E20);
		Ship mutableShip2 = new Ship(500,500,30,10,10,(Math.PI/2), 10E15);
		Ship mutableShip3 = new Ship(10,300,10,0,10,0,10E15);
		
		world1000x1000.addEntity(mutableShip1);
		world1000x1000.addEntity(mutableShip2);
		world1000x1000.addEntity(mutableShip3);
		
		double timeToCollisionMutShip1And3 = world1000x1000.getTimeToNextCollisionEntity(mutableShip1);
		double timeToCollisionMutShip3And1 = world1000x1000.getTimeToNextCollisionEntity(mutableShip3);
		double timeToCollisionMutShip2 = world1000x1000.getTimeToNextCollisionEntity(mutableShip2);
		double timeToCollisionWithBoundaryShip2 = world1000x1000.getTimeCollisionToBoundary(mutableShip2);
		assertEquals(9.0, timeToCollisionMutShip1And3, EPSILON);
		assertEquals(9.0, timeToCollisionMutShip3And1, EPSILON);
		assertEquals(timeToCollisionWithBoundaryShip2, timeToCollisionMutShip2, EPSILON);
	}
	
	@Test
	public void getPositionEntityUponCollision_Tests() {
		
	}
	
	
	/*
	 * Test 1 tests the world where a collision between 2 ships will happen first.
	 * Test 2 tests the same world but without one of the colliding ships,
	 * 		as a result mutableShip2 collides to a boundary before any other collision.
	 */
	@Test
	public void getTimeToNextCollision_Tests() {
		World world1000x1000 = new World(1000,1000);
		Ship mutableShip1 = new Ship(100,100,0,20,10,0, 5.5E20);
		Ship mutableShip2 = new Ship(500,500,30,10,10,(Math.PI/2), 10E15);
		Ship mutableShip3 = new Ship(10,300,10,0,10,0,10E15);
		Ship immutableShip1 = new Ship(70,70,0,0,10,(Math.PI/2));
		Ship immutableShip2 = new Ship(700,700,0,0,Ship.minRadiusShip,0);
		Bullet mutableBullet1 = new Bullet(200,200,10,0,Bullet.minBulletRadius);
		
		world1000x1000.addEntity(mutableShip1);
		world1000x1000.addEntity(mutableShip2);
		world1000x1000.addEntity(mutableShip3);
		world1000x1000.addEntity(immutableShip1);
		world1000x1000.addEntity(immutableShip2);
		world1000x1000.addEntity(mutableBullet1);
		
		assertEquals(9.0, world1000x1000.getTimeToNextCollision(), EPSILON);
		
		world1000x1000.removeEntity(mutableShip1);
		assertEquals(490.0/30.0, world1000x1000.getTimeToNextCollision(), EPSILON);
	}
	
	/*
	 * Tests 1 & 2 test a world where a collision between 2 ships will happen first,
	 * 		this function has to return these ships.
	 * Test 3 tests the same world but without one of the colliding ships,
	 * 		as a result this function returns the ship that will collide with a boundary first.
	 */
	@Test
	public void getFirstCollidingEntities_Tests() {
		World world1000x1000 = new World(1000,1000);
		Ship mutableShip1 = new Ship(100,100,0,20,10,0, 5.5E20);
		Ship mutableShip2 = new Ship(500,500,30,10,10,(Math.PI/2), 10E15);
		Ship mutableShip3 = new Ship(10,300,10,0,10,0,10E15);
		Ship immutableShip1 = new Ship(70,70,0,0,10,(Math.PI/2));
		Ship immutableShip2 = new Ship(700,700,0,0,Ship.minRadiusShip,0);
		Bullet mutableBullet1 = new Bullet(200,200,10,0,Bullet.minBulletRadius);
		
		world1000x1000.addEntity(mutableShip1);
		world1000x1000.addEntity(mutableShip2);
		world1000x1000.addEntity(mutableShip3);
		world1000x1000.addEntity(immutableShip1);
		world1000x1000.addEntity(immutableShip2);
		world1000x1000.addEntity(mutableBullet1);
		
		boolean booleanToCheck1 = ((world1000x1000.getFirstCollidingEntities()[0] == mutableShip1) || 
				(world1000x1000.getFirstCollidingEntities()[0] == mutableShip3));
		boolean booleanToCheck2 = ((world1000x1000.getFirstCollidingEntities()[1] == mutableShip1) || 
				(world1000x1000.getFirstCollidingEntities()[1] == mutableShip3));
		assertTrue(booleanToCheck1);
		assertTrue(booleanToCheck2);
		
		world1000x1000.removeEntity(mutableShip1);
		boolean booleanToCheck3 = (world1000x1000.getFirstCollidingEntities()[0] == mutableShip2);
		assertTrue(booleanToCheck3);
	}
	
	/*
	 * Test 1, 2, 3 & 4 test the world where a collision between 2 ships will happen first.
	 * Tests 5 & 6 test the same world but without one of the colliding ships,
	 * 		as a result mutableShip2 collides to a boundary before any other collision.
	 */
	@Test
	public void getPositionNextCollision_Tests() {
		World world1000x1000 = new World(1000,1000);
		Ship mutableShip1 = new Ship(100,100,0,20,10,0, 5.5E20);
		Ship mutableShip2 = new Ship(500,500,30,10,10,(Math.PI/2), 10E15);
		Ship mutableShip3 = new Ship(10,300,10,0,10,0,10E15);
		Ship immutableShip1 = new Ship(70,70,0,0,10,(Math.PI/2));
		Ship immutableShip2 = new Ship(700,700,0,0,Ship.minRadiusShip,0);
		Bullet mutableBullet1 = new Bullet(200,200,10,0,Bullet.minBulletRadius);
		
		world1000x1000.addEntity(mutableShip1);
		world1000x1000.addEntity(mutableShip2);
		world1000x1000.addEntity(mutableShip3);
		world1000x1000.addEntity(immutableShip1);
		world1000x1000.addEntity(immutableShip2);
		world1000x1000.addEntity(mutableBullet1);
		
		assertEquals(100.0, world1000x1000.getPositionNextCollision()[0], EPSILON);
		assertEquals(mutableShip1.getCollisionPosition(mutableShip3)[0], 
				world1000x1000.getPositionNextCollision()[0], EPSILON);
		assertEquals(290.0, world1000x1000.getPositionNextCollision()[1], EPSILON);
		assertEquals(mutableShip1.getCollisionPosition(mutableShip3)[1], 
				world1000x1000.getPositionNextCollision()[1], EPSILON);
		
		world1000x1000.removeEntity(mutableShip1);
		assertEquals(world1000x1000.getWorldWidth(), world1000x1000.getPositionNextCollision()[0], EPSILON);
		assertEquals(world1000x1000.getPositionCollisionWithBoundary(mutableShip2)[1],
				world1000x1000.getPositionNextCollision()[1], EPSILON);
	}
	
	/*
	 * Tests 1, 2 & 3 test a world where a ship fires a bullet, 
	 * 	but the firing position of the bullet is occupied by an other ship,
	 *  meaning the bullet would spawn in the other ship, resulting in overlapping entities.
	 *  As a result both the bullet and the other ship are terminated.
	 */
	@Test
	public void getOverlappingEntities_Tests() {
		World world1000x1000 = new World(1000,1000);
		Ship shootingShip = new Ship(600,600,0,0,10,0,10E15);
		Ship shipInFiringRange = new Ship(620,600,0,0,10,0,10E15);
		Bullet bullet = new Bullet(600,600,0,0,9);
		
		world1000x1000.addEntity(shootingShip);
		world1000x1000.addEntity(shipInFiringRange);
		shootingShip.loadBulletOnShip(bullet);
		world1000x1000.addEntity(bullet);
		shootingShip.putInFiringPosition(bullet);
		
		boolean booleanToCheck1 = ((world1000x1000.getOverlappingEntities()[0] == bullet) || 
				(world1000x1000.getOverlappingEntities()[0] == shipInFiringRange));
		boolean booleanToCheck2 = ((world1000x1000.getOverlappingEntities()[1] == bullet) || 
				(world1000x1000.getOverlappingEntities()[1] == shipInFiringRange));
		assertTrue(booleanToCheck1);
		assertTrue(booleanToCheck2);
	}
	
	/*
	 * This world contains 2 ships, one of the ships fires a bullet,
	 *  but the firing position of the bullet is occupied by the other ship,
	 *  meaning the bullet would spawn in the other ship, resulting in overlapping entities.
	 *  As a result both the bullet and the other ship are terminated.
	 *  
	 * Test 1 tests if the bullet and the other ship are terminated.
	 * Test 2 & 3 check the new position of the ship who survived,
	 *  after keeping the same speed for a given amount of time.
	 */
	@Test
	public void evolve_OverlappingEntities() {
		World world1000x1000 = new World(1000,1000);
		Ship shootingShip = new Ship(600,600,10,0,10,0,10E15);
		Ship shipInFiringRange = new Ship(620,600,0,0,10,0,10E15);
		Bullet bullet = new Bullet(600,600,0,0,Bullet.minBulletRadius);
		
		world1000x1000.addEntity(shootingShip);
		world1000x1000.addEntity(shipInFiringRange);
		shootingShip.loadBulletOnShip(bullet);
		shootingShip.fireBullet();
		
		CollisionListener collisionListener = null;
		world1000x1000.evolve(5, collisionListener);
		
		assertEquals(1,world1000x1000.getAllEntities().size());
		assertEquals(650,shootingShip.getPositionX(),EPSILON);
		assertEquals(600,shootingShip.getPositionY(),EPSILON);
	}
	
	/*
	 * This world contains two ships. One with a velocity in the positive x-direction,
	 *  the other with a velocity in the positive y-direction.
	 *  Both ships collide after 9 seconds, resulting in an added y-velocity for the ship going
	 *  in the positive x-direction. And a diminished y-velocity for the ship going in the
	 *  positive y-direction.
	 *  
	 *  Tests 1, 2, 3 & 4 test the new velocities after 10 seconds.
	 */
	@Test
	public void evolve_CollisionShips() {
		World world1000x1000 = new World(1000,1000);
		Ship mutableShip1 = new Ship(100,100,0,20,10,0, 5.5E20);
		Ship mutableShip3 = new Ship(10,300,10,0,10,0,10E15);
		world1000x1000.addEntity(mutableShip1);
		world1000x1000.addEntity(mutableShip3);
		
		CollisionListener collisionListener = null;
		world1000x1000.evolve(10, collisionListener);
		assertEquals(0.0, mutableShip1.getVelocityX(), EPSILON);
		assertTrue(mutableShip1.getVelocityY() < 20);
		assertEquals(10.0,mutableShip3.getVelocityX(), EPSILON);
		assertTrue(mutableShip3.getVelocityY() > 0);
	}
	
	/*
	 * This world contains a bullet and a ship. The bullet and
	 * 	the ship collide after 9 seconds, resulting in an empty world.
	 * 
	 * This test tests if the world is empty after 10 seconds.
	 */
	@Test
	public void evolve_CollisionShipAndBullet() {
		World world1000x1000 = new World(1000,1000);
		Ship mutableShip1 = new Ship(100,100,0,20,10,0, 5.5E20);
		Bullet bullet = new Bullet(10,300,10,0,10);
		world1000x1000.addEntity(mutableShip1);
		world1000x1000.addEntity(bullet);
		
		assertFalse(world1000x1000.getAllEntities().isEmpty());
		CollisionListener collisionListener = null;
		world1000x1000.evolve(10, collisionListener);
		assertTrue(world1000x1000.getAllEntities().isEmpty());
	}
	

}
