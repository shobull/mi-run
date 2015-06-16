package cz.fit.cvut.bvm.structures.instructions;

import java.util.EmptyStackException;
import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;

public class ReturnInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		while (!context.getOperandStack().isEmpty()) {
			context.getOperandStack().pop();
		}
		executionStack.pop();

		try {
			System.out
					.println("Spoustim instrukci return. Vracim se do metody: "
							+ executionStack.peek().getMethod().getName()
							+ " z tridy "
							+ executionStack.peek().getMethod().getBvmClass()
									.getName());
		} catch (EmptyStackException e) {
			System.out
					.println("Spoustim instrukci return. ExecutionStack je prazdny, takze bych mel koncit");
		}
	}

}
