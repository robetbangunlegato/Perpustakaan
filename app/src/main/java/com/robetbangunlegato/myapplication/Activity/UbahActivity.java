package com.robetbangunlegato.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.robetbangunlegato.myapplication.API.APIRequestData;
import com.robetbangunlegato.myapplication.API.RetroServer;
import com.robetbangunlegato.myapplication.Model.ModelResponse;
import com.robetbangunlegato.myapplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {
    String yId, yNama, yKoordinat, yFoto, yAlamat, yTentang;
    private EditText etNama, etKoordinat, etFoto, etAlamat, etTentang;
    private Button btnUbah;

    String nama, koordinat, foto, alamat, tentang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

//        terima intent dari AdapterPerpustakaan
        Intent terima = getIntent();
        yId = terima.getStringExtra("xId");
        yNama = terima.getStringExtra("xNama");
        yKoordinat = terima.getStringExtra("xKoordinat");
        yFoto = terima.getStringExtra("xFoto");
        yAlamat = terima.getStringExtra("xAlamat");
        yTentang = terima.getStringExtra("xTentang");


        etNama = findViewById(R.id.et_nama);
        etKoordinat = findViewById(R.id.et_koordinat);
        etFoto = findViewById(R.id.et_foto);
        etAlamat = findViewById(R.id.et_alamat);
        etTentang = findViewById(R.id.et_tentang);
        btnUbah = findViewById(R.id.bt_ubah);


        etNama.setText(yNama);
        etKoordinat.setText(yKoordinat);
        etFoto.setText(yFoto);
        etAlamat.setText(yAlamat);
        etTentang.setText(yTentang);
//        action button ubah
        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                koordinat = etKoordinat.getText().toString();
                foto = etFoto.getText().toString();
                alamat = etAlamat.getText().toString();
                tentang = etTentang.getText().toString();

                if (nama.trim().isEmpty()){
                    etNama.setError("Nama harus di isi!");
                } else if (koordinat.trim().isEmpty()) {
                    etKoordinat.setError("Koordinat harus di isi!");
                } else if (foto.trim().isEmpty()) {
                    etFoto.setError("Foto harus di isi!");
                } else if (alamat.trim().isEmpty()) {
                    etAlamat.setError("Alamat harus di isi!");
                } else if (tentang.trim().isEmpty()) {
                    etTentang.setError("Tentang harus di isi!");
                }else {
                    ubahPerpustakaan();
                }

            }
        });

    }

    private void ubahPerpustakaan(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardUpdate(yId, nama, koordinat, foto, alamat, tentang);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode, pesan;
                kode = response.body().getKode();
                pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "kode : " + kode +" Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Error: Gagal mengubah data! ", Toast.LENGTH_SHORT).show();
            }
        });
    }

}