package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.BVMHeap;
import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMArrayRef;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class IALoadInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {

		BVMInteger index = (BVMInteger) context.getOperandStack().pop();
		BVMArrayRef arrayref = (BVMArrayRef) context.getOperandStack().pop();

		if (arrayref.isNull()) {
			throw new NullPointerException(
					"Nelze nacitat prvek pole, ktery nebyl inicializovan!");
		} else if (index.getVal() < 0 || index.getVal() >= arrayref.getSize()) {
			throw new ArrayIndexOutOfBoundsException(
					"Nelze nacitat prvek z indexu " + index.getVal()
							+ ", kdyz pole ma velikost " + arrayref.getSize());
		}

		BVMInteger[] array = (BVMInteger[]) BVMHeap.getInstance().getArray(
				arrayref);
		context.getOperandStack().push(array[index.getVal()]);

		System.out.println("Spoustim instrukci iaload.");

	}
}
