import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
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
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Liveshare extends Frame implements ActionListener, KeyListener
{
    MenuBar mbr;
    Menu m,java;
    MenuItem open, save, saveAs,compile,run,share;
    CheckboxMenuItem type;
    TextArea txt;
    FileDialog fd;
    String p="";
    String name="";
    String path="";
    String file="";
    int loc;
    Liveshare() throws Exception
    {

        Class.forName("FBInit");

        setVisible(true);
        setSize(700,700);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                System.exit(0);
            }
        });
        mbr= new MenuBar();
        m=new Menu("File");
        open= new MenuItem("Open");
        open.addActionListener(this);
        save= new MenuItem("Save");
        save.addActionListener(this);
        saveAs= new MenuItem("Save As");
        saveAs.addActionListener(this);
        java= new Menu("Live Share");
        share= new MenuItem("Share");
         
        // run= new MenuItem("Compile");
        // run.addActionListener(this);
        // compile= new MenuItem("Run");
        // compile.addActionListener(this);
        type= new CheckboxMenuItem("Applet",false);
         java.add(share);
        m.add(open);
        m.add(save);
        m.add(saveAs);
        
        mbr.add(m);
        mbr.add(java);
        share.addActionListener(this);
        txt= new TextArea();
        txt.setBackground(Color.BLACK);
        txt.setForeground(Color.WHITE);
        txt.addKeyListener(this);
        txt.setFont(new Font("Monospace",Font.PLAIN,16));
        setMenuBar(mbr);
        add(txt);
        setTitle("Untitled");

        Thread t1 = new Thread()
        {
            public void run()
            {
             try{ 

                Firestore db= FirestoreClient.getFirestore();
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
                    loc=txt.getCaretPosition();
                
                    // txt.removeKeyListener(Liveshare.this);
                    txt.setText(snapshot.getString("Content"));
                    // txt.addKeyListener(Liveshare.this);
                    txt.setCaretPosition(loc);
                    } else {
                    System.out.print("Current data: null");
                    }
                    }
                }); }catch(Exception e){};
            } 
            };
                t1.start();

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
        System.out.println("File saved");
        // txt.setText(Content)
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        String s= ae.getActionCommand();
        System.out.println(s);
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
                System.out.println(sb +" "+p);
                saveFile(p,sb);
            } catch (Exception e) {
               System.out.println(e);
            }

        }
        else if(s.equals("Share")) 
        {
            
            

            
        }
       
        else 
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
        
    }
    public void lshare() throws Exception
    {
                   
          String content = txt.getText();
         HashMap<String,String> hm = new HashMap<>();
                    hm.put("Content",content);
                    Firestore db= FirestoreClient.getFirestore();
                    ApiFuture<WriteResult> future =db.collection("Live").document("Share").set(hm);
        
      


    }
    public void keyTyped( KeyEvent ke)
    {
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
     public void keyPressed( KeyEvent ke)
    {}
    public void keyReleased( KeyEvent ke)
    {}
    // public void keyTyped( KeyEvent ke)
    // {}
    public static void main(String a[])throws Exception
    {
        new Liveshare();
    }
}