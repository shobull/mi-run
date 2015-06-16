package cz.fit.cvut.bvm.structures.operands;

import cz.fit.cvut.bvm.structures.BVMClass;

public class BVMExceptionHandler {

	private short startPc;
	private short endPc;
	private short handlerPc;
	private BVMClass catchType;

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

	public BVMClass getCatchType() {
		return catchType;
	}

	public void setCatchType(BVMClass catchType) {
		this.catchType = catchType;
	}

	public boolean isInOffset(int invokingPc) {
		return (startPc <= invokingPc && (endPc-1) >= invokingPc);
	}
	
	@Override
	public String toString() {
		return "BVMExceptionHandler [startPc=" + startPc + ", endPc=" + endPc
				+ ", handlerPc=" + handlerPc + ", catchType=" + catchType + "]";
	}

}
