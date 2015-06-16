package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class ISubInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMInteger operand1;
		BVMInteger operand2;

		if (context.getOperandStack().peek() instanceof BVMInteger) {
			operand1 = (BVMInteger) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("ISUB err - 1. operand neni BVMInteger.");
		}

		if (context.getOperandStack().peek() instanceof BVMInteger) {
			operand2 = (BVMInteger) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("ISUB err - 2. operand neni BVMInteger.");
		}

		BVMInteger result = operand2.sub(operand1);
		context.getOperandStack().push(result);

		System.out.println("Spoustim instrukci isub. Na vrcholu zasobniku je: "
				+ context.getOperandStack().peek());

	}

}
