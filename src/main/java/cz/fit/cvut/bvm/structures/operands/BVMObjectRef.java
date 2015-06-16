package cz.fit.cvut.bvm.structures.operands;

import cz.fit.cvut.bvm.structures.BVMClass;

public class BVMObjectRef extends BVMGenericRef {

	private BVMClass classRef;

	/**
	 * null
	 */
	public BVMObjectRef() {
		this.classRef = null;
	}
	
	public BVMObjectRef(Integer heapRef) {
		super(heapRef);
	}

	public BVMClass getClassRef() {
		return classRef;
	}

	public void setClassRef(BVMClass classRef) {
		this.classRef = classRef;
	}

	@Override
	public BVMValue copy() {
		BVMObjectRef bvmObjectRef = new BVMObjectRef(heapRef);
		bvmObjectRef.setClassRef(classRef);
		return bvmObjectRef;
	}

	@Override
	public String toString() {
		if (heapRef != null) {
			return "BVMObjectRef [classRef=" + classRef.getName()
					+ ", heapRef=" + heapRef + "]";
		} else {
			return "BVMObjectRef [NULL]";
		}
	}

}
