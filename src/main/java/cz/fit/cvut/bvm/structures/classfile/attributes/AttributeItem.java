package cz.fit.cvut.bvm.structures.classfile.attributes;

public class AttributeItem {

	private int attributeNameIndex; // Nepotrebny
	private String attributeName;
	private int attributeLength;
	private byte[] info;

	public int getAttributeNameIndex() {
		return attributeNameIndex;
	}

	public void setAttributeNameIndex(int attributeNameIndex) {
		this.attributeNameIndex = attributeNameIndex;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public int getAttributeLength() {
		return attributeLength;
	}

	public void setAttributeLength(int attributeLength) {
		this.attributeLength = attributeLength;
	}

	public byte[] getInfo() {
		return info;
	}

	public void setInfo(byte[] info) {
		this.info = info;
	}

	public void allocateInfo() {
		this.info = new byte[this.attributeLength];
	}

	@Override
	public String toString() {
		return "AttributeItem [attributeName=" + attributeName + ", attributeLength="
				+ attributeLength + "]";
	}

}
