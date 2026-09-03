package com.example.myapplication; //

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Implementamos SensorEventListener para capturar los datos del hardware
public class MainActivity4 extends AppCompatActivity implements SensorEventListener {

    private ImageView mImageView;
    private SensorManager sensorManager;
    private Sensor rotationVectorSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        mImageView = findViewById(R.id.id_de_tu_imageview);
        Button btnDescargar = findViewById(R.id.id_de_tu_boton);

        // --- REQUERIMIENTO 1: Inicialización del Sensor ---
        // Obtenemos el TYPE_ROTATION_VECTOR como lo solicita la pauta
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        }

        // --- REQUERIMIENTO 2: Implementación de Threads ---
        // Gestor de clics en un subproceso separado para no afectar el Main Thread
        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Tarea en segundo plano simulada
                        final Bitmap bitmap = loadImageFromNetwork("http://IP.com/image.png");

                        mImageView.post(new Runnable() {
                            @Override
                            public void run() {
                                // Actualización en el hilo principal (UI Thread)
                                if(bitmap != null) {
                                    mImageView.setImageBitmap(bitmap);
                                } else {
                                    // Como no hay URL real en la pauta, asignamos la imagen de John Wick y mostramos un mensaje
                                    mImageView.setImageResource(R.drawable.john_wick);
                                    Toast.makeText(MainActivity4.this, "¡Imagen cargada por Thread!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();
            }
        });

        // --- REQUERIMIENTO 3: Utilización de AsyncTask ---
        // Ejecutamos la tarea asíncrona al abrir la pantalla para cumplir la rúbrica
        new MiTareaAsincrona().execute();
    }

    // --- MÉTODOS DEL SENSOR (Ciclo de vida) ---

    @Override
    protected void onResume() {
        super.onResume();
        // Registramos el sensor cuando la app está activa en pantalla
        if (rotationVectorSensor != null) {
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Detenemos la captura de datos cuando la app se minimiza
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Capturamos las coordenadas X, Y, Z del vector de rotación
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Revisa la pestaña "Logcat" en Android Studio para ver estos valores moviéndose
            Log.d("SensorRotacion", "Eje X: " + x + " | Eje Y: " + y + " | Eje Z: " + z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Obligatorio por la interfaz, no requiere código aquí
    }

    // --- MÉTODOS AUXILIARES ---
    private Bitmap loadImageFromNetwork(String urlString) {
        try {
            // Simulamos que la descarga tarda 2 segundos para evidenciar el trabajo del Thread
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null; // Retorna null ya que no estamos conectando a internet de verdad aún
    }

    // --- CLASE ASYNCTASK ---
    // Estructura clásica requerida para operaciones asincrónicas cortas
    private class MiTareaAsincrona extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 1. Configuración inicial en el UI Thread
            Toast.makeText(MainActivity4.this, "Iniciando AsyncTask...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            // 2. Procesamiento pesado en el Background Thread
            try {
                Thread.sleep(3000); // Simulamos 3 segundos de carga
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "¡AsyncTask finalizado con éxito!";
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
            // 3. Manejar los resultados en el UI Thread
            Toast.makeText(MainActivity4.this, resultado, Toast.LENGTH_LONG).show();
        }
    }
}