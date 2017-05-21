package asteroids.model.programs.expressions;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import asteroids.model.Entity;
import asteroids.model.Asteroid;
import asteroids.part3.programs.SourceLocation;

public class AsteroidExpression extends ReadVariable<Entity>{
	
	public AsteroidExpression(SourceLocation sourceLocation) {
		super("asteroid", sourceLocation);
	}
	
	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public String toString() {
		return "asteroid";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.ENTITY;
	}
	
	@Override
	public Entity getValue(Map<String, Expression<?>> variables) {
		Set<Asteroid> allAsteroids = this.getShip().getWorld().getAllAsteroids();
		Asteroid closestAsteroid = null;
		try{ closestAsteroid = allAsteroids.stream()
	        .min(Comparator.comparingDouble(x -> 
	        	x.getDistanceBetween(this.getShip()))).get();
		}catch(NoSuchElementException e){
		}
		return closestAsteroid;
	}
}
