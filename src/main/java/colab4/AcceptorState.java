package colab4;

public class AcceptorState {

    // The highest proposal number this acceptor has promised to respond to.
    private int highestPromiseId = -1;

    // The proposal number of the value that has been accepted.
    private int acceptedId = -1;

    // The actual value that was accepted.
    private String acceptedValue = "";

    public synchronized boolean promise(int proposalId) {
        if (proposalId > highestPromiseId) {
            highestPromiseId = proposalId;
            return true;
        }
        return false;
    }

    public synchronized int getHighestPromise() {
        return highestPromiseId;
    }

    public synchronized boolean accept(int proposalId, String proposalValue) {
        if (proposalId >= highestPromiseId) {
            highestPromiseId = proposalId;
            acceptedId = proposalId;
            acceptedValue = proposalValue;
            return true;
        }
        return false;
    }

    public synchronized int getAcceptedId() {
        return acceptedId;
    }

    public synchronized String getAcceptedValue() {
        return acceptedValue;
    }
}