package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class IStoreXInst extends BasicInstruction {

	private int n;

	public IStoreXInst(int x) {
		this.n = x;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMInteger operand = (BVMInteger) context.getOperandStack().pop();
		context.setLocal(n, operand);
		System.out.println("Spoustim instrukci istore_" + n
				+ ". Do lok. promennych [" + n + "] = " + operand);
	}
}
