package de.fhswf.ma.thema7.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 *
 * Data from TYPE_GAME_ROTATION_VECTOR
 *
 */
public class OrientationData implements SensorEventListener
{
    private SensorManager sensorManager;
    private Sensor sensor;

    private float[] output = new float[3];

    public OrientationData()
    {
        sensorManager = (SensorManager) Constants.CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
    }

    /**
     *
     * Registers a SensorListener for the TYPE_GAME_ROTATION_VECTOR sensor
     *
     */
    public void register()
    {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     *
     * unregisters the SensorListener for the TYPE_GAME_ROTATION_VECTOR sensor when the application is not in focus
     *
     */
    public void pause()
    {
        sensorManager.unregisterListener(this);
    }

    /**
     *
     * returns sensor output
     *
     * @return  returns x,y,z rotation
     */
    public float[] getOutput()
    {
        return output;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR)
        {
            output = sensorEvent.values;
            //System.out.println("x ist" + output[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

}
