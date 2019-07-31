package com.example.ideal;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Selling extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 71;
    EditText nameitem;
    EditText itemdescr;
    EditText Location;
    Button upload;
    Button post;
    ImageView imagess;
    FirebaseFirestore firebaseFirestore;
    private StorageReference reference;
    private Uri uri;
    Button pick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling);
        firebaseFirestore = FirebaseFirestore.getInstance();
        nameitem = (EditText) findViewById(R.id.itemname);
        itemdescr = (EditText) findViewById(R.id.itemdes);
        Location = (EditText) findViewById(R.id.loc);
        upload = (Button) findViewById(R.id.uploader);
        post = (Button) findViewById(R.id.pst);
        imagess = (ImageView) findViewById(R.id.showing);
        pick = (Button) findViewById(R.id.choose);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        reference = FirebaseStorage.getInstance().getReference("uploads");
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Openfilechooser();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkpermissions()){
                    uploadfile();
                }
            }
        });
    }

public boolean checkpermissions(){
        if (Build.VERSION.SDK_INT >Build.VERSION_CODES.M){
            int uploadperm=Selling.this.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE");
            uploadperm+=Selling.this.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
            if (uploadperm !=0){
                this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE});
            }
        }
    return false;
}

    private void requestPermissions(String[] strings) {
    }

    public void save() {
        String name = nameitem.getText().toString();
        String item = itemdescr.getText().toString();
        String place = Location.getText().toString();

        Itemsell itemsell = new Itemsell(name, item, place);
        firebaseFirestore.collection("Items").add(itemsell).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Selling.this, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Selling.this, "Error!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Openfilechooser() {
Intent intent=new Intent();
intent.setType("images/*");
intent.putExtra("return-data",true);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && requestCode == RESULT_OK && data != null && data.getData() != null) {

            try{
                uri = data.getData();
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imagess.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    public String getfileextension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadfile() {
        if (uri != null) {
            StorageReference reference1=reference.child("upload"+System.currentTimeMillis()+"."+getfileextension(uri));
            reference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Selling.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Selling.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
