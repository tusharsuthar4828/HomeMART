package com.homemart;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.homemart.BasicActivties.LoginSignUpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.signInButton)
    Button mSignInButton;

    @BindView(R.id.registerbutton)
    Button mRegisterButton;

    @BindView(R.id.nameTextView)
    TextView mNameTextView;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, LoginSignUpActivity.class);

                Pair[] pairs = new Pair[2];

                //first
                pairs[0] = new Pair<View, String>(mNameTextView, "nameTransition");
                //second
                pairs[1] = new Pair<View, String>(mSignInButton, "buttonTransition");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);

                intent.putExtra("activity", "login");
                startActivity(intent, activityOptions.toBundle());


            }
        });


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SplashActivity.this, LoginSignUpActivity.class);

                Pair[] pairs = new Pair[2];

                //first
                pairs[0] = new Pair<View, String>(mNameTextView, "nameTransition");
                //second
                pairs[1] = new Pair<View, String>(mSignInButton, "buttonTransition");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);

                intent.putExtra("activity", "signup");
                startActivity(intent, activityOptions.toBundle());


            }
        });


        /*mRegisterButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Toast.makeText(SplashActivity.this, "s", Toast.LENGTH_SHORT).show();
                Intent intent =  new Intent(SplashActivity.this,SampleActivity.class);

                Pair[] pairs = new Pair[2];

                //first
                pairs[0] = new Pair<View,String>(mNameTextView,"nameTransition");
                //second
                pairs[1] = new Pair<View,String>(mSignInButton,"buttonTransition");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,pairs);

                intent.putExtra("activity","signup");
                startActivity(intent,activityOptions.toBundle());
                return true;
            }
        });*/
    }
}
