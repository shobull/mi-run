package cz.fit.cvut.bvm.utils;

public class PrintUtils {

	public static String printHexStyle(int number) {
		return "0x"
				+ String.format("%4s", Integer.toHexString(number)).replace(
						" ", "0");
	}

}
