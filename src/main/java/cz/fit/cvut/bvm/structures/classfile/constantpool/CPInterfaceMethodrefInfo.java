package cz.fit.cvut.bvm.structures.classfile.constantpool;

public class CPInterfaceMethodrefInfo extends CPItem {
	private int classIndex;
	private int nameAndTypeIndex;

	public CPInterfaceMethodrefInfo() {
		super(CONSTANT_InterfaceMethodref);
	}

	public int getClassIndex() {
		return classIndex;
	}

	public void setClassIndex(int classIndex) {
		this.classIndex = classIndex;
	}

	public int getNameAndTypeIndex() {
		return nameAndTypeIndex;
	}

	public void setNameAndTypeIndex(int nameAndTypeIndex) {
		this.nameAndTypeIndex = nameAndTypeIndex;
	}

	@Override
	public String toString() {
		return "CPInterfaceMethodrefInfo [classIndex=" + classIndex + ", nameAndTypeIndex="
				+ nameAndTypeIndex + ", toString()=" + super.toString() + "]";
	}
}
