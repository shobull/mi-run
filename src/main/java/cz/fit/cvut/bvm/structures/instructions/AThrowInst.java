package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.structures.BVMMethodArea;
import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMExceptionHandler;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;

public class AThrowInst extends BasicInstruction {

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {

		BVMObjectRef bvmObjectRef = (BVMObjectRef) context.getOperandStack()
				.pop();

		if (!bvmObjectRef.getClassRef().isSubclassOf(
				BVMMethodArea.getInstance().getThrowableClass())) {
			throw new RuntimeException("Vyjjimka "
					+ bvmObjectRef.getClassRef().getName()
					+ " neni potomek Throwable.");
		}

		if (bvmObjectRef.isNull()) {
			throw new NullPointerException("Athrow - objekt "
					+ bvmObjectRef.getClassRef().getName() + " je null.");
		}

		ExecutionFrame actualFrame = context;
		while (!handlerFound(actualFrame, bvmObjectRef)) {
			executionStack.pop();
			if (executionStack.isEmpty()) {
				throw new RuntimeException("Nenalezen handler pro vyjjimku "
						+ bvmObjectRef.getClassRef().getName() + " - KONCIM");
			}
			actualFrame = executionStack.peek();
		}
		System.out.println("Spoustim instrukci athrow. Jdu na PC: "
				+ actualFrame.getPc() + ": a na vrch. zasobniku je: "
				+ actualFrame.getOperandStack().peek());
	}

	private boolean handlerFound(ExecutionFrame actualFrame,
			BVMObjectRef exceptionObjectRef) {
		for (BVMExceptionHandler handler : actualFrame.getMethod()
				.getExceptionTable()) {
			if (handler.isInOffset(actualFrame.getInvokingPc())) {
				if (handler.getCatchType() != null
						&& exceptionObjectRef.getClassRef().isSubclassOf(
								handler.getCatchType())) {
					actualFrame.setPc(handler.getHandlerPc());
					actualFrame.getOperandStack().push(exceptionObjectRef);
					return true;
				}
			}
		}
		return false;
	}
}
