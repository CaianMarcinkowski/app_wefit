package com.evaluation.wefit.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Criado por Caian Marcinkowski Ferreira - 28/09/2022
// GitHub: https://github.com/CaianMarcinkowski

//Entidade que realiza a arquitetura da tabela onde sera armazenada as informações dos Repositorios do Github
@Entity(tableName = "git_repos")
public class GitRepos {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "full_name")
    public String full_name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "stargazers_count")
    public int stargazers_count;

    @ColumnInfo(name = "language")
    public String language;

    @ColumnInfo(name = "html_url")
    public String html_url;

}
