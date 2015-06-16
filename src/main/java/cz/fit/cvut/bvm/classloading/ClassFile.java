package cz.fit.cvut.bvm.classloading;

import cz.fit.cvut.bvm.exceptions.ClassfileValidationException;
import cz.fit.cvut.bvm.structures.BVMClass;
import cz.fit.cvut.bvm.structures.BVMField;
import cz.fit.cvut.bvm.structures.BVMMethod;
import cz.fit.cvut.bvm.structures.BVMMethodArea;
import cz.fit.cvut.bvm.structures.classfile.attributes.AttributeItem;
import cz.fit.cvut.bvm.structures.classfile.attributes.CodeAttribute;
import cz.fit.cvut.bvm.structures.classfile.attributes.CodeExceptionTableItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPUtf8Info;
import cz.fit.cvut.bvm.structures.classfile.fields.FieldItem;
import cz.fit.cvut.bvm.structures.classfile.methods.MethodItem;
import cz.fit.cvut.bvm.structures.operands.BVMArrayRef;
import cz.fit.cvut.bvm.structures.operands.BVMExceptionHandler;
import cz.fit.cvut.bvm.structures.operands.BVMFloat;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;
import cz.fit.cvut.bvm.structures.operands.BVMLong;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;
import cz.fit.cvut.bvm.structures.operands.BVMValue;
import cz.fit.cvut.bvm.utils.ConstantPoolUtils;

public class ClassFile {

	private int minorVersion;
	private int majorVersion;
	private int constantPoolCnt;
	private int accessFlags;
	private int thisClass;
	private String classname;
	private int superClass;
	private String superClassname;
	private int interfacesCnt;
	private int fieldsCnt;
	private int methodsCnt;
	private int attributesCnt;

	/** Pole pro polozky constant pool. POZOR! indexovane od 1 (ne od 0) */
	private CPItem[] cPItems;

	/**
	 * Pole indexu do constant pool. Kazda polozka, kam ukazuje index interface
	 * musi byt CONSTANT_Class_info
	 */
	private int[] interfaces;

	/**
	 * Pole Fields
	 */
	private FieldItem[] fields;

	/**
	 * Pole Methods
	 */
	private MethodItem[] methods;

	/**
	 * Pole Attributes
	 */
	private AttributeItem[] attributes;

