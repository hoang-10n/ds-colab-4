package colab4;

import java.io.*;
import java.net.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProposerClient {
    private int[] acceptorPorts;
    private String host = "localhost";
    private ObjectMapper mapper = new ObjectMapper();

    public ProposerClient(int[] port) {
        this.acceptorPorts = port;
    }

    private Message sendMessage(int port, Message msg) {
        try (Socket socket = new Socket(host, port);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String json = mapper.writeValueAsString(msg);
            writer.println(json);
            String response = reader.readLine();
            return mapper.readValue(response, Message.class);

        } catch (IOException e) {
            System.out.println("Connection to port " + port + " failed");
            return null;
        }
    }

    public void propose(int proposalId, String value) {
        int promises = 0;
        List<Message> responses = new ArrayList<>();

        // Phase 1: Send PREPARE
        for (int port : acceptorPorts) {
            Message reply = sendMessage(port, new Message("PREPARE", proposalId, -1, ""));
            if (reply != null && reply.getType().equals("PROMISE")) {
                promises++;
                responses.add(reply);
            }
        }

        int limit = acceptorPorts.length / 2 + 1;
        if (promises < limit) {
            System.out.println("Not enough promises. Proposal failed.");
            return;
        }

        // Use value from highest accepted proposal, if any
        String finalValue = value;
        for (Message r : responses) {
            if (r.getAcceptedId() > -1) {
                finalValue = r.getValue();
            }
        }

        // Phase 2: Send ACCEPT
        int acceptedCount = 0;
        for (int port : acceptorPorts) {
            Message reply = sendMessage(port, new Message("ACCEPT", proposalId, -1, finalValue));
            if (reply != null && reply.getType().equals("ACCEPTED")) {
                acceptedCount++;
            }
        }

        if (acceptedCount >= (acceptorPorts.length / 2 + 1)) {
            System.out.println("Proposal chosen: " + finalValue);
        } else {
            System.out.println("Proposal rejected.");
        }
    }
}
