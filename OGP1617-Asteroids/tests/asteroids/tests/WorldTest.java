package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.*;

import asteroids.model.*;

public class WorldTest {
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
	private static Ship immutableShip1, immutableMinimalShip, immutableOverlappingShip;
	
	private static Bullet mutableMinimalBullet, mutableBullet1;
	
	private static World world100x100, world1000x1000;
	
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
		world100x100 = new World (100,100);
		world1000x1000 = new World (1000,1000);
		
		immutableShip1 = new Ship(500,500,0,0,10,(Math.PI/2));
		immutableOverlappingShip = new Ship(500,495,0,0,10,0);
		immutableMinimalShip = new Ship(0,0,0,0,Ship.minRadiusShip,0);
		
	}
	
	@Test
	public void createLegalWorld(){
		World world=new World(20, 10);
		System.out.println("test" + world.getWorldSize());
		double[] size = world.getWorldSize();
		assertEquals(size[0], 20, EPSILON);
		assertEquals(size[1], 10, EPSILON);
	}
	
	@Test 
	public void withinBoundaries_LegalCase(){
		mutableShip1.setPosition(100, 100);
		assertTrue(world1000x1000.withinBoundaries(mutableShip1));
	}
	
	@Test 
	public void withinBoundaries_IllegalCase(){
		mutableShip1.setPosition(9.89, 10);
		assertFalse(world1000x1000.withinBoundaries(mutableShip1));
	}
	
	@Test 
	public void addEntity_LegalCase(){
		world1000x1000.addEntity(immutableShip1);
		assertTrue(world1000x1000.getAllEntities().contains(immutableShip1));
	}
	
/*
	@Test (expected = IllegalArgumentException.class)
	public void addOverlapping_Entities() {
		world1000x1000.addEntity(immutableOverlappingShip);
		assertFalse(world1000x1000.getAllEntities().contains(immutableOverlappingShip));
	}
	
	@Test 
	public void addEntity_LegalCase(){
		world1000x1000.addEntity(immutableShip1);
		assertTrue(world1000x1000.getAllEntities().contains(immutableShip1));
	}
	*/
}
