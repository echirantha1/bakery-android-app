package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainSignup extends AppCompatActivity {

    private EditText txt_email, txt_password, txt_name;
    private AppCompatButton btn_singup, btn_login;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase real;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_signup);

        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        txt_name = findViewById(R.id.txt_name);
        btn_singup = findViewById(R.id.btn_signup_page);
        btn_login = findViewById(R.id.btn_login_singup_page);

        firebaseAuth = FirebaseAuth.getInstance();
        real = FirebaseDatabase.getInstance();
        user = firebaseAuth.getCurrentUser();

        //Client Login Code
        DatabaseReference myRef = real.getReference().child("users");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainSignup.this, MainLogin.class));
            }
        });

        btn_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String clientEmail = txt_email.getText().toString();
                String clientPass = txt_password.getText().toString();
                String clientName = txt_name.getText().toString();

                if (clientEmail.isEmpty() || clientPass.isEmpty() || clientName.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }

                else if (clientPass.length()<5)
                {
                    Toast.makeText(getApplicationContext(), "Password Should atleast Contain 6 Characters", Toast.LENGTH_SHORT).show();
                }

                else{
                    firebaseAuth.createUserWithEmailAndPassword(clientEmail, clientPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                String userId = firebaseAuth.getCurrentUser().getUid();

                                Map<String, Object> user = new HashMap<>();
                                user.put("Email", clientEmail);
                                user.put("Name", clientName);

                                FirebaseDatabase.getInstance().getReference("user").child(userId).push()
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });

                                Toast.makeText(getApplicationContext(), "Sucessfully Registered", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainSignup.this, MainLogin.class));

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }
        });
    }
}