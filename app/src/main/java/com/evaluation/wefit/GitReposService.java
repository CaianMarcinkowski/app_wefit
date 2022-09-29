package com.evaluation.wefit;

import com.evaluation.wefit.models.GitRepos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// Criado por Caian Marcinkowski Ferreira - 28/09/2022
// GitHub: https://github.com/CaianMarcinkowski

// Interface que realiza o GET no endpoint do GitHub, alterando apenas o usuario do git que é enviado via parametro para função

public interface GitReposService {

    public static final String BASE_URL = "https://api.github.com/users/appswefit/";

    @GET("https://api.github.com/users/{userGit}/repos")
    Call<List<GitRepos>> listGitRepos(@Path("userGit") String userGit);

}
