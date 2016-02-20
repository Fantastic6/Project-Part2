package sample;

import java.util.Vector;

public class cache {
    private Vector<block> cacheBlock;
    
    private class block {
        private boolean dirty = false; // false = no one change it & true = someone changed it
        private short tag = 0;
        private short[] value = new short[4];
    }
    
    public cache() {
        cacheBlock = new Vector(16);
    }
    
    public void addblock(short tagnumber, Memory memory) {
        int startaddress = (int)(tagnumber << 2);
        block temp = new block();
        temp.tag = tagnumber;
        temp.value[0] = memory.get(startaddress);
        temp.value[1] = memory.get(startaddress + 1);
        temp.value[2] = memory.get(startaddress + 2);
        temp.value[3] = memory.get(startaddress + 3);
        cacheBlock.add(temp);
    } //Add one block to vector. Need to check whether this tag exist and the size of block before call this addblock().  
    
    public void printcontent() {
        int size = cacheBlock.size();
        System.out.println("size of cahce is: " + size);
        for(int i = 0; i < size; i++) {
            block p = new block();
            p = cacheBlock.elementAt(i);
            System.out.println("number of this block: " + i);
            System.out.println("dirtybit of this block: " + p.dirty);
            System.out.println("tag of this block: " + p.tag);
            System.out.println("value0 of this block: " + p.value[0]);
            System.out.println("value1 of this block: " + p.value[1]);
            System.out.println("value2 of this block: " + p.value[2]);
            System.out.println("value3 of this block: " + p.value[3]);
        }
    } // This is just for testing this class. This will print every block in cacheblock.
    
    public int checkexist(short tagnumber) {
        int size = cacheBlock.size();
        for(int i = 0; i < size; i++) {
            block p = new block();
            p = cacheBlock.elementAt(i);
            if(p.tag == tagnumber) { return i;}
        }
        return -1;
    } //This method is to check if a block is exist by input tag number. Output is the index and -1 means the block does not exist.
    
    public void deleteblock(Memory memory) {
        block p = new block();
        p = cacheBlock.elementAt(0);
        if(p.dirty == false) {
            cacheBlock.removeElementAt(0);
        }
        else {
            int startaddress = (int)(p.tag << 2);
            memory.set(startaddress, p.value[0]);
            memory.set(startaddress + 1, p.value[1]);
            memory.set(startaddress + 2, p.value[2]);
            memory.set(startaddress + 3, p.value[3]);
            cacheBlock.removeElementAt(0);
        }
    } //When we delete a block, we checked the dirtybit to decide if we need to write back to memory
    
    public short read(int address, Memory memory) {
        short tagvalue = (short)(address >> 2);
        int offset = address & 0x3;
        int index = checkexist(tagvalue);
        if(index == -1) {
            if(cacheBlock.size() < 16) {
                addblock(tagvalue, memory);
            }
            else {
                deleteblock(memory);
                addblock(tagvalue, memory);
            }
        }
        index = checkexist(tagvalue);
        return cacheBlock.elementAt(index).value[offset];
    } //read function will return short value; addblock if necessary 
    
    public void write(int address, short value, Memory memory) {
        short tagvalue = (short)(address >> 2);
        int offset = address & 0x3;
        int index = checkexist(tagvalue);
        if(index == -1) {
            if(cacheBlock.size() < 16) {
                addblock(tagvalue, memory);
            }
            else {
                deleteblock(memory);
                addblock(tagvalue, memory);
            }
        }
        index = checkexist(tagvalue);
        cacheBlock.elementAt(index).value[offset] = value;
        cacheBlock.elementAt(index).dirty = true;
    } //write function will update the value in block; addblock if necessary
    
    public static void main(String[] args) {
        Memory mem = new Memory();
        mem.initialize();
        cache test = new cache();
        for(int i = 0; i < 2048; i++) {   
            mem.set(i, (short)i);
        }
        System.out.println("read address 555: "+ test.read(555, mem));
        System.out.println("read address 100: "+ test.read(100, mem));
        System.out.println("read address 1: "+ test.read(1, mem));
        System.out.println("read address 15: "+ test.read(15, mem));
        System.out.println("read address 55: "+ test.read(55, mem));
        System.out.println("read address 77: "+ test.read(77, mem));
        System.out.println("read address 111: "+ test.read(111, mem));
        System.out.println("read address 222: "+ test.read(222, mem));
        System.out.println("read address 333: "+ test.read(333, mem));
        System.out.println("read address 444: "+ test.read(444, mem));
        System.out.println("read address 599: "+ test.read(599, mem));
        System.out.println("read address 666: "+ test.read(666, mem));
        System.out.println("read address 690: "+ test.read(690, mem));
        System.out.println("read address 777: "+ test.read(777, mem));
        System.out.println("read address 888: "+ test.read(888, mem));
        System.out.println("read address 1000: "+ test.read(1000, mem));
        test.write(555, (short) 9999, mem);
        System.out.println("read address 1111: "+ test.read(1111, mem));
        System.out.println("value at address 555 in memory is: " + mem.get(555));
        test.printcontent();
    }
}
    