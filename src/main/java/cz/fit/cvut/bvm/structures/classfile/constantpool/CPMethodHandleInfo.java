package cz.fit.cvut.bvm.structures.classfile.constantpool;

import cz.fit.cvut.bvm.exceptions.ClassfileValidationException;

public class CPMethodHandleInfo extends CPItem {

	/**
	 * referenceKind
	 * <ul>
	 * <li>== 1: REF_getField</li>
	 * <li>== 2: REF_getStatic</li>
	 * <li>== 3: REF_putField</li>
	 * <li>== 4: REF_putStatic</li>
	 * <li>== 5: REF_invokeVirtual</li>
	 * <li>== 6: REF_invokeStatic</li>
	 * <li>== 7: REF_invokeSpecial</li>
	 * <li>== 8: REF_newInvokeSpecial</li>
	 * <li>== 9: REF_invokeInterface</li>
	 * </ul>
	 */
	private int referenceKind;
	private int referenceIndex;

	public CPMethodHandleInfo() {
		super(CONSTANT_MethodHandle);
	}

	public int getReferenceKind() {
		return referenceKind;
	}

	public void setReferenceKind(int referenceKind) {
		if (referenceKind < 1 || referenceKind > 9) {
			throw new ClassfileValidationException("Neplatna polozka referenceKind pro CPMethodHandleInfo ("
					+ referenceKind + ") - musi byt v rozmezi 1-9");
		}
		this.referenceKind = referenceKind;
	}

	public int getReferenceIndex() {
		return referenceIndex;
	}

	public void setReferenceIndex(int referenceIndex) {
		this.referenceIndex = referenceIndex;
	}

	@Override
	public String toString() {
		return "CPMethodHandleInfo [referenceKind=" + referenceKind + ", referenceIndex=" + referenceIndex
				+ ", toString()=" + super.toString() + "]";
	}

}
