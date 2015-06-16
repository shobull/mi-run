package cz.fit.cvut.bvm.structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Stack;

import org.junit.Test;

import cz.fit.cvut.bvm.structures.operands.BVMFloat;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;
import cz.fit.cvut.bvm.structures.operands.BVMLong;
import cz.fit.cvut.bvm.structures.operands.BVMValue;

public class XALoadTest {
		
	@Test
	public void baload()
	{
		// newarray, byte, dup, 
		// iconst_3, iconst_5, bastore, iconst_3, baload 
		byte[] bytecode = {(byte)188, (byte)8, (byte)89, 
				(byte)6, (byte)8, (byte)84, (byte)6, (byte)51};
		// index = 3
		// value = 5
		
		ExecutionFrame ef;
		
		BVMInteger arrayLength = new BVMInteger(42);

		BVMMethod m = new BVMMethod();
		m.setBytecode(bytecode);
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(arrayLength);
		TestUtils.executeFrame(ef);
		
		BVMValue val = ef.getOperandStack().peek();
		assertTrue("on stack should be Int", val instanceof BVMInteger);
		assertEquals(5, ((BVMInteger)val).getVal());
	}
	
	@Test
	public void saload()
	{
		// newarray, short, dup, 
		// iconst_3, iconst_5, sastore, iconst_3, saload 
		byte[] bytecode = {(byte)188, (byte)9, (byte)89, 
				(byte)6, (byte)8, (byte)86, (byte)6, (byte)53};
		// index = 3
		// value = 5
		
		ExecutionFrame ef;
		
		BVMInteger arrayLength = new BVMInteger(42);

		BVMMethod m = new BVMMethod();
		m.setBytecode(bytecode);
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(arrayLength);
		TestUtils.executeFrame(ef);
		
		BVMValue val = ef.getOperandStack().peek();
		assertTrue("on stack should be Int", val instanceof BVMInteger);
		assertEquals(5, ((BVMInteger)val).getVal());
	}
	
	@Test
	public void caload()
	{
		// newarray, char, dup, 
		// iconst_3, iconst_5, castore, iconst_3, caload 
		byte[] bytecode = {(byte)188, (byte)5, (byte)89, 
				(byte)6, (byte)8, (byte)85, (byte)6, (byte)52};
		// index = 3
		// value = 5
		
		ExecutionFrame ef;
		
		BVMInteger arrayLength = new BVMInteger(42);

		BVMMethod m = new BVMMethod();
		m.setBytecode(bytecode);
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(arrayLength);
		TestUtils.executeFrame(ef);
		
		BVMValue val = ef.getOperandStack().peek();
		assertTrue("on stack should be Int", val instanceof BVMInteger);
		assertEquals(5, ((BVMInteger)val).getVal());
	}
	
	@Test
	public void iaload()
	{
		// newarray, int, dup, iconst_3, iconst_5, iastore, 
		//  iconst_3, iaload 
		byte[] bytecode = {(byte)188, (byte)10, (byte)89, 
				(byte)6, (byte)8, (byte)79, (byte)6, (byte)46};
		// index = 3
		// value = 5
		
		ExecutionFrame ef;
		
		BVMInteger arrayLength = new BVMInteger(42);

		BVMMethod m = new BVMMethod();
		m.setBytecode(bytecode);
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(arrayLength);
		TestUtils.executeFrame(ef);
		
		BVMValue val = ef.getOperandStack().peek();
		assertTrue("on stack should be Int", val instanceof BVMInteger);
		assertEquals(5, ((BVMInteger)val).getVal());
	}
	
	@Test
	public void laload()
	{
		// newarray, long, dup, 
		// iconst_3, lconst_1, lastore, iconst_3, laload 
		byte[] bytecode = {(byte)188, (byte)11, (byte)89, 
				(byte)6, (byte)10, (byte)80, (byte)6, (byte)47};
		// index = 3
		// value = 1
		
		ExecutionFrame ef;
		
		BVMInteger arrayLength = new BVMInteger(42);
		
		BVMMethod m = new BVMMethod();
		m.setBytecode(bytecode);
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(arrayLength);
		TestUtils.executeFrame(ef);
		
		BVMValue val = ef.getOperandStack().peek();
		assertTrue("on stack should be long", val instanceof BVMLong);
		assertEquals(1, ((BVMLong)val).getVal());
	}
	
	@Test
	public void faload()
	{
		// newarray, float, dup, 
		// iconst_3, fconst_2, fastore, iconst_3, faload 
		byte[] bytecode = {(byte)188, (byte)6, (byte)89, 
				(byte)6, (byte)13, (byte)81, (byte)6, (byte)48};
		// index = 3
		// value = 2
		
		ExecutionFrame ef;
		
		BVMInteger arrayLength = new BVMInteger(42);
		
		BVMMethod m = new BVMMethod();
		m.setBytecode(bytecode);
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(arrayLength);
		TestUtils.executeFrame(ef);
		
		BVMValue val = ef.getOperandStack().peek();
		assertTrue("on stack should be float", val instanceof BVMFloat);
		assertEquals(2, ((BVMFloat)val).getVal(), 0.0000000001);
	}
}
