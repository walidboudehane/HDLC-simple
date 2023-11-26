import java.io.IOException;

public class Trame {
    public static final int windowSize = 7;
    private String Flag="01111110";
    private String data;
    private  String Num="00000000";
    private String Type;
    private String CRC;

    public Trame() {
    }
    protected  String StringTrame(){   
        return getFlag()+getType()+getNum()+getData()+getCRC()+getFlag();
    }
    protected String getFlag(){   
        return this.Flag;
    }

    protected String getType(){
        return this.Type;
    }

    protected  String getNum(){
        return this.Num;
    }
    protected String getCRC(){
        return this.CRC;
    }
    protected String getData(){
        return this.data;
    }

    protected void setType(char c){
        this.Type=TypeBinaryString(c);
    }
    
    protected void setNum(int a){
        if (a<=windowSize){
            String a_str=Integer.toBinaryString(a) ;
            this.Num=Num.substring(0,windowSize-a_str.length()+1)+a_str;
        }
    }
    protected void setData(String s){
        this.data=dataToBinary(s);
    }
    protected void calculeCRC(){
        this.CRC=Crc.calcule_CRC(getType()+getNum()+getData());
    }


    public String TypeBinaryString(char c){
        return  "0"+Integer.toBinaryString((int) c);
    }



    public  Boolean test_Checksum(String s){
        String trame_without_flags=getType()+getNum()+getData()+getCRC();
        return Integer.parseInt(Crc.calcule_CRC(trame_without_flags),2)==0;
    }

    public String dataToBinary(String line){
        String lineBinary="";
        for(int i=0;i<line.length();i++){
            lineBinary+=Integer.toBinaryString(line.charAt(i));
        }
        return "0"+lineBinary;
    }

    public static void main(String[] args) throws IOException {
        Trame t=new Trame();
        t.setNum(3);
        t.setType('I');
        t.setData("A");
        t.calculeCRC();
        /*System.out.println(t.getFlag());
        System.out.println(t.getNum());
        System.out.println(t.getType());
        System.out.println(t.getData());
        System.out.println(t.getCRC());
        System.out.println(t.StringTrame());*/
    }
}
