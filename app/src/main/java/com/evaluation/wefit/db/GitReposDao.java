package com.evaluation.wefit.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GitReposDao {

    @Query("SELECT * FROM git_repos")
    List<GitRepos> getAllGitRepos();

    @Insert
    void insertGitRepos(GitRepos getGitRepos);

    @Delete
    void delete(GitRepos gitRepos);
}
