package echoserver;

import java.net.*;
import java.io.*;

public class EchoClient {
    public static final int portNumber = 6013;

    public static void main(String[] args) throws IOException {
        String server;
        // Use "127.0.0.1", i.e., localhost, if no server is specified.
        if (args.length == 0) {
            server = "127.0.0.1";
        } else {
            server = args[0];
        }

        try {
            // Create socket
            Socket socket = new Socket(server, portNumber);

            // Helpful guide here:
            // https://stackoverflow.com/questions/1830698/what-is-inputstream-output-stream-why-and-when-do-we-use-them
            // Object that gets input from the server
            InputStream receiveServerInput = socket.getInputStream();
            // Object that sends output to the server
            OutputStream sendServerOutput = socket.getOutputStream();

            byte[] buffer = new byte[1024];
            int bufferSize;

            // Read data in from user input
            while ((bufferSize = System.in.read(buffer)) != -1) {

                // Send data to server
                sendServerOutput.write(buffer, 0, bufferSize);
                // Clear buffer
                sendServerOutput.flush();

                // Receive data from server and write it to the console
                bufferSize = receiveServerInput.read(buffer);
                System.out.write(buffer, 0, bufferSize);
                System.out.flush();
            }

            // Tell server we won't be sending anymore output
            socket.shutdownOutput();

            // Read whatever data the server has left
            while ((bufferSize = receiveServerInput.read(buffer)) != -1) {
                System.out.write(buffer, 0, bufferSize);
                System.out.flush();
            }
            // Close the socket when we're done reading from it
            socket.close();

            // Provide some minimal error handling.
        } catch (ConnectException ce) {
            System.out.println("We were unable to connect to " + server);
            System.out.println("You should make sure the server is running.");
        } catch (IOException ioe) {
            System.out.println("We caught an unexpected exception");
            System.err.println(ioe);
        }
    }
}