package com.robetbangunlegato.myapplication.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;
    private List<ModelPerpustakaan> data;

    public String getKode(){return kode;}
    public String getPesan(){return pesan;}

    public List<ModelPerpustakaan> getData(){
        return data;
    }
}
