package cz.fit.cvut.bvm.structures.classfile.attributes;

import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;

public class ConstantValueAttribute extends AttributeItem {

	private CPItem value;

	public CPItem getValue() {
		return value;
	}

	public void setValue(CPItem value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ConstantValueAttribute [value=" + value + ", toString()="
				+ super.toString() + "]";
	}
	
}
