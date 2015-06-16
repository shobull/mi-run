package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class IfInst extends BasicInstruction {

	private int val;
	private Comparator comp;

	public IfInst(int val, Comparator comp) {
		this.val = val;
		this.comp = comp;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMInteger value;

		if (context.getOperandStack().peek() instanceof BVMInteger) {
			value = (BVMInteger) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("err - operand neni BVMInteger.");
		}

		if (doJump(value.getVal())) {
			context.setPc(context.getPc() + val - 3);
		}

		System.out.println("Spoustim instrukci if.");

	}

	public enum Comparator {
		EQUAL, NOT_EQUAL, LESS, LESS_EQUAL, GREATER, GREATER_EQUAL;
	}

	private boolean doJump(int int1) {
		switch (comp) {
		case EQUAL:
			return int1 == 0;
		case NOT_EQUAL:
			return int1 != 0;
		case LESS:
			return int1 < 0;
		case LESS_EQUAL:
			return int1 <= 0;
		case GREATER:
			return int1 > 0;
		case GREATER_EQUAL:
			return int1 >= 0;
		default:
			throw new RuntimeException("unreachable code");
		}
	}
}
