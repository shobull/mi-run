package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.garbagecollector.GarbageCollector;
import cz.fit.cvut.bvm.structures.BVMClass;
import cz.fit.cvut.bvm.structures.BVMHeap;
import cz.fit.cvut.bvm.structures.BVMMethodArea;
import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPClassInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;
import cz.fit.cvut.bvm.utils.ConstantPoolUtils;

public class NewInst extends BasicInstruction {

	private int index;

	public NewInst(int index) {
		this.index = index;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		CPItem[] runtimeConstantPool = context.getMethod().getBvmClass()
				.getcPItems();

		if (runtimeConstantPool[index] instanceof CPClassInfo) {
			String classname = ConstantPoolUtils.getClassname(
					runtimeConstantPool, index);

			BVMClass resolvedClass = BVMMethodArea.getInstance()
					.getClassByName(classname);
			resolvedClass.initialize(executionStack);

			if (resolvedClass.isAbstract() || resolvedClass.isInterface()) {
				throw new InstantiationError(
						"Nelze vytvaret instanci abstraktni tridy nebo interfacu.");
			}

			if (BVMHeap.getInstance().isAlmostFull()) {
				GarbageCollector.getInstance().cleanHeap(executionStack);
			}

			BVMObjectRef newObjectRef = BVMHeap.getInstance().createObject(
					resolvedClass);
			context.getOperandStack().push(newObjectRef);

			System.out
					.println("Spoustim instrukci new. Na vrcholu zasobniku je: "
							+ context.getOperandStack().peek());
		} else {
			throw new RuntimeException(
					"Instrukce NEW - typ vytvareneho objektu neni interface ani trida");
		}

	}

}
