package com.ligue1.applicationligue1.model.api.match;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Matches {

    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("season")
    @Expose
    private String season;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("fixtures")
    @Expose
    private List<Fixture> fixtures = null;

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Fixture> getFixtures() {
        return fixtures;
    }

    public void setFixtures(List<Fixture> fixtures) {
        this.fixtures = fixtures;
    }
}