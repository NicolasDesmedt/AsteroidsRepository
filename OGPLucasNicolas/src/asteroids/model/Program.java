package asteroids.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.Type;
import asteroids.model.programs.functions.*;
import asteroids.model.programs.statements.*;


public class Program {
	
	public Program(List<Function> functions, Statement body) {
		this.functions.addAll(functions);
		for (Function function : functions)
			function.setProgram(this);
		this.body = body;
		this.body.setProgram(this);
		addToToDoList(body);
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
	
	private Map<String, Statement> functionsMap = new HashMap<>();
	
	public void addToFunctionsMap(String functionName, Statement body) {
		functionsMap.put(functionName, body);
	}
	
	public Map<String, Statement> getFunctionsMap() {
		return new HashMap<String, Statement>(functionsMap);
	}
	
	public Map<String, Expression<?>> getFunctionsReturn() {
		return new HashMap<String, Expression<?>>(functionsReturn);
	}
	
	public void addToFunctionsReturn(String functionName, Expression<?> expression) {
		functionsReturn.put(functionName, expression);
	}
	
	private Map<String, Expression<?>> functionsReturn = new HashMap<>();
	
	private final Statement body;
	
	public void addToGlobals(String variableName, Expression<?> expression) {
		this.globals.put(variableName, expression);
	}
	
	public Map<String, Expression<?>> getGlobals() {
		return this.globals;
	}
	
	private Map<String, Expression<?>> globals = new HashMap<String, Expression<?>>();
	
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
		return new ArrayList<Statement>(toDoList);
	}
	
	public void removeFromToDoList(int index) {
		toDoList.remove(index);
	}
	
	public void addAllToToDoListInSecond(List<Statement> statementsList) {
		toDoList.addAll(1,statementsList);
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
		return new ArrayList<Object>(valuesPrinted);
	}
	
	public <T> void addToValuesPrinted(T value) {
		valuesPrinted.add(value);
	}
	
	private List<Object> valuesPrinted = new ArrayList<>();
	
	public Map<String, Expression<?>> getVariables() {
		return new HashMap<String, Expression<?>>(variables);
	}
	
	public void removeFromVariables(String variableName) {
		variables.remove(variableName);
	}
	
	public void addToVariables(String variableName, Expression<?> expression) {
		variables.put(variableName, expression);
	}
	
	private Map<String, Expression<?>> variables = new HashMap<>();
	
	private boolean executingStatementsInFunction = false;
	
	public boolean executingStatementsInFunction() {
		return this.executingStatementsInFunction;
	}
	
	public void setExecutingStatementsInFunction(Boolean bool) {
		this.executingStatementsInFunction = bool;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public List<Object> execute(double dt) {
		try{
			for (Function function : functions) {
				function.evaluateFunction();
			}
		}catch(IllegalArgumentException|AssertionError e){
			throw new IllegalArgumentException(e.getMessage());
		}
		//System.out.println("TodoLIST:" + this.getToDoList() +"stop");
		if (!this.getToDoList().isEmpty()) {
			try {
				//System.out.println("TIJD VOOR " + this.getTimeLeft());
				this.getStatementToDo(this.getToDoList()).executeStatement(this.getVariables());
				//System.out.println("TIJD NA " + this.getTimeLeft());
				if (!isPutOnHold()) {
					this.removeFromToDoList(0);
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

	private String currentFunction;

	public List<Object> executeProgram(double dt) {
		putOnHold(false);
		this.addTime(dt);
		return this.execute(this.getTimeLeft());
	}

	public void setCurrentFunction(String functionName) {
		this.currentFunction = functionName;
	}
	
	public String getCurrentFunction() {
		return this.currentFunction;
	}

	private Map<String, Map<String, Expression<?>>> localVariables = new HashMap<String, Map<String, Expression<?>>>();
	
	public Map<String, Map<String, Expression<?>>> getLocalVariables() {
		return this.localVariables;
	}
	
	public void addToLocals(String functionName, String varName, Expression<?> value) {
		Map<String, Expression<?>> variable = new HashMap<>();
		variable.put(varName, value);
		this.localVariables.put(functionName, variable);
	}
	
	public Expression<?> getLocalVarExpr(String functionName, String varName) {
		return this.getLocalVariables().get(functionName).get(varName);
	}
}
