package cz.fit.cvut.bvm.structures.classfile.attributes;

import java.util.Arrays;

public class CodeAttribute extends AttributeItem {

	private short maxStack;
	private short maxLocals;
	private int codeLength;
	private byte[] code;
	private short exceptionTableLength;
	private CodeExceptionTableItem[] exceptionTable;
	private short attributesCnt;
	private AttributeItem[] attributes;

	public short getMaxStack() {
		return maxStack;
	}

	public void setMaxStack(short maxStack) {
		this.maxStack = maxStack;
	}

	public short getMaxLocals() {
		return maxLocals;
	}

	public void setMaxLocals(short maxLocals) {
		this.maxLocals = maxLocals;
	}

	public int getCodeLength() {
		return codeLength;
	}

	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}

	public byte[] getCode() {
		return code;
	}

	public void allocateCode() {
		this.code = new byte[codeLength];
	}

	public void setCode(byte[] code) {
		this.code = code;
	}

	public short getExceptionTableLength() {
		return exceptionTableLength;
	}

	public void setExceptionTableLength(short exceptionTableLength) {
		this.exceptionTableLength = exceptionTableLength;
	}

	public short getAttributesCnt() {
		return attributesCnt;
	}

	public void setAttributesCnt(short attributesCnt) {
		this.attributesCnt = attributesCnt;
	}

	public void allocateAttributes() {
		this.attributes = new AttributeItem[attributesCnt];
	}

	public AttributeItem[] getAttributes() {
		return attributes;
	}

	public void setAttributes(AttributeItem[] attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(int index, AttributeItem item) {
		this.attributes[index] = item;
	}

	public CodeExceptionTableItem[] getExceptionTable() {
		return exceptionTable;
	}

	public void setExceptionTable(CodeExceptionTableItem[] exceptionTable) {
		this.exceptionTable = exceptionTable;
	}

	public void allocateExceptionTableItems() {
		this.exceptionTable = new CodeExceptionTableItem[exceptionTableLength];
	}

	public void addExceptionTableItem(int index, CodeExceptionTableItem item) {
		exceptionTable[index] = item;
	}

	@Override
	public String toString() {
		return "CodeAttribute [maxStack=" + maxStack + ", maxLocals="
				+ maxLocals + ", codeLength=" + codeLength
				+ ", exceptionTableLength=" + exceptionTableLength
				+ ", attributesCnt=" + attributesCnt + ", attributes="
				+ Arrays.toString(attributes) + ", toString()="
				+ super.toString() + "]";
	}

}
