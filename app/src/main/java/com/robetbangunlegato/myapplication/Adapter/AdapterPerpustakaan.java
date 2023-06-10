package com.robetbangunlegato.myapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.robetbangunlegato.myapplication.API.APIRequestData;
import com.robetbangunlegato.myapplication.API.RetroServer;
import com.robetbangunlegato.myapplication.Activity.DetailActivity;
import com.robetbangunlegato.myapplication.Activity.MainActivity;
import com.robetbangunlegato.myapplication.Activity.UbahActivity;
import com.robetbangunlegato.myapplication.Model.ModelPerpustakaan;
import com.robetbangunlegato.myapplication.Model.ModelResponse;
import com.robetbangunlegato.myapplication.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPerpustakaan extends RecyclerView.Adapter<AdapterPerpustakaan.VHPerpustakaan> {
    private Context ctx;

    private List<ModelPerpustakaan> listPerpustakaan;

    public AdapterPerpustakaan(Context ctx, List<ModelPerpustakaan> listPerpustakaan){
        this.ctx = ctx;
        this.listPerpustakaan = listPerpustakaan;
    }

    @NonNull
    @Override
    public VHPerpustakaan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_item_perpustakaan, parent,false);
        return new VHPerpustakaan(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPerpustakaan.VHPerpustakaan holder, int position) {
        ModelPerpustakaan MP = listPerpustakaan.get(position);
        holder.tvId.setText(MP.getId());
        holder.tvNama.setText(MP.getNama());

        holder.tvKoordinat.setText(MP.getKoordinat());
        holder.tvTentang.setText(MP.getTentang());
        holder.tvAlamat.setText(MP.getAlamat());
        holder.tvFoto.setText(MP.getFoto());
        Glide
                .with(ctx)
                .load(MP.getFoto())
                .into(holder.ivFoto);

//        detail activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xNama, xKoordinat, xFoto, xAlamat, xTentang;
                xNama = MP.getNama();
                xKoordinat = MP.getKoordinat();
                xFoto = MP.getFoto();
                xAlamat = MP.getAlamat();
                xTentang = MP.getTentang();

//                kirim data ke DetailActivity
                Intent kirim = new Intent(ctx, DetailActivity.class);
                kirim.putExtra("xNama",xNama);
                kirim.putExtra("xKoordinat",xKoordinat);
                kirim.putExtra("xFoto",xFoto);
                kirim.putExtra("xAlamat",xAlamat);
                kirim.putExtra("xTentang",xTentang);
                ctx.startActivity(kirim);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPerpustakaan.size();
    }

    public class VHPerpustakaan extends RecyclerView.ViewHolder{
        TextView tvId, tvNama, tvKoordinat,tvFoto,tvAlamat,tvTentang;
        ImageView ivFoto;

        public VHPerpustakaan(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvTentang = itemView.findViewById(R.id.tv_tentang);
            ivFoto = itemView.findViewById(R.id.iv_foto);
            tvKoordinat = itemView.findViewById(R.id.tv_koordinat);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvFoto = itemView.findViewById(R.id.tv_foto);

//            kondisi jika hold klik
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Anda memilihi perpustakaan "+ tvNama.getText().toString() + " Apa yang ingin anda lakukan ?");
                    pesan.setCancelable(true);

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deletePerpustakaan(tvId.getText().toString());
                        }
                    });

//                    action button ubah
                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent kirim = new Intent(ctx, UbahActivity.class);
                            kirim.putExtra("xId", tvId.getText().toString());
                            kirim.putExtra("xNama", tvNama.getText().toString());
                            kirim.putExtra("xKoordinat", tvKoordinat.getText().toString());
                            kirim.putExtra("xFoto", tvFoto.getText().toString());
                            kirim.putExtra("xAlamat", tvAlamat.getText().toString());
                            kirim.putExtra("xTentang", tvTentang.getText().toString());
                            ctx.startActivity(kirim);
                        }
                    });
                    pesan.show();
                    return false;
                }
            });

        }
        void deletePerpustakaan(String id){
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardDelete(id);

            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : "+ kode + " Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retrivePerpustakaan();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
