package cz.fit.cvut.bvm.structures.classfile.constantpool;

public class CPLongInfo extends CPItem {

	private long value;

	public CPLongInfo() {
		super(CONSTANT_Long);
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CPLongInfo [value=" + value + ", toString()=" + super.toString() + "]";
	}

}
