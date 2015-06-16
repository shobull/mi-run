package cz.fit.cvut.bvm.structures.classfile.constantpool;

public class CPDoubleInfo extends CPItem {

	private double value;

	public CPDoubleInfo() {
		super(CONSTANT_Double);
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CPDoubleInfo [value=" + value + ", toString()=" + super.toString() + "]";
	}

}
