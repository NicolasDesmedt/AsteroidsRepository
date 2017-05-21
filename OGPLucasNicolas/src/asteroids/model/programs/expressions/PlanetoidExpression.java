package asteroids.model.programs.expressions;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import asteroids.model.Entity;
import asteroids.model.Planetoid;
import asteroids.part3.programs.SourceLocation;

public class PlanetoidExpression extends ReadVariable<Entity>{
	
	public PlanetoidExpression(SourceLocation sourceLocation) {
		super("planetoid", sourceLocation);
	}
	
	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public String toString() {
		return "planetoid";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.ENTITY;
	}
	
	@Override
	public Entity getValue(Map<String, Expression<?>> variables) {
		Set<Planetoid> allPlanetoids = this.getShip().getWorld().getAllPlanetoids();
		Planetoid closestPlanetoid = null;
		try{ closestPlanetoid = allPlanetoids.stream()
	        .min(Comparator.comparingDouble(x -> 
	        	x.getDistanceBetween(this.getShip()))).get();
		}catch(NoSuchElementException e){
		}
		return closestPlanetoid;
	}

}
