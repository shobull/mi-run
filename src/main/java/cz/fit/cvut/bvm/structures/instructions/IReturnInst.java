package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class IReturnInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		if (!context.getMethod().returnIntCharShortBoolByte()) {
			throw new RuntimeException("Metoda "
					+ context.getMethod().getName()
					+ " nevraci int, char, short, byte, bool!");
		}

		BVMInteger returnValue = (BVMInteger) context.getOperandStack().pop();
		while (!context.getOperandStack().isEmpty()) {
			context.getOperandStack().pop();
		}
		executionStack.pop();
		executionStack.peek().getOperandStack().push(returnValue);

		System.out.println("Spoustim instrukci ireturn. Vracim hodnotu: "
				+ returnValue + " a jdu zpet do metody "
				+ executionStack.peek().getMethod().getName() + ".");
	}

}
