package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMLong;

public class LLoadInst extends BasicInstruction {

	private int n;

	public LLoadInst(int n) {
		this.n = n;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMLong operand = (BVMLong) context.getLocal(n);
		context.getOperandStack().push(operand);
		System.out
				.println("Spoustim instrukci lload. Na vrcholu zasobniku je: "
						+ context.getOperandStack().peek());
	}

}
