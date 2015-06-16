package cz.fit.cvut.bvm;

import java.io.IOException;
import java.util.Stack;

import cz.fit.cvut.bvm.structures.BVMClass;
import cz.fit.cvut.bvm.structures.BVMHeap;
import cz.fit.cvut.bvm.structures.BVMMethod;
import cz.fit.cvut.bvm.structures.BVMMethodArea;
import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.instructions.BasicInstruction;
import cz.fit.cvut.bvm.structures.operands.BVMArrayRef;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;
import cz.fit.cvut.bvm.utils.NativeUtils;

public class BestVirtualMachine {

	private BVMMethodArea methodArea = BVMMethodArea.getInstance();
	private Stack<ExecutionFrame> executionStack = new Stack<ExecutionFrame>();

	/**
	 * Load a program
	 */
	public void loadProgram(String classpath, String classname,
			String[] arguments) throws IOException {
		BVMMethod mainMethod = methodArea.loadFirstClass(classpath, classname)
				.getMainMethod();

		ExecutionFrame mainFrame = new ExecutionFrame(mainMethod);
		if (!"()V".equals(mainMethod.getDescriptor())) {
			// main method accept parameters (String[] args)
			BVMArrayRef arrayRef = prepareArguments(arguments);
			mainFrame.setLocal(0, arrayRef);
		}

		executionStack.push(mainFrame);
	}

	/**
	 * Prepare parameters into frame with main method
	 */
	private BVMArrayRef prepareArguments(String[] arguments) {
		BVMClass stringClass = methodArea.getClassByName("java/lang/String");
		BVMArrayRef arrayRef = BVMHeap.getInstance().createObjectArray(
				stringClass, arguments.length);

		for (int i = 0; i < arguments.length; i++) {
			BVMObjectRef stringRef = NativeUtils
					.NativeStringToBVMString(arguments[i]);
			BVMHeap.getInstance().getArray(arrayRef)[i] = stringRef;
		}

		return arrayRef;
	}

	/**
	 * Run program
	 */
	public void run() {
		while (!executionStack.isEmpty()) {
			BasicInstruction instruction = executionStack.peek()
					.fetchNextInstruction();
			if (instruction != null) {
				instruction.execute(executionStack.peek(), executionStack);
			}
		}

	}

}
