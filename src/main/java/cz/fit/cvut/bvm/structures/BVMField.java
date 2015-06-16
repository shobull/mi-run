package cz.fit.cvut.bvm.structures;

import cz.fit.cvut.bvm.structures.operands.BVMValue;

public class BVMField extends BVMEntity {

	private static final int ACC_VOLATILE = 0x0040;
	private static final int ACC_TRANSIENT = 0x0080;
	private static final int ACC_SYNTHETIC = 0x1000;
	private static final int ACC_ENUM = 0x4000;

	/**
	 * Reference to class
	 */
	private BVMClass bvmClass;

	/**
	 * The field's name
	 */
	private String name;

	/**
	 * The field's type
	 */
	private String descriptor;

	/**
	 * The field's value
	 */
	private BVMValue value;

	public void setBvmClass(BVMClass bvmClass) {
		this.bvmClass = bvmClass;
	}

	public BVMClass getBvmClass() {
		return bvmClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	public boolean isClassref() {
		return descriptor.startsWith("L");
	}

	public boolean isPrimitiveArray() {
		return descriptor.startsWith("[") && !descriptor.startsWith("[L");
	}
	
	public boolean isClassrefArray() {
		return descriptor.startsWith("[L");
	}

	public BVMValue getValue() {
		return value;
	}

	public void setValue(BVMValue value) {
		this.value = value;
	}

	public boolean isVolatile() {
		return (modifiers & ACC_VOLATILE) == ACC_VOLATILE;
	}

	public boolean isEnum() {
		return (modifiers & ACC_ENUM) == ACC_ENUM;
	}

	public boolean isSynthetic() {
		return (modifiers & ACC_SYNTHETIC) == ACC_SYNTHETIC;
	}

	public boolean isTransient() {
		return (modifiers & ACC_TRANSIENT) == ACC_TRANSIENT;
	}

	@Override
	public String toString() {
		return "BVMField [name=" + name + ", descriptor=" + descriptor
				+ ", value=" + value + ", toString()=" + super.toString() + "]";
	}

}
