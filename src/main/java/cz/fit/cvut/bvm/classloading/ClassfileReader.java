package cz.fit.cvut.bvm.classloading;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import cz.fit.cvut.bvm.exceptions.ClassfileValidationException;
import cz.fit.cvut.bvm.structures.classfile.attributes.AttributeItem;
import cz.fit.cvut.bvm.structures.classfile.attributes.CodeAttribute;
import cz.fit.cvut.bvm.structures.classfile.attributes.CodeExceptionTableItem;
import cz.fit.cvut.bvm.structures.classfile.attributes.ConstantValueAttribute;
import cz.fit.cvut.bvm.structures.classfile.attributes.SourceFileAttribute;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPClassInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPDoubleInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPFieldrefInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPFloatInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPIntegerInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPInvokeDynamicInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPLongInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPMethodHandleInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPMethodTypeInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPMethodrefInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPNameAndTypeInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPStringInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPUtf8Info;
import cz.fit.cvut.bvm.structures.classfile.fields.FieldItem;
import cz.fit.cvut.bvm.structures.classfile.methods.MethodItem;
import cz.fit.cvut.bvm.utils.ConstantPoolUtils;

public class ClassfileReader {

	private ClassFile classFile;

	/**
	 * Metoda pro nacteni a parsovani classfile
	 */
	public ClassFile readClassfile(File f) throws IOException {

		classFile = new ClassFile();

		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);

		int c;

		/* CA FE BA BE */
		parseMagic(dis);

		/* Minor verze */
		c = parseMinorVersion(dis);
		classFile.setMinorVersion(c);

		/* Major verze */
		c = parseMajorVersion(dis);
		classFile.setMajorVersion(c);

		/* Velikost CP */
		c = parseContantPoolCnt(dis);
		classFile.setConstantPoolCnt(c);

		/* CP o velikosti: getConstantPoolCnt() - 1 */
		classFile.alloateCPItems();
		for (int i = 1; i < classFile.getConstantPoolCnt(); i++) {
			CPItem cpItem = parseCPItem(dis);
			classFile.addCPItem(i, cpItem);
			if (cpItem instanceof CPLongInfo || cpItem instanceof CPDoubleInfo) {
				/*
				 * Dle specifikace: pokud na i-te pozici v constant pool je
				 * CONSTANT_Double_info nebo CONSTANT_Long_info, tak dalsi
				 * platna polozka je na pozici i+2 (pozice i+1 se preskoci)
				 */
				i++;
			}
			System.out.println("Polozek v CP: " + i);
		}

		/* Access flags */
		c = parseAccessFlags(dis);
		classFile.setAccessFlags(c);

		/* This class */
		c = parseThisClass(dis);
		classFile.setThisClass(c);
		classFile.setClassname(ConstantPoolUtils.getClassname(
				classFile.getcPItems(), c));

		/* Super class */
		c = parseSuperClass(dis);
		classFile.setSuperClass(c);
		if (c != 0) {
			// Pokud superclass == 0, tak se musi jednat o java.lang.Object
			classFile.setSuperClassname(ConstantPoolUtils.getClassname(
					classFile.getcPItems(), c));
		} else {
			classFile.setSuperClassname("");
		}

		/* Interfaces count */
		c = parseInterfacesCnt(dis);
		classFile.setInterfacesCnt(c);
		classFile.allocateInterfaces();

		/* Nacitani jednotlivych interface */
		for (int i = 0; i < classFile.getInterfacesCnt(); i++) {
			c = parseInterface(dis);
			classFile.addInterface(i, c);
		}

		/* Fields count */
		c = parseFieldsCnt(dis);
		classFile.setFieldsCnt(c);
		classFile.allocateFields();

		/* Fields */
		for (int i = 0; i < classFile.getFieldsCnt(); i++) {
			FieldItem fieldItem = parseFieldItem(dis);
			classFile.addField(i, fieldItem);
		}

