package com.example.eatit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_mail extends AppCompatActivity {
    EditText email, password;
    Button login_btn;
    TextView toreg;
    ProgressBar progressBar;
    FirebaseAuth fauthent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mail);

        email = findViewById(R.id.log_mail_mail);
        password = findViewById(R.id.log_mail_password);
        login_btn = findViewById(R.id.log_mail_login);
        toreg = findViewById(R.id.log_mail_toreg);
        progressBar = findViewById(R.id.progressBar);
        fauthent = FirebaseAuth.getInstance();

        if (fauthent.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (TextUtils.isEmpty(mail))
                {
                    email.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(pass) && pass.length() < 8)
                {
                    password.setError("Required Field must be Greater than 8 characters.");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fauthent.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(login_mail.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }else
                        {
                            Toast.makeText(login_mail.this, "Error : "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });

        toreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),registration_mail.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(),login_main.class));
    }
}
