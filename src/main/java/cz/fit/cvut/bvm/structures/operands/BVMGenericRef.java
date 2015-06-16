package cz.fit.cvut.bvm.structures.operands;

public abstract class BVMGenericRef implements BVMValue {

	protected final Integer heapRef;
	
	/**
	 * creates null
	 */
	public BVMGenericRef() {
		this.heapRef = null;
	}
	
	public BVMGenericRef(Integer heapRef) {
		this.heapRef = heapRef;
	}

	public Integer getHeapRef() {
		return heapRef;
	}

	public boolean isNull() {
		return heapRef == null;
	}

	@Override
	public abstract BVMValue copy();

}
