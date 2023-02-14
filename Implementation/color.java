//change color of the text in JTextPane 
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class color extends JFrame implements ActionListener
{
    JTextPane textPane;
    JScrollPane scrollPane;
    JButton button;
    JTextField textField;
    JLabel label;
    String text;
    String color;
    String pattern;
    String replacement;
    
    public color()
    {
        super("Color");
        setLayout(new FlowLayout());
        
        textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(400, 400));
        textPane.setEditable(false);
        textPane.setText("This is a test.\nThis is another test.\nThis is a third test.");
        
        scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        
        button = new JButton("Change Color");
        button.addActionListener(this);
        
        textField = new JTextField(10);
        textField.setText("This is a test.");
        
        label = new JLabel("Enter text to change color:");
        
        add(scrollPane);
        add(button);
        add(textField);
        add(label);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        text = textField.getText();
        color = textField.getText();
        pattern = text;
        replacement = "<font color=\"" + color + "\">" + text + "</font>";
        
        textPane.setText(textPane.getText().replaceAll(pattern, replacement));
    }
}