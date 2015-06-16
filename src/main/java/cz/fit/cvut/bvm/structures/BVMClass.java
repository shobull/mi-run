package cz.fit.cvut.bvm.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.operands.BVMValue;

public class BVMClass extends BVMEntity {

	private static final int ACC_INTERFACE = 0x0200;
	private static final int ACC_SUPER = 0x0020;
	private static final int ACC_ABSTRACT = 0x0400;
	private static final int ACC_ENUM = 0x4000;
	private static final int ACC_SYNTHETIC = 0x1000;
	private static final int ACC_ANNOTATION = 0x2000;

	/**
	 * The fully qualified name of the type
	 */
	private String name;

	/**
	 * The fully qualified name of the type's direct superclass (unless the type
	 * is an interface or class java.lang.Object, neither of which have a
	 * superclass)
	 */
	private String superclassName;
	private BVMClass superclass;

	/**
	 * An ordered list of the fully qualified names of any direct
	 * superinterfaces
	 */
	private List<String> superInterfaceNames = new ArrayList<String>();
	private List<BVMClass> superInterfaces = new ArrayList<BVMClass>();

	/**
	 * Constant Pool
	 */
	private CPItem[] cPItems;

	/**
	 * Field information
	 */
	private Map<String, BVMField> fields = new HashMap<String, BVMField>();

	/**
	 * Method information
	 */
	private List<BVMMethod> methods = new ArrayList<BVMMethod>();

	/**
	 * All class (static) variables declared in the type, except constants
	 */

	private boolean initialized = false;

	/**
	 * ----------------------------- GETTERS & SETTERS  -----------------------------------
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuperclassName() {
		return superclassName;
	}

	public void setSuperclassName(String superclassName) {
		this.superclassName = superclassName;
	}

	public BVMClass getSuperclass() {
		return superclass;
	}

	public void setSuperclass(BVMClass superclass) {
		this.superclass = superclass;
	}

	public List<String> getSuperInterfaceNames() {
		return superInterfaceNames;
	}

	public void addSuperInterfaceName(String superInterface) {
		this.superInterfaceNames.add(superInterface);
	}

	public void addSuperInterface(BVMClass superInterface) {
		this.superInterfaces.add(superInterface);
	}

	public void setSuperInterfaceNames(List<String> superInterfaces) {
		this.superInterfaceNames = superInterfaces;
	}

	public CPItem[] getcPItems() {
		return cPItems;
	}

	public void setcPItems(CPItem[] cPItems) {
		this.cPItems = cPItems;
	}

	public Map<String, BVMField> getFields() {
		return fields;
	}

	public void setFields(Map<String, BVMField> fields) {
		this.fields = fields;
	}

	public void addField(String key, BVMField field) {
		this.fields.put(key, field);
	}

	public void setField(String fieldname, BVMValue value) {
		fields.get(fieldname).setValue(value);
	}

	public List<BVMMethod> getMethods() {
		return methods;
	}

	public void addMethod(String key, BVMMethod method) {
		this.methods.add(method);
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public boolean isInterface() {
		return (modifiers & ACC_INTERFACE) == ACC_INTERFACE;
	}

	public boolean isSynthetic() {
		return (this.modifiers & ACC_SYNTHETIC) == ACC_SYNTHETIC;
	}

	public boolean isAnnotation() {
		return (this.modifiers & ACC_ANNOTATION) == ACC_ANNOTATION;
	}

	public boolean isAbstract() {
		return (this.modifiers & ACC_ABSTRACT) == ACC_ABSTRACT;
	}

	public boolean isEnum() {
		return (this.modifiers & ACC_ENUM) == ACC_ENUM;
	}

	public boolean isSuper() {
		return (this.modifiers & ACC_SUPER) == ACC_SUPER;
	}

	/**
	 * ----------------------------- METHODS -----------------------------------
	 */
	public BVMMethod getMainMethod() {
		BVMMethod mainMethod = getMethodByNameAndDescriptor("main",
				"([Ljava/lang/String;)V");

		if (mainMethod == null) {
			mainMethod = getMethodByNameAndDescriptor("main", "()V");
		}

		return mainMethod;
	}

