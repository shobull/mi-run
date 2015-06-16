package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class IStoreInst extends BasicInstruction {

	private short index;

	public IStoreInst(short index) {
		this.index = index;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMInteger operand = (BVMInteger) context.getOperandStack().pop();
		context.setLocal(index, operand);
		System.out.println("Spoustim instrukci istore. Do lok. promennych ["
				+ index + "] = " + operand);
	}

}
