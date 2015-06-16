package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMGenericRef;

public class ALoadXInst extends BasicInstruction {

	private int index;

	public ALoadXInst(int index) {
		this.index = index;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMGenericRef operand = (BVMGenericRef) context.getLocal(index);
		context.getOperandStack().push(operand);
		System.out
				.println("Spoustim instrukci aload. Na vrcholu zasobniku je: "
						+ context.getOperandStack().peek());
	}

}
