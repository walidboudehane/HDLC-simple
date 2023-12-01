/**
 * Represents a class for CRC calculation.
 */
public class CRC {

    /**
     * The generator polynomial for CRC calculation.
     */
    final static String POLYNOMIAL_GENERATOR = "10001000000100001";

    /**
     * String of zeros for CRC calculation.
     */
    final static String ZEROS = "0000000000000000";

    /**
     * Performs XOR operation on two binary strings.
     *
     * @param s1 The first binary string.
     * @param s2 The second binary string.
     * @return The result of XOR operation on the input strings.
     */
    static String xor(String s1, String s2) {
        StringBuilder resultString = new StringBuilder();
        int min_length=Math.min(s1.length(), s2.length());
        for (int i = 0; i <min_length ; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                resultString.append('1');
            } else {
                resultString.append('0');
            }
        }
        return resultString.toString();
    }

    /**
     * Calculates the CRC for a string using the generator polynomial.
     *
     * @param s The input string.
     * @return A string of size 16 representing the CRC.
     */
    public static String calculateCRC(String s) {
        String result = s + ZEROS;
        int polynomialSize = POLYNOMIAL_GENERATOR.length();
        int i = 0;
        while (i <= result.length() - polynomialSize) {
            if (result.charAt(i) == '1') {
                String replacement = xor(POLYNOMIAL_GENERATOR, result.substring(i, i + polynomialSize));
                result = result.replace(result.substring(i, i + POLYNOMIAL_GENERATOR.length()), replacement);
            }
            i++;
        }
        while (result.charAt(0) == '0' && result.length() > polynomialSize - 1) {
            result = result.substring(1);
        }
        return result;
    }
}
