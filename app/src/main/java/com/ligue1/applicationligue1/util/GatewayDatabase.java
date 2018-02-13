package com.ligue1.applicationligue1.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ligue1.applicationligue1.model.SportsClub;

public final class GatewayDatabase {

    private final static String SPORTS_CLUB = "SPORTS_CLUB";
    private final static String IMAGE = "IMAGE";
    private final static String NAME = "NAME";
    private final static String RANK = "RANK";
    private final static String POINTS = "POINTS";
    private final static String TEAM_LINK = "TEAM_LINK";
    private final static String SHORT_NAME = "SHORT_NAME";

    public static void saveTeam(SportsClub sportsClub, Context context){
        SharedPreferences prefs = context.getSharedPreferences(SPORTS_CLUB, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(sportsClub.getName() + IMAGE, sportsClub.getImg());
        editor.putString(sportsClub.getName() + NAME, sportsClub.getName());
        editor.putString(sportsClub.getName() + RANK, sportsClub.getRank());
        editor.putString(sportsClub.getName() + POINTS, sportsClub.getPoints());
        editor.putString(sportsClub.getName() + TEAM_LINK, sportsClub.getTeamLink());
        editor.putString(sportsClub.getName() + SHORT_NAME, sportsClub.getShortName());
        editor.apply();
    }

    public static SportsClub getTeam(String name, Context context){
        SportsClub sportsClub = new SportsClub();
        sportsClub.setName(name);

        SharedPreferences prefs = context.getSharedPreferences(SPORTS_CLUB, Context.MODE_PRIVATE);

        sportsClub.setImg(prefs.getString(sportsClub.getName() + IMAGE, null));
        sportsClub.setRank(prefs.getString(sportsClub.getName() + RANK,null));
        sportsClub.setPoints(prefs.getString(sportsClub.getName() + POINTS,null));
        sportsClub.setTeamLink(prefs.getString(sportsClub.getName() + TEAM_LINK,null));
        sportsClub.setShortName(prefs.getString(sportsClub.getName() + SHORT_NAME,null));

        return sportsClub;
    }
}
