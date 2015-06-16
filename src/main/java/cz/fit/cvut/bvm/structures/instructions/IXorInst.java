package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class IXorInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMInteger value2 = (BVMInteger) context.getOperandStack().pop();
		BVMInteger value1 = (BVMInteger) context.getOperandStack().pop();

		BVMInteger result = new BVMInteger(value1.getVal() ^ value2.getVal());
		context.getOperandStack().push(result);

		System.out.println("Spoustim instrukci ixor. Na vrcholu zasobniku je: "
				+ context.getOperandStack().peek());
	}

}
