import java.nio.charset.StandardCharsets;

/**
 * Represents a communication frame.
 */
public class Frame {

    /**
     * Size of the communication window.
     */
    public static final int WINDOW_SIZE = 8;

    /**
     * Length of a byte in bits.
     */
    public static final int BYTE_LENGTH = 8;

    private static String FLAG = "01111110";
    private String data;
    private int num;
    private char type;

    /**
     * Constructs a Frame object.
     *
     * @param type The type of the frame.
     * @param num  The frame number.
     * @param data The frame data.
     */
    public Frame(char type, int num, String data) {
        this.type = type;
        this.num = num;
        this.data = data;
    }

    /**
     * Converts the frame to a binary string representation.
     *
     * @return The binary string representation of the frame.
     */
    protected String stringFrame() {
        String typeBitString = typeBinaryString(type);
        String numBitString = convertNumToString(num);
        String dataBits = dataToBinary(data);
        String crc = CRC.calculateCRC(typeBitString + numBitString + dataBits);

        return addFlag(typeBitString + numBitString + dataBits + crc);
    }



    /**
     * Extracts the type from a  string representation of a frame without flags .
     *
     * @param s The string representation of the frame without flags.
     * @return The type.
     */
    protected static char getType(String s) {
        return (char) Integer.parseInt(s.substring(0,BYTE_LENGTH), 2);
    }

    /**
     * Extracts the frame number from a string representation of a frame without flags .
     *
     * @param s The string representation of the frame without flags.
     * @return The frame number.
     */
    protected static int getNum(String s) {
        return Integer.parseInt(s.substring(BYTE_LENGTH, 2 * BYTE_LENGTH), 2);
    }


    /**
     * Gets the data from a string representation of a frame without flags .
     *
     * @param s The string representation of the frame without flags.
     * @return The data.
     */
    public static String getData(String s) {
        return s.substring(2 * BYTE_LENGTH, s.length() - 2 * BYTE_LENGTH);
    }
    /**
     * Gets the CRC from a string representation of a frame without flags .
     *
     * @param s The string representation of the frame without flags.
     * @return The CRC.
     */
     public String getCRC(String s){
        return s.substring(s.length()-2*BYTE_LENGTH,s.length());
    }

     /**
     * Adds flag bits to the given data.
     *
     * @param data The data to which flags are added.
     * @return The data with flags added.
     */
    public static String addFlag(String data) {
        return FLAG + Flag.addZeros(data) + FLAG;
    }

    /**
     * Removes flags from the given data.
     *
     * @param s The data with flags.
     * @return The data with flags removed.
     */
    public static String suppFlag(String s) {
        return Flag.deleteZeros(s.substring(BYTE_LENGTH, s.length() - BYTE_LENGTH));
    }

   

    /**
     * Converts the frame number to a binary string representation.
     *
     * @param a The frame number.
     * @return The binary string representation of the frame number.
     */
    protected String convertNumToString(int a) {
        if (a <= WINDOW_SIZE) {
            String aStr = Integer.toBinaryString(a);
            return "00000000".substring(0, WINDOW_SIZE - aStr.length()) + aStr;
        } else {
            String aStr = Integer.toBinaryString(a % WINDOW_SIZE);
            return "00000000".substring(0, WINDOW_SIZE - aStr.length()) + aStr;
        }
    }


    /**
     * Converts the type to a binary string representation.
     *
     * @param c The type.
     * @return The binary string representation of the type.
     */
    public String typeBinaryString(char c) {
        return "0" + Integer.toBinaryString((int) c);
    }

    /**
     * Tests the checksum of a string representation of a frame.
     *
     * @param s The string representation of the frame.
     * @return True if the checksum is valid, false otherwise.
     */
    public static boolean Checksum(String s) {
        return Integer.parseInt(CRC.calculateCRC(s), 2) == 0;
    }

    /**
     * Converts data to binary string representation. {inspiré de chatgpt }
     *
     * @param line The data to convert.
     * @return The binary string representation of the data.
     */
    public String dataToBinary(String line) {
        byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
        StringBuilder stringBinary = new StringBuilder();

        for (byte b : bytes) {
            String binaryString = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            stringBinary.append(binaryString);
        }

        return stringBinary.toString();
    }

    /**
     * Converts a binary string to data. 
     *
     * @param binaryString The binary string to convert.
     * @return The converted data.
     */
    public static String binaryToData(String binaryString) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < binaryString.length(); i += BYTE_LENGTH) {
            String byteString = binaryString.substring(i, i + BYTE_LENGTH);
            int byteValue = Integer.parseInt(byteString, 2);
            result.append((char) byteValue);
        }

        return result.toString();
    }
    
}
