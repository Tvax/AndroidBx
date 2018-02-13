package com.ligue1.applicationligue1.model;

/**
 * Résultats d'un match de football
 */
public class Match {

    //date de la rencontre
    private String date;

    //equipe jouant à domicile
    private SportsClub homeTeam;

    //equipe jouant à l'extérieur
    private SportsClub awayTeam;

    //nombre de buts de l'équipe jouant à domicile
    private String goalsHomeTeam;

    //nombre de buts de l'équipe jouant à l'extérieur
    private String goalsAwayTeam;

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

    public String getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public void setGoalsHomeTeam(String goalsHomeTeam) {
        this.goalsHomeTeam = goalsHomeTeam;
    }

    public String getGoalsAwayTeam() {
        return goalsAwayTeam;
    }

    public void setGoalsAwayTeam(String goalsAwayTeam) {
        this.goalsAwayTeam = goalsAwayTeam;
    }
}
