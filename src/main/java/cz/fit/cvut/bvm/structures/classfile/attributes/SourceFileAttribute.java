package cz.fit.cvut.bvm.structures.classfile.attributes;


public class SourceFileAttribute extends AttributeItem {

	private short sourceFileIndex; // Nepotrebny
	private String sourceFile;

	public short getSourceFileIndex() {
		return sourceFileIndex;
	}

	public void setSourceFileIndex(short sourceFileIndex) {
		this.sourceFileIndex = sourceFileIndex;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	@Override
	public String toString() {
		return "SourceFileAttribute [sourceFile=" + sourceFile + ", toString()=" + super.toString() + "]";
	}

}
