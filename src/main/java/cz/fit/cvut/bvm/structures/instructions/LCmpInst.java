package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;
import cz.fit.cvut.bvm.structures.operands.BVMLong;

public class LCmpInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMLong operand1;
		BVMLong operand2;

		if (context.getOperandStack().peek() instanceof BVMLong) {
			operand2 = (BVMLong) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("err - 2. operand neni BVMLong.");
		}

		if (context.getOperandStack().peek() instanceof BVMLong) {
			operand1 = (BVMLong) context.getOperandStack().pop();
		} else {
			throw new RuntimeException("err - 1. operand neni BVMLong.");
		}

		int resultVal = compare(operand1.getVal(), operand2.getVal());
		BVMInteger result = new BVMInteger(resultVal);
		context.getOperandStack().push(result);
		
		System.out.println("Spoustim instrukci lcmp.");
	}
	
	private int compare(long l, long m)
	{
		if (l > m)
			return 1;
		if (l < m)
			return -1;
		else
			return 0;
	}

}