	public BVMMethod getInitMethod() {
		return getMethodByNameAndDescriptor("<clinit>", "()V");
	}

	public BVMMethod getMethodByNameAndDescriptor(String name, String descriptor) {
		for (BVMMethod method : methods) {
			if (name.equals(method.getName())
					&& descriptor.equals(method.getDescriptor())) {
				return method;
			}
		}
		System.out.println("Nenalezena metoda " + name + "()");
		return null;
	}

	public boolean containsMethod(String methodName, String methodDescriptor) {
		return getMethodByNameAndDescriptor(methodName, methodDescriptor) != null;
	}

	/**
	 * Overuje viditelnost metody pro volajici tridu
	 * 
	 * @param method
	 *            Metoda jejiz viditelnost overuji
	 * @param invokingClass
	 * 
	 */
	public boolean hasAccesTo(BVMMethod method, BVMClass invokingClass) {
		if (method.isPublic()) {
			return true;
		} else if (method.isPrivate()
				&& name.equals(method.getBvmClass().getName())) {
			return true;
		} else if (method.isProtected()) {
			if (method.isStatic()) {
				if (this.isSubclassOf(method.getBvmClass())) {
					return true;
				}
			} else {
				if (invokingClass.isSubclassOf(method.getBvmClass())
						|| method.getBvmClass().isSubclassOf(invokingClass)) {
					return true;
				}
			}
		} else if (!method.isPrivate() && !method.isProtected()
				&& !method.isPublic()) {
			return true;
		}

		return false;
	}

	public boolean hasAccesTo(BVMField field, BVMClass invokingClass) {
		if (field.isPublic()) {
			return true;
		} else if (field.isPrivate()
				&& name.equals(field.getBvmClass().getName())) {
			return true;
		} else if (field.isProtected()) {
			if (field.isStatic()) {
				if (this.isSubclassOf(field.getBvmClass())) {
					return true;
				}
			} else {
				if (invokingClass.isSubclassOf(field.getBvmClass())
						|| field.getBvmClass().isSubclassOf(invokingClass)) {
					return true;
				}
			}
		} else if (!field.isPrivate() && !field.isProtected()
				&& !field.isPublic()) {
			return true;
		}

		return false;
	}

	public boolean isSubclassOf(BVMClass clazz) {
		if (name.equals(clazz.getName())) {
			return true;
		} else if (superclass == null) {
			return false;
		}
		return superclass.isSubclassOf(clazz);
	}

	public boolean isImplementationOf(BVMClass interfaze) {
		if (name.equals(interfaze.getName())) {
			return true;
		} else if (superInterfaces.isEmpty()) {
			return false;
		}
		for (BVMClass si : superInterfaces) {
			if (si.isImplementationOf(interfaze)) {
				return true;
			}
		}
		return false;
	}

	public boolean isInstanceOf(BVMClass clazz) {
		if (!clazz.isInterface()) {
			return isSubclassOf(clazz);
		}
		if (superclass != null) {
			return superclass.isInstanceOf(clazz) || isImplementationOf(clazz);
		} else {
			return isImplementationOf(clazz);
		}
	}

	public BVMField resolveField(String fieldName, String fieldDescriptor,
			String classname) {
		BVMClass bvmClass = BVMMethodArea.getInstance().getClassByName(
				classname);

		BVMClass classWithField = bvmClass.searchForField(fieldName,
				fieldDescriptor, classname);

		if (classWithField == null) {
			throw new NoSuchFieldError("Nenalezen field: " + fieldName);
		}

		BVMField lookedUpField = classWithField.getFieldByNameAndDescriptor(
				fieldName, fieldDescriptor);

		System.out.println("Field " + fieldName + " nalezen ve tride "
				+ classWithField.getName());

		if (!this.hasAccesTo(lookedUpField, bvmClass)) {
			throw new IllegalAccessError("Trida " + this.name
					+ " pozaduje pristup k fieldu " + fieldDescriptor + " "
					+ fieldName + " a nema pristup!");
		}

		return lookedUpField;
	}

