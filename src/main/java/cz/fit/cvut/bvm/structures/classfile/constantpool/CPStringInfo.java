package cz.fit.cvut.bvm.structures.classfile.constantpool;

public class CPStringInfo extends CPItem {

	private int stringIndex;

	public CPStringInfo() {
		super(CONSTANT_String);
	}

	public int getStringIndex() {
		return stringIndex;
	}

	public void setStringIndex(int stringIndex) {
		this.stringIndex = stringIndex;
	}

	@Override
	public String toString() {
		return "CPStringInfo [stringIndex=" + stringIndex + ", toString()=" + super.toString() + "]";
	}

}
