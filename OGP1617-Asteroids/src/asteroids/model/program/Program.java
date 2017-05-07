package asteroids.model.program;

import java.util.ArrayList;
import java.util.List;

import asteroids.model.Ship;
import asteroids.model.program.functions.*;
import asteroids.model.program.statements.*;

public class Program {
	
	public Program(List<Function> functions, Statement main) {
		this.functions.addAll(functions);
		this.main = main;
	}
	
	public Statement getMainStatement() {
		return main;
	}

	private final List<Function> functions = new ArrayList<Function>();
	
	private final Statement main;
	
	public void execute(Ship ship) {
		
	}
}
