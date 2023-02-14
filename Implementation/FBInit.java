import java.io.*;
import com.google.firebase.*;
import com.google.cloud.firestore.*;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

public class FBInit
{
    static{
            
    try {
        //firebase initialization   
        FileInputStream serviceAccount =
        new FileInputStream("./FB.json");
    
        FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build();
    
        FirebaseApp.initializeApp(options);
        
        System.out.println("Firebase Connected..");
    }catch(Exception e)
    {
        System.out.println("Exception in initialization : "+e);
    }    
    }
}