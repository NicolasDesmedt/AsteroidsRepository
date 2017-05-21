package asteroids.model.programs.expressions;

import java.util.Map;

import asteroids.model.Entity;
import asteroids.part3.programs.SourceLocation;

public class EntityExpression extends Literals<Entity> {

	public EntityExpression(Entity entity, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	private final Entity entity;
	
	@Override
	public boolean isMutable() {
		return false;
	}
		
	@Override
	public String toString() {
		return getEntity().toString();
	}
	
	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.ENTITY;
	}

	@Override
	public Entity getValue(Map<String, Expression<?>> variables) {
		return getEntity();
	}
	

}
