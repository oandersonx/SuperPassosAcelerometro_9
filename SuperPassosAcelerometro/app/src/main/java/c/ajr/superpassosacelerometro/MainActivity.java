package c.ajr.superpassosacelerometro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView xText, yText, zText;
    private Sensor mySensor, mStepDetectorSensorPassos;
    private SensorManager SM,mSenssorManagerPassos;
    private String vconc="";
    private boolean temp = true;
    int test=0;

    //Variaveis para contagem de passos
    private TextView totalPassos;
    private Button btnReset;
    private int passos = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Criando o Sensor Manager
        mSenssorManagerPassos = (SensorManager)getSystemService(SENSOR_SERVICE);
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);


        //Sensor Acelerometro
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        //Registrando Sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

        //Atribuindo TextView
        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);

        //Referente a contagem de passos

        totalPassos = (TextView)findViewById(R.id.tvTotalPassos);
        btnReset = (Button)findViewById(R.id.btReset);

        mStepDetectorSensorPassos = mSenssorManagerPassos.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);


        mSenssorManagerPassos.registerListener(this, mStepDetectorSensorPassos, SensorManager.SENSOR_DELAY_NORMAL);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passos = 0;
                totalPassos.setText("0");

            }
        });

    }

    //Referente a contagem de passos

    @Override
    protected void onResume() {
        super.onResume();
        mSenssorManagerPassos.registerListener(this, mStepDetectorSensorPassos, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSenssorManagerPassos.unregisterListener(this, mStepDetectorSensorPassos);
    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onSensorChanged(SensorEvent event) {

       xText.setText("X: " + event.values[0]);
        yText.setText("Y: " + event.values[1]);
        zText.setText("Z: " + event.values[2]);


        Sensor sensor = event.sensor;
        if(sensor.getType()==Sensor.TYPE_STEP_DETECTOR){
            passos++;
            totalPassos.setText(""+passos);
        }


    }








    public void stop (View v){
        temp = false;
        test=0;
    }
    public void send (View v){

        if(test==0) {


            new Thread(new Runnable() {
                @Override
                public void run() {
                    test = 1;
                    temp=true;
                    while (temp) {
                        vconc = xText.getText().toString() + yText.getText().toString() + zText.getText().toString();

                        DataSender dataSender = new DataSender();
                        dataSender.execute(vconc);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }).start();

        }

    }



}
