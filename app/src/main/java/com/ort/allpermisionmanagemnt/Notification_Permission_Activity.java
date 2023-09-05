package com.ort.allpermisionmanagemnt;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Notification_Permission_Activity extends AppCompatActivity {

    Button Notification_Btn;
    String[] permissions = new String[]{
            Manifest.permission.POST_NOTIFICATIONS
    };

    boolean permission_post_notification = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_permission);
        Notification_Btn = findViewById(R.id.Notification_Btn);
        Notification_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.S) {
                    // The device is running Android 12 or higher
                    // You can put your code for Android 12+ here
                    Toast.makeText(getApplicationContext(), "Notification Permision Granted", Toast.LENGTH_SHORT).show();

                    if (!permission_post_notification) {

                        requestPermissionNotification();
                    } else {
                        Toast.makeText(getApplicationContext(), "Notification Permision Granted", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                } else {
                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    // The device is running an Android version lower than 12
                    // You can put your code for older Android versions here
                }



            }
        });


    }

    private void requestPermissionNotification() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, perform your action here
            permission_post_notification = true;
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }



       /* if (ContextCompat.checkSelfPermission(getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED) {
         permission_post_notification=true;
        }else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)){
                Log.e("Permission","Inside else first time");

            }else {
                Log.e("Permission","Inside else 2  time");

            }
        }
*/
    }



  /*  public ActivityResultLauncher<String> resultLauncher=
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),isGranted->{


                if (isGranted){
                    permission_post_notification=true;
                }else {
                    permission_post_notification=false;
                    showPermissionDialog("Notification Permission");

                }


            });
*/


    ActivityResultLauncher<String> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    permission_post_notification = true;
                    // Permission is granted, you can perform your action here
                } else {
                    permission_post_notification = false;
                    Intent rintent=new Intent();
                    rintent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    rintent.setData(uri);
                    startActivity(rintent);
                   // showPermissionDialog("Notification Permission");
                    // Handle the case where permission is denied
                }
            });





}