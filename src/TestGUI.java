import javax.swing.*;
import java.io.IOException;

public class TestGUI {
    public static void main(String[] args) throws IOException {
        VMS vms = VMS.getInstance();

        String testNo = "1";

        int GUI = 1;
        if (GUI == 1) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new InputFileFrame();
                }
            });
        } else {
            Test.runTest(testNo);
        }
    }
}
