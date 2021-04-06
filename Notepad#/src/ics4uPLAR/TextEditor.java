package ics4uPLAR;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
/**
 * 
 * This class is used to initiate <i> <b>Notepad#</b></i> software.
 * 
 * @author Abdullah Quadri
 * @version 1 April - 4 April 2021
 *
 */
public class TextEditor extends JFrame implements ActionListener{

 JTextArea textArea;
 JScrollPane scrollPane;
 JLabel fontLabel;
 JSpinner fontSizeSpinner;
 JButton fontColorButton;
 JComboBox fontBox;
 
 JMenuBar menuBar;
 JMenu fileMenu;
 JMenuItem openItem;
 JMenuItem saveItem;
 JMenuItem exitItem;

 TextEditor(){
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.setTitle("Notepad#");
  this.setSize(500, 500);
  this.setLayout(new FlowLayout());
  this.setLocationRelativeTo(null);
  // Organize pad area
  textArea = new JTextArea();
  textArea.setLineWrap(true);
  textArea.setWrapStyleWord(true);
  textArea.setFont(new Font("Arial",Font.PLAIN,20));
  // Make right vertical scrolll bar on right
  scrollPane = new JScrollPane(textArea);
  scrollPane.setPreferredSize(new Dimension(450,450));
  scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
  // Label for font spinner
  fontLabel = new JLabel("Font: ");
  // Font spinner with all system fonts.
  fontSizeSpinner = new JSpinner();
  fontSizeSpinner.setPreferredSize(new Dimension(50,25));
  fontSizeSpinner.setValue(20);
  fontSizeSpinner.addChangeListener(new ChangeListener() {

   @Override
   /**
    * This method displays the menu bar in Notepad#. This methods takes the
    * e parameter of ChangeEvent object and returns nothing.
    * 
    */
   public void stateChanged(ChangeEvent e) {
    
    textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) fontSizeSpinner.getValue())); 
   }
   
  });
  
  fontColorButton = new JButton("Color");
  fontColorButton.addActionListener(this);
  
  String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
  
  fontBox = new JComboBox(fonts);
  fontBox.addActionListener(this);
  fontBox.setSelectedItem("Arial");
  
  //  menubar 
  
   menuBar = new JMenuBar();
   fileMenu = new JMenu("File");
   openItem = new JMenuItem("Open");
   saveItem = new JMenuItem("Save");
   exitItem = new JMenuItem("Exit");
   
   openItem.addActionListener(this);
   saveItem.addActionListener(this);
   exitItem.addActionListener(this);
   
   fileMenu.add(openItem);
   fileMenu.add(saveItem);
   fileMenu.add(exitItem);
   menuBar.add(fileMenu);
  
  //  /menubar 
   
  this.setJMenuBar(menuBar);
  this.add(fontLabel);
  this.add(fontSizeSpinner);
  this.add(fontColorButton);
  this.add(fontBox);
  this.add(scrollPane);
  this.setVisible(true);
 }

 @Override
 /**
  * This method is to execute any command when it is selected in Notepad#. 
  * This function takes the e Parameter of ActionEvent object, but it returns nothing.
  * 
  */
 public void actionPerformed(ActionEvent e) {
  // Colour box with all the different colours.
  if(e.getSource()==fontColorButton) {
   JColorChooser colorChooser = new JColorChooser();
   
   Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
   
   textArea.setForeground(color);
  }
  // Correct font size of pad based on value in fontBox
  if(e.getSource()==fontBox) {
   textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
  }
  // Importing items from your own hard disk.
  if(e.getSource()==openItem) {
   JFileChooser fileChooser = new JFileChooser();
   fileChooser.setCurrentDirectory(new File("."));
   // This filter allows the user only to open txt file iwth notepad.
   FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
   fileChooser.setFileFilter(filter);
   
   int response = fileChooser.showOpenDialog(null);
   
   if(response == JFileChooser.APPROVE_OPTION) {
    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
    Scanner fileIn = null;
    
    try {
     fileIn = new Scanner(file);
     if(file.isFile()) {
      while(fileIn.hasNextLine()) {
       String line = fileIn.nextLine()+"\n";
       textArea.append(line);
      }
     }
    } catch (FileNotFoundException e1) {
     e1.printStackTrace();
    }
    finally {
     fileIn.close();
    }
   }
  }
  if(e.getSource()==saveItem) {
   JFileChooser fileChooser = new JFileChooser();
   fileChooser.setCurrentDirectory(new File("."));
   
   int response = fileChooser.showSaveDialog(null);
   
   if(response == JFileChooser.APPROVE_OPTION) {
    File file;
    PrintWriter fileOut = null;
    
    file = new File(fileChooser.getSelectedFile().getAbsolutePath());
    try {
     fileOut = new PrintWriter(file);
     fileOut.println(textArea.getText());
    } 
    catch (FileNotFoundException e1) {
     e1.printStackTrace();
    }
    finally {
     fileOut.close();
    }   
   }
  }
  if(e.getSource()==exitItem) {
   System.exit(0);
  }  
 }
}