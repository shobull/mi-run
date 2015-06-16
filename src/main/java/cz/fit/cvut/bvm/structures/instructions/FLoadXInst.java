package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMFloat;

public class FLoadXInst extends BasicInstruction {

	private int n;

	public FLoadXInst(int n) {
		this.n = n;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMFloat operand = (BVMFloat) context.getLocal(n);
		context.getOperandStack().push(operand);
		System.out.println("Spoustim instrukci fload_" + n
				+ ". Na vrcholu zasobniku je: "
				+ context.getOperandStack().peek());
	}
}
