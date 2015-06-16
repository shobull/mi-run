package cz.fit.cvut.bvm.structures;

public class BVMEntity {

	protected int modifiers;

	private static final int ACC_PUBLIC = 0x0001;
	private static final int ACC_FINAL = 0x0010;
	private static final int ACC_PRIVATE = 0x0002;
	private static final int ACC_PROTECTED = 0x0004;
	private static final int ACC_STATIC = 0x0008;

	public int getModifiers() {
		return modifiers;
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	public boolean isFinal() {
		return (this.modifiers & ACC_FINAL) == ACC_FINAL;
	}

	public boolean isPublic() {
		return (this.modifiers & ACC_PUBLIC) == ACC_PUBLIC;
	}

	public boolean isPrivate() {
		return (this.modifiers & ACC_PRIVATE) == ACC_PRIVATE;
	}

	public boolean isProtected() {
		return (this.modifiers & ACC_PROTECTED) == ACC_PROTECTED;
	}

	public boolean isStatic() {
		return (this.modifiers & ACC_STATIC) == ACC_STATIC;
	}

	@Override
	public String toString() {
		return "BVMEntity [modifiers=" + modifiers + ", isFinal()=" + isFinal()
				+ ", isPublic()=" + isPublic() + ", isPrivate()=" + isPrivate()
				+ ", isProtected()=" + isProtected() + ", isStatic()="
				+ isStatic() + "]";
	}

}
