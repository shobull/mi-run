package cz.fit.cvut.bvm.exceptions;

public class ClassfileValidationException extends RuntimeException {

	private static final long serialVersionUID = -1226682094838949288L;

	public ClassfileValidationException(String msg) {
		super(msg);
	}

}
