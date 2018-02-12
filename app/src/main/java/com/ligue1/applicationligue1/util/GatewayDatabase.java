package com.ligue1.applicationligue1.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ligue1.applicationligue1.model.Equipe;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class GatewayDatabase {

    public static void saveTeam(Equipe equipe, Context context){
        SharedPreferences prefs = context.getSharedPreferences("Equipe", 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(equipe.name+"Image", equipe.img);
        editor.putString(equipe.name+"Name", equipe.name);
        editor.putString(equipe.name+"Rank", equipe.rank);
        editor.putString(equipe.name+"Points", equipe.points);
        editor.putString(equipe.name+"TeamLink", equipe.teamLink);
        editor.putString(equipe.name+"ShortName", equipe.shortName);
        editor.commit();
    }

    public static Equipe getTeam(String name, Context context){
        Equipe equipe = new Equipe();
        equipe.name = name;

        SharedPreferences prefs = context.getSharedPreferences("Equipe", 0);

//        Map<String, ?> allEntries = prefs.getAll();
//        for (Map.Entry<String, ?> entry : allEntries.entrySet())
//            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());

        equipe.img = prefs.getString(equipe.name+"Image", null);
        equipe.rank = prefs.getString(equipe.name+"Rank",null);
        equipe.points = prefs.getString(equipe.name+"Points",null);
        equipe.teamLink = prefs.getString(equipe.name+"TeamLink",null);
        equipe.shortName = prefs.getString(equipe.name+"ShortName",null);

        return equipe;
    }
}
