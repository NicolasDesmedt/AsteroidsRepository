package asteroids.tests;

import static org.junit.Assert.*;
import org.junit.*;

import asteroids.facade.Facade;
import asteroids.model.Ship;
import asteroids.util.ModelException;

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
	 * rescpectively with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing right (0).
	 */
	private static Ship mutableShip1, mutableShip2;
	
	/**
	 * Variables referencing ships with position [0,0], 
	 * velocity[0,0], radius 10 and orientation facing right (0),
	 * rescpectively with position [0,0], 
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
		mutableShip1 = new Ship(0,0,20,20,10,0);
		mutableShip2 = new Ship(10,10,0,0,10,(Math.PI/2));
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
		
	}

	@Test
	public void getPosition_LegalCase() {
		double[] position = immutableShip1.getPosition();
		assertEquals(100, position[0], EPSILON);
		assertEquals(-100, position[1], EPSILON);
	}
	
	@Test
	public void setPosition_LegalCase() throws IllegalArgumentException {
		mutableShip1.setPosition(new double[] {100,200});
		double[] position = mutableShip1.getPosition();
		assertEquals(100, position[0], EPSILON);
		assertEquals(200, position[1], EPSILON);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setPosition_NaN() throws IllegalArgumentException {
		mutableShip1.setPosition(new double[] {Double.NaN,200});
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setPosition_Infinite() throws IllegalArgumentException {
		mutableShip1.setPosition(new double[] {Double.NEGATIVE_INFINITY,200});
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setPosition_InvalidLength() throws IllegalArgumentException {
		mutableShip1.setPosition(new double[] {10,20,30});
	}
	
	@Test
	public void getVelocity_LegalCase() {
		double[] velocity = immutableShip1.getVelocity();
		assertEquals(-20, velocity[0], EPSILON);
		assertEquals(0, velocity[1], EPSILON);
	}
	
	@Test
	public void setVelocity_LegalCase() {
		mutableShip1.setVelocity(new double[] {10,-10});
		double[] velocity = mutableShip1.getVelocity();
		assertEquals(10, velocity[0], EPSILON);
		assertEquals(-10, velocity[1], EPSILON);
	}

	@Test
	public void setVelocity_ExceedingSpeedOfLight() {
		mutableShip1.setVelocity(new double[] {400000,200000});
		double[] velocity = mutableShip1.getVelocity();
		assertEquals(268328.1573, velocity[0], EPSILON);
		assertEquals(134164.0787, velocity[1], EPSILON);
	}

	@Test
	public void setVelocity_XVelocityInfinite() {
		mutableShip1.setVelocity(new double[] {Double.POSITIVE_INFINITY,-10});
		double[] velocity = mutableShip1.getVelocity();
		assertEquals(Ship.MAX_SPEED, velocity[0], EPSILON);
		assertEquals(0, velocity[1], EPSILON);
	}
	
	@Test
	public void setVelocity_XAndYVelocityInfinite() {
		mutableShip1.setVelocity(new double[] {Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY});
		double[] velocity = mutableShip1.getVelocity();
		assertEquals((Ship.MAX_SPEED/Math.sqrt(2)), velocity[0], EPSILON);
		assertEquals((-Ship.MAX_SPEED/Math.sqrt(2)), velocity[1], EPSILON);
	}
	
	@Test
	public void setVelocity_YNaN() {
		mutableShip1.setVelocity(new double[] {10,Double.NaN});
		double[] velocity = mutableShip1.getVelocity();
		assertEquals(0, velocity[0], EPSILON);
		assertEquals(0, velocity[1], EPSILON);
	}
	
	@Test
	public void getRadius_LegalCase(){
		double radius = immutableShip1.getRadius();
		assertEquals(10, radius, EPSILON);
	}
	
	@Test
	public void setRadius_LegalCase() throws IllegalArgumentException {
		mutableShip1.setRadius(50);
		double radius = mutableShip1.getRadius();
		assertEquals(50, radius, EPSILON);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setRadius_UnderMinimumRadius() throws IllegalArgumentException {
		mutableShip1.setRadius((Ship.MIN_RADIUS-1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setRadius_NaN() throws IllegalArgumentException {
		mutableShip1.setRadius(Double.NaN);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setRadius_Infinite() throws IllegalArgumentException {
		mutableShip1.setRadius(Double.POSITIVE_INFINITY);
	}
	
	@Test
	public void getOrientation_LegalCase(){
		double radius = immutableShip2.getOrientation();
		assertEquals((Math.PI/2), radius, EPSILON);
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
	public void Thrust_LegalCase(){
		mutableShip1.thrust(10);
		double[] velocity = mutableShip1.getVelocity();
		assertEquals(30, velocity[0], EPSILON);
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
	public void Turn_LegalCase(){
		mutableShip2.turn(Math.PI);
		double orientation = mutableShip2.getOrientation();
		assertEquals(((Math.PI*3)/2), orientation, EPSILON);
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
	
	@Test
	public void Overlap_LegalCase(){
		assertTrue(immutableShip2.overlap(immutableShip3));
	}
	
	@Test
	public void Overlap_Itself(){
		assertTrue(immutableShip1.overlap(immutableShip1));
	}
	
	@Test
	public void GetTimeToCollision_LegalCase() throws IllegalArgumentException{
		double timeToCollision = immutableShip1.getTimeToCollision(immutableShip3);
		assertEquals(4.5, timeToCollision, EPSILON);
	}
	
	@Test
	public void GetTimeToCollision_NoCollision() throws IllegalArgumentException{
		double timeToCollision = immutableShip1.getTimeToCollision(immutableShip2);
		assertEquals(Double.POSITIVE_INFINITY, timeToCollision, EPSILON);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void GetTimeToCollision_OverlappingShips() throws IllegalArgumentException{
		immutableShip2.getTimeToCollision(immutableShip3);
	}
	
	@Test
	public void getCollisionPosition_LegalCase() throws IllegalArgumentException{
		double[] CollisionPosition = immutableShip1.getCollisionPosition(immutableShip3);
		assertEquals(10, CollisionPosition[0], EPSILON);
		assertEquals(-70, CollisionPosition[1], EPSILON);
	}

	@Test
	public void getCollisionPosition_NoCollision() throws IllegalArgumentException{
		double[] CollisionPosition = immutableShip1.getCollisionPosition(immutableShip2);
		assertEquals(null, CollisionPosition);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getCollisionPosition_OverlappingShips() throws IllegalArgumentException{
		immutableShip2.getCollisionPosition(immutableShip3);
	}
	
}
