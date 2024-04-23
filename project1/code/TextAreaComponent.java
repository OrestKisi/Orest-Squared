import javax.swing.*;

public class TextAreaComponent implements Component {
    private JScrollPane scrollPane;

    public TextAreaComponent(int x, int y, int w, int h) {


        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBounds(x, y, w, h);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(x, y, w, h);

    }

    @Override
    public JComponent getComponent() {
        return scrollPane;
    }
}
