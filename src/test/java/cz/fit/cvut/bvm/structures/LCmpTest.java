package cz.fit.cvut.bvm.structures;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;

import cz.fit.cvut.bvm.structures.operands.BVMInteger;
import cz.fit.cvut.bvm.structures.operands.BVMLong;

public class LCmpTest {
		
	@Test
	public void lcmp() {
		
		// lcmp
		byte[] bytecode = {(byte)148}; 
		
		ExecutionFrame ef;
		BVMInteger result;
		
		BVMLong long1 = new BVMLong(1);
		BVMLong longMinus1 = new BVMLong(-1);
		BVMLong long0 = new BVMLong(0);
		
		BVMMethod m = new BVMMethod();
		m.setBytecode(bytecode);
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(longMinus1);
		ef.getOperandStack().push(long0);
		TestUtils.executeFrame(ef);
		
		result = (BVMInteger) ef.getOperandStack().peek();
		assertEquals("value1 < value2", -1, result.getVal());
		
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(long1);
		ef.getOperandStack().push(long1);
		TestUtils.executeFrame(ef);
		
		result = (BVMInteger) ef.getOperandStack().peek();
		assertEquals("value1 == value2", 0, result.getVal());
		
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(long1);
		ef.getOperandStack().push(longMinus1);
		TestUtils.executeFrame(ef);
		
		result = (BVMInteger) ef.getOperandStack().peek();
		assertEquals("value1 > value2", 1, result.getVal());
	}
}
