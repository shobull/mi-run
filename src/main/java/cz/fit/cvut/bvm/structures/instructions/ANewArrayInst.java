package cz.fit.cvut.bvm.structures.instructions;

import java.util.Stack;

import cz.fit.cvut.bvm.garbagecollector.GarbageCollector;
import cz.fit.cvut.bvm.structures.BVMClass;
import cz.fit.cvut.bvm.structures.BVMHeap;
import cz.fit.cvut.bvm.structures.BVMMethodArea;
import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.operands.BVMArrayRef;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;
import cz.fit.cvut.bvm.utils.ConstantPoolUtils;

public class ANewArrayInst extends BasicInstruction {

	private int index;

	public ANewArrayInst(int index) {
		this.index = index;
	}

	@Override
	public void execute(ExecutionFrame context,
			Stack<ExecutionFrame> executionStack) {
		CPItem[] runtimeConstantPool = context.getMethod().getBvmClass()
				.getcPItems();
		String classname = ConstantPoolUtils.getClassname(runtimeConstantPool,
				index);
		BVMClass resolvedClass = BVMMethodArea.getInstance().getClassByName(
				classname);

		BVMInteger count = (BVMInteger) context.getOperandStack().pop();
		if (count.getVal() < 0) {
			throw new NegativeArraySizeException(
					"Nelze vytvorit pole o zaporne velikosti.");
		}

		if (BVMHeap.getInstance().isAlmostFull()) {
			GarbageCollector.getInstance().cleanHeap(executionStack);
		}

		BVMArrayRef newArrayRef = BVMHeap.getInstance().createObjectArray(
				resolvedClass, count.getVal());
		context.getOperandStack().push(newArrayRef);

		System.out
				.println("Spoustim instrukci anewarray. Na vrcholu zasobniku je: "
						+ context.getOperandStack().peek());

	}

}
