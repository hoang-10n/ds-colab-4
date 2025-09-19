package colab4;

import java.io.Serializable;

public class Message implements Serializable {
    private String type;
    private int proposalId;
    private int acceptedId;
    private String value;

    // No-argument constructor
    public Message() {}

    public Message(String type, int proposalId, int acceptedId, String value) {
        this.type = type;
        this.proposalId = proposalId;
        this.acceptedId = acceptedId;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public int getProposalId() {
        return proposalId;
    }

    public int getAcceptedId() {
        return acceptedId;
    }

    public String getValue() {
        return value;
    }
}
