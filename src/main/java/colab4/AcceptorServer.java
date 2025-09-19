package colab4;

import java.io.*;
import java.net.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AcceptorServer {
    private static AcceptorState state = new AcceptorState();
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java AcceptorServer <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Acceptor running on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleClient(socket)).start();
            }
        }
    }

    private static void handleClient(Socket socket) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            String line = reader.readLine();
            if (line == null || line.trim() == "") return;

            System.out.println(line);

            Message msg = mapper.readValue(line, Message.class);
            Message response = null;

            switch (msg.getType()) {
                case "PREPARE":
                    boolean promised = state.promise(msg.getProposalId());
                    if (promised) {
                        response = new Message("PROMISE", msg.getProposalId(),
                                state.getAcceptedId(), state.getAcceptedValue());
                    } else {
                        response = new Message("REJECT", msg.getProposalId(), -1, "");
                    }
                    break;

                case "ACCEPT":
                    boolean accepted = state.accept(msg.getProposalId(), msg.getValue());
                    if (accepted) {
                        response = new Message("ACCEPTED", msg.getProposalId(),
                                msg.getProposalId(), msg.getValue());
                    } else {
                        response = new Message("REJECT", msg.getProposalId(), -1, "");
                    }
                    break;
            }

            writer.println(mapper.writeValueAsString(response));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
