package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Starter {
    public static void main(String args[]) throws IOException {
        System.out.println("Starting");
        String curLine = "";
        System.out.println("server\\client mode? (type 'quit' to exit): ");
        InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);
        while (true) {
            curLine = in.readLine();
            if (curLine.equals("quit")) {
                return;
            } else if (curLine.equals("server")) {
                Server.main(null);
               // new core.Server(777).run();
               // System.out.println("core.Server stopped");
                return;
            } else if (curLine.equals("client")) {
                System.out.print("server (default localhost):");
                String server = in.readLine();
                if (server == null || server.length() < 2)
                    server = "localhost";
                System.out.print("port (default 45000:");
                String port = in.readLine();
                if (port == null || port.length() < 2)
                    port = "777";
                Client.main(new String[]{server, port});
               // new core.Client(server, Integer.parseInt(port)).run();
            }
        }
    }
}
