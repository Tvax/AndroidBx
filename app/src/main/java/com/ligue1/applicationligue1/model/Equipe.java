package com.ligue1.applicationligue1.model;

import com.ligue1.applicationligue1.api.match.TeamCallback;
import com.ligue1.applicationligue1.api.team.Team;
import com.ligue1.applicationligue1.util.JsonParser;

public class Equipe {
    public String id;
    public String img;
    public String name;
    public String rank;
    public String points;
    public String teamLink;
    public String shortName;

    public Equipe(){

    }

    public Equipe(String name, String teamLink){
        this.name = name;
        this.teamLink = teamLink;
        this.id = teamLink.split("/")[5];
        setImageAndShortName();
    }

    private void setImageAndShortName(){
        JsonParser.getTeam(teamCallback, this.id);
    }

    private TeamCallback teamCallback = new TeamCallback() {
        @Override
        public void onSuccess(Team value) {
            if(value == null){
                shortName = name;
            }
            else {
                shortName = value.getShortName();
                img = value.getCrestUrl();
            }
        }


        @Override
        public void onError() {
        }
    };
}
