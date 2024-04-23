import javax.swing.*;

public class LabelComponent implements Component {
    private JLabel label;

    public LabelComponent(String text, int x, int y, int w, int h) {
        label = new JLabel(text);
        label.setBounds(x, y, w, h);
    }

    @Override
    public JComponent getComponent() {
        return label;
    }
}
