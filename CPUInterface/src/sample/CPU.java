package sample;/*
 *
 * Name: CPU.java
 * Author: Richard Hsu and Steve
 * Overview: This will be the CPU class of the Processor. 
 * It is made up of different functions to handle the different
 * instructions that need to be carried out. (EX: Load/Store...)
 *
*/


import java.util.BitSet;

public class CPU {

    // 4 general purpose registers
    short[] GPR = new short[4];

    // 3 index registers but 4 different values, 0 , 1, 2, 3
    short[] IR = new short[4];

    // 1 instruction set register
    short ISR = 0;

    // 1 12 bit program counter
    BitSet PC = new BitSet(12);

    /**  This is that processes one single instruction. 
     *      
     *  @param: int index (index in memory on where instruction is)
     *  @return: void
     */
    public void process_instruction(int index) {

        // fetch the value that PC is pointing to (an instruction) and move it into IR
        ISR = Main.memory.memorybank[index];

        //System.out.println("Instruction stored at ISR. ISR = " + memory[index]);

        //find the opcode
        int opcode = ISR >> 10;
        opcode = opcode & 0x3F;
        System.out.println("ISR " + ISR);
        System.out.println("opcode is " + opcode);

        // create an array for the parameters
        int[] parameters;

        // parse the instruction at ISR into Opcode, R, IX, I, Address
        parameters = parseInstruction(ISR, opcode);

        // check the parameters array and see that everything is there
        /*
        for (int i = 0; i < parameters.length; i++) {
            System.out.println(parameters[i]);
        }
        */

        // use opcode to figure out what instruction to run
        switch (opcode) {
            case 1:
                // call load
                ldr(parameters[1], parameters[2], parameters[3], parameters[4], IR, GPR);
                break;
            case 2:
                str(parameters[1], parameters[2], parameters[3], parameters[4], IR, GPR);
                break;
            case 3:
                lda(parameters[1], parameters[2], parameters[3], parameters[4], IR, GPR);
                break;
            case 4:
                amr(parameters[1], parameters[2], parameters[3], parameters[4], IR, GPR);
                break;
            case 5:
                smr(parameters[1], parameters[2], parameters[3], parameters[4], IR, GPR);
                break;
            case 6:
                air(parameters[1], parameters[4], GPR);
                break;
            case 7:
                sir(parameters[1], parameters[4], GPR);
                break;
            case 41:
                ldx(parameters[2], parameters[4], IR, parameters[3]);
                break;
            case 42:
                stx(parameters[2], parameters[4], IR, parameters[3]);
                break;
            default:
                break;
        }


    }


    /** This is a method that converts the PC into an int.
     *  Since the PC is a bitset, the conversion requires shifting of bits.  
     *  @param: BitSet bitset
     *  @return: int
     */
    public int bitSetToInt(BitSet bitSet) {
        int bitInteger = 0;
        for (int i = 0; i < 32; i++) {
            if(bitSet.get(i)) {
                bitInteger |= (1 << i);
            }
        }
        System.out.println("Converting: " + bitInteger);
        return bitInteger;
    }

    /** This is a method that takes the instruction and opcode and then parses it into the 
     *  required values. 
     *  @param: short instruction, int opcode
     *  @return: int[]
     */
    public int[] parseInstruction(short instruction, int opcode) {
        int GPRValue = 0;
        int IRValue = 0;
        int IAValue = 0;
        int AddressValue = 0;
        int[] parameters = new int[5];
        switch (opcode) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 41:
            case 42:
                GPRValue = (instruction & 0x3FF) >> 8;
                IRValue = (instruction & 0xFF) >> 6;
                IAValue = (instruction & 0x3F) >> 5;
                AddressValue = instruction & 0x1F;
                parameters[0] = opcode;
                parameters[1] = GPRValue;
                parameters[2] = IRValue;
                parameters[3] = IAValue;
                parameters[4] = AddressValue;
                break;
            default:
                break;

        }


