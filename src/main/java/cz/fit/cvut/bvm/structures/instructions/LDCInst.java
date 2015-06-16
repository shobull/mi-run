package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPFloatInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPIntegerInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPStringInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPUtf8Info;
import cz.fit.cvut.bvm.structures.operands.BVMFloat;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;
import cz.fit.cvut.bvm.utils.NativeUtils;

public class LDCInst extends BasicInstruction {

	private byte constantPoolIndex;

	public LDCInst(byte constantPoolIndex) {
		this.constantPoolIndex = constantPoolIndex;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		CPItem[] constantPool = context.getMethod().getBvmClass().getcPItems();
		if (constantPool[constantPoolIndex] instanceof CPIntegerInfo) {
			CPIntegerInfo cpIntegerInfo = (CPIntegerInfo) constantPool[constantPoolIndex];
			context.getOperandStack().push(
					new BVMInteger(cpIntegerInfo.getValue()));
		} else if (constantPool[constantPoolIndex] instanceof CPFloatInfo) {
			CPFloatInfo cpFloatInfo = (CPFloatInfo) constantPool[constantPoolIndex];
			context.getOperandStack()
					.push(new BVMFloat(cpFloatInfo.getValue()));
		} else if (constantPool[constantPoolIndex] instanceof CPStringInfo) {

			CPStringInfo stringInfo = (CPStringInfo) constantPool[constantPoolIndex];
			CPUtf8Info utf8Info = (CPUtf8Info) constantPool[stringInfo
					.getStringIndex()];
			String stringText = utf8Info.getValue();

			BVMObjectRef stringRef = NativeUtils
					.NativeStringToBVMString(stringText);

			context.getOperandStack().push(stringRef);
		}
		
		System.out.println("Spoustim instrukci ldc. Na vrcholu zasobniku je: "
				+ context.getOperandStack().peek());
	}

}
