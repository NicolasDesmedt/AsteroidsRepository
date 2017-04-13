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

public class EntityTest {
	
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
	private static Ship immutableShip1, immutableShip2, immutableShip3;
	
	
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
		immutableShip3 = new Ship(10,10,0,-20,10,0,10E15,2E5);
	}
	
	@Test
	public void getPosition_LegalCase() {
		double[] position = immutableShip1.getPosition();
		assertEquals(100, position[0], EPSILON);
		assertEquals(-100, position[1], EPSILON);
	}
	
	/*
	 * Test number 1 tests a legal case.
	 * Test number 2 tests if the x position is NaN.
	 * Test number 3 tests if the y position is NaN.
	 * Test number 4 tests if the both positions are NaN.
	 * Test number 5 tests if the x position is infinite.
	 * Test number 6 tests if the y position is infinite.
	 * Test number 7 tests if the both positions are infinite.
	 * Test number 8 tests when a double array of length 3 is given.
	 */
	@Test
	public void isValidPosition_Tests() {
		assertTrue(Entity.isValidPosition(new double[] {0, 0}));
		assertFalse(Entity.isValidPosition(new double[] {Double.NaN, 0.0}));
		assertFalse(Entity.isValidPosition(new double[] {0.0, Double.NaN}));
		assertFalse(Entity.isValidPosition(new double[] {Double.NaN, Double.NaN}));
		assertFalse(Entity.isValidPosition(new double[] {Double.POSITIVE_INFINITY, 0.0}));
		assertFalse(Entity.isValidPosition(new double[] {0.0, Double.POSITIVE_INFINITY}));
		assertFalse(Entity.isValidPosition(new double[] {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY}));
		assertFalse(Entity.isValidPosition(new double[] {10, 0.0, 3}));
		
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
	public void getVelocity_LegalCase() {
		double[] velocity = immutableShip1.getVelocity();
		assertEquals(-20, velocity[0], EPSILON);
		assertEquals(0, velocity[1], EPSILON);
	}
	
	/*
	 * Test number 1 tests a legal case.
	 * Test number 2 tests if the x velocity is NaN.
	 * Test number 3 tests if the y velocity is NaN.
	 * Test number 4 tests if the both velocities are NaN.
	 * Test number 5 tests when a double array of length 3 is given.
	 */
	@Test
	public void isValidVelocity_Tests() {
		assertTrue(Entity.isValidVelocity(new double[] {0, 0}));
		assertFalse(Entity.isValidVelocity(new double[] {Double.NaN, 0.0}));
		assertFalse(Entity.isValidVelocity(new double[] {0.0, Double.NaN}));
		assertFalse(Entity.isValidVelocity(new double[] {Double.NaN, Double.NaN}));
		assertFalse(Entity.isValidVelocity(new double[] {10, 0.0, 3}));
		
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
	public void getSpeed_LegalCase() {
		double speed = immutableShip1.getSpeed(Math.sqrt(2), Math.sqrt(2));
		assertEquals(2, speed, EPSILON);
	}
	
	@Test
	public void getRadius_LegalCase(){
		double radius = immutableShip1.getRadius();
		assertEquals(10, radius, EPSILON);
	}
	
	@Test
	public void getMaxSpeed_LegalCase(){
		double maxSpeed = immutableShip3.getMaxSpeed();
		assertEquals(2E5, maxSpeed, EPSILON);
	}
	
	@Test
	public void setMaxSpeed_LegalCase() throws IllegalArgumentException{
		mutableShip1.setMaxSpeed(2E5);
		double maxSpeed = mutableShip1.getMaxSpeed();
		assertEquals(2E5, maxSpeed, EPSILON);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setMaxSpeed_NaN() throws IllegalArgumentException{
		mutableShip1.setMaxSpeed(Double.NaN);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setMaxSpeed_Negative() throws IllegalArgumentException{
		mutableShip1.setMaxSpeed(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setMaxSpeed_BiggerThanSPEED_OF_LIGHT() throws IllegalArgumentException{
		mutableShip1.setMaxSpeed(4E5);
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
		assertEquals(Math.sqrt(20000)-immutableShip1.getRadius()-immutableShip2.getRadius(), distance, EPSILON);
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
	/*
	@Test(expected = IllegalStateException.class)
	public void GetTimeToCollision_OverlappingShips() throws IllegalStateException, NullPointerException{
		immutableShip2.getTimeToCollision(immutableShip3);
	}
	*/
	
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
	
	
	
}