		/* Methods count */
		c = parseMethodsCnt(dis);
		classFile.setMethodsCnt(c);
		classFile.allocateMethods();

		/* Methods */
		for (int i = 0; i < classFile.getMethodsCnt(); i++) {
			MethodItem methodItem = parseMethodItem(dis);
			classFile.addMethod(i, methodItem);
		}

		/* Attributes count */
		c = parseAttributesCnt(dis);
		classFile.setAttributesCnt(c);
		classFile.allocateAttributes();

		/* Attributes */
		for (int i = 0; i < classFile.getAttributesCnt(); i++) {
			AttributeItem attributeItem = parseAttributeItem(dis);
			classFile.addAttribute(i, attributeItem);
		}

		return classFile;

		/* ---------------- ! CLASSFILE NACTEN ! ------------------- */
	}

	/**
	 * Na zaklade nacteneho tagu (typ polozky constant pool) se vytvori polozka
	 * constant pool
	 */
	private CPItem parseCPItem(DataInputStream dis) throws IOException {
		/*
		 * Ziskam tag struktury cp_info (1 byte), ktery rika, jakeho typu je
		 * nasledujici polozka v CP
		 */
		int tag = dis.readByte();

		CPItem cpItem = null;

		switch (tag) {
		case 7:
			// parse CONSTANT_Class
			cpItem = parseConstantClass(dis);
			break;
		case 9:
			// parse CONSTANT_Fieldref
			cpItem = parseConstantFieldref(dis);
			break;
		case 10:
			// parse CONSTANT_Methodref
			cpItem = parseConstantMethodref(dis);
			break;
		case 11:
			// parse CONSTANT_InterfaceMethodred
			cpItem = parseConstantInterfaceMethodred(dis);
			break;
		case 8:
			// parse CONSTANT_String
			cpItem = parseConstantString(dis);
			break;
		case 3:
			// parse CONSTANT_Integer
			cpItem = parseConstantInteger(dis);
			break;
		case 4:
			// parse CONSTANT_Float
			cpItem = parseConstantFloat(dis);
			break;
		case 5:
			// parse CONSTANT_Long
			cpItem = parseConstantLong(dis);
			break;
		case 6:
			// parse CONSTANT_Double
			cpItem = parseConstantDouble(dis);
			break;
		case 12:
			// parse NameAndType
			cpItem = parseConstantNameAndType(dis);
			break;
		case 1:
			// parse CONSTANT_Utf8
			cpItem = parseConstantUtf8(dis);
			break;
		case 15:
			// parse CONSTANT_MethodHandle
			cpItem = parseConstantMethodHandle(dis);
			break;
		case 16:
			// parse CONSTANT_MethodType
			cpItem = parseConstantMethodType(dis);
			break;
		case 18:
			// parse CONSTANT_InvokeDynamic
			cpItem = parseConstantInvokeDynamic(dis);
			break;
		default:
			throw new ClassfileValidationException(
					"Neplatný tag položky v constant poolu (" + tag + ")");
		}

		System.out.println("Nactena polozka CP: " + cpItem);

		return cpItem;

	}

	/**
	 * Vytvori polozku field
	 */
	private FieldItem parseFieldItem(DataInputStream dis) throws IOException {
		FieldItem fieldItem = new FieldItem();

		short c = dis.readShort();
		fieldItem.setAccessFlags(c);

		c = dis.readShort();
		fieldItem.setNameIndex(c);
		fieldItem.setName(ConstantPoolUtils
				.getCPUtf8(classFile.getcPItems(), c).getValue());

		c = dis.readShort();
		fieldItem.setDescriptorIndex(c);
		fieldItem.setDescriptor(ConstantPoolUtils.getCPUtf8(
				classFile.getcPItems(), c).getValue());

		c = dis.readShort();
		fieldItem.setAttributesCnt(c);
		fieldItem.allocateAttributes();

		for (int i = 0; i < fieldItem.getAttributesCnt(); i++) {
			AttributeItem attributeItem = parseAttributeItem(dis);
			fieldItem.addAttribute(i, attributeItem);
		}

		System.out.println("Nacetl jsem field: " + fieldItem);

		return fieldItem;
	}

	/**
	 * Parsuje metodu
	 */
	private MethodItem parseMethodItem(DataInputStream dis) throws IOException {
		MethodItem methodItem = new MethodItem();
		int c = dis.readShort();
		methodItem.setAccessFlags(c);

		c = dis.readShort();
		methodItem.setNameIndex(c);
		methodItem.setName(ConstantPoolUtils.getCPUtf8(classFile.getcPItems(),
				c).getValue());

		c = dis.readShort();
		methodItem.setDescriptorIndex(c);
		methodItem.setDescriptor(ConstantPoolUtils.getCPUtf8(
				classFile.getcPItems(), c).getValue());

		c = dis.readShort();
		methodItem.setAttributesCnt(c);
		methodItem.allocateAttributes();

		for (int i = 0; i < methodItem.getAttributesCnt(); i++) {
			AttributeItem attributeItem = parseAttributeItem(dis);
			methodItem.addAttribute(i, attributeItem);
		}

		System.out.println("Nacetl jsem metodu: " + methodItem);

		return methodItem;
	}

	/**
	 * Vytvori polozku AttributeItem (soucast FieldItem)
	 */
	private AttributeItem parseAttributeItem(DataInputStream dis)
			throws IOException {
		// Nactu attributeNameIndex
		int c = dis.readShort();
		AttributeItem attributeItem = null;

		String attributeName = ConstantPoolUtils.getCPUtf8(
				classFile.getcPItems(), c).getValue();
		attributeItem = resolveAttributeItemType(attributeName, dis);
		attributeItem.setAttributeNameIndex(c);
		attributeItem.setAttributeName(attributeName);

		System.out.println("Nacetl jsem attribut: " + attributeItem);

		return attributeItem;
	}

	private AttributeItem resolveAttributeItemType(String attributeName,
			DataInputStream dis) throws IOException {
		AttributeItem attributeItem = null;

		/* TMP */
		byte[] arr;
		int c;
		/* TMP */

		switch (attributeName) {
		case "Code":
			attributeItem = parseCodeAttribute(dis);
			break;
		case "SourceFile":
			attributeItem = parseSourceFileAttribute(dis);
			break;
		case "ConstantValue":
			attributeItem = parseConstantValueAttribute(dis);
			break;
		case "LineNumberTable":
			attributeItem = new AttributeItem();
			c = dis.readInt();
			attributeItem.setAttributeLength(c);
			arr = new byte[c];
			dis.read(arr);
			break;
		default:
			attributeItem = new AttributeItem();
			c = dis.readInt();
			attributeItem.setAttributeLength(c);
			arr = new byte[c];
			dis.read(arr);
		}

		attributeItem.setAttributeName(attributeName);
		return attributeItem;
	}

	/**
	 * Nacte atribut SourceFile
	 */
	private SourceFileAttribute parseSourceFileAttribute(DataInputStream dis)
			throws IOException {
		SourceFileAttribute sourceFileAttribute = new SourceFileAttribute();

		int c = dis.readInt();
		sourceFileAttribute.setAttributeLength(c);

		c = dis.readShort();
		sourceFileAttribute.setSourceFileIndex((short) c);
		sourceFileAttribute.setSourceFile(ConstantPoolUtils.getCPUtf8(
				classFile.getcPItems(), c).getValue());

		return sourceFileAttribute;
	}

	/**
	 * Nacte atribut ConstantValue
	 */
	private ConstantValueAttribute parseConstantValueAttribute(
			DataInputStream dis) throws IOException {
		ConstantValueAttribute constantValueAttribute = new ConstantValueAttribute();

		int c = dis.readInt();
		constantValueAttribute.setAttributeLength(c);

		// hodnota atributu - pokud je to string, tak je to pouze odkaz do CP na
		// Utf8Info!!!
		c = dis.readShort();
		constantValueAttribute.setValue(classFile.getcPItems()[c]);

		return constantValueAttribute;
	}

	/**
	 * Nacte atribut CodeAttribute
	 */
	private CodeAttribute parseCodeAttribute(DataInputStream dis)
			throws IOException {
		CodeAttribute codeAttribute = new CodeAttribute();

		int c = dis.readInt();
		codeAttribute.setAttributeLength(c);

		c = dis.readShort();
		codeAttribute.setMaxStack((short) c);

		c = dis.readShort();
		codeAttribute.setMaxLocals((short) c);

		c = dis.readInt();
		codeAttribute.setCodeLength(c);
		codeAttribute.allocateCode();
		byte[] arr = new byte[codeAttribute.getCodeLength()];
		dis.read(arr);
		codeAttribute.setCode(arr);

		c = dis.readShort();
		codeAttribute.setExceptionTableLength((short) c);

		codeAttribute.allocateExceptionTableItems();
		for (int i = 0; i < codeAttribute.getExceptionTableLength(); i++) {
			codeAttribute.addExceptionTableItem(i,
					new CodeExceptionTableItem(dis.readShort(),
							dis.readShort(), dis.readShort(), dis.readShort()));
		}

		c = dis.readShort();
		codeAttribute.setAttributesCnt((short) c);
		codeAttribute.allocateAttributes();

		for (int i = 0; i < codeAttribute.getAttributesCnt(); i++) {
			AttributeItem attributeItem = parseAttributeItem(dis);
			codeAttribute.addAttribute(i, attributeItem);
		}

		return codeAttribute;
	}

	/**
	 * Zkontroluje 1. az 4. byte classfile. Tyto byty musi dle specifikace
	 * obsahovat hodnoty 0xCA, 0xFE, 0xBA, 0xBE
	 */
	private void parseMagic(DataInputStream dis) throws IOException {
		int magic = dis.readInt();

		if (0xCAFEBABE != magic) {
			throw new ClassfileValidationException(
					"Chyba při čtení classfile - magic (1. byte musí být 0xCA)");
		}

		System.out.println("Načetl jsem 0xCA 0xFE 0xBA 0xBE");
	}

	/**
	 * Z 5. a 6. bytu classfile nacte minor verzi
	 */
	private int parseMinorVersion(DataInputStream dis) throws IOException {
		short c = dis.readShort();
		System.out.println("Nacetl jsem minor version: " + c);
		return c;
	}

	/**
	 * Ze 7. a 8. bytu classfile nacte major verzi
	 */
	private int parseMajorVersion(DataInputStream dis) throws IOException {
		short c = dis.readShort();
		System.out.println("Nacetl jsem major version: " + c);
		return c;
	}

	/**
	 * Z 9. bytu nacte velikost contant pool. Pocet prvku v CP je ve skutecnosti
	 * o 1 mensi
	 */
	private int parseContantPoolCnt(DataInputStream dis) throws IOException {
		short c = dis.readShort();
		System.out.println("Nastavil jsem velikost CP na " + c);
		return c;
	}

	/**
	 * Z 1. a 2. bytu po constant pool nacte access flags
	 */
	private int parseAccessFlags(DataInputStream dis) throws IOException {
		short c = dis.readShort();
		String flags = String.format("%4s", Integer.toHexString(c)).replace(
				" ", "0");
		System.out.println("Nacetl jsem access flags: 0x" + flags);
		return c;
	}

	/**
	 * Z 3. a 4. bytu po constant pool nacte this class
	 */
	private int parseThisClass(DataInputStream dis) throws IOException {
		short c = dis.readShort();
		System.out.println("Nacetl jsem this class index #" + c);
		return c;
	}

	/**
	 * Z 5. a 6. bytu po constant pool nacte super class
	 */
	private int parseSuperClass(DataInputStream dis) throws IOException {
		short c = dis.readShort();
		System.out.println("Nacetl jsem super class index #" + c);
		return c;
	}

	/**
	 * Z 7. a 8. bytu po constant pool nacte interfaces count
	 */
	private int parseInterfacesCnt(DataInputStream dis) throws IOException {
		short c = dis.readShort();
		System.out.println("Nacetl jsem interfaces cnt " + c);
		return c;
	}

	/**
	 * Od 9. byte po constant pool se nachazi interfacesCnt polozek interface
	 * (1byte indexu do constant pool).
	 */
	private int parseInterface(DataInputStream dis) throws IOException {
		short c = dis.readShort();
		System.out.println("Nacetl jsem interface: " + c);
		return c;
	}

	/**
	 * Po datech s interface nasleduje pocet fields
	 */
	private int parseFieldsCnt(DataInputStream dis) throws IOException {
		short c = dis.readShort();
		System.out.println("Nacetl jsem pocet fields " + c);
		return c;
	}

	/**
	 * Po datech s fields nasleduje pocet method
	 */
	private int parseMethodsCnt(DataInputStream dis) throws IOException {
		short c = dis.readShort();
		System.out.println("Nacetl jsem pocet metod " + c);
		return c;
	}

	/**
	 * Po metodach nacitam pocet atributu
	 */
	private int parseAttributesCnt(DataInputStream dis) throws IOException {
		short c = dis.readShort();
		System.out.println("Nacetl jsem pocet atributu " + c);
		return c;
	}

	/**
	 * CONSTANT_Utf8 (tag=1) - Nacitam polozku constant pool
	 * 
	 * Metoda rovnou nacte dekoduje z bytu UTF-8 String. Polozka CP se ve
	 * skutecnosti sklada z: <br />
	 * - u2 length <br />
	 * - u1 bytes[length] <br />
	 */
	private CPUtf8Info parseConstantUtf8(DataInputStream dis)
			throws IOException {
		CPUtf8Info cpUtf8Info = new CPUtf8Info();
		String val = dis.readUTF();
		cpUtf8Info.setValue(val);
		return cpUtf8Info;
	}

	/**
	 * CONSTANT_Integer (tag=3) - Nacitam polozku constant pool
	 */
	private CPIntegerInfo parseConstantInteger(DataInputStream dis)
			throws IOException {
		int c = dis.readInt();
		CPIntegerInfo cpIntegerInfo = new CPIntegerInfo();
		cpIntegerInfo.setValue(c);
		return cpIntegerInfo;
	}

	/**
	 * CONSTANT_Float (tag=4) - Nacitam polozku constant pool
	 */
	private CPFloatInfo parseConstantFloat(DataInputStream dis)
			throws IOException {
		float f = dis.readFloat();
		CPFloatInfo cpFloatInfo = new CPFloatInfo();
		cpFloatInfo.setValue(f);
		return cpFloatInfo;
	}

	/**
	 * CONSTANT_Long (tag=5) - Nacitam polozku constant pool
	 */
	private CPLongInfo parseConstantLong(DataInputStream dis)
			throws IOException {
		long f = dis.readLong();
		CPLongInfo cpLongInfo = new CPLongInfo();
		cpLongInfo.setValue(f);
		return cpLongInfo;
	}

	/**
	 * CONSTANT_Double (tag=6) - Nacitam polozku constant pool
	 */
	private CPDoubleInfo parseConstantDouble(DataInputStream dis)
			throws IOException {
		double d = dis.readDouble();
		CPDoubleInfo cpDoubleInfo = new CPDoubleInfo();
		cpDoubleInfo.setValue(d);
		return cpDoubleInfo;
	}

	/**
	 * CONSTANT_Class (tag=7) - Nacitam polozku constant pool
	 */
	private CPClassInfo parseConstantClass(DataInputStream dis)
			throws IOException {
		short c = dis.readShort();
		CPClassInfo cpClassInfo = new CPClassInfo();
		cpClassInfo.setNameIndex(c);
		return cpClassInfo;
	}

	/**
	 * CONSTANT_String (tag=8) - Nacitam polozku constant pool
	 */
	private CPStringInfo parseConstantString(DataInputStream dis)
			throws IOException {
		short c = dis.readShort();
		CPStringInfo cpStringInfo = new CPStringInfo();
		cpStringInfo.setStringIndex(c);
		return cpStringInfo;
	}

	/**
	 * CONSTANT_Fieldref (tag=9) - Nacitam polozku constant pool
	 */
	private CPFieldrefInfo parseConstantFieldref(DataInputStream dis)
			throws IOException {
		short c = dis.readShort();
		CPFieldrefInfo cpFieldrefInfo = new CPFieldrefInfo();
		cpFieldrefInfo.setClassIndex(c);

		c = dis.readShort();
		cpFieldrefInfo.setNameAndTypeIndex(c);

		return cpFieldrefInfo;
	}

	/**
	 * CONSTANT_Methodref (tag=10) - Nacitam polozku constant pool
	 */
	private CPMethodrefInfo parseConstantMethodref(DataInputStream dis)
			throws IOException {
		short c = dis.readShort();
		CPMethodrefInfo cpMethodrefInfo = new CPMethodrefInfo();
		cpMethodrefInfo.setClassIndex(c);

		c = dis.readShort();
		cpMethodrefInfo.setNameAndTypeIndex(c);

		return cpMethodrefInfo;
	}

	/**
	 * CONSTANT_InterfaceMethodref (tag=11) - Nacitam polozku constant pool
	 */
	private CPItem parseConstantInterfaceMethodred(DataInputStream dis)
			throws IOException {
		short c = dis.readShort();
		CPMethodrefInfo cpMethodrefInfo = new CPMethodrefInfo();
		cpMethodrefInfo.setClassIndex(c);

		c = dis.readShort();
		cpMethodrefInfo.setNameAndTypeIndex(c);

		return cpMethodrefInfo;
	}

	/**
	 * CONSTANT_NameAndType (tag=12) - Nacitam polozku constant pool
	 */
	private CPNameAndTypeInfo parseConstantNameAndType(DataInputStream dis)
			throws IOException {
		short c = dis.readShort();
		CPNameAndTypeInfo cpNameAndTypeInfo = new CPNameAndTypeInfo();
		cpNameAndTypeInfo.setNameIndex(c);

		c = dis.readShort();
		cpNameAndTypeInfo.setDescriptionIndex(c);

		return cpNameAndTypeInfo;
	}

	/**
	 * CONSTANT_MethodHandle (tag=15) - Nacitam polozku constant pool
	 */
	private CPMethodHandleInfo parseConstantMethodHandle(DataInputStream dis)
			throws IOException {
		short c = dis.readShort();
		CPMethodHandleInfo cpMethodHandleInfo = new CPMethodHandleInfo();
		cpMethodHandleInfo.setReferenceKind(c);

		c = dis.readShort();
		cpMethodHandleInfo.setReferenceIndex(c);

		return cpMethodHandleInfo;
	}

	/**
	 * CONSTANT_MethodType (tag=16) - Nacitam polozku constant pool
	 */
	private CPMethodTypeInfo parseConstantMethodType(DataInputStream dis)
			throws IOException {
		short c = dis.readShort();
		CPMethodTypeInfo cpMethodTypeInfo = new CPMethodTypeInfo();
		cpMethodTypeInfo.setDescriptorIndex(c);

		return cpMethodTypeInfo;
	}

	/**
	 * CONSTANT_InvokeDynamic (tag=18) - Nacitam polozku constant pool
	 */
	private CPInvokeDynamicInfo parseConstantInvokeDynamic(DataInputStream dis)
			throws IOException {
		short c = dis.readShort();
		CPInvokeDynamicInfo cpInvokeDynamicInfo = new CPInvokeDynamicInfo();
		cpInvokeDynamicInfo.setBootstrapMethodAttrIndex(c);

		c = dis.readShort();
		cpInvokeDynamicInfo.setNameAndTypeIndex(c);
		return cpInvokeDynamicInfo;
	}

}
