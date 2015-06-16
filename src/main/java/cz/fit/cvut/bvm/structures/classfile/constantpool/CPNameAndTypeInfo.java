package cz.fit.cvut.bvm.structures.classfile.constantpool;

public class CPNameAndTypeInfo extends CPItem {

	private int nameIndex;
	private int descriptionIndex;

	public CPNameAndTypeInfo() {
		super(CONSTANT_NameAndType);
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	public int getDescriptionIndex() {
		return descriptionIndex;
	}

	public void setDescriptionIndex(int descriptionIndex) {
		this.descriptionIndex = descriptionIndex;
	}

	@Override
	public String toString() {
		return "CPNameAndTypeInfo [nameIndex=" + nameIndex + ", descriptionIndex=" + descriptionIndex
				+ ", toString()=" + super.toString() + "]";
	}

}
