package com.evaluation.wefit.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.evaluation.wefit.R;
import com.evaluation.wefit.db.AppDataBase;
import com.evaluation.wefit.db.GitRepos;
import com.evaluation.wefit.db.GitReposDao;

import java.util.ArrayList;

// Criado por Caian Marcinkowski Ferreira - 28/09/2022
// GitHub: https://github.com/CaianMarcinkowski

//Adapter do RecyclerView para mostrar as informações de forma dinamica na Home e na Favoreites
public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<String> full_name = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private ArrayList<Integer> stargazers_count = new ArrayList<>();
    private ArrayList<String> language = new ArrayList<>();
    private ArrayList<String> html_url = new ArrayList<>();

    private String type;

    Context context;

    public static final String EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID";
    public static final String EXTRA_FULL_NAME = "com.gtappdevelopers.gfgroomdatabase.EXTRA_FULL_NAME";
    public static final String EXTRA_DESCRIPTION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_DESCRIPTION";
    public static final String EXTRA_STARGAZERS_COUNT = "com.gtappdevelopers.gfgroomdatabase.EXTRA_STARGAZERS_COUNT";
    public static final String EXTRA_LANGUAGE = "com.gtappdevelopers.gfgroomdatabase.EXTRA_LANGUAGE";
    public static final String EXTRA_HTML_URL = "com.gtappdevelopers.gfgroomdatabase.EXTRA_HTML_URL";

    public AdapterHome(Context context,
                       ArrayList<String> full_name,
                       ArrayList<String> description,
                       ArrayList<Integer> stargazers_count,
                       ArrayList<String> language,
                       ArrayList<String> html_url,
                       String type) {
        this.layoutInflater = LayoutInflater.from(context);

        this.full_name = full_name;
        this.description = description;
        this.stargazers_count = stargazers_count;
        this.language = language;
        this.html_url = html_url;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.card_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.full_name.setText(full_name.get(position));
        holder.description.setText(description.get(position));
        holder.stargazers_count.setText(stargazers_count.get(position).toString());
        holder.language.setText(language.get(position));

        if (this.type.equalsIgnoreCase("FAVORITES")) {
            holder.btn_favorite.setVisibility(View.GONE);
        }

        holder.btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GitRepos gitRepos = new GitRepos();

                gitRepos.full_name = full_name.get(position);
                gitRepos.description = description.get(position);
                gitRepos.html_url = html_url.get(position);
                gitRepos.language = language.get(position);
                gitRepos.stargazers_count = stargazers_count.get(position);

                AppDataBase db = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "DB_NAME")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();

                GitReposDao gitReposDao = db.gitReposDao();
                gitReposDao.insertGitRepos(gitRepos);

                AdapterHome.this.full_name.remove(holder.getAdapterPosition());
                AdapterHome.this.notifyItemRemoved(holder.getAdapterPosition());
                AdapterHome.this.notifyItemRangeChanged(position, getItemCount());

                Toast.makeText(context, "Item adicionado com sucesso!", Toast.LENGTH_SHORT).show();

            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(html_url.get(position)));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.full_name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView full_name, description, stargazers_count, language;
        Button btn_favorite;
        ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            full_name = itemView.findViewById(R.id.id_title);
            description = itemView.findViewById(R.id.id_description);
            stargazers_count = itemView.findViewById(R.id.tv_stargazers_count);
            language = itemView.findViewById(R.id.tv_language);
            btn_favorite = itemView.findViewById(R.id.btnFavorite);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClick) {
            this.itemClickListener = itemClick;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(getLayoutPosition());
        }

    }
}
