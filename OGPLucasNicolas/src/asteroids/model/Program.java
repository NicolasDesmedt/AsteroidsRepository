package asteroids.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.functions.*;
import asteroids.model.programs.statements.*;


public class Program {
	
	public Program(List<Function> functions, Statement body) {
		addAllFunctions(functions);
		for (Function function : this.getFunctions())
			function.setProgram(this);
		this.body = body;
		this.body.setProgram(this);
		addToToDoList(body);
	}
	
	public Statement getProgramBody() {
		return body;
	}
	
	private final Statement body;
	
	public Ship getShip() {
		return this.ship;
	}
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	private Ship ship;
	
	// EXECUTEMENT OF PROGRAM
	
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
	
	public List<Object> execute(double dt) {
		try{
			for (Function function : functions) {
				function.evaluateFunction();
			}
		}catch(IllegalArgumentException|AssertionError e){
			throw new IllegalArgumentException(e.getMessage());
		}
		if (!this.getToDoList().isEmpty()) {
			try {
				this.getStatementToDo(this.getToDoList()).executeStatement(this.getVariables());
				if (!isPutOnHold()) {
					this.removeFromToDoList(0);
					execute(this.getTimeLeft());
				}
			}catch(IllegalArgumentException|AssertionError e){
				throw new IllegalArgumentException(e.getMessage());
			}
		}
		List<Object> toReturn = null;
		if (this.getToDoList().isEmpty()) {
			toReturn = this.getValuesPrinted();
		}
		return toReturn;
	}
	
	// TO DO LIST
	
	public List<Statement> getToDoList() {
		return new ArrayList<Statement>(toDoList);
	}
	
	public void addToToDoList(Statement statement) {
		toDoList.add(statement);
	}
	
	public void removeFromToDoList(int index) {
		toDoList.remove(index);
	}
	
	public void addToToDoListInSecond(Statement statement) {
		toDoList.add(1, statement);
	}
	
	public void addAllToToDoListInSecond(List<Statement> statementsList) {
		toDoList.addAll(1,statementsList);
	}
	
	public void addToToDoListAtIndex(int index, Statement statement) {
		toDoList.add(index, statement);
	}
	
	public Statement getStatementToDo(List<Statement> toDoList) {
		return toDoList.get(0);
	}
	
	public Statement getStatementAt(int index, List<Statement> toDoList) {
		return toDoList.get(index);
	}
	
	private List<Statement> toDoList = new ArrayList<Statement>();
	
	// VARIABLES AND PRINTED VALUES
	
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
	
	public void addToVariables(String variableName, Expression<?> expression) {
		variables.put(variableName, expression);
	}
	
	public void removeFromVariables(String variableName) {
		variables.remove(variableName);
	}
	
	private Map<String, Expression<?>> variables = new HashMap<>();
	
	// FUNCTIONS //
	
	public List<Function> getFunctions() {
		return new ArrayList<Function>(functions);
	}
	
	public void addAllFunctions(List<Function> functions) {
		this.functions.addAll(functions);
	}

	private final List<Function> functions = new ArrayList<Function>();
	
	public Map<String, Statement> getFunctionsMap() {
		return new HashMap<String, Statement>(functionsMap);
	}
	
	public void addToFunctionsMap(String functionName, Statement body) {
		functionsMap.put(functionName, body);
	}
	
	private Map<String, Statement> functionsMap = new HashMap<>();
	
	public Map<String, Expression<?>> getFunctionsResult() {
		return new HashMap<String, Expression<?>>(functionsResult);
	}
	
	public void addToFunctionsResult(String functionName, Expression<?> expression) {
		functionsResult.put(functionName, expression);
	}
	
	private Map<String, Expression<?>> functionsResult = new HashMap<>();
	
	public Map<String, Expression<?>> getGlobals() {
		return this.globals;
	}
	
	public void addToGlobals(String variableName, Expression<?> expression) {
		this.globals.put(variableName, expression);
	}
	
	private Map<String, Expression<?>> globals = new HashMap<String, Expression<?>>();
	
	public Map<String, Map<String, Expression<?>>> getLocalVariables() {
		return this.localVariables;
	}
	
	public void addToLocals(String functionName, String varName, Expression<?> value) {
		Map<String, Expression<?>> variable = new HashMap<>();
		variable.put(varName, value);
		this.localVariables.put(functionName, variable);
	}
	
	private Map<String, Map<String, Expression<?>>> localVariables = 
			new HashMap<String, Map<String, Expression<?>>>();
	
	public Expression<?> getLocalVarExpr(String functionName, String varName) {
		return this.getLocalVariables().get(functionName).get(varName);
	}
	
	public String getCurrentFunction() {
		return this.currentFunction;
	}
	
	public void setCurrentFunction(String functionName) {
		this.currentFunction = functionName;
	}
	
	private String currentFunction;
	
	private boolean executingStatementsInFunction = false;
	
	public boolean executingStatementsInFunction() {
		return this.executingStatementsInFunction;
	}
	
	public void setExecutingStatementsInFunction(Boolean bool) {
		this.executingStatementsInFunction = bool;
	}

}
