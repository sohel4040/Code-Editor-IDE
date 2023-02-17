import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import com.formdev.flatlaf.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import javax.annotation.*;
import com.google.firebase.*;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.EventListener;
import com.google.firebase.*;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.cloud.FirestoreClient;
//Uid Paste
import java.awt.datatransfer.*;

//dnd
import java.awt.dnd.*;

//HighFlighter
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
//resize
import java.awt.image.BufferedImage;
//keyword color
import javax.swing.text.*;

import java.io.*;
import javax.swing.tree.*;
import java.util.*;


public class Editor extends JFrame implements ActionListener, KeyListener, MouseWheelListener
{
    JSplitPane jsp;
    JPanel p1,p2,p3;
    JTabbedPane jtp;
    JMenuItem openf;
    JMenuItem create;
    JMenuItem rename;
    JMenuItem delete;
    JFileChooser jfc;
    ArrayList<File> arr;
    DefaultTreeModel dft;
    DefaultMutableTreeNode root;
    JTree jt;
    JScrollPane jspp,jspp1;
    JPopupMenu jp1;
    String FolderPath,fname;

    JMenuBar mbr;
    JMenu m,java,format,theme,Live,help,setting,view;
    JMenuItem open, save, saveAs,find,compile,run,font,dark,light,doc,read,rnw,c,r,acc,logout;
    CheckboxMenuItem type;
    JTextPane txt;
    FileDialog fd;
    JComboBox  jb,js;
    String p="";
    String name="";
    String path="";
    String file="";
    JPopupMenu jp;
    //For Login
    JPanel log,fnr;
    JTextField uid,search,replace,pname;
    JButton login,paste,find1,replace1;
    JLabel logtitle,count;
    JDialog logd,fnrd;
    HashMap<String,String> hm;
    Firestore db;
    Boolean themec;
    //ls
    boolean write =false;
    boolean logstatus=false;
    
