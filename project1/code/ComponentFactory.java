import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.*;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jfree.chart.ChartPanel;

public class ComponentFactory {

    public enum ComponentType {
        LABEL,
        TEXT_FIELD,
        BUTTON,
        COMBO_BOX,
        TEXT_AREA,
        XY_CHART,
        DATE_CHOOSER
    }

    public JComponent createComponent(ComponentType type, Object... args) {

        switch (type) {
            case BUTTON:
                return createButton(args);
            case LABEL:
                return createLabel(args);
            case TEXT_FIELD:
                return createTextField(args);
            case COMBO_BOX:
                return createComboBox(args);
            case TEXT_AREA:
                return createTextArea(args);
            case DATE_CHOOSER:
                return createDateChooser(args);
            case XY_CHART:
                return createXYChart(args);
            // default:
            // throw new IllegalArgumentException("Unknown component type");
        }
        return null;
    }

    private JButton createButton(Object[] args) {

        ButtonComponent component = null;

        if (args.length == 6) {
            System.out.println(args[5]);

            component = new ButtonComponent((String) args[0], (int) args[1], (int) args[2],
                    (int) args[3], (int) args[4], (ActionListener) args[5]);

            
        }
        else {
            component = new ButtonComponent((String) args[0], (int) args[1], (int) args[2],
                    (int) args[3], (int) args[4], null);
        }
        return (JButton) component.getComponent();
    }

    private JLabel createLabel(Object[] args) {

        LabelComponent label = new LabelComponent((String) args[0], (int) args[1], (int) args[2], (int) args[3],
                (int) args[4]);

        return (JLabel) label.getComponent();
    }

    private JTextField createTextField(Object[] args) {

        TextFieldComponent component = new TextFieldComponent((int) args[0], (int) args[1], (int) args[2],
                (int) args[3], (boolean) args[4]);

        return (JTextField) component.getComponent();
    }

    private JComboBox<String> createComboBox(Object[] args) {

        ComboBoxComponent component = new ComboBoxComponent((int) args[0], (int) args[1], (int) args[2], (int) args[3], (String[]) args[4],
                (int) args[5], (ItemListener) args[6]);
                
        return (JComboBox<String>) component.getComponent();
    }

    private JScrollPane createTextArea(Object[] args) {
        TextAreaComponent textArea = new TextAreaComponent((int) args[0], (int) args[1], (int) args[2], (int) args[3]);
        return (JScrollPane) textArea.getComponent();
    }

    public static JDatePickerImpl createDateChooser(Object[] args) {

        DateChooserComponent dateChooser = new DateChooserComponent((int) args[0], (int) args[1], (int) args[2],
                (int) args[3]);

        return (JDatePickerImpl) dateChooser.getComponent();
    }

    public static ChartPanel createXYChart(Object[] args) {

        XYChartComponent chartPanel = new XYChartComponent((int) args[0], (int) args[1], (int) args[2], (int) args[3]);

        return (ChartPanel) chartPanel.getComponent();
    }
}
