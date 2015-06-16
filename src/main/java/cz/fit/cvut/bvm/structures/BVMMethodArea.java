package cz.fit.cvut.bvm.structures;

import java.util.HashMap;
import java.util.Map;

import cz.fit.cvut.bvm.classloading.ClassBuilder;

public class BVMMethodArea {

	private static BVMMethodArea INSTANCE = new BVMMethodArea();

	private ClassBuilder classBuilder = new ClassBuilder();
	private Map<String, BVMClass> methodArea = new HashMap<String, BVMClass>();

	private BVMMethodArea() {
	};

	public static BVMMethodArea getInstance() {
		return INSTANCE;
	}

	public BVMClass loadFirstClass(String classpath, String classname) {
		classBuilder.setClasspath(classpath);
		BVMClass bvmClass = loadClass(classname);
		return bvmClass;
	}

	private BVMClass loadClass(String classname) {
		BVMClass bvmClass = classBuilder.loadClass(classname);

		if (bvmClass.getSuperclassName().length() > 0) {
			BVMClass parentClass = getClassByName(bvmClass.getSuperclassName());
			bvmClass.setSuperclass(parentClass);
		}

		for (String si : bvmClass.getSuperInterfaceNames()) {
			BVMClass superInterface = getClassByName(si);
			bvmClass.addSuperInterface(superInterface);
		}

		methodArea.put(bvmClass.getName(), bvmClass);
		System.out.println("-> NACETL JSEM TRIDU: " + classname);
		
		return bvmClass;
	}

	public BVMClass getClassByName(String name) {
		if (methodArea.containsKey(name)) {
			return methodArea.get(name);
		} else {
			return loadClass(name);
		}
	}
	
	public BVMClass getThrowableClass() {
		return getClassByName("java/lang/Throwable");
	}
	
	public BVMClass getObjectClass() {
		return getClassByName("java/lang/Object");
	}

	public void printMethodArea() {
		for (String key : this.methodArea.keySet()) {
			System.out.println("- " + key);
		}
	}

}
