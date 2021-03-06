package cz.fit.cvut.bvm.structures;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;

public class IfNoNullTest {
	
	byte firstByte(short number)
	{
		return (byte) (number & 0xFF);
	}
	
	byte secondByte(short number)
	{
		return (byte) ((number >> 8) & 0xFF);
	}
	
	@Test
	public void ifnonull() {
		
		BVMObjectRef ref1 = new BVMObjectRef(42);
		
		BVMObjectRef ref2 = new BVMObjectRef();
		
		ExecutionFrame ef;
		
		// ifnonull
		byte[] bytecode = {(byte)199, 
				secondByte((short) 45), firstByte((short)45)}; 
		
		BVMMethod m = new BVMMethod();
		m.setBytecode(bytecode);
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(ref1);
		
		TestUtils.executeFrame(ef);
		assertEquals("should jump", 45, ef.getPc());

		
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(ref2);
		
		TestUtils.executeFrame(ef);
		assertEquals("should not jump", 3, ef.getPc());
	}
}
