package com.example.missensores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView textView1, textView2;
    SensorManager sensorManager;
    Vibrator vibrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        List<Sensor> lista = sensorManager.getSensorList(Sensor.TYPE_ALL);

        String cadena = "";
        for (Sensor s : lista) {
            cadena += "Sensor: " + s.getName() + " ==> " + s.getStringType() + " Fabricante: " + s.getVendor() + "\n";

        }

        //textView1.setText(cadena);
        System.out.println(cadena);

        sensorManager.registerListener((SensorEventListener) this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                sensorManager.SENSOR_DELAY_NORMAL);
        vibrador = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);



        sensorManager.registerListener((SensorEventListener) this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                sensorManager.SENSOR_DELAY_NORMAL);



    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            textView2.setText("Valor: " + sensorEvent.values[0]);

            if ( sensorEvent.values[0]==0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrador.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrador.vibrate(500);
                }
            }


        }else if (sensorEvent.sensor.getType()==Sensor.TYPE_LIGHT){
            textView1.setText("Luz: "+ sensorEvent.values[0]);
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}