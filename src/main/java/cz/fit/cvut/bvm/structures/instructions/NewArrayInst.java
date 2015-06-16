package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.garbagecollector.GarbageCollector;
import cz.fit.cvut.bvm.structures.BVMHeap;
import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMArrayRef;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class NewArrayInst extends BasicInstruction {

	private short atype;

	public NewArrayInst(short atype) {
		this.atype = atype;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {

		BVMInteger count = (BVMInteger) context.getOperandStack().pop();

		if (count.getVal() < 0) {
			throw new NegativeArraySizeException(
					"Nelze vytvorit pole o zaporne velikosti.");
		}

		if (BVMHeap.getInstance().isAlmostFull()) {
			GarbageCollector.getInstance().cleanHeap(executionStack);
		}

		BVMArrayRef newArrayRef = BVMHeap.getInstance().createPrimitiveArray(
				atype, count.getVal());
		context.getOperandStack().push(newArrayRef);

		System.out
				.println("Spoustim instrukci newarray. Na vrcholu zasobniku je: "
						+ context.getOperandStack().peek());
	}

}
