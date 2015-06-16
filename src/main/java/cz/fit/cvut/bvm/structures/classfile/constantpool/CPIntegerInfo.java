package cz.fit.cvut.bvm.structures.classfile.constantpool;

public class CPIntegerInfo extends CPItem {

	private int value;

	public CPIntegerInfo() {
		super(CONSTANT_Integer);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CPIntegerInfo [value=" + value + ", toString()=" + super.toString() + "]";
	}

}
