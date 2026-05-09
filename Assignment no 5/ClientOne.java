import java.io.*;
import java.net.*;

public class ClientOne {
    public static void main(String args[]) throws IOException {
        // Connect to server to send messages
        Socket s = new Socket("localhost", 7000);
        PrintStream out = new PrintStream(s.getOutputStream());

        // ClientOne owns port 7001 — it listens here for the token from ClientTwo
        ServerSocket ss = new ServerSocket(7001);
        Socket s1 = ss.accept();
        BufferedReader in1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
        PrintStream out1 = new PrintStream(s1.getOutputStream());

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = "Token"; // ClientOne starts with the token

        while (true) {
            if (str.equalsIgnoreCase("Token")) {
                System.out.println("Do you want to send some data? (Yes/No)");
                str = br.readLine();
                if (str.equalsIgnoreCase("Yes")) {
                    System.out.println("Enter the data: ");
                    str = br.readLine();
                    out.println("ClientOne: " + str); // send to server
                }
                // Pass token to ClientTwo
                out1.println("Token");
                System.out.println("Token passed to ClientTwo");
            }
            // Wait to receive token back from ClientTwo
            System.out.println("Waiting for Token...");
            str = in1.readLine();
        }
    }
}
