package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMLong;

public class LAddInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMLong value1;
		BVMLong value2;

		if (context.getOperandStack().peek() instanceof BVMLong) {
			value1 = (BVMLong) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("LADD err - 1. operand neni BVMLong.");
		}

		if (context.getOperandStack().peek() instanceof BVMLong) {
			value2 = (BVMLong) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("LADD err - 2. operand neni BVMLong.");
		}

		BVMLong result = value1.add(value2);
		context.getOperandStack().push(result);

		System.out.println("Spoustim instrukci ladd. Na vrcholu zasobniku je: "
				+ context.getOperandStack().peek());
	}

}
