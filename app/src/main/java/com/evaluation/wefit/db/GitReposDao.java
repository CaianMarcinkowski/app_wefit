package com.evaluation.wefit.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// Criado por Caian Marcinkowski Ferreira - 28/09/2022
// GitHub: https://github.com/CaianMarcinkowski

//Interface que é definida as funções que serão realizadas no banco de dados SQLite (Local), Ex.: QUERY, INSERT, DELETE e UPDATE

@Dao
public interface GitReposDao {

    //Query onde busca todos os git_repos (Repositorios do Github cadastrados) no SQLite
    @Query("SELECT * FROM git_repos")
    List<GitRepos> getAllGitRepos();

    //Realiza insert no banco SQLite com as informações passados por parametro no objeto GitRepos
    @Insert
    void insertGitRepos(GitRepos getGitRepos);

    //Deleta o objeto envia via paramtro (GitRepos)
    @Delete
    void delete(GitRepos gitRepos);
}
