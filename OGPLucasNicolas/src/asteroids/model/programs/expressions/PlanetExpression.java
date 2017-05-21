package asteroids.model.programs.expressions;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import asteroids.model.Entity;
import asteroids.model.MinorPlanet;
import asteroids.part3.programs.SourceLocation;

public class PlanetExpression extends ReadVariable<Entity>{
	
	public PlanetExpression(SourceLocation sourceLocation) {
		super("planet", sourceLocation);
	}
	
	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public String toString() {
		return "planet";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.ENTITY;
	}
	
	@Override
	public Entity getValue(Map<String, Expression<?>> variables) {
		Set<MinorPlanet> allMinorPlanets = this.getShip().getWorld().getAllMinorPlanets();
		MinorPlanet closestMinorPlanet = null;
		try{ closestMinorPlanet = allMinorPlanets.stream()
	        .min(Comparator.comparingDouble(x -> 
	        	x.getDistanceBetween(this.getShip()))).get();
		}catch(NoSuchElementException e){
		}
		return closestMinorPlanet;
	}

}
