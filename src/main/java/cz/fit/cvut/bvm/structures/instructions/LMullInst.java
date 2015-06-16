package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMLong;

public class LMullInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMLong operand1;
		BVMLong operand2;

		if (context.getOperandStack().peek() instanceof BVMLong) {
			operand1 = (BVMLong) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("LMUL err - 1. operand neni BVMLong.");
		}

		if (context.getOperandStack().peek() instanceof BVMLong) {
			operand2 = (BVMLong) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("LMUL err - 2. operand neni BVMLong.");
		}

		BVMLong result = operand1.mul(operand2);
		context.getOperandStack().push(result);

		System.out
				.println("Spoustim instrukci lmul. Na vrcholu zasobniku je: "
						+ context.getOperandStack().peek());

	}

}
