package cz.fit.cvut.bvm.structures.operands;

public class BVMDouble implements BVMValue {

	private final double val;

	public BVMDouble(double val) {
		this.val = val;
	}

	public double getVal() {
		return val;
	}

	public BVMDouble add(BVMDouble operand) {
		return new BVMDouble(this.getVal() + operand.getVal());
	}

	public BVMDouble sub(BVMDouble operand) {
		return new BVMDouble(this.getVal() - operand.getVal());
	}

	public BVMDouble mul(BVMDouble operand) {
		return new BVMDouble(this.getVal() * operand.getVal());
	}

	public BVMDouble div(BVMDouble operand) {
		return new BVMDouble(this.getVal() / operand.getVal());
	}

	@Override
	public BVMDouble copy() {
		return new BVMDouble(this.val);
	}

	@Override
	public String toString() {
		return "BVMDouble [val=" + val + "]";
	}

}
