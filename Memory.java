package sample;

public class Memory {
	short[] memorybank = new short[2048];
	
	public void initialize(){
		for(int i = 0; i < 2047; i++) {
			memorybank[i] = 0;
		}
	}
	
	public void set(int address, short value){
		memorybank[address] = value;
	}

	public short get(int address) {
		return memorybank[address];
	}
}
