package cz.fit.cvut.bvm;

import java.io.IOException;

public class Main {

	private static String getClassname(String arg) {
		if (arg.lastIndexOf("/") != -1) {

			return arg.substring(arg.lastIndexOf("/") + 1)
					.replace(".class", "");
		} else
			return arg.substring(arg.lastIndexOf("\\") + 1).replace(".class",
					"");
	}

	private static String getClasspath(String arg) {
		if (arg.lastIndexOf("/") != -1) {
			return arg.substring(0, arg.lastIndexOf("/") + 1);
		} else {
			return arg.substring(0, arg.lastIndexOf("\\") + 1);
		}
	}

	/**
	 * Spusteni VM
	 * @param args[0] - zkompilovany .class soubor
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		if (args.length < 1) {
			System.out.println("Chybi vstupni soubor jako parametr.");
			return;
		}

		String classpath = getClasspath(args[0]);
		String classname = getClassname(args[0]);
		BestVirtualMachine bvm = new BestVirtualMachine();

		String[] arguments = new String[args.length - 1];
		for (int i = 1; i < args.length; i++) {
			arguments[i - 1] = args[i];
		}

		System.out.println("Best Virtual Machine Starting!");

		bvm.loadProgram(classpath, classname, arguments);
		System.out.println("\n------------------------------------->");
		System.out.println("Spoustim kod tridy: " + classname);
		bvm.run();

		System.out.println("Best Virtual Machine end!");
	}
}
