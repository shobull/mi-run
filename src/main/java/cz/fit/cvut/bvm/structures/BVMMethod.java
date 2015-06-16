package cz.fit.cvut.bvm.structures;

import java.util.Arrays;

import cz.fit.cvut.bvm.structures.operands.BVMExceptionHandler;
import cz.fit.cvut.bvm.utils.BVMUtils;

public class BVMMethod extends BVMEntity {

	private static final int ACC_ABSTRACT = 0x0400;
	private static final int ACC_SYNCHRONIZED = 0x0020;
	private static final int ACC_BRIDGE = 0x0040;
	private static final int ACC_VARARGS = 0x0080;
	private static final int ACC_STRICT = 0x0800;
	private static final int ACC_SYNTHETIC = 0x1000;
	private static final int ACC_NATIVE = 0x0100;

	/**
	 * The method's name
	 */
	private String name;

	/**
	 * The method's return type (or void)
	 */
	private String descriptor;

	/**
	 * The number and types (in order) of the method's parameters
	 */

	/**
	 * The method's bytecodes
	 */
	private byte[] bytecode;

	/**
	 * The sizes of the operand stack and local variables sections of the
	 * method's stack frame
	 */
	private int maxLocals;

	/**
	 * An exception table
	 */
	private BVMExceptionHandler[] exceptionTable;

	/**
	 * Pointer to the class of the method
	 */
	private BVMClass bvmClass;

	/**
	 * ------------------ GETTERS & SETTERS ---------------------------
	 */

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

	public int getMaxLocals() {
		return maxLocals;
	}

	public void setMaxLocals(int maxLocals) {
		this.maxLocals = maxLocals;
	}

	public byte[] getBytecode() {
		return bytecode;
	}

	public void setBytecode(byte[] bytecode) {
		this.bytecode = bytecode;
	}

	public BVMClass getBvmClass() {
		return bvmClass;
	}

	public void setBvmClass(BVMClass bvmClass) {
		this.bvmClass = bvmClass;
	}

	public void allocateExceptionTable(int length) {
		exceptionTable = new BVMExceptionHandler[length];
	}

	public BVMExceptionHandler[] getExceptionTable() {
		return exceptionTable;
	}

	public void setExceptionTable(BVMExceptionHandler[] exceptionTable) {
		this.exceptionTable = exceptionTable;
	}

	public void addExceptionHandler(int i, BVMExceptionHandler exceptionHandler) {
		this.exceptionTable[i] = exceptionHandler;
	}

	public boolean isAbstract() {
		return (this.modifiers & ACC_ABSTRACT) == ACC_ABSTRACT;
	}

	public boolean isNative() {
		return (this.modifiers & ACC_NATIVE) == ACC_NATIVE;
	}

	public boolean isSynthetic() {
		return (this.modifiers & ACC_SYNTHETIC) == ACC_SYNTHETIC;
	}

	public boolean isSynchronized() {
		return (this.modifiers & ACC_SYNCHRONIZED) == ACC_SYNCHRONIZED;
	}

	public boolean isVarargs() {
		return (this.modifiers & ACC_VARARGS) == ACC_VARARGS;
	}

	public boolean isStrict() {
		return (this.modifiers & ACC_STRICT) == ACC_STRICT;
	}

	public boolean isBridge() {
		return (this.modifiers & ACC_BRIDGE) == ACC_BRIDGE;
	}

	/** ------------------ METHODS --------------------------- */

	public boolean returnLong() {
		return descriptor.substring(descriptor.indexOf(")") + 1).equals("J");
	}

	public boolean returnIntCharShortBoolByte() {
		return descriptor.substring(descriptor.indexOf(")") + 1).equals("I")
				|| descriptor.substring(descriptor.indexOf(")") + 1)
						.equals("Z")
				|| descriptor.substring(descriptor.indexOf(")") + 1)
						.equals("S")
				|| descriptor.substring(descriptor.indexOf(")") + 1)
						.equals("C")
				|| descriptor.substring(descriptor.indexOf(")") + 1)
						.equals("B");
	}

	public boolean returnArray() {
		return descriptor.subSequence(descriptor.indexOf(")") + 1,
				descriptor.indexOf(")") + 2).equals("[");
	}

	public boolean returnReference() {
		return descriptor.subSequence(descriptor.indexOf(")") + 1,
				descriptor.indexOf(")") + 2).equals("L");
	}

	public int getParametersCnt() {
		return BVMUtils.getParametersCnt(descriptor);
	}

	public boolean isInit() {
		return "<init>".equals(name) || "<clinit>".equals(name);
	}

	@Override
	public String toString() {
		return "BVMMethod [name=" + name + ", maxLocals=" + maxLocals
				+ ", returnType=" + descriptor + ", attributesCnt="
				+ getParametersCnt() + ", bytecode="
				+ Arrays.toString(bytecode) + ", toString()="
				+ super.toString() + "]";
	}

}
