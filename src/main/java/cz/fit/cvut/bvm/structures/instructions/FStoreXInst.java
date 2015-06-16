package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMFloat;

public class FStoreXInst extends BasicInstruction {

	private int n;

	public FStoreXInst(int x) {
		this.n = x;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMFloat operand = (BVMFloat) context.getOperandStack().pop();
		context.setLocal(n, operand);
		System.out.println("Spoustim instrukci fstore_" + n
				+ ". Do lok. promennych [" + n + "] = " + operand);
	}

}
