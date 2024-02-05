// Java Program to demonstrate 
// JTabbedPane with Labels 
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener; 
  
// Driver Class 
public class SwingGUI { 
      // main function 



      public static JFrame window;
      public static ActionListener al;
      public static ItemListener il;
    public static void main(String[] args) { 
        // Run the Swing application on the Event Dispatch Thread (EDT) 
        SwingUtilities.invokeLater(new Runnable() { 
            public void run() { 
                // Create a new JFrame (window) 
               window = new JFrame("JTabbedPane Example"); 
                // Close operation when the window is closed 
               window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation when the window is closed 
               // Set the initial size of the window 
               window.setSize(500, 320); 



                addActionListener();
                addItemListener();
  
                // Create a JTabbedPane, which will hold the tabs 
                addTab();
  
                // Make the JFrame visible 
                window.setVisible(true); 
            } 
        }); 
    }
    
    





    public static void addLabel(String text, int x, int y, int w, int h, JPanel panel){
        //Create a label object, set the position, and add to the frame
        JLabel lbl1 = new JLabel(text, JLabel.LEFT);
        lbl1.setBounds(x, y, w, h);
        panel.add(lbl1);
     }


     public static void addTextField(int x, int y, int w, int h, JPanel panel){
        JTextField name = new JTextField();
        name.setBounds(x, y, w, h);
        panel.add(name);
     }


     public static void addRadioBttn(String text1, String text2, int x, int y, int w, int h, JPanel panel){
        JRadioButton rdbtn1 = new JRadioButton(text1);
         rdbtn1.setActionCommand(text1);
         JRadioButton rdbtn2 = new JRadioButton(text2);
         rdbtn2.setActionCommand(text2);
         rdbtn1.setBounds(x,y,w,h);
         rdbtn2.setBounds(x+100,40,x+10,50);
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


     public static void addComboBox(int x, int y, int w, int h, JPanel panel){
        String arr[] = { "Meat", "Fruits", "Vegies"};

        JComboBox cb = new JComboBox(arr);
        cb.setBounds(x, y, w, h);
        cb.setSelectedIndex(0);
        cb.addItemListener(il);
        panel.add(cb);
     }


     public static void addTab(){
                JTabbedPane tabPanel = new JTabbedPane(); 

                // Create the first tab (page1) 
                JPanel page1 = new JPanel(); 
                page1.setLayout(null);
                createTab(page1, 0);
  
                // Create the second tab (page2) and add a JLabel to it 
                JPanel page2 = new JPanel(); 
                page2.setLayout(null);
                createTab(page2, 1);
  
                // Create the third tab (page3) and add a JLabel to it 
                JPanel page3 = new JPanel(); 
                page3.add(new JLabel("This is Tab 3")); 
  
                // Add the three tabs to the JTabbedPane 
                tabPanel.addTab("Home", page1); 
                tabPanel.addTab("Recipes", page2); 
                tabPanel.addTab("Log", page3); 
  
                // Add the JTabbedPane to the JFrame's content 
                window.add(tabPanel); 
     }


     public static void createTab(JPanel win, int id){


        if(id == 0){
            addLabel("Product Name: ",80,20,100,20, win);
            addTextField(200, 20, 200, 20, win);

            addLabel("Product Type: ", 80,60,100,20, win);
            addRadioBttn("Local", "Foreign", 200, 40, 80, 50, win);

            addLabel("Description", 80,100,120,20, win);
            addTextArea(200,100,200,100, win);
            
            addBttn("Submit", 200, 220, 100, 30, win);
        }
        else if(id == 1){
            addLabel("Recipe Title: ",80,20,100,20, win);
            addTextField(200, 20, 200, 20, win);

            addLabel("Calories: ",80,40,100,20, win);
            addTextField(200, 40, 200, 20, win);

            addLabel("Food Type: ",80,60,100,20, win);
            addComboBox(200, 60, 200, 20, win);

            

            addBttn("Add Recipe", 200, 220, 100, 30, win);
        }
        
     }



     public static void addActionListener(){
        al = new ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                    JButton b = (JButton)e.getSource();
                    if(b.getText() == "Submit"){
                        System.out.println("Hello");
                    }
                }
            };
     }


     public static void addItemListener(){
        il = new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                JComboBox cb = (JComboBox)e.getSource();

                if (cb.getSelectedItem() == "Vegies") {
 
                    System.out.println("Hello");
                }
            
         } };
    }
} 