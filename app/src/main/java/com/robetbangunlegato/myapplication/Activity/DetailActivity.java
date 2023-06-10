package com.robetbangunlegato.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.robetbangunlegato.myapplication.R;

public class DetailActivity extends AppCompatActivity {
    private Button btLokasi;
    private ImageView ivFoto;
    private TextView tvNama, tvAlamat, tvTentang;
    private String yNama, yKoordinat, yAlamat, yTentang, yFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivFoto = findViewById(R.id.iv_foto);
        tvNama = findViewById(R.id.tv_nama);
        tvAlamat = findViewById(R.id.tv_alamat);
        tvTentang = findViewById(R.id.tv_tentang);
        btLokasi = findViewById(R.id.bt_koordinat);

        Intent terima = getIntent();
        yNama = terima.getStringExtra("xNama");
        yAlamat = terima.getStringExtra("xAlamat");
        yTentang = terima.getStringExtra("xTentang");
        yFoto = terima.getStringExtra("xFoto");
        yKoordinat = terima.getStringExtra("xKoordinat");

        tvNama.setText(yNama);
        tvAlamat.setText(yAlamat);
        tvTentang.setText(yTentang);
        Glide
                .with(DetailActivity.this)
                .load(yFoto)
                .into(ivFoto);

        btLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri lokasi = Uri.parse("geo:0,0?q=" + yKoordinat);
                Intent bukalokasi = new Intent(Intent.ACTION_VIEW, lokasi);
                startActivity(bukalokasi);
            }
        });
    }
}