        return parameters;
    }

    /** This is a method that loads a register from memory. 
     *  @param: int R, int IX, int I, int address, short[] IR, short[] GPR
     *  @return: void
     */
    public void ldr(int R, int IX, int I, int address, short[] IR, short[] GPR) {
        // calculate the effective address
        int EA = getEA(IX, I, address, IR);


        // load register IX with the contents of the specified address
        //GPR[R] = memory[EA]; // Actual code here
        GPR[R] = Main.memory.get(EA);
        System.out.println("Load completed");
        System.out.println("GPR[R]:"+GPR[R]);


    }

    /** This is a method that stores register to memory. 
     *  @param: int R, int IX, int I, int address, short[] memory, short[] IR, short[] GPR
     *  @return: void
     */
    public void str(int R, int IX, int I, int address, short[] IR, short[] GPR) {
        int EA = getEA(IX, I, address, IR);
        System.out.println("EA:" + EA);
        //store
//		memory[EA] = GPR[R];
        Main.memory.set(EA, GPR[R]);
        System.out.println("Store completed");
        System.out.println("Main.memorybank[EA]" + Main.memory.memorybank[EA]);
    }

    /** This is a method that loads the register with address.  
     *  @param: int R, int IX, int I, int address, short[] IR, short[] GPR
     *  @return: void
     */
    public void lda(int R, int IX, int I, int address, short[] IR, short[] GPR) {
        int EA = getEA(IX, I, address, IR);
        System.out.println("EA" + EA);
        GPR[R] = (short) EA;
        System.out.println("Load completed");
        System.out.println("GPR[R]"+GPR[R]);

    }

    /** This is a method that adds memory to register.
     *  @param: int R, int IX, int I, int address, short[] memory, short[] IR, short[] GPR
     *  @return: void
     */
    public void amr(int R, int IX, int I, int address, short[] IR, short[] GPR) {
        int EA = getEA(IX, I, address, IR);

        //GPR[R] = (short) (Main.memory.memorybank[EA] + GPR[R]);
        GPR[R] = (short) (Main.memory.get(EA) + GPR[R]);
        System.out.println("Add completed");
        System.out.println(GPR[R]);

    }

    /** This is a method that substracts memory from resiter. 
     *  @param: int R, int IX, int I, int address, short[] IR, short[] GPR
     *  @return: void
     */
    public void smr(int R, int IX, int I, int address, short[] IR, short[] GPR) {
        int EA = getEA(IX, I, address, IR);

        //GPR[R] = (short) (GPR[R] - Main.memory.memorybank[EA]);
        GPR[R] = (short) (GPR[R] - Main.memory.get(EA));
        System.out.println("Subtract completed");
        System.out.println(GPR[R]);

    }

    /** This is a method that adds memory to register. 
     *  @param: int R, int im, short[] GPR
     *  @return: void
     */
    public void air(int R, int im, short[] GPR) {
        GPR[R] += (short) im;
        System.out.println("Add completed");
        System.out.println(GPR[R]);
    }

    /** This is a method that subtracts the intermediate from the register. 
     *  @param: int R, int im, short[] GPR
     *  @return: void
     */
    public void sir(int R, int im, short[] GPR) {
        GPR[R] -= (short) im;
        System.out.println("Subtract completed");
        System.out.println(GPR[R]);

    }

    /** This is a method that loads index register from memory. 
     *  @param: int IX, int address, short[] IR, short[] memory, int param
     *  @return: void
     */
    public void ldx(int IX, int address, short[] IR, int param) {
        if(param == 0) {
            IR[IX] = (short) address;
        }
        else{
            //IR[IX] = Main.memory.memorybank[address];
            IR[IX] = Main.memory.get(address);
        }
        System.out.println("Load completed");
        System.out.println("IR[IX]" + IR[IX]);

    }

    /** This is a method that stores index register to memory. 
     *  @param: int IX, int address, short[] IR, short[] memory, int param
     *  @return: void
     */
    public void stx(int IX, int address, short[] IR, int param) {
        if (param == 0) {
            //Main.memory.memorybank[address] = IR[IX];
            Main.memory.set(address, IR[IX]);
        }else{
            Main.memory.set(Main.memory.memorybank[address],IR[IX]);
        }
        System.out.println("Load completed");
        System.out.println("Main.memorybank[EA]" + address);

    }

    /** 
     *  This is a method that calculates the effective address. 
     *  @param: int IX, int I, int address, short[] IR
     *  @return: int
     */
    public int getEA(int IX, int I, int address, short[] IR) {
        System.out.println("IR:" + IR[IX]);
        // handle no indirect addressing
        if(I == 0) {
            if(IX == 0) {
                // load register IX with the contents of the specified address
                return address;
            } else {
                return address + IR[IX];

            }

        } else {
            if(IX == 0) {
                return Main.memory.memorybank[address];

            } else {
                return Main.memory.memorybank[address + IR[IX]];
            }
        }
    }

    /** 
     *  This is a getter for GPR.
     *  @param: int index
     *  @return: short
     */
    public short getGPRValue(int index) {
        return GPR[index];
    }

    /** 
     *  This is a setter for GPR.
     *  @param: int index, short value
     *  @return: void
     */
    public void setGPRValue(int index, short value) {
        GPR[index] = value;
    }

    /** 
     *  This is a getter for IR.
     *  @param: int index
     *  @return: short
     */
    public short getIRValue(int index) {
        return IR[index];
    }

    /** 
     *  This is a setter for IR.
     *  @param: int index, short value
     *  @return: void
     */
    public void setIRValue(int index, short value) {
        IR[index] = value;
    }

    /** 
     *  This is a getter for ISR.
     *  @param: void
     *  @return: short
     */
    public short getISRValue() {
        return ISR;
    }

    /** 
     *  This is a setter for ISR.
     *  @param: short value
     *  @return: void
     */
    public void setISRValue(short value) {
        ISR = value;
    }

    /** 
     *  This is a getter for PC.
     *  @param: void
     *  @return: int
     */
    public int getPC() {
        return bitSetToInt(PC);
    }

    /** This is a method that converts the PC into a bitset.
     *  Since the PC is a bitset, the conversion requires shifting of bits.  
     *  @param: int value
     *  @return: void
     */
    public void setPC(int value) {
        int index = 0;
        String binaryString = Integer.toBinaryString(value);
        System.out.println(binaryString);
        int j = 0;
        for (int i = binaryString.length() - 1; i >= 0; i--) {
            if(binaryString.charAt(i) == '1') {
                PC.set(j);
            }
            j++;
        }
        System.out.println("PC final: " + PC.toString());


    }


}