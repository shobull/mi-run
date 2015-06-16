package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPDoubleInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPLongInfo;
import cz.fit.cvut.bvm.structures.operands.BVMDouble;
import cz.fit.cvut.bvm.structures.operands.BVMLong;
import cz.fit.cvut.bvm.structures.operands.BVMValue;

public class LDC2W extends BasicInstruction {

	private int index;

	public LDC2W(int index) {
		this.index = index;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		CPItem[] runtimeConstantPool = context.getMethod().getBvmClass()
				.getcPItems();

		BVMValue val = null;
		if (runtimeConstantPool[index] instanceof CPLongInfo) {
			CPLongInfo cpLongInfo = (CPLongInfo) runtimeConstantPool[index];
			val = new BVMLong(cpLongInfo.getValue());
		} else if (runtimeConstantPool[index] instanceof CPDoubleInfo) {
			CPDoubleInfo cpDoubleInfo = (CPDoubleInfo) runtimeConstantPool[index];
			val = new BVMDouble(cpDoubleInfo.getValue());
		}

		context.getOperandStack().push(val);

		System.out
				.println("Spoustim instrukci ldc2_w. Na vrcholu zasobniku je: "
						+ context.getOperandStack().peek());

	}

}
