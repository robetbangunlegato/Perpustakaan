package com.robetbangunlegato.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robetbangunlegato.myapplication.API.APIRequestData;
import com.robetbangunlegato.myapplication.API.RetroServer;
import com.robetbangunlegato.myapplication.Adapter.AdapterPerpustakaan;
import com.robetbangunlegato.myapplication.Model.ModelPerpustakaan;
import com.robetbangunlegato.myapplication.Model.ModelResponse;
import com.robetbangunlegato.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvPerpustakaan;
    private RecyclerView.Adapter adPerpustakaan;

    private FloatingActionButton fabTambah;
    private RecyclerView.LayoutManager lmPerpustakaan;

    private ProgressBar pbPerpustakaan;
    private List<ModelPerpustakaan> listPerpustakaan = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPerpustakaan = findViewById(R.id.rv_perpustakaan);
        pbPerpustakaan = findViewById(R.id.pb_perpustakaan);
        fabTambah = findViewById(R.id.fab_tambah);

        lmPerpustakaan = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPerpustakaan.setLayoutManager(lmPerpustakaan);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrivePerpustakaan();

    }

    public void retrivePerpustakaan(){
        pbPerpustakaan.setVisibility(View.VISIBLE);

        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardRetrive();
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listPerpustakaan = response.body().getData();
                adPerpustakaan = new AdapterPerpustakaan(MainActivity.this, listPerpustakaan);
                rvPerpustakaan.setAdapter(adPerpustakaan);
                adPerpustakaan.notifyDataSetChanged();
                pbPerpustakaan.setVisibility(View.GONE);

                fabTambah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, TambahActivity.class));
                    }
                });
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbPerpustakaan.setVisibility(View.GONE);


            }
        });
    }
}