/**
 * Represents a utility class for creating different types of frames.
 */
public class FrameType {

    /**
     * Creates an acknowledgment (ACK) frame with the specified number.
     *
     * @param num The acknowledgment number.
     * @return A string representation of the ACK frame.
     */
    public static String AckFrame(int num) {
        Frame t = new Frame('A', num, "");
        return t.stringFrame();
    }

    /**
     * Creates a negative acknowledgment (NACK) frame with the specified number.
     *
     * @param num The negative acknowledgment number.
     * @return A string representation of the NACK frame.
     */
    public static String NackFrame(int num) {
        Frame t = new Frame('R', num, "");
        return t.stringFrame();
    }

    /**
     * Creates an information frame with the specified number and data.
     *
     * @param num  The information frame number.
     * @param data The data to be included in the frame.
     * @return A string representation of the information frame.
     */
    public static String InfoFrame(int num, String data) {
        Frame t = new Frame('I', num, data);
        return t.stringFrame();
    }

    /**
     * Creates an end frame indicating the termination of transmission.
     *
     * @return A string representation of the end frame.
     */
    public static String EndFrame() {
        Frame t = new Frame('F', 0, "");
        return t.stringFrame();
    }

    /**
     * Creates a connection frame.
     *
     * @return A string representation of the connection frame.
     */
    public static String connectFrame() {
        Frame t = new Frame('C', 0, "");
        return t.stringFrame();
    }
}
