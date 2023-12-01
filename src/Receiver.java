import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Represents a receiver in a communication system.
 */
public class Receiver {

    private int port;
    private Socket senderSocket;
    private ServerSocket receiverSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Constructs a Receiver object with the specified port and initializes sockets and streams.
     *
     * @param port The port for communication.
     */
    public Receiver(int port) {
        this.port = port;

        try {
            this.receiverSocket = new ServerSocket(this.port);
            this.senderSocket = receiverSocket.accept();
            this.in = new BufferedReader(new InputStreamReader(senderSocket.getInputStream()));
            this.out = new PrintWriter(senderSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Listens for incoming frames, processes them, and performs corresponding actions.
     *
     * @param outPath The path for writing the output data.
     * @throws IOException If an I/O error occurs during communication.
     */
    public void listen(String outPath) throws IOException {
        receiverSocket.setSoTimeout(3000);

        ArrayList<String> receivedDataTab = new ArrayList<>();
        int ackNum, nackNum;
        String response;
        String receivedData,correctData;
    

        while ((receivedData = in.readLine()) != null) {

            correctData=Frame.suppFlag(receivedData);

            switch (Frame.getType(correctData)) {
                case 'C':
                    ackNum = Frame.getNum(correctData);
                    response = FrameType.AckFrame(ackNum);
                    out.println(response);
                    

                case 'I':
                    if (Frame.Checksum(correctData)) {
                           
                        receivedDataTab.add(correctData);

                        String StringToWrite = Frame.binaryToData(Frame.getData(correctData));
                           
                        IOFile.writeFile(StringToWrite, outPath);

                        ackNum = (Frame.getNum(correctData) + 1) % (Sender.WINDOW_SIZE + 1);

                        response = FrameType.AckFrame(ackNum);
                        out.println(response);
                    } else {
                        nackNum = (Frame.getNum(correctData) + 1) % (Sender.WINDOW_SIZE + 1);
                        response = FrameType.NackFrame(nackNum);
                        out.println(response);
                    }
                        

                case 'F':
                    response = FrameType.AckFrame(0);
                    out.println(response);
                    break;
                }
            }
            
        }
    
    public void close() throws IOException {
        if (out != null) out.close();
        if (in != null) in.close();
        if (senderSocket != null) senderSocket.close();
        if (receiverSocket != null) receiverSocket.close();
        
    }

    /**
     * The main method to execute the Receiver program.
     *
     * @param args Command line arguments. Requires the port number.
     * the output is in the file out.txt
     */
    public static void main(String[] args) {
        int port = 0;
        if (args.length != 1) {
            System.out.println("we need one arg.");
            System.exit(0);
        }
        try {
            port = Integer.parseInt(args[0]);
            Receiver r = new Receiver(port);
            r.listen("out.txt");
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
