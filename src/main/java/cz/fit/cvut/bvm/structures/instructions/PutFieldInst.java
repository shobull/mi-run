package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.BVMClass;
import cz.fit.cvut.bvm.structures.BVMField;
import cz.fit.cvut.bvm.structures.BVMHeap;
import cz.fit.cvut.bvm.structures.BVMMethodArea;
import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPFieldrefInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPNameAndTypeInfo;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;
import cz.fit.cvut.bvm.structures.operands.BVMValue;
import cz.fit.cvut.bvm.utils.ConstantPoolUtils;

public class PutFieldInst extends BasicInstruction {

	private int index;

	public PutFieldInst(int index) {
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

		BVMClass bvmClass = BVMMethodArea.getInstance().getClassByName(
				classname);

		BVMClass classWithField = bvmClass.searchForField(fieldName,
				fieldDescriptor, classname);

		if (classWithField == null) {
			throw new NoSuchFieldError("Nenalezen field: " + fieldName);
		}

		BVMField lookedUpField = classWithField.getFieldByNameAndDescriptor(
				fieldName, fieldDescriptor);

		if (lookedUpField.isFinal()) {
			if (!bvmClass.getName().equals(classWithField.getName())
					|| !context.getMethod().getName().equals("<init>")) {
				throw new RuntimeException(
						"PutField pro final field se musi provadet rovnou ve tride a v kontruktoru!");
			}
		}

		BVMValue value = context.getOperandStack().pop();
		BVMObjectRef objectRef = (BVMObjectRef) context.getOperandStack().pop();

		if (objectRef.getHeapRef() == null) {
			throw new NullPointerException("Instance tridy " + classname
					+ " nebyla inicializovana.");
		}

		BVMValue[] instanceValues = BVMHeap.getInstance().getObject(objectRef);
		instanceValues[objectRef.getClassRef().getFieldIndex(lookedUpField)] = value;

		System.out.println("Spoustim instrukci putfield. Provadim prirazeni: "
				+ lookedUpField.getName() + " = " + value);
	}
}
