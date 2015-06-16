package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.BVMClass;
import cz.fit.cvut.bvm.structures.BVMMethodArea;
import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPClassInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPUtf8Info;
import cz.fit.cvut.bvm.structures.operands.BVMArrayRef;
import cz.fit.cvut.bvm.structures.operands.BVMGenericRef;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;

public class InstanceOfInst extends BasicInstruction {

	private int constantPoolIndex;

	public InstanceOfInst(int constantPoolIndex) {
		this.constantPoolIndex = constantPoolIndex;
	}


	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		
	    BVMGenericRef objectRef = (BVMGenericRef) context.getOperandStack().pop();
	    if (objectRef.isNull()) {
	    	pushResult(context,0);
	    	return;
	    } 
	    
		CPItem[] constantPool = context.getMethod().getBvmClass().getcPItems();
		CPClassInfo classInfo = (CPClassInfo) constantPool[constantPoolIndex];
		CPUtf8Info utf8Info = (CPUtf8Info) constantPool[classInfo.getNameIndex()];
		String className = utf8Info.getValue();
		
		if (className.equals("java/lang/Object")) {
	    	pushResult(context,1);
	    	return;
		}
		
	    BVMClass clazz = BVMMethodArea.getInstance().getClassByName(className);
	    
	    if (objectRef instanceof BVMObjectRef) {
	    	
		    BVMClass objectClass = ((BVMObjectRef)objectRef).getClassRef();

		    if (objectClass.isInstanceOf(clazz))
		    {
		    	pushResult(context,1);
		    	return;
		    }
	    } else if (objectRef instanceof BVMArrayRef) {
	    	throw new UnsupportedOperationException("array instaceOf X not implemented");
	    }
	    pushResult(context,0);

	}
	
	private void pushResult(ExecutionFrame context, int result) {
		
		context.getOperandStack().push(new BVMInteger(result));
		System.out.println("Spoustim instrukci instanceof.");
	}
	
}
