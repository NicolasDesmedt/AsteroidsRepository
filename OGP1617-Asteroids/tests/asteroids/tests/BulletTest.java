package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.*;

import asteroids.model.*;

/**
 * A class collecting tests for all the public methods of the ship class.
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
	 * Variables referencing ships with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing right (0),
	 * respectively with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing right (0).
	 */
	private static Ship mutableShip1, mutableShip2;
	
	/**
	 * Variables referencing ships with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing right (0),
	 * respectively with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing right (0).
	 */
	private static Ship immutableShip1, immutableMinimalShip;
	
	private static Bullet mutableMinimalBullet, mutableBullet1;
	
	/**
	 * Set up a mutable test fixture.
	 * 
	 * @post The variable ship1 references a new ship with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing right (0).
	 * 
	 * @post The variable ship2 references a new ship with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing up (PI/2).
	 */
	
	@Before
	public void setUpMutableFixture() {
		mutableShip1 = new Ship(0,0,20,20,10,0, 5.5E20);
		mutableShip2 = new Ship(10,10,10,10,10,(Math.PI/2), 10E15);
		
		mutableMinimalBullet = new Bullet(0,0,0,0,Bullet.minBulletRadius);
		mutableBullet1 = new Bullet(0,0,0,0,(Bullet.minBulletRadius+1));
	}
	
	/**
	 * Set up a immutable test fixture.
	 * 
	 * @post The variable immutableShip1 references a new ship with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing right (0).
	 * 
	 * @post The variable immutableShip1 references a new ship with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing up (PI/2).
	 * 
	 * @post The variable immutableShip1 references a new ship with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing right (0).
	 */
	
	@BeforeClass
	public static void setUpImmutableFixture() {
		immutableShip1 = new Ship(0,0,0,0,10,(Math.PI/2));
		immutableMinimalShip = new Ship(0,0,0,0,Ship.minRadiusShip,0);
		
	}
	
	@Test
    public void terminate_LegalCase(){
		mutableBullet1.setShip(immutableShip1);
		mutableBullet1.terminate();
        assertTrue(mutableBullet1.getShip() == null);
        assertTrue(mutableBullet1.isTerminated());
    }
	
	/*
	 * Test number 1 tests a legal case.
	 * Test number 2 tests the minimal possible mass for a given bullet.
	 * Test number 3 tests a value just under the minimal possible mass for a given bullet.
	 * Test number 4 tests in case the MAX_VALUE for double is provided.
	 * Test number 5 tests in case NaN is provided.
	 */
	@Test
	public void isValidMass_Tests() {
		assertTrue(mutableBullet1.isValidMass(mutableBullet1.calculateMinimalMass()+20));
		assertTrue(mutableBullet1.isValidMass(mutableBullet1.calculateMinimalMass()));
		assertFalse(mutableBullet1.isValidMass(mutableBullet1.calculateMinimalMass()-1));
		assertFalse(mutableBullet1.isValidMass(Double.MAX_VALUE));
		assertFalse(mutableBullet1.isValidMass(Double.NaN));
	}
	
	
	
}