package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.BVMHeap;
import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMArrayRef;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;
import cz.fit.cvut.bvm.structures.operands.BVMLong;

public class LAstoreInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {

		BVMLong value = (BVMLong) context.getOperandStack().pop();
		BVMInteger index = (BVMInteger) context.getOperandStack().pop();
		BVMArrayRef arrayref = (BVMArrayRef) context.getOperandStack().pop();

		if (arrayref.isNull()) {
			throw new NullPointerException(
					"Nelze ulozit prvek na pozici, ktera nebyla inicializovana.");
		} else if (index.getVal() < 0 || index.getVal() >= arrayref.getSize()) {
			throw new ArrayIndexOutOfBoundsException(
					"Nelze ulozit prvek na pozici " + index.getVal()
							+ " do pole o velikosti " + arrayref.getSize());
		}

		BVMLong[] array = (BVMLong[]) BVMHeap.getInstance().getArray(
				arrayref);
		array[index.getVal()] = value;

		if (context.getOperandStack().isEmpty()) {
			System.out.println("Spoustim instrukci lastore.");
		} else {
			System.out
					.println("Spoustim instrukci lastore. Na vrcholu zasobniku je: "
							+ context.getOperandStack().peek());
		}

	}

}
