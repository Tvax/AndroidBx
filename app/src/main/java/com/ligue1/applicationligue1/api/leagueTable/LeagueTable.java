package com.ligue1.applicationligue1.api.leagueTable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeagueTable {
//
//    @SerializedName("_links")
//    @Expose
//    private Links links;
//    @SerializedName("leagueCaption")
//    @Expose
//    private String leagueCaption;
//    @SerializedName("matchday")
    @Expose
    private int matchday;
    @SerializedName("standing")
    @Expose
    private List<Standing> standing = null;

//    public Links getLinks() {
//        return links;
//    }
//
//    public void setLinks(Links links) {
//        this.links = links;
//    }
//
//    public String getLeagueCaption() {
//        return leagueCaption;
//    }
//
//    public void setLeagueCaption(String leagueCaption) {
//        this.leagueCaption = leagueCaption;
//    }
//
//    public int getMatchday() {
//        return matchday;
//    }
//
//    public void setMatchday(int matchday) {
//        this.matchday = matchday;
//    }

    public List<Standing> getStanding() {
        return standing;
    }

    public void setStanding(List<Standing> standing) {
        this.standing = standing;
    }

}
