package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class IRemInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {

		BVMInteger value2 = (BVMInteger) context.getOperandStack().pop();
		BVMInteger value1 = (BVMInteger) context.getOperandStack().pop();

		if (value2.getVal() == 0) {
			throw new ArithmeticException("Delitel nesmi byt 0!");
		}

		BVMInteger result = value1.sub(value1.div(value2).mul(value2));

		context.getOperandStack().push(result);

		System.out.println("Spoustim irem. Na vrcholu zasobniku je: "
				+ context.getOperandStack().peek());
	}

}
