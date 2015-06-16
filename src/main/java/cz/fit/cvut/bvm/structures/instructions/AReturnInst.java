package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMGenericRef;

public class AReturnInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {

		if (!context.getMethod().returnReference()
				&& !context.getMethod().returnArray()) {
			throw new RuntimeException("Metoda "
					+ context.getMethod().getName() + " nevraci reference!");
		}

		BVMGenericRef returnValue = (BVMGenericRef) context.getOperandStack().pop();
		while (!context.getOperandStack().isEmpty()) {
			context.getOperandStack().pop();
		}
		executionStack.pop();
		executionStack.peek().getOperandStack().push(returnValue);

		System.out.println("Spoustim instrukci areturn. Vracim hodnotu: "
				+ returnValue + " a jdu zpet do metody "
				+ executionStack.peek().getMethod().getName() + ".");
	}
}
