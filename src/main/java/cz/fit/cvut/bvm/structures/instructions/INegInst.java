package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class INegInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {

		BVMInteger value = (BVMInteger) context.getOperandStack().pop();
		BVMInteger result = value.neg();

		context.getOperandStack().push(result);

		System.out.println("Spoustim instrukci ineg. Na vrcholu zasobniku je: "
				+ context.getOperandStack().peek());
	}
}