	public BVMMethod resolveMethod(String methodName, String methodDescriptor,
			String classname) {

		BVMClass bvmClass = BVMMethodArea.getInstance().getClassByName(
				classname);

		BVMClass classWithMethod = bvmClass.searchForMethod(methodName,
				methodDescriptor, classname);
		if (classWithMethod == null) {
			throw new NoSuchMethodError("Nenalezena metoda: " + methodName);
		}

		BVMMethod lookedUpMethod = classWithMethod
				.getMethodByNameAndDescriptor(methodName, methodDescriptor);

		if (lookedUpMethod.isAbstract() && !classWithMethod.isAbstract()) {
			throw new AbstractMethodError("Metoda " + methodName
					+ " je abstraktni, ale trida " + classWithMethod.getName()
					+ " neni.");
		}

		if (!this.hasAccesTo(lookedUpMethod, bvmClass)) {
			throw new IllegalAccessError("Trida " + this.name
					+ " volajici metodu " + methodDescriptor + " " + methodName
					+ " k ni nema pristup!");
		}

		return lookedUpMethod;
	}

	public BVMMethod resolveInterfaceMethod(String methodName,
			String methodDescriptor, String classname) {

		BVMClass bvmClass = BVMMethodArea.getInstance().getClassByName(
				classname);

		if (!bvmClass.isInterface()) {
			throw new IncompatibleClassChangeError("Invokeinterface - typ "
					+ name + " neni interface!");
		}

		BVMClass actualClass = bvmClass;
		while (true) {
			if (actualClass.containsMethod(methodName, methodDescriptor)) {
				return actualClass.getMethodByNameAndDescriptor(methodName,
						methodDescriptor);
			} else {
				if (superclass != null && superclass.isInterface()) {
					actualClass = superclass;
				} else {
					break;
				}
			}
		}

		return null;
	}

	/**
	 * Rekurzivni vyhledavani metody ve tride, jejich nadtridach
	 */
	public BVMClass searchForMethod(String methodName, String methodDescriptor,
			String invokedClassname) {
		System.out.println("Hledam metodu: " + methodName + " v " + name);

		if (name.equals(invokedClassname) && this.isInterface()) {
			throw new IncompatibleClassChangeError(
					"Metodu nesmim hledat rovnou v interface.");
		}

		if (containsMethod(methodName, methodDescriptor)) {
			return this;
		}

		if (superclass != null) {
			if (superclass.searchForMethod(methodName, methodDescriptor,
					invokedClassname) != null) {
				return superclass;
			}
		}

		BVMClass bvmClass = null;
		for (String superinterface : this.superInterfaceNames) {
			bvmClass = BVMMethodArea.getInstance().getClassByName(
					superinterface);
			if (bvmClass.searchForMethod(methodName, methodDescriptor,
					invokedClassname) != null) {
				return bvmClass;
			}
		}

		return null;
	}

	public void initialize(Stack<ExecutionFrame> executionStack) {
		if (initialized) {
			return;
		}
		initialized = true;

		System.out.println("--Startuji inicializaci tridy \"" + this.getName()
				+ "\"");
		BVMMethod initMethod = getInitMethod();
		if (initMethod == null) {
			System.out.println("--Koncim inicializaci tridy " + this.getName());
			return;
		}

		ExecutionFrame initFrame = new ExecutionFrame(initMethod);
		executionStack.push(initFrame);
		initFrame.executeFrame(executionStack);
		System.out.println("--Koncim inicializaci tridy " + this.getName());
	}

