package com.evaluation.wefit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.evaluation.wefit.controller.AdapterHome;
import com.evaluation.wefit.db.AppDataBase;
import com.evaluation.wefit.models.GitRepos;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {

    private static final String TAG_FAILURE = "FAILURE";
    private static final String TAG_RESPONSE = "RESPONSE";
    TextView full_name, description, stargazers_count, language;

    private Context context;

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
        
        openDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showCustomDialog();
            }
        });

        attHome("appswefit");
    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(Home.this);
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
        attHome(input);
        dialog.dismiss();
    }

    private void attHome(String gitUser) {
        Retrofit retrifit = new Retrofit.Builder()
                .baseUrl(GitReposService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitReposService service = retrifit.create(GitReposService.class);
        Call<List<GitRepos>> requestGitRepos = service.listGitRepos(gitUser);

        requestGitRepos.enqueue(new Callback<List<GitRepos>>() {
            @Override
            public void onResponse(Call<List<GitRepos>> call, Response<List<GitRepos>> response) {

                RecyclerView recyclerView;
                AdapterHome adapter;
                ArrayList<String> list_description = new ArrayList<>();
                ArrayList<String> list_full_name = new ArrayList<>();
                ArrayList<Integer> list_stargazers_count = new ArrayList<>();
                ArrayList<String> list_language = new ArrayList<>();
                ArrayList<String> list_html_url = new ArrayList<>();

                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Erro ao buscar informações do Github, verificar endpoint", Toast.LENGTH_SHORT).show();
                    Log.i(TAG_RESPONSE, "Erro: " + response.code());
                } else {
                    List<GitRepos> gitRepos = response.body();

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
                    recyclerView.setLayoutManager(new LinearLayoutManager(Home.this));
                    adapter = new AdapterHome(Home.this, list_full_name, list_description, list_stargazers_count, list_language, list_html_url);
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<List<GitRepos>> call, Throwable t) {
                Log.e(TAG_FAILURE, "Erro: " + t.getMessage());
            }

        });
    }



}