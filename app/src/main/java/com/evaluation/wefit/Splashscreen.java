package com.evaluation.wefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

// Criado por Caian Marcinkowski Ferreira - 28/09/2022
// GitHub: https://github.com/CaianMarcinkowski

// Tela de Splashscreen onde é mostrado o logo da WeFit e após um delay de 2000 Milissegundos é carregada a tela de Home

public class Splashscreen extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override public void run() {
                mostrarHome();
            }
        }, 2000);
    }

    private void mostrarHome() {
        Intent intent = new Intent(Splashscreen.this, Home.class);
        startActivity(intent);
        finish();
    }
}

