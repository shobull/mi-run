package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;

/**
 * push byte
 * 
 * @author Adam
 *
 */
public class NopInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		
		System.out.println("Spoustim instrukci nop ");
	}

}
