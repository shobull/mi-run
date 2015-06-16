package cz.fit.cvut.bvm.structures.classfile.constantpool;


public class CPUtf8Info extends CPItem {

	private String value;

	public CPUtf8Info() {
		super(CONSTANT_Utf8);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CPUtf8Info [value=" + value + ", toString()=" + super.toString() + "]";
	}

}
