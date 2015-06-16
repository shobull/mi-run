package cz.fit.cvut.bvm.structures.classfile.methods;

import java.util.ArrayList;
import java.util.Arrays;

import cz.fit.cvut.bvm.structures.classfile.attributes.AttributeItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPUtf8Info;

public class MethodItem {

	private int accessFlags;
	private int nameIndex; // Jiz nepotrebny
	private String name;
	private int descriptorIndex; // Jiz nepotrebny
	private String descriptor;
	private int attributesCnt;
	private AttributeItem[] attributes;

	public int getAccessFlags() {
		return accessFlags;
	}

	public void setAccessFlags(int accessFlags) {
		this.accessFlags = accessFlags;
	}

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

	public AttributeItem[] getAttributes() {
		return attributes;
	}

	public void setAttributes(AttributeItem[] attributes) {
		this.attributes = attributes;
	}

	public void allocateAttributes() {
		this.attributes = new AttributeItem[attributesCnt];
	}

	public void addAttribute(int index, AttributeItem attribute) {
		this.attributes[index] = attribute;
	}

	/** ------------------------- METHODS -------------------------- */

	/**
	 * Vrati vsechny AttributeItem dane metody se jmenem z parametru
	 * 
	 * @param name
	 *            Nazev atributu
	 */
	public ArrayList<AttributeItem> getAttributesByName(String name, CPItem[] cPItems) {
		ArrayList<AttributeItem> attributeItems = new ArrayList<AttributeItem>();

		for (int i = 0; i < this.attributes.length; i++) {
			CPUtf8Info cpUtf8Info = (CPUtf8Info) cPItems[this.attributes[i].getAttributeNameIndex()];
			if (cpUtf8Info.getValue().equals(name)) {
				attributeItems.add(this.attributes[i]);
			}
		}

		return attributeItems;
	}

	@Override
	public String toString() {
		return "MethodItem [accessFlags=" + accessFlags + ", name=" + name + ", descriptor=" + descriptor
				+ ", attributesCnt=" + attributesCnt + ", attributes=" + Arrays.toString(attributes)
				+ "]";
	}

}
