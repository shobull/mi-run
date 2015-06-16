package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMFloat;

public class FAddInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMFloat operand1;
		BVMFloat operand2;

		if (context.getOperandStack().peek() instanceof BVMFloat) {
			operand1 = (BVMFloat) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("FADD err - 1. operand neni BVMFloat.");
		}

		if (context.getOperandStack().peek() instanceof BVMFloat) {
			operand2 = (BVMFloat) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("FADD err - 2. operand neni BVMFloat.");
		}

		BVMFloat result = operand1.add(operand2);
		context.getOperandStack().push(result);

		System.out.println("Spoustim instrukci fadd. Na vrcholu zasovniku je: "
				+ context.getOperandStack().peek());

	}

}
