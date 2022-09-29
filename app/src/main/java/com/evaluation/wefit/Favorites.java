package com.evaluation.wefit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.evaluation.wefit.controller.AdapterHome;
import com.evaluation.wefit.db.AppDataBase;
import com.evaluation.wefit.db.GitRepos;
import com.evaluation.wefit.db.GitReposDao;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import java.util.ArrayList;
import java.util.List;

// Criado por Caian Marcinkowski Ferreira - 29/09/2022
// GitHub: https://github.com/CaianMarcinkowski

// Classe onde sera mostrado os repositórios do GitHub que foram marcados como favoritos na tela de Home, Repositórios que estão armazenados no SQLite

public class Favorites extends AppCompatActivity {

    private static final String TAG_FAILURE = "FAILURE";
    private static final String TAG_RESPONSE = "RESPONSE";
    private static final String TYPE = "FAVORITES";
    TextView full_name, description, stargazers_count, language;

    BottomNavigationItemView favorite, git;
    ImageView openDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        full_name = (TextView) findViewById(R.id.id_title);
        description = (TextView) findViewById(R.id.id_description);
        stargazers_count = (TextView) findViewById(R.id.tv_stargazers_count);
        language = (TextView) findViewById(R.id.tv_language);

        openDialog = (ImageView) findViewById(R.id.iv_config);

        git = (BottomNavigationItemView) findViewById(R.id.git);
        favorite = (BottomNavigationItemView) findViewById(R.id.star);

        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Favorites.this, Home.class);
                startActivity(i);
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Favorites.this, Favorites.class);
                startActivity(i);
            }
        });

        openDialog.setVisibility(View.GONE);
        openDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showCustomDialog();
            }
        });

        attHome();
    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(Favorites.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();
        Button submitButton = dialog.findViewById(R.id.btn_dialog_salvar);
        final EditText input_text = dialog.findViewById(R.id.et_dialog);

        submitButton.setOnClickListener((v) -> {
            searchRepos(input_text.getText().toString(), dialog);
        });

    }

    private void searchRepos(String input, Dialog dialog) {
        attHome();
        dialog.dismiss();
    }

    private void attHome() {

        RecyclerView recyclerView;
        AdapterHome adapter;
        ArrayList<String> list_description = new ArrayList<>();
        ArrayList<String> list_full_name = new ArrayList<>();
        ArrayList<Integer> list_stargazers_count = new ArrayList<>();
        ArrayList<String> list_language = new ArrayList<>();
        ArrayList<String> list_html_url = new ArrayList<>();

        AppDataBase db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "DB_NAME")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        GitReposDao dao = db.gitReposDao();
        List<GitRepos> gitRepos = dao.getAllGitRepos();

        for (int i = 0; i < gitRepos.size(); i++) {
            if (gitRepos.get(i).description == null) {
                list_description.add("Não ha descrição");
            } else {
                list_description.add(gitRepos.get(i).description);
            }

            if (gitRepos.get(i).language == null) {
                list_language.add("-");
            } else {
                list_language.add(gitRepos.get(i).language);
            }

            list_full_name.add(gitRepos.get(i).full_name);
            list_stargazers_count.add(gitRepos.get(i).stargazers_count);
            list_html_url.add(gitRepos.get(i).html_url);

        }
        recyclerView = findViewById(R.id.rvHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(Favorites.this));

        adapter = new AdapterHome(Favorites.this, list_full_name, list_description, list_stargazers_count, list_language, list_html_url, this.TYPE);
        recyclerView.setAdapter(adapter);

    }

}


