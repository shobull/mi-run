package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMLong;

public class LConstXInst extends BasicInstruction {

	private long val;

	public LConstXInst(long val) {
		this.val = val;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMLong operand = new BVMLong(val);
		context.getOperandStack().push(operand);
		System.out.println("Spoustim instrukci lconst_" + val
				+ ". Na vrcholu zasovniku je: "
				+ context.getOperandStack().peek());
	}

}
