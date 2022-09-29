package com.evaluation.wefit.models;

import java.util.List;

public class GitRepos {
    public String author;
    public String full_name;
    public String description;
    public int stargazers_count;
    public String language;
    public String html_url;
    public List<Owner> owners = null;
}
