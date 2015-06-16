package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class IAddInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMInteger operand1;
		BVMInteger operand2;

		if (context.getOperandStack().peek() instanceof BVMInteger) {
			operand1 = (BVMInteger) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("IADD err - 1. operand neni BVMInteger.");
		}

		if (context.getOperandStack().peek() instanceof BVMInteger) {
			operand2 = (BVMInteger) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("IADD err - 2. operand neni BVMInteger.");
		}

		BVMInteger result = operand1.add(operand2);
		context.getOperandStack().push(result);

		System.out.println("Spoustim instrukci iadd. Na vrcholu zasovniku je: "
				+ context.getOperandStack().peek());

	}

}
