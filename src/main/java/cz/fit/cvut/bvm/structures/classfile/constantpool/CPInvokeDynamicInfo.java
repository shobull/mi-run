package cz.fit.cvut.bvm.structures.classfile.constantpool;

public class CPInvokeDynamicInfo extends CPItem {

	private int bootstrapMethodAttrIndex;
	private int nameAndTypeIndex;

	public CPInvokeDynamicInfo() {
		super(CONSTANT_InvokeDynamic);
	}

	public int getBootstrapMethodAttrIndex() {
		return bootstrapMethodAttrIndex;
	}

	public void setBootstrapMethodAttrIndex(int bootstrapMethodAttrIndex) {
		this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
	}

	public int getNameAndTypeIndex() {
		return nameAndTypeIndex;
	}

	public void setNameAndTypeIndex(int nameAndTypeIndex) {
		this.nameAndTypeIndex = nameAndTypeIndex;
	}

	@Override
	public String toString() {
		return "CPInvokeDynamicInfo [bootstrapMethodAttrIndex=" + bootstrapMethodAttrIndex
				+ ", nameAndTypeIndex=" + nameAndTypeIndex + ", toString()=" + super.toString() + "]";
	}

}
