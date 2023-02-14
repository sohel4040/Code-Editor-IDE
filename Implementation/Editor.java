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
//HighFlighter
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
//resize
import java.awt.image.BufferedImage;
//keyword color
import javax.swing.text.*;


public class Editor extends JFrame implements ActionListener, KeyListener, MouseWheelListener
{
    JMenuBar mbr;
    JMenu m,java,format,theme,Live,help,setting,view;
    JMenuItem open, save, saveAs,find,compile,run,font,dark,light,doc,read,rnw,c,r,acc,logout,srch;
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
    JTextField uid,search,replace;
    JButton login,paste,find1,replace1;
    JLabel logtitle,count;
    JDialog logd,fnrd;
    HashMap<String,String> hm;
    Firestore db;
    
    Editor() throws Exception
    {   
         
        // Color 
            final StyleContext cont = StyleContext.getDefaultStyleContext();
            final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.ORANGE);
            final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.WHITE);
            DefaultStyledDocument doc1 = new DefaultStyledDocument() {
            public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        if (text.substring(wordL, wordR).matches("(\\W)*(abstract|assert|boolean|break|byte|case|catch|char|class|continue|default|do|double|else|enum|extends|final|finally|float|for|if|implements|import|instanceof|int|interface|long|native|new|null|package|synchronized|private|this|protected|throw|public|throws|return|transient|short|true|static|try|strictfp|void|super|volatile|switch|while)"))
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
        //txt = new JTextPane();
       
        add(new JScrollPane(txt));
        setVisible(true);


          //our color logic
         




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

        srch= new JMenuItem("Search");
        // search.setIcon(new ImageIcon("share.png"));
        srch.addActionListener(this);
        help.add(srch);
        
        read= new JMenuItem("Read only");
        read.setIcon(new ImageIcon("share.png"));
        read.addActionListener(this);

        find = new JMenuItem("Find and Replace");
        find.addActionListener(this);
        find.setIcon(new ImageIcon("find.png"));

        rnw= new JMenuItem("Read and Write");
        rnw.setIcon(new ImageIcon("share.png"));
        rnw.addActionListener(this);
        doc= new JMenuItem("Documentation");
        doc.setIcon(new ImageIcon("doc.png"));
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

        // type= new CheckboxMenuItem("Applet",false);
        dark= new JMenuItem("Dark theme");
        dark.setIcon(new ImageIcon("dark.png"));
        dark.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                
                 
                     txt.setBackground(new Color(69,73,74));
                     txt.setForeground(Color.WHITE);
            
            }
        });  //rgba(30,30,30)  rgba(240,240,240)
         light= new JMenuItem("Light theme");
         light.setIcon(new ImageIcon("light.png"));
         light.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {

                     txt.setBackground(new Color(240,240,240));
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
        // System.out.println("File saved");
        // txt.setText(Content)
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
            // System.out.println(fd.getDirectory()+fd.getFile());
            String show="";
            try {      
                 show=openFile(p);
            } catch (Exception e) {
                System.out.println("Exception");
            }
            txt.setText(show);
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
                share();
            }catch(Exception e)
            {
                System.out.println(e);
            }

        }
        else if(s.equals("Read and Write"))
        {


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
                    ProcessBuilder p= new ProcessBuilder("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe", "https://codeeditorgpm.github.io/Login/" ).inheritIO();
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
        else if(s.equals("Search"))
        {
            JTextField jt= new JTextField(20);
            jt.setBounds(45,50,380,40);
            jt.setFont(new Font("Monospace",Font.BOLD,20));
            jt.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae)
                {
                    String c=jt.getText();

                    String c1=c.substring(0,1);
                    String url="";
                    if(c1.equals("J"))
                    {
                        url="https://docs.oracle.com/javase/8/docs/api/javax/swing/"+jt.getText()+".html";
                    }
                    else
                    {
                        url="https://docs.oracle.com/javase/8/docs/api/java/awt/"+jt.getText()+".html";
                    }

                     try {
                            ProcessBuilder p= new ProcessBuilder("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe", url ).inheritIO();
                            Process ps=p.start();
                            ps.waitFor();
                            ps.destroy();

                        }catch(Exception e){System.out.println("Exception in Account Login : "+e);};

                }
            });
            JPanel jp= new JPanel();
            jp.setLayout(null);
            jp.add(jt);
            JDialog jd= new JDialog(this,"Search any classes and methods",false);
            jd.setSize(500,200);
            jd.setLayout(new BorderLayout());
            jd.add(jp);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);
            
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



    public static void main(String a[])
    {
        try
        {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
            new Editor();
        }catch(Exception e){System.out.println(e);}    
    }
}