package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.BVMField;
import cz.fit.cvut.bvm.structures.BVMHeap;
import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPFieldrefInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPNameAndTypeInfo;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;
import cz.fit.cvut.bvm.structures.operands.BVMValue;
import cz.fit.cvut.bvm.utils.ConstantPoolUtils;

public class GetFieldInst extends BasicInstruction {

	private int index;

	public GetFieldInst(int index) {
		this.index = index;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		CPItem[] runtimeConstantPool = context.getMethod().getBvmClass()
				.getcPItems();

		CPFieldrefInfo cpFieldrefInfo = (CPFieldrefInfo) runtimeConstantPool[index];
		CPNameAndTypeInfo nameAndTypeInfo = (CPNameAndTypeInfo) runtimeConstantPool[cpFieldrefInfo
				.getNameAndTypeIndex()];

		String classname = ConstantPoolUtils.getClassname(runtimeConstantPool,
				cpFieldrefInfo.getClassIndex());
		String fieldName = ConstantPoolUtils.getCPUtf8(runtimeConstantPool,
				nameAndTypeInfo.getNameIndex()).getValue();
		String fieldDescriptor = ConstantPoolUtils.getCPUtf8(
				runtimeConstantPool, nameAndTypeInfo.getDescriptionIndex())
				.getValue();

		BVMField resolvedField = context.getMethod().getBvmClass()
				.resolveField(fieldName, fieldDescriptor, classname);

		BVMObjectRef objectRef = (BVMObjectRef) context.getOperandStack().pop();

		Integer fieldIndex = objectRef.getClassRef().getFieldIndex(
				resolvedField);
		BVMValue valueToPush = BVMHeap.getInstance().getObject(objectRef)[fieldIndex];
		context.getOperandStack().push(valueToPush);
		System.out
				.println("Spoustim instrukci getfield. Na vrcholu zasobniku je: "
						+ context.getOperandStack().peek());
	}

}
