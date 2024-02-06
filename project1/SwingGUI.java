// Java Program to demonstrate 
// JTabbedPane with Labels 
import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.*;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener; 
  
// Driver Class 
public class SwingGUI { 
      // main function 



      public static JFrame window;
      public static ActionListener al;
      public static ItemListener il;
      public static JTabbedPane tabPanel;
      
    public static void main(String[] args) { 
        // Run the Swing application on the Event Dispatch Thread (EDT) 
        SwingUtilities.invokeLater(new Runnable() { 
            public void run() { 
                // Create a new JFrame (window) 
               window = new JFrame("Project 1 - Food Application - Orest Brukhal, Orest Kisi"); 
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


     public static void addComboBox(int x, int y, int w, int h, JPanel panel, int id){

        String arr[] = {"null"};
        
        if(id == 0){
            String arr1[] = { "Meat", "Fruits", "Vegies", "Other"};
            arr = arr1;
        }
        if(id == 1){
            String arr1[] = { "Jan", "Feb", "Mar", "Apr"};
            arr = arr1;
        }
        

        JComboBox cb = new JComboBox(arr);
        cb.setBounds(x, y, w, h);
        cb.setSelectedIndex(0);
        cb.addItemListener(il);
        panel.add(cb);
     }




     public static void addDateChooser(int x, int y, int w, int h, JPanel win){
        addTextField(x, y, w/2, h, win, true);
        addComboBox(x+20, y, w+20, h, win, 1);
        addTextField(x+80, y, w, h, win, true);
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
            addLabel("Product Name: ",80,20,100,20, win);
            addTextField(200, 20, 200, 20, win, true);

            addLabel("Product Type: ", 80,60,100,20, win);
            addRadioBttn("Local", "Foreign", 200, 40, 80, 50, win);

            addLabel("Description", 80,100,120,20, win);
            addTextArea(200,100,200,100, win);
            
            addBttn("Submit", 200, 220, 100, 30, win);
        }
        //find log
        else if(id == 1){
            addLabel("Recipe Title: ",80,20,100,20, win);
            addDateChooser(240, 20, 40, 20, win);

            
            addBttn("Search", 250, 40, 100, 30, win);
            
            addLabel("Description", 80,100,120,20, win);
            addTextArea(200,100,200,100, win);
        }

        //add log
        else if(id == 2){
            addLabel("Recipe Title: ",80,20,100,20, win);
            addDateChooser(240, 20, 40, 20, win);
        }

        //find recipes
        else if(id == 3){
            addLabel("Recipe Title: ",80,20,100,20, win);
            addTextField(200, 20, 200, 20, win, true);

            addBttn("Search", 250, 40, 100, 30, win);

            addLabel("Description", 80,100,120,20, win);
            addTextArea(200,100,200,100, win);
        }

        //add recipes
        else if(id == 4){
            addLabel("Recipe Title: ",80,20,100,20, win);
            addTextField(200, 20, 200, 20, win, true);

            addLabel("Calories: ",80,40,100,20, win);
            addTextField(200, 40, 200, 20, win, true);

            addLabel("Food Type: ",80,60,100,20, win);
            addComboBox(200, 60, 200, 20, win, 0);
            addLabel("Other: ",80,80,100,20, win);
            addTextField(200, 80, 200, 20, win, false);

            

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





                JTextField other = getJTextFieldByJLabelText("Other: ");

                if (cb.getSelectedItem() == "Other"){
                    
                    other.setEditable(true);
                    
                }
                else {
                    other.setEditable(false);
                }
            
         } };
    }
} 