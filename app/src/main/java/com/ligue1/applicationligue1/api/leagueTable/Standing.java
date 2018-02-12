package com.ligue1.applicationligue1.api.leagueTable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Standing {
    @SerializedName("_links")
    @Expose
    private Links_ links;
    @SerializedName("position")
    @Expose
    private int position;
    @SerializedName("teamName")
    @Expose
    private String teamName;
    @SerializedName("crestURI")
    @Expose
    private String crestURI;
//    @SerializedName("playedGames")
//    @Expose
//    private int playedGames;
    @SerializedName("points")
    @Expose
    private int points;
//    @SerializedName("goals")
//    @Expose
//    private int goals;
//    @SerializedName("goalsAgainst")
//    @Expose
//    private int goalsAgainst;
//    @SerializedName("goalDifference")
//    @Expose
//    private int goalDifference;
//    @SerializedName("wins")
//    @Expose
//    private int wins;
//    @SerializedName("draws")
//    @Expose
//    private int draws;
//    @SerializedName("losses")
//    @Expose
//    private int losses;
//    @SerializedName("home")
//    @Expose
//    private Home home;
//    @SerializedName("away")
//    @Expose
//    private Away away;

    public Links_ getLinks() {
        return links;
    }
    public int getPosition() {
        return position;
    }

    public String getTeamName() {
        return teamName;
    }


    public String getCrestURI() {
        return crestURI;
    }


//    public int getPlayedGames() {
//        return playedGames;
//    }
//
//    public void setPlayedGames(int playedGames) {
//        this.playedGames = playedGames;
//    }

    public int getPoints() {
        return points;
    }

//
//    public int getGoals() {
//        return goals;
//    }
//
//    public void setGoals(int goals) {
//        this.goals = goals;
//    }
//
//    public int getGoalsAgainst() {
//        return goalsAgainst;
//    }
//
//    public void setGoalsAgainst(int goalsAgainst) {
//        this.goalsAgainst = goalsAgainst;
//    }
//
//    public int getGoalDifference() {
//        return goalDifference;
//    }
//
//    public void setGoalDifference(int goalDifference) {
//        this.goalDifference = goalDifference;
//    }
//
//    public int getWins() {
//        return wins;
//    }
//
//    public void setWins(int wins) {
//        this.wins = wins;
//    }
//
//    public int getDraws() {
//        return draws;
//    }
//
//    public void setDraws(int draws) {
//        this.draws = draws;
//    }
//
//    public int getLosses() {
//        return losses;
//    }
//
//    public void setLosses(int losses) {
//        this.losses = losses;
//    }

//    public Home getHome() {
//        return home;
//    }
//
//    public void setHome(Home home) {
//        this.home = home;
//    }
//
//    public Away getAway() {
//        return away;
//    }
//
//    public void setAway(Away away) {
//        this.away = away;
//    }

}
