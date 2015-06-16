package cz.fit.cvut.bvm.utils;

public class BVMUtils {

	public static int getParametersCnt(String descriptor) {
		String tempDescr = descriptor.substring(descriptor.indexOf("(") + 1,
				descriptor.indexOf(")"));
		// Odstranim znak pro pole
		tempDescr = tempDescr.replace("[", "");
		// Nahradim classu jednim znakem, abych mohl pouzit .length()
		tempDescr = tempDescr.replaceAll("L.*?;", "X");
		return tempDescr.length();
	}

}
