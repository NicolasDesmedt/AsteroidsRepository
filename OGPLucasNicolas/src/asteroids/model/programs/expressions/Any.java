package asteroids.model.programs.expressions;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import asteroids.model.Entity;
import asteroids.part3.programs.SourceLocation;

public class Any extends ReadVariable<Entity>{

	public Any(SourceLocation sourceLocation) {
		super("any", sourceLocation);
	}
	
	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public String toString() {
		return "any";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.ENTITY;
	}
	
	public Entity getRandomEntity(Set<Entity> set) {
		int size = set.size();
		int item = new Random().nextInt(size); 
		int i = 0;
		Entity entityReturned = null;
		if (size == 0) {
			return entityReturned;
		}
		for(Entity entity : set)
		{
		    if (i == item)
		        entityReturned = entity;
		    i++;
		}
		return entityReturned;
	}
	
	@Override
	public Entity getValue(Map<String, Expression<?>> variables) {
		return (Entity)getRandomEntity(this.getShip().getWorld().getAllEntities());
	}
}
