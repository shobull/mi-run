package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

/**
 * push byte
 * 
 * @author Adam
 *
 */
public class BIPush extends BasicInstruction {

	private short val;

	public BIPush(short val) {
		this.val = val;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {

		BVMInteger operand = new BVMInteger(val);
		context.getOperandStack().push(operand);
		System.out.println("Spoustim instrukci bipush " + val
				+ ". Na vrcholu zasovniku je: "
				+ context.getOperandStack().peek());
	}

}
