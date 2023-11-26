public class Crc{
    final static String pol_generateur="10001000000100001";
    final static String   zeros="0000000000000000";
    
    private static String xor(String s1, String s2) {

        String resultatString = "";
        for (int i = 0; i < Math.min(s1.length(), s2.length()); i++) {
            if (s1.charAt(i) != s2.charAt(i)){
                resultatString+='1';
                continue;}
            resultatString+='0';
        }
        return resultatString;}
     
    /**
     * calcule le  CRC pour un string en utilsant le polynome génerateur
     * 
     * @param  s string 
     * @return un string de taille 16 qui peut se traduire 
     */
    public static String calcule_CRC(String s) {
        String Tx=s+zeros;
        int polynome_taille=pol_generateur.length();
        int i=0;
        while (i <= Tx.length() - polynome_taille){
                if (Tx.charAt(i) == '1') {
                    String remplacement=xor(pol_generateur,Tx.substring(i, i + polynome_taille));
                    Tx=Tx.replace(Tx.substring(i, i + pol_generateur.length()),remplacement);
                }
                i++;
            } 
        while (Tx.charAt(0) == '0' && Tx.length() > polynome_taille - 1) {
                Tx=Tx.substring(1, Tx.length());
            }
            //Tx="0b"+Tx;
        return Tx;
    }
    public static void main(String[] args) {
        System.out.println(xor("1011", "1111"));
        //exemple du devoir 1 
        System.out.println(calcule_CRC("1000000000000000"));
    }
}
