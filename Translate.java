/**
 * Created by makhanov on 2/21/16.
 */
public class Translate {

    private String instruction;
    private String registers;
    private String x;
    private String address;
    private String indexI;
    public String[] output = {"000000","00","00","0","00000"};
    private String[] tokens;
    private String inputString;

    public void ICheck() {

        int length = inputString.length();

        if (inputString.contains("]")) {
            indexI = Character.toString(inputString.charAt(length-2));
            if (indexI.equals("1"))
                output[3] = "1";
        }

    }

    public void addressCheck() {
        if (address.contains("[")) {
            address = address.replace("[", "");
        }
        int binaryNum = Integer.parseInt(address);
        output[4] = Integer.toBinaryString(binaryNum);
    }


    public void registerCheck() {
        if (registers.equals("3")) {
            output[1] = "11";
        }else if(registers.equals("1")) {
            output[1] = "01";
        }else if(registers.equals("2")) {
            output[1] = "10";
        }
    }

    public void xCheck() {
        if (x.equals("3")) {
            output[2] = "11";
        }else if(x.equals("1")) {
            output[2] = "01";
        }else if(x.equals("2")) {
            output[2] = "10";
        }
    }

    public Translate(String inputData) {
        // TODO Auto-generated constructor stub
        inputString = inputData;
        String delim = "[ ,]";
        tokens = inputData.split(delim);
        instruction = tokens[0];
        instructionCheck();
    }

    public void instructionCheck(){
        switch(instruction){
            case "LDR":
                LDR();
                break;
            case "STR":
                STR();
                break;
            case "LDA":
                LDA();
                break;
            case "LDX":
                LDX();
                break;
            case "STX":
                STX();
                break;
            case "JZ":
                JZ();
                break;
            case "JNE":
                JNE();
                break;
            case "JCC":
                JCC();
                break;
            case "JMA":
                JMA();
                break;
            case "JSR":
                JSR();
                break;
            case "RFS":
                RFS();
                break;
            case "SOB":
                SOB();
                break;
            case "JGE":
                JGE();
                break;
            case "AMR":
                AMR();
                break;
            case "SMR":
                SMR();
                break;
            case "AIR":
                AIR();
                break;
            case "SIR":
                SIR();
                break;
            case "MLT":
                MLT();
                break;
            case "DVD":
                DVD();
                break;
            case "TRR":
                TRR();
                break;
            case "AND":
                AND();
                break;
            case "ORR":
                ORR();
                break;
            case "NOT":
                NOT();
                break;
            case "SRC":
                SRC();
                break;
            case "RRC":
                RRC();
                break;
            case "IN":
                IN();
                break;
            case "OUT":
                OUT();
                break;
        }
    }

    public void basicMethod() {
        registers = tokens[1];
        x = tokens[2];
        address = tokens[3];
        xCheck();
        ICheck();
        addressCheck();
        registerCheck();
    }

    public void LDR() {
        output[0] = "000001";
        basicMethod();
    }

    public void STR() {
        output[0] = "000010";
        basicMethod();
    }

    public void LDA() {
        output[0] = "000011";
        basicMethod();
    }

    public void JZ() {
        output[0] = "001010";
        basicMethod();
    }

    public void JNE() {
        output[0] = "001011";
        basicMethod();
    }

    public void JCC() {
        output[0] = "001100";
        basicMethod();
    }

    public void SOB() {
        output[0] = "010000";
        basicMethod();
    }

    public void JGE() {
        output[0] = "010001";
        basicMethod();
    }

    public void AMR() {
        output[0] = "000100";
        basicMethod();
    }

    public void SMR() {
        output[0] = "000101";
        basicMethod();
    }

    public void basicMethod2() {
        x = tokens[1];
        address = tokens[2];
        xCheck();
        ICheck();
        addressCheck();
    }

    public void LDX() {
        output[0] = "101001";
        basicMethod2();
    }

    public void STX() {
        output[0] = "101010";
        basicMethod2();
    }

    public void JMA() {
        output[0] = "001101";
        basicMethod2();
    }

    public void JSR() {
        output[0] = "001110";
        basicMethod2();
    }

    public void RFS() {
        output[0] = "001111";
        address = tokens[1];
        addressCheck();
    }

    public void AIR() {
        output[0] = "000110";
        registers = tokens[1];
        address = tokens[2];
        registerCheck();
        addressCheck();
    }

    public void SIR() {
        output[0] = "000111";
        registers = tokens[1];
        address = tokens[2];
        registerCheck();
        addressCheck();
    }

    public void method2() {
        registers = tokens[1];
        x = tokens[2];
        registerCheck();
        xCheck();
    }

    public void MLT() {
        output[0] = "010100";
        method2();
    }

    public void DVD() {
        output[0] = "010101";
        method2();
    }

    public void TRR() {
        output[0] = "010110";
        method2();
    }

    public void AND() {
        output[0] = "010111";
        method2();
    }

    public void ORR() {
        output[0] = "011000";
        method2();
    }

    public void NOT() {
        output[0] = "011001";
        registers = tokens[1];
        registerCheck();
    }

    public void SRC() {
        output[0] = "011111";
        registers = tokens[1];
        address = tokens[2];
        output[2] = tokens[3] + tokens[4];
        registerCheck();
        addressCheck();
    }

    public void RRC() {
        output[0] = "100000";
        registers = tokens[1];
        address = tokens[2];
        output[2] = tokens[3] + tokens[4];
        registerCheck();
        addressCheck();

    }

    public void method(){
        registers = tokens[1];
        address = tokens[2];
        registerCheck();
        addressCheck();
    }

    public void IN() {
        output[0] = "111101";
        method();
    }

    public void OUT() {
        output[0] = "111110";
        method();
    }

    public void stringBuilder() {
        StringBuilder strBuilder = new StringBuilder();

        for (int i = 0; i < output.length; i++) {
            strBuilder.append(output[i]);
        }
        String newString = strBuilder.toString();
        System.out.println(newString);

    }

}

public void main(String[] args) {
    Translate translate = new Translate;
    translate.stringBuilder();
}
