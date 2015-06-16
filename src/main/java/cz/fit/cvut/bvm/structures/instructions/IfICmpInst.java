package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class IfICmpInst extends BasicInstruction {

	private int val;
	private Comparator comp;

	public IfICmpInst(int val, Comparator comp) {
		this.val = val;
		this.comp = comp;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMInteger operand1;
		BVMInteger operand2;

		if (context.getOperandStack().peek() instanceof BVMInteger) {
			operand2 = (BVMInteger) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("err - 2. operand neni BVMInteger.");
		}

		if (context.getOperandStack().peek() instanceof BVMInteger) {
			operand1 = (BVMInteger) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("err - 1. operand neni BVMInteger.");
		}

		if (doJump(operand1.getVal(), operand2.getVal())) {
			context.setPc(context.getPc() + val - 3);
		}

		System.out
				.println("Spoustim instrukci ificmp.");
	}

	public enum Comparator {
		EQUAL, NOT_EQUAL, LESS, LESS_EQUAL, GREATER, GREATER_EQUAL;
	}

	private boolean doJump(int int1, int int2) {
		switch (comp) {
		case EQUAL:
			return int1 == int2;
		case NOT_EQUAL:
			return int1 != int2;
		case LESS:
			return int1 < int2;
		case LESS_EQUAL:
			return int1 <= int2;
		case GREATER:
			return int1 > int2;
		case GREATER_EQUAL:
			return int1 >= int2;
		default:
			throw new RuntimeException("unreachable code");
		}
	}

}
