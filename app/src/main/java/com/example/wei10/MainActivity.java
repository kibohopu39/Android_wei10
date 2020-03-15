package com.example.wei10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private File sdroot,approot;//檔案路徑
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //////////////////////////////////////////////
        // 權限詢問撰寫
        // Here, thisActivity is the current activity
        // 沒有權限--->問使用者
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?----這部份多可不必寫
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        323);
                //--->MY_PERMISSIONS_REQUEST_READ_CONTACTS就是最後一個參數，常數，尚未定義，暫時先隨意填

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            Log.v("wei","debug1");
            init();
        }

    }
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 323: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v("wei","debug2");
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.v("wei","debug3");
                    finish();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    public void init(){
        String Estate=Environment.getExternalStorageState();
        Log.v("wei",Estate);//mounted or removed
        sdroot= Environment.getExternalStorageDirectory();
        Log.v("wei",sdroot.getAbsolutePath());//取得絕對路徑
        approot=new File(sdroot,"Android/data/"+getPackageName());

        Log.v("wei",approot.getAbsolutePath());
        if (!approot.exists()){
            if(approot.mkdirs()){
                Log.v("wei","OK");}
            else{
                Log.v("wei","xxx");
            }
        }else {approot.exists();
            Log.v("wei","fail");
        }
    }

    public void test1(View view){
        String content="hello world!";
        File mydata=new File(sdroot.getAbsolutePath(),"/001.txt");
        try {
            FileOutputStream fout=new FileOutputStream(mydata);
            fout.write(content.getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Log.v("wei",e.toString());
        }
    }

    public void test2(View view){
        String data="hello world!\n";
        File mydata=new File(approot.getAbsolutePath()+"/002.txt");
        try {
            FileOutputStream fout=new FileOutputStream(mydata);
            fout.write(data.getBytes());
            fout.flush();
            fout.close();
        }catch(Exception e){
            Log.v("wei",e.toString());
        }
    }

    public void test3(View view){

    }
    public void test4(View view){

    }
    public void test5(View view){
        File xxxdir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        
    }
}
