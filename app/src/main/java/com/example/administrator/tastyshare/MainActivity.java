package com.example.administrator.tastyshare;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_USECAMERA = 101;
    private static final int REQUEST_USEGPS = 102;
    private GpsService gpsService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLocationPermission();

        Button registerButton = (Button) findViewById(R.id.register_button);

        registerButton.setOnClickListener(this::registerButtonClick);

        Button searchButton = (Button) findViewById(R.id.search_button);

        searchButton.setOnClickListener(this::searchButtonClick);

        gpsService = new GpsService(this);
        boolean gpsCheckSign = gpsService.isGpsCheck();
        if(gpsCheckSign != true){
            createGpsActivationDialog();
        }
    }// onCreate


    private void createGpsActivationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS 활성화 요청")
                .setMessage("GPS를 활성화 하시겠습니까?").setCancelable(false)
                .setPositiveButton("활성화", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gpsActivationRequest();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소를 선택하였습니다.\n위치정보를 이용하실수 없습니다.",Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void gpsActivationRequest() {
        Intent enableGpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(enableGpsIntent, REQUEST_USEGPS);
    }


    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권한이 없을 경우
            //최초 권한 요청인지, 혹은 사용자에 의한 재요청인지 확인
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // 사용자가 임의로 권한을 취소시킨 경우
                // 권한 재요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            }
        }
    }


    private void searchButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void registerButtonClick(View view) {
        createAskedToUseCameraDialog();
    }

    public void createAskedToUseCameraDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("카메라를 실행 하시겠습니까?");
        builder.setPositiveButton("예", (dialog, which) -> {
            Intent actionCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(actionCameraIntent, REQUEST_USECAMERA);
        });

        builder.setNegativeButton("아니요", (dialog, which) -> {

            Intent intent = new Intent(this, RegisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        });

        builder.show();

    }//end


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_USECAMERA) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

            Intent intent = new Intent(this, RegisterActivity.class);
            intent.putExtra("CAPTURE_OK", imageBitmap);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        if (requestCode == REQUEST_USEGPS) {
            boolean gpsCheckSign = gpsService.isGpsCheck();
            if(gpsCheckSign != true){
                Toast.makeText(getApplicationContext(),"취소를 선택하였습니다.\n위치정보를 이용하실수 없습니다.",Toast.LENGTH_LONG).show();
//                createGpsActivationDialog();
            }
            else{
                Toast.makeText(getApplicationContext(), "GPS 활성화 되었습니다. 잠시만 기다려주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

