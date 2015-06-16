package cz.fit.cvut.bvm.structures;

import java.util.Iterator;
import java.util.Stack;

import cz.fit.cvut.bvm.structures.instructions.AALoadInst;
import cz.fit.cvut.bvm.structures.instructions.AAstoreInst;
import cz.fit.cvut.bvm.structures.instructions.AConstNullInstr;
import cz.fit.cvut.bvm.structures.instructions.ALoadXInst;
import cz.fit.cvut.bvm.structures.instructions.ANewArrayInst;
import cz.fit.cvut.bvm.structures.instructions.AReturnInst;
import cz.fit.cvut.bvm.structures.instructions.AStoreInst;
import cz.fit.cvut.bvm.structures.instructions.AThrowInst;
import cz.fit.cvut.bvm.structures.instructions.ArrayLengthInst;
import cz.fit.cvut.bvm.structures.instructions.BIPush;
import cz.fit.cvut.bvm.structures.instructions.BasicInstruction;
import cz.fit.cvut.bvm.structures.instructions.DupInst;
import cz.fit.cvut.bvm.structures.instructions.FALoadInst;
import cz.fit.cvut.bvm.structures.instructions.FAddInst;
import cz.fit.cvut.bvm.structures.instructions.FAstoreInst;
import cz.fit.cvut.bvm.structures.instructions.FCmpXInst;
import cz.fit.cvut.bvm.structures.instructions.FConstXInst;
import cz.fit.cvut.bvm.structures.instructions.FDivInst;
import cz.fit.cvut.bvm.structures.instructions.FLoadXInst;
import cz.fit.cvut.bvm.structures.instructions.FMullInst;
import cz.fit.cvut.bvm.structures.instructions.FStoreInst;
import cz.fit.cvut.bvm.structures.instructions.FStoreXInst;
import cz.fit.cvut.bvm.structures.instructions.FSubInst;
import cz.fit.cvut.bvm.structures.instructions.GetFieldInst;
import cz.fit.cvut.bvm.structures.instructions.GetStaticInst;
import cz.fit.cvut.bvm.structures.instructions.GoToInst;
import cz.fit.cvut.bvm.structures.instructions.I2fInst;
import cz.fit.cvut.bvm.structures.instructions.IALoadInst;
import cz.fit.cvut.bvm.structures.instructions.IAddInst;
import cz.fit.cvut.bvm.structures.instructions.IAndInst;
import cz.fit.cvut.bvm.structures.instructions.IAstoreInst;
import cz.fit.cvut.bvm.structures.instructions.IConstXInst;
import cz.fit.cvut.bvm.structures.instructions.IDivInst;
import cz.fit.cvut.bvm.structures.instructions.IIncInst;
import cz.fit.cvut.bvm.structures.instructions.ILoadXInst;
import cz.fit.cvut.bvm.structures.instructions.IMullInst;
import cz.fit.cvut.bvm.structures.instructions.INegInst;
import cz.fit.cvut.bvm.structures.instructions.IOrInst;
import cz.fit.cvut.bvm.structures.instructions.IRemInst;
import cz.fit.cvut.bvm.structures.instructions.IReturnInst;
import cz.fit.cvut.bvm.structures.instructions.IStoreInst;
import cz.fit.cvut.bvm.structures.instructions.IStoreXInst;
import cz.fit.cvut.bvm.structures.instructions.ISubInst;
import cz.fit.cvut.bvm.structures.instructions.IXorInst;
import cz.fit.cvut.bvm.structures.instructions.IfACmpInst;
import cz.fit.cvut.bvm.structures.instructions.IfICmpInst;
import cz.fit.cvut.bvm.structures.instructions.IfInst;
import cz.fit.cvut.bvm.structures.instructions.IfNoNullInst;
import cz.fit.cvut.bvm.structures.instructions.IfNullInst;
import cz.fit.cvut.bvm.structures.instructions.InstanceOfInst;
import cz.fit.cvut.bvm.structures.instructions.InvokeInterfaceInst;
import cz.fit.cvut.bvm.structures.instructions.InvokeSpecialInst;
import cz.fit.cvut.bvm.structures.instructions.InvokeStaticInst;
import cz.fit.cvut.bvm.structures.instructions.InvokeVirtualInst;
import cz.fit.cvut.bvm.structures.instructions.LALoadInst;
import cz.fit.cvut.bvm.structures.instructions.LAddInst;
import cz.fit.cvut.bvm.structures.instructions.LAstoreInst;
import cz.fit.cvut.bvm.structures.instructions.LCmpInst;
import cz.fit.cvut.bvm.structures.instructions.LConstXInst;
import cz.fit.cvut.bvm.structures.instructions.LDC2W;
import cz.fit.cvut.bvm.structures.instructions.LDCInst;
import cz.fit.cvut.bvm.structures.instructions.LDivInst;
import cz.fit.cvut.bvm.structures.instructions.LLoadInst;
import cz.fit.cvut.bvm.structures.instructions.LMullInst;
import cz.fit.cvut.bvm.structures.instructions.LReturnInst;
import cz.fit.cvut.bvm.structures.instructions.LStoreInst;
import cz.fit.cvut.bvm.structures.instructions.LSubInst;
import cz.fit.cvut.bvm.structures.instructions.NewArrayInst;
import cz.fit.cvut.bvm.structures.instructions.NewInst;
import cz.fit.cvut.bvm.structures.instructions.NopInst;
import cz.fit.cvut.bvm.structures.instructions.Pop2Inst;
import cz.fit.cvut.bvm.structures.instructions.PopInst;
import cz.fit.cvut.bvm.structures.instructions.PutFieldInst;
import cz.fit.cvut.bvm.structures.instructions.PutStaticInst;
import cz.fit.cvut.bvm.structures.instructions.ReturnInst;
import cz.fit.cvut.bvm.structures.instructions.SIPushInst;
import cz.fit.cvut.bvm.structures.operands.BVMValue;

