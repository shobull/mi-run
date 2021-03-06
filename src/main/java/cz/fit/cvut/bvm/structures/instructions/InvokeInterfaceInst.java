package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.BVMClass;
import cz.fit.cvut.bvm.structures.BVMMethod;
import cz.fit.cvut.bvm.structures.BVMNativeMethodArea;
import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPMethodrefInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPNameAndTypeInfo;
import cz.fit.cvut.bvm.structures.operands.BVMDouble;
import cz.fit.cvut.bvm.structures.operands.BVMLong;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;
import cz.fit.cvut.bvm.structures.operands.BVMValue;
import cz.fit.cvut.bvm.utils.BVMUtils;
import cz.fit.cvut.bvm.utils.ConstantPoolUtils;

public class InvokeInterfaceInst extends BasicInstruction {

	private int index;

	// count a zero neni potreba (historicky pozustatek)
	@SuppressWarnings("unused")
	private short count;

	@SuppressWarnings("unused")
	private short zero;
	
	public InvokeInterfaceInst(int index, short count, short zero) {
		this.index = index;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		CPItem[] runtimeConstantPool = context.getMethod().getBvmClass()
				.getcPItems();
		CPMethodrefInfo cpMethodrefInfo = (CPMethodrefInfo) runtimeConstantPool[index];
		CPNameAndTypeInfo nameAndTypeInfo = (CPNameAndTypeInfo) runtimeConstantPool[cpMethodrefInfo
				.getNameAndTypeIndex()];

		String classname = ConstantPoolUtils.getClassname(runtimeConstantPool,
				cpMethodrefInfo.getClassIndex());
		String methodName = ConstantPoolUtils.getCPUtf8(runtimeConstantPool,
				nameAndTypeInfo.getNameIndex()).getValue();
		String methodDescriptor = ConstantPoolUtils.getCPUtf8(
				runtimeConstantPool, nameAndTypeInfo.getDescriptionIndex())
				.getValue();

		BVMMethod resolvedMethod = context
				.getMethod()
				.getBvmClass()
				.resolveInterfaceMethod(methodName, methodDescriptor, classname);

		if (resolvedMethod == null) {
			throw new NoSuchMethodError("Metoda " + methodName + " nenalezena!");
		}

		if ("<init>".equals(resolvedMethod.getName())
				|| "<clinit>".equals(resolvedMethod.getName())) {
			throw new RuntimeException(
					"Metoda volana invokevirtual nesmi byt ani <init> ani <clinit>.");
		}

		int parametersCnt = BVMUtils.getParametersCnt(methodDescriptor);
		BVMValue[] parameters = new BVMValue[parametersCnt];
		for (int i = parametersCnt - 1; i >= 0; i--) {
			BVMValue operand = context.getOperandStack().pop();
			parameters[i] = operand;
		}

		BVMObjectRef objectRef = (BVMObjectRef) context.getOperandStack().pop();

		if (objectRef.getHeapRef() == null) {
			throw new NullPointerException("Instance tridy " + classname
					+ " nebyl inicializovan.");
		}

		BVMMethod lookedUpMethod = searchForMethod(objectRef.getClassRef(),
				methodName, methodDescriptor);

		if (lookedUpMethod == null) {
			throw new AbstractMethodError("Metoda " + methodName
					+ " nebyla nalezena.");
		} else if (!lookedUpMethod.isPublic()) {
			throw new IllegalAccessError("Metoda neni public.");
		} else if (lookedUpMethod.isAbstract()) {
			throw new AbstractMethodError("Metoda je abstract.");
		}
		
		if (resolvedMethod.isNative())
		{
			BVMNativeMethodArea.getInstance()
				.callNativeMethod(classname, methodName,
						 context, executionStack);
			return;
		}

		System.out.println("Spoustim metodu: " + lookedUpMethod.getName());
		ExecutionFrame executionFrame = new ExecutionFrame(lookedUpMethod);

		executionFrame.setLocal(0, objectRef);
		index = 1;
		for (int i = 0; i < parametersCnt; i++) {
			executionFrame.setLocal(index, parameters[i]);
			System.out.println("Metoda: " + lookedUpMethod.getName()
					+ " parametr[" + index + "] = " + parameters[i]);
			if (parameters[i] instanceof BVMLong
					|| parameters[i] instanceof BVMDouble) {
				index++;
			}
			index++;
		}

		executionStack.push(executionFrame);
	}

	private BVMMethod searchForMethod(BVMClass invokingClass,
			String methodName, String methodDescriptor) {

		if (invokingClass.containsMethod(methodName, methodDescriptor)) {
			System.out.println("Nasel jsem metodu " + methodName + " v tride "
					+ invokingClass.getName());
			return invokingClass.getMethodByNameAndDescriptor(methodName,
					methodDescriptor);
		}

		if (invokingClass.getSuperclass() != null) {
			return searchForMethod(invokingClass.getSuperclass(), methodName,
					methodDescriptor);
		}

		System.out.println("Metoda " + methodDescriptor + " " + methodName
				+ " nenalezena!!!");

		return null;

	}

}
