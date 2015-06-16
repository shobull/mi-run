package cz.fit.cvut.bvm.structures;

import java.util.Stack;

public class TestUtils {
	
	public static byte firstByte(short number)
	{
		return (byte) (number & 0xFF);
	}
	
	public static byte secondByte(short number)
	{
		return (byte) ((number >> 8) & 0xFF);
	}
	
	public static void executeFrame(ExecutionFrame ef) {
		Stack<ExecutionFrame> stack = new Stack<ExecutionFrame>();
		stack.add(ef);
		ef.executeFrame(stack);
	}
}

