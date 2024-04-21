
// Java Program to demonstrate 
// JTabbedPane with Labels 
import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.util.Date;
import java.util.Properties;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

// Driver Class 
public class SwingGUI {

    public static ActionListener al;
    public static ItemListener il;
    public static JTabbedPane tabPanel;
    public static JFrame window;
    public static CSVWriter cw;
    public static ApplicationFacade af;

    public SwingGUI(JFrame _window) {

        cw = new CSVWriter();
        window = _window;

        addActionListener();
        addItemListener();
        
        addTab();
        
        af = new ApplicationFacade(tabPanel, cw);
        
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
            items = cw.getExerciseNames();
            String[] temp = Arrays.copyOf(items, items.length + 1);
            temp[temp.length - 1] = "Other  ";
            items = temp;

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
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBounds(x, y, w, h);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(x, y, w, h);

        panel.add(scrollPane);
    }

    public static void addDateChooser(int x, int y, int w, int h, JPanel win) {
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        datePicker.addActionListener(e -> {
            Date selectedDate = (Date) datePicker.getModel().getValue();
            if (selectedDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // You can change the date format as needed
                String formattedDate = sdf.format(selectedDate);
                datePicker.getJFormattedTextField().setText(formattedDate);
            }
        });

        datePicker.setBounds(x, y, w, h);

        win.add(datePicker);
    }

    public static void addXYChart(int x, int y, int w, int h, JPanel win) {
        // Create dataset
        XYSeries series = new XYSeries("Progress");
        series.add(100, 0);
        series.add(0, 300);
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        // Create XY chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "My Progress", // chart title
                "Date", // x-axis label
                "Calories", // y-axis label
                dataset // dataset
        );

        // Set font for chart title
        Font font = new Font("Arial", Font.PLAIN, 14);
        chart.getTitle().setFont(font);

        // Get plot and set fonts
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.getDomainAxis().setLabelFont(font); // X-axis label font
        plot.getRangeAxis().setLabelFont(font); // Y-axis label font
        plot.getDomainAxis().setTickLabelFont(font); // X-axis tick label font
        plot.getRangeAxis().setTickLabelFont(font); // Y-axis tick label font

        // Set background color and outline
        plot.setBackgroundPaint(Color.GRAY);
        plot.setOutlinePaint(Color.BLUE);
        plot.setOutlineStroke(new BasicStroke(3));

        // Set line color
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);

        // Customize the X-axis to display dates
        DateAxis domainAxis = new DateAxis("Date");
        domainAxis.setDateFormatOverride(new SimpleDateFormat("yyyy/MM/dd"));
        plot.setDomainAxis(domainAxis);

        // Create ChartPanel to display the chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(w, h));
        chartPanel.setBounds(x, y, w, h);

        // Add ChartPanel to the specified JPanel
        win.add(chartPanel);
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

    public static void createTab(JPanel win, int id) {

        // my progress
        if (id == 0) {
            addLabel("From: ", 50, 20, 100, 20, win);
            addDateChooser(170, 20, 150, 20, win);

            addLabel("To: ", 50, 60, 100, 20, win);
            addDateChooser(170, 60, 150, 20, win);

            addBttn("Check", 300, 100, 100, 30, win);

            addLabel("Description:   ", 50, 140, 120, 20, win);
            addTextArea(170, 140, 400, 350, win);

            addXYChart(0, 500, 590, 400, win);
        }
        // find log
        else if (id == 1) {
            addLabel("Log Date: ", 50, 20, 100, 20, win);
            addDateChooser(170, 20, 150, 20, win);

            addBttn("Search", 300, 60, 100, 30, win);

            addLabel("Description ", 50, 100, 120, 20, win);
            addTextArea(170, 100, 350, 500, win);
        }

        // add log
        else if (id == 2) {
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

            addLabel("Calorie Limit: ", 50, 320, 100, 20, win);
            addTextField(170, 320, 150, 20, win, true);

            addLabel("Exersice Performed: ", 50, 380, 120, 20, win);
            addComboBox(170, 380, 150, 20, win, 1);

            addLabel("Excersice Name: ", 50, 420, 100, 20, win);
            addTextField(170, 420, 150, 20, win, false);
            addLabel("Calories Burn(ccal/min): ", 50, 440, 100, 20, win);
            addTextField(170, 440, 150, 20, win, false);

            addLabel("Time Spent: ", 50, 480, 100, 20, win);
            addTextField(170, 480, 150, 20, win, true);

            addLabel("Current:  ", 50, 600, 120, 20, win);
            addTextArea(170, 600, 350, 200, win);

            addBttn("Add Log", 170, 800, 100, 30, win);
        }

        // find recipes
        else if (id == 3) {
            addLabel("Recipe Title:  ", 50, 20, 100, 20, win);
            addTextField(170, 20, 150, 20, win, true);

            addBttn("Search ", 330, 60, 100, 30, win);

            addLabel("Description:  ", 50, 100, 120, 20, win);
            addTextArea(170, 100, 400, 500, win);
        }

        // add recipes
        else if (id == 4) {
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
            addTextArea(170, 260, 350, 200, win);

            addBttn("Add Recipe", 170, 480, 100, 30, win);
        }

        // add exercise
        else if (id == 5) {
            addLabel("Exercise Title: ", 50, 20, 100, 20, win);
            addTextField(170, 20, 150, 20, win, true);

            addLabel("Calories:   ", 50, 60, 100, 20, win);
            addTextField(170, 60, 150, 20, win, true);

            addBttn("Add Exercise", 170, 100, 200, 30, win);
        }
    }

    public static void addActionListener() {
        al = new ActionListener() {
            List<String> products = new ArrayList<>();

            public void actionPerformed(java.awt.event.ActionEvent e) {
                JButton b = (JButton) e.getSource();

                // Check if the button is "Add Product to Recipe"
                if (b.getText().equals("Add Product to Recipe")) {
                    products = af.addProductsToRecipe(products);
                }
                // Add Recipe tab
                if (b.getText().equals("Add Recipe")) {
                    products = af.addRecipe(products);
                }

                // Add Exercise tab
                if (b.getText().equals("Add Exercise")) {
                    af.addExercise();
                }
                // Add Product to Log
                if (b.getText().equals("Add Product to Log")) {

                    products = af.addProductsToLog(products);
                }

                // Add Log
                if (b.getText().equals("Add Log")) {

                    products = af.addLog(products);
                }

                // Find Recipe
                if (b.getText().equals("Search ")) {
                    af.findRecipe();
                }

                // Find Log
                if (b.getText().equals("Search")) {
                    af.findLog();
                }

                // My Progress tab
                if (b.getText().equals("Check")) {
                    af.checkProgress();
                }

            }
        };
    }






    public static void addItemListener() {
        il = new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                af.changeItems(e);
            }
        };
    }

}