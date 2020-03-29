package com.homemart.BasicActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.homemart.MainActivity;
import com.homemart.R;
import com.homemart.models.Seller;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginSignUpActivity extends AppCompatActivity {

    @BindView(R.id.signin_button)
    Button mSignInButton;
    @BindView(R.id.register_button)
    Button mRegisterButton;
    @BindView(R.id.signup_button)
    Button mSignUpButton;
    @BindView(R.id.registered_button)
    Button mRegisteredButton;

    @BindView(R.id.signin_linearlayout)
    LinearLayout mSignInLinearLayout;
    @BindView(R.id.signup_linearlayout)
    LinearLayout mSignUpLinearLayout;

    @BindView(R.id.signin_email_edittext)
    EditText mSignInEmailEditext;

    @BindView(R.id.signin_password_edittext)
    EditText mSignInPasswordEditext;

    @BindView(R.id.signup_name_edittext)
    EditText mSignUpNameEditext;

    @BindView(R.id.signup_phoneno_edittext)
    EditText mSignUpPhonenoEditext;

    @BindView(R.id.signup_email_edittext)
    EditText mSignUpEmailEditext;

    @BindView(R.id.signup_password_edittext)
    EditText mSignUpPasswordEditext;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginSignUpActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("sellers");

        mRegisterButton.setOnClickListener(v -> {
            mSignInLinearLayout.setVisibility(View.GONE);
            mSignUpLinearLayout.setVisibility(View.VISIBLE);

        });
        mRegisteredButton.setOnClickListener(v -> {
            mSignUpLinearLayout.setVisibility(View.GONE);
            mSignInLinearLayout.setVisibility(View.VISIBLE);

        });

        mSignInButton.setOnClickListener(v -> {
            if (mSignInEmailEditext.getText().toString().isEmpty()) {
                Toast.makeText(LoginSignUpActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
            } else if (mSignInPasswordEditext.getText().toString().isEmpty() || mSignInPasswordEditext.getText().toString().length() < 6) {
                Toast.makeText(LoginSignUpActivity.this, "Password should be min 6 digit", Toast.LENGTH_SHORT).show();
            } else {
                loginUser();
            }
        });

        mSignUpButton.setOnClickListener(v -> {


            if (mSignUpNameEditext.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
            } else if (mSignUpEmailEditext.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            }
            if ((mSignUpPhonenoEditext.getText().toString().isEmpty()) || mSignUpPhonenoEditext.getText().length() != 10) {
                Toast.makeText(this, "Enter Valid phone no !", Toast.LENGTH_SHORT).show();
            } else if (mSignUpPasswordEditext.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            } else if (mSignUpPasswordEditext.getText().toString().length() < 6) {
                Toast.makeText(this, "Password should be min 6 digit", Toast.LENGTH_SHORT).show();
            } else {
                createUser();
            }

        });
    }

    private void loginUser() {
        final ProgressDialog progressDialog = ProgressDialog.show(LoginSignUpActivity.this, "Please wait...", "Proccessing...", true);

        final String mEmail = mSignInEmailEditext.getText().toString();
        final String mPassword = mSignInPasswordEditext.getText().toString();

        mFirebaseAuth.signInWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();

                    if (task.isSuccessful()) {
                        Toast.makeText(LoginSignUpActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginSignUpActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();


                    } else {
                        Log.e("ERROR", Objects.requireNonNull(task.getException()).toString());
                        //Toast.makeText(LoginSignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Toast.makeText(LoginSignUpActivity.this, "Wrong Credentials !", Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void createUser() {

        String mUsername = mSignUpNameEditext.getText().toString().trim();
        String mPhone = mSignUpPhonenoEditext.getText().toString().trim();
        String mEmail = mSignUpEmailEditext.getText().toString().trim();
        String mPassword = mSignUpPasswordEditext.getText().toString().trim();

        //final User user = new User(mUsername, mEmail, mPassword, mPhone);

        Seller seller = new Seller(mUsername, mEmail, mPassword, "", mPhone, "", 0);

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait...", "Processing...", true);
        (mFirebaseAuth.createUserWithEmailAndPassword(mEmail, mPassword))
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();


                    if (task.isSuccessful()) {
                        Toast.makeText(LoginSignUpActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                        String unique_id = mFirebaseAuth.getUid();
                        mDatabaseReference.child(unique_id).setValue(seller);

                        //List<String> categorylist = new ArrayList<>();

                        FirebaseDatabase.getInstance().getReference().child("links").child("category").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                for (DataSnapshot dataSnapshotobject : children) {
                                    //categorylist.add(dataSnapshotobject.getKey());
                                    mDatabaseReference.child(unique_id).child("categories").child(dataSnapshotobject.getKey().toString()).setValue("nothing");
                                }
                                mDatabaseReference.child(unique_id).child("address").setValue("nothing");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        mFirebaseAuth.signOut();
                        Intent intent = new Intent(LoginSignUpActivity.this, LoginSignUpActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e("ERROR", task.getException().toString());
                        //Toast.makeText(RegistrationActivity.this,"registration failed", Toast.LENGTH_LONG).show();
                        /*
                        String exceptionGenerated = task.getException().getMessage();
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginSignUpActivity.this);


                        LayoutInflater layoutInflater = LayoutInflater.from(LoginSignUpActivity.this);
                        View view = layoutInflater.inflate(R.layout.dailog_exception, null, false);

                        alertException_textView = view.findViewById(R.id.showException_TextView);
                        alertException_textView.setText(exceptionGenerated);

                        mBuilder.setView(view);
                        dailog = mBuilder.create();
                        dailog.show();
                        */
                        Toast.makeText(LoginSignUpActivity.this, "Registration Failed !", Toast.LENGTH_LONG).show();
                    }
                });

    }
}
