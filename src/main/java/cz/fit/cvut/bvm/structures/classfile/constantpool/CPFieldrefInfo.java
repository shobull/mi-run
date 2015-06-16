package cz.fit.cvut.bvm.structures.classfile.constantpool;

public class CPFieldrefInfo extends CPItem {

	private int classIndex;
	private int nameAndTypeIndex;

	public CPFieldrefInfo() {
		super(CONSTANT_Fieldref);
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
		return "CPFieldrefInfo [classIndex=" + classIndex + ", nameAndTypeIndex=" + nameAndTypeIndex
				+ ", toString()=" + super.toString() + "]";
	}

}