	public int getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(int minorVersion) {
		this.minorVersion = minorVersion;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public int getConstantPoolCnt() {
		return constantPoolCnt;
	}

	public void setConstantPoolCnt(int constantPoolCnt) {
		this.constantPoolCnt = constantPoolCnt;
	}

	public void alloateCPItems() {
		this.cPItems = new CPItem[constantPoolCnt];
	}

	public CPItem[] getcPItems() {
		return cPItems;
	}

	public void setcPItems(CPItem[] cPItems) {
		this.cPItems = cPItems;
	}

	public void addCPItem(int index, CPItem cPItem) {
		if (this.cPItems != null) {
			this.cPItems[index] = cPItem;
		} else {
			throw new ClassfileValidationException(
					"Chyba při přidání cpItem. Pole cPItems nebylo zatím vytvořeno.");
		}
	}

	public int getAccessFlags() {
		return accessFlags;
	}

	public void setAccessFlags(int accessFlags) {
		this.accessFlags = accessFlags;
	}

	public int getThisClass() {
		return thisClass;
	}

	public void setThisClass(int thisClass) {
		this.thisClass = thisClass;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public int getSuperClass() {
		return superClass;
	}

	public void setSuperClass(int superClass) {
		this.superClass = superClass;
	}

	public String getSuperClassname() {
		return superClassname;
	}

	public void setSuperClassname(String superClassname) {
		this.superClassname = superClassname;
	}

	public int getInterfacesCnt() {
		return interfacesCnt;
	}

	public void setInterfacesCnt(int interfacesCnt) {
		this.interfacesCnt = interfacesCnt;
	}

	public int[] getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(int[] interfaces) {
		this.interfaces = interfaces;
	}

	public void addInterface(int index, int value) {
		if (this.interfaces != null) {
			this.interfaces[index] = value;
		} else {
			throw new ClassfileValidationException(
					"Chyba při přidání interfacu. Pole pro interface nebylo zatím vytvořeno.");
		}
	}

	public void allocateInterfaces() {
		this.interfaces = new int[interfacesCnt];
	}

	public int getFieldsCnt() {
		return fieldsCnt;
	}

	public void setFieldsCnt(int fieldsCnt) {
		this.fieldsCnt = fieldsCnt;
	}

	public void addField(int index, FieldItem fieldItem) {
		if (this.fields != null) {
			this.fields[index] = fieldItem;
		} else {
			throw new ClassfileValidationException(
					"Chyba při přidání field. Pole fields nebylo zatím vytvořeno.");
		}
	}

	public void allocateFields() {
		this.fields = new FieldItem[fieldsCnt];
	}

	public FieldItem[] getFields() {
		return fields;
	}

	public void setFields(FieldItem[] fields) {
		this.fields = fields;
	}

	public int getMethodsCnt() {
		return methodsCnt;
	}

	public void setMethodsCnt(int methodsCnt) {
		this.methodsCnt = methodsCnt;
	}

	public void allocateMethods() {
		this.methods = new MethodItem[methodsCnt];
	}

	public void addMethod(int i, MethodItem methodItem) {
		this.methods[i] = methodItem;
	}

	public int getAttributesCnt() {
		return attributesCnt;
	}

	public void setAttributesCnt(int attributesCnt) {
		this.attributesCnt = attributesCnt;
	}

	public MethodItem[] getMethods() {
		return methods;
	}

	public void setMethods(MethodItem[] methods) {
		this.methods = methods;
	}

	public AttributeItem[] getAttributes() {
		return attributes;
	}

	public void setAttributes(AttributeItem[] attributes) {
		this.attributes = attributes;
	}

	public void allocateAttributes() {
		this.attributes = new AttributeItem[attributesCnt];
	}

	public void addAttribute(int index, AttributeItem attribute) {
		this.attributes[index] = attribute;
	}

	/**
	 * ----------------------------- METHODS -----------------------------------
	 */

	private BVMMethod parseMethod(int index) {
		BVMMethod bvmMethod = new BVMMethod();

		CPUtf8Info cpName = ConstantPoolUtils.getCPUtf8(this.cPItems, this.methods[index].getNameIndex());
		CPUtf8Info cpDescription = ConstantPoolUtils.getCPUtf8(this.cPItems,
				this.methods[index].getDescriptorIndex());

		int modifiers = this.methods[index].getAccessFlags();

		bvmMethod.setModifiers(modifiers);
		bvmMethod.setDescriptor(cpDescription.getValue());
		bvmMethod.setName(cpName.getValue());
		
		if (!isInterface() && !bvmMethod.isNative()) {
			CodeAttribute codeAttribute = (CodeAttribute) this.methods[index].getAttributesByName("Code",
					this.cPItems).get(0);
			bvmMethod.setBytecode(codeAttribute.getCode());
			bvmMethod.setMaxLocals(codeAttribute.getMaxLocals());
			bvmMethod.allocateExceptionTable(codeAttribute.getExceptionTableLength());
			int i = 0;
			for (CodeExceptionTableItem exceptionTableItem : codeAttribute.getExceptionTable()) {
				bvmMethod.addExceptionHandler(i, parseExceptionHandler(exceptionTableItem));
				i++;
			}

		}
		
		return bvmMethod;
	}

	private BVMExceptionHandler parseExceptionHandler(CodeExceptionTableItem exceptionTableItem) {
		BVMExceptionHandler exceptionHandler = new BVMExceptionHandler();
		exceptionHandler.setStartPc(exceptionTableItem.getStartPc());
		exceptionHandler.setEndPc(exceptionTableItem.getEndPc());
		exceptionHandler.setHandlerPc(exceptionTableItem.getHandlerPc());

		// Pokud catchType == 0, tak je to finally blok
		if (exceptionTableItem.getCatchType() != 0) {
			String classname = ConstantPoolUtils
					.getClassname(this.cPItems, exceptionTableItem.getCatchType());
			BVMClass handlerClass = BVMMethodArea.getInstance().getClassByName(classname);
			exceptionHandler.setCatchType(handlerClass);
		}

		return exceptionHandler;
	}

	private BVMField parseField(int index) {
		String descriptor = fields[index].getDescriptor();
		BVMValue value = resolveBVMValueType(descriptor);

		BVMField bvmField = new BVMField();
		bvmField.setName(this.fields[index].getName());
		bvmField.setModifiers(this.fields[index].getAccessFlags());
		bvmField.setDescriptor(this.fields[index].getDescriptor());
		bvmField.setValue(value);

		return bvmField;
	}

	private BVMValue resolveBVMValueType(String descriptor) {
		descriptor = descriptor.substring(descriptor.indexOf(")") + 1);

		switch (descriptor.substring(0, 1)) {
		case "B":
			return new BVMInteger(0);
		case "C":
			// return new BVMFloat(0.0f);
			break;
		case "D":
			// return new BVMFloat(0.0f);
			break;
		case "F":
			return new BVMFloat(0.0f);
		case "I":
			return new BVMInteger(0);
		case "J":
			return new BVMLong(0l);
		case "L":
			return new BVMObjectRef();
		case "S":
			return new BVMInteger(0);
		case "Z":
			return new BVMInteger(0);
		case "[":
			return new BVMArrayRef(null);
		default:
			break;
		}

		return null;
	}

	private boolean isInterface() {
		return (this.accessFlags & 0x0200) == 0x0200;
	}

	public BVMClass getBVMClass() {
		BVMClass bvmClass = new BVMClass();

		bvmClass.setName(this.classname);
		bvmClass.setSuperclassName(this.superClassname);
		bvmClass.setModifiers(this.accessFlags);
		bvmClass.setcPItems(this.cPItems);

		for (int i = 0; i < this.interfacesCnt; i++) {
			bvmClass.addSuperInterfaceName(ConstantPoolUtils.getClassname(
					this.cPItems, this.interfaces[i]));
		}

		for (int i = 0; i < this.fieldsCnt; i++) {
			BVMField bvmField = parseField(i);
			bvmField.setBvmClass(bvmClass);
			bvmClass.addField(bvmField.getName(), bvmField);
		}

		for (int i = 0; i < this.methods.length; i++) {
			BVMMethod bvmMethod = parseMethod(i);
			bvmMethod.setBvmClass(bvmClass);
			bvmClass.addMethod(bvmMethod.getName(), bvmMethod);
		}

		System.out.println("Mam namapovano: " + bvmClass);
		return bvmClass;
	}

	/** ---------------- UTILS ----------------- */

}
