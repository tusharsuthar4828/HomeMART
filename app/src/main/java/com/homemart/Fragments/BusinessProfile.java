package com.homemart.Fragments;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.homemart.Adapters.DisplayBuinessSellerProductsAdapter;
import com.homemart.R;
import com.homemart.models.CategoryToDisplay;
import com.homemart.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static android.app.Activity.RESULT_OK;
import static com.homemart.utils.Capitalize.capitalize;


/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessProfile extends Fragment {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST_TWO = 2;

    AlertDialog alertDialog;
    DatabaseReference mSignUpDatabaseReference;
    //MAIN PART

    private String mSellerName;
    private String mSellerPhoneNo;
    private String mSellerRating;
    private String mSellerProfileURL;
    private String mSellerDescription;

    @BindView(R.id.sellername_textview)
    TextView mSellerNameTextView;

    @BindView(R.id.phoneno_textview)
    TextView mSellerPhoneNoTextView;

    @BindView(R.id.description_textview)
    TextView mSellerDescriptionTextView;

    @BindView(R.id.display_rating)
    MaterialRatingBar mMaterialRatingBar;

    @BindView(R.id.seller_profile_image)
    ImageView mSellerProfileImageView;

    @BindView(R.id.background_image)
    ImageView mBackgroundImageView;

    @BindView(R.id.edit_details)
    Button mEditDetailsButton;

    public BusinessProfile() {
        // Required empty public constructor
    }


    FirebaseAuth mAuth;
    private String username = "";
    private String phoneno = "";
    private String address1 = "";
    private String address2 = "";
    private String city = "";
    private String state = "";
    private String pincode = "";
    private String country = "";
    private String description = "";

    public void setAddress(String address1, String address2, String city, String state, String pincode, String country) {
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.country = country;

    }

    public void setUser(String username, String phoneno, String description) {
        this.username = username;
        this.phoneno = phoneno;
        this.description = description;
    }

    private boolean userfetch = false;
    private boolean addressfetch = false;
    private boolean gotobusiness = false;
    Uri mUri = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_business_profile, container, false);
        ButterKnife.bind(this, rootView);

        mAuth = FirebaseAuth.getInstance();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(rootView.getContext());
        View mView = getLayoutInflater().inflate(R.layout.dailog_address, null);

        EditText mUsernameEditText = mView.findViewById(R.id.username);
        EditText mPhonenoEditText = mView.findViewById(R.id.phoneno);

        EditText mAddress1EditText = mView.findViewById(R.id.address_1);
        EditText mAddress2EditText = mView.findViewById(R.id.address_2);
        EditText mCityEditText = mView.findViewById(R.id.city);
        EditText mStateEditText = mView.findViewById(R.id.state);
        EditText mPincodeEditText = mView.findViewById(R.id.pincode);
        EditText mCountryEditText = mView.findViewById(R.id.country);
        EditText mDescriptionEditText = mView.findViewById(R.id.description);
        Button mSubmitButton = mView.findViewById(R.id.submit);
        Button mCancelButton = mView.findViewById(R.id.cancel);

        mBuilder.setView(mView);
        alertDialog = mBuilder.create();
        alertDialog.setCancelable(false);

        mSignUpDatabaseReference = FirebaseDatabase.getInstance().getReference().child("sellers").child(mAuth.getUid());

        mSignUpDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                setUser(dataSnapshot.child("username").getValue().toString(),
                        dataSnapshot.child("phoneno").getValue().toString(),
                        dataSnapshot.child("description").getValue().toString());
                userfetch = true;


                String address = dataSnapshot.child("address").getValue().toString();
                if (address.equals("nothing")) {
                    alertDialog.show();
                    mUsernameEditText.setText(username);
                    mPhonenoEditText.setText(phoneno);

                } else {
                    setAddress(dataSnapshot.child("address").child("address1").getValue().toString(),
                            dataSnapshot.child("address").child("address2").getValue().toString(),
                            dataSnapshot.child("address").child("city").getValue().toString(),
                            dataSnapshot.child("address").child("state").getValue().toString(),
                            dataSnapshot.child("address").child("pincode").getValue().toString(),
                            dataSnapshot.child("address").child("country").getValue().toString());

                    addressfetch = true;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mSignUpDatabaseReference.child("address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUsernameEditText.getText().toString().trim();
                phoneno = mPhonenoEditText.getText().toString().trim();

                address1 = mAddress1EditText.getText().toString().trim();
                address2 = mAddress2EditText.getText().toString().trim();
                city = mCityEditText.getText().toString().trim();
                state = mStateEditText.getText().toString().trim();
                pincode = mPincodeEditText.getText().toString().trim();
                country = mCountryEditText.getText().toString().trim();
                description = mDescriptionEditText.getText().toString().trim();

                saveDetails(username, phoneno, address1, address2, city, state, pincode, country, description, mView);
            }
        });

        //EDIT DETAILS

        mEditDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userfetch) {
                    alertDialog.show();
                    mUsernameEditText.setText(username);
                    mPhonenoEditText.setText(phoneno);

                    mAddress1EditText.setText(address1);
                    mAddress2EditText.setText(address2);

                    mCityEditText.setText(city);
                    mStateEditText.setText(state);

                    mPincodeEditText.setText(pincode + "");
                    mCountryEditText.setText(country);

                    mDescriptionEditText.setText(description);

                } else {
                    Toast.makeText(rootView.getContext(), "Internet Error ! Try Again !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address1.isEmpty() || address2.isEmpty() || city.isEmpty() || state.isEmpty() || pincode.isEmpty() || country.isEmpty() || description.isEmpty()) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatiner, new Home(), "Home").commit();
                }
                alertDialog.dismiss();
            }
        });

        //MAIN PART


        List<CategoryToDisplay> categoryData = new ArrayList<>();

        //FIREBASE
        DatabaseReference mSellerProductsDatabaseReference;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        mSellerProductsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("sellers").child(currentUser.getUid());

        mSellerProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_IMAGE_REQUEST_TWO);
            }
        });
        mSellerProductsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mSellerProfileURL = dataSnapshot.child("imageURL").getValue().toString();

                if (!mSellerProfileURL.isEmpty()) {

                    Picasso.get().load(mSellerProfileURL)
                            .placeholder(R.drawable.loading)
                            .fit().centerCrop().into(mSellerProfileImageView);

                    Picasso.get().load(mSellerProfileURL)
                            .placeholder(R.color.white)
                            .fit().centerCrop().into(mBackgroundImageView);
                }

                mSellerName = dataSnapshot.child("username").getValue().toString();
                mSellerPhoneNo = dataSnapshot.child("phoneno").getValue().toString();
                mSellerRating = dataSnapshot.child("rating").getValue().toString();
                mSellerDescription = dataSnapshot.child("description").getValue().toString();


                mSellerNameTextView.setText(capitalize(mSellerName) + "'s Store");
                mSellerPhoneNoTextView.setText("+91 " + mSellerPhoneNo);
                mMaterialRatingBar.setRating(Float.parseFloat(mSellerRating));
                mSellerDescriptionTextView.setText(mSellerDescription);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mSellerProductsDatabaseReference.child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> categorylist = dataSnapshot.getChildren();

                categoryData.clear();
                for (DataSnapshot eachCategory : categorylist) {
                    List<Product> productData = new ArrayList<>();

                    Iterable<DataSnapshot> productlist = eachCategory.getChildren();
                    Log.d("TAG", "#####onDataChange: " + eachCategory.getValue().toString());
                    if (!eachCategory.getValue().toString().equals("nothing")) {

                        for (DataSnapshot eachproduct : productlist) {

                            Object object = eachproduct.getValue(Object.class);
                            String json = new Gson().toJson(object);
                            Log.d("Log3", "onDataChange: " + json);

                            Product product = new Gson().fromJson(json, Product.class);
                            Log.d("Log4", "onDataChange: " + product.getDescription());
                            Log.d("Log4", "onDataChange: " + product.getName());
                            product.setKey(eachproduct.getKey());
                            product.setProducttype(eachCategory.getKey());
                            productData.add(product);


                        }
                    }
                    Log.d("TAG", "onDataChange: " + productData.size());
                    //if (productData.size() > 0) {
                    categoryData.add(new CategoryToDisplay(eachCategory.getKey(), productData));
                    //}
                }
                RecyclerView recyclerView = rootView.findViewById(R.id.sellerproducts_recyclerview);
                LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());

                //instantiate your adapter with the list of genres
                Log.d("TAG", "onDataChange: ");
                DisplayBuinessSellerProductsAdapter adapter = new DisplayBuinessSellerProductsAdapter(categoryData, BusinessProfile.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                /*recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                             recyclerView.findViewHolderForAdapterPosition().itemView.performClick();
                    }
                });*/


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootView;
    }

    private void saveDetails(String username, String phoneno, String address1, String address2, String city, String state, String pincode, String country, String description, View mView) {

        if (username.isEmpty())
            Toast.makeText(mView.getContext(), "Enter Username !", Toast.LENGTH_SHORT).show();
        else if (phoneno.isEmpty())
            Toast.makeText(mView.getContext(), "Enter phoneno !", Toast.LENGTH_SHORT).show();
        else if (Long.parseLong(phoneno) < 1000000000)
            Toast.makeText(mView.getContext(), "Enter valid phoneno !", Toast.LENGTH_SHORT).show();
        else if (address1.isEmpty())
            Toast.makeText(mView.getContext(), "Enter Address !", Toast.LENGTH_SHORT).show();
        else if (address2.isEmpty())
            Toast.makeText(mView.getContext(), "Enter Address !", Toast.LENGTH_SHORT).show();
        else if (city.isEmpty())
            Toast.makeText(mView.getContext(), "Enter City !", Toast.LENGTH_SHORT).show();
        else if (state.isEmpty())
            Toast.makeText(mView.getContext(), "Enter State !", Toast.LENGTH_SHORT).show();
        else if (pincode.isEmpty())
            Toast.makeText(mView.getContext(), "Enter Pincode !", Toast.LENGTH_SHORT).show();
        else if (Integer.parseInt(pincode) < 100000)
            Toast.makeText(mView.getContext(), "Enter Proper Pincode !", Toast.LENGTH_SHORT).show();
        else if (country.isEmpty())
            Toast.makeText(mView.getContext(), "Enter Country !", Toast.LENGTH_SHORT).show();
        else if (description.isEmpty())
            Toast.makeText(mView.getContext(), "Enter Description !", Toast.LENGTH_SHORT).show();
        else {
            mSignUpDatabaseReference.child("address").setValue(new Address(address1, address2, city, state, Integer.parseInt(pincode), country));
            mSignUpDatabaseReference.child("username").setValue(username);
            mSignUpDatabaseReference.child("phoneno").setValue(phoneno);
            mSignUpDatabaseReference.child("description").setValue(description);
            alertDialog.dismiss();
        }
    }

    TextView mProductName;
    TextView mProductPrice;
    TextView mProductUnit;
    TextView mProductDescription;
    ImageView mProductImageView;
    Button mOkButton;
    Button mCancelButton;
    AlertDialog alertDialog_addproduct;
    AlertDialog.Builder mBuilder;
    RadioGroup mRadioGroup;
    RadioButton mVegButton, mNonVegBUtton;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    int mFoodtype = -1;
    private ProgressBar mProgressBar;

    public void prepareProduct(String category_name) {
        Context context = getActivity();
        //Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();

        mBuilder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.dailog_add_product, null);

        mProductName = mView.findViewById(R.id.product_name);
        mProductPrice = mView.findViewById(R.id.product_price);
        mProductUnit = mView.findViewById(R.id.product_unit);
        mProductDescription = mView.findViewById(R.id.product_description);
        mProductImageView = mView.findViewById(R.id.upload_preview);
        mOkButton = mView.findViewById(R.id.ok_button);
        mCancelButton = mView.findViewById(R.id.cancel_button);
        mProgressBar = mView.findViewById(R.id.upload_progress);
        mRadioGroup = mView.findViewById(R.id.radioGroup);

        mVegButton = mView.findViewById(R.id.radioVeg);
        mNonVegBUtton = mView.findViewById(R.id.radioNonVeg);


        mBuilder.setView(mView);

        alertDialog_addproduct = mBuilder.create();
        alertDialog_addproduct.setCancelable(false);
        alertDialog_addproduct.show();

        /*int selectedId = mRadioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton) findViewById(selectedId);
        if(selectedId==-1){
            Toast.makeText(getContext(),"Nothing selected", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getContext(),genderradioButton.getText(), Toast.LENGTH_SHORT).show();
        }*/
        mRadioGroup.clearCheck();
        mFoodtype = -1;

        mVegButton.setHighlightColor(getResources().getColor(R.color.green_500));
        mVegButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.green_500)));

        mNonVegBUtton.setHighlightColor(getResources().getColor(R.color.red_500));
        mNonVegBUtton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.red_500)));

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if (null != rb) {
                    //Toast.makeText(getContext(), rb.getText(), Toast.LENGTH_SHORT).show();

                    if (rb.getText().toString().trim().equals("Veg")) {
                        mFoodtype = 0;
                    } else {
                        mFoodtype = 1;
                    }
                }

            }
        });
        //mProductImageView.set
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog_addproduct.dismiss();
            }
        });

        mProductImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_IMAGE_REQUEST);
            }
        });

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProductName.getText().toString().isEmpty())
                    Toast.makeText(context, "Enter Product Name !", Toast.LENGTH_SHORT).show();
                else if (mProductPrice.getText().toString().isEmpty())
                    Toast.makeText(context, "Enter Price !", Toast.LENGTH_SHORT).show();
                else if (Integer.parseInt(mProductPrice.getText().toString()) < 0)
                    Toast.makeText(context, "Enter Proper Price !" + mProductPrice.getText(), Toast.LENGTH_SHORT).show();
                else if (mProductUnit.getText().toString().isEmpty())
                    Toast.makeText(context, "Enter Unit !", Toast.LENGTH_SHORT).show();
                else if (mProductDescription.getText().toString().isEmpty())
                    Toast.makeText(context, "Enter Description !", Toast.LENGTH_SHORT).show();
                else if (mFoodtype == -1)
                    Toast.makeText(context, "Select food type!", Toast.LENGTH_SHORT).show();
                else if (mUri == null)
                    Toast.makeText(context, "Select Image to upload !", Toast.LENGTH_SHORT).show();
                else {
                    uploadFile(category_name);
                    mOkButton.setEnabled(false);
                    Toast.makeText(context, "Product added", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void openFileChooser(int requestid) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, requestid);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mUri = data.getData();
            Picasso.get().load(mUri).into(mProductImageView);
        } else if (requestCode == PICK_IMAGE_REQUEST_TWO && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mUri = data.getData();
            Picasso.get().load(mUri).into(mSellerProfileImageView);
            uploadProfileImage();
        }

    }

    private void uploadProfileImage() {
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Please wait...", "Proccessing...", true);

        String fileName = System.currentTimeMillis() + "." + getFileExtension(mUri);
        final String path = "seller_profile_image/" + fileName;

        final StorageReference storageRef = storage.getReference();

        final StorageReference productImage = storageRef.child(path);
        final UploadTask uploadTask = productImage.putFile(mUri);


        Task<Uri> urlTask = uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                //mProgressBar.setProgress((int) progress);
            }
        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    Toast.makeText(getContext(), "error in upload !", Toast.LENGTH_SHORT).show();
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return productImage.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    mSignUpDatabaseReference.child("imageURL").setValue(downloadUri.toString());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //mProgressBar.setProgress(0);
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "File Uploaded", Toast.LENGTH_SHORT).show();
                            mUri = null;

                        }
                    }, 500);

                } else {
                    // Handle failures
                    // ...
                    Toast.makeText(getContext(), "Failed !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadFile(String category_name) {

        String fileName = System.currentTimeMillis() + "." + getFileExtension(mUri);
        final String path = "products_image/" + fileName;

        final StorageReference storageRef = storage.getReference();

        final StorageReference productImage = storageRef.child(path);
        final UploadTask uploadTask = productImage.putFile(mUri);


        Task<Uri> urlTask = uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                mProgressBar.setProgress((int) progress);
            }
        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return productImage.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    addProduct(downloadUri.toString(), category_name);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                            Toast.makeText(getContext(), "File Uploaded", Toast.LENGTH_SHORT).show();
                            if (alertDialog_addproduct.isShowing()) {
                                alertDialog_addproduct.dismiss();
                                mUri = null;
                            }
                        }
                    }, 500);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    private void addProduct(String imageURL, String category_name) {
        String name = mProductName.getText().toString().trim();
        int price = Integer.parseInt(mProductPrice.getText().toString());
        String unit = mProductUnit.getText().toString().trim();
        String desc = mProductDescription.getText().toString().trim();
        //int foodtype = mProductType.getText().toString();


        Product product = new Product(desc, imageURL, name, price, unit, mFoodtype);

        FirebaseDatabase.getInstance().getReference().child("sellers").child(mAuth.getUid()).child("categories").child(category_name).push().setValue(product);

        Toast.makeText(getContext(), "Product Added", Toast.LENGTH_SHORT).show();
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
