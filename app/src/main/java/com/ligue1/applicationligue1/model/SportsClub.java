package com.ligue1.applicationligue1.model;

import com.ligue1.applicationligue1.model.api.match.TeamCallback;
import com.ligue1.applicationligue1.model.api.team.Team;
import com.ligue1.applicationligue1.util.JsonParser;

public class SportsClub {
    private String id;
    private String img;
    private String name;
    private String rank;
    private String points;
    private String teamLink;
    private String shortName;
    private String playedGames;
    private String goals;
    private String goalsAgainst;
    private String goalDifference;
    private String wins;
    private String draws;
    private String looses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
                setImg(value.getCrestUrl());
            }
        }


        @Override
        public void onError() {
        }
    };
}
