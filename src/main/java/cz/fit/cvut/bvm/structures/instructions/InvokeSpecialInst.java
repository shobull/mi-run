package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.BVMClass;
import cz.fit.cvut.bvm.structures.BVMMethod;
import cz.fit.cvut.bvm.structures.BVMMethodArea;
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

/**
 * Static binding - Metodu, kterou spoustim hledam dle reference v classfile (od
 * kompilatoru)
 * 
 * @author lubos
 *
 */
public class InvokeSpecialInst extends BasicInstruction {

	private int index;

	public InvokeSpecialInst(int index) {
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

		BVMClass currentClass = context.getMethod().getBvmClass();
		BVMClass referencedClass = BVMMethodArea.getInstance().getClassByName(classname);
		
		BVMMethod resolvedMethod = context.getMethod().getBvmClass().resolveMethod(methodName, methodDescriptor, classname);

		BVMClass classForSearching = null;
		if (!"<init>".equals(resolvedMethod.getName()) &&
				!referencedClass.isInterface() && !referencedClass.getName().equals(currentClass.getName()) && currentClass.isSubclassOf(referencedClass)
				) {
			classForSearching = currentClass.getSuperclass();
		} else {
			classForSearching = referencedClass;
		}
		
		BVMMethod lookedUpMethod = searchForMethod(classForSearching, methodName, methodDescriptor);
		
		if (resolvedMethod.isNative())
		{
			BVMNativeMethodArea.getInstance()
				.callNativeMethod(classname, methodName,
						 context, executionStack);
			return;
		}
		
		System.out.println("Spoustim metodu: " + lookedUpMethod.getName()
				+ " tridy " + lookedUpMethod.getBvmClass().getName() + " + z tridy " + context.getMethod().getBvmClass().getName());
		ExecutionFrame executionFrame = new ExecutionFrame(lookedUpMethod);

		int parametersCnt = BVMUtils.getParametersCnt(methodDescriptor);
		BVMValue[] parameters = new BVMValue[parametersCnt];
		for (int i = parametersCnt - 1; i >= 0; i--) {
			BVMValue operand = context.getOperandStack().pop();
			parameters[i] = operand;
		}

		BVMObjectRef bvmObjectRef = (BVMObjectRef) context.getOperandStack()
				.pop();

		executionFrame.setLocal(0, bvmObjectRef);
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
