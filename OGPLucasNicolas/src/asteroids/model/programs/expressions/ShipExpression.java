package asteroids.model.programs.expressions;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import asteroids.model.Entity;
import asteroids.model.Ship;
import asteroids.part3.programs.SourceLocation;

public class ShipExpression extends ReadVariable<Entity> {

	public ShipExpression(SourceLocation sourceLocation) {
		super("ship", sourceLocation);
	}
	
	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public String toString() {
		return "ship";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.ENTITY;
	}
	
	@Override
	public Entity getValue(Map<String, Expression<?>> variables) {
		Set<Ship> allShips = this.getShip().getWorld().getAllShips();
		Ship closestShip = null;
		try{ closestShip = allShips.stream()
	        .filter(x -> x != this.getShip())
	        .min(Comparator.comparingDouble(x -> 
	        	x.getDistanceBetween(this.getShip()))).get();
		}catch(NoSuchElementException e){
		}
		return closestShip;
	}
	
}
