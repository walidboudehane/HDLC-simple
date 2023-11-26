import java.io.IOException;


public class TypeTrame extends Trame {
    TypeTrame(char type){
        super();
    }
    
    public static Trame InformationTrame(String line,int num) throws IOException{
        Trame t=new Trame();
        t.setNum(num);
        t.setType('I');
        t.setData(line);
        t.calculeCRC();
        return t;
    }
    public static Trame ConnectTrame() throws IOException{
        Trame t=new Trame();
        t.setNum(0);
        t.setType('C');
        t.setData("");
        t.calculeCRC();
        return t;
    }
    public static Trame ReceptionTrame(int numACK ) throws IOException{      
        Trame t=new Trame();
        t.setNum(numACK);
        t.setType('A');
        t.setData("");
        t.calculeCRC();
        return t;
    }
    public static Trame RejetTrame(int numNACK ) throws IOException{      
        Trame t=new Trame();
        t.setNum(numNACK);
        t.setType('R');
        t.setData("");
        t.calculeCRC();
        return t;
    }
    public static Trame FinTrame() throws IOException{      
        Trame t=new Trame();
        t.setNum(8); //cas special
        t.setType('F');
        t.setData("");
        t.calculeCRC();
        return t;
    }
    public static Boolean isPbitTrame(Trame t,int p) throws IOException{      
        return t.StringTrame().length()==p;
    }
}
