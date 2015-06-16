package cz.fit.cvut.bvm.structures.operands;

public class BVMLong implements BVMValue {

	private final long val;

	public BVMLong(long val) {
		this.val = val;
	}

	public long getVal() {
		return val;
	}

	public BVMLong add(BVMLong operand2) {
		return new BVMLong(this.getVal() + operand2.getVal());
	}

	public BVMLong sub(BVMLong operand2) {
		return new BVMLong(this.getVal() - operand2.getVal());
	}

	public BVMLong mul(BVMLong operand2) {
		return new BVMLong(this.getVal() * operand2.getVal());
	}

	public BVMLong div(BVMLong operand2) {
		return new BVMLong(this.getVal() / operand2.getVal());
	}

	@Override
	public BVMValue copy() {
		return new BVMLong(val);
	}

	@Override
	public String toString() {
		return "BVMLong [val=" + val + "]";
	}

}
