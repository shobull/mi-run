package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMLong;

public class LReturnInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		if (!context.getMethod().returnLong()) {
			throw new RuntimeException("Metoda "
					+ context.getMethod().getName() + " nevraci Long!");
		}

		BVMLong returnValue = (BVMLong) context.getOperandStack().pop();
		while (!context.getOperandStack().isEmpty()) {
			context.getOperandStack().pop();
		}
		executionStack.pop();
		executionStack.peek().getOperandStack().push(returnValue);

		System.out.println("Spoustim instrukci lreturn. Vracim hodnotu: "
				+ returnValue + " a jdu zpet do metody "
				+ executionStack.peek().getMethod().getName() + ".");
	}

}
