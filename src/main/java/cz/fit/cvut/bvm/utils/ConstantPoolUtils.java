package cz.fit.cvut.bvm.utils;

import cz.fit.cvut.bvm.exceptions.ClassfileValidationException;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPClassInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPUtf8Info;

public class ConstantPoolUtils {

	public static CPUtf8Info getCPUtf8(CPItem[] constantPool, int index) {
		if (constantPool[index] instanceof CPUtf8Info) {
			return (CPUtf8Info) constantPool[index];
		} else {
			throw new ClassfileValidationException(
					"Neplatny odkaz do constant pool z MethodItem nameIndex -> musi byt Utf8Info");
		}
	}

	public static String getClassname(CPItem[] constantPool, int classIndex) {
		CPUtf8Info cpUtf8Info;
		CPClassInfo cpClassInfo;

		if (constantPool[classIndex] instanceof CPClassInfo) {
			cpClassInfo = (CPClassInfo) constantPool[classIndex];
			cpUtf8Info = getCPUtf8(constantPool, cpClassInfo.getNameIndex());
			return cpUtf8Info.getValue();
		} else {
			throw new ClassfileValidationException(
					"Neplatny odkaz do constant pool - odkaz na class musi byt ClassInfo");
		}
	}

}
