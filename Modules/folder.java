import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.tree.*;
import java.util.*;

public class folder extends JFrame implements ActionListener
{
    JSplitPane jsp;
    JPanel p1,p2,p3;
    JTabbedPane jtp;
    JTree jt;
    JButton b1,b2;
    JMenuBar jb;
    JMenu file;
    JMenuItem open;
    // FileDialog fd;
    JFileChooser jfc;
    ArrayList<File> arr;
    DefaultTreeModel dft;
    DefaultMutableTreeNode root;
    String FolderPath,fname;
    JTextArea ja;
    JScrollPane jspp,jspp1;
    JPopupMenu jp1;
    JMenuItem create;
    JMenuItem rename;
    JMenuItem delete;
   

    public folder()
    {
        jp1= new JPopupMenu();
        create=new JMenuItem("New File");
        create.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                DefaultMutableTreeNode d= (DefaultMutableTreeNode)jt.getSelectionPath().getLastPathComponent();
                System.out.println(d.getUserObject()+"");
                String path="";
                Object all[]=jt.getSelectionPath().getPath();
                for(int i=0;i<all.length;i++)
                {
                    path=path+"\\"+all[i];
                }
                String fnm=FolderPath.substring(0,(FolderPath.length()-(fname.length()+1)));
                String p=fnm+path;
                File cr= new File(p);
                if(cr.isDirectory())
                {
                    try
                    {
                    String fn=JOptionPane.showInputDialog(folder.this,"Enter a file name");
                    if(!fn.equals(null))
                    {
                        String nfile=p+"\\"+fn;
                        File nf= new File(nfile);
                        DefaultMutableTreeNode n= new DefaultMutableTreeNode(fn);
                        dft.insertNodeInto(n,d,d.getChildCount());
                        dft.reload();
                        System.out.println(nfile);
                        System.out.println(nf.createNewFile());
                    }
                    }catch(Exception e){System.out.println(e);}
                }
            }
        });
        rename=new JMenuItem("Rename");
        delete=new JMenuItem("Delete");
        jp1.add(create);
        jp1.add(rename);
        jp1.add(delete);
        p3 = new JPanel();
        p3.setLayout(new BorderLayout());
        ja = new JTextArea();
        jspp1 = new JScrollPane(ja);
        ja.setFont(new Font("Sans Serif",Font.PLAIN,17));
        p3.add(jspp1);
        jtp= new JTabbedPane();
        jtp.addTab("Main.java",p3);

        arr= new ArrayList<File>();
        jb= new JMenuBar();
        file= new JMenu("File");
        open = new JMenuItem("Open Folder");
        open.addActionListener(this);
        file.add(open);
        jb.add(file);
        setJMenuBar(jb);
     
        setVisible(true);
        p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        p2.add(jtp);
        setSize(800,700);
        setDefaultCloseOperation(3);
        jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        jsp.setTopComponent(p1);
        jsp.setBottomComponent(p2);
        jsp.setDividerSize(10);
        jsp.setDividerLocation(250);
        jsp.setOneTouchExpandable(true);
        add(jsp);  
    }
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getActionCommand().equals("Open Folder"))
        {
            jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option=jfc.showOpenDialog(this);
            if(option == JFileChooser.APPROVE_OPTION)
            {
               File file = jfc.getSelectedFile();
               fname=file.getName();
            //    System.out.println("Folder Selected: " + file.getName());
            //    System.out.println("Folder Path: " + file.getPath());
               FolderPath=file.getPath();
               root= new DefaultMutableTreeNode(file.getName());
               jt = new JTree(root);  
               jt.setFont(new Font("Sans Serif",Font.PLAIN,17));
               jt.addMouseListener(new MouseAdapter(){
                    public void mouseClicked(MouseEvent me)
                    {
                        if(me.getButton()==MouseEvent.BUTTON3)
                        {
                            
                            jp1.show(me.getComponent(), me.getX(), me.getY());

                        }   
                        else if(me.getButton()==MouseEvent.BUTTON1)
                        {
                             String path="";
                                // DefaultMutableTreeNode t=(DefaultMutableTreeNode)jt.getSelectionPath().getLastPathComponent();
                                Object all[]=jt.getSelectionPath().getPath();
                                for(int i=0;i<all.length;i++)
                                {
                                    // String a=(String)all[i].getUserObject();
                                    path=path+"\\"+all[i];
                                }
                                // System.out.println(path);
                                String fnm=FolderPath.substring(0,(FolderPath.length()-(fname.length()+1)));
                                String p=fnm+path;
                                String data="";
                                String result="";
                                try
                                {
                                // System.out.println(p);
                                File n = new File(p);
                                BufferedReader fr = new BufferedReader(new FileReader(n));
                                while((data = fr.readLine()) != null)
                                {
                                    result=result+"\n"+data;
                                
                                }
                                System.out.println(result);
                                ja.setText(result);
                                
                                
                                }catch(Exception e){};
                        }
                       

                       
                    }
                });
                jspp= new JScrollPane(jt);
               p1.add(jspp);
               dft = (DefaultTreeModel) jt.getModel();
               find(file,root);
            //    for(int j=0;j<arr.size();j++)
            //    {
            //        File fi= arr.get(j);
            //        System.out.println(fi.getName());
            //     //    DefaultMutableTreeNode dt= new DefaultMutableTreeNode(fi.getName());
            //     //    dft.insertNodeInto(dt,root,j);
            //    }
            }
            else
            {
               System.out.println("Open command canceled");
            }
        }
    }
    public void find(File f,DefaultMutableTreeNode r)
    {
        DefaultMutableTreeNode parent=r;
        File farr[]=f.listFiles();
        if(farr!= null)
        {
            for(int i=0;i<farr.length;i++)
            {
                File f1=farr[i];
                if(!f1.isHidden() && f1.isDirectory())
                {
                    // arr.add(f1);
                    DefaultMutableTreeNode dt= new DefaultMutableTreeNode(f1.getName());
                    dft.insertNodeInto(dt,parent,i);
                    find(f1,dt);
                }
                else if(f1.isFile())
                {
                    DefaultMutableTreeNode file= new DefaultMutableTreeNode(f1.getName());
                    dft.insertNodeInto(file,parent,i);
                }
            } 
        }
        else
        {
            return;
        }
        return;
    }
    public static void main(String a[])
    {
        try
        {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        }catch(Exception e){
            System.out.println(e);
        }
        new folder();
    }
}