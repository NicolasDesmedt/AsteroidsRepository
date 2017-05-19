package asteroids.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.Type;
import asteroids.model.programs.functions.*;
import asteroids.model.programs.statements.*;
import asteroids.util.ModelException;


public class Program {
	
	public Program(List<Function> functions, Statement body) {
		this.functions.addAll(functions);
		this.body = body;
		//addToToDoList(body);
		if (body instanceof Sequence) {
			List<Statement> statementsList = ((Sequence)body).getStatementList();
			for (Statement statement : statementsList) {
				addToToDoList(statement);
			}
		}
		else {
			addToToDoList(body);
		}
		body.setProgram(this);
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
	
	public List<Object> getValuesPrinted() {
		return valuesPrinted;
	}
	
	public <T> void addToValuesPrinted(T value) {
		valuesPrinted.add(value);
	}
	
	private List<Object> valuesPrinted = new ArrayList<>();
	
	public Map<String, Expression<?>> getVariables() {
		return variables;
	}
	
	private Map<String, Expression<?>> variables = new HashMap<>();
	
	public List<Object> execute(double dt) {
		addTime(dt);
		putOnHold(false);
		for (Function function : functions) {
			function.evaluateFunction();
		}
		System.out.println("TodoLIST:" + this.getToDoList() +"stop");
		for (Statement statement : this.getToDoList()) {
			System.out.println(statement);
			try {
				statement.executeStatement(this.getVariables());
			}catch(IllegalArgumentException|AssertionError e){
				throw new IllegalArgumentException("Illegal statement execution");
			}
//			if (!isPutOnHold()) {
//				this.getToDoList().remove(statement);
//				System.out.println("TodoLISTv2:" + this.getToDoList() +"stop");
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
