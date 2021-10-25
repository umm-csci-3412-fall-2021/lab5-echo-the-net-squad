package echoserver;

import java.net.*;

import java.io.*;

public class EchoServer {
    public static final int portNumber = 6013;

    public static void main(String[] args) {
        try {
            // Start listening on the specified port
            ServerSocket socket = new ServerSocket(portNumber);

            // Run forever, which is common for server style services
            while (true) {
                // Wait until someone connects, thereby requesting a date
                Socket client = socket.accept();
                System.out.println("Got a request!");

                //Object that gets input from the client
                InputStream clientInput = client.getInputStream();
                //Object that sends output to the client
                OutputStream clientOutput = client.getOutputStream();

                //Buffer size of client input
                byte[] bufferSize = new byte[1024];
                int bytesRead;

                // Read data sent by client socket
                while ((bytesRead = clientInput.read(bufferSize)) != -1) {
                    // Write data back to client
                    clientOutput.write(bufferSize, 0, bytesRead);
                    clientOutput.flush();
                }

                //TODO: Close the socket via client
                // Close the client socket since we're done.
                socket.close();
            }
            // *Very* minimal error handling.
        } catch (IOException ioe) {
            System.out.println("We caught an unexpected exception");
            System.err.println(ioe);
        }
    }
}