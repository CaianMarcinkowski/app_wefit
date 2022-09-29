package com.evaluation.wefit.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GitReposRepository {

    private GitReposDao dao;
    private LiveData<List<GitRepos>> allRepos;

    public GitReposRepository(Application application){
        AppDataBase database = AppDataBase.getDbInstance(application);
        dao = database.gitReposDao();
        allRepos = (LiveData<List<GitRepos>>) dao.getAllGitRepos();
    }

    public void insert(GitRepos model){
        new InsertCourseAsyncTask(dao).execute(model);
    }

    public void delete(GitRepos model){
        new DeleteCourseAsyncTask(dao).execute();
    }

    public LiveData<List<GitRepos>> getAllGitRepos(){
        return allRepos;
    }

    private static class InsertCourseAsyncTask extends AsyncTask<GitRepos, Void, Void> {
        private GitReposDao dao;

        private InsertCourseAsyncTask(GitReposDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(GitRepos... model) {
            // below line is use to insert our modal in dao.
            dao.insertGitRepos(model[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<GitRepos, Void, Void> {
        private GitReposDao dao;

        private DeleteCourseAsyncTask(GitReposDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(GitRepos... models) {
            // below line is use to delete
            // our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }
}
