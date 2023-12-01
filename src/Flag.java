public class Flag {
        /**
         * add zeros to our frame data (without flags)
         *
         * @param data the string to be stuffed
         * @return the string with an added '0' after any 5 consecutive '1''s
         */
        public static String addZeros(String data) {
            String result = "";
            int count = 0;

            for (int i = 0; i < data.length(); i++) { 
    
                if (data.charAt(i) == '1') {
                    count++;
                    result += data.charAt(i);
                    if (count == 5) {
                        result += '0';
                        count = 0;
                    }
                } else {
                    count = 0;
                    result += data.charAt(i);
                }
            }
            return result;
        }
    
        /**
         * delete added zeros to our frame data (without flags)
         *
         * @param dataStaffed the string to be unstuffed
         * @return the string with a removed bit ('0') after any 5 consecutive '1''s
         */
        public static String deleteZeros(String dataStaffed) {
            String result = "";
            int count = 0;
    
            for (int i = 0; i < dataStaffed.length(); i++) {
                
    
                if (dataStaffed.charAt(i) == '1') {
                    count++;
                    result += dataStaffed.charAt(i);
                    if (count == 5) {
                        i++; // Skip the '0' to be removed
                        count = 0;
                    }
                } else {
                    count = 0;
                    result += dataStaffed.charAt(i);
                }
            }
    
            return result;
        }
    
    
}
