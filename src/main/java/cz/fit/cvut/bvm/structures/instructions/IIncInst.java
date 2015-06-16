package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class IIncInst extends BasicInstruction {

	private short index;
	private short constant;

	public IIncInst(short index, short constant) {
		this.index = index;
		this.constant = constant;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		BVMInteger local = (BVMInteger) context.getLocal(index);
		context.setLocal(index, local.add(new BVMInteger(constant)));

		System.out.println("Spoustim instrukci iinc");
	}

}
