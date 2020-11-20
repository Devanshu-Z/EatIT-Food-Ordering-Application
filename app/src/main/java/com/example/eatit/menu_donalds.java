package com.example.eatit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class menu_donalds extends AppCompatActivity {

    TextView burger_1,burger_2;
    RelativeLayout menu1,menu2;
    int count=0;
    Button bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_donalds);
        burger_1 = findViewById(R.id.burger_1);
        burger_2 = findViewById(R.id.burger_2);
        bill = findViewById(R.id.bill);
        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);


            bill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), bill.class));
                    finish();
                }
            });


        burger_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count =1;
                menu1.setBackgroundResource(R.drawable.back_rect2);
                bill.setVisibility(View.VISIBLE);

            }
        });

        burger_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 1;
                menu2.setBackgroundResource(R.drawable.back_rect2);
            }
        });


    }
}
