package cz.fit.cvut.bvm.structures;

import java.util.HashMap;
import java.util.Map;

import cz.fit.cvut.bvm.structures.operands.BVMArrayRef;
import cz.fit.cvut.bvm.structures.operands.BVMFloat;
import cz.fit.cvut.bvm.structures.operands.BVMGenericRef;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;
import cz.fit.cvut.bvm.structures.operands.BVMLong;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;
import cz.fit.cvut.bvm.structures.operands.BVMValue;

public class BVMHeap {

	private static final BVMHeap INSTANCE = new BVMHeap();

	private final int GARBAGE_COLLECTING_HEAP_LIMIT = 50;

	private final int MAXIMUM_HEAP_SIZE = 100;

	private Integer heapPtr = 0;

	private Map<Integer, BVMValue[]> objectHeap = new HashMap<Integer, BVMValue[]>();

	private BVMHeap() {
	};

	public static BVMHeap getInstance() {
		return INSTANCE;
	}

	/** ----------------- GETTERS & SETTERS ---------------- */

	public Map<Integer, BVMValue[]> getObjectHeap() {
		return objectHeap;
	}

	/** --------------------- METHODS ----------------------- */

	public BVMValue[] getObject(BVMGenericRef reference) {
		return objectHeap.get(reference.getHeapRef());
	}

	public void removeFromHeap(Integer value) {
		objectHeap.remove(value);
	}

	public BVMValue[] getArray(BVMGenericRef reference) {
		return objectHeap.get(reference.getHeapRef());
	}

	public BVMObjectRef createObject(BVMClass bvmClass) {

		BVMObjectRef objectRef = new BVMObjectRef(heapPtr);
		objectRef.setClassRef(bvmClass);

		BVMValue[] classFields = new BVMValue[bvmClass.getFieldsCnt()];

		String descriptorTemp, classname;
		BVMClass referenceClass;

		for (int k = 0; k < bvmClass.getFieldsCnt(); k++) {
			BVMField field = bvmClass.getFieldByIndex(k);
			switch (field.getDescriptor().substring(0, 1)) {
			case "S":
			case "B":
			case "C":
			case "I":
				classFields[k] = new BVMInteger(0);
				break;
			case "F":
				classFields[k] = new BVMFloat(0.0f);
				break;
			case "L":
				descriptorTemp = bvmClass.getFieldByIndex(k).getDescriptor();
				classname = descriptorTemp.substring(1,
						descriptorTemp.length() - 1);
				referenceClass = BVMMethodArea.getInstance().getClassByName(
						classname);
				BVMObjectRef bvmObjectRefTmp = new BVMObjectRef();
				bvmObjectRefTmp.setClassRef(referenceClass);
				classFields[k] = bvmObjectRefTmp;
				break;
			case "[":
				BVMArrayRef arrayRef = new BVMArrayRef(null);
				if (field.getDescriptor().startsWith("[L")) {
					descriptorTemp = bvmClass.getFieldByIndex(k)
							.getDescriptor();
					classname = descriptorTemp.substring(2,
							descriptorTemp.length() - 1);
					referenceClass = BVMMethodArea.getInstance()
							.getClassByName(classname);
					arrayRef.setType(referenceClass);
				}
				classFields[k] = arrayRef;
				break;
			default:
				break;
			}
		}

		this.objectHeap.put(heapPtr, classFields);

		heapPtr++;
		return objectRef;
	}

	public BVMArrayRef createPrimitiveArray(short atype, int count) {
		BVMArrayRef arrayRef = new BVMArrayRef(heapPtr);
		arrayRef.setSize(count);

		BVMValue[] values = null;

		switch (atype) {
		case 6:
		case 7:
			values = new BVMFloat[count];
			for (int i = 0; i < count; i++) {
				values[i] = new BVMFloat(0.0f);
			}
			break;
		case 4: // boolean
		case 5: // char
		case 8: // byte
		case 9: // short
		case 10: // int
			values = new BVMInteger[count];
			for (int i = 0; i < count; i++) {
				values[i] = new BVMInteger(0);
			}
			break;
		case 11:
			values = new BVMLong[count];
			for (int i = 0; i < count; i++) {
				values[i] = new BVMLong(0l);
			}
			break;
		default:
			break;
		}

		this.objectHeap.put(heapPtr, values);

		heapPtr++;
		return arrayRef;
	}

	public BVMArrayRef createObjectArray(BVMClass resolvedClass, int count) {
		BVMArrayRef arrayRef = new BVMArrayRef(heapPtr);
		arrayRef.setSize(count);
		arrayRef.setType(resolvedClass);

		BVMValue[] values = new BVMObjectRef[count];
		for (int i = 0; i < count; i++) {
			values[i] = new BVMObjectRef();		
			((BVMObjectRef) values[i]).setClassRef(resolvedClass);
		}

		this.objectHeap.put(heapPtr, values);

		heapPtr++;
		return arrayRef;
	}

	public boolean isAlmostFull() {
		return objectHeap.size() >= GARBAGE_COLLECTING_HEAP_LIMIT;
	}

	public boolean isFull() {
		return objectHeap.size() >= MAXIMUM_HEAP_SIZE;
	}

	public void printHeap() {
		StringBuilder sb = new StringBuilder();
		BVMValue[] values = null;
		sb.append("--------- HEAP (size: " + objectHeap.size()
				+ ") ---------\n");
		for (Integer key : objectHeap.keySet()) {
			values = objectHeap.get(key);
			sb.append("objekt " + key + ": ");
			for (int i = 0; i < values.length; i++) {
				sb.append(values[i] + ", ");
			}
			sb.append("\n");
		}
		sb.append("--------- HEAP END (size: " + objectHeap.size() + ") ------");
		System.out.println(sb.toString());
	}

}
