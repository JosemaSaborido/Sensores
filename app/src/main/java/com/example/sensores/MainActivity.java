package com.example.sensores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor sensorTemperatura;
    private Sensor sensorHumedad;
    private TextView snTemperatura;
    private TextView snHumedad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        snTemperatura = (TextView) findViewById(R.id.snTemperatura);
        snHumedad = (TextView) findViewById(R.id.snHumedad);

        //Obtenemos el sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Instanciamos los sensores
        sensorTemperatura = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumedad = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        registrarSensorListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registrarSensorListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener((SensorEventListener) this);
    }



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                snTemperatura.setText(String.valueOf(sensorEvent.values[0]));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                snHumedad.setText(String.valueOf(sensorEvent.values[0]));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void registrarSensorListener() {
        //Comprobar si estan disponibles los sensores y asignamos listener
        if(null != sensorTemperatura){
            sensorManager.registerListener((SensorEventListener) this, sensorTemperatura, SensorManager.SENSOR_DELAY_NORMAL);
            snTemperatura.setText("Cargando...");
        }else{
            snTemperatura.setText("N/D");
        }
        if(null != sensorHumedad){
            sensorManager.registerListener((SensorEventListener) this, sensorHumedad, SensorManager.SENSOR_DELAY_NORMAL);
            snHumedad.setText("Cargando...");
        }else{
            snHumedad.setText("N/D");
        }
    }
}