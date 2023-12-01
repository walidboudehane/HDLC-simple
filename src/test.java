public class test {

    public static void main(String[] args) {
        // Test des fonctions de la classe Frame
        testFrameFunctions();

        // Test des fonctions de la classe FrameType
        testFrameTypeFunctions();

        // Test des fonctions de la classe CRC
        testCRCFunctions();
    }

    private static void testFrameFunctions() {
        Frame frame = new Frame('I', 1, "Hello, World!");

        System.out.println("Frame string representation: " + frame.stringFrame());
        System.out.println("Extracted Type: " + frame.getType(frame.suppFlag(frame.stringFrame())));
        System.out.println("Extracted Number: " + frame.getNum(frame.suppFlag(frame.stringFrame())));
        System.out.println("Extracted Data: " + frame.getData(frame.suppFlag(frame.stringFrame())));
        System.out.println("CRC: " + frame.getCRC(frame.suppFlag(frame.stringFrame())));
        System.out.println("Checksum Test: " + frame.Checksum(frame.suppFlag(frame.stringFrame())));
    }

    private static void testFrameTypeFunctions() {
        System.out.println("ACK Frame: " + FrameType.AckFrame(1));
        System.out.println("NACK Frame: " + FrameType.NackFrame(2));
        System.out.println("Info Frame: " + FrameType.InfoFrame(3, "Test Data"));
        System.out.println("End Frame: " + FrameType.EndFrame());
        System.out.println("Connect Frame: " + FrameType.connectFrame());
    }

    private static void testCRCFunctions() {
        System.out.println("CRC of '110110': " + CRC.calculateCRC("110110"));
    }
}
