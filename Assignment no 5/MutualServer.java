import java.io.*;
import java.net.*;

public class MutualServer implements Runnable {
    Socket socket = null;
    static ServerSocket ss;

    MutualServer(Socket newSocket) {
        this.socket = newSocket;
    }

    public static void main(String args[]) throws IOException {
        ss = new ServerSocket(7000);
        System.out.println("Server Started");
        while (true) {
            Socket s = ss.accept();
            MutualServer es = new MutualServer(s);
            Thread t = new Thread(es);
            t.start();
        }
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            // Read and display messages sent by clients
            while ((line = in.readLine()) != null) {
                System.out.println("Message received: " + line);
            }
        } catch (Exception e) { }
    }
}
