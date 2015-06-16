package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMLong;

public class LLoadXInst extends BasicInstruction {

	private int index;

	public LLoadXInst(int index) {
		this.index = index;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMLong operand = (BVMLong) context.getLocal(index);
		context.getOperandStack().push(operand);
		System.out
				.println("Spoustim instrukci lload. Na vrcholu zasobniku je: "
						+ context.getOperandStack().peek());

	}

}
