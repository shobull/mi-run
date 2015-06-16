package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMFloat;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

/**
 * Convert int to float
 */
public class I2fInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMInteger integerVal = (BVMInteger) context.getOperandStack().pop();
		float floatVal = (float) integerVal.getVal();
		BVMFloat floatValTyped = new BVMFloat(floatVal);
		context.getOperandStack().push(floatValTyped);
		System.out.println("Spoustim instrukci i2f. Konvertuji int: "
				+ integerVal.getVal() + " na " + floatValTyped.getVal());
	}

}
