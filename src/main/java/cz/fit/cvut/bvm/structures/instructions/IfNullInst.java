package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMGenericRef;

public class IfNullInst extends BasicInstruction {

	private int offset;

	public IfNullInst(int offset) {
		this.offset = offset;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMGenericRef operand1;

		if (context.getOperandStack().peek() instanceof BVMGenericRef) {
			operand1 = (BVMGenericRef) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("err - 1. operand neni BVMGenericRef.");
		}

		if (operand1.isNull()) {
			context.setPc(context.getPc() + offset - 3);
		}
		System.out.println("Spoustim instrukci ifnull.");
	}
}
