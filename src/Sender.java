import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Represents a sender in a communication system.
 */
public class Sender {

    //Size of the communication window.
    public static final int WINDOW_SIZE = 7;
    //Timeout duration for socket operations.
    public static final int TIMEOUT = 3000;
    private int port;
    private String host;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;


    /**
     * Constructs a Sender object and establishes a connection with the Receiver.
     *
     * @param host The host address of the Receiver.
     * @param port The port number for communication.
     * @throws IOException If an I/O error occurs during socket setup.
     */
    public Sender(String host, int port ) throws IOException {
        this.port = port;
        this.host = host;
        this.socket = new Socket(this.host, this.port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Establishes a connection with the Receiver.
     *
     * @throws UnknownHostException If the host address is not recognized.
     * @throws IOException          If an I/O error occurs during communication.
     */
    public void connect() throws UnknownHostException, IOException {
        socket.setSoTimeout(TIMEOUT);

        String connectFrameString = FrameType.connectFrame();
        out.println(connectFrameString);

        System.out.println("\nS : connection demand :\n" + connectFrameString);
        String response = in.readLine();
        char frameType = Frame.getType(response);

        if (frameType == 'A') {
            System.out.println("\nR : connection approved.\n" + response);
        } else {
            System.out.println("\nR : connection denied.\n" + response);
        }
    }

    /**
     * Sends frames to the Receiver.
     *
     * @param inPath The path to the input file containing data for frames.
     * @throws UnknownHostException If the host address is not recognized.
     * @throws IOException          If an I/O error occurs during communication.
     * @throws InterruptedException If the thread is interrupted during execution.
     */
    public void send(String inPath,int goBackN) throws UnknownHostException, IOException, InterruptedException {
        if (goBackN==0){
        socket.setSoTimeout(TIMEOUT);
        ArrayList<String> framesList = new ArrayList<>();
        framesList = inputFrames(inPath);

        ArrayList<String> framesSent = new ArrayList<>(framesList);
        int i = 0;
        while (!framesList.isEmpty()) {
            String frameToSent = framesList.get(0);
            framesSent.add(framesList.get(0));
            framesList.remove(0);

            out.println(frameToSent);

            int frameNumber = (i) % (1+WINDOW_SIZE);
            System.out.println("\nS : sent Frame " + frameNumber + " :\n" + frameToSent);

            try {
                String response = in.readLine();

                if (((i + 1) % WINDOW_SIZE == 0 || framesList.isEmpty())) {
                    PrintAckType(response);
                }
            } catch (SocketTimeoutException e) {
                // Timeout
                System.out.println("Timeout: Resending frames ");
                framesList.addAll(0, framesSent);
                framesSent.clear();
                i = 0; // Reset the count for the next iteration
                continue;
            }

            i++;
        }
        String closeFrame = FrameType.EndFrame();
        out.println(closeFrame);

        System.out.println("\nS : close connection demand : \n" + closeFrame);

        String response = in.readLine();

        if (Frame.getType(response) == 'A') {
            System.out.println("\nR : confirmation Connection closed\n" + response);
        }}else {
            System.out.println("you should use option 0 for go back N");
            close();
        }
    }

    /**
     * Determines the type of acknowledgment (ACK or NACK) and prints the corresponding message.
     *
     * @param s The acknowledgment message.
     */
    public void PrintAckType(String s) {
        char frameType1 = Frame.getType(s);
        int RRnum = Frame.getNum(s);

        if (Frame.getType(s) == 'A') {
            System.out.println("\nR : RR" + RRnum + "\n" + s);

        } else if (frameType1 == 'R') {
            System.out.println("\nR : REJ" + RRnum + "\n" + s);
        }
    }

    /**
     * Closes the connection with the Receiver.
     *
     * @throws IOException If an I/O error occurs during socket closure.
     */
    public void close() throws IOException {
        socket.close();
        in.close();
        out.close();
    }
   
    /**
     * Reads lines from the input file and converts each line into a frame.
     *
     * @param inputPath The path to the input file.
     * @return An ArrayList containing frames of all the lines.
     * @throws FileNotFoundException If the input file is not found.
     * @throws IOException           If an I/O error occurs during file reading.
     */
    public static ArrayList<String> inputFrames(String inputPath) throws FileNotFoundException, IOException {

        ArrayList<String> dataList = new ArrayList<>();
        ArrayList<String> inputFrameList = new ArrayList<>();
        dataList = IOFile.readFile(inputPath);
        int localFrameNum = 0;
        for (String e : dataList) {

            inputFrameList.add(FrameType.InfoFrame(localFrameNum, e));
            localFrameNum += 1;
            if (localFrameNum == 8) {
                localFrameNum = 0;
            }

        }

        return inputFrameList;
    }

    /**
     * The main method to execute the Sender program.
     *
     * @param args Command line arguments. Requires host address and port number.
     * @throws InterruptedException If the thread is interrupted during execution.
     */
    public static void main(String[] args) throws InterruptedException {
        String host,pathFile;
        int port ,goBackN;
        if (args.length !=4) {
            System.out.println("you need 4 args");
            System.exit(0);
        }

        try {
            host = args[0];
            port = Integer.parseInt(args[1]);
            
            pathFile=args[2];
            goBackN=Integer.parseInt(args[3]);
            Sender s = new Sender(host, port);
            s.connect();
            s.send(pathFile,goBackN);
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
