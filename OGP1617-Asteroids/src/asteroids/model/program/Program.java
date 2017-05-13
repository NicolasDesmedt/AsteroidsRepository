package asteroids.model.program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asteroids.model.Ship;
import asteroids.model.program.expressions.Type;
import asteroids.model.program.functions.*;
import asteroids.model.program.statements.*;


public class Program {
	
	public Program(List<Function> functions, Statement body) {
		this.functions.addAll(functions);
		this.body = body;
	}
	
	public Statement getProgramBody() {
		return body;
	}

	private final List<Function> functions = new ArrayList<Function>();
	
	private final Statement body;
	
	private final Map<String, Type> globals = new HashMap<String, Type>();
	
	private int timeLeft = 0;
	
	public int getTimeLeft() {
		return timeLeft;
	}
	
	private List<? extends Object> valuesPrinted = new ArrayList<>();
	
	public List<? extends Object> getValuesPrinted() {
		return valuesPrinted;
	}
	
	public void addTime(double timeToAdd) {
		timeLeft += timeToAdd;
	}
	
	public List<? extends Object> execute(double dt) {
		addTime(dt);
		for (Function function : functions) {
			function.evaluateFunction();
		}
		body.executeStatement();
		return getValuesPrinted();
	}
}
