package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

/**
 * Instruction iconst_X, X = {-1, 0, 1, 2, 3, 4 5}
 */
public class IConstXInst extends BasicInstruction {

	private int val;

	public IConstXInst(int x) {
		this.val = x;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMInteger operand = new BVMInteger(val);
		context.getOperandStack().push(operand);
		System.out.println("Spoustim instrukci iconst_" + val
				+ ". Na vrcholu zasovniku je: "
				+ context.getOperandStack().peek());
	}

}
