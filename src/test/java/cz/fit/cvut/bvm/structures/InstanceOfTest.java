package cz.fit.cvut.bvm.structures;

import static cz.fit.cvut.bvm.structures.TestUtils.firstByte;
import static cz.fit.cvut.bvm.structures.TestUtils.secondByte;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.suppressConstructor;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import cz.fit.cvut.bvm.structures.classfile.constantpool.CPClassInfo;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPItem;
import cz.fit.cvut.bvm.structures.classfile.constantpool.CPUtf8Info;
import cz.fit.cvut.bvm.structures.operands.BVMInteger;
import cz.fit.cvut.bvm.structures.operands.BVMObjectRef;
import cz.fit.cvut.bvm.structures.operands.BVMValue;

@RunWith(PowerMockRunner.class)
@PrepareForTest( { BVMMethodArea.class })
public class InstanceOfTest {
		
	private static BVMClass clazz;
	private static BVMClass clazzUp;
	private static BVMClass clazzUpUp;
	
	private static BVMClass interfaze;
	private static BVMClass interfazeUpA;
	private static BVMClass interfazeUpB;
	private static BVMClass interfazeUpBUpA;
	
	@BeforeClass
	public static void createClassStructure() {
		
		interfazeUpBUpA = new BVMClass();
		interfazeUpBUpA.setModifiers(0x0200);
		interfazeUpBUpA.setName("foo/bar/InterfazeUpBUpA");
		
		interfazeUpA = new BVMClass();
		interfazeUpA.setModifiers(0x0200);
		interfazeUpA.setName("foo/bar/InterfazeUpA");
		
		interfazeUpB = new BVMClass();
		interfazeUpB.setModifiers(0x0200);
		interfazeUpB.setName("foo/bar/InterfazeUpB");
		interfazeUpB.addSuperInterface(interfazeUpBUpA);
		
		interfaze = new BVMClass();
		interfaze.setModifiers(0x0200);
		interfaze.setName("foo/bar/Interfaze");
		interfaze.addSuperInterface(interfazeUpA);
		interfaze.addSuperInterface(interfazeUpB);

		
        clazzUpUp = new BVMClass();
        clazzUpUp.setName("foo/bar/CoolClassUpUp");

        
        clazzUp = new BVMClass();
        clazzUp.setSuperclass(clazzUpUp);
        clazzUp.setName("foo/bar/CoolClassUp");
        clazzUp.addSuperInterface(interfaze);
        
        clazz = new BVMClass();
        clazz.setSuperclass(clazzUp);
        clazz.setName("foo/bar/CoolClass");
		
	}
	
	
	
	@Before
	public void initSingletonMethodArea() {
		//Tell powermock to not to invoke constructor
        //import import static org.powermock.api.easymock.PowerMock.suppressConstructor;
        suppressConstructor(BVMMethodArea.class);
        //mock static
        mockStatic(BVMMethodArea.class);
        //create mock for Singleton
        BVMMethodArea mockSingleton = createMock(BVMMethodArea.class);
        //set expectation for getInstance()
        expect(BVMMethodArea.getInstance()).andReturn(mockSingleton).anyTimes();
    //set expectation for getHostName()
        expect(mockSingleton.getClassByName(clazz.getName())).andReturn(clazz);
        expect(mockSingleton.getClassByName(clazzUp.getName())).andReturn(clazzUp);
        expect(mockSingleton.getClassByName(clazzUpUp.getName())).andReturn(clazzUpUp);
        expect(mockSingleton.getClassByName(interfaze.getName())).andReturn(interfaze);
        expect(mockSingleton.getClassByName(interfazeUpA.getName())).andReturn(interfazeUpA);
        expect(mockSingleton.getClassByName(interfazeUpB.getName())).andReturn(interfazeUpB);
        expect(mockSingleton.getClassByName(interfazeUpBUpA.getName())).andReturn(interfazeUpBUpA);

        replay(BVMMethodArea.class);
        replay(mockSingleton);
	}
	
	
	
	@Test
	public void ClassInstanceofClassTrivial() {

		BVMMethod currentMethod = cretaeMethodWithClass(clazz.getName());
		
		BVMClass objectClass = new BVMClass();
		objectClass.setName(clazz.getName());
		
		BVMObjectRef objectRef = new BVMObjectRef(666);
		objectRef.setClassRef(objectClass);
		//objectRef -> objectClass -> className
		
		
		ExecutionFrame ef = new ExecutionFrame(currentMethod);
		ef.getOperandStack().push(objectRef);
		
		TestUtils.executeFrame(ef);
		
		BVMValue val = ef.getOperandStack().peek();
		assertTrue("result should be integer", val instanceof BVMInteger);
		assertEquals(1, ((BVMInteger)val).getVal());		
	}
	
