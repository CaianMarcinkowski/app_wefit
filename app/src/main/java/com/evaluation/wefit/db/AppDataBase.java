package com.evaluation.wefit.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Criado por Caian Marcinkowski Ferreira - 28/09/2022
// GitHub: https://github.com/CaianMarcinkowski

//Classe que instancia o Banco de dados com o nome de "DB_NAME"
@Database(entities = {GitRepos.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {

    public abstract  GitReposDao gitReposDao();

    private static AppDataBase INSTANCE;

    public static AppDataBase getDbInstance(Context context) {
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "DB_NAME")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
