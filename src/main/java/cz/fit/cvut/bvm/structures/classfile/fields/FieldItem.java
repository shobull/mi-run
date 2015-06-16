package cz.fit.cvut.bvm.structures.classfile.fields;

import cz.fit.cvut.bvm.structures.classfile.attributes.AttributeItem;
import cz.fit.cvut.bvm.utils.PrintUtils;

public class FieldItem {

	private int accessFlags;
	private int nameIndex; // Nepotrebny
	private String name;
	private int descriptorIndex; // Nepotrebny
	private String descriptor;
	private int attributesCnt;
	private AttributeItem[] attributes;

	public int getNameIndex() {
		return nameIndex;
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDescriptorIndex() {
		return descriptorIndex;
	}

	public void setDescriptorIndex(int descriptorIndex) {
		this.descriptorIndex = descriptorIndex;
	}

	public String getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	public int getAttributesCnt() {
		return attributesCnt;
	}

	public void setAttributesCnt(int attributesCnt) {
		this.attributesCnt = attributesCnt;
	}

	public int getAccessFlags() {
		return accessFlags;
	}

	public void setAccessFlags(int accessFlags) {
		this.accessFlags = accessFlags;
	}

	public AttributeItem[] getAttributes() {
		return attributes;
	}

	public void setAttributes(AttributeItem[] attributes) {
		this.attributes = attributes;
	}

	public void allocateAttributes() {
		this.attributes = new AttributeItem[this.attributesCnt];
	}

	public void addAttribute(int index, AttributeItem attribute) {
		this.attributes[index] = attribute;
	}

	@Override
	public String toString() {
		String flags = PrintUtils.printHexStyle(accessFlags);
		return "FieldItem [access_flags=" + flags + ", name=" + name + ", descriptor="
				+ descriptor + ", attributesCnt=" + attributesCnt + "]";
	}

}
