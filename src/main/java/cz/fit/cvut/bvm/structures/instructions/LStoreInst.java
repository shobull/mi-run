package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMLong;

public class LStoreInst extends BasicInstruction {

	private int index;

	public LStoreInst(int index) {
		this.index = index;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMLong operand = (BVMLong) context.getOperandStack().pop();
		context.setLocal(index, operand);
		System.out.println("Spoustim instrukci lstore. Do lok. promennych ["
				+ index + "] = " + operand);
	}

}
