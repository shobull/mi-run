package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class SIPushInst extends BasicInstruction {

	private int val;

	public SIPushInst(int val) {
		this.val = val;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		context.getOperandStack().push(new BVMInteger(val));
		System.out.println("Spoustim sipush. Na vrcholu zasobniku je: "
				+ context.getOperandStack().peek());
	}

}
