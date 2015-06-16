package cz.fit.cvut.bvm.structures.operands;

import cz.fit.cvut.bvm.structures.BVMClass;

public class BVMArrayRef extends BVMGenericRef {

	private int size;

	private BVMClass type;

	public BVMArrayRef(Integer heapRef) {
		super(heapRef);
	}

	public void setSize(int count) {
		this.size = count;
	}

	public int getSize() {
		return size;
	}

	public void setType(BVMClass type) {
		this.type = type;
	}

	public boolean isPrimitiveArray() {
		return type == null;
	}

	public BVMClass getType() {
		return type;
	}

	public boolean isInterface() {
		return type.isInterface();
	}

	@Override
	public BVMValue copy() {
		BVMArrayRef bvmArrayRef = new BVMArrayRef(heapRef);
		bvmArrayRef.setSize(size);
		bvmArrayRef.setType(type);
		return bvmArrayRef;
	}

	@Override
	public String toString() {
		if (heapRef != null) {
			return "BVMArrayRef [heapRef=" + heapRef + ", size=" + size
			/* + ", type=" + type.getName() + "]"; */
			+ "]"; // null pointer
		} else {
			return "BVMArrayRef [NULL]";
		}
	}

}
