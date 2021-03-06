package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.*;

import asteroids.model.*;

/**
 * A class collecting tests for all the public methods of the bullet class.
 * 
 * @version 1.0
 * @author Lucas Desard and Nicolas Desmedt
 *
 */

public class BulletTest {
	
	/**
	 * Variable defining the maximum acceptable distance of the result due to rounding issues.
	 */
	private static final double EPSILON = 0.0001;
	
	/**
	 * Variable of a ship referencing ship with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing up (PI/2).
	 * Variables of bullets referencing a bullet with position [200,20], 
	 * velocity[10,-10] and radius minBulletRadius
	 * respectively a bullet with position [20,20], 
	 * velocity[0,0] and radius (minBulletRadius+1)
	 * Variable of a world referencing a world with height and width 1000.
	 */
	private static Ship mutableShip1;
	
	private static Bullet mutableMinimalBullet, mutableBullet1;
	
	private static World world1000x1000;

	
	/**
	 * Set up a mutable test fixture.
	 * 
	 * @post The variable mutableMinimalBullet references a new bullet with position [500,500], 
	 * velocity[0,250] and radius minBulletRadius
	 * 
	 * @post The variable mutableBullet1 references a new bullet with position with position [120,120], 
	 * velocity[0,0] and radius (minBulletRadius+1)
	 * 
	 * @post The variable mutableShip1 references a new ship with position [120,120], 
	 * velocity[0,0], radius 50 and orientation facing up (PI/2).
	 * 
	 * @post The variable world1000x1000 references a new world with height and width 1000.
	 */
	
	@Before
	public void setUpMutableFixture() {
		mutableMinimalBullet = new Bullet(500,500,0,250,Bullet.minBulletRadius);
		mutableBullet1 = new Bullet(120,120,0,0,(Bullet.minBulletRadius+1));
		mutableShip1 = new Ship(120,120,0,0,50,(Math.PI/2));		
		world1000x1000 = new World (1000,1000);
		world1000x1000.addEntity(mutableMinimalBullet);
		world1000x1000.addEntity(mutableShip1);
		mutableShip1.loadBulletOnShip(mutableBullet1);
	}
	
	@Test
    public void terminate_LegalCase(){
		mutableBullet1.terminate();
        assertTrue(mutableBullet1.getShip() == null);
        assertTrue(mutableBullet1.isTerminated());
    }
	
	/*
	 * Test number 1 tests a legal case.
	 * Test number 2 tests the minimal possible radius for a given ship.
	 * Test number 3 tests a value just under the minimal possible radius for a given ship.
	 * Test number 4 tests in case the POSITIVE_INFINITY for double is provided.
	 * Test number 5 tests in case NaN is provided.
	 */
	@Test
	public void isValidRadius_Tests() {
		assertTrue(mutableMinimalBullet.isValidRadius(Bullet.minBulletRadius+10));
		assertTrue(mutableMinimalBullet.isValidRadius(Bullet.minBulletRadius));
		assertFalse(mutableMinimalBullet.isValidRadius(Bullet.minBulletRadius-1));
		assertFalse(mutableMinimalBullet.isValidRadius(Double.POSITIVE_INFINITY));
		assertFalse(mutableMinimalBullet.isValidRadius(Double.NaN));
	}
	
	@Test
	public void setShip_LegalCase() {
		mutableMinimalBullet.setShip(mutableShip1);
		Ship ship = mutableMinimalBullet.getShip();
		assertEquals(ship, mutableShip1);
	}
	
	@Test
	public void incrementCounterBoundaryCollisions_LegalCase() {
		world1000x1000.evolve(2, null);
		int boundaryCollisions = mutableMinimalBullet.getCounterBoundaryCollisions();
		assertEquals(1, boundaryCollisions);
	}
	
	@Test
	public void collidesWithBoundary_LegalCase() {
		world1000x1000.evolve(2, null);
		assertEquals(mutableMinimalBullet.getCounterBoundaryCollisions(), 1);
		assertEquals(mutableMinimalBullet.getVelocityX(), 0, EPSILON);
		assertEquals(mutableMinimalBullet.getVelocityY(), -250, EPSILON);
	}
	
	@Test
	public void collidesWithBoundary_maxCollisions() {
		world1000x1000.evolve(10, null);
		assertEquals(mutableMinimalBullet.getCounterBoundaryCollisions(), 3);
		assertTrue(mutableMinimalBullet.isTerminated());
	}
	
	@Test
	public void hits_LegalCase2Bullets() {
		mutableMinimalBullet.hits(mutableBullet1);
		assertTrue(mutableMinimalBullet.isTerminated());
		assertTrue(mutableBullet1.isTerminated());
	}
	
	@Test
	public void hits_LegalCaseOtherShip() {
		mutableMinimalBullet.hits(mutableShip1);
		assertTrue(mutableMinimalBullet.isTerminated());
		assertTrue(mutableShip1.isTerminated());
	}
	
	@Test
	public void hits_LegalCaseSourceShip() {
		mutableShip1.fireBullet();
		world1000x1000.evolve(10, null);
		assertEquals(mutableBullet1.getShip(), mutableShip1);
		assertTrue(mutableShip1.getBulletsOnShip().contains(mutableBullet1));
	}
	
}