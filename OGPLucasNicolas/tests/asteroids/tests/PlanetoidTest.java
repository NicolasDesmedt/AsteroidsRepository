package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.*;

import asteroids.model.*;

/**
 * A class collecting tests for all the public methods of the planetoid class.
 * 
 * @version 1.0
 * @author Lucas Desard and Nicolas Desmedt
 *
 */

public class PlanetoidTest {
		
	/**
	 * Variable defining the maximum acceptable distance of the result due to rounding issues.
	 */
	private static final double EPSILON = 0.0001;		
	
	/**		 
	* Variable of a ship referencing ship with position [500,700], 
	* velocity[100,0], radius 50 and orientation facing down (3*PI/2).
	*/
	private static Ship mutableShip1;
	
	/**		 
	* Variable of a bullet referencing a bullet with position [500,700], 
	* velocity[0,0] and radius 10.
	*/
	private static Bullet mutableBullet1;
	
	/**		 
	* Variables of planetoids referencing a planetoid with position [250,250], 
	* velocity[0,0], radius 50 and total distance traveled 0,
	* respectively a planetoid with position [500,500], 
	* velocity[0,0], radius 50 and total distance traveled 1000000,
	* respectively a planetoid with position [900,900], 
	* velocity[0,0], radius 14 and total distance traveled 10000000,
	* respectively a planetoid with position [900,900], 
	* velocity[0,0], radius 20 and total distance traveled 0.
	*/	
	private static Planetoid mutablePlanetoid1, mutablePlanetoid2, mutablePlanetoid3, mutablePlanetoid4;
	
	/**		 
	* Variable of a world referencing a world with height and width 1000.
	*/	
	private static World world1000x1000;
		
	/**
	 * Set up a mutable test fixture.
	 * 
	 * @post The variable mutableShip1 references a new ship with position [500,700], 
	 * velocity[100,0], radius 50 and orientation facing down (3*PI/2).
	 * 
	 * @post The variable mutableBullet1 references a new bullet with position with position [500,700], 
	 * velocity[0,0] and radius 10.
	 * 
	 * @post The variable mutablePlanetoid1 references a new planetoid with position [250,250], 
	 * velocity[0,0], radius 50 and total distance traveled 0.
	 * 
	 * @post The variable mutablePlanetoid2 references a new planetoid with position [500,500], 
	 * velocity[0,0], radius 50 and total distance traveled 1000000.
	 * 
	 * @post The variable mutablePlanetoid3 references a new planetoid with position [900,900], 
	 * velocity[0,0], radius 14 and total distance traveled 10000000.
	 * 
	 * @post The variable mutablePlanetoid4 references a new planetoid with position [900,900], 
	 * velocity[0,0], radius 20 and total distance traveled 0.
	 * 
	 * @post The variable world1000x1000 references a new world with height and width 1000.
	 */
		
	@Before
	public void setUpMutableFixture() {
		mutableShip1 = new Ship(500,700,100,0,50,(3*Math.PI/2));
		mutableBullet1 = new Bullet(500,700,0,0,10);
		mutablePlanetoid1 = new Planetoid(250, 250, 500, 0, 50, 0);
		mutablePlanetoid2 = new Planetoid(500, 500, 0, 0, 50, 1000000);
		mutablePlanetoid3 = new Planetoid(900, 900, 0, 0, 14, 10000000);
		mutablePlanetoid4 = new Planetoid(900, 900, 0, 0, 20, 0);
		world1000x1000 = new World (1000,1000);
		world1000x1000.addEntity(mutableShip1);
		world1000x1000.addEntity(mutablePlanetoid1);
		world1000x1000.addEntity(mutablePlanetoid2);
		world1000x1000.addEntity(mutablePlanetoid4);
		mutableShip1.loadBulletOnShip(mutableBullet1);
		}
	
	@Test
    public void createPlanetoid_LegalCase(){
		assertEquals(mutablePlanetoid2.getTraveledDistance(), 1000000, EPSILON);
		assertEquals(mutablePlanetoid2.getInitialRadius(), 50, EPSILON);
		assertEquals(mutablePlanetoid2.getRadius(), 49, EPSILON);
    }
	
	@Test
    public void createPlanetoid_TraveledToMuch(){
		assertTrue(mutablePlanetoid3.isTerminated());
    }
	
	@Test
    public void movePlanetoid_LegalCase(){
		world1000x1000.evolve(200, null);
		assertEquals(mutablePlanetoid1.getTraveledDistance(), 100000, EPSILON);
		assertEquals(mutablePlanetoid1.getInitialRadius(), 50, EPSILON);
		assertEquals(mutablePlanetoid1.getRadius(), 49.9, EPSILON);
    }
	
	@Test
    public void gotHitByBulletPlanetoid_SmallPlanetoid(){
		world1000x1000.evolve(4, null);
		mutableShip1.turn(-Math.PI);
		mutableShip1.fireBullet();
		world1000x1000.evolve(1, null);
		assertEquals(world1000x1000.getAllEntities().size(), 3, EPSILON);
		assertTrue(mutablePlanetoid4.isTerminated());
    }
	
	@Test
    public void gotHitByBulletPlanetoid_BigPlanetoid(){
		mutableShip1.fireBullet();
		world1000x1000.evolve(1, null);
		int numberAsteroids = 0;
		for (Entity entity : world1000x1000.allEntities ){
			if (entity instanceof Asteroid) numberAsteroids++;
		}
		assertEquals(world1000x1000.getAllEntities().size(), 5, EPSILON);
		assertEquals(numberAsteroids, 2);
		assertTrue(mutablePlanetoid2.isTerminated());
    }
	
}