    Editor() throws Exception
    {   
        //Dark
         final StyleContext cont = StyleContext.getDefaultStyleContext();
            final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.ORANGE);
            final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.WHITE);
        // Light
            final StyleContext cont1 = StyleContext.getDefaultStyleContext();
            final AttributeSet attr1 = cont1.addAttribute(cont1.getEmptySet(), StyleConstants.Foreground, Color.ORANGE);
            final AttributeSet attrBlack1 = cont1.addAttribute(cont1.getEmptySet(), StyleConstants.Foreground, Color.BLACK);

            DefaultStyledDocument doc1 = new DefaultStyledDocument() {
         
            public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {

                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) 
                    before = 0;
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        //chcek file extension
                        String regex="";
                        int index=name.indexOf(".");
                        String fname=name.substring((index+1),(name.length()));
                        if(fname.equals("java"))
                        {
                            regex="(\\W)*(abstract|assert|boolean|break|byte|case|catch|char|class|continue|default|do|double|else|enum|extends|final|finally|float|for|if|implements|import|instanceof|int|interface|long|native|new|null|package|synchronized|private|this|protected|throw|public|throws|return|transient|short|true|static|try|strictfp|void|super|volatile|switch|while)";
                        }
                        else if(fname.equals("py"))
                        {
                            regex="(\\W)*(False|True|await|else|import|pass|None|break|except|in|raise|class|finally|is|for|if|return|and|continue|lambda|try|as|def|from|nonlocal|while|assert|del|global|not|with|async|elif|nonlocal|or|yield|elif|pass|yield|except|import|raise|with|del|global|not|async|elif|nonlocal)";
                        }
                        else{

                        }

                        if (text.substring(wordL, wordR).matches(regex))
                        
                            setCharacterAttributes(wordL, wordR - wordL, attr, false);
                        else
                            setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                        wordL = wordR;
                    }
                    wordR++;
                }
            }
            public void remove (int offs, int len) throws BadLocationException {
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offs);

                if (text.substring(before, after).matches("(\\W)*(abstract|assert|boolean|break|byte|case|catch|char|class|continue|default|do|double|else|enum|extends|final|finally|float|for|if|implements|import|instanceof|int|interface|long|native|new|null|package|synchronized|private|this|protected|throw|public|throws|return|transient|short|true|static|try|strictfp|void|super|volatile|switch|while)")) {
                    setCharacterAttributes(before, after - before, attr, false);
                } else {
                    setCharacterAttributes(before, after - before, attrBlack, false);
                }
            }
        };

        txt = new JTextPane(doc1);
        // txt = new JTextPane();
       
        
        setVisible(true);

        Class.forName("FBInit");
        db= FirestoreClient.getFirestore();
        setSize(1300,800);
        setLocationRelativeTo(null);
        setVisible(true);
        Image icon = Toolkit.getDefaultToolkit().getImage("logo.png");    
        setIconImage(icon);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                System.exit(0);
            }
        });
        jp= new JPopupMenu("Compile & Run");
        c= new JMenuItem("Compile");
        c.setIcon(new ImageIcon("compile.png"));
        jp.add(c);
        c.addActionListener(this);
        r = new JMenuItem("Run");
        r.setIcon(new ImageIcon("run.png"));
        r.addActionListener(this);
        jp.add(r);
        mbr= new JMenuBar();
        m=new JMenu("File");
        view = new JMenu("View");
        format = new JMenu("Format");
        theme = new JMenu("Theme");
        Live = new JMenu("Live Share");
        help = new JMenu("Help");
        setting = new JMenu("Settings");

        openf = new JMenuItem("Open folder");
        openf.addActionListener(this);
        openf.setIcon(new ImageIcon("open.png"));

        
        read= new JMenuItem("Read only");
        read.setIcon(new ImageIcon("share.png"));
        read.addActionListener(this);

        find = new JMenuItem("Find and Replace");
        find.addActionListener(this);
        find.setIcon(new ImageIcon("find.png"));

        rnw= new JMenuItem("Write Only");
        rnw.setIcon(new ImageIcon("share.png"));
        rnw.addActionListener(this);
        doc= new JMenuItem("Documentation");
        doc.setIcon(new ImageIcon("doc.png"));
        doc.addActionListener(this);
        open= new JMenuItem("Open");
        open.addActionListener(this);
        open.setIcon(new ImageIcon("open.png"));
        save= new JMenuItem("Save");
        save.addActionListener(this);
        save.setIcon(new ImageIcon("save.png"));
        saveAs= new JMenuItem("Save As");
        saveAs.addActionListener(this);
        saveAs.setIcon(new ImageIcon("save as.png"));
        java= new JMenu("Compile & Run");
        font = new JMenuItem("Font and Size");
        font.setIcon(new ImageIcon("font.png"));
        run= new JMenuItem("Compile");
        run.addActionListener(this);
        compile= new JMenuItem("Run");
        compile.addActionListener(this);
        compile.setIcon(new ImageIcon("run.png"));
        run.setIcon(new ImageIcon("compile.png"));
        acc=new JMenuItem("Account");

        logout=new JMenuItem("Logout");
        logout.addActionListener(this);
        logout.setIcon(new ImageIcon("logout.png"));

        dark= new JMenuItem("Dark theme");
        dark.setIcon(new ImageIcon("dark.png"));
        dark.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                    themec=false;
                     txt.setBackground(new Color(69,73,74));
                     txt.setForeground(Color.WHITE);
            }
        });  
         light= new JMenuItem("Light theme");
         light.setIcon(new ImageIcon("light.png"));
         light.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                    themec=true;
                    
                     txt.setBackground(new Color(150, 150, 153));
                     txt.setForeground(Color.BLACK);
            }
        }); 
        Live.add(read);
        Live.add(rnw);
        help.add(doc);
        theme.add(dark);
        theme.add(light);
        format.add(font);
        java.add(run);
        java.add(compile);
        m.add(open);
        m.add(openf);
        m.add(save);
        m.add(saveAs);
        mbr.add(m);
        mbr.add(view);
        view.add(find);
        mbr.add(java);
        mbr.add(format);
        mbr.add(theme);
        mbr.add(Live);
        mbr.add(help);
        mbr.add(setting);
        setting.add(acc);
        
        txt.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent me)
            {
                if(MouseEvent.BUTTON3 == me.getButton())
                {
                     jp.show(me.getComponent(), me.getX(), me.getY());
                }
            }
        });
  
        font.addActionListener(this);
        txt.addKeyListener(this);
        txt.setFont(new Font("Monospace",Font.PLAIN,18));
        setJMenuBar(mbr);
 
        setTitle("Untitled");

        logInfo();
    //DND
        txt.setDropTarget(new DropTarget() {
        public synchronized void drop(DropTargetDropEvent evt) {
        try {
            evt.acceptDrop(DnDConstants.ACTION_COPY);
            java.util.List<File> droppedFiles = (java.util.List<File>)
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
                txt.setText(content);

                name=file.getName();
                p=file.getAbsolutePath();
                path=file.getParent();
                setTitle(name);
                jtp.setTitleAt(0,name);
                
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
});

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
                    String fn=JOptionPane.showInputDialog(Editor.this,"Enter a file name");
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
      
        jspp1 = new JScrollPane(txt);
        p3.add(jspp1);
        jtp= new JTabbedPane();
        jtp.addTab("Untitled",p3);

        arr= new ArrayList<File>();
        
     
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
    public void runProgram(String FileName,String dir) throws Exception
    { 
       int index=FileName.indexOf(".");
        String fname=FileName.substring((index+1),(FileName.length()));
        // System.out.println(fname);
         if(fname.equals("java"))
          {
                    Thread t1=new Thread()
                    {
                        public void run()
                        {
                            try{
                                boolean a=false;
                                File f2=new File(dir);
                                File [] f= f2.listFiles();
                                String filenm= FileName.substring(0,FileName.length()-4)+ "class";
                                for(int i=0;i<f.length;i++)
                                {
                                    File f3 = f[i];
                                    if(f3.getName().equals(filenm))
                                    {
                                            a=true;
                                            break;
                                    }

                                }
                                // System.out.println(a);
                                if (a)
                                {

                                    String directory = "cd /d " +  "\"" + dir + "\"";
                                    String command = "java "+ FileName.substring(0,FileName.length()-5);
                                    // System.out.println(directory + command);
                                ProcessBuilder p1= new ProcessBuilder("cmd.exe", "/k", directory + " & "+ command).inheritIO();
                                Process p=p1.start();
                                p.waitFor();
                                p.destroy();
                                

                                }  
                            }catch(Exception e){};
                        }
                    };
                    t1.start();
          }
          else if (fname.equals("cpp"))
          {
              Thread t1=new Thread()
                    {
                        public void run()
                        {
                            try{
                                boolean a=false;
                                File f2=new File(dir);
                                File [] f= f2.listFiles();
                                for(int i=0;i<f.length;i++)
                                {
                                    File f3 = f[i];
                                    // System.out.println(f3.getName());
                                    if(f3.getName().equals("a.exe"))
                                    {
                                            a=true;
                                            break;
                                    }

                                }
                                System.out.println(a);
                                if (a)
                                {

                                    String directory = "cd /d " +  "\"" + dir + "\"";
                                    String command = ".\\a.exe";
                                    // System.out.println(directory + command);
                                ProcessBuilder p1= new ProcessBuilder("cmd.exe", "/k", directory + " & "+ command).inheritIO();
                                Process p=p1.start();
                                p.waitFor();
                                p.destroy();
                                

                                }  
                            }catch(Exception e){};
                        }
                    };
                    t1.start();
          }
          else if(fname.equals("html"))
          {
             Thread t= new Thread(){
                    
                    public void run(){

                        try {
                        // String directory = "cd /d " +  "\"" + dir + "\"";
                        // String command = "g++ "+ FileName;
                        System.out.println(dir+""+FileName);
                        // Runtime r= Runtime.getRuntime();
                        // Process p= r.exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
                    ProcessBuilder p= new ProcessBuilder("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe", dir + FileName ).inheritIO();
                    
                    Process ps=p.start();
                    ps.waitFor();
                        ps.destroy();
                        

                        }catch(Exception e){};
                    }

                };
                t.start();
    
          }
          else if (fname.equals("c"))
          {
              Thread t1=new Thread()
                    {
                        public void run()
                        {
                            try{
                                boolean a=false;
                                File f2=new File(dir);
                                File [] f= f2.listFiles();
                                for(int i=0;i<f.length;i++)
                                {
                                    File f3 = f[i];
                                    // System.out.println(f3.getName());
                                    if(f3.getName().equals("a.exe"))
                                    {
                                            a=true;
                                            break;
                                    }

                                }
                                System.out.println(a);
                                if (a)
                                {

                                    String directory = "cd /d " +  "\"" + dir + "\"";
                                    String command = ".\\a.exe";
                                    // System.out.println(directory + command);
                                ProcessBuilder p1= new ProcessBuilder("cmd.exe", "/k", directory + " & "+ command).inheritIO();
                                Process p=p1.start();
                                p.waitFor();
                                p.destroy();
                                

                                }  
                            }catch(Exception e){};
                        }
                    };
                    t1.start();
          }
          else if (fname.equals("py"))
          {
              Thread t= new Thread(){
                    
                    public void run(){

                        try {
                        String directory = "cd /d " +  "\"" + dir + "\"";
                        String command = "python "+ FileName;
                        // System.out.println(directory + command);
                        
                    ProcessBuilder p= new ProcessBuilder("cmd.exe", "/k", directory + " & "+ command).inheritIO();
                    
                    Process ps=p.start();
                    ps.waitFor();
                        ps.destroy();
                        

                        }catch(Exception e){};
                    }

                };
                t.start();
          }
        

    }

    public void compileProgram(String FileName,String dir) throws Exception
    {
        int index=FileName.indexOf(".");
        String fname=FileName.substring((index+1),(FileName.length()));
        // System.out.println(fname);
         if(fname.equals("java"))
          {
                    Thread t= new Thread(){
                    
                    public void run(){

                        try {
                        String directory = "cd /d " +  "\"" + dir + "\"";
                        String command = "javac "+ FileName;
                        // System.out.println(directory + command);
                        
                    ProcessBuilder p= new ProcessBuilder("cmd.exe", "/k", directory + " & "+ command).inheritIO();
                    
                    Process ps=p.start();
                    ps.waitFor();
                        ps.destroy();
                        

                        }catch(Exception e){};
                    }

                };
                t.start();
          }
          else if(fname.equals("cpp"))
          {
             Thread t= new Thread(){
                    
                    public void run(){

                        try {
                        String directory = "cd /d " +  "\"" + dir + "\"";
                        String command = "g++ "+ FileName;
                        // System.out.println(directory + command);
                        
                    ProcessBuilder p= new ProcessBuilder("cmd.exe", "/k", directory + " & "+ command).inheritIO();
                    
                    Process ps=p.start();
                    ps.waitFor();
                        ps.destroy();
                        

                        }catch(Exception e){};
                    }

                };
                t.start();
    
          }
           else if(fname.equals("c"))
          {
             Thread t= new Thread(){
                    
                    public void run(){

                        try {
                        String directory = "cd /d " +  "\"" + dir + "\"";
                        String command = "gcc "+ FileName;
                        // System.out.println(directory + command);
                        
                    ProcessBuilder p= new ProcessBuilder("cmd.exe", "/k", directory + " & "+ command).inheritIO();
                    
                    Process ps=p.start();
                    ps.waitFor();
                        ps.destroy();
                        }catch(Exception e){};
                    }

                };
                t.start();
    
          }



    }
    public void keyPressed(KeyEvent ke)
    {
        // System.out.println("K P"+(int)ke.getKeyChar());
    }
    public void keyTyped(KeyEvent ke) 
    {
        // System.out.println("K T"+(int)ke.getKeyChar());
        try {
            int key=(int)ke.getKeyChar();
            if(key==19)
            {
                String source=txt.getText();
                saveFile(p,source);
                setTitle(name);
            }
             //40-(,123-{,91-[
            else
            {
                setTitle(" * "+name);
            }
            
        } catch (Exception e) {
          System.out.println(e);
        }

        //live share
        
        if(write){

        
        String content = txt.getText();

        Thread t = new Thread()
        {
            public void run()
            {
                try {
                    HashMap<String,String> hm = new HashMap<>();
                    hm.put("Content",content);
                    Firestore db= FirestoreClient.getFirestore();
                    ApiFuture<WriteResult> future =db.collection("Live").document("Share").set(hm);
                     
                    System.out.println("document updated" +future.get().getUpdateTime());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
           
        };
        t.start();
        }

    }
    public void keyReleased(KeyEvent ke)
    {
        int key=(int)ke.getKeyChar();
        // int i=txt.getCaretPosition();
        if(key==40)
        { 
            int i=txt.getCaretPosition();
           // txt.insert(")",i);
            // System.out.println(i +" : "+(i+1));
        }
        // System.out.println("K R"+(int)ke.getKeyChar());
        
    }
    public void mouseWheelMoved(MouseWheelEvent mwe)
    {

    } 


    public String openFile(String path) throws Exception
    {
        File f= new File(path);
        InputStream is= new FileInputStream(f);
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        String s="";
        String result="";
        while((s=br.readLine()) != null)
        {
            result= result+"\n"+s;
        }
        br.close();
        return result;
    }
    public void saveFile(String path,String Content) throws Exception
    {
        File w= new File(path);
        FileWriter fw= new FileWriter(w);
        fw.write(Content);
        fw.close();
    }
    public void actionPerformed(ActionEvent ae)
    {
        String s= ae.getActionCommand();
        // System.out.println(s);
        if(s.equals("Open"))
        {
            fd=new FileDialog(this,"Choose a File",FileDialog.LOAD);
            fd.setVisible(true);
            p=fd.getDirectory()+fd.getFile();
            name=fd.getFile();
            file=name;
            path=fd.getDirectory();
            setTitle(fd.getFile());
            jtp.setTitleAt(0,name);

            // System.out.println("p"+p);
            // System.out.println("name"+name);
            // System.out.println("file"+file);
            // System.out.println("path"+path);
             jsp.setDividerLocation(0);
            // System.out.println(fd.getDirectory()+fd.getFile());
            String show="";
            try {      
                 show=openFile(p);
            } catch (Exception e) {
                System.out.println("Exception");
            }
            txt.setText(show);
        }
        else if(s.equals("Documentation"))
        {
                    JPanel log=new JPanel();
                    log.setLayout(null);
                    log.setBounds(20,20,600,400);
                    
                    JLabel logtitle=new JLabel("Search for documentation");
                    logtitle.setBounds(130,50,400,60);
                    Font tf= new Font("Monospace",Font.BOLD,25);
                    logtitle.setFont(tf);
                    log.add(logtitle);
                    // 
                    
                    pname=new JTextField();
                    pname.setBounds(130,130,350,40);
                    log.add(pname);

                    JButton login=new JButton("Explore");
                    login.setBounds(200,230,200,45);
                    log.add(login);
                    login.addActionListener(this);
                    JDialog logd=new JDialog(this,"Documentation",true);
                    logd.setResizable(false);
                    logd.add(log);
                    logd.setSize(600,400);
                    logd.setLocationRelativeTo(null);
                    logd.setVisible(true);

        }
        else if(s.equals("Open folder"))
        {
            jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option=jfc.showOpenDialog(this);
            if(option == JFileChooser.APPROVE_OPTION)
            {
               File file1 = jfc.getSelectedFile();
               fname=file1.getName();
        
               FolderPath=file1.getPath();
               root= new DefaultMutableTreeNode(file1.getName());
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
                             String path1="";
                                
                                Object all[]=jt.getSelectionPath().getPath();
                                for(int i=0;i<all.length;i++)
                                {
                                    // String a=(String)all[i].getUserObject();
                                    path1=path1+"\\"+all[i];
                                }
                                // System.out.println(path);
                                String fnm=FolderPath.substring(0,(FolderPath.length()-(fname.length()+1)));
                                String p=fnm+path1;
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
                             
                                txt.setText(result);
                                name =n.getName();
                                jtp.setTitleAt(0,name);
                                Editor.this.setTitle(name);

                                p=n.getAbsolutePath();
                        
                                file=name;
                                path=n.getParent()+"\\";

                                
                                }catch(Exception e){};
                        }
                       

                       
                    }
                });
                jspp= new JScrollPane(jt);
               p1.add(jspp);
               dft = (DefaultTreeModel) jt.getModel();
               find(file1,root);
   
            }
            else
            {
               System.out.println("Open command canceled");
            }
        }
        else if(s.equals("Save")) 
        {
            try {
                String sb=txt.getText();
                saveFile(p,sb);
            } catch (Exception e) {
               System.out.println(e);
            }

        }
        else if(s.equals("Compile")) 
        {//System.out.println("HI");
            try {
                compileProgram(name,path);
            } catch (Exception e) {
               System.out.println(e);
            }
        }
        else if(s.equals("Explore"))
        {
                    String url="https://docs.oracle.com/javase/7/docs/api/";


                    StringTokenizer st = new StringTokenizer(pname.getText(),".");

                    // String[] nm = new String[st.countTokens()];
                    int i=0;
                    while(st.hasMoreTokens())
                    {
                        url+=st.nextToken()+"/";
                        i++;
                    }

                    url= url.substring(0,url.length()-1)+".html";

                    try{
                    ProcessBuilder p= new ProcessBuilder("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe", url).inheritIO();
                    Process ps=p.start();
                    ps.waitFor();
                        ps.destroy();
                        }catch(Exception e){System.out.println("Exception in Account Login : "+e);};

                    System.out.println(url);
        }
        else if(s.equals("Run"))
        {
            try{
                runProgram(name,path);
            } catch (Exception e) {
               System.out.println(e);
            }
        }
        else if(s.equals("Read only"))
        {
           try
            {
                if(logstatus)
                    share();
                else{
                JOptionPane.showMessageDialog(this,"You must be logged in for this feature!");
                 }
            }catch(Exception e)
            {
                System.out.println(e);
            }
        }
        else if(s.equals("Write Only"))
        {
            if(logstatus)
                write=true;
            else{
                JOptionPane.showMessageDialog(this,"You must be logged in for this feature!");
            }

        }
         else if(s.equals("Font and Size"))
        {
            JDialog d = new JDialog(this,"Format",false);
            JButton jbb=new JButton("Apply");
            jbb.addActionListener(this);
            JLabel jl= new JLabel("Select Font");
            jb = new JComboBox();
            jb.setBounds(40,100,150,25);
            jbb.setBounds(370,220,80,25);
            jl.setBounds(40,50,150,50);                        

            jb.addItem("Dialog");        
            jb.addItem("DialogInput");
            jb.addItem("Monospaced");
            jb.addItem("SansSerif");
            jb.addItem("Serif");
          
            JLabel jl1= new JLabel("Select Size");
            js = new JComboBox();
            js.setBounds(240,100,150,25);
            jl1.setBounds(240,50,150,50);
            js.addItem("10");        
            js.addItem("11");
            js.addItem("12");
            js.addItem("14");
            js.addItem("16");
            js.addItem("18");
            js.addItem("20");
            js.addItem("22");
            js.addItem("24");
            js.addItem("26");
            jbb.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae)
                {
                    String font=(String)jb.getSelectedItem();
                    int size=Integer.parseInt(js.getSelectedItem().toString());
                    Font f= new Font(font,Font.PLAIN,size);
                    txt.setFont(f);
                    d.dispose();
                }
            });

           
            d.setLocation(720,350);
            d.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent we)
                {
                    d.dispose();
                }
            });
            d.setSize(500,300);
            d.setLayout(null);
            d.add(jb);
            d.add(jl);
            d.add(js);
            d.add(jl1);
            d.add(jbb);
            d.setVisible(true);
        }
        else if(s.equals("Save As"))
        {
            String data=txt.getText();
            fd= new FileDialog(this,"Save a file",FileDialog.SAVE);
            fd.setVisible(true);
            String savePath=fd.getDirectory()+fd.getFile();
            setTitle(fd.getFile());
            try {
                saveFile(savePath,data);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else if(s.equals("Find and Replace"))
        {
            //Panel
                    fnr=new JPanel();
                    fnr.setLayout(null);
                    fnr.setBounds(20,20,400,300);

                    // int w = this.getSize().width;
                    // int h = this.getSize().height; 
                    // int x=this.getLocationOnScreen();


                    fnrd=new JDialog(this,"Find And Replace",false);
                    fnrd.add(fnr);
                    fnrd.setResizable(false);
                    fnrd.setBounds(1140,150,450,230);
                    fnrd.setVisible(true);
                    fnrd.addWindowListener(new WindowAdapter(){
                        public void windowClosing(WindowEvent we)
                        {
                            fnrd.dispose();
                            txt.getHighlighter() .removeAllHighlights();
                        }
                    });

                    // fnrd.setLocationRelativeToParent(this);
                    // fnrd.setLocationByPlatform(true);
                    
                    count=new JLabel();
                    count.setBounds(150,140,400,30);
                   
                    Font tf= new Font("Monospace",Font.PLAIN,14);
                    count.setFont(tf);
                    fnr.add(count);

                    search=new JTextField();
                    search.setBounds(40,30,250,30);
                    fnr.add(search);

                    replace=new JTextField();
                    replace.setBounds(40,90,250,30);
                    fnr.add(replace);

                    find1=new JButton("Find");
                    find1.setBounds(320,30,95,30);
                    fnr.add(find1);
                    find1.addActionListener(this);

                    replace1=new JButton("Replace");
                    replace1.setBounds(320,90,95,30);
                    fnr.add(replace1);
                    replace1.addActionListener(this);

                    
        }
        else if(s.equals("Find"))
        {
                    int ct=0;
                    String txt1 = txt.getText();
                    String txt2 = search.getText();
                    String txt3 = replace.getText();
                
                if(!txt2.trim().equals(""))
                {
                    if (txt1.contains(txt2)) 
                    {
                       txt.getHighlighter() .removeAllHighlights();
                       
                            try{
                                 for(int i=0;i<txt1.length();i++)
                                 {
                                     if(txt1.substring(i,i+txt2.length()).equals(txt2))
                                     {
                                         ct++;
                                         txt.getHighlighter().addHighlight(i,i+txt2.length(),new DefaultHighlighter.DefaultHighlightPainter(Color.decode("#ff6969")));
                                     }
                                 }
                            }catch(Exception e1){}
                    }
                   count.setText(ct+" Searches Found ");
               }
                else
                {
                    count.setText("Find string must not be empty");
                }
        }
        else if(s.equals("Replace"))
        {
                    String txt1 = txt.getText();
                    String txt2 = search.getText();
                    String txt3 = replace.getText();
                    if (txt1.contains(txt2)) 
                    {
                        
                    txt.setText(txt1.replaceAll(txt2, txt3));

                    }
        }
        else if(s.equals("Account"))
        {
             try {
                    ProcessBuilder p= new ProcessBuilder("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe", "https://codeeditorgpm.github.io/Login/" ).inheritIO();
                    Process ps=p.start();
                    ps.waitFor();
                        ps.destroy();
                        }catch(Exception e){System.out.println("Exception in Account Login : "+e);};
                    
                    //Panel
                    log=new JPanel();
                    log.setLayout(null);
                    log.setBounds(20,20,600,400);
                    
                    logtitle=new JLabel("Paste Generated UID Here :");
                    logtitle.setBounds(130,50,400,60);
                    Font tf= new Font("Monospace",Font.BOLD,25);
                    logtitle.setFont(tf);
                    log.add(logtitle);

                    uid=new JTextField();
                    uid.setBounds(130,130,250,40);
                    log.add(uid);

                    login=new JButton("Login");
                    login.setBounds(200,230,200,45);
                    log.add(login);
                    login.addActionListener(this);


                    paste=new JButton("Paste");
                    paste.setBounds(390,130,80,40);
                    log.add(paste);
                    paste.addActionListener(this);

                    logd=new JDialog(this,"GitHub Login",true);
                    logd.setResizable(false);
                    logd.add(log);
                    logd.setSize(600,400);
                    logd.setLocationRelativeTo(null);
                    logd.setVisible(true);
        }
        else if(s.equals("Logout"))
        {
            try{
            FileWriter fw=new FileWriter("UserData",false);
            fw.write("");
            fw.close();
            logInfo();
            JOptionPane.showMessageDialog(this,"Accout is Logged Out !");
            }catch(Exception e){System.out.println(e);};
        }
        else if(s.equals("Paste"))
        {
                uid.setText(paste());
        }
        else if(s.equals("Login"))
        {
                if(!(uid.getText().equals("")) && uid.getText().length()==28 )
                {
                    try{
                   DocumentReference docRef = db.collection("users").document(uid.getText());
                    // asynchronously retrieve the document
                    ApiFuture<DocumentSnapshot> future = docRef.get();
                    // ...
                    // future.get() blocks on response
                    DocumentSnapshot document = future.get();
                    if (document.exists()) {;
                    hm=(HashMap)document.getData();
                    // System.out.println("Email="+hm.get("email")+"\nPrfile Url="+ hm.get("profileurl"));
                    saveInFile();
                    logInfo();
                    } 
                    else {
                    JOptionPane.showMessageDialog(this,"No User Found !");
                    }
                    }catch(Exception e){System.out.println(e);};
                }
                else
                {
                    JOptionPane.showMessageDialog(this,"Please Enter Valid User ID !");
                }
                logd.setVisible(false);
        }
    }
    //Login Acc Info on load
    public void logInfo()
    {
        try{
        BufferedReader br=new BufferedReader(new FileReader("UserData"));
        String []info=new String[2];
        int i=0;
        String data="";
        while((data=br.readLine())!=null)
        {
            info[i]=data;
            i++;
        }
        if(info[0]!=null && info[1]!=null)
        {
            acc.setText(info[0]);
        
            ImageIcon i3 = resizeImageIcon(new ImageIcon(new URL(info[1])),24,24);

            acc.setIcon(i3);
            acc.removeActionListener(this);
            setting.add(logout);
            logstatus=true;
        }
        else{
                setting.remove(logout);
                acc.setText("Account");
                acc.setIcon(new ImageIcon("g2.png"));
                acc.addActionListener(this);
        }
        }catch(Exception e){System.out.println(e);}
    }
    //Paste Copied text
    private String paste() {
    String text="";
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    DataFlavor flavor = DataFlavor.stringFlavor;
    if (clipboard.isDataFlavorAvailable(flavor)) {
      try {
        text = (String) clipboard.getData(flavor);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
    return text;
  }
    public void saveInFile()
    {
        try{
        FileWriter fw=new FileWriter("UserData",false);
        fw.write(hm.get("email")+"\n"+hm.get("profileurl"));
        fw.close();
        }catch(Exception e){System.out.println(e);};
        //setProfile dp and nm
        acc.setText(hm.get("email"));
        acc.setIcon(new ImageIcon(hm.get("profileurl")));
    }
    //Share
    public void share()
    {
         try{ 
                DocumentReference docRef = db.collection("Live").document("Share");
                docRef.addSnapshotListener(new com.google.cloud.firestore.EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirestoreException e) {
                                    System.out.println("onEvent");
                    if (e != null) {
                    System.err.println("Listen failed: " + e);
                    return;
                }

                    if (snapshot != null && snapshot.exists()) {
                
                    System.out.println("Current data: " + snapshot.getData());
                    // loc=txt.getCaretPosition();
                
                    // txt.removeKeyListener(Liveshare.this);
                    txt.setText(snapshot.getString("Content"));
                    // txt.addKeyListener(Liveshare.this);
                    // txt.setCaretPosition(loc);
                    } else {
                    System.out.print("Current data: null");
                    }
                    }
                }); }catch(Exception e){System.out.println(e);};
             
       }
    
    public static ImageIcon resizeImageIcon( ImageIcon imageIcon , Integer width , Integer height )
     {
    BufferedImage bufferedImage = new BufferedImage( width , height , BufferedImage.TRANSLUCENT );

    Graphics2D graphics2D = bufferedImage.createGraphics();
    graphics2D.drawImage( imageIcon.getImage() , 0 , 0 , width , height , null );
    graphics2D.dispose();

    return new ImageIcon( bufferedImage , imageIcon.getDescription() );
     }

     // key color
     
    private int findLastNonWordChar (String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar (String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
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
            new Editor();
        }catch(Exception e){System.out.println(e);}    
    }
}