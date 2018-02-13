package com.ligue1.applicationligue1.model.api.leagueTable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links_ {

    @SerializedName("team")
    @Expose
    private Team team;

    public Team getTeam() {
        return team;
    }
}