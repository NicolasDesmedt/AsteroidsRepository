package asteroids.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.Null;
import asteroids.model.programs.expressions.Type;
import asteroids.model.programs.functions.*;
import asteroids.model.programs.statements.*;
import asteroids.util.ModelException;


public class Program {
	
	public Program(List<Function> functions, Statement body) {
		this.functions.addAll(functions);
		this.body = body;
		this.body.setProgram(this);
		addToToDoList(body);
		getVariables().put("self", new Null(null));
//		if (body instanceof Sequence) {
//			List<Statement> statementsList = ((Sequence)body).getStatementList();
//			for (Statement statement : statementsList) {
//				addToToDoList(statement);
//			}
//		}
//		else {
//			addToToDoList(body);
//		}
	}
	
	public Statement getProgramBody() {
		return body;
	}

	private final List<Function> functions = new ArrayList<Function>();
	
	private final Statement body;
	
	private final Map<String, Type> globals = new HashMap<String, Type>();
	
	public double getTimeLeft() {
		//return round(timeLeft,5);
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
	
	public void addToToDoListInSecond(Statement statement) {
		toDoList.add(1, statement);
	}
	
	public void addToToDoListAtIndex(int index, Statement statement) {
		toDoList.add(index, statement);
	}
	
	public Statement getStatementToDo(List<Statement> toDoList) {
		return toDoList.get(0);
	}
	
	private List<Statement> toDoList = new ArrayList<Statement>();
	
	public Statement getStatementAt(int index, List<Statement> toDoList) {
		return toDoList.get(index);
	}
	
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
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public List<Object> execute(double dt) {
		for (Function function : functions) {
			function.evaluateFunction();
		}
		//System.out.println("TodoLIST:" + this.getToDoList() +"stop");
		if (!this.getToDoList().isEmpty()) {
			try {
				//System.out.println("TIJD VOOR " + this.getTimeLeft());
				this.getStatementToDo(this.getToDoList()).executeStatement(this.getVariables());
				//System.out.println("TIJD NA " + this.getTimeLeft());
				if (!isPutOnHold()) {
					this.getToDoList().remove(0);
					execute(this.getTimeLeft());
				}
			}catch(IllegalArgumentException|AssertionError e){
				throw new IllegalArgumentException("Illegal statement execution");
			}
		}
//		for (Statement statement : this.getToDoList()) {
//			System.out.println(statement);
//			try {
//				statement.executeStatement(this.getVariables());
//			}catch(IllegalArgumentException|AssertionError e){
//				throw new IllegalArgumentException("Illegal statement execution");
//			}
//			if (!isPutOnHold()) {
//				this.getToDoList().remove(statement);
//				System.out.println("TodoLISTv2:" + this.getToDoList() +"stop");
//		}
		List<Object> toReturn = null;
		if (this.getToDoList().isEmpty()) {
			toReturn = this.getValuesPrinted();
		}
		return toReturn;
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

	public List<Object> executeProgram(double dt) {
		putOnHold(false);
		this.addTime(dt);
		return this.execute(this.getTimeLeft());
	}
}
