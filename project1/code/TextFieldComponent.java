import javax.swing.*;

public class TextFieldComponent implements Component {
    private JTextField textField;

    public TextFieldComponent(int x, int y, int w, int h, boolean editable) {
        textField = new JTextField();
        textField.setBounds(x, y, w, h);
        textField.setEditable(editable);
    }

    @Override
    public JComponent getComponent() {
        return textField;
    }
}
