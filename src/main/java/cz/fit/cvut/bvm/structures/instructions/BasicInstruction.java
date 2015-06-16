package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;

public abstract class BasicInstruction {

	public abstract void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack);

}
