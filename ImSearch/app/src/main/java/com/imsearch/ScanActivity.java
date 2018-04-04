package com.imsearch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.imsearch.models.CreateList;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class ScanActivity extends AppCompatActivity {

    Button newScan;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference database;
    private String personId;

    private final String image_titles[] = {
            "Img1",
            "Img2",
            "Img3",
            "Img4",
            "Img5",
            "Img6",
            "Img7",
            "Img8"
    };

    private final Integer image_ids[] = {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8
    };

    ArrayList<CreateList> scanImages;
    MyAdapter adapter;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);


        newScan = (Button)findViewById(R.id.new_scan);
        newScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });


        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);


        scanImages = new ArrayList<>();

        adapter = new MyAdapter(getApplicationContext(), scanImages);
        recyclerView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance("gs://medigocare-ab6aa.appspot.com/");

        Intent intent = getIntent();

        if(intent != null && intent.hasExtra("id")){
            personId = intent.getStringExtra("id");
            storageReference = storage.getReference().child(personId);
            prepareData(personId);
        }
    }
    private void prepareData(String personId){

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        database.child("users").child(personId).child("scans").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while(iterator.hasNext()){
                    String imageName = iterator.next().getKey();

                    downloadImage(imageName);

                    Log.d(TAG, imageName);
                }

                progress.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String TAG = "Debugging";

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bitmapData = baos.toByteArray();

            final String imageName = String.valueOf(System.currentTimeMillis());

            UploadTask uploadTask = storage.getReference().child(personId).child(imageName).putBytes(bitmapData);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.d(TAG, "Image upload failed"+exception.getLocalizedMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                    downloadImage(imageName);

                    database.child("users").child(personId).child("scans").child(imageName).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Log.d(TAG, "Image reference stored succesfully");
                            }
                        }
                    });
                }
            });
        }
    }

    private void downloadImage(String imageName){
        storageReference.child(imageName).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] image) {
                // Use the bytes to display the image

                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length, options);

                CreateList createList = new CreateList();
                createList.setImage_title("test");
                createList.setImage_ID(122);
                createList.setBitmap(bitmap);

                scanImages.add(createList);

                //refresh the adapter
                adapter.notifyDataSetChanged();

                if(progress.isShowing()){
                    progress.dismiss();
                }

                Log.d(TAG, "image found");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.d(TAG, "image "+exception.getLocalizedMessage());
            }
        });
    }
}
