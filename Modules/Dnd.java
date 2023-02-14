import java.awt.*;
import javax.swing.*;
import java.awt.dnd.*;
import java.io.*;
import java.util.List;
import java.awt.datatransfer.DataFlavor;

public class Dnd extends JFrame 
{
    Dnd()
    {
        setVisible(true);
        setSize(500,500);
        setDefaultCloseOperation(3);

JTextArea myPanel = new JTextArea();
myPanel.setDropTarget(new DropTarget() {
    public synchronized void drop(DropTargetDropEvent evt) {
        try {
            evt.acceptDrop(DnDConstants.ACTION_COPY);
            List<File> droppedFiles = (List<File>)
                evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
            for (File file : droppedFiles) {
                // process files
                System.out.println(file.getName());
                if(!(file.isDirectory()))
                {
                BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String content="";
                String data="";
                while((data=br.readLine())!=null)
                {
                    content=content+"\n"+data;
                }
                myPanel.setText(content);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
});

    add(myPanel);
    }
    public static void main(String ar[])
    {
        new Dnd();
    }
}