package de.fhswf.ma.thema7;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private boolean running = false;

    private SensorManager sensorManager;
    private Sensor sensor;

    private SensorEventListener gyroscopeSensorListener;

    private TextView xValue;
    private TextView yValue;
    private TextView zValue;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        //sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        //sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        xValue = (TextView) findViewById(R.id.xValue);
        yValue = (TextView) findViewById(R.id.yValue);
        zValue = (TextView) findViewById(R.id.zValue);

        gyroscopeSensorListener = new SensorEventListener()
        {
            @Override
            public void onSensorChanged(SensorEvent event)
            {
                //
                xValue.setText("" + event.values[0]);
                yValue.setText("" + event.values[1]);
                zValue.setText("" + event.values[2]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i)
            {
            }
        };

        // Register the listener
        sensorManager.registerListener(gyroscopeSensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        running = true;
    }
}