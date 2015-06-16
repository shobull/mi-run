package cz.fit.cvut.bvm.utils;

import cz.fit.cvut.bvm.structures.BVMClass;
import cz.fit.cvut.bvm.structures.BVMField;
import cz.fit.cvut.bvm.structures.BVMHeap;
import cz.fit.cvut.bvm.structures.BVMMethodArea;
import cz.fit.cvut.bvm.structures.operands.BVMArrayRef;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;
import cz.fit.cvut.bvm.structures.operands.BVMValue;

public class NativeUtils {
	
	private static BVMMethodArea methodArea = BVMMethodArea.getInstance();
	private static BVMHeap heap = BVMHeap.getInstance();
	
	public static BVMArrayRef createCharArrayFromString(String s) {
		BVMArrayRef charArrRef = heap.
				createPrimitiveArray((short) 5, s.length());
		BVMInteger[] arr = (BVMInteger[]) heap.getArray(charArrRef);
		copyStringInArray(s, arr);
		return charArrRef;
	}
	
	public static BVMObjectRef NativeStringToBVMString(String s) {
		BVMClass stringClass = methodArea.getClassByName("java/lang/String");
		BVMObjectRef stringRef = heap.createObject(stringClass);
		BVMValue[] stringInstance = heap.getObject(stringRef);
		
		BVMField field = stringClass.getFields().get("value");
		int fieldIndex = stringClass.getFieldIndex(field);
		
		BVMArrayRef arrRef = 
				NativeUtils.createCharArrayFromString(s);
		stringInstance[fieldIndex] = arrRef;
		return stringRef;
	}
	
	
	public static void copyStringInArray(String s, BVMInteger[] arr) {
		char[] input = s.toCharArray();
		for (int i = 0; i < input.length; i++) {
			arr[i] = new BVMInteger(input[i]);
		}
	}

	public static String getNativeStringFromRef(BVMObjectRef strRef, String prefix) {		
		BVMValue[] fields = heap.getObject(strRef);
		
		BVMClass stringClass = methodArea.getClassByName("java/lang/String");		
		BVMField field = stringClass.getFields().get("value");
		int fieldIndex = stringClass.getFieldIndex(field);
		
		BVMArrayRef arrRef = (BVMArrayRef) fields[fieldIndex];
		BVMInteger[] chars = (BVMInteger[]) heap.getArray(arrRef);
		
		StringBuilder b = new StringBuilder(prefix);
		for (BVMInteger i : chars) {
			b.append((char)i.getVal());
		}
		return b.toString();
	}
		
}
