package com.imsearch;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.imsearch.fragments.AddressFragment;
import com.imsearch.fragments.ContactFragment;
import com.imsearch.fragments.CredentialFragment;
import com.imsearch.fragments.MasterFragment;
import com.imsearch.models.Address;
import com.imsearch.models.Contact;
import com.imsearch.models.Credential;
import com.imsearch.models.Person;

import java.util.HashMap;


public class SignUpFlow extends AppCompatActivity implements CredentialFragment.CredentialFragmentInteractionListener,
        ContactFragment.ContactFragmentInteractionListener, AddressFragment.AddressFragmentInteractionListener, View.OnClickListener {

    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private String TAG = "Debugging";
    private static HashMap<Integer, MasterFragment> fragmentsGroup = new HashMap<>();
    int currentPageNumber = 0;
    private Button nextButton, previousButton, registerButton;
    private Credential credential;
    private Contact contact;
    private Address address;
    private FirebaseAuth mAuth;
    private DatabaseReference database;


    static {
        fragmentsGroup.put(0, CredentialFragment.newInstance());
        fragmentsGroup.put(1, ContactFragment.newInstance());
        fragmentsGroup.put(2, AddressFragment.newInstance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_flow);

        previousButton = (Button) findViewById(R.id.previous);
        registerButton = (Button) findViewById(R.id.register);
        nextButton = (Button) findViewById(R.id.next);

        previousButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setAdapter(mPagerAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onClick(View v) {

        currentPageNumber = mPager.getCurrentItem();

        if(v.getId() == R.id.previous){

            if(currentPageNumber == 1){
                previousButton.setVisibility(View.INVISIBLE);
            }
             else if(currentPageNumber == 2){
                nextButton.setVisibility(View.VISIBLE);
                registerButton.setVisibility(View.INVISIBLE);
            }
            currentPageNumber = (currentPageNumber - 1)%3;
        }
         else if(v.getId() == R.id.register){

            if(getCurrentFragment().isAllFieldsPopulated()){

            }
        }
         else if(v.getId() == R.id.next){

            if(getCurrentFragment().isAllFieldsPopulated()){
                if(currentPageNumber == 0){
                    previousButton.setVisibility(View.VISIBLE);
                }
                else if(currentPageNumber == 1){
                    nextButton.setVisibility(View.INVISIBLE);
                    registerButton.setVisibility(View.VISIBLE);
                }
                currentPageNumber = (currentPageNumber + 1)%3;
            }
        }

        mPager.setCurrentItem(currentPageNumber);
    }

    private MasterFragment getCurrentFragment(){

        return SignUpFlow.fragmentsGroup.get(mPager.getCurrentItem());
    }

    @Override
    public void onFragmentInteraction(Credential credential) {
        this.credential = credential;

        if(credential != null){
            Log.d(TAG, "Email "+credential.getEmail());
            Log.d(TAG, "Password "+credential.getPassword());
        }
    }

    @Override
    public void onFragmentInteraction(Contact contact) {
        this.contact = contact;

        if(contact != null){
            Log.d(TAG, "First name "+contact.getFirstName());
            Log.d(TAG, "Last name "+contact.getLastName());
            Log.d(TAG, "Phone Number "+contact.getPhone());
        }
    }

    @Override
    public void onFragmentInteraction(Address address) {
        this.address = address;

        final Person person = new Person(credential, contact, address);

        registerUser(person);

        if(address != null){
            Log.d(TAG, "House "+address.getHouse());
            Log.d(TAG, "Street "+address.getStreet());
            Log.d(TAG, "City "+address.getCity());
            Log.d(TAG, "County"+address.getCounty());
            Log.d(TAG, "Country"+address.getCountry());
        }
    }

    public void registerUser(final Person person){

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        mAuth.createUserWithEmailAndPassword(person.getCredential().getEmail(), person.getCredential().getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task != null && task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    person.setId(user.getUid());

                    HashMap<String, Object> dic = new HashMap<String, Object>();
                    dic.put("user", person);

                    database.child("users").child(user.getUid()).updateChildren(dic, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if(databaseError != null){
                                Log.d(TAG, "Some error "+databaseError.getDetails());
                            }
                            else {
                                Log.d(TAG, "User created");

                                progress.dismiss();

                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            }


                        }
                    });


                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(SignUpFlow.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SignUpFlow.fragmentsGroup.get(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    public Person test(){

        Credential credential = new Credential();
        credential.setEmail("dummy9@gmail.com");
        credential.setPassword("Nik@99111");

        Contact contact = new Contact();
        contact.setFirstName("Nikhil");
        contact.setLastName("Patil");
        contact.setPhone("9029202020202");

        Address address = new Address();
        address.setHouse("60");
        address.setStreet("shanid");
        address.setCity("harolds cross");
        address.setCounty("dublin");
        address.setCountry("ireland");


        Person person = new Person(credential, contact, address);

        //person.setId("ddddwww");

        return person;

    }
}
