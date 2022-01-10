package com.nutan.enggadminapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EnterData extends AppCompatActivity {

    String colgNameStr,colgAddStr,feesStr,cetScoreStr,placementPercentageStr,colgDescriptionStr,localityStr;
    EditText colgNameEt,colgAddEt,feesEt,cetScoreEt,placementPercentageEt,addBranch,colgDescription,localityEt;
    Button imgBtn,uploadDataBtn,AddBranchBtn;
    String branch, tempbranch="";
    TextView branches;
    RecyclerView clgImageRecyclerView;
    ArrayList<Uri> clgImagesList;
    ArrayList<String> clgImagesUrlList;
    private clgImagesAdapter clgImagesAdapterer;

    private static final int WRITE_PERMISSION = 99;
    private static final String TAG = "clgs";

    private FirebaseFirestore db;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);

        colgDescription = findViewById(R.id.ColgDescriptionEt);
        addBranch = findViewById(R.id.addBranchesEt);
        colgNameEt =findViewById(R.id.colg_name_et);
        colgAddEt =findViewById(R.id.address_Et);
        feesEt =findViewById(R.id.feesEt);
        cetScoreEt =findViewById(R.id.cetScoreEt);
        placementPercentageEt =findViewById(R.id.placementPercentageEt);
        branches = findViewById(R.id.branchesId);
        localityEt = findViewById(R.id.localityEt);
        clgImageRecyclerView = findViewById(R.id.img_preview_recycleView);
        imgBtn = findViewById(R.id.AddcolgImgId);
        uploadDataBtn = findViewById(R.id.upload_Data_Btn);
        AddBranchBtn = findViewById(R.id.AddBranchBtn);


        branch=addBranch.getText().toString();


        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        LayoutInflater inflater = LayoutInflater.from(EnterData.this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        requestWritePermission();
        setupRecyclerView();



        List<String> branchList = new ArrayList<>();
        clgImagesList = new ArrayList<>();
        clgImagesUrlList = new ArrayList<>();

        AddBranchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                branch=addBranch.getText().toString();

                tempbranch += "\u25CF" + " " + branch +"\n";
                branches.setText(tempbranch);

                branchList.add(branch);
                Toast.makeText(EnterData.this,branch + " Branch Added", Toast.LENGTH_SHORT).show();
                   addBranch.setText(null);
            }
        });



        //add img button
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(600,400)
                        .start(EnterData.this);
            }
        });

       Map<String,Object> map = new HashMap<>();
        uploadDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate())
                {






                map.put("collegeName",colgNameStr);
                map.put("Address",colgAddStr);
                map.put("Fees",feesStr);
                map.put("Branches",branchList);
                map.put("PlacementPercentage",placementPercentageStr);
                map.put("CollegeDescription",colgDescriptionStr);
                map.put("CetScore",cetScoreStr);
                map.put("locality",localityStr);
                map.put("clgImageNo",String.valueOf(clgImagesList.size()));

                db.collection("Colleges").document(colgNameStr).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            int i = 0;
                            if (clgImagesList.size() > 0){
                                while (i < clgImagesList.size()){
                                    int imageNo = i +1;
                                    uploadImages(clgImagesList.get(i),imageNo,colgNameStr);
                                    i++;
                                }
                            }
                            Toast.makeText(EnterData.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(EnterData.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                }
            }
        });


    }

    private boolean validate() {

        colgNameStr = colgNameEt.getText().toString();
        colgAddStr = colgAddEt.getText().toString();
        feesStr = feesEt.getText().toString();
        cetScoreStr = cetScoreEt.getText().toString();
        placementPercentageStr = placementPercentageEt.getText().toString();
        colgDescriptionStr = colgDescription.getText().toString();
        localityStr = localityEt.getText().toString();
        if(colgNameStr == null )
        {
            Toast.makeText(EnterData.this, "Please enter College name", Toast.LENGTH_SHORT).show();
            return false;
        }else  if(colgAddStr == null )
        {
            Toast.makeText(EnterData.this, "Please enter College address", Toast.LENGTH_SHORT).show();
            return false;
        }else if(feesStr == null )
        {
            Toast.makeText(EnterData.this, "Please enter College fees", Toast.LENGTH_SHORT).show();
            return false;
        }else if(cetScoreStr == null )
        {
            Toast.makeText(EnterData.this, "Please enter cet score", Toast.LENGTH_SHORT).show();
            return false;
        }else if(placementPercentageStr == null )
        {
            Toast.makeText(EnterData.this, "Please enter placement %", Toast.LENGTH_SHORT).show();
            return false;
        }else if(colgDescriptionStr == null )
        {
            Toast.makeText(EnterData.this, "Please enter College description", Toast.LENGTH_SHORT).show();
            return false;
        }else  if(localityStr == null )
        {
            Toast.makeText(EnterData.this, "Please enter locality", Toast.LENGTH_SHORT).show();
            return false;
        }
           else if(clgImagesList.isEmpty())
        {
            Toast.makeText(EnterData.this, "Please add College Images", Toast.LENGTH_SHORT).show();
            return false;
        }


               return true;





    }

    //Permissions
    private void requestWritePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_PERMISSION);
            }
        }
    }

    private void setupRecyclerView(){


        clgImagesAdapterer = new clgImagesAdapter(clgImagesList);
        clgImageRecyclerView.setHasFixedSize(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2, RecyclerView.VERTICAL,false);
        clgImageRecyclerView.setLayoutManager(gridLayoutManager);
        clgImageRecyclerView.setAdapter(clgImagesAdapterer);
    }
    
    //upload images to storage
    private void uploadImages(Uri uri, int imageNo,String id){
        //first upload images to storage and fetch them urls
        String randomId = UUID.randomUUID().toString();

        final StorageReference imageStoragePath = storageReference.child("clgImages").child(randomId + ".jpg");
        UploadTask uploadTask = imageStoragePath.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String imageDownloadUrl;
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri url = uriTask.getResult();
                imageDownloadUrl = url.toString();
                Log.d(TAG, "onSuccess: " + imageDownloadUrl);

                db.collection("Colleges").document(id).update("clgImage"+String.valueOf(imageNo),imageDownloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: updated db :: "+ imageNo);
                        Toast.makeText(EnterData.this, "updated image to db..." +  imageNo, Toast.LENGTH_SHORT).show();
                        if(clgImagesList.size() == imageNo)
                        {
                            Toast.makeText(EnterData.this, "Succcesfully added all data", Toast.LENGTH_SHORT).show();
                            //restart Activity
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK){
                Uri imageUri = result.getUri();
                clgImagesList.add(imageUri);
                clgImagesAdapterer.notifyDataSetChanged();
                Log.d(TAG, "onActivityResult: clgS uri :: " + clgImagesList);
            }else  if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
                Toast.makeText(this, "Error : Pick an image" + error , Toast.LENGTH_SHORT).show();
            }else  if (resultCode == CropImageActivity.RESULT_CANCELED){
                Toast.makeText(this, "Select a image!", Toast.LENGTH_SHORT).show();
            }

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("SELLER", "Write Permission Granted");
            } else {
                Log.d("SELLER", "Write Permission Failed");
                Toast.makeText(this, "You must allow permission write external storage to your mobile device.", Toast.LENGTH_SHORT).show();
//                requestWritePermission();
                finish();
            }
        }
    }
}