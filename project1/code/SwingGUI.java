import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class SwingGUI {

    private JFrame window;
    private JTabbedPane tabPanel;
    private CSVWriter csvWriter;
    private static ApplicationFacade applicationFacade;
    private ComponentFactory factory;
    private static ActionListener al;
    private static ItemListener il;

    public SwingGUI(JFrame window) {
        factory = new ComponentFactory();
        this.window = window;
        csvWriter = new CSVWriter();
        initializeGUI();
        applicationFacade = new ApplicationFacade(tabPanel, csvWriter);
    }

    private void initializeGUI() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLayout(new BorderLayout());

        tabPanel = new JTabbedPane();
        initializeTabs();

        window.add(tabPanel, BorderLayout.CENTER);
        window.setVisible(true);
    }

    private void initializeTabs() {
        String[] tabNames = {"My Progress", "Find Log", "Add Log", "Find Recipes", "Add Recipes", "Add Exercise"};
        for (int i = 0; i < tabNames.length; i++) {
            JPanel panel = new JPanel(null);

            addActionListener();
            addItemListener();
            createTab(panel, i);
            
            tabPanel.addTab(tabNames[i], panel);
        }
    }

    private void createTab(JPanel panel, int id) {
        switch (id) {
            case 0:
                addComponentsForMyProgress(panel);
                break;
            case 1:
                addComponentsForFindLog(panel);
                break;
            case 2:
                addComponentsForAddLog(panel);
                break;
            case 3:
                addComponentsForFindRecipes(panel);
                break;
            case 4:
                addComponentsForAddRecipes(panel);
                break;
            case 5:
                addComponentsForAddExercise(panel);
                break;
        }
    }

    private void addComponentsForMyProgress(JPanel panel) {
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "From: ", 50, 20, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.DATE_CHOOSER, 170, 20, 150, 20));
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "To: ", 50, 60, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.DATE_CHOOSER, 170, 60, 150, 20));
        panel.add((JButton) factory.createComponent(ComponentFactory.ComponentType.BUTTON, "Check", 300, 100, 100, 30, al));
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Description:   ", 50, 140, 120, 20));
        panel.add((JScrollPane) factory.createComponent(ComponentFactory.ComponentType.TEXT_AREA, 170, 140, 400, 350));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.XY_CHART, 0, 500, 590, 400));
    }

    private void addComponentsForFindLog(JPanel panel) {
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Log Date: ", 50, 20, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.DATE_CHOOSER, 170, 20, 150, 20));
        panel.add((JButton) factory.createComponent(ComponentFactory.ComponentType.BUTTON, "Search", 300, 60, 100, 30, al));
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Description ", 50, 100, 120, 20));
        panel.add((JScrollPane) factory.createComponent(ComponentFactory.ComponentType.TEXT_AREA, 170, 100, 350, 300));
    }

    private void addComponentsForAddLog(JPanel panel) {
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Date:  ", 50, 20, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.DATE_CHOOSER, 170, 20, 150, 20));
    
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Current weight:  ", 50, 60, 120, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 170, 60, 150, 20, true));
    
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Product Consumed:  ", 50, 100, 120, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.COMBO_BOX, 170, 100, 150, 20, csvWriter.getProductNames(), 0, il));
    
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Product Name:  ", 50, 140, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 170, 140, 150, 20, false));
    
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Product Info:  ", 50, 180, 100, 20));
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Calories:  ", 170, 180, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 250, 180, 50, 20, false));
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Fat(g):  ", 320, 180, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 400, 180, 50, 20, false));
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Protein(g):  ", 170, 220, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 250, 220, 50, 20, false));
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Carbohydrates:  ", 320, 220, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 400, 220, 50, 20, false));
    
        panel.add((JButton) factory.createComponent(ComponentFactory.ComponentType.BUTTON, "Add Product to Log", 170, 260, 200, 30), al);
        
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Calorie Limit: ", 50, 320, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 170, 320, 150, 20, true));
    
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Exercise Performed: ", 50, 380, 120, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.COMBO_BOX, 170, 380, 150, 20, csvWriter.getExerciseNames(), 1, il));
    
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Exercise Name: ", 50, 420, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 170, 420, 150, 20, false));
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Calories Burn(ccal/min): ", 50, 440, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 170, 440, 150, 20, false));
    
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Time Spent: ", 50, 480, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 170, 480, 150, 20, true));
    
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Current:  ", 50, 520, 120, 20));
        panel.add((JScrollPane) factory.createComponent(ComponentFactory.ComponentType.TEXT_AREA, 170, 520, 100, 200));
    
        panel.add((JButton) factory.createComponent(ComponentFactory.ComponentType.BUTTON, "Add Log", 170, 620, 100, 30, al));
    }

    private void addComponentsForFindRecipes(JPanel panel) {
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Recipe Title:  ", 50, 20, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 170, 20, 150, 20, true));
    
        panel.add((JButton) factory.createComponent(ComponentFactory.ComponentType.BUTTON, "Search ", 330, 60, 100, 30, al));
    
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Description:  ", 50, 100, 120, 20));
        panel.add((JScrollPane) factory.createComponent(ComponentFactory.ComponentType.TEXT_AREA, 170, 100, 400, 500));
    }

    private void addComponentsForAddRecipes(JPanel panel) {
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Recipe Title: ", 50, 20, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 170, 20, 150, 20, true));
    
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Product: ", 50, 60, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.COMBO_BOX, 170, 60, 150, 20, csvWriter.getProductNames(), 0, il));
    
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Product Name: ", 50, 100, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 170, 100, 150, 20, false));
    
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Product Info: ", 50, 140, 100, 20));
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Calories: ", 170, 140, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 250, 140, 50, 20, false));
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Fat(g): ", 320, 140, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 400, 140, 50, 20, false));
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Protein(g): ", 170, 180, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 250, 180, 50, 20, false));
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Carbohydrates: ", 320, 180, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 400, 180, 50, 20, false));
    
        panel.add((JButton) factory.createComponent(ComponentFactory.ComponentType.BUTTON, "Add Product to Recipe", 170, 220, 200, 30, al));
    
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Current: ", 50, 260, 100, 20));
        panel.add((JScrollPane) factory.createComponent(ComponentFactory.ComponentType.TEXT_AREA, 170, 260, 350, 200));
    
        panel.add((JButton) factory.createComponent(ComponentFactory.ComponentType.BUTTON, "Add Recipe", 170, 480, 100, 30, al));
    }
    
    private void addComponentsForAddExercise(JPanel panel) {
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Exercise Title: ", 50, 20, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 170, 20, 150, 20, true));
    
        panel.add((JLabel) factory.createComponent(ComponentFactory.ComponentType.LABEL, "Calories:   ", 50, 60, 100, 20));
        panel.add((JComponent) factory.createComponent(ComponentFactory.ComponentType.TEXT_FIELD, 170, 60, 150, 20, true));
    
        panel.add((JButton) factory.createComponent(ComponentFactory.ComponentType.BUTTON, "Add Exercise", 170, 100, 200, 30, al));
    }
    


    public static void addActionListener() {
        al = new ActionListener() {

            List<String> products = new ArrayList<String>();

            public void actionPerformed(java.awt.event.ActionEvent e) {
                JButton b = (JButton) e.getSource();

                // Check if the button is "Add Product to Recipe"
                if (b.getText().equals("Add Product to Recipe")) {
                    products = applicationFacade.addProductsToRecipe(products);
                }
                // Add Recipe tab
                if (b.getText().equals("Add Recipe")) {
                    products = applicationFacade.addRecipe(products);
                }

                // Add Exercise tab
                if (b.getText().equals("Add Exercise")) {
                    applicationFacade.addExercise();
                }
                // Add Product to Log
                if (b.getText().equals("Add Product to Log")) {

                    products = applicationFacade.addProductsToLog(products);
                }

                // Add Log
                if (b.getText().equals("Add Log")) {

                    products = applicationFacade.addLog(products);
                }

                // Find Recipe
                if (b.getText().equals("Search ")) {
                    applicationFacade.findRecipe();
                }

                // Find Log
                if (b.getText().equals("Search")) {
                    applicationFacade.findLog();
                }

                // My Progress tab
                if (b.getText().equals("Check")) {
                    applicationFacade.checkProgress();
                }

            }
        };
    }






    public static void addItemListener() {
        il = new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                applicationFacade.changeItems(e);
            }
        };
    }
    
}