package com.example.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShareActivity extends AppCompatActivity {

    private ImageView imageAdd;
    private EditText description;
    private TextView post;
    private ArrayList<Post> postArrayList;
    private Uri selectedPhotoUri;
    private static final int PICK_PHOTO_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        imageAdd=findViewById(R.id.image_added);
        description=findViewById(R.id.description);
        post=findViewById(R.id.post);
        postArrayList=new ArrayList<>();

        imageAdd.setOnClickListener(v -> {
            // Fotoğraf seçmek için bir intent başlatın ve startActivityForResult kullanarak sonucu alın
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_PHOTO_REQUEST);
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = description.getText().toString().trim();
                postGonder(title, selectedPhotoUri);
            }
        });

    }


    @Override
    //GALERİDENN FOTOĞRAF EKLEYEBİLMEK İÇİN YAZILDI
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PHOTO_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedPhotoUri = data.getData();

            // Seçilen fotoğrafı ImageView'da gösterin veya başka bir işlem yapın
            imageAdd.setImageURI(selectedPhotoUri);

            // Seçilen fotoğrafın URI'sini diğer işlemlerde kullanabilirsiniz
            // ...
        }
    }

    //FOTOĞRAF FİREBASE DE DEPOLANDİ
    public void postGonder(String description, Uri selectedPhotoUri) {
        // Firebase Storage referansını alın
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // Fotoğrafın yükleneceği dosya adı (rastgele)
        String fileName = UUID.randomUUID().toString() + ".jpg";

        // Storage'a fotoğrafı yükleme
        StorageReference photoRef = storageRef.child(fileName);
        UploadTask uploadTask = photoRef.putFile(selectedPhotoUri);

        // Yükleme işlemi tamamlandığında
        uploadTask.addOnSuccessListener(taskSnapshot -> {
                    // Fotoğraf yükleme başarılı olduğunda yapılacak işlemler
                    // Yüklenen fotoğrafın URL'si
                    photoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String photoUrl = uri.toString();
                        String postId = UUID.randomUUID().toString(); // Post için benzersiz bir kimlik oluşturun
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Aktif kullanıcının kimliğini alın
                        Timestamp timestamp = new Timestamp(new Date()); // Şu anki zamanı alın

                        Map<String, Object> postData = new HashMap<>();
                        postData.put("postId", postId);
                        postData.put("userId", userId);
                        postData.put("photoUrl", photoUrl);
                        postData.put("description", description);
                        postData.put("timestamp", timestamp);

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("posts")
                                .document(postId)
                                .set(postData)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(ShareActivity.this, "Başarıyla Gerçekleştirildi.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ShareActivity.this,MainActivity.class);
                                    startActivity(intent);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(ShareActivity.this, "Hata oluştu", Toast.LENGTH_SHORT).show();
                                });
                    });
                })
                .addOnFailureListener(exception -> {
                    Toast.makeText(ShareActivity.this, "Fotoğraf yükleme başarısız.", Toast.LENGTH_SHORT).show();
                });
    }
}

