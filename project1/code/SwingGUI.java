// Java Program to demonstrate 
// JTabbedPane with Labels 
import javax.swing.*;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
  
// Driver Class 
public class SwingGUI {
    public static JFrame window;
    public static ActionListener al;
    public static ItemListener il;
    public static JTabbedPane tabPanel;
    public static CSVWriter cw;

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize theme. Using fallback." );
        }


        SwingUtilities.invokeLater(() -> {
            window = new JFrame("Project 1 - Food Application - Orest Brukhal, Orest Kisi");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(600, 700);
            window.setResizable(false);

            cw = new CSVWriter();

            addActionListener();
            addItemListener();

            addTab();

            window.setVisible(true);
        });
    }

    public static void addLabel(String text, int x, int y, int w, int h, JPanel panel) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, w, h);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label);
    }

    public static void addTextField(int x, int y, int w, int h, JPanel panel, boolean editable) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, w, h);
        textField.setEditable(editable);
        panel.add(textField);
    }

    public static void addBttn(String text, int x, int y, int w, int h, JPanel panel) {
        JButton button = new JButton(text);
        button.setBounds(x, y, w, h);
        button.addActionListener(al);
        panel.add(button);
    }

    public static void addComboBox(int x, int y, int w, int h, JPanel panel, int id) {
        JComboBox<String> comboBox = new JComboBox<>();
        String[] items;
    
        if (id == 0) {
            items = cw.getProductNames();

            String[] temp = Arrays.copyOf(items, items.length + 1);
            temp[temp.length - 1] = "Other";
            items = temp;
        } else if (id == 1) {
            items = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        } else {
            items = new String[0];
        }
    
        comboBox.setModel(new DefaultComboBoxModel<>(items));
        comboBox.setBounds(x, y, w, h);
        comboBox.setSelectedIndex(0);
        comboBox.addItemListener(il);
        panel.add(comboBox);
    }

    public static void addTextArea(int x, int y, int w, int h, JPanel panel) {
        JTextArea textArea = new JTextArea();
        textArea.setBounds(x, y, w, h);
        textArea.setEditable(false);
        panel.add(textArea);
    }

    public static void addDateChooser(int x, int y, int w, int h, JPanel win) {
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        datePicker.setBounds(x, y, w, h);
        win.add(datePicker);
    }

    public static void addTab() {
        tabPanel = new JTabbedPane();

        addPanelToTab("My Progress", 0);
        addPanelToTab("Find Log", 1);
        addPanelToTab("Add Log", 2);
        addPanelToTab("Find Recipes", 3);
        addPanelToTab("Add Recipes", 4);
        addPanelToTab("Add Exercise", 5);

        window.add(tabPanel);
    }

    public static void addPanelToTab(String name, int id) {
        JPanel page = new JPanel();
        page.setLayout(null);
        createTab(page, id);
        tabPanel.addTab(name, page);
    }


    public static void createTab(JPanel win, int id){

        // my progress
        if(id == 0){
            addLabel("From: ", 50, 20, 100, 20, win);
            addDateChooser(170, 20, 150, 20, win);
    
            addLabel("To: ", 50, 60, 100, 20, win);
            addDateChooser(170, 60, 150, 20, win);
    
            addBttn("Check", 300, 100, 100, 30, win);
    
            addLabel("Description:   ", 50, 140, 120, 20, win);
            addTextArea(170, 140, 350, 100, win);
        }
        // find log
        else if(id == 1){
            addLabel("Log Date: ", 50, 20, 100, 20, win);
            addDateChooser(170, 20, 150, 20, win);
    
            addBttn("Search", 300, 60, 100, 30, win);
    
            addLabel("Description ", 50, 100, 120, 20, win);
            addTextArea(170, 100, 350, 300, win);
        }
    
        // add log
        else if(id == 2){
            addLabel("Date:  ", 50, 20, 100, 20, win);
            addDateChooser(170, 20, 150, 20, win);
    
            addLabel("Current weight:  ", 50, 60, 120, 20, win);
            addTextField(170, 60, 150, 20, win, true);
    
            addLabel("Product Consumed:  ", 50, 100, 120, 20, win);
            addComboBox(170, 100, 150, 20, win, 0);
    
            addLabel("Product Name:  ", 50, 140, 100, 20, win);
            addTextField(170, 140, 150, 20, win, false);
    
            addLabel("Product Info:  ", 50, 180, 100, 20, win);
            addLabel("Calories:  ", 170, 180, 100, 20, win);
            addTextField(250, 180, 50, 20, win, false);
            addLabel("Fat(g):  ", 320, 180, 100, 20, win);
            addTextField(400, 180, 50, 20, win, false);
            addLabel("Protein(g):  ", 170, 220, 100, 20, win);
            addTextField(250, 220, 50, 20, win, false);
            addLabel("Carbohydrates:  ", 320, 220, 100, 20, win);
            addTextField(400, 220, 50, 20, win, false);
    
            addBttn("Add Product to Log", 170, 260, 200, 30, win);
    
            addLabel("Current:  ", 50, 300, 120, 20, win);
            addTextArea(170, 300, 350, 100, win);
    
            addBttn("Add Log", 170, 440, 100, 30, win);
        }
    
        // find recipes
        else if(id == 3){
            addLabel("Recipe Title:  ", 50, 20, 100, 20, win);
            addTextField(170, 20, 150, 20, win, true);
    
            addBttn("Search ", 330, 60, 100, 30, win);
    
            addLabel("Description:  ", 50, 100, 120, 20, win);
            addTextArea(170, 100, 350, 100, win);
        }
    
        // add recipes
        else if(id == 4){
            addLabel("Recipe Title: ", 50, 20, 100, 20, win);
            addTextField(170, 20, 150, 20, win, true);
    
            addLabel("Product: ", 50, 60, 100, 20, win);
            addComboBox(170, 60, 150, 20, win, 0);
    
            addLabel("Product Name: ", 50, 100, 100, 20, win);
            addTextField(170, 100, 150, 20, win, false);
    
            addLabel("Product Info: ", 50, 140, 100, 20, win);
            addLabel("Calories: ", 170, 140, 100, 20, win);
            addTextField(250, 140, 50, 20, win, false);
            addLabel("Fat(g): ", 320, 140, 100, 20, win);
            addTextField(400, 140, 50, 20, win, false);
            addLabel("Protein(g): ", 170, 180, 100, 20, win);
            addTextField(250, 180, 50, 20, win, false);
            addLabel("Carbohydrates: ", 320, 180, 100, 20, win);
            addTextField(400, 180, 50, 20, win, false);
    
            addBttn("Add Product to Recipe", 170, 220, 200, 30, win);
    
            addLabel("Current: ", 50, 260, 100, 20, win);
            addTextArea(170, 260, 350, 20, win);
    
            addBttn("Add Recipe", 170, 300, 100, 30, win);
        }

        // add exercise
        else if(id == 5){
            addLabel("Exercise Title: ", 50, 20, 100, 20, win);
            addTextField(170, 20, 150, 20, win, true);
            
            addLabel("Calories:   ", 50, 60, 100, 20, win);
            addTextField(170, 60, 150, 20, win, true);
    
            addBttn("Add Exercise", 170, 100, 200, 30, win);
        }
    }
    
    

     public static JTextField getJTextFieldByJLabelText(String title) {
        Component[] components;
        JTextField txt = new JTextField();
        JPanel pan = new JPanel();


        for (int i = 0; i < tabPanel.getComponents().length; i++) {
            if(tabPanel.getComponents()[i] instanceof JPanel){
                pan = (JPanel)tabPanel.getComponents()[i];

                components = pan.getComponents();            
                for (int j = 0; j < components.length; j++) {

                    if(components[j] instanceof JLabel){
                        JLabel jl = (JLabel)components[j];
                        if(jl.getText().equals(title)){
                            txt = (JTextField)components[j+1];
                        }
                    }
                
                }
            }
        }

        return txt;
    }

    public static JTextArea getJTextAreaByJLabelText(String title) {
        Component[] components;
        JTextArea txt = new JTextArea();
        JPanel pan = new JPanel();


        for (int i = 0; i < tabPanel.getComponents().length; i++) {
            if(tabPanel.getComponents()[i] instanceof JPanel){
                pan = (JPanel)tabPanel.getComponents()[i];

                components = pan.getComponents();            
                for (int j = 0; j < components.length; j++) {

                    if(components[j] instanceof JLabel){
                        JLabel jl = (JLabel)components[j];
                        if(jl.getText().equals(title)){
                            txt = (JTextArea)components[j+1];
                        }
                    }
                
                }
            }
        }

        return txt;
    }

    public static String[] getDateByLabelText(String title) {
        Component[] components;
        JDatePickerImpl datePicker = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), new Properties()), null);
        JTextField txt3 = new JTextField();
    
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
                            } else if (components[j + 1] instanceof JTextField) {
                                txt3 = (JTextField) components[j + 1];
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
                // Handle the case where the split result does not contain enough elements
                // You might want to set default values or handle this case differently based on your requirements
            }
        }
    
        return date;
    }
    
    
    public static void addActionListener(){
        al = new ActionListener() {
            ArrayList products = new ArrayList<>();
    
            public void actionPerformed(java.awt.event.ActionEvent e) {
                JButton b = (JButton)e.getSource();
                if(b.getText().equals("Add Log")){
                    System.out.println("Log added: ");
                }
                // Add Product to Recipe
                if(b.getText().equals("Add Product to Recipe")){
                    // Adding product to textarea
                    getJTextAreaByJLabelText("Current: ").append("1x - " + getJTextFieldByJLabelText("Product Name: ").getText() + "(" + getJTextFieldByJLabelText("Calories: ").getText() + "cal, " + getJTextFieldByJLabelText("Fat(g): ").getText() + "g of fat, " + getJTextFieldByJLabelText("Protein(g): ").getText() + "g of protein, " + getJTextFieldByJLabelText("Carbohydrates: ").getText() + " carbohydrates.");
                    try {
                        // Adding product to food.csv
                        cw.write(new Food(getJTextFieldByJLabelText("Product Name: ").getText(), Integer.parseInt(getJTextFieldByJLabelText("Calories: ").getText()), Integer.parseInt(getJTextFieldByJLabelText("Fat(g): ").getText()), Integer.parseInt(getJTextFieldByJLabelText("Carbohydrates: ").getText()), Integer.parseInt(getJTextFieldByJLabelText("Protein(g): ").getText())));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    products.add(getJTextFieldByJLabelText("Product Name: ").getText());
                    products.add(1);
                }
                // Add Recipe tab
                if(b.getText().equals("Add Recipe")){
                    // Messaging on textarea
                    getJTextAreaByJLabelText("Current: ").setText("\"" +getJTextFieldByJLabelText("Recipe Title: ").getText() + "\" recipe added to food.csv");
                    try {
                        cw.write(new Recipe(getJTextFieldByJLabelText("Recipe Title: ").getText(), products));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    products.clear();
                }
                // Add Exercise tab
                if(b.getText().equals("Add Exercise")){    
                    try {
                        // Adding exercise to exercise.csv
                        cw.write(new Exercise(getJTextFieldByJLabelText("Exercise Title: ").getText(), Integer.parseInt(getJTextFieldByJLabelText("Calories:   ").getText())));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                // Add Product to Log
                if(b.getText().equals("Add Product to Log")){
                    // Adding product to textarea
                    getJTextAreaByJLabelText("Current:  ").append("1x - " + getJTextFieldByJLabelText("Product Name:  ").getText() + "(" + getJTextFieldByJLabelText("Calories:  ").getText() + "cal, " + getJTextFieldByJLabelText("Fat(g):  ").getText() + "g of fat, " + getJTextFieldByJLabelText("Protein(g):  ").getText() + "g of protein, " + getJTextFieldByJLabelText("Carbohydrates:  ").getText() + " carbohydrates.");
                    try {
                        // Adding product to food.csv
                        cw.write(new Food(getJTextFieldByJLabelText("Product Name:  ").getText(), Integer.parseInt(getJTextFieldByJLabelText("Calories:  ").getText()), Integer.parseInt(getJTextFieldByJLabelText("Fat(g):  ").getText()), Integer.parseInt(getJTextFieldByJLabelText("Carbohydrates:  ").getText()), Integer.parseInt(getJTextFieldByJLabelText("Protein(g):  ").getText())));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    products.add(getJTextFieldByJLabelText("Product Name:  ").getText());
                    products.add(1);
                }
                // Find Recipe
                if(b.getText().equals("Search ")){
                    try {
                        String str = getJTextFieldByJLabelText("Recipe Title:  ").getText();
                        Element el = cw.getByName(1, str);
                        getJTextAreaByJLabelText("Description:  ").setText(str + "\nRecipe Contains: ");
                        for (int i = 3; i < el.toArray().length; i+=2) {
                            getJTextAreaByJLabelText("Description:  ").append("\n" + el.toArray()[i] + " - " + el.toArray()[i+1]);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }




               // Add Log
            if (b.getText().equals("Add Log")) {
                String[] str = getDateByLabelText("Date:  ");
                double d = Double.parseDouble(getJTextFieldByJLabelText("Current weight:  ").getText());
                Log log = new Log(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]), d);
                try {
                    cw.write(log);
                    cw.write(new Consumption(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]), products));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                getJTextAreaByJLabelText("Current:  ").setText("\"" + getJTextFieldByJLabelText("Recipe Title: ").getText() + "\" recipe added to food.csv");
                products.clear();
            }
            // Find Recipe
            if (b.getText().equals("Search ")) {
                try {
                    String str = getJTextFieldByJLabelText("Recipe Title:  ").getText();
                    Element el = cw.getByName(1, str);
                    getJTextAreaByJLabelText("Description:  ").setText(str + "\nRecipe Contains: ");
                    for (int i = 3; i < el.toArray().length; i += 2) {
                        getJTextAreaByJLabelText("Description:  ").append("\n" + el.toArray()[i] + " - " + el.toArray()[i + 1]);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            // Find Log
            if (b.getText().equals("Search")) {
                try {
                    // Get the year, month, and day from the user input
                    String[] date = getDateByLabelText("Log Date: ");

                    // Ensure that the date array has three elements
                    if (date.length == 3) {
                        // Attempt to retrieve the log for the specified date
                        Element containsYear = cw.getByName(3, date[0]);
                        Element containsMonth = cw.getByName(3, date[1]);
                        Element containsDay = cw.getByName(3, date[2]);

                        // Check if any of the elements is null before accessing toArray()
                        if (containsYear != null && containsMonth != null && containsDay != null) {
                            // Display log details
                            getJTextAreaByJLabelText("Description ").setText("Your weight on " + containsYear.toArray()[0] + " was " + containsYear.toArray()[2]);

                            Element containsYear1 = cw.getByName(4, date[0]);
                            Element containsMonth1 = cw.getByName(4, date[1]);
                            Element containsDay1 = cw.getByName(4, date[2]);

                            getJTextAreaByJLabelText("Description ").append("\nYou have consumed: ");
                            for (int i = 3; i < containsYear1.toArray().length; i += 2) {
                                getJTextAreaByJLabelText("Description ").append("\n" + containsYear1.toArray()[i] + " - " + containsYear1.toArray()[i + 1]);
                            }
                        } else {
                            // Handle case where log for the given date was not found
                            getJTextAreaByJLabelText("Description ").setText("Log not found for the specified date.");
                        }
                    } else {
                        // Handle case where user input for date is incomplete
                        getJTextAreaByJLabelText("Description ").setText("Please provide a complete date.");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            // My Progress tab
            if (b.getText().equals("Check")) {
                String[] date0 = getDateByLabelText("From: ");
                String[] date1 = getDateByLabelText("To: ");
                Element containsYear = null;
                Element containsYear2 = null;
                try {
                    containsYear = cw.getByName(3, date0[0]);
                    containsYear2 = cw.getByName(3, date1[0]);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                JTextArea ta = getJTextAreaByJLabelText("Description:   ");
                ta.setText(containsYear.toArray()[0] + " - " + containsYear.toArray()[2] + "kg");
                ta.append("\n" + containsYear2.toArray()[0] + " - " + containsYear2.toArray()[2] + "kg");
            }
        }
    };
    }
    


     public static void addItemListener(){
        il = new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                JComboBox cb = (JComboBox)e.getSource();

                JTextField other = getJTextFieldByJLabelText("Product Name: ");
                JTextField cal = getJTextFieldByJLabelText("Calories: ");
                JTextField fat = getJTextFieldByJLabelText("Fat(g): ");
                JTextField prot = getJTextFieldByJLabelText("Protein(g): ");
                JTextField carbo = getJTextFieldByJLabelText("Carbohydrates: ");

                JTextField other1 = getJTextFieldByJLabelText("Product Name:  ");
                JTextField cal1 = getJTextFieldByJLabelText("Calories:  ");
                JTextField fat1 = getJTextFieldByJLabelText("Fat(g):  ");
                JTextField prot1 = getJTextFieldByJLabelText("Protein(g):  ");
                JTextField carbo1 = getJTextFieldByJLabelText("Carbohydrates:  ");

                if (cb.getSelectedItem() == "Other"){
                    
                    other.setEditable(true);
                    cal.setEditable(true);
                    fat.setEditable(true);
                    prot.setEditable(true);
                    carbo.setEditable(true);
                    other1.setEditable(true);
                    cal1.setEditable(true);
                    fat1.setEditable(true);
                    prot1.setEditable(true);
                    carbo1.setEditable(true);
                }
                else {
                    other.setEditable(false);
                    cal.setEditable(false);
                    fat.setEditable(false);
                    prot.setEditable(false);
                    carbo.setEditable(false);
                    other1.setEditable(false);
                    cal1.setEditable(false);
                    fat1.setEditable(false);
                    prot1.setEditable(false);
                    carbo1.setEditable(false);
                }
            
         } };
    }
} 



class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy,MM,dd");

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parse(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null && value instanceof Date) {
            return dateFormatter.format(value);
        }
        return "";
    }
}