package com.example.eatit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {
    FirebaseFirestore fstore;
    FirebaseAuth fauth;
    TextView pname, pphone , pmail , ploc , pmethod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pname = findViewById(R.id.pro_name);
        pmail = findViewById(R.id.pro_mail);
        pphone = findViewById(R.id.pro_phone);
        ploc = findViewById(R.id.pro_loc);
        pmethod = findViewById(R.id.pro_method);
        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        DocumentReference docref = fstore.collection("users").document(fauth.getCurrentUser().getUid());
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists())
                {
                    pname.setText(documentSnapshot.getString("name"));
                    pmail.setText(documentSnapshot.getString("email"));
                    ploc.setText(documentSnapshot.getString("Location"));
                    pphone.setText(documentSnapshot.getString("phone"));
                    pmethod.setText(documentSnapshot.getString("method"));
                }
            }
        });



    }


    @Override
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
