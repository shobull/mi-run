package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMGenericRef;

public class AStoreInst extends BasicInstruction {

	private int index;

	public AStoreInst(int index) {
		this.index = index;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMGenericRef value = (BVMGenericRef) context.getOperandStack().pop();
		context.setLocal(index, value);

		if (context.getOperandStack().empty()) {
			System.out
					.println("Spoustim instrukci astore. Zasobnik je prazdny.");
		} else {
			System.out
					.println("Spoustim instrukci astore. Do vrcholu zasobniku je: "
							+ context.getOperandStack().peek());
		}
	}

}
