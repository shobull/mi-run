package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class ILoadXInst extends BasicInstruction {

	private int n;

	public ILoadXInst(int n) {
		this.n = n;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMInteger operand = (BVMInteger) context.getLocal(n);
		context.getOperandStack().push(operand);
		System.out.println("Spoustim instrukci iload_" + n
				+ ". Na vrcholu zasobniku je: "
				+ context.getOperandStack().peek());
	}

}
