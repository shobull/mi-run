package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMGenericRef;
 
public class IfACmpInst extends BasicInstruction {

	private int offset;
	private Comparator comp;

	public IfACmpInst(int offset, Comparator comp) {
		this.offset = offset;
		this.comp = comp;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMGenericRef operand1;
		BVMGenericRef operand2;

		if (context.getOperandStack().peek() instanceof BVMGenericRef) {
			operand2 = (BVMGenericRef) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("err - 2. operand neni BVMGenericRef.");
		}

		if (context.getOperandStack().peek() instanceof BVMGenericRef) {
			operand1 = (BVMGenericRef) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("err - 1. operand neni BVMGenericRef.");
		}

		if (doJump(operand1.getHeapRef(), operand2.getHeapRef())) {
			context.setPc(context.getPc() + offset - 3);
		}
		System.out.println("Spoustim instrukci ifacmpXX.");
	}

	public enum Comparator {
		EQUAL, NOT_EQUAL;
	}

	private boolean doJump(Integer int1, Integer int2) {
		switch (comp) {
		case EQUAL:
			return int1 == int2;
		case NOT_EQUAL:
			return int1 != int2;
		default:
			throw new RuntimeException("unreachable code");
		}
	}

}
