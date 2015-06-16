package cz.fit.cvut.bvm.garbagecollector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import cz.fit.cvut.bvm.structures.BVMClass;
import cz.fit.cvut.bvm.structures.BVMField;
import cz.fit.cvut.bvm.structures.BVMHeap;
import cz.fit.cvut.bvm.structures.ExecutionFrame;
import cz.fit.cvut.bvm.structures.operands.BVMArrayRef;
import cz.fit.cvut.bvm.structures.operands.BVMGenericRef;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;
import cz.fit.cvut.bvm.structures.operands.BVMValue;

/**
 * Implementace jednoducheho garbage collectoru
 */
public class GarbageCollector {

	private static GarbageCollector INSTANCE = new GarbageCollector();

	private Set<Integer> signedObjects = new HashSet<Integer>();

	private GarbageCollector() {
	};

	public static GarbageCollector getInstance() {
		return INSTANCE;
	}

	public void cleanHeap(Stack<ExecutionFrame> executionStack) {
		System.out.println("GarbageCollector starting...");
		BVMHeap.getInstance().printHeap();
		findLiveObjects(executionStack);
		cleanHeap();
		if (BVMHeap.getInstance().isFull()) {
			throw new OutOfMemoryError("Operacni pamet je plna!");
		}
		System.out.println("GarbageCollector finished.");
	}

	private void findLiveObjects(Stack<ExecutionFrame> executionStack) {
		// Iterating through whole execution stack
		Iterator<ExecutionFrame> stackIterator = executionStack.iterator();
		while (stackIterator.hasNext()) {
			ExecutionFrame frame = stackIterator.next();

			// Iterating through operandstack in execution frame
			Iterator<BVMValue> operandIterator = frame.getOperandStack().iterator();
			while (operandIterator.hasNext()) {
				BVMValue value = operandIterator.next();

				if (value instanceof BVMGenericRef) {
					BVMGenericRef referenceValue = (BVMGenericRef) value;
					if (!referenceValue.isNull()) {
						if (signedObjects.add(referenceValue.getHeapRef())) {
							System.out.println("Nasel jsem pouzitou referenci na stacku " + referenceValue.getHeapRef());
							searchInside(referenceValue);
						}
					}
				}
			}

			// Iterating through static in execution frame
			for (int i = 0; i < frame.getLocals().length; i++) {
				if (frame.getLocal(i) instanceof BVMGenericRef) {
					BVMGenericRef referenceValue = (BVMGenericRef) frame.getLocal(i);
					if (!referenceValue.isNull()) {
						if (signedObjects.add(referenceValue.getHeapRef())) {
							System.out.println("Nasel jsem pouzitou referenci v locals " + referenceValue.getHeapRef());
							searchInside(referenceValue);
						}
					}
				}
			}

		}
	}

	private void searchInside(BVMGenericRef objectRef) {
		BVMObjectRef tempObjectRef;
		BVMArrayRef tempArrayRef;
		BVMClass clazz = null;
		if (objectRef instanceof BVMObjectRef) {
			tempObjectRef = (BVMObjectRef) objectRef;
			clazz = tempObjectRef.getClassRef();

			for (BVMField field : clazz.listAllFields()) {
				Integer fieldIndex = clazz.getFieldIndex(field);
				if (field.isClassref()) {
					tempObjectRef = (BVMObjectRef) BVMHeap.getInstance().getObject(objectRef)[fieldIndex];
					if (!tempObjectRef.isNull()) {
						if (signedObjects.add(tempObjectRef.getHeapRef())) {
							System.out.println("Nasel jsem pouzitou referenci na obj. "
											+ field.getName()
											+ " v objektu "
											+ clazz.getName()
											+ " heapref: "
											+ tempObjectRef.getHeapRef());
							searchInside(tempObjectRef);
						}
					}
				} else if (field.isClassrefArray()) {
					tempArrayRef = (BVMArrayRef) BVMHeap.getInstance()
							.getArray(objectRef)[fieldIndex];
					if (!tempArrayRef.isNull()) {
						if (signedObjects.add(tempArrayRef.getHeapRef())) {
							System.out
									.println("Nasel jsem pouzitou referenci na pole "
											+ field.getName()
											+ " v objektu "
											+ clazz.getName()
											+ " heapref: "
											+ tempArrayRef.getHeapRef());
							searchInside(tempArrayRef);
						}
					}
				} else if (field.isPrimitiveArray()) {
					tempArrayRef = (BVMArrayRef) BVMHeap.getInstance().getArray(objectRef)[fieldIndex];
					if (!tempArrayRef.isNull()) {
						if (signedObjects.add(tempArrayRef.getHeapRef())) {
							System.out.println("Nasel jsem pouzitou referenci na primitiv. pole "
											+ field.getName()
											+ " v objektu "
											+ clazz.getName()
											+ " heapref: "
											+ tempArrayRef.getHeapRef());
						}
					}
				}
			}

		} else if (objectRef instanceof BVMArrayRef) {
			tempArrayRef = (BVMArrayRef) objectRef;

			if (!tempArrayRef.isPrimitiveArray()) {
				BVMGenericRef[] arrayOfReferences = (BVMGenericRef[]) BVMHeap
						.getInstance().getArray(tempArrayRef);
				for (int i = 0; i < arrayOfReferences.length; i++) {
					if (!arrayOfReferences[i].isNull()) {
						if (signedObjects.add(arrayOfReferences[i].getHeapRef())) {
							System.out.println("Nasel jsem pouzitou referenci z pole "+ tempArrayRef.getHeapRef());
							searchInside(arrayOfReferences[i]);
						}
					}
				}
			}

		}

	}

	private void cleanHeap() {
		Iterator<Integer> heapIterator = BVMHeap.getInstance().getObjectHeap()
				.keySet().iterator();

		while (heapIterator.hasNext()) {
			Integer value = heapIterator.next();
			if (!signedObjects.contains(value)) {
				heapIterator.remove();
				System.out.println("MAZU OBJEKT S ODKAZEM: " + value);
			}
		}
		signedObjects.clear();
	}

}
