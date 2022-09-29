package com.evaluation.wefit.models;

// Criado por Caian Marcinkowski Ferreira - 28/09/2022
// GitHub: https://github.com/CaianMarcinkowski

//Classe com as informações dos repositorios que buscaremos do end point e usaremos para armazenar no SQLite

public class GitRepos {
    public String full_name;
    public String description;
    public int stargazers_count;
    public String language;
    public String html_url;
}
