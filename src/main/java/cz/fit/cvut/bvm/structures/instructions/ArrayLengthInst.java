package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMArrayRef;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class ArrayLengthInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {

		BVMArrayRef arrayRef = (BVMArrayRef) context.getOperandStack().pop();
		BVMInteger size = new BVMInteger(arrayRef.getSize());
		context.getOperandStack().push(size);

		System.out.println("Spoustim instrukci arraylength.");
	}

}
