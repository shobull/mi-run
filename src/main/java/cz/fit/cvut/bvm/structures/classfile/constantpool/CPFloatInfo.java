package cz.fit.cvut.bvm.structures.classfile.constantpool;

public class CPFloatInfo extends CPItem {

	private float value;

	public CPFloatInfo() {
		super(CONSTANT_Float);
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CPFloatInfo [value=" + value + ", toString()=" + super.toString() + "]";
	}

}