	@Test
	public void NullInstanceoffClass() {

		BVMMethod currentMethod = cretaeMethodWithClass(clazz.getName());
				
		BVMObjectRef objectRef = new BVMObjectRef();		
		
		ExecutionFrame ef = new ExecutionFrame(currentMethod);
		ef.getOperandStack().push(objectRef);
		
		TestUtils.executeFrame(ef);
		
		BVMValue val = ef.getOperandStack().peek();
		assertTrue("result should be integer", val instanceof BVMInteger);
		assertEquals(0, ((BVMInteger)val).getVal());		
	}

	
	@Test
	public void ClassInstanceofClassTranzitivity() {
		
		BVMMethod currentMethod = cretaeMethodWithClass(clazzUpUp.getName());
		
		BVMObjectRef objectRef = new BVMObjectRef(666);
		objectRef.setClassRef(clazz);
		
		ExecutionFrame ef = new ExecutionFrame(currentMethod);
		ef.getOperandStack().push(objectRef);
		
		TestUtils.executeFrame(ef);
		
		BVMValue val = ef.getOperandStack().peek();
		assertTrue("result should be integer", val instanceof BVMInteger);
		assertEquals(1, ((BVMInteger)val).getVal());		
	}
	
	@Test
	public void ClassInstanceofClassInterface() {
		
		BVMMethod currentMethod = cretaeMethodWithClass(interfazeUpBUpA.getName());
		
		BVMObjectRef objectRef = new BVMObjectRef(666);
		objectRef.setClassRef(clazz);
		
		ExecutionFrame ef = new ExecutionFrame(currentMethod);
		ef.getOperandStack().push(objectRef);
		
		TestUtils.executeFrame(ef);
		
		BVMValue val = ef.getOperandStack().peek();
		assertTrue("result should be integer", val instanceof BVMInteger);
		assertEquals(1, ((BVMInteger)val).getVal());		
	}
	
	@Test
	public void InterfaceInstanceofInterface() {
		
		BVMMethod currentMethod = cretaeMethodWithClass(interfazeUpBUpA.getName());
		
		BVMObjectRef objectRef = new BVMObjectRef(666);
		objectRef.setClassRef(interfaze);
		
		ExecutionFrame ef = new ExecutionFrame(currentMethod);
		ef.getOperandStack().push(objectRef);
		
		TestUtils.executeFrame(ef);
		
		BVMValue val = ef.getOperandStack().peek();
		assertTrue("result should be integer", val instanceof BVMInteger);
		assertEquals(1, ((BVMInteger)val).getVal());		
	}

	@Test
	public void ClassInstanceofObject() {
		
		BVMMethod currentMethod = cretaeMethodWithClass("java/lang/Object");
		
		BVMObjectRef objectRef = new BVMObjectRef(666);
		objectRef.setClassRef(clazz);
		
		ExecutionFrame ef = new ExecutionFrame(currentMethod);
		ef.getOperandStack().push(objectRef);
		
		TestUtils.executeFrame(ef);
		
		BVMValue val = ef.getOperandStack().peek();
		assertTrue("result should be integer", val instanceof BVMInteger);
		assertEquals(1, ((BVMInteger)val).getVal());		
	}
	
	@Test
	public void InterfaceInstanceofObject() {
		
		BVMMethod currentMethod = cretaeMethodWithClass("java/lang/Object");
		
		BVMObjectRef objectRef = new BVMObjectRef(666);
		objectRef.setClassRef(interfaze);
		
		ExecutionFrame ef = new ExecutionFrame(currentMethod);
		ef.getOperandStack().push(objectRef);
		
		TestUtils.executeFrame(ef);
		
		BVMValue val = ef.getOperandStack().peek();
		assertTrue("result should be integer", val instanceof BVMInteger);
		assertEquals(1, ((BVMInteger)val).getVal());		
	}

	
	private BVMMethod cretaeMethodWithClass(String className) {

		// instanceof, 0
		byte[] bytecode = { (byte) 193, firstByte((byte) 0), secondByte((byte) 0) };

		CPClassInfo ClassInfo = new CPClassInfo();
		ClassInfo.setNameIndex(1);

		CPUtf8Info utf8Info = new CPUtf8Info();
		utf8Info.setValue(className);

		CPItem[] constantPool = { ClassInfo, utf8Info };

		BVMClass clazz = new BVMClass();
		clazz.setcPItems(constantPool);

		BVMMethod method = new BVMMethod();
		method.setBytecode(bytecode);
		method.setBvmClass(clazz);
		// method -> class -> cp(ClassInfo) -> cp(utf8Info) -> className
		
		return method;
	}

}
