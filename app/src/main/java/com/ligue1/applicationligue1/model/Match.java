package com.ligue1.applicationligue1.model;

public class Match {
    private String date;
    private SportsClub homeTeam;
    private SportsClub awayTeam;
    private String goalsTeam1;
    private String goalsTeam2;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public SportsClub getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(SportsClub homeTeam) {
        this.homeTeam = homeTeam;
    }

    public SportsClub getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(SportsClub awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getGoalsTeam1() {
        return goalsTeam1;
    }

    public void setGoalsTeam1(String goalsTeam1) {
        this.goalsTeam1 = goalsTeam1;
    }

    public String getGoalsTeam2() {
        return goalsTeam2;
    }

    public void setGoalsTeam2(String goalsTeam2) {
        this.goalsTeam2 = goalsTeam2;
    }
}
