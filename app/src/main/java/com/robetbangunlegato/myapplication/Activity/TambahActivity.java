package com.robetbangunlegato.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

public class TambahActivity extends AppCompatActivity {

    private EditText etNama, etAlamat, etFoto, etKoordinat, etTentang;
    private Button btSimpan;
    private String nama, alamat, foto, koordinat,tentang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        etNama =findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_alamat);
        etFoto = findViewById(R.id.et_foto);
        etKoordinat = findViewById(R.id.et_koordinat);
        etTentang = findViewById(R.id.et_tentang);
        btSimpan = findViewById(R.id.bt_simpan);

        btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                alamat = etAlamat.getText().toString();
                foto = etFoto.getText().toString();
                koordinat = etKoordinat.getText().toString();
                tentang = etTentang.getText().toString();

                if (nama.trim().isEmpty()){
                    etNama.setError("Nama harus di isi");
                }else if (alamat.trim().isEmpty()){
                    etAlamat.setError("Alamat harus di isi");
                } else if (foto.trim().isEmpty()) {
                    etFoto.setError("Foto harus di isi");
                } else if (koordinat.trim().isEmpty()) {
                    etKoordinat.setError("Koordinat harus di isi");
                } else if (tentang.trim().isEmpty()) {
                    etTentang.setError("Tentang harus di isi");
                } else {
                    tambahPerpustakaan();
                }

            }
        });
    }

    private void tambahPerpustakaan(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardCreate(nama, koordinat, foto, alamat, tentang);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode, pesan;
                kode = response.body().getKode();
                pesan = response.body().getPesan();
                Toast.makeText(TambahActivity.this, "kode : " + kode + "Pesan" + pesan , Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal mengirim data!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}