public class ExecutionFrame {

	private Stack<BVMValue> operandStack = new Stack<BVMValue>();
	private BVMValue[] locals;
	private int pc = 0;
	private BVMMethod method;

	public ExecutionFrame(BVMMethod method) {
		this.method = method;
		this.locals = new BVMValue[method.getMaxLocals()];
	}

	/** ------------------- GETTERS & SETTERS -------------------- */

	public Stack<BVMValue> getOperandStack() {
		return this.operandStack;
	}

	public BVMMethod getMethod() {
		return this.method;
	}

	public BVMValue[] getLocals() {
		return locals;
	}
	
	public void setLocal(int index, BVMValue value) {
		locals[index] = value;
	}

	public BVMValue getLocal(int index) {
		return locals[index];
	}

	public int getPc() {
		return pc;
	}

	public int getInvokingPc() {
		return pc - 1;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	/** ------------------- METHODS -------------------- */

	public BasicInstruction fetchNextInstruction() {
		if (hasNextInstruction()) {
			return resolveInstruction(method.getBytecode()[pc++]);
		}
		return null;
	}

	private boolean hasNextInstruction() {
		return pc < method.getBytecode().length;
	}

	public void executeFrame(Stack<ExecutionFrame> executionStack) {
		BasicInstruction instruction = null;
		while (this.hasNextInstruction()) {
			instruction = executionStack.peek().fetchNextInstruction();
			if (instruction != null) {
				instruction.execute(executionStack.peek(), executionStack);
			}
		}
	}

	private BasicInstruction resolveInstruction(byte instructionCode) {
		BasicInstruction basicInstruction = null;

		int byteInt = instructionCode & 0xFF;

		switch (byteInt) {
		// CONSTANTS ///////////////////////////////////////////////////////////
		case 0x0:
			System.out.println("Nactena instrukce nop");
			basicInstruction = new NopInst();
			break;
		case 0x1:
			System.out.println("Nactena instrukce aconst_null");
			basicInstruction = new AConstNullInstr();
			break;
		case 0x2:
			System.out.println("Nactena instrukce iconst_1");
			basicInstruction = new IConstXInst(-1);
			break;
		case 0x3:
			System.out.println("Nactena instrukce iconst_0");
			basicInstruction = new IConstXInst(0);
			break;
		case 0x4:
			System.out.println("Nactena instrukce iconst_1");
			basicInstruction = new IConstXInst(1);
			break;
		case 0x5:
			System.out.println("Nactena instrukce iconst_2");
			basicInstruction = new IConstXInst(2);
			break;
		case 0x6:
			System.out.println("Nactena instrukce iconst_3");
			basicInstruction = new IConstXInst(3);
			break;
		case 0x7:
			System.out.println("Nactena instrukce iconst_4");
			basicInstruction = new IConstXInst(4);
			break;
		case 0x8:
			System.out.println("Nactena instrukce iconst_5");
			basicInstruction = new IConstXInst(5);
			break;
		case 0x9:
			System.out.println("Nactena instrukce lconst_0");
			basicInstruction = new LConstXInst(0l);
			break;
		case 0x0a:
			System.out.println("Nactena instrukce lconst_1");
			basicInstruction = new LConstXInst(1l);
			break;
		case 0x0b:
			System.out.println("Nactena instrukce fconst_0");
			basicInstruction = new FConstXInst(0);
			break;
		case 0x0c:
			System.out.println("Nactena instrukce fconst_1");
			basicInstruction = new FConstXInst(1);
			break;
		case 0x0d:
			System.out.println("Nactena instrukce fconst_2");
			basicInstruction = new FConstXInst(2);
			break;
		//case 0x0e:
			//TODO dconst_0
		//case 0x0f:
			//TODO dconst_1
		case 0x10:
			basicInstruction = new BIPush(nextByte());
			System.out.println("Nactena instrukce bipush");
			break;
		case 0x11:
			System.out.println("Nactena instrukce sipush");
			byte byte1 = method.getBytecode()[pc++];
			byte byte2 = method.getBytecode()[pc++];
			int next = (((byte1 & 0xFF) << 8) | (byte2 & 0xFF));
			basicInstruction = new SIPushInst(next);
			break;
		case 0x12:
			basicInstruction = new LDCInst(method.getBytecode()[pc++]);
			System.out.println("Nactena instrukce LDC");
			break;
		//case 0x13:
			//TODO ldc_w
		case 0x14:
			System.out.println("Nactena instrukce ldc2_w");
			basicInstruction = new LDC2W(nextTwoBytes());
			break;
			
		// LOADS ///////////////////////////////////////////////////////////////
		case 0x15:
			System.out.println("Nactena instrukce iload");
			basicInstruction = new ILoadXInst(nextByte());
			break;
		case 0x16:
			System.out.println("Nactena instrukce lload");
			basicInstruction = new LLoadInst(nextByte());
			break;
		//case 0x17:
			//TODO fload
		//case 0x18:
			//TODO dload
		case 0x19:
			basicInstruction = new ALoadXInst(nextByte());
			System.out.println("Nactena instrukce aload");
			break;
		case 0x1a:
			System.out.println("Nactena instrukce iload_0");
			basicInstruction = new ILoadXInst(0);
			break;
		case 0x1b:
			System.out.println("Nactena instrukce iload_1");
			basicInstruction = new ILoadXInst(1);
			break;
		case 0x1c:
			System.out.println("Nactena instrukce iload_2");
			basicInstruction = new ILoadXInst(2);
			break;
		case 0x1d:
			System.out.println("Nactena instrukce iload_3");
			basicInstruction = new ILoadXInst(3);
			break;
		case 0x1e:
			System.out.println("Nactena instrukce lload_0");
			basicInstruction = new LLoadInst(0);
			break;
		case 0x1f:
			System.out.println("Nactena instrukce lload_1");
			basicInstruction = new LLoadInst(1);
			break;
		case 0x20:
			System.out.println("Nactena instrukce lload_2");
			basicInstruction = new LLoadInst(2);
			break;
		case 0x21:
			System.out.println("Nactena instrukce lload_3");
			basicInstruction = new LLoadInst(3);
			break;
		case 0x22:
			basicInstruction = new FLoadXInst(0);
			System.out.println("Nactena instrukce fload_0");
			break;
		case 0x23:
			basicInstruction = new FLoadXInst(1);
			System.out.println("Nactena instrukce fload_1");
			break;
		case 0x24:
			basicInstruction = new FLoadXInst(2);
			System.out.println("Nactena instrukce fload_2");
			break;
		case 0x25:
			basicInstruction = new FLoadXInst(3);
			System.out.println("Nactena instrukce fload_3");
			break;
		//case 0x26:
			//TODO dload_0
		//case 0x27:
			//TODO dload_1
		//case 0x28:
			//TODO dload_2
		//case 0x29:
			//TODO dload_3		
		case 0x2a:
			basicInstruction = new ALoadXInst(0);
			System.out.println("Nactena instrukce aload_0");
			break;
		case 0x2b:
			basicInstruction = new ALoadXInst(1);
			System.out.println("Nactena instrukce aload_1");
			break;
		case 0x2c:
			basicInstruction = new ALoadXInst(2);
			System.out.println("Nactena instrukce aload_2");
			break;	
		case 0x2d:
			basicInstruction = new ALoadXInst(3);
			System.out.println("Nactena instrukce aload_3");
			break;
		case 0x2e:
			basicInstruction = new IALoadInst();
			System.out.println("Nactena instrukce iaload");
			break;		
		case 0x2f:
			basicInstruction = new LALoadInst();
			System.out.println("Nactena instrukce laload");
			break;
		case 0x30:
			basicInstruction = new FALoadInst();
			System.out.println("Nactena instrukce faload");
			break;
		//case 0x31:
			//TODO daload	
		case 0x32:
			basicInstruction = new AALoadInst();
			System.out.println("Nactena instrukce aaload");
			break;
		case 0x33:
			basicInstruction = new IALoadInst();
			System.out.println("Nactena instrukce baload");
			break;
		case 0x34:
			basicInstruction = new IALoadInst();
			System.out.println("Nactena instrukce caload");
			break;		
		case 0x35:
			basicInstruction = new IALoadInst();
			System.out.println("Nactena instrukce saload");
			break;
			
		// STORES //////////////////////////////////////////////////////////////
		case 0x36:
			System.out.println("Nactena instrukce istore");
			basicInstruction = new IStoreInst(nextByte());
			break;
		case 0x37:
			System.out.println("Nactena instrukce lstore");
			basicInstruction = new LStoreInst(nextByte());
			break;
		case 0x38:
			System.out.println("Nactena instrukce fstore");
			basicInstruction = new FStoreInst(nextByte());
			break;
		//case 0x39:
			//TODO dstore
		case 0x3a:
			System.out.println("Nactena instrukce astore");
			basicInstruction = new AStoreInst(nextByte());
			break;
		case 0x3b:
			System.out.println("Nactena instrukce istore_0");
			basicInstruction = new IStoreXInst(0);
			break;
		case 0x3c:
			System.out.println("Nactena instrukce istore_1");
			basicInstruction = new IStoreXInst(1);
			break;
		case 0x3d:
			System.out.println("Nactena instrukce istore_2");
			basicInstruction = new IStoreXInst(2);
			break;
		case 0x3e:
			System.out.println("Nactena instrukce istore_3");
			basicInstruction = new IStoreXInst(3);
			break;
		case 0x3f:
			System.out.println("Nactena instrukce lstore_0");
			basicInstruction = new LStoreInst(0);
			break;
		case 0x40:
			System.out.println("Nactena instrukce lstore_1");
			basicInstruction = new LStoreInst(1);
			break;
		case 0x41:
			System.out.println("Nactena instrukce lstore_2");
			basicInstruction = new LStoreInst(2);
			break;
		case 0x42:
			System.out.println("Nactena instrukce lstore_3");
			basicInstruction = new LStoreInst(3);
			break;
		case 0x43:
			System.out.println("Nactena instrukce fstore_0");
			basicInstruction = new FStoreXInst(0);
			break;
		case 0x44:
			System.out.println("Nactena instrukce fstore_1");
			basicInstruction = new FStoreXInst(1);
			break;
		case 0x45:
			System.out.println("Nactena instrukce fstore_2");
			basicInstruction = new FStoreXInst(2);
			break;
		case 0x46:
			System.out.println("Nactena instrukce fstore_3");
			basicInstruction = new FStoreXInst(3);
			break;
		//case 0x47:
			//TODO dstore_0	
		//case 0x48:
			//TODO dstore_1	
		//case 0x49:
			//TODO dstore_2
		//case 0x4a:
			//TODO dstore_3	
		case 0x4b:
			basicInstruction = new AStoreInst(0);
			System.out.println("Nactena instrukce astore_0");
			break;
		case 0x4c:
			basicInstruction = new AStoreInst(1);
			System.out.println("Nactena instrukce astore_1");
			break;
		case 0x4d:
			basicInstruction = new AStoreInst(2);
			System.out.println("Nactena instrukce astore_2");
			break;
		case 0x4e:
			basicInstruction = new AStoreInst(3);
			System.out.println("Nactena instrukce astore_3");
			break;
		case 0x4f:
			basicInstruction = new IAstoreInst();
			System.out.println("Nactena instrukce iastore");
			break;
		case 0x50:
			basicInstruction = new LAstoreInst();
			System.out.println("Nactena instrukce lastore");
			break;
		case 0x51:
			basicInstruction = new FAstoreInst();
			System.out.println("Nactena instrukce fastore");
			break;
		//case 0x52:
			//TODO dastore
		case 0x53:
			basicInstruction = new AAstoreInst();
			System.out.println("Nactena instrukce aastore");
			break;
		case 0x54:
			basicInstruction = new IAstoreInst();
			System.out.println("Nactena instrukce bastore");
			break;
		case 0x55:
			basicInstruction = new IAstoreInst();
			System.out.println("Nactena instrukce castore");
			break;
		case 0x56:
			basicInstruction = new IAstoreInst();
			System.out.println("Nactena instrukce sastore");
			break;
			
		// STACK ///////////////////////////////////////////////////////////////
		case 0x57:
			basicInstruction = new PopInst();
			System.out.println("Nactena instrukce pop");
			break;
		case 0x58:
			basicInstruction = new Pop2Inst();
			System.out.println("Nactena instrukce pop2");
			break;
		case 0x59:
			basicInstruction = new DupInst();
			System.out.println("Nactena instrukce dup");
			break;
		//case 0x5a:
			//TODO dup_x1 	
		//case 0x5b:
			//TODO dup_x2
		//case 0x5c:
			//TODO dup2
		//case 0x5d:
			//TODO dup2_x1
		//case 0x5e:
			//TODO dup2_x2
		//case 0x5f:
			//TODO swap
			
		// MATH ////////////////////////////////////////////////////////////////
		case 0x60:
			basicInstruction = new IAddInst();
			System.out.println("Nactena instrukce iadd");
			break;
		case 0x61:
			basicInstruction = new LAddInst();
			System.out.println("Nactena instrukce ladd");
			break;
		case 0x62:
			basicInstruction = new FAddInst();
			System.out.println("Nactena instrukce fadd");
			break;
		//case 0x63:
			//TODO dadd  
		case 0x64:
			basicInstruction = new ISubInst();
			System.out.println("Nactena instrukce isub");
			break;
		case 0x65:
			basicInstruction = new LSubInst();
			System.out.println("Nactena instrukce lsub");
			break;
		case 0x66:
			basicInstruction = new FSubInst();
			System.out.println("Nactena instrukce fsub");
			break;
		//case 0x67:
			//TODO dsub  
		case 0x68:
			basicInstruction = new IMullInst();
			System.out.println("Nactena instrukce imul");
			break;
		case 0x69:
			basicInstruction = new LMullInst();
			System.out.println("Nactena instrukce lmul");
			break;
		case 0x6a:
			basicInstruction = new FMullInst();
			System.out.println("Nactena instrukce fmul");
			break;
		//case 0x6b:
			//TODO dmul
		case 0x6c:
			basicInstruction = new IDivInst();
			System.out.println("Nactena instrukce idiv");
			break;
		case 0x6d:
			basicInstruction = new LDivInst();
			System.out.println("Nactena instrukce ldiv");
			break;
		case 0x6e:
			basicInstruction = new FDivInst();
			System.out.println("Nactena instrukce fdiv");
			break;
		//case 0x6f:
			//TODO ddiv  
		case 0x70:
			basicInstruction = new IRemInst();
			System.out.println("Nactena instrukce irem");
			break;
		//case 0x71:
			//TODO lrem  
		//case 0x72:
			//TODO frem  
		//case 0x73:
			//TODO drem  
		case 0x74:
			basicInstruction = new INegInst();
			System.out.println("Nactena instrukce ineg");
			break;
		//case 0x75:
			//TODO lneg  
		//case 0x76:
			//TODO fneg  
		//case 0x77:
			//TODO dneg  
		//case 0x78:
			//TODO ishl  
		//case 0x79:
			//TODO lshl  
		//case 0x7a:
			//TODO ishr  
		//case 0x7b:
			//TODO lshr  
		//case 0x7c:
			//TODO iushr  
		//case 0x7d:
			//TODO lushr  
		case 0x7e:
			basicInstruction = new IAndInst();
			System.out.println("Nactena instrukce iand");
			break;
		case 0x7f:
			//TODO land  
		case 0x80:
			basicInstruction = new IOrInst();
			System.out.println("Nactena instrukce ior");
			break;
		//case 0x81:
			//TODO lor  
		case 0x82:
			basicInstruction = new IXorInst();
			System.out.println("Nactena instrukce ixor");
			break;
		//case 0x83:
			//TODO lxor  
		case 0x84:
			basicInstruction = new IIncInst(nextByte(), nextByte());
			System.out.println("Nactena instrukce iinc");
			break;
			
		// CONVERSIONS /////////////////////////////////////////////////////////
		case 0x86:
			basicInstruction = new I2fInst();
			System.out.println("Nactena instrukce i2f");
			break;
			
		// COMPARSIONS /////////////////////////////////////////////////////////
		case 0x94:
			basicInstruction = new LCmpInst();
			System.out.println("Nactena instrukce lcmp");
			break;
		case 0x95:
			basicInstruction = new FCmpXInst(FCmpXInst.FCMP.L);
			System.out.println("Nactena instrukce fcml");
			break;		
		case 0x96:
			basicInstruction = new FCmpXInst(FCmpXInst.FCMP.G);
			System.out.println("Nactena instrukce fcmpg");
			break;
		//case 0x97:
			//TODO dcmpl  
		//case 0x98:
			//TODO dcmpg  
		case 0x99:
			basicInstruction = new IfInst(nextTwoBytes(),
					IfInst.Comparator.EQUAL);
			System.out.println("Nactena instrukce ifeq");
			break;
		case 0x9a:
			basicInstruction = new IfInst(nextTwoBytes(),
					IfInst.Comparator.NOT_EQUAL);
			System.out.println("Nactena instrukce ifne");
			break;
		case 0x9b:
			basicInstruction = new IfInst(nextTwoBytes(),
					IfInst.Comparator.LESS);
			System.out.println("Nactena instrukce iflt");
			break;
		case 0x9c:
			basicInstruction = new IfInst(nextTwoBytes(),
					IfInst.Comparator.GREATER_EQUAL);
			System.out.println("Nactena instrukce ifge");
			break;
		case 0x9d:
			basicInstruction = new IfInst(nextTwoBytes(),
					IfInst.Comparator.GREATER);
			System.out.println("Nactena instrukce ifgt");
			break;
		case 0x9e:
			basicInstruction = new IfInst(nextTwoBytes(),
					IfInst.Comparator.LESS_EQUAL);
			System.out.println("Nactena instrukce ifle");
			break;
		case 0x9f:
			basicInstruction = new IfICmpInst(nextTwoBytes(),
					IfICmpInst.Comparator.EQUAL);
			System.out.println("Nactena instrukce if_icmpeq");
			break;
		case 0xa0:
			basicInstruction = new IfICmpInst(nextTwoBytes(),
					IfICmpInst.Comparator.NOT_EQUAL);
			System.out.println("Nactena instrukce if_icmpne");
			break;
		case 0xa1:
			basicInstruction = new IfICmpInst(nextTwoBytes(),
					IfICmpInst.Comparator.LESS);
			System.out.println("Nactena instrukce if_icmplt");
			break;
		case 0xa2:
			basicInstruction = new IfICmpInst(nextTwoBytes(),
					IfICmpInst.Comparator.GREATER_EQUAL);
			System.out.println("Nactena instrukce if_icmpge");
			break;
		case 0xa3:
			basicInstruction = new IfICmpInst(nextTwoBytes(),
					IfICmpInst.Comparator.GREATER);
			System.out.println("Nactena instrukce if_icmpgt");
			break;
		case 0xa4:
			basicInstruction = new IfICmpInst(nextTwoBytes(),
					IfICmpInst.Comparator.LESS_EQUAL);
			System.out.println("Nactena instrukce if_icmple");
			break;
		case 0xa5:
			basicInstruction = new IfACmpInst(nextTwoBytes(),
					IfACmpInst.Comparator.EQUAL);
			System.out.println("Nactena instrukce if_acmpeq");
			break;
		case 0xa6:
			basicInstruction = new IfACmpInst(nextTwoBytes(),
					IfACmpInst.Comparator.NOT_EQUAL);
			System.out.println("Nactena instrukce if_acmpne");
			break;
			
		// CONTROL /////////////////////////////////////////////////////////////
		case 0xa7:
			basicInstruction = new GoToInst(nextTwoBytes());
			System.out.println("Nactena instrukce goto");
			break;
		case 0xac:
			basicInstruction = new IReturnInst();
			System.out.println("Nactena instrukce ireturn");
			break;
		case 0xad:
			basicInstruction = new LReturnInst();
			System.out.println("Nactena instrukce lreturn");
			break;
		case 0xb0:
			basicInstruction = new AReturnInst();
			System.out.println("Nactena instrukce areturn");
			break;
		case 0xb1:
			basicInstruction = new ReturnInst();
			System.out.println("Nactena instrukce return");
			break;
		
		// REFERENCIS //////////////////////////////////////////////////////////
		case 0xb2:
			basicInstruction = new GetStaticInst(method.getBytecode()[pc++],
					method.getBytecode()[pc++]);
			System.out.println("Nactena instrukce getstatic");
			break;
		case 0xb3:
			System.out.println("Nactena instrukce putstatic");
			basicInstruction = new PutStaticInst(method.getBytecode()[pc++],
					method.getBytecode()[pc++]);
			break;
		case 0xb4:
			System.out.println("Nactena instrukce getfield");
			basicInstruction = new GetFieldInst(nextTwoBytes());
			break;
		case 0xb5:
			System.out.println("Nactena instrukce putfield");
			basicInstruction = new PutFieldInst(nextTwoBytes());
			break;
		case 0xb6:
			System.out.println("Nactena instrukce invokevirtual");
			basicInstruction = new InvokeVirtualInst(nextTwoBytes());
			break;
		case 0xb7:
			System.out.println("Nactena instrukce invokespecial");
			basicInstruction = new InvokeSpecialInst(nextTwoBytes());
			break;
		case 0xb8:
			System.out.println("Nactena instrukce invokestatic");
			basicInstruction = new InvokeStaticInst(nextTwoBytes());
			break;
		case 0xb9:
			System.out.println("Nactena instrukce invokeinterface");
			basicInstruction = new InvokeInterfaceInst(nextTwoBytes(),
					nextByte(), nextByte());
			break;
		case 0xba:
			//invokedynamic - java nepouziva
			throw new UnsupportedOperationException(
					"invokedynamic is not supported by BVM");
		case 0xbb:
			basicInstruction = new NewInst(nextTwoBytes());
			System.out.println("Nactena instrukce new");
			break;
		case 0xbc:
			basicInstruction = new NewArrayInst(nextByte());
			System.out.println("Nactena instrukce newarray");
			break;
		case 0xbd:
			basicInstruction = new ANewArrayInst(nextTwoBytes());
			System.out.println("Nactena instrukce anewarray");
			break;
		case 0xbe:
			basicInstruction = new ArrayLengthInst();
			System.out.println("Nactena instrukce arraylength");
			break;
		case 0xbf:
			System.out.println("Nactena instrukce athrow");
			basicInstruction = new AThrowInst();
			break;
		//case 0xc0:
			//TODO checkcast
		case 0xc1: 
			System.out.println("Nactena instrukce instanceof");
			basicInstruction = new InstanceOfInst(nextTwoBytes());
			break;
		//case 0xc2:
			//TODO monitorenter
		//case 0xc3:
			//TODO monitorexit
			
		// EXTENDED ////////////////////////////////////////////////////////////
		case 0xc6:
			System.out.println("Nactena instrukce ifnull");
			basicInstruction = new IfNullInst(nextTwoBytes());
			break;
		case 0xc7:
			System.out.println("Nactena instrukce ifnonull");
			basicInstruction = new IfNoNullInst(nextTwoBytes());
			break;
			
		////////////////////////////////////////////////////////////////////////
		default:
			break;
		}

		return basicInstruction;
	}

	private int nextTwoBytes() {
		byte byte1 = method.getBytecode()[pc++];
		byte byte2 = method.getBytecode()[pc++];
		return (byte1 << 8) | byte2;
	}

	private short nextByte() {
		short byte1 = method.getBytecode()[pc++];
		return byte1;
	}

	public void printStack() {
		StringBuilder sb = new StringBuilder();
		sb.append("Stav zasobniku operandu: \n");
		sb.append("-------- BOTTOM ---------\n");
		Iterator<BVMValue> it = operandStack.iterator();
		while (it.hasNext()) {
			sb.append(it.next() + "\n");
		}
		sb.append("-------- TOP ---------");
		System.out.println(sb.toString());
	}
}
