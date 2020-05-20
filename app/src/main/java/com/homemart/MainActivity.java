package com.homemart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.homemart.Fragments.AboutUs;
import com.homemart.Fragments.BusinessProfile;
import com.homemart.Fragments.ContactUs;
import com.homemart.Fragments.DisplayAllSellers;
import com.homemart.Fragments.Home;
import com.homemart.utils.Capitalize;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final String TAG = MainActivity.class.getSimpleName();
    MenuItem mLogoutMenuItem;
    DrawerLayout mDrawerLayout;
    Menu nav_Menu;
    FirebaseUser currentUser;
    TextView mUserNameTextView;
    TextView mEmailTextView;
    private AppBarConfiguration mAppBarConfiguration;
    ImageView mProfileView, mBackgroundView;

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
        View headerView = navigationView.getHeaderView(0);
        mUserNameTextView = headerView.findViewById(R.id.display_user_name);
        mEmailTextView = headerView.findViewById(R.id.user_email);
        mProfileView = headerView.findViewById(R.id.imageView);
        mBackgroundView = headerView.findViewById(R.id.background_imageview);



        FirebaseDatabase.getInstance().getReference().child("sellers").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String imageURL = dataSnapshot.child("imageURL").getValue().toString();
                if(!imageURL.isEmpty()){
                    Picasso.get().load(imageURL)
                            .placeholder(R.drawable.loading)
                            .fit().centerCrop(1).into(mProfileView);

                    Picasso.get().load(imageURL)
                            .placeholder(R.color.white)
                            .fit().centerCrop(1).into(mBackgroundView);
                }

                mUserNameTextView.setText(Capitalize.capitalize(dataSnapshot.child("username").getValue().toString()));
                mEmailTextView.setText(dataSnapshot.child("email").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new Home(), "Home").commit();

        //BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        //toolbar.setTitle("Shop");
                        Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_nearby:
                        //toolbar.setTitle("My Gifts");
                        Toast.makeText(this, "home1", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_business:
                        //toolbar.setTitle("Cart");
                        Toast.makeText(this, "home2", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            };

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag("DisplaySellerProducts") != null && getSupportFragmentManager().findFragmentByTag("DisplaySellerProducts").isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new DisplayAllSellers(), "DisplayAllSellers").commit();
        } else if (getSupportFragmentManager().findFragmentByTag("DisplayAllSellers") != null && getSupportFragmentManager().findFragmentByTag("DisplayAllSellers").isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new Home(), "HOME").commit();
        } else if (getSupportFragmentManager().findFragmentByTag("BusinessProfile") != null && getSupportFragmentManager().findFragmentByTag("BusinessProfile").isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new Home(), "Home").commit();
        } else if (getSupportFragmentManager().findFragmentByTag("ContactUs") != null && getSupportFragmentManager().findFragmentByTag("ContactUs").isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new Home(), "Home").commit();
        } else if (getSupportFragmentManager().findFragmentByTag("AboutUs") != null && getSupportFragmentManager().findFragmentByTag("AboutUs").isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new Home(), "Home").commit();
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
                //Toast.makeText(this, "" + itemId, Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new Home(), "Home").commit();
                break;
            case R.id.nav_business:
                //Toast.makeText(this, "" + itemId, Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new ContactUs(), "ContactUs").commit();
                break;
            case R.id.nav_aboutus:
                //Toast.makeText(this, "" + itemId, Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new AboutUs(), "AboutUs").commit();
                break;
            case R.id.nav_nearby:
                //Toast.makeText(this, "" + itemId, Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new BusinessProfile(), "BusinessProfile").commit();
                break;
            case R.id.nav_logout:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    FirebaseAuth.getInstance().signOut();
                    //mLogoutMenuItem.setVisible(false);
                    //mUserNameTextView.setText("User not logged");
                    //mEmailTextView.setText("Click here to login !");
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new Home(),"HOME").commit();
                    startActivity(new Intent(MainActivity.this, SplashActivity.class));
                    finish();
                }

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
