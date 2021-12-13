package com.siamsoft.implicit_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et;
    Button btn_call;
    Button btn_cam;
    Button btn_cont;
    Button btn_brows;
    Button btn_gal;
    Button btn_dial;
    Button btn_ass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText) findViewById(R.id.etx);
        btn_call = (Button) findViewById(R.id.b_call);
        btn_cam = (Button) findViewById(R.id.b_camera);
        btn_cont = (Button) findViewById(R.id.b_contact);
        btn_brows = (Button) findViewById(R.id.b_browser);
        btn_gal = (Button) findViewById(R.id.b_gallery);
        btn_dial = (Button) findViewById(R.id.b_dialpad);
        btn_ass = (Button) findViewById(R.id.b_assignment);


        btn_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i1 = new Intent();

                i1.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivity(i1);

            }
        });


        btn_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent();

                i2.setAction(Intent.ACTION_VIEW);
                i2.setData(Uri.parse("content://contacts/people/"));
                startActivity(i2);

            }
        });

        btn_gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i3 = new Intent();
                i3.setAction(Intent.ACTION_VIEW);
                i3.setData(Uri.parse("content://media/external/images/media/"));
                startActivity(i3);
            }
        });

        btn_brows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i4 = new Intent();
                i4.setAction(Intent.ACTION_VIEW);
                String url = et.getText().toString();
                i4.setData(Uri.parse("http://www." + url));
                startActivity(Intent.createChooser(i4, "t"));
            }
        });

        btn_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i5 = new Intent();
                i5.setAction(Intent.ACTION_DIAL);
                i5.setData(Uri.parse("tel:" + et.getText()));
                startActivity(i5);

            }
        });


        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isPermissionGranted()) {
                    call_action();
                }
            }
        });


    }

    private void call_action() {
        String phnnum = et.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phnnum));
        startActivity(callIntent);
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}