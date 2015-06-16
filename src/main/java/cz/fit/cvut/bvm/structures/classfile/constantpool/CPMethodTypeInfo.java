package cz.fit.cvut.bvm.structures.classfile.constantpool;

public class CPMethodTypeInfo extends CPItem {

	private int descriptorIndex;

	public CPMethodTypeInfo() {
		super(CONSTANT_MethodType);
	}

	public int getDescriptorIndex() {
		return descriptorIndex;
	}

	public void setDescriptorIndex(int descriptorIndex) {
		this.descriptorIndex = descriptorIndex;
	}

	@Override
	public String toString() {
		return "CPMethodTypeInfo [descriptorIndex=" + descriptorIndex + ", toString()=" + super.toString()
				+ "]";
	}

}
