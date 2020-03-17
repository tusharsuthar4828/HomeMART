package com.homemart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.homemart.BasicActivities.LoginSignUpActivity;
import com.homemart.Fragments.AboutUs;
import com.homemart.Fragments.ContactUs;
import com.homemart.Fragments.Home;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    MenuItem mLogoutMenuItem;
    DrawerLayout mDrawerLayout;
    Menu nav_Menu;
    FirebaseUser currentUser;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        NavigationView navigationView = findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        //toolbar.setLogo(R.drawable.logo);


        //FIREBASE
        //mAuth = FirebaseAuth.getInstance();
        //mDatabaseReference = FirebaseDatabase.getInstance().getReference("User").child(mAuth.getUid()+"");
        mDrawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new Home(), "HOME").commit();

    }


    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("SELECTBUDGET");
        if (fragment != null && fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new Home(), "HOME").commit();
        } else {
            super.onBackPressed();
        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Log.d(TAG, "onNavigationItemSelected: " + itemId);

        switch (itemId) {
            case R.id.nav_home:
                Toast.makeText(this, "" + itemId, Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new Home(), "HOME").commit();
                break;
            case R.id.nav_contactus:
                Toast.makeText(this, "" + itemId, Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new ContactUs(), "CONTACTUS").commit();
                break;
            case R.id.nav_aboutus:
                Toast.makeText(this, "" + itemId, Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new AboutUs(), "ABOUTUS").commit();
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
