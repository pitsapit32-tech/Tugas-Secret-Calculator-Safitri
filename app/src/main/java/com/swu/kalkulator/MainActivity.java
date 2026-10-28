
package com.swu.kalkulator;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etAngka1, etAngka2;
    Button btnTambah, btnKurang, btnKali, btnBagi, btnHitung;
    TextView tvHasil;

    String operasi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAngka1 = findViewById(R.id.etAngka1);
        etAngka2 = findViewById(R.id.etAngka2);

        btnTambah = findViewById(R.id.btnTambah);
        btnKurang = findViewById(R.id.btnKurang);
        btnKali = findViewById(R.id.btnKali);
        btnBagi = findViewById(R.id.btnBagi);
        btnHitung = findViewById(R.id.btnHitung);

        tvHasil = findViewById(R.id.tvHasil);

        btnTambah.setOnClickListener(v -> operasi = "+");
        btnKurang.setOnClickListener(v -> operasi = "-");
        btnKali.setOnClickListener(v -> operasi = "*");
        btnBagi.setOnClickListener(v -> operasi = "/");

        btnHitung.setOnClickListener(v -> hitung());
    }

    private void hitung() {
        String angka1 = etAngka1.getText().toString().trim();
        String angka2 = etAngka2.getText().toString().trim();

        if (angka1.isEmpty() || angka2.isEmpty()) {
            Toast.makeText(
                    this,
                    "Masukkan kedua angka terlebih dahulu",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        try {
            double a = Double.parseDouble(angka1);
            double b = Double.parseDouble(angka2);
            double hasil;

            switch (operasi) {
                case "+":
                    hasil = a + b;
                    break;

                case "-":
                    hasil = a - b;
                    break;

                case "*":
                    hasil = a * b;
                    break;

                case "/":
                    if (b == 0) {
                        tvHasil.setText("Tidak bisa dibagi nol");
                        return;
                    }
                    hasil = a / b;
                    break;

                default:
                    tvHasil.setText("Pilih operasi terlebih dahulu");
                    return;
            }


            tvHasil.setText("Hasil: " + hasil);
            // Secret Calculator sesuai 5 digit NIM: 03981
            if (hasil == 1) {
                bukaKamera();
            } else if (hasil == 3) {
                bukaGambar();
            }


        } catch (NumberFormatException e) {
            tvHasil.setText("Angka yang dimasukkan tidak valid");
        }
    }


    private void bukaKamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Aplikasi kamera tidak tersedia",
                    Toast.LENGTH_SHORT
            ).show();
        }

    }
    private void bukaSensor() {
        android.hardware.SensorManager sensorManager =
                (android.hardware.SensorManager)
                        getSystemService(SENSOR_SERVICE);

        android.hardware.Sensor accelerometer =
                sensorManager.getDefaultSensor(
                        android.hardware.Sensor.TYPE_ACCELEROMETER);

        android.hardware.Sensor gyroscope =
                sensorManager.getDefaultSensor(
                        android.hardware.Sensor.TYPE_GYROSCOPE);

        String pesan = "Sensor: ";

        if (accelerometer != null && gyroscope != null) {
            pesan += "Accelerometer dan Gyroscope tersedia";
        } else if (accelerometer != null) {
            pesan += "Accelerometer tersedia, Gyroscope tidak tersedia";
        } else if (gyroscope != null) {
            pesan += "Gyroscope tersedia, Accelerometer tidak tersedia";
        } else {
            pesan += "Sensor tidak tersedia";
        }

        Toast.makeText(this, pesan, Toast.LENGTH_LONG).show();
    }

    private void putarAudio() {
        android.media.ToneGenerator suara =
                new android.media.ToneGenerator(
                        android.media.AudioManager.STREAM_MUSIC, 100);

        suara.startTone(android.media.ToneGenerator.TONE_CDMA_PIP, 1000);

        new android.os.Handler(android.os.Looper.getMainLooper())
                .postDelayed(() -> suara.release(), 1200);

    }

    private void bukaGambar() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("image/*");
        startActivity(intent);
    }


    private void bukaGPS() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:0,0?q=lokasi saya"));

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Aplikasi peta tidak tersedia",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
