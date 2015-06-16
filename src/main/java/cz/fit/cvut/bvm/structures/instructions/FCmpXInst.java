package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMFloat;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class FCmpXInst extends BasicInstruction {

	private FCMP naNBehavior;

	public FCmpXInst(FCMP naNBehavior) {
		this.naNBehavior = naNBehavior;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMFloat operand1;
		BVMFloat operand2;

		if (context.getOperandStack().peek() instanceof BVMFloat) {
			operand2 = (BVMFloat) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("err - 2. operand neni BVMFloat.");
		}

		if (context.getOperandStack().peek() instanceof BVMFloat) {
			operand1 = (BVMFloat) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("err - 1. operand neni BVMFloat.");
		}

		int resultVal = compare(operand1.getVal(), operand2.getVal());
		BVMInteger result = new BVMInteger(resultVal);
		context.getOperandStack().push(result);

		System.out.println("Spoustim instrukci lcmp.");
	}

	private int compare(float l, float m) {
		if (l > m)
			return 1;
		if (l < m)
			return -1;
		if (l == m)
			return 0;
		else
			return naNBehavior.getNaNComparssionResult();
	}

	public enum FCMP {
		G(1), L(-1);

		private int val;

		private FCMP(int val) {
			this.val = val;
		}

		public int getNaNComparssionResult() {
			return val;
		}
	}
}
