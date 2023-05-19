package com.example.instagram_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email, password;
    private String txtEmail, txtPassword;

    private TextView signUp;
    private Button btnLogin;
    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;


    private void init(){
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnLogin);
        signUp=findViewById(R.id.signUp);
        preferences = getSharedPreferences("GirisBilgi", MODE_PRIVATE);
        editor = preferences.edit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        String p_email = preferences.getString("email", "");
        String p_password = preferences.getString("password", "");

        if (!TextUtils.isEmpty(p_email) && !TextUtils.isEmpty(p_password)) {

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEmail= email.getText().toString().trim();
                txtPassword= password.getText().toString().trim();

                if (!TextUtils.isEmpty(txtEmail))
                {

                    if (!TextUtils.isEmpty(txtPassword))
                    {
                        signIn(txtEmail,txtPassword);;
                    }

                    else
                    {
                        //password empty
                        Toast.makeText(LoginActivity.this, "Please enter a valid password.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    //email empty
                    Toast.makeText(LoginActivity.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                }



            }

            private void signIn(String txtEmail, String txtPassword) {
                mAuth.signInWithEmailAndPassword(txtEmail,txtPassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    editor.putString("email", txtEmail);
                                    editor.putString("password", txtPassword);
                                    editor.apply();


                                    Toast.makeText(LoginActivity.this, "You have successfully logged in", Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);

                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);

            }
        });

    }


}