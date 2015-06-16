package cz.fit.cvut.bvm.classloading;

import java.io.File;
import java.io.IOException;

import cz.fit.cvut.bvm.structures.BVMClass;

public class ClassBuilder {

	private String classpath;

	private ClassfileReader classFileReader = new ClassfileReader();

	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}

	public BVMClass loadClass(String classname) {
		String fullClassPath = this.classpath + classname + ".class";
		File f = new File(fullClassPath);
		if (!f.exists()) {
			throw new RuntimeException("Soubor \"" + fullClassPath + "\" nenalezen");
		}

		try {
			// Nacte classfile do struktury ClassFile
			System.out.println("\n-> ZACINAM NACITAT TRIDU: " + classname);
			ClassFile classfile = classFileReader.readClassfile(f);
			BVMClass bvmClass = classfile.getBVMClass();
			return bvmClass;
		} catch (IOException e) {
			throw new RuntimeException("problem pri cteni souboru " + fullClassPath);
		}
	}
}