	public int getFieldsCnt() {
		int fieldCount = fields.size();

		if (superclass != null) {
			fieldCount += superclass.getFieldsCnt();
		}

		return fieldCount;
	}

	public List<BVMField> listAllFields() {
		List<BVMField> allFields = new ArrayList<BVMField>();

		for (String fieldName : fields.keySet()) {
			BVMField field = fields.get(fieldName);
			if (field.isClassref() || field.isClassrefArray()
					|| field.isPrimitiveArray()) {
				allFields.add(field);
			}
		}

		if (superclass != null) {
			allFields.addAll(superclass.listAllFields());
		}
		return allFields;
	}

	public Integer getFieldIndex(BVMField field) {
		int index = 0;
		if (containsField(field.getName(), field.getDescriptor())) {
			for (String key : fields.keySet()) {
				if (field.getName().equals(key)) {
					return index;
				}
				index++;
			}
		} else {
			index += fields.size();
			index += superclass.getFieldIndex(field);
		}

		return index;
	}

	public BVMField getFieldByIndex(int index) {
		BVMClass actualClass = this;
		while (index > (actualClass.getFields().size() - 1)) {
			index -= actualClass.getFields().size();
			actualClass = actualClass.getSuperclass();
		}

		int i = 0;
		for (String key : actualClass.fields.keySet()) {
			if (i == index) {
				return actualClass.fields.get(key);
			}
			i++;
		}
		return null;
	}

	private boolean containsField(String fieldname, String fieldDescriptor) {
		return getFieldByNameAndDescriptor(fieldname, fieldDescriptor) != null;
	}

	public BVMField getFieldByNameAndDescriptor(String name, String descriptor) {
		if (fields.containsKey(name)) {
			BVMField field = fields.get(name);
			if (descriptor.equals(field.getDescriptor())) {
				return field;
			}
		}
		return null;
	}

	/**
	 * Overuje viditelnost fieldu pro tridu z parametru
	 * 
	 * @param fieldname
	 *            nazev fieldu
	 * @param invokedClassname
	 *            nazev tridy, pro kterou zjistuji viditelnost
	 */
	private boolean hasRightToAccessField(String fieldname,
			String invokedClassname) {

		if (fields.get(fieldname).isPublic()) {
			return true;
		} else if (fields.get(fieldname).isPrivate()
				&& invokedClassname.equals(name)) {
			return true;
		} else if (fields.get(fieldname).isProtected()) {
			return true;
		} else if (!fields.get(fieldname).isPrivate()
				&& !fields.get(fieldname).isPublic()
				&& !fields.get(fieldname).isProtected()) {
			return true;
		}
		return false;
	}

	/**
	 * Rekurzivni vyhledavani fieldu ve tride, jejich interfacech a nadtridach
	 */
	public BVMClass searchForField(String fieldname, String fieldDescriptor,
			String invokedClassname) {

		if (containsField(fieldname, fieldDescriptor)
				&& hasRightToAccessField(fieldname, invokedClassname)) {
			return this;
		}

		BVMClass bvmClass = null;
		for (String superinterface : this.superInterfaceNames) {
			bvmClass = BVMMethodArea.getInstance().getClassByName(
					superinterface);
			if (bvmClass.searchForField(fieldname, fieldDescriptor,
					invokedClassname) != null) {
				return bvmClass;
			}
		}

		if (superclass != null) {
			if (superclass.searchForField(fieldname, fieldDescriptor,
					invokedClassname) != null) {
				return superclass;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "BVMClass [name=" + name + ", superclassName=" + superclassName
				+ ", isInterface=" + isInterface() + ", isPublic=" + isPublic()
				+ ", isAbstract=" + isAbstract() + ", superInterfaces="
				+ superInterfaceNames + ", cPItems=" + Arrays.toString(cPItems)
				+ ", fields=" + fields + ", methods=" + methods + "]";
	}

}
