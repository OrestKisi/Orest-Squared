import java.awt.event.ItemListener;

import javax.swing.*;

public class ComboBoxComponent implements Component {
    private JComboBox<String> comboBox;

    public ComboBoxComponent(int x, int y, int w, int h, String[] items, int id, ItemListener il) {

        comboBox = new JComboBox<>(items);

        switch (id) {
            case 0:
                comboBox.addItem("Other");
                break;
            
                case 1:
                comboBox.addItem("Other ");
                break;
        
            default:
                break;
        }
        comboBox.setBounds(x, y, w, h);
        comboBox.addItemListener(il);
    }

    @Override
    public JComponent getComponent() {
        return comboBox;
    }
}
