package com.example.eatit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registeration extends AppCompatActivity {
    EditText name,email,loc;
    TextView phone;
    Button save;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    String userid;
    String phonenum ;
    String FLAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        name= findViewById(R.id.reg_name);
        email= findViewById(R.id.reg_mail);
        loc = findViewById(R.id.reg_loc);
        save = findViewById(R.id.save_btn);
        firebaseAuth= FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();
        phone = findViewById(R.id.reg_phone);
        FLAG = "Phone Number Registration";

        final DocumentReference docref = fstore.collection("users").document(userid);

        phone.setText(firebaseAuth.getCurrentUser().getPhoneNumber());
        phonenum = firebaseAuth.getCurrentUser().getPhoneNumber();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !loc.getText().toString().isEmpty() )
                {
                    String uname = name.getText().toString();
                    String umail = email.getText().toString();
                    String uloc = loc.getText().toString();
                    String uphone = phonenum;
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
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }else
                            {
                                Toast.makeText(Registeration.this, "Data is not Inserted", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }else
                {
                    Toast.makeText(Registeration.this, "All Fields are Required.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
