package com.example.minwoo.seoul18app;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DetectActivity extends AppCompatActivity implements SensorEventListener {
    TextView textView;
    TextView textView2;
    private static SensorManager sensorManager;
    private Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);
        textView=(TextView)findViewById(R.id.magnetText);
        textView2=(TextView)findViewById(R.id.magnetText2);
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(sensor!=null){
            sensorManager.registerListener(this, sensor,SensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(this,"센서가 없거나 이상이 있습니다", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        float azimuth =Math.round(sensorEvent.values[0]);
        float pitch=Math.round(sensorEvent.values[1]);
        float roll=Math.round(sensorEvent.values[2]);
        double tesla =Math.sqrt((azimuth*azimuth)+(pitch*pitch)+(roll*roll));
        String text=String.format("%.0f",tesla);
        textView.setText(text+"µT");
        if(tesla<45){
            textView2.setText("안전: 몰카가 감지되지 않습니다");
        }else if(tesla>=45&&tesla<80) {
            textView2.setText("주의: 몰카가 있을 확률이 희박합니다");
        }else if(tesla>=80&&tesla<140){
            textView2.setText("경고: 몰카가 있을 확률이 높습니다. 반드시 주변을 확인해주세요");
        }else if(tesla>=140){
            textView2.setText("위험: 높은 자기장이 감지되었습니다. 몰카 유무를 반드시 확인하십시오");
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor,int i){

    }
}
