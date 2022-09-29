package com.evaluation.wefit.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
