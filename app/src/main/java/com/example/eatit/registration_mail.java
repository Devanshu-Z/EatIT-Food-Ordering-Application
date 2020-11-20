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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registration_mail extends AppCompatActivity {
    EditText name, email, password, phone, location;
    Button register_btn;
    TextView tologin;
    FirebaseAuth fauthent;
    FirebaseFirestore fstore;
    ProgressBar progressBar;
    String userid, FLAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_mail);

        name = findViewById(R.id.reg_mail_name);
        email = findViewById(R.id.reg_mail_mail);
        password = findViewById(R.id.reg_mail_password);
        phone = findViewById(R.id.reg_mail_phone);
        location = findViewById(R.id.reg_mail_loc);
        register_btn = findViewById(R.id.reg_mail_register);
        tologin = findViewById(R.id.reg_mail_tologin);
        fauthent = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        progressBar =findViewById(R.id.progressBar2);
        FLAG = "Email Password Registration";




        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String uname = name.getText().toString();
                String uphone = phone.getText().toString();
                String uloc = location.getText().toString();


                if (TextUtils.isEmpty(mail) && TextUtils.isEmpty(uname) && TextUtils.isEmpty(uphone) && TextUtils.isEmpty(uloc)  && TextUtils.isEmpty(pass))
                {
                    Toast.makeText(registration_mail.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length() < 8)
                {
                    password.setError("Must be Greater than or Equal to 8 characters.");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fauthent.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(registration_mail.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                            createdatabse();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }else
                        {
                            Toast.makeText(registration_mail.this, "Error : "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }

            private void createdatabse() {

                userid = fauthent.getCurrentUser().getUid();
                final DocumentReference docref = fstore.collection("users").document(userid);

                String umail = email.getText().toString();
                String uname = name.getText().toString();
                String uphone = phone.getText().toString();
                String uloc = location.getText().toString();
                String umethod = FLAG;


                Map<String,Object> user = new HashMap<>();
                user.put("name", uname);
                user.put("email", umail);
                user.put("Location" , uloc);
                user.put("phone", uphone);
                user.put("method", umethod);


                docref.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(registration_mail.this, "Data Inserted", Toast.LENGTH_SHORT).show();

                        }else
                        {
                            Toast.makeText(registration_mail.this, "Data is not Inserted", Toast.LENGTH_SHORT).show();
                        }

                    }
                });




            }
        });


        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login_mail.class));
                finish();
            }
        });
    }


}
