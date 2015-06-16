package cz.fit.cvut.bvm.structures;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;

import cz.fit.cvut.bvm.structures.operands.BVMFloat;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;

public class FCmpXTest {
		
	@Test
	public void fcmpg() {
		
		ExecutionFrame ef;
		BVMInteger result;
		
		BVMFloat float1 = new BVMFloat(1);
		BVMFloat floatNan = new BVMFloat(Float.NaN);
		
		//fcmpg
		fcmpX((byte)150);
		
		// fcmpg
		byte[] bytecode = {(byte)150}; 
		
		
		BVMMethod m = new BVMMethod();
		m.setBytecode(bytecode);
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(float1);
		ef.getOperandStack().push(floatNan);
		TestUtils.executeFrame(ef);
		
		result = (BVMInteger) ef.getOperandStack().peek();
		assertEquals("fcmpg comparsion with NaN", 1, result.getVal());
	}
	
	@Test
	public void fcmpl() {
		
		ExecutionFrame ef;
		BVMInteger result;
		
		BVMFloat float1 = new BVMFloat(1);
		BVMFloat floatNan = new BVMFloat(Float.NaN);
		
		//fcmpl
		fcmpX((byte)149);
		
		// fcmpl
		byte[] bytecode = {(byte)149}; 
		
		
		BVMMethod m = new BVMMethod();
		m.setBytecode(bytecode);
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(float1);
		ef.getOperandStack().push(floatNan);
		TestUtils.executeFrame(ef);
		
		result = (BVMInteger) ef.getOperandStack().peek();
		assertEquals("fcmpl comparsion with NaN", -1, result.getVal());
	}
	
	private void fcmpX(byte instruction) {
		
		byte[] bytecode = {instruction}; 
		
		ExecutionFrame ef;
		BVMInteger result;
		
		BVMFloat float1 = new BVMFloat(1);
		BVMFloat floatMinus1 = new BVMFloat(-1);
		BVMFloat float0 = new BVMFloat(0);
		
		BVMMethod m = new BVMMethod();
		m.setBytecode(bytecode);
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(floatMinus1);
		ef.getOperandStack().push(float0);
		TestUtils.executeFrame(ef);
		
		result = (BVMInteger) ef.getOperandStack().peek();
		assertEquals("value1 < value2", -1, result.getVal());
		
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(float1);
		ef.getOperandStack().push(float1);
		TestUtils.executeFrame(ef);
		
		result = (BVMInteger) ef.getOperandStack().peek();
		assertEquals("value1 == value2", 0, result.getVal());
		
		ef = new ExecutionFrame(m); 
		ef.getOperandStack().push(float1);
		ef.getOperandStack().push(floatMinus1);
		TestUtils.executeFrame(ef);
		
		result = (BVMInteger) ef.getOperandStack().peek();
		assertEquals("value1 > value2", 1, result.getVal());
	}
}
