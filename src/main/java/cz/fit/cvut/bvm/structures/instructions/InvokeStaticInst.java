package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.BVMMethod;
import cz.fit.cvut.bvm.structures.BVMNativeMethodArea;
import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPMethodrefInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPNameAndTypeInfo;
import cz.fit.cvut.bvm.structures.operands.BVMDouble;
import cz.fit.cvut.bvm.structures.operands.BVMLong;
import cz.fit.cvut.bvm.structures.operands.BVMValue;
import cz.fit.cvut.bvm.utils.BVMUtils;
import cz.fit.cvut.bvm.utils.ConstantPoolUtils;

public class InvokeStaticInst extends BasicInstruction {

	private int index;

	public InvokeStaticInst(int nextTwoBytes) {
		index = nextTwoBytes;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		CPItem[] runtimeConstantPool = context.getMethod().getBvmClass()
				.getcPItems();
		CPMethodrefInfo methodInfo = (CPMethodrefInfo) runtimeConstantPool[index];

		CPNameAndTypeInfo nameAndTypeInfo = (CPNameAndTypeInfo) runtimeConstantPool[methodInfo
				.getNameAndTypeIndex()];

		String classname = ConstantPoolUtils.getClassname(runtimeConstantPool,
				methodInfo.getClassIndex());
		String methodName = ConstantPoolUtils.getCPUtf8(runtimeConstantPool,
				nameAndTypeInfo.getNameIndex()).getValue();
		String methodDescriptor = ConstantPoolUtils.getCPUtf8(
				runtimeConstantPool, nameAndTypeInfo.getDescriptionIndex())
				.getValue();

		
		BVMMethod resolvedMethod = context.getMethod().getBvmClass()
				.resolveMethod(methodName, methodDescriptor, classname);

		if (!resolvedMethod.isStatic() || resolvedMethod.isAbstract()
				|| "<init>".equals(resolvedMethod.getName())
				|| "<clinit>".equals(resolvedMethod.getName())) {
			throw new RuntimeException(
					"Metoda volana invokestatic musi byt static nesmi byt abstract ani <init> ani <clinit>.");
		}
		
		if (resolvedMethod.isNative())
		{
			BVMNativeMethodArea.getInstance()
				.callNativeMethod(classname, methodName,
						 context, executionStack);
			return;
		}


		resolvedMethod.getBvmClass().initialize(executionStack);

		ExecutionFrame executionFrame = new ExecutionFrame(resolvedMethod);

		int parametersCnt = BVMUtils.getParametersCnt(methodDescriptor);
		BVMValue[] parameters = new BVMValue[parametersCnt];
		for (int i = parametersCnt - 1; i >= 0; i--) {
			BVMValue operand = context.getOperandStack().pop();
			parameters[i] = operand;
		}

		index = 0;
		for (int i = 0; i < parametersCnt; i++) {
			executionFrame.setLocal(index, parameters[i]);
			if (parameters[i] instanceof BVMLong
					|| parameters[i] instanceof BVMDouble) {
				index++;
			}
			index++;
		}

		System.out.println("Spoustim metodu: " + resolvedMethod.getName());
		executionStack.push(executionFrame);
	}
}
