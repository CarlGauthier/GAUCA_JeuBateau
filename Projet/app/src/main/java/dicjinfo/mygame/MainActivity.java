package dicjinfo.mygame;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    boolean baseSet = false;
    private GameView gameView;

    SensorManager sensorManager;
    Sensor gyro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyro = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        Display display = getWindowManager().getDefaultDisplay();
        gameView = new GameView(this, display.getWidth(), display.getHeight());
        int width = display.getWidth();
        int height = display.getHeight();
        setContentView(gameView);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float z = 0;
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
            z = event.values[2];
        if(!baseSet) {
            baseSet = true;
            gameView.setBaseGyro(z);
        }
        gameView.updateGyroDifference(z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this, gyro);
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        baseSet = false;
        sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
        gameView.resume();
    }
}
