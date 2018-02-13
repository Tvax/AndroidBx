package com.ligue1.applicationligue1.model;

import com.ligue1.applicationligue1.model.api.match.TeamCallback;
import com.ligue1.applicationligue1.model.api.team.Team;
import com.ligue1.applicationligue1.util.JsonParser;

/**
 * Equipe de football
 */
public class SportsClub {

    //id du {@link SportsClub} sur l'API
    private String id;

    //lien de lu logo de l'équipe
    private String logoUrl;

    //nom de l'équipe
    private String name;

    //classement de l'équipe
    private String rank;

    //nombre de points de l'équipe
    private String points;

    //lien de l'équipe sur l'API
    private String teamLink;

    //nom raccourci de l'équipe
    private String shortName;

    //nombre de matchs joués
    private String playedGames;

    //nombre de buts marqués
    private String goals;

    //nombre de but contre
    private String goalsAgainst;

    //différence des buts
    private String goalDifference;

    //nombre de victoires
    private String wins;

    //nombre de matchs nuls
    private String draws;

    //nombre de défaites
    private String looses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getTeamLink() {
        return teamLink;
    }

    public void setTeamLink(String teamLink) {
        this.teamLink = teamLink;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(String playedGames) {
        this.playedGames = playedGames;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(String goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public String getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(String goalDifference) {
        this.goalDifference = goalDifference;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public String getDraws() {
        return draws;
    }

    public void setDraws(String draws) {
        this.draws = draws;
    }

    public String getLooses() {
        return looses;
    }

    public void setLooses(String looses) {
        this.looses = looses;
    }

    public SportsClub(){

    }

    public SportsClub(String name, String teamLink){
        this.setName(name);
        this.setTeamLink(teamLink);
        this.setId(teamLink.split("/")[5]);
        setImageAndShortName();
    }

    /**
     * Assigne une valeur à imgUrl et à shortName
     */
    private void setImageAndShortName(){
        JsonParser.getTeam(teamCallback, this.getId());
    }

    private TeamCallback teamCallback = new TeamCallback() {
        @Override
        public void onSuccess(Team value) {
            if(value == null){
                setShortName(getName());
            }
            else {
                setShortName(value.getShortName());
                setLogoUrl(value.getCrestUrl());
            }
        }
        @Override
        public void onError() {
        }
    };
}
