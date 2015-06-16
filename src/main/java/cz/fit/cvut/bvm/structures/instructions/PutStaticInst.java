package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.BVMClass;
import cz.fit.cvut.bvm.structures.BVMMethodArea;
import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPFieldrefInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPNameAndTypeInfo;
import cz.fit.cvut.bvm.structures.operands.BVMValue;
import cz.fit.cvut.bvm.utils.ConstantPoolUtils;

public class PutStaticInst extends BasicInstruction {

	int index;

	public PutStaticInst(short byte1, short byte2) {
		index = (byte1 << 8) | byte2;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		CPItem[] runtimeConstantPool = context.getMethod().getBvmClass()
				.getcPItems();

		CPFieldrefInfo cpFieldrefInfo = (CPFieldrefInfo) runtimeConstantPool[index];
		CPNameAndTypeInfo cpNameAndTypeInfo = (CPNameAndTypeInfo) runtimeConstantPool[cpFieldrefInfo
				.getNameAndTypeIndex()];

		String classname = ConstantPoolUtils.getClassname(runtimeConstantPool,
				cpFieldrefInfo.getClassIndex());
		String fieldname = ConstantPoolUtils.getCPUtf8(runtimeConstantPool,
				cpNameAndTypeInfo.getNameIndex()).getValue();
		String descriptor = ConstantPoolUtils.getCPUtf8(runtimeConstantPool,
				cpNameAndTypeInfo.getDescriptionIndex()).getValue();

		BVMClass bvmClass = BVMMethodArea.getInstance().getClassByName(
				classname);
		BVMClass fieldDeclaredIn = bvmClass.searchForField(fieldname,
				descriptor, bvmClass.getName());
		if (fieldDeclaredIn == null) {
			throw new NoSuchFieldError("Nenalezen field: " + fieldname);
		}
		fieldDeclaredIn.initialize(executionStack);

		BVMValue value = context.getOperandStack().pop();
		fieldDeclaredIn.setField(fieldname, value);

		System.out.println("Spoustim instrukci putstatic. Nastavuji: "
				+ fieldname + " = " + value);
	}
}
