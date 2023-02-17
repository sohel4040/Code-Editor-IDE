import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;

public class ourcolor extends JFrame {
  
 DefaultStyledDocument document;
    public ourcolor () {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
           
    

    







        JTextPane txt = new JTextPane();
        txt.setText("public class Hi {} private noob");
        add(new JScrollPane(txt));
        setVisible(true);

 try{
    //    document  = new DefaultStyledDocument();
    //    // JTextPane textpane = new JTextPane(document);
    //     StyleContext context = new StyleContext();
    //     // build a style
    //     Style style = context.addStyle("test", null);
    //     // set some style properties
    //     StyleConstants.setForeground(style, Color.BLUE);
    //     // add some data to the document
    //     document.insertString(0, "Hi", style);
    
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setForeground(attrs, Color.RED);
          StyledDocument sdoc = txt.getStyledDocument();

        sdoc.setCharacterAttributes(2,4, attrs, false);

         }catch(Exception e){
         System.out.println(e);}




    }

    public static void main (String args[]) {
        new ourcolor();
    }
}