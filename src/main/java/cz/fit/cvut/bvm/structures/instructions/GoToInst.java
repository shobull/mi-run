package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;

public class GoToInst extends BasicInstruction {

	private int val;

	public GoToInst(int val) {
		this.val = val;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {

		context.setPc(context.getPc() + val - 3);

		System.out.println("Spoustim instrukci goto. Jdu na PC: "
				+ context.getPc());
	}
}
