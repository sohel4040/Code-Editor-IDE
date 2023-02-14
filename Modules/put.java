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
public class put extends JFrame
{
    public put()
    {
      setVisible(true);
      setLayout(new FlowLayout());
      setDefaultCloseOperation(3);
      setSize(500,500);
    }
    public void addData(String value)
    {
        Thread t = new Thread()
        {
            public void run()
            {
                try {
                    HashMap<String,String> hm = new HashMap<>();
                    hm.put("Name",value);
                    Firestore db= FirestoreClient.getFirestore();
                    ApiFuture<WriteResult> future =db.collection("Students").document("123").set(hm);
                     
                    System.out.println("document updated" +future.get().getUpdateTime());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
           
        };
        t.start();
        
    }
    public static void main(String a[]) throws Exception
    {
        Class.forName("FBInit");
        put p= new put();
        TextField jt = new TextField(20);
        jt.addTextListener(new TextListener(){
            public void textValueChanged(TextEvent te)
            {
                p.addData(jt.getText());
            }
        });
        p.add(jt);
        JButton jb= new JButton("Add");
        jb.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                
                p.addData(jt.getText());
            }
        });
        p.add(jb);
        
        
    }
}