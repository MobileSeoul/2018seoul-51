package com.example.minwoo.seoul18app;

import android.Manifest;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import noman.googleplaces.PlacesListener;

public class SafeActivity extends AppCompatActivity {
    private GoogleApiClient mGoogleApiClient = null;
    private GoogleMap mGoogleMap = null;
    private Marker currentMarker = null;
    private SensorManager mySensor;
    private Sensor sensor;
    boolean fallDetected = false;
    boolean fallChecked = false;
    static int sensorValue = 70;
    float accelX[] = new float[sensorValue];
    float accelY[] = new float[sensorValue];
    float accelZ[] = new float[sensorValue];
    double latitude;
    double longitude;
    private static IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
    private static BroadcastReceiver mBroadcastReceiver= null;
    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초
    private AppCompatActivity mActivity;
    private GPSHelper gpsHelper;
    boolean askPermissionOnceAgain = false;
    boolean mRequestingLocationUpdates = false;
    Location mCurrentLocatiion;
    boolean mMoveMapByUser = true;
    boolean mMoveMapByAPI = true;
    LatLng currentPosition;
   boolean isPlay= true;
   int index=0;
   int k=0;
    final Context context=this;
    String sms, myUrl;
    Button sosButton, warnButton,callButton,cvButton,policeButton;
    ArrayList<String> phones;
    LocationRequest locationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS)
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

    List<Marker> previous_marker = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe);
        previous_marker = new ArrayList<Marker>();
        requestSmsPermission();
        sosButton=(Button)findViewById(R.id.sosB);
        callButton=(Button)findViewById(R.id.callB);
        cvButton=(Button)findViewById(R.id.cvB);  Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final MediaPlayer player= MediaPlayer.create(SafeActivity.this, notification);
        IntentFilter filter=new IntentFilter(Intent.ACTION_HEADSET_PLUG);
       // sosButton.setOnClickListener(new Clicker1());
        TinyDB tinyDB=new TinyDB(context);
        phones=tinyDB.getListString("phoneLists");
        cvButton.setOnClickListener(new SafeActivity.Clicker1());
        callButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(isPlay==true){
                    player.setLooping(true);
                    player.start();
                    isPlay=false;
                }else{
                    player.pause();
                    isPlay=true;
                }
            }
    });

        sosButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (!isPermission) {
                    callPermission();
                    return;
                }
                gpsHelper = new GPSHelper(SafeActivity.this);
                // GPS 사용유무 가져오기
                if (gpsHelper.isGetLocation()) {

                    latitude = gpsHelper.getLatitude();
                    longitude = gpsHelper.getLongitude();
                     myUrl = "http://maps.google.com/?q="+String.valueOf(latitude)+","+String.valueOf(longitude);
                   // txtLat.setText(String.valueOf(latitude));
                  //  txtLon.setText(String.valueOf(longitude));
                    try {
                        //전송
                        SmsManager smsManager = SmsManager.getDefault();
                        PendingIntent sentPI;
                        String SENT = "SMS_SENT";
                        StringBuffer smsBody = new StringBuffer();
                        smsBody.append("위험에 처했습니다 현위치:");
                        smsBody.append(Uri.parse(myUrl));
        for(int i=0; i<phones.size(); i++) {
            sentPI = PendingIntent.getBroadcast(SafeActivity.this, 0, new Intent(SENT), 0);
            //현재 약 50 chars 추정
            smsManager.sendTextMessage(phones.get(i), null, smsBody.toString(), sentPI, null);
        }
                      //  smsManager.sendTextMessage("01055521559", null, sms, sentPI, null);
                      //  smsManager.sendTextMessage("01055521559", null,"OOO씨가 위험에 처해있다고 합니다. 도움을 청하십시오. 현재 위치를 확인하려면 링크를 확인하십시오. http://maps.google.com/?q=", null, null);
                        Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else {
                    // GPS 를 사용할수 없으므로
                    gpsHelper.showSettingsAlert();
                }
            }
        });

        callPermission();  // 권한 요청을 해야 함


    }


    private void requestSmsPermission() {
        String permission = Manifest.permission.SEND_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }

    private class Clicker1 implements View.OnClickListener {
        public void onClick(View v) {
            Bundle myData;
            switch (v.getId()) {
                case R.id.cvB:
                    Intent mapIntent = new Intent(context, MapActivity.class);
                    startActivityForResult(mapIntent, 102);
                    break;
            }
        }
    }


}
