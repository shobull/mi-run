package cz.fit.cvut.bvm.structures.operands;

public class BVMInteger implements BVMValue {

	private final int val;

	public BVMInteger(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}

	public BVMInteger add(BVMInteger operand) {
		return new BVMInteger(this.getVal() + operand.getVal());
	}

	public BVMInteger sub(BVMInteger operand) {
		return new BVMInteger(this.getVal() - operand.getVal());
	}

	public BVMInteger mul(BVMInteger operand) {
		return new BVMInteger(this.getVal() * operand.getVal());
	}

	public BVMInteger div(BVMInteger operand) {
		return new BVMInteger(this.getVal() / operand.getVal());
	}

	public BVMInteger neg() {
		return new BVMInteger(0 - this.getVal());
	}
	
	@Override
	public BVMInteger copy() {
		return new BVMInteger(this.val);
	}

	@Override
	public String toString() {
		return "BVMInteger [val=" + val + "]";
	}

}
