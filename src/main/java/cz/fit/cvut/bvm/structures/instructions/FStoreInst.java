package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMFloat;

public class FStoreInst extends BasicInstruction {

	private short index;

	public FStoreInst(short index) {
		this.index = index;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMFloat operand = (BVMFloat) context.getOperandStack().pop();
		context.setLocal(index, operand);
		System.out.println("Spoustim instrukci fstore. Do lok. promennych ["
				+ index + "] = " + operand);
	}
}
