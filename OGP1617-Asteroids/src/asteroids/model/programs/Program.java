package asteroids.model.programs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asteroids.model.Ship;
import asteroids.model.programs.expressions.Type;
import asteroids.model.programs.functions.*;
import asteroids.model.programs.statements.*;


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
	
	public double getTimeLeft() {
		return timeLeft;
	}
	
	public void setTimeLeft(double timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	private double timeLeft = 0.0;
	
	public void addTime(double timeToAdd) {
		timeLeft += timeToAdd;
	}
	
	public List<Statement> getToDoList() {
		return this.toDoList;
	}
	
	public void addToToDoList(Statement statement) {
		toDoList.add(statement);
	}
	
	public void addToToDoListInFront(Statement statement) {
		toDoList.add(0, statement);
	}
	
	private List<Statement> toDoList = new ArrayList<Statement>();
	
	private List<Object> valuesPrdoubleed = new ArrayList<>();
	
	public List<Object> getValuesPrinted() {
		return valuesPrdoubleed;
	}
	
	public List<Object> execute(double dt) {
		addTime(dt);
		putOnHold(false);
		for (Function function : functions) {
			function.evaluateFunction();
		}
		for (Statement statement : this.getToDoList()) {
			statement.executeStatement();
			if (!isPutOnHold()) {
				this.getToDoList().remove(statement);
			}
		}
		return getValuesPrinted();
	}

	public Ship getShip() {
		return this.ship;
	}
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	private Ship ship;

	public void substractTime(double timeToSubstract) {
		this.addTime(-timeToSubstract);
	}
	
	public boolean isPutOnHold() {
		return putOnHold;
	}

	public void putOnHold(boolean bool) {
		this.putOnHold = bool;
	}
	
	private boolean putOnHold;
}
