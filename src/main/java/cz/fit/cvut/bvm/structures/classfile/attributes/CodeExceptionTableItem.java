package cz.fit.cvut.bvm.structures.classfile.attributes;

public class CodeExceptionTableItem {

	private short startPc;
	private short endPc;
	private short handlerPc;
	private short catchType;

	public CodeExceptionTableItem(short startPc, short endPc, short handlerPc,
			short catchType) {
		super();
		this.startPc = startPc;
		this.endPc = endPc;
		this.handlerPc = handlerPc;
		this.catchType = catchType;
	}

	public short getStartPc() {
		return startPc;
	}

	public void setStartPc(short startPc) {
		this.startPc = startPc;
	}

	public short getEndPc() {
		return endPc;
	}

	public void setEndPc(short endPc) {
		this.endPc = endPc;
	}

	public short getHandlerPc() {
		return handlerPc;
	}

	public void setHandlerPc(short handlerPc) {
		this.handlerPc = handlerPc;
	}

	public short getCatchType() {
		return catchType;
	}

	public void setCatchType(short catch_type) {
		this.catchType = catch_type;
	}

	@Override
	public String toString() {
		return "CodeExceptionTableItem [startPc=" + startPc + ", endPc="
				+ endPc + ", handlerPc=" + handlerPc + ", catchType="
				+ catchType + "]";
	}

}
