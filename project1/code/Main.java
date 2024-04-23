import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static JFrame window;
    public static SwingGUI sg;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize theme. Using fallback.");
        }

        SwingUtilities.invokeLater(() -> {
            window = new JFrame("Project 1 - Food Application - Orest Brukhal, Orest Kisi");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(600, 1000);
            window.setResizable(true);

            sg = new SwingGUI(window);
            window.setVisible(true);
        });
    }
}
