package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;

public class AConstNullInstr extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		context.getOperandStack().push(new BVMObjectRef());

		System.out
				.println("Spoustim instrukci aconst_null. Na vrcholu zasobniku je: "
						+ context.getOperandStack().peek());
	}

}
