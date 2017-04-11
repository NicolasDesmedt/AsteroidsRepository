package asteroids.tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.*;

import asteroids.model.Ship;
import asteroids.model.Bullet;


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
	private static Ship immutableShip1, immutableShip2, immutableShip3, immutableMinimalShip;
	
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
	 * @post The variable immutableShip2 references a new ship with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing up (PI/2).
	 * 
	 * @post The variable immutableShip2 references a new ship with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing right (0).
	 */
	
	@BeforeClass
	public static void setUpImmutableFixture() {
		immutableShip1 = new Ship(100,-100,-20,0,10,0);
		immutableShip2 = new Ship(0,0,0,0,10,(Math.PI/2));
		immutableShip3 = new Ship(10,10,0,-20,10,0);
		immutableMinimalShip = new Ship(0,0,0,0,Ship.MIN_RADIUS_SHIP,0);
		
	}
	
	@Test
	public void setMass_LegalCase() {
		mutableShip1.setMass(7E15);
		double mass = mutableShip1.getMass();
		assertEquals(7E15, mass, EPSILON);
	}
	
	@Test
	public void setMass_UnderMinDensity() {
		mutableShip1.setMass(0);
		double mass = mutableShip1.getMass();
		assertEquals(5.5E20, mass, EPSILON);
	}

	@Test
	public void setMass_MaxValue() {
		mutableShip1.setMass(Double.MAX_VALUE);
		double mass = mutableShip1.getMass();
		assertEquals(5.5E20, mass, EPSILON);
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
		assertTrue(immutableMinimalShip.isValidRadius(Ship.MIN_RADIUS_SHIP+10));
		assertTrue(immutableMinimalShip.isValidRadius(Ship.MIN_RADIUS_SHIP));
		assertFalse(immutableMinimalShip.isValidRadius(Ship.MIN_RADIUS_SHIP-1));
		assertFalse(immutableMinimalShip.isValidRadius(Double.POSITIVE_INFINITY));
		assertFalse(immutableMinimalShip.isValidRadius(Double.NaN));
		
	}
	
	@Test
	public void getOrientation_LegalCase(){
		double radius = immutableShip2.getOrientation();
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
	public void getVelocity_LegalCase() {
		double[] velocity = immutableShip1.getVelocity();
		assertEquals(-20, velocity[0], EPSILON);
		assertEquals(0, velocity[1], EPSILON);
	}
	
	@Test
	public void setVelocity_LegalCase() {
		mutableShip1.setVelocity(10,-10);
		double[] velocity = mutableShip1.getVelocity();
		assertEquals(10, velocity[0], EPSILON);
		assertEquals(-10, velocity[1], EPSILON);
	}

	@Test
	public void setVelocity_ExceedingSpeedOfLight() {
		mutableShip1.setVelocity(400000,200000);
		double[] velocity = mutableShip1.getVelocity();
		assertEquals(268328.1573, velocity[0], EPSILON);
		assertEquals(134164.0787, velocity[1], EPSILON);
	}

	@Test
	public void setVelocity_XVelocityInfinite() {
		mutableShip1.setVelocity(Double.POSITIVE_INFINITY,-10);
		double[] velocity = mutableShip1.getVelocity();
		assertEquals(immutableShip1.getMaxSpeed(), velocity[0], EPSILON);
		assertEquals(0, velocity[1], EPSILON);
	}
	
	@Test
	public void setVelocity_XAndYVelocityInfinite() {
		mutableShip1.setVelocity(Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY);
		double[] velocity = mutableShip1.getVelocity();
		assertEquals((immutableShip1.getMaxSpeed()/Math.sqrt(2)), velocity[0], EPSILON);
		assertEquals((-immutableShip1.getMaxSpeed()/Math.sqrt(2)), velocity[1], EPSILON);
	}
	
	@Test
	public void setVelocity_YNaN() {
		mutableShip1.setVelocity(10,Double.NaN);
		double[] velocity = mutableShip1.getVelocity();
		assertEquals(10, velocity[0], EPSILON);
		assertEquals(0, velocity[1], EPSILON);
	}

	@Test
	public void Thrust_LegalCase(){
		mutableShip1.thrust(10);
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
		mutableShip2.move(10);
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
		Bullet bullet = mutableShip2.selectLoadedBullet();
		assertEquals(bullet, null);
	}
	
	
/*
	@Test
	public void getPosition_LegalCase() {
		double[] position = immutableShip1.getPosition();
		assertEquals(100, position[0], EPSILON);
		assertEquals(-100, position[1], EPSILON);
	}
	
	@Test
	public void setPosition_LegalCase() throws IllegalArgumentException {
		mutableShip1.setPosition(100,200);
		double[] position = mutableShip1.getPosition();
		assertEquals(100, position[0], EPSILON);
		assertEquals(200, position[1], EPSILON);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setPosition_NaN() throws IllegalArgumentException {
		mutableShip1.setPosition(Double.NaN,200);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setPosition_Infinite() throws IllegalArgumentException {
		mutableShip1.setPosition(Double.NEGATIVE_INFINITY,200);
	}
	
	@Test
	public void getRadius_LegalCase(){
		double radius = immutableShip1.getRadius();
		assertEquals(10, radius, EPSILON);
	}
	
	@Test
	public void setRadius_LegalCase() throws IllegalArgumentException {
		double radius = mutableShip1.getRadius();
		assertEquals(10, radius, EPSILON);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setRadius_UnderMinimumRadius() throws IllegalArgumentException {
		new Ship(0,0,20,20,(Ship.getMinRadius()-1),0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setRadius_NaN() throws IllegalArgumentException {
		new Ship(0,0,20,20,Double.NaN,0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setRadius_Infinite() throws IllegalArgumentException {
		new Ship(0,0,20,20,Double.POSITIVE_INFINITY,0);
	}
	
	@Test
	public void Move_LegalCase() throws IllegalArgumentException{
		mutableShip1.move(2);
		double[] position = mutableShip1.getPosition();
		assertEquals(40, position[0], EPSILON);
		assertEquals(40, position[1], EPSILON);
	}
	
	@Test
	public void Move_DurationIs0() throws IllegalArgumentException{
		mutableShip1.move(0);
		double[] position = mutableShip1.getPosition();
		assertEquals(0, position[0], EPSILON);
		assertEquals(0, position[1], EPSILON);
	}
	
	@Test
	public void Move_VelocityIs0() throws IllegalArgumentException{
		mutableShip2.move(2);
		double[] position = mutableShip1.getPosition();
		assertEquals(0, position[0], EPSILON);
		assertEquals(0, position[1], EPSILON);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void Move_DurationIsNaN() throws IllegalArgumentException{
		mutableShip1.move(Double.NaN);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void Move_DurationIsInfinite() throws IllegalArgumentException{
		mutableShip1.move(Double.POSITIVE_INFINITY);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void Move_DurationIsNegative() throws IllegalArgumentException{
		mutableShip1.move(-1);
	}
	
	@Test
	public void GetDistanceBetween_LegalCase(){
		double distance = immutableShip1.getDistanceBetween(immutableShip2);
		assertEquals((Math.sqrt(20000)-immutableShip1.getRadius()-immutableShip2.getRadius()), distance, EPSILON);
	}
	
	@Test
	public void GetDistanceBetween_Itselfs(){
		double distance = immutableShip1.getDistanceBetween(immutableShip1);
		assertEquals(0, distance, EPSILON);
	}
	
	@Test(expected = NullPointerException.class)
	public void GetDistanceBetween_NullShip(){
		immutableShip1.getDistanceBetween(null);
	}
	
	@Test
	public void Overlap_LegalCase(){
		assertTrue(immutableShip2.overlap(immutableShip3));
	}
	
	@Test
	public void Overlap_Itself(){
		assertTrue(immutableShip1.overlap(immutableShip1));
	}
	
	@Test(expected = NullPointerException.class)
	public void Overlap_NullShip(){
		immutableShip1.overlap(null);
	}
	
	@Test
	public void GetTimeToCollision_LegalCase() throws IllegalStateException{
		double timeToCollision = immutableShip1.getTimeToCollision(immutableShip3);
		assertEquals(4.5, timeToCollision, EPSILON);
	}
	
	@Test
	public void GetTimeToCollision_NoCollision() throws IllegalStateException, NullPointerException{
		double timeToCollision = immutableShip1.getTimeToCollision(immutableShip2);
		assertEquals(Double.POSITIVE_INFINITY, timeToCollision, EPSILON);
	}
	
	@Test(expected = IllegalStateException.class)
	public void GetTimeToCollision_OverlappingShips() throws IllegalStateException, NullPointerException{
		immutableShip2.getTimeToCollision(immutableShip3);
	}
	
	@Test(expected = NullPointerException.class)
	public void GetTimeToCollision_NullShip() throws IllegalStateException, NullPointerException{
		immutableShip2.getTimeToCollision(null);
	}
	
	@Test
	public void getCollisionPosition_LegalCase() throws NullPointerException, IllegalStateException{
		double[] CollisionPosition = immutableShip1.getCollisionPosition(immutableShip3);
		assertEquals(10, CollisionPosition[0], EPSILON);
		assertEquals(-70, CollisionPosition[1], EPSILON);
	}

	@Test
	public void getCollisionPosition_NoCollision() throws NullPointerException, IllegalStateException{
		double[] CollisionPosition = immutableShip1.getCollisionPosition(immutableShip2);
		assertEquals(null, CollisionPosition);
	}

	@Test(expected = IllegalStateException.class)
	public void getCollisionPosition_OverlappingShips() throws NullPointerException, IllegalStateException{
		immutableShip2.getCollisionPosition(immutableShip3);
	}
	
	@Test(expected = NullPointerException.class)
	public void getCollisionPosition_NullShip() throws NullPointerException, IllegalStateException{
		immutableShip2.getCollisionPosition(null);
	}
*/
}
