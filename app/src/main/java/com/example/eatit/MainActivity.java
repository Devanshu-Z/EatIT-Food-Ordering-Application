package com.example.eatit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.eatit.HelperClasses.HomeAdapter.bestAdaptor;
import com.example.eatit.HelperClasses.HomeAdapter.best_HelperClass;
import com.example.eatit.HelperClasses.HomeAdapter.main_menuAdaptor;
import com.example.eatit.HelperClasses.HomeAdapter.main_menuHelperClass;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    static final float END_SCALE = 0.7f;

    //Dashboard
    RecyclerView best_recycler;
    RecyclerView main_recycler;
    RecyclerView.Adapter adapter;

    // Drawer Layout
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView nav_menu;
    LinearLayout contentView;
    FirebaseAuth fauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Dashboard Hooks
        best_recycler = findViewById(R.id.best_recycler);
        main_recycler = findViewById(R.id.main_recycler);

        //Drawer Layout Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        nav_menu = findViewById(R.id.nav_menu);
        contentView = findViewById(R.id.content);
        fauth = FirebaseAuth.getInstance();

        //Dashboard
        bestRecycler();
        mainRecycler();

        //Drawer Layout
        navigationdrawer();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private void mainRecycler() {

        main_recycler.setHasFixedSize(true);
        main_recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));

        ArrayList<main_menuHelperClass> mainLocations = new ArrayList<>();
        mainLocations.add(new main_menuHelperClass(R.drawable.donalds,"MC Donalds", "Best Burger and Fries"));
        mainLocations.add(new main_menuHelperClass(R.drawable.domino,"Dominos Pizza", "Best Pan Pizza's"));
        mainLocations.add(new main_menuHelperClass(R.drawable.kfc,"KFC", "Best Fried Chicken"));
        mainLocations.add(new main_menuHelperClass(R.drawable.donalds,"MC Donalds", "Best Burger and Fries"));
        mainLocations.add(new main_menuHelperClass(R.drawable.domino,"Dominos Pizza", "Best Pan Pizza's"));
        mainLocations.add(new main_menuHelperClass(R.drawable.kfc,"KFC", "Best Fried Chicken"));

        adapter = new main_menuAdaptor(this,mainLocations);
        main_recycler.setAdapter(adapter);
    }

    private void bestRecycler() {

        best_recycler.setHasFixedSize(true);
        best_recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

        ArrayList<best_HelperClass> bestLocations = new ArrayList<>();
        bestLocations.add(new best_HelperClass(R.drawable.donalds,"MC Donalds", "Best Burger and Fries"));
        bestLocations.add(new best_HelperClass(R.drawable.domino,"Dominos Pizza", "Best Pan Pizza's"));
        bestLocations.add(new best_HelperClass(R.drawable.kfc,"KFC", "Best Fried Chicken"));

        adapter = new bestAdaptor(bestLocations);
        best_recycler.setAdapter(adapter);
    }

    private void navigationdrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_home:{
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        return true;
                    }
                    case R.id.nav_fav:{
                        Toast.makeText(MainActivity.this, "FAVOURITE", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    case R.id.nav_hist:{
                        Toast.makeText(MainActivity.this, "HISTORY", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    case R.id.nav_pro:{
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        finish();
                        return true;
                    }
                    case R.id.nav_faq:{
                        Toast.makeText(MainActivity.this, "FAQ's", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    case R.id.nav_logout:{
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),login_main.class));
                        finish();
                        return true;
                    }
                }


                return true;
            }
        });
        navigationView.setCheckedItem(R.id.nav_home);

        nav_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else
                {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        animatenav();
    }

    private void animatenav() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.colorAccent));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }


}
