package com.evaluation.wefit;

import com.evaluation.wefit.models.GitRepos;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitReposService {

    public static final String BASE_URL = "https://api.github.com/users/appswefit/";

    @GET("https://api.github.com/users/{userGit}/repos")
    Call<List<GitRepos>> listGitRepos(@Path("userGit") String userGit);

}
