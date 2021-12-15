package com.example.abdurrahmangazi.hangitakimhangiligde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    static final int YENIDEN_BASLAT = 1;
    private TextView soru;
    private TextView ilerlemeYazisi;
    private Button cevap1;
    private Button cevap2;
    private Button cevap3;
    private Button cevap4;
    private ArrayList<Soru> sorular;
    private int dogruCevapSayisi;
    private int soruNo;
    private int dogruCevap;
    private TextView puan;
    private ProgressBar progressBar;

    private void soruDosyasiOku(){
        InputStream stream = getResources().openRawResource(R.raw.ligsorulari);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-16")));
        Soru yeniSoru;
        String soru = null;
        sorular = new ArrayList<Soru>();
        try {
            while ((soru = reader.readLine()) != null) {
                String[] soruIcerik = soru.split(";");
                if ((soruIcerik[0].equalsIgnoreCase("A") || soruIcerik[0].equalsIgnoreCase("B") || soruIcerik[0].equalsIgnoreCase("C") || soruIcerik[0].equalsIgnoreCase("D"))){
                    yeniSoru = new Soru(soruIcerik[1], soruIcerik[2], soruIcerik[3], soruIcerik[4], soruIcerik[5], soruIcerik[0]);
                    sorular.add(yeniSoru);
                }
            }
        } catch (IOException e) {
        }
    }

    private Soru simdikiSoruyuBul(){
        int i;
        i = 0;
        for (Soru soru:sorular){
            if (i == soruNo){
                return soru;
            } else {
                i++;
            }
        }
        return null;
    }

    private void simdikiSoruyuGoster(){
        Soru simdikiSoru;
        simdikiSoru = simdikiSoruyuBul();
        if (simdikiSoru != null){
            soru.setText((soruNo + 1) + ") " + simdikiSoru.getSoruCumle() + " futbol klubü hangi ligde oynuyor ?");
            cevap1.setText("A) " + simdikiSoru.getCevap1());
            cevap2.setText("B) " + simdikiSoru.getCevap2());
            cevap3.setText("C) " + simdikiSoru.getCevap3());
            cevap4.setText("D) " + simdikiSoru.getCevap4());
            if (simdikiSoru.getDogruCevap().equalsIgnoreCase("A")){
                dogruCevap = 1;
            } else {
                if (simdikiSoru.getDogruCevap().equalsIgnoreCase("B")){
                    dogruCevap = 2;
                } else {
                    if (simdikiSoru.getDogruCevap().equalsIgnoreCase("C")){
                        dogruCevap = 3;
                    } else {
                        dogruCevap = 4;
                    }
                }
            }
        }
    }

    private void sinaviBaslat(){
        soruNo = 0;
        dogruCevapSayisi = 0;
        progressBar.setProgress(0);
        puan.setVisibility(View.GONE);
        ilerlemeYazisi.setText("İlerleme Durumu "+"0/10");
        Collections.shuffle(sorular);
        simdikiSoruyuGoster();
        cevap1.setEnabled(true);
        cevap2.setEnabled(true);
        cevap3.setEnabled(true);
        cevap4.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soru = (TextView) findViewById(R.id.soru);
        soru.setEnabled(false);
        cevap1 = (Button) findViewById(R.id.cevap1);
        cevap2 = (Button) findViewById(R.id.cevap2);
        cevap3 = (Button) findViewById(R.id.cevap3);
        cevap4 = (Button) findViewById(R.id.cevap4);
        Button sinaviBaslat = (Button) findViewById(R.id.sinaviBaslat);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ilerlemeYazisi = (TextView) findViewById(R.id.ilerlemeYazisi) ;
        puan = (TextView) findViewById(R.id.puan);
        cevap1.setOnClickListener(cevapTikla);
        cevap2.setOnClickListener(cevapTikla);
        cevap3.setOnClickListener(cevapTikla);
        cevap4.setOnClickListener(cevapTikla);
        sinaviBaslat.setOnClickListener(sinaviBaslatTikla);
        soruDosyasiOku();
        sinaviBaslat();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == YENIDEN_BASLAT) {
            if (resultCode == RESULT_OK) {
                sinaviBaslat();
            }
        }
    }
    public OnClickListener cevapTikla = new OnClickListener() {
        public void onClick(View v){
            if (((String)v.getTag()).equalsIgnoreCase(Integer.toString(dogruCevap))){
                dogruCevapSayisi++;
            }
            soruNo++;
            progressBar.setProgress(soruNo);
            ilerlemeYazisi.setText("İlerleme Durumu "+soruNo+"/10");
            if (soruNo < 10){
                simdikiSoruyuGoster();
            } else {
                puan.setVisibility(View.VISIBLE);
                puan.setText("Puan:"+dogruCevapSayisi*10);
                String mesaj = "100 üzerinden "+dogruCevapSayisi*10 + " puan aldınız";
                Toast.makeText(getApplicationContext(), mesaj, Toast.LENGTH_LONG).show();
                cevap1.setEnabled(false);
                cevap2.setEnabled(false);
                cevap3.setEnabled(false);
                cevap4.setEnabled(false);
            }
        }
    };


    public OnClickListener sinaviBaslatTikla = new OnClickListener() {
        public void onClick(View v){
            sinaviBaslat();
        }
    };

}

