package com.example.instagram_clone;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.instagram_clone.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.Inet4Address;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {


    private EditText email, password,userName,confirmPassword;
    private String txtEmail, txtPassword,txtUserName,txtConfirmPassword;
    private TextView login;
    private Button btnSignUp;


    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseFirestore mFireStore;

    public void init(){
        mAuth=FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        userName=findViewById(R.id.username);
        confirmPassword=findViewById(R.id.confirm_password);
        btnSignUp=findViewById(R.id.btnSignUp);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUserName=userName.getText().toString().trim();
                txtEmail=email.getText().toString().trim();
                txtPassword=password.getText().toString().trim();
                txtConfirmPassword=confirmPassword.getText().toString().trim();


                if(!TextUtils.isEmpty(txtUserName)){
                    if (!TextUtils.isEmpty(txtEmail)){
                        if (!TextUtils.isEmpty(txtPassword)){
                            if (!TextUtils.isEmpty(txtConfirmPassword)){
                                if (txtPassword.equals(txtConfirmPassword)){

                                    mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword)
                                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()){
                                                        mUser = mAuth.getCurrentUser();
                                                        if (mUser != null){

                                                            Map<String,Object> user =new HashMap<>();
                                                            user.put("UserName",txtUserName);
                                                            user.put("Name","");
                                                            user.put("Bio","");
                                                            user.put("ImageUrl","https://tr.wikipedia.org/wiki/Instagram");
                                                            user.put("Email",txtEmail);
                                                            user.put("Password",txtPassword);

                                                            mFireStore.collection("users").document(mUser.getUid())
                                                                    .collection("userKnowledge").document()
                                                                    .set(user).addOnSuccessListener(SignUpActivity.this, new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void unused) {

                                                                            Toast.makeText(SignUpActivity.this, "You have successfully registered.", Toast.LENGTH_SHORT).show();
                                                                            finish();
                                                                            Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                                                                            startActivity(intent);
                                                                        }
                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(SignUpActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });

                                                        }

                                                    }else{

                                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                                    }


                                                }
                                            });

                                }else
                                    Toast.makeText(SignUpActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();

                            }else
                                Toast.makeText(SignUpActivity.this, "Please enter the password information again.", Toast.LENGTH_SHORT).show();

                        }else
                            Toast.makeText(SignUpActivity.this, "Please choose a valid password.", Toast.LENGTH_SHORT).show();

                    }else
                        Toast.makeText(SignUpActivity.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(SignUpActivity.this, "Please enter a valid username information.", Toast.LENGTH_SHORT).show();

            }
        });

    }


}