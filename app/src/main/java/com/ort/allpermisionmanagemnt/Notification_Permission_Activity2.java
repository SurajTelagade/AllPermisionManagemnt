package com.ort.allpermisionmanagemnt;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Notification_Permission_Activity2 extends AppCompatActivity {
    Button Notification_Btn;
    String[] permissions = new String[]{
            Manifest.permission.POST_NOTIFICATIONS
    };

    boolean permission_post_notification = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_permission2);
        Notification_Btn = findViewById(R.id.Notification_Btn);
        Notification_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("NotificationPermision",permission_post_notification+":permission_post_notification");


                if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.S) {
                    requestPermissionNotification();
                } else {
                    Intent intent=new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(intent);
                }



            }
        });


    }

    private void requestPermissionNotification() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }else {
            Intent intent=new Intent(getApplicationContext(), MainActivity2.class);
            startActivity(intent);
        }

    }








    ActivityResultLauncher<String> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                 Intent intent=new Intent(getApplicationContext(), MainActivity2.class);
                 startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"Allow Notification Permission from Setting",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);

                }
            });





}