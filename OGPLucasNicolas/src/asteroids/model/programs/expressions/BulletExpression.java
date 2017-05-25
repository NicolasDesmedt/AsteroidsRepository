package asteroids.model.programs.expressions;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import asteroids.model.Bullet;
import asteroids.model.Entity;
import asteroids.part3.programs.SourceLocation;

public class BulletExpression extends ReadVariable<Entity>{

	public BulletExpression(SourceLocation sourceLocation) {
		super("bullet", sourceLocation);
	}
	
	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public String toString() {
		return "bullet";
	}

	@Override
	public Type getType(Map<String, Expression<?>> variables) {
		return Type.ENTITY;
	}
	
	@Override
	public Entity getValue(Map<String, Expression<?>> variables) {
		Set<Entity> allEntities = this.getShip().getWorld().getAllEntities();
		allEntities.toArray();
		List<Entity> allBullets = allEntities.stream()                
                .filter(x -> x instanceof Bullet)     
                .collect(Collectors.toList());
		
		List<Entity> filteredBullets = allBullets.stream()
				.filter(x -> ((Bullet)x).getSource() == this.getShip())
				.collect(Collectors.toList());
		
		if (filteredBullets.size() == 0)
			return null;
		
		int randomNum = ThreadLocalRandom.current().nextInt(0, filteredBullets.size());

		return filteredBullets.get(randomNum);
	}

}
