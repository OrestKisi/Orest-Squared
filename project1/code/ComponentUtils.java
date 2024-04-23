import javax.swing.*;

import java.awt.Component;
import java.awt.event.ItemEvent;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.jfree.chart.ChartPanel;



public class ComponentUtils {

    private static JTabbedPane tabPanel;

    public ComponentUtils(JTabbedPane _tabPanel){
        tabPanel = _tabPanel;
    }



    public static JTextField getJTextFieldByJLabelText(String title) {
        Component[] components;
        JTextField txt = new JTextField();
        JPanel pan = new JPanel();

        for (int i = 0; i < tabPanel.getComponents().length; i++) {
            if (tabPanel.getComponents()[i] instanceof JPanel) {
                pan = (JPanel) tabPanel.getComponents()[i];

                components = pan.getComponents();
                for (int j = 0; j < components.length; j++) {

                    if (components[j] instanceof JLabel) {
                        JLabel jl = (JLabel) components[j];
                        if (jl.getText().equals(title)) {
                            txt = (JTextField) components[j + 1];
                        }
                    }

                }
            }
        }

        return txt;
    }

    public JComboBox<String> getJComboBoxByJLabelText(String title) {
        Component[] components;
        JComboBox<String> comboBox = new JComboBox<>();
        JPanel pan = new JPanel();

        for (int i = 0; i < tabPanel.getComponents().length; i++) {
            if (tabPanel.getComponents()[i] instanceof JPanel) {
                pan = (JPanel) tabPanel.getComponents()[i];

                components = pan.getComponents();
                for (int j = 0; j < components.length; j++) {

                    if (components[j] instanceof JLabel) {
                        JLabel jl = (JLabel) components[j];
                        if (jl.getText().equals(title)) {
                            comboBox = (JComboBox<String>) components[j + 1];
                        }
                    }

                }
            }
        }

        return comboBox;
    }

    public JTextArea getJTextAreaByJLabelText(String title) {
        Component[] components;
        JScrollPane jsc = new JScrollPane();
        JTextArea txt = new JTextArea();
        JPanel pan = new JPanel();

        for (int i = 0; i < tabPanel.getComponents().length; i++) {
            if (tabPanel.getComponents()[i] instanceof JPanel) {
                pan = (JPanel) tabPanel.getComponents()[i];

                components = pan.getComponents();
                for (int j = 0; j < components.length; j++) {

                    if (components[j] instanceof JLabel) {
                        JLabel jl = (JLabel) components[j];
                        if (jl.getText().equals(title)) {
                            jsc = (JScrollPane) components[j + 1];
                            txt = (JTextArea) jsc.getViewport().getView();
                        }
                    }

                }
            }
        }

        return txt;
    }


    public String[] getDateByLabelText(String title) {
        Component[] components;
        JDatePickerImpl datePicker = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), new Properties()),
                null);
        String[] date = new String[3];

        JPanel pan = new JPanel();

        for (int i = 0; i < tabPanel.getComponents().length; i++) {
            if (tabPanel.getComponents()[i] instanceof JPanel) {
                pan = (JPanel) tabPanel.getComponents()[i];

                components = pan.getComponents();
                for (int j = 0; j < components.length; j++) {

                    if (components[j] instanceof JLabel) {
                        JLabel jl = (JLabel) components[j];
                        if (jl.getText().equals(title)) {
                            if (components[j + 1] instanceof JDatePickerImpl) {
                                datePicker = (JDatePickerImpl) components[j + 1];
                            }
                        }
                    }

                }
            }
        }

        if (datePicker.getModel().getValue() != null) {
            Date selectedDate = (Date) datePicker.getModel().getValue();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);
            date[0] = String.valueOf(calendar.get(Calendar.YEAR));
            date[1] = String.valueOf(calendar.get(Calendar.MONTH) + 1); // Month is zero-based
            date[2] = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            String[] dateParts = datePicker.getJFormattedTextField().getText().split(",");
            if (dateParts.length >= 3) {
                date[0] = dateParts[0];
                date[1] = dateParts[1];
                date[2] = dateParts[2];
            } else {
                // default
            }
        }

        return date;
    }

    public ChartPanel getChartPanel() {
        Component[] components;
        ChartPanel chartPanel = new ChartPanel(null);
        JPanel pan = new JPanel();

        for (int i = 0; i < tabPanel.getComponents().length; i++) {
            if (tabPanel.getComponents()[i] instanceof JPanel) {
                pan = (JPanel) tabPanel.getComponents()[i];

                components = pan.getComponents();
                for (int j = 0; j < components.length; j++) {

                    if (components[j] instanceof ChartPanel) {
                        chartPanel = (ChartPanel) components[j];
                    }

                }
            }
        }

        return chartPanel;
    }



    
    public void changeItems(ItemEvent e){


        JComboBox cb = (JComboBox) e.getSource();

                JTextField[] products = {

                getJTextFieldByJLabelText("Product Name: "),
                getJTextFieldByJLabelText("Calories: "),
                getJTextFieldByJLabelText("Fat(g): "),
                getJTextFieldByJLabelText("Protein(g): "),
                getJTextFieldByJLabelText("Carbohydrates: "),

                getJTextFieldByJLabelText("Product Name:  "),
                getJTextFieldByJLabelText("Calories:  "),
                getJTextFieldByJLabelText("Fat(g):  "),
                getJTextFieldByJLabelText("Protein(g):  "),
                getJTextFieldByJLabelText("Carbohydrates:  "),

                };

                JTextField[] exercises = {

                getJTextFieldByJLabelText("Exercise Name: "),
                getJTextFieldByJLabelText("Calories Burn(ccal/min): "),

                };

                if (cb.getSelectedItem() == "Other") {

                    for (JTextField field : products) {
                        field.setEditable(true);
                    }

                } else {
                    for (JTextField field : products) {
                        field.setEditable(false);
                    }
                }

                if (cb.getSelectedItem() == "Other ") {

                    for (JTextField field : exercises) {
                        field.setEditable(true);
                    }


                } else {

                    for (JTextField field : exercises) {
                        field.setEditable(false);
                    }
                }

    }
}
