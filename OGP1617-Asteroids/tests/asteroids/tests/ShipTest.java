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

public class ShipTest {
	
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
		mutableShip2.loadBulletOnShip(mutableMinimalBullet);
		mutableShip2.loadBulletOnShip(mutableBullet1);
		mutableShip2.terminate();
        assertTrue(mutableShip2.isTerminated());
        assertTrue(mutableMinimalBullet.isTerminated());
        assertTrue(mutableBullet1.isTerminated());
    }
	
	/*
	 * Test number 1 tests a legal case.
	 * Test number 2 tests the minimal possible mass for a given ship.
	 * Test number 3 tests a value just under the minimal possible mass for a given ship.
	 * Test number 4 tests in case the MAX_VALUE for double is provided.
	 * Test number 5 tests in case NaN is provided.
	 */
	@Test
	public void isValidMass_Tests() {
		assertTrue(immutableMinimalShip.isValidMass(immutableMinimalShip.calculateMinimalMass()+20));
		assertTrue(immutableMinimalShip.isValidMass(immutableMinimalShip.calculateMinimalMass()));
		assertFalse(immutableMinimalShip.isValidMass(immutableMinimalShip.calculateMinimalMass()-1));
		assertFalse(immutableMinimalShip.isValidMass(Double.MAX_VALUE));
		assertFalse(immutableMinimalShip.isValidMass(Double.NaN));
		
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
		assertTrue(immutableMinimalShip.isValidRadius(Ship.minRadiusShip+10));
		assertTrue(immutableMinimalShip.isValidRadius(Ship.minRadiusShip));
		assertFalse(immutableMinimalShip.isValidRadius(Ship.minRadiusShip-1));
		assertFalse(immutableMinimalShip.isValidRadius(Double.POSITIVE_INFINITY));
		assertFalse(immutableMinimalShip.isValidRadius(Double.NaN));
		
	}
	
	@Test
	public void getOrientation_LegalCase(){
		double radius = immutableShip1.getOrientation();
		assertEquals((Math.PI/2), radius, EPSILON);
	}
	
	/*
	 * Test number 1 tests a legal case.
	 * Test number 2 tests the minimal possible radius.
	 * Test number 3 tests the maximal possible radius.
	 * Test number 4 tests a value just under the minimal possible radius.
	 * Test number 5 tests a value just over the minimal possible radius.
	 * Test number 6 tests in case NaN is provided.
	 */
	@Test
	public void isValidOrientation_Tests() {
		assertTrue(immutableMinimalShip.isValidOrientation(1));
		assertTrue(immutableMinimalShip.isValidOrientation(0));
		assertTrue(immutableMinimalShip.isValidOrientation(2*Math.PI));
		assertFalse(immutableMinimalShip.isValidOrientation(-0.01));
		assertFalse(immutableMinimalShip.isValidOrientation(2*Math.PI+0.01));
		assertFalse(immutableMinimalShip.isValidOrientation(Double.NaN));
		
	}
	
	@Test
	public void setOrientation_LegalCase(){
		mutableShip1.setOrientation(Math.PI);
		double radius = mutableShip1.getOrientation();
		assertEquals(Math.PI, radius, EPSILON);
	}
	
	@Test(expected = AssertionError.class)
	public void setOrientation_Negative(){
		mutableShip1.setOrientation(-1);
	}
	
	@Test(expected = AssertionError.class)
	public void setOrientation_Over2PI(){
		mutableShip1.setOrientation(7);
	}
	
	@Test
	public void Turn_LegalCase(){
		mutableShip2.turn(Math.PI);
		double orientation = mutableShip2.getOrientation();
		assertEquals(((Math.PI*3)/2), orientation, EPSILON);
	}

	@Test
	public void Thrust_LegalCase(){
		mutableShip1.thrust(10);
		double[] velocity = mutableShip1.getVelocity();
		assertEquals(40, velocity[0], 0.01);
		assertEquals(20, velocity[1], EPSILON);
	}
	
	@Test
	public void Thrust_NegativeAmount(){
		mutableShip1.thrust(-10);
		double[] velocity = mutableShip1.getVelocity();
		assertEquals(20, velocity[0], EPSILON);
		assertEquals(20, velocity[1], EPSILON);
	}
	
	@Test
	public void setThrusterForce_LegalCase() {
		mutableShip1.setThrusterForce(8E10);
		double thrusterForce = mutableShip1.getThrusterForce();
		assertEquals(8E10, thrusterForce, EPSILON);
	}
	
	@Test
	public void setThrusterForce_Zero() {
		mutableShip1.setThrusterForce(0);
		double thrusterForce = mutableShip1.getThrusterForce();
		assertEquals(0, thrusterForce, EPSILON);
	}
	
	@Test
	public void setThrusterForce_NegativeForce() {
		mutableShip1.setThrusterForce(-1);
		double thrusterForce = mutableShip1.getThrusterForce();
		assertEquals(Ship.defaultThrusterForce, thrusterForce, EPSILON);
	}
	
	@Test
	public void setThrusterForce_Infinity() {
		mutableShip1.setThrusterForce(Double.POSITIVE_INFINITY);
		double thrusterForce = mutableShip1.getThrusterForce();
		assertEquals(Ship.defaultThrusterForce, thrusterForce, EPSILON);
	}
	
	@Test
	public void setThrusterForce_NaN() {
		mutableShip1.setThrusterForce(Double.NaN);
		double thrusterForce = mutableShip1.getThrusterForce();
		assertEquals(Ship.defaultThrusterForce, thrusterForce, EPSILON);
	}
	
	@Test
	public void loadBulletOnShip_LegalCase(){
		double originalMass = mutableShip2.getMass();
		double bulletMass = mutableMinimalBullet.getMass();
		mutableShip2.loadBulletOnShip(mutableMinimalBullet);
		assertTrue(mutableShip2.getBulletsOnShip().contains(mutableMinimalBullet));
		double newMass = mutableShip2.getMass();
		assertEquals((originalMass + bulletMass), newMass, EPSILON);
		assertEquals(mutableMinimalBullet.getShip(), mutableShip2);
		assertEquals(mutableMinimalBullet.getPositionX(), mutableShip2.getPositionX(), EPSILON);
		assertEquals(mutableMinimalBullet.getPositionY(), mutableShip2.getPositionY(), EPSILON);
		assertEquals(mutableMinimalBullet.getVelocityX(), mutableShip2.getVelocityX(), EPSILON);
		assertEquals(mutableMinimalBullet.getVelocityY(), mutableShip2.getVelocityY(), EPSILON);
	}
	
	@Test
	public void loadBulletsOnShip_LegalCase(){
		Bullet[] bullets = {mutableBullet1, mutableMinimalBullet};
		double originalMass = mutableShip2.getMass();
		double bulletsMass = mutableMinimalBullet.getMass() + mutableBullet1.getMass();
		mutableShip2.loadBulletsOnShip(bullets);
		assertTrue(mutableShip2.getBulletsOnShip().contains(mutableMinimalBullet));
		assertTrue(mutableShip2.getBulletsOnShip().contains(mutableBullet1));
		double newMass = mutableShip2.getMass();
		assertEquals((originalMass + bulletsMass), newMass, EPSILON);
		assertEquals(mutableMinimalBullet.getShip(), mutableShip2);
		assertEquals(mutableBullet1.getShip(), mutableShip2);
		assertEquals(mutableMinimalBullet.getPositionX(), mutableShip2.getPositionX(), EPSILON);
		assertEquals(mutableMinimalBullet.getPositionY(), mutableShip2.getPositionY(), EPSILON);
		assertEquals(mutableMinimalBullet.getVelocityX(), mutableShip2.getVelocityX(), EPSILON);
		assertEquals(mutableMinimalBullet.getVelocityY(), mutableShip2.getVelocityY(), EPSILON);
		assertEquals(mutableBullet1.getPositionX(), mutableShip2.getPositionX(), EPSILON);
		assertEquals(mutableBullet1.getPositionY(), mutableShip2.getPositionY(), EPSILON);
		assertEquals(mutableBullet1.getVelocityX(), mutableShip2.getVelocityX(), EPSILON);
		assertEquals(mutableBullet1.getVelocityY(), mutableShip2.getVelocityY(), EPSILON);
	}
	
	@Test
	public void getNbBulletsOnShip_LegalCase(){
		mutableShip2.loadBulletOnShip(mutableMinimalBullet);
		mutableShip2.loadBulletOnShip(mutableBullet1);
		int nbOfBullets = mutableShip2.getNbBulletsOnShip();
		assertEquals(2, nbOfBullets, EPSILON);
	}
	
	@Test
	public void removeBulletFromShip_LegalCase(){
		mutableShip2.loadBulletOnShip(mutableMinimalBullet);
		mutableShip2.removeBulletFromShip(mutableMinimalBullet);
		Ship ship = mutableMinimalBullet.getShip();
		assertEquals(null, ship);
		assertFalse(mutableShip2.getBulletsOnShip().contains(mutableMinimalBullet));
	}
	
	@Test
	public void move_LegalCase(){
		mutableShip2.loadBulletOnShip(mutableMinimalBullet);
		mutableShip2.loadBulletOnShip(mutableBullet1);
		mutableShip2.move(20);
		assertEquals(mutableMinimalBullet.getPositionX(), mutableShip2.getPositionX(), EPSILON);
		assertEquals(mutableMinimalBullet.getPositionY(), mutableShip2.getPositionY(), EPSILON);
		assertEquals(mutableBullet1.getPositionX(), mutableShip2.getPositionX(), EPSILON);
		assertEquals(mutableBullet1.getPositionY(), mutableShip2.getPositionY(), EPSILON);
	}
	
	@Test
	public void fireBullet_LegalCase(){
		mutableShip2.loadBulletOnShip(mutableMinimalBullet);
		mutableShip2.fireBullet();
		assertEquals(mutableMinimalBullet.getVelocityX(), 0, EPSILON);
		assertEquals(mutableMinimalBullet.getVelocityY(), Ship.bulletSpeed, EPSILON);
		assertFalse(mutableShip2.getBulletsOnShip().contains(mutableMinimalBullet));
		assertEquals(mutableMinimalBullet.getSource(), mutableShip2);

	}
	
	@Test
	public void putInFiringPosition_LegalCase(){
		mutableShip2.loadBulletOnShip(mutableMinimalBullet);
		mutableShip2.putInFiringPosition(mutableMinimalBullet);
		assertEquals(mutableMinimalBullet.getPositionX(), 10, EPSILON);
		assertEquals(mutableMinimalBullet.getPositionY(), (10+mutableShip2.getRadius()+mutableMinimalBullet.getRadius()), EPSILON);

	}
	
	@Test
	public void selectLoadedBullet_LegalCase(){
		mutableShip2.loadBulletOnShip(mutableMinimalBullet);
		Bullet bullet = mutableShip2.selectLoadedBullet();
		assertEquals(bullet, mutableMinimalBullet);
	}
	
	@Test
	public void selectLoadedBullet_ContainsNoBullet(){
		Bullet bullet = mutableShip1.selectLoadedBullet();
		assertEquals(bullet, null);
	}
	
}