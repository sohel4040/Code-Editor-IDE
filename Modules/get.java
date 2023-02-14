import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.annotation.*;
import com.google.firebase.*;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.EventListener;
public class get extends JFrame
{
    public get()
    {
      setVisible(true);
      setLayout(new FlowLayout());
      setDefaultCloseOperation(3);
      setSize(500,500);
    }
    public static void main(String a[]) throws Exception
    {
        Class.forName("FBInit");
        get g = new get();
        JTextField jt = new JTextField(40);
        g.add(jt);
        Firestore db= FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("Students").document("123");
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
            // jt.setText(snapshot.getData()+"");
            jt.setText(snapshot.getString("Name"));
            
            } else {
            System.out.print("Current data: null");
            }
            }
        });
        // As an admin, the app has access to read and write all data, regardless of Security Rules
        // final FirebaseDatabase database = FirebaseDatabase.getInstance();
        // DatabaseReference ref = FirebaseDatabase.getInstance()
        // .getReference("restricted_access/secret_document");

        // // Attach a listener to read the data at our posts reference
        // ref.addValueEventListener(new ValueEventListener() {
        //   @Override
        //   public void onDataChange(DataSnapshot dataSnapshot) {
        //     Post post = dataSnapshot.getValue(Post.class);
        //     System.out.println(post);
        //   }
        
        //   @Override
        //   public void onCancelled(DatabaseError databaseError) {
        //     System.out.println("The read failed: " + databaseError.getCode());
        //   }
        // });
    }
}