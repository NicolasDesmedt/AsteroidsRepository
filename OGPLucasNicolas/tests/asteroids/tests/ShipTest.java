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
	 * Variable of a ship referencing ship with position [0,0], 
	 * velocity[20,20], radius 10, orientation facing right (0) and mass 5.5E20.
	 * respectively a ship with position [10,10], 
	 * velocity[10,10], radius 10, orientation facing up (PI/2) and mass 10E15.
	 * Variables of bullets referencing a bullet with position [0,0], 
	 * velocity[0,0] and radius minBulletRadius
	 * respectively a bullet with position [0,0], 
	 * velocity[0,0] and radius (minBulletRadius+1)
	 */

	private static Ship mutableShip1, mutableShip2;
	
	private static Bullet mutableMinimalBullet, mutableBullet1;
	
	/**
	 *
	 * Variable of a ship referencing ship with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing up (PI/2).
	 * respectively a ship with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing up (0).
	 * Variable of a world referencing a world with height and width 1000.
	 */

	private static Ship immutableShip1, immutableMinimalShip;
	
	private static World world1000x1000;
	
	/**
	 * Set up a mutable test fixture.
	 * 
	 * @post The variable ship1 references a new ship with position [0,0], 
	 * velocity[20,20], radius 10, orientation facing right (0) and mass 5.5E20.
	 * 
	 * @post The variable ship2 references a new ship with position [0,0], 
	 * velocity[0,0], radius 10, orientation facing right (PI/2) and mass 10E15.
	 * 
	 * @post The variable mutableMinimalBullet references a new bullet with position [0,0], 
	 * velocity[0,0] and radius minBulletRadius
	 * 
	 * @post The variable mutableBullet1 references a new bullet with position with position [0,0], 
	 * velocity[0,0] and radius (minBulletRadius+1)
	 */
	
	@Before
	public void setUpMutableFixture() {
		mutableShip1 = new Ship(100,100,20,20,10,0, 5.5E20);
		mutableShip2 = new Ship(10,10,10,10,10,(Math.PI/2), 10E15);
		
		mutableMinimalBullet = new Bullet(100,100,0,0,Bullet.minBulletRadius);
		mutableBullet1 = new Bullet(100,100,0,0,(Bullet.minBulletRadius+1));
	}
	
	/**
	 * Set up a immutable test fixture.
	 * 
	 * @post The variable immutableShip1 references a new ship with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing right (PI/2).
	 * 
	 * @post The variable immutableMinimalShip references a new ship with position [0,0], 
	 * velocity[0,0], radius minRadiusShip and orientation facing up (0).
	 * 
	 * @post The variable world1000x1000 references a new world with height and width 1000.
	 */
	
	@BeforeClass
	public static void setUpImmutableFixture() {
		immutableShip1 = new Ship(0,0,0,0,10,(Math.PI/2));
		immutableMinimalShip = new Ship(0,0,0,0,Ship.minRadiusShip,0);
		world1000x1000 = new World (1000,1000);
	}
	
	@Test
    public void terminate_LegalCase(){
		mutableShip1.loadBulletOnShip(mutableMinimalBullet);
		mutableShip1.loadBulletOnShip(mutableBullet1);
		mutableShip1.terminate();
        assertTrue(mutableShip1.isTerminated());
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
		mutableShip1.setThrust(true);
		mutableShip1.thrust(10000);
		double[] velocity = mutableShip1.getVelocity();
		assertEquals(40, velocity[0], EPSILON);
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
		double originalMass = mutableShip1.getMass();
		double bulletMass = mutableMinimalBullet.getMass();
		mutableShip1.loadBulletOnShip(mutableMinimalBullet);
		assertTrue(mutableShip1.getBulletsOnShip().contains(mutableMinimalBullet));
		double newMass = mutableShip1.getMass();
		assertEquals((originalMass + bulletMass), newMass, EPSILON);
		assertEquals(mutableMinimalBullet.getShip(), mutableShip1);
		assertEquals(mutableMinimalBullet.getPositionX(), mutableShip1.getPositionX(), EPSILON);
		assertEquals(mutableMinimalBullet.getPositionY(), mutableShip1.getPositionY(), EPSILON);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void loadBulletOnShip_BulletNotInShip(){
		mutableShip2.loadBulletOnShip(mutableMinimalBullet);
	}
	
	@Test
	public void inShip_LegalCase(){
		assertTrue(mutableShip1.inShip(mutableMinimalBullet));
		
	}
	
	@Test
	public void inShip_BulletOutOfShip(){
		assertFalse(mutableShip2.inShip(mutableMinimalBullet));

	}
	
	@Test
	public void loadBulletsOnShip_LegalCase(){
		Bullet[] bullets = {mutableBullet1, mutableMinimalBullet};
		double originalMass = mutableShip1.getMass();
		double bulletsMass = mutableMinimalBullet.getMass() + mutableBullet1.getMass();
		mutableShip1.loadBulletsOnShip(bullets);
		assertTrue(mutableShip1.getBulletsOnShip().contains(mutableMinimalBullet));
		assertTrue(mutableShip1.getBulletsOnShip().contains(mutableBullet1));
		double newMass = mutableShip1.getMass();
		assertEquals((originalMass + bulletsMass), newMass, EPSILON);
		assertEquals(mutableMinimalBullet.getShip(), mutableShip1);
		assertEquals(mutableBullet1.getShip(), mutableShip1);
		assertEquals(mutableMinimalBullet.getPositionX(), mutableShip1.getPositionX(), EPSILON);
		assertEquals(mutableMinimalBullet.getPositionY(), mutableShip1.getPositionY(), EPSILON);
		assertEquals(mutableBullet1.getPositionX(), mutableShip1.getPositionX(), EPSILON);
		assertEquals(mutableBullet1.getPositionY(), mutableShip1.getPositionY(), EPSILON);
	}
	
	@Test
	public void getNbBulletsOnShip_LegalCase(){
		mutableShip1.loadBulletOnShip(mutableMinimalBullet);
		mutableShip1.loadBulletOnShip(mutableBullet1);
		int nbOfBullets = mutableShip1.getNbBulletsOnShip();
		assertEquals(2, nbOfBullets, EPSILON);
	}
	
	@Test
	public void removeBulletFromShip_LegalCase(){
		mutableShip1.loadBulletOnShip(mutableMinimalBullet);
		mutableShip1.removeBulletFromShip(mutableMinimalBullet);
		Ship ship = mutableMinimalBullet.getShip();
		assertEquals(null, ship);
		assertFalse(mutableShip1.getBulletsOnShip().contains(mutableMinimalBullet));
	}
	
	@Test
	public void move_LegalCase(){
		mutableShip1.loadBulletOnShip(mutableMinimalBullet);
		mutableShip1.loadBulletOnShip(mutableBullet1);
		mutableShip1.move(20);
		assertEquals(mutableMinimalBullet.getPositionX(), mutableShip1.getPositionX(), EPSILON);
		assertEquals(mutableMinimalBullet.getPositionY(), mutableShip1.getPositionY(), EPSILON);
		assertEquals(mutableBullet1.getPositionX(), mutableShip1.getPositionX(), EPSILON);
		assertEquals(mutableBullet1.getPositionY(), mutableShip1.getPositionY(), EPSILON);
	}
	
	@Test
	public void fireBullet_LegalCase(){
		mutableShip1.setWorld(world1000x1000);
		mutableShip1.loadBulletOnShip(mutableMinimalBullet);
		mutableShip1.fireBullet();
		assertEquals(mutableMinimalBullet.getVelocityX(), Ship.bulletSpeed, EPSILON);
		assertEquals(mutableMinimalBullet.getVelocityY(), 0, EPSILON);
		assertFalse(mutableShip1.getBulletsOnShip().contains(mutableMinimalBullet));
		assertEquals(mutableMinimalBullet.getSource(), mutableShip1);
	}
	
	@Test
	public void putInFiringPosition_LegalCase(){
		mutableShip1.loadBulletOnShip(mutableMinimalBullet);
		mutableShip1.putInFiringPosition(mutableMinimalBullet);
		assertEquals(mutableMinimalBullet.getPositionX(), 111, EPSILON);
		assertEquals(mutableMinimalBullet.getPositionY(), 100, EPSILON);
	}
	
	@Test
	public void selectLoadedBullet_LegalCase(){
		mutableShip1.loadBulletOnShip(mutableMinimalBullet);
		Bullet bullet = mutableShip1.selectLoadedBullet();
		assertEquals(bullet, mutableMinimalBullet);
	}
	
	@Test
	public void selectLoadedBullet_ContainsNoBullet(){
		Bullet bullet = mutableShip1.selectLoadedBullet();
		assertEquals(bullet, null);
	}
	
	@Test
	public void collidesWithPlanetoid_LegalCase(){
		world1000x1000.addEntity(mutableShip1);
		Planetoid planetoid = new Planetoid(250, 250, 500, 0, 50, 0);
		for(int i=0; i<1000; i++){
			mutableShip1.collidesWithPlanetoid(planetoid);
			assertTrue(world1000x1000.withinBoundaries(mutableShip1));
		}
	}
	
}