package za.ac.tut.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import java.awt.Font;
import javax.swing.border.SoftBevelBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import za.ac.tut.encryption.MessageEncryptor;
import za.ac.tut.message.Message;

/**
 *
 * @author <Jeffrey Nkadimeng 231983031>
 */

public class SecureMessagesFrame extends JFrame {
    
    private JLabel heading;
    private JPanel headingPnl;
    
    private JPanel plainPnl;
    private JTextArea plainTxtArea;
    private JScrollPane plainScroll;
    
    private JPanel encryptPnl;
    private JTextArea encryptedTextArea;
    private JScrollPane encryptedScroll;
    
    private JPanel plainAndEncryptPnl;
    
    private JMenu fileMenu;
    private JMenuItem openFile;
    private JMenuItem encrypt;
    private JMenuItem save;
    private JMenuItem clear; 
    private JMenuItem exit;
    
    private JMenuBar menuBar;
    
    private JPanel mainPnl;
    
    public SecureMessagesFrame() {
         setTitle("Secure Messages");
         setSize(600,400);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         setDefaultLookAndFeelDecorated(true);
         setVisible(true);
         
         heading = new JLabel("Message Encryptor");
         heading.setFont(new Font(Font.SANS_SERIF, Font.ITALIC + Font.BOLD, 25));
         heading.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
         heading.setForeground(Color.BLUE);
         headingPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
         headingPnl.add(heading);
        
         plainPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
         plainPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 2), "Plain message"));
         plainTxtArea = new JTextArea();
         plainTxtArea.setColumns(25);
         plainTxtArea.setRows(10);
         plainScroll = new JScrollPane(plainTxtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
         plainPnl.add(plainScroll);
        
         encryptPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
         encryptPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 2), "Encrypted message"));
         encryptedTextArea = new JTextArea();
         encryptedTextArea.setEditable(false);
         encryptedTextArea.setColumns(25);
         encryptedTextArea.setRows(10);
         encryptedScroll = new JScrollPane(encryptedTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
         encryptPnl.add(encryptedScroll);
      
         plainAndEncryptPnl = new JPanel(new BorderLayout());
         plainAndEncryptPnl.add(plainPnl, BorderLayout.WEST);
         plainAndEncryptPnl.add(encryptPnl, BorderLayout.EAST);
        
         fileMenu = new JMenu("File");
         
         openFile = new JMenuItem("Open file...");
         openFile.addActionListener(new OpenFileListener());
        
         encrypt = new JMenuItem("Encrypt message...");
         encrypt.addActionListener(new EncryptItemListener());
        
         save = new JMenuItem("Save encrypted message...");
         save.addActionListener(new SaveItemListener());
        
         clear = new JMenuItem("Clear");
         clear.addActionListener(new ClearItemListener());
        
         exit = new JMenuItem("Exit");
         exit.addActionListener(new ExitItemListener());
        
         fileMenu.add(openFile);
         fileMenu.add(encrypt);
         fileMenu.add(save);
         fileMenu.addSeparator();
         fileMenu.add(clear);
         fileMenu.add(exit);
         
         menuBar = new JMenuBar();
         setJMenuBar(menuBar);
        
         menuBar.add(fileMenu);
        
        
         mainPnl = new JPanel(new BorderLayout());
         mainPnl.add(headingPnl, BorderLayout.NORTH);
         mainPnl.add(plainAndEncryptPnl, BorderLayout.CENTER);
        
         add(mainPnl);
         pack();
    }
    
    private class ClearItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent act) {
            plainTxtArea.setText("");
            encryptedTextArea.setText("");
        }
    }
    
    private class ExitItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent act) {
            System.exit(0);
        }
    }
    
    private class OpenFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent act) {
            JFileChooser fChooser = new JFileChooser();
            
            int found = fChooser.showOpenDialog(null);
            
            if (found == JFileChooser.APPROVE_OPTION) {
                File file = fChooser.getSelectedFile();
                try (BufferedReader bReader = new BufferedReader(new FileReader(file))) {
                    String output = "";
                    String line;
                    while ((line = bReader.readLine()) != null) {
                        output += line + "\n";
                    }
                    plainTxtArea.setText(output);
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(null, "Could not read the file ");
                }
            }
        }
    }
    
    private class EncryptItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent act) {
            if (!plainTxtArea.getText().isEmpty()) {
                MessageEncryptor msgE = new MessageEncryptor(plainTxtArea.getText());
                Message msg = new Message(plainTxtArea.getText());
                encryptedTextArea.setText(msgE.encryptMessage(msg.getMessage()));
            } else {
                JOptionPane.showMessageDialog(null, "Insert a message");
            }
        }
    }
    
    private class SaveItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent act) {
            JFileChooser fChooser = new JFileChooser();
            
            int found = fChooser.showSaveDialog(null);
            
            if (found == JFileChooser.APPROVE_OPTION) {
                File file = fChooser.getSelectedFile();
                try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(file))) {
                    bWriter.write(encryptedTextArea.getText());
                    JOptionPane.showMessageDialog(null,"Successfully saved");
                    bWriter.close();
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(null, "Could not write to the file ");
                }
            }
        }
    }
}