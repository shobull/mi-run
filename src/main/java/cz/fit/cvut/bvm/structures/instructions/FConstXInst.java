package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMFloat;

/**
 * Instruction iconst_X, X = {0, 1, 2}
 */
public class FConstXInst extends BasicInstruction {

	private float val;

	public FConstXInst(float x) {
		this.val = x;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMFloat operand = new BVMFloat(val);
		context.getOperandStack().push(operand);
		System.out.println("Spoustim instrukci fconst_" + (int) val
				+ ". Na vrcholu zasobniku je: "
				+ context.getOperandStack().peek());
	}
}
