package colab4;

public class Demo {
    public static void main(String[] args) {
        int[] port = { 9001, 9002, 9003, 9004, 9005 };
        ProposerClient c1 = new ProposerClient(port);
        ProposerClient c2 = new ProposerClient(port);

        new Thread(
                () -> c2.propose(1, "SCAN_ASTEROID_BELT")).start();
        new Thread(
                () -> c1.propose(1, "CORRECT_TRAJECTORY")).start();
    }
}
