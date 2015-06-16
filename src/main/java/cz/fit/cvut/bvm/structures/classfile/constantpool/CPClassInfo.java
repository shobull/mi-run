package cz.fit.cvut.bvm.structures.classfile.constantpool;

public class CPClassInfo extends CPItem {

	private int nameIndex;

	public CPClassInfo() {
		super(CONSTANT_String);
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	@Override
	public String toString() {
		return "CPClassInfo [nameIndex=" + nameIndex + ", toString()=" + super.toString() + "]";
	}

}
