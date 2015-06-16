package cz.fit.cvut.bvm.structures;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.operands.BVMArrayRef;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;
import cz.fit.cvut.bvm.structures.operands.BVMValue;
import cz.fit.cvut.bvm.utils.Native;
import cz.fit.cvut.bvm.utils.NativeUtils;


public class BVMNativeMethodArea {

	private static BVMNativeMethodArea INSTANCE = new BVMNativeMethodArea();
	private BVMNativeMethodArea() {};

	private static BVMMethodArea methodArea = BVMMethodArea.getInstance();
	private static BVMHeap heap = BVMHeap.getInstance();
	
	public static BVMNativeMethodArea getInstance() {
		return INSTANCE;
	}

	public void callNativeMethod(String classname, String methodName, 
			ExecutionFrame context, Stack<ExecutionFrame> executionStack)
	{
		System.out.println("Spoustim nativni funkci");
		
		String path = classname + "." + methodName;
		if(path.equals("bvmlib/Native.println"))
		{
			println(context, executionStack);
		}
		else if(path.equals("bvmlib/Native.intToCharArray"))
		{
			intToCharArray(context, executionStack);
		}
		else if(path.equals("bvmlib/Native.readAllLines"))
		{
			readAllLines(context, executionStack);
		}
		else if(path.equals("bvmlib/Native.write"))
		{
			write(context, executionStack);
		}
		else 
		{
			throw new UnsupportedOperationException("native method: " + 
					path + " not found");
		}
	}
	
	
	private void println(ExecutionFrame context, 
			Stack<ExecutionFrame> executionStack) {
		
		BVMObjectRef strRef = (BVMObjectRef) context.getOperandStack().pop();
		
		String s = NativeUtils.getNativeStringFromRef(strRef, "> ");
		System.out.println(s);
	}
	
	private void intToCharArray(ExecutionFrame context, 
			Stack<ExecutionFrame> executionStack) {
		BVMInteger integer = (BVMInteger) context.getOperandStack().pop();
		
		String str = Integer.toString(integer.getVal());
		
		BVMArrayRef ref = NativeUtils.createCharArrayFromString(str);
		context.getOperandStack().push(ref);
	}
	
	private void readAllLines(ExecutionFrame context, 
			Stack<ExecutionFrame> executionStack) {
		
		BVMObjectRef strRef = (BVMObjectRef) context.getOperandStack().pop();
		String path = NativeUtils.getNativeStringFromRef(strRef, "");
		
		String [] lines = Native.readAllLines(path);
		
		
		BVMClass stringClass = methodArea.getClassByName("java/lang/String");
		BVMArrayRef arrRef = heap.createObjectArray(stringClass, lines.length);
		BVMValue[] arr = heap.getObject(arrRef);

		for (int i = 0; i < lines.length; i++) {
			arr[i] = NativeUtils.NativeStringToBVMString(lines[i]);
		}

		context.getOperandStack().push(arrRef);
	}
	
	private void write(ExecutionFrame context, 
			Stack<ExecutionFrame> executionStack) {
		
		String text = popToNativeString(context);		
		String path = popToNativeString(context);		
		
		Native.write(path, text);
	}
	
	
	private String popToNativeString(ExecutionFrame context) {
		BVMObjectRef strRef = (BVMObjectRef) context.getOperandStack().pop();
		return NativeUtils.getNativeStringFromRef(strRef, "");
	}
}
