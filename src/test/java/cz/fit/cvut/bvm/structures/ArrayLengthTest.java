package cz.fit.cvut.bvm.structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Stack;

import org.junit.Test;

import cz.fit.cvut.bvm.structures.operands.BVMInteger;
import cz.fit.cvut.bvm.structures.operands.BVMValue;

public class ArrayLengthTest {
		
	@Test
	public void arraylength()
	{
		// newarray, int, arraylength
		byte[] bytecode = {(byte)188, (byte)10, (byte)190};
		
		BVMInteger arrayLength = new BVMInteger(42);
		
		ExecutionFrame ef;
		
		BVMMethod m = new BVMMethod();
		m.setBytecode(bytecode);
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(arrayLength);
		
		TestUtils.executeFrame(ef);
		
		BVMValue val = ef.getOperandStack().peek();
		assertTrue("lenght should be integer", val instanceof BVMInteger);
		assertEquals(42, ((BVMInteger)val).getVal());
	}
}
