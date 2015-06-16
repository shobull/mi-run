package cz.fit.cvut.bvm.structures;

import org.junit.Test;

import static cz.fit.cvut.bvm.structures.TestUtils.firstByte;
import static cz.fit.cvut.bvm.structures.TestUtils.secondByte;
import static org.junit.Assert.*;

public class IfACmpTest {

	ExecutionFrame executeBytecode(byte[] bytecode) {
		BVMMethod m = new BVMMethod();
		m.setBytecode(bytecode);
		ExecutionFrame ef = new ExecutionFrame(m);
		TestUtils.executeFrame(ef);
		return ef;
	}


	@Test
	public void ifeq() {

		ExecutionFrame ef;

		// iconst_0, ifeq
		byte[] bytecode = {(byte) 3, (byte) 153,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode);
		assertEquals("should jump", 46, ef.getPc());

		// iconst_1, ifeq,
		byte[] bytecode2 = {(byte) 4, (byte) 153,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode2);
		assertEquals("should not jump", 4, ef.getPc());

		//bipush, -1, ifeq
		byte[] bytecode3 = {(byte) 16, (byte) -1, (byte) 153,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode3);
		assertEquals("should not jump", 5, ef.getPc());
	}

	@Test
	public void ifne() {

		ExecutionFrame ef;

		// iconst_0, ifeq
		byte[] bytecode = {(byte) 3, (byte) 154,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode);
		assertEquals("should not jump", 4, ef.getPc());

		// iconst_1, ifeq,
		byte[] bytecode2 = {(byte) 4, (byte) 154,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode2);
		assertEquals("should jump", 46, ef.getPc());

		//bipush, -1, ifeq
		byte[] bytecode3 = {(byte) 16, (byte) -1, (byte) 154,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode3);
		assertEquals("should jump", 47, ef.getPc());
	}

	@Test
	public void iflt() { // if less then

		ExecutionFrame ef;

		// iconst_0, iflt
		byte[] bytecode = {(byte) 3, (byte) 155,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode);
		assertEquals("should not jump", 4, ef.getPc());

		// iconst_1, iflt
		byte[] bytecode2 = {(byte) 4, (byte) 155,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode2);
		assertEquals("should not jump", 4, ef.getPc());

		//bipush, -1, iflt
		byte[] bytecode3 = {(byte) 16, (byte) -1, (byte) 155,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode3);
		assertEquals("should jump", 47, ef.getPc());
	}

	@Test
	public void ifge() { // if greater or equal

		ExecutionFrame ef;

		// iconst_0, ifge
		byte[] bytecode = {(byte) 3, (byte) 156,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode);
		assertEquals("should jump", 46, ef.getPc());

		// iconst_1, ifge
		byte[] bytecode2 = {(byte) 4, (byte) 156,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode2);
		assertEquals("should jump", 46, ef.getPc());

		//bipush, -1, ifge
		byte[] bytecode3 = {(byte) 16, (byte) -1, (byte) 156,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode3);
		assertEquals("should not jump", 5, ef.getPc());
	}

	@Test
	public void ifgt() { // if greater then

		ExecutionFrame ef;

		// iconst_0, ifgt
		byte[] bytecode = {(byte) 3, (byte) 157,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode);
		assertEquals("should not jump", 4, ef.getPc());

		// iconst_1, ifgt
		byte[] bytecode2 = {(byte) 4, (byte) 157,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode2);
		assertEquals("should jump", 46, ef.getPc());

		//bipush, -1, ifgt
		byte[] bytecode3 = {(byte) 16, (byte) -1, (byte) 157,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode3);
		assertEquals("should not jump", 5, ef.getPc());
	}

	@Test
	public void ifle() { // if less or equal

		ExecutionFrame ef;

		// iconst_0, ifle
		byte[] bytecode = {(byte) 3, (byte) 158,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode);
		assertEquals("should jump", 46, ef.getPc());

		// iconst_1, ifle
		byte[] bytecode2 = {(byte) 4, (byte) 158,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode2);
		assertEquals("should not jump", 4, ef.getPc());

		//bipush, -1, ifle
		byte[] bytecode3 = {(byte) 16, (byte) -1, (byte) 158,
				secondByte((short) 45), firstByte((short) 45)};

		ef = executeBytecode(bytecode3);
		assertEquals("should jump", 47, ef.getPc());
	}
}
