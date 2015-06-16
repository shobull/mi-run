package cz.fit.cvut.bvm.structures.operands;

public class BVMFloat implements BVMValue {

	private final float val;

	public BVMFloat(float val) {
		this.val = val;
	}

	public float getVal() {
		return val;
	}

	public BVMFloat add(BVMFloat operand2) {
		return new BVMFloat(this.getVal() + operand2.getVal());
	}

	public BVMFloat sub(BVMFloat operand2) {
		return new BVMFloat(this.getVal() - operand2.getVal());
	}

	public BVMFloat mul(BVMFloat operand2) {
		return new BVMFloat(this.getVal() * operand2.getVal());
	}

	public BVMFloat div(BVMFloat operand2) {
		return new BVMFloat(this.getVal() / operand2.getVal());
	}

	@Override
	public BVMFloat copy() {
		return new BVMFloat(this.val);
	}

	@Override
	public String toString() {
		return "BVMFloat [val=" + val + "]";
	}

}
