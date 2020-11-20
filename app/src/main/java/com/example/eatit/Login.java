package com.example.eatit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    public static final String TAG = "TAG";
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    Button nxt_btn;
    EditText log_phone, otp;
    TextView resend;
    ProgressBar progressBar;
    CountryCodePicker codePicker;
    String verificationid;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean Verificationinprogress = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fauth = FirebaseAuth.getInstance();
        nxt_btn = findViewById(R.id.nxt_btn);
        log_phone = findViewById(R.id.log_phone);
        otp = findViewById(R.id.otp);
        resend = findViewById(R.id.resend);
        progressBar = findViewById(R.id.progressBar);
        codePicker = findViewById(R.id.ccp);
        fstore = FirebaseFirestore.getInstance();

        nxt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Verificationinprogress)
                {
                    if(!log_phone.getText().toString().isEmpty() && log_phone.getText().toString().length()==10)
                    {
                        String phonenum = "+"+codePicker.getSelectedCountryCode()+log_phone.getText().toString();
                        Log.d(TAG, "onClick: Phone NO. ->"+ phonenum);
                        progressBar.setVisibility(View.VISIBLE);

                        requestotp(phonenum);

                    }else
                    {
                        log_phone.setError("Phone Number is not Valid.");
                    }
                }else
                {
                    String userotp = otp.getText().toString();
                    if (userotp.length() == 6)
                    {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, userotp);
                        verifyAuth(credential);
                    }else
                    {
                        otp.setError("Enter Valid OTP");
                    }

                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (fauth.getCurrentUser()!=null)
        {
            progressBar.setVisibility(View.VISIBLE);
            checkuserprofile();
        }
    }



    private void verifyAuth(PhoneAuthCredential credential) {
        fauth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {

                    checkuserprofile();

                }else
                {
                    Toast.makeText(Login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





    private void checkuserprofile() {
        DocumentReference docref= fstore.collection("users").document(fauth.getCurrentUser().getUid());
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists())
                {

                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }else
                {

                    startActivity(new Intent(getApplicationContext(),Registeration.class));
                    finish();
                }
            }
        });
        Toast.makeText(Login.this, "Authentication is Successfull", Toast.LENGTH_SHORT).show();
    }
    private void requestotp(String phonenum) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenum, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationid = s;
                token = forceResendingToken;
                progressBar.setVisibility(View.GONE);
                otp.setVisibility(View.VISIBLE);
                nxt_btn.setText("VERIFY");

                Verificationinprogress = true;

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Toast.makeText(Login.this, "OTP Expired.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                verifyAuth(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(Login.this, "Cannot Create Account "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
