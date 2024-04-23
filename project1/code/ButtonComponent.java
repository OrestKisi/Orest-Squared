import javax.swing.*;
import java.awt.event.ActionListener;

public class ButtonComponent implements Component {
    private JButton button;

    public ButtonComponent(String text, int x, int y, int w, int h, ActionListener listener) {
        button = new JButton(text);
        button.setBounds(x, y, w, h);
        button.addActionListener(listener);
    }

    @Override
    public JComponent getComponent() {
        return button;
    }
}
