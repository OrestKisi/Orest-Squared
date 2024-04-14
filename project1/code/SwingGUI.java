// Java Program to demonstrate 
// JTabbedPane with Labels 
import javax.swing.*;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.io.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
  
// Driver Class 
public class SwingGUI {
    public static JFrame window;
    public static ActionListener al;
    public static ItemListener il;
    public static JTabbedPane tabPanel;
    public static CSVWriter cw;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                window = new JFrame("Project 1 - Food Application - Orest Brukhal, Orest Kisi");
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setSize(500, 640);
                window.setResizable(false);

                cw = new CSVWriter();

                addActionListener();
                addItemListener();

                addTab();

                window.setVisible(true);
            }
        });
    }

    public static void addLabel(String text, int x, int y, int w, int h, JPanel panel){
        JLabel lbl1 = new JLabel(text, JLabel.LEFT);
        lbl1.setBounds(x, y, w, h);
        panel.add(lbl1);
    }


    public static void addTextField(int x, int y, int w, int h, JPanel panel, boolean editable){
        JTextField name = new JTextField();
        name.setBounds(x, y, w, h);
        name.setEditable(editable);
        panel.add(name);
    }


    public static void addRadioBttn(String text1, String text2, int x, int y, int w, int h, JPanel panel){
        JRadioButton rdbtn1 = new JRadioButton(text1);
        rdbtn1.setActionCommand(text1);
        JRadioButton rdbtn2 = new JRadioButton(text2);
        rdbtn2.setActionCommand(text2);
        rdbtn1.setBounds(x, y, w, h);
        rdbtn2.setBounds(x + 100, y, w, h); // Adjusted spacing
        ButtonGroup bgrp = new ButtonGroup();
        bgrp.add(rdbtn1);
        bgrp.add(rdbtn2);
        panel.add(rdbtn1);
        panel.add(rdbtn2);
    }


    public static void addTextArea(int x, int y, int w, int h, JPanel panel){
        JTextArea txtArea = new JTextArea();
        txtArea.setBounds(x, y, w, h);
        panel.add(txtArea);
    }

    public static void addBttn(String text, int x, int y, int w, int h, JPanel panel){
        JButton btn = new JButton(text);
        btn.setBounds(x, y, w, h);
        btn.addActionListener(al);
        panel.add(btn);
    }


    public static void addComboBox(int x, int y, int w, int h, JPanel panel, int id){
        String arr[] = {"null"};

        if(id == 0){
            arr = cw.getProductNames();
            arr[arr.length-1] = "Other";
        }
        if(id == 1){
            String arr1[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            arr = arr1;
        }

        JComboBox cb = new JComboBox(arr);
        cb.setBounds(x, y, w, h);
        cb.setSelectedIndex(0);
        cb.addItemListener(il);
        panel.add(cb);
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

     public static void addTab(){
                tabPanel = new JTabbedPane(); 

                addPanelToTab("My Progress", 0);
                addPanelToTab("Find Log", 1);
                addPanelToTab("Add Log", 2);
                addPanelToTab("Find Recipes", 3);
                addPanelToTab("Add Recipes", 4);

  
                // Add the JTabbedPane to the JFrame's content 
                window.add(tabPanel); 
     }

     public static void addPanelToTab(String name, int id){
        JPanel page1 = new JPanel(); 
        page1.setLayout(null);
        createTab(page1, id);
        tabPanel.addTab(name, page1); 
     }


     public static void createTab(JPanel win, int id){


        //my progress
        if(id == 0){
            addLabel("From: ",80,20,100,20, win);
            addDateChooser(240, 20, 100, 20, win);

            addLabel("To: ",80,40,100,20, win);
            addDateChooser(240, 40, 100, 20, win);

            addBttn("Check", 250, 60, 100, 30, win);

            addLabel("Description:   ", 80,100,120,20, win);
            addTextArea(200,100,200,100, win);
        }
        //find log
        else if(id == 1){
            addLabel("Log Date: ",80,20,100,20, win);
            addDateChooser(240, 20, 40, 20, win);

            
            addBttn("Search", 250, 40, 100, 30, win);
            
            addLabel("Description ", 80,100,120,20, win);
            addTextArea(200,100,200,100, win);
        }

        //add log
        else if(id == 2){
            addLabel("Date:  ",80,20,100,20, win);
            addDateChooser(240, 20, 40, 20, win);

            addLabel("Current weight:  ", 80,40,120,20, win);
            addTextField(200, 40, 200, 20, win, true);

            addLabel("Product Consumed:  ",80,60,100,20, win);
            addComboBox(200, 60, 200, 20, win, 0);

            addLabel("Product Name:  ",80,100,100,20, win);
            addTextField(200, 100, 200, 20, win, false);

            addLabel("Product Info:  ",80,130,100,20, win);
            addLabel("Calories:  ",200,120,100,20, win);
            addTextField(260, 120, 20, 20, win, false);
            addLabel("Fat(g):  ",290,120,100,20, win);
            addTextField(380, 120, 20, 20, win, false);
            addLabel("Protein(g):  ",200,140,100,20, win);
            addTextField(260, 140, 20, 20, win, false);
            addLabel("Carbohydrates:  ",290,140,100,20, win);
            addTextField(380, 140, 20, 20, win, false);


            addBttn("Add Product to Log", 200, 160, 200, 20, win);

            addLabel("Current:  ", 80,200,120,20, win);
            addTextArea(200,200,200,20, win);

            addBttn("Add Log", 200, 220, 100, 30, win);
        }

        //find recipes
        else if(id == 3){
            addLabel("Recipe Title:  ",80,20,100,20, win);
            addTextField(200, 20, 200, 20, win, true);
            

            addBttn("Search ", 250, 40, 100, 30, win);

            addLabel("Description:  ", 80,100,120,20, win);
            addTextArea(200,100,200,100, win);
        }

        //add recipes
        else if(id == 4){
            addLabel("Recipe Title: ",80,20,100,20, win);
            addTextField(200, 20, 200, 20, win, true);

            addLabel("Product: ",80,60,100,20, win);
            addComboBox(200, 60, 200, 20, win, 0);

            addLabel("Product Name: ",80,100,100,20, win);
            addTextField(200, 100, 200, 20, win, false);

            addLabel("Product Info: ",80,130,100,20, win);
            addLabel("Calories: ",200,120,100,20, win);
            addTextField(260, 120, 20, 20, win, false);
            addLabel("Fat(g): ",290,120,100,20, win);
            addTextField(380, 120, 20, 20, win, false);
            addLabel("Protein(g): ",200,140,100,20, win);
            addTextField(260, 140, 20, 20, win, false);
            addLabel("Carbohydrates: ",290,140,100,20, win);
            addTextField(380, 140, 20, 20, win, false);


            addBttn("Add Product to Recipe", 200, 160, 200, 20, win);

            addLabel("Current: ", 80,200,120,20, win);
            addTextArea(200,200,200,20, win);
            

            addBttn("Add Recipe", 200, 220, 100, 30, win);
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
                    if(b.getText() == "Add Log"){
                        System.out.println("Log added: ");
                    }

                    //add recipe tab
                    if(b.getText() == "Add Product to Recipe"){
                        //adding product to textarea
                        getJTextAreaByJLabelText("Current: ").append("1x - " + getJTextFieldByJLabelText("Product Name: ").getText() + "(" + getJTextFieldByJLabelText("Calories: ").getText() + "cal, " + getJTextFieldByJLabelText("Fat(g): ").getText() + "g of fat, " + getJTextFieldByJLabelText("Protein(g): ").getText() + "g of protein, " + getJTextFieldByJLabelText("Corbohydrates: ").getText() + " carbohydrates.");
                        try {

                            //adding product to food.csv
                            cw.write(new Food(getJTextFieldByJLabelText("Product Name: ").getText(), Integer.parseInt(getJTextFieldByJLabelText("Calories: ").getText()), Integer.parseInt(getJTextFieldByJLabelText("Fat(g): ").getText()), Integer.parseInt(getJTextFieldByJLabelText("Carbohydrates: ").getText()), Integer.parseInt(getJTextFieldByJLabelText("Protein(g): ").getText())));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        products.add(getJTextFieldByJLabelText("Product Name: ").getText());
                        products.add(1);
                    }
                    //add recipe tab
                    if(b.getText() == "Add Recipe"){    

                        //messaging on textarea
                        getJTextAreaByJLabelText("Current: ").setText("\"" +getJTextFieldByJLabelText("Recipe Title: ").getText() + "\" recipe added to food.csv");

                        try {
                            cw.write(new Recipe(getJTextFieldByJLabelText("Recipe Title: ").getText(), products));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        products.clear();
                    }
                    //add log
                    if(b.getText() == "Add Product to Log"){
                        //adding product to textarea
                        getJTextAreaByJLabelText("Current:  ").append("1x - " + getJTextFieldByJLabelText("Product Name:  ").getText() + "(" + getJTextFieldByJLabelText("Calories:  ").getText() + "cal, " + getJTextFieldByJLabelText("Fat(g):  ").getText() + "g of fat, " + getJTextFieldByJLabelText("Protein(g):  ").getText() + "g of protein, " + getJTextFieldByJLabelText("Corbohydrates:  ").getText() + " carbohydrates.");
                        
                        try {

                            //adding product to food.csv
                            cw.write(new Food(getJTextFieldByJLabelText("Product Name:  ").getText(), Integer.parseInt(getJTextFieldByJLabelText("Calories:  ").getText()), Integer.parseInt(getJTextFieldByJLabelText("Fat(g):  ").getText()), Integer.parseInt(getJTextFieldByJLabelText("Carbohydrates:  ").getText()), Integer.parseInt(getJTextFieldByJLabelText("Protein(g):  ").getText())));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        products.add(getJTextFieldByJLabelText("Product Name:  ").getText());
                        products.add(1);
                    }

                    //add log
                    if(b.getText() == "Add Log"){    


                        String[] str = getDateByLabelText("Date:  ");
                        double d = Double.parseDouble(getJTextFieldByJLabelText("Current weight:  ").getText());
                        System.out.print(d);
                        
                        Log log = new Log(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]), d);
                        Consumption con = new Consumption(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]), products);

                        try {
                            cw.write(log);
                            cw.write(con);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        getJTextAreaByJLabelText("Current:  ").setText("\"" +getJTextFieldByJLabelText("Recipe Title: ").getText() + "\" recipe added to food.csv");


                        products.clear();
                    }

                    //find recipe
                    if(b.getText() == "Search "){
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

                    //find log
                    if(b.getText() == "Search"){
                        try {

                            Element containsYear = cw.getByName(2, getDateByLabelText("Log Date: ")[0]);
                            Element containsMonth = cw.getByName(2, getDateByLabelText("Log Date: ")[1]);
                            Element containsDay = cw.getByName(2, getDateByLabelText("Log Date: ")[2]);


                            //if(containsDay == containsMonth && containsMonth == containsYear){
                            getJTextAreaByJLabelText("Description ").setText("Your weight on " + containsYear.toArray()[0] +" was " + containsYear.toArray()[2]);
                                

                            //}
                                
                            Element containsYear1 = cw.getByName(3, getDateByLabelText("Log Date: ")[0]);
                            Element containsMonth1 = cw.getByName(3, getDateByLabelText("Log Date: ")[1]);
                            Element containsDay1 = cw.getByName(3, getDateByLabelText("Log Date: ")[2]);

                            getJTextAreaByJLabelText("Description ").append("\nYour have consumed: ");
                            for (int i = 3; i < containsYear1.toArray().length; i+=2) {
                                getJTextAreaByJLabelText("Description ").append("\n" + containsYear1.toArray()[i] + " - " + containsYear1.toArray()[i+1]);
                            }

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }


                    //My Progress tab
                    if(b.getText() == "Check"){

                        String[] date0 = getDateByLabelText("From: ");
                        String[] date1 = getDateByLabelText("To: ");

                        Element containsYear = null;
                        Element containsYear2 = null;

                        try {
                            containsYear = cw.getByName(2, date0[0]);
                            containsYear2 = cw.getByName(2, date1[0]);
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
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



// Add this class inside your SwingGUI class or import it separately
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



class CSVWriter {

    public void write(Element el) throws IOException{

        String[] data = el.toArray();
        File csvFile = null;

        if(el instanceof Log || el instanceof Consumption){
            csvFile = new File("log.csv");
        }
        else {
            csvFile = new File("food.csv");
        }
        
        FileWriter fileWriter = new FileWriter(csvFile, true);


            StringBuilder line = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                line.append(data[i].replaceAll("\"","\"\""));
                if (i != data.length - 1) {
                    line.append(',');
                }
            }
            line.append("\n");
            fileWriter.write(line.toString());
        
        fileWriter.close();

    }

    public List<List<String>> reading(int id) throws FileNotFoundException, IOException{

        String name = "";
        if(id == 1){
            name = "food.csv";
        }
        else {
            name = "log.csv";
        }

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(name))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
                
            }

        }
        return records;
    }

    public Element getByName(int id, String name) throws FileNotFoundException, IOException{

        Element food = null;
        List<List<String>> list = null;

        //reading 1 = food.csv
        //else = log.csv

        if(id == 1 || id == 0){
            list = reading(1);
        }
        else {
            list = reading(0);
        }
        
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).contains(name)){
                
                List<String> inList = list.get(i);
                
                if(id == 0 && inList.contains("b")){
                    food = new Food(name, Integer.parseInt(inList.get(2)), Integer.parseInt(inList.get(3)), Integer.parseInt(inList.get(4)), Integer.parseInt(inList.get(5)));
                }
                else if(id == 1 && inList.contains("r")){
                    ArrayList al = new ArrayList<>();
                    for (int j = 1; j < inList.size(); j++) {
                        al.add(inList.get(j));
                    }
                    food = new Recipe(name, al);
                }
                else if(id == 2 && inList.contains("w") && inList.get(0).contains(name)){
                    food = new Log(Integer.parseInt(inList.get(0)), Integer.parseInt(inList.get(1)), Integer.parseInt(inList.get(2)), Double.parseDouble(inList.get(4)));
                }
                else if(id == 3 && inList.contains("f")){
                    ArrayList al = new ArrayList<>();
                    for (int j = 3; j < inList.size(); j++) {
                        al.add(inList.get(j));
                    }
                    food = new Consumption(Integer.parseInt(inList.get(0)), Integer.parseInt(inList.get(1)), Integer.parseInt(inList.get(2)), al);
                }
                
            }
        }
        return food;
    }

    public String[] getProductNames(){

        List<List<String>> list = null;

        try {
            list = reading(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] arr = null;
        ArrayList al = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            List<String> inList = list.get(i);

            for (int j = 0; j < inList.size(); j++) {
                if(inList.contains("b")){
                    al.add(inList.get(1));
                }
            }
        }

        arr = new String[al.size()];
        for (int i = 0; i < al.size(); i++) {
            arr[i] = (String)al.get(i);
        }
        return arr;
    }
}

/**
 *  
 */
class Element {

    public String[] toArray() {
        String[] arr = null;
        return arr;
    }
}

class Food  extends Element{

    private String name;
    private int ccal, fat, carbohydrates, protein;

    public Food(String _name, int _ccal, int _fat, int _carbohydrates, int _protein){
        name = _name; ccal = _ccal; fat = _fat; carbohydrates = _carbohydrates; protein = _protein;
    }

    public String[] toArray() {
        String[] arr = {"b", name, String.valueOf(ccal), String.valueOf(fat), String.valueOf(protein), String.valueOf(carbohydrates)};
        return arr;
    }
    
}

class Recipe extends Element{

    private String name;
    private ArrayList na;

    public Recipe(String _name, ArrayList names_amounts){
        name = _name;  na = names_amounts;
    }

    public String[] toArray() {

        String[] arr = null;
        ArrayList arli = new ArrayList<>();


        arli.add("r"); arli.add(name);

        for (int i = 0; i < na.size(); i++) {
            arli.add(na.get(i));
        }

        arr = new String[arli.size()];

        for (int i = 0; i < arli.size(); i++) {
            arr[i] = String.valueOf(arli.get(i));
        }

        return arr;
    }
    
}

class Log extends Element{

    private String date;
    private double weight;

    public Log(int year, int month, int day, double _weight){
        date = String.valueOf(year) + "," + String.valueOf(month) + "," + String.valueOf(day);
        weight = _weight;
    }

    public String[] toArray() {
        String[] arr = {date, "w", String.valueOf(weight)};
        return arr;
    }
}

class Consumption extends Element{

    private String date;
    private ArrayList na;

    public Consumption(int year, int month, int day, ArrayList names_amounts){
        date = String.valueOf(year) + "," + String.valueOf(month) + "," + String.valueOf(day);
        na = names_amounts;
    }


    public String[] toArray() {
        
        String[] arr = null;
        ArrayList arli = new ArrayList<>();

        arli.add(date); arli.add("f");

        for (int i = 0; i < na.size(); i++) {
            arli.add(na.get(i));
        }

        arr = new String[arli.size()];

        for (int i = 0; i < arli.size(); i++) {
            arr[i] = String.valueOf(arli.get(i));
        }
        return arr;
    }
}