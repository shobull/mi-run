package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMValue;

public class DupInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMValue value = context.getOperandStack().peek();
		BVMValue duplicateValue = value.copy();
		context.getOperandStack().push(duplicateValue);

		System.out.println("Spoustim instrukci dup. Na vrcholu zasobniku je: "
				+ context.getOperandStack().peek());
	}

}
