package com.ligue1.applicationligue1.util;

import android.support.annotation.NonNull;

import com.ligue1.applicationligue1.model.api.endpoint.footballEndpoints;
import com.ligue1.applicationligue1.model.api.leagueTable.LeagueTable;
import com.ligue1.applicationligue1.model.api.leagueTable.LeagueTableCallBack;
import com.ligue1.applicationligue1.model.api.leagueTable.Standing;
import com.ligue1.applicationligue1.model.api.match.Fixture;
import com.ligue1.applicationligue1.model.api.match.Matches;
import com.ligue1.applicationligue1.model.api.match.MatchesCallback;
import com.ligue1.applicationligue1.model.api.match.TeamCallback;
import com.ligue1.applicationligue1.model.api.team.Team;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class JsonParser {
    private static final String BASE_URL = "http://api.football-data.org/v1/";
    private static final String LEAGUE = "FL1";

    private static footballEndpoints setRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(footballEndpoints.class);
    }

    public static void getTeams(final LeagueTableCallBack callBack) {
        footballEndpoints apiService = setRetrofit();

        try {
            Call<LeagueTable> call = apiService.getLeagueTable();
            call.enqueue(new Callback<LeagueTable>() {
                @Override
                public void onResponse(@NonNull Call<LeagueTable> call, @NonNull Response<LeagueTable> response) {
                    callBack.onSuccess(response.body().getStanding());
                }

                @Override
                public void onFailure(@NonNull Call<LeagueTable> call, @NonNull Throwable t) {
                    callBack.onError();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void getMatches(final MatchesCallback matchesCallback, String id) {
        footballEndpoints apiService = setRetrofit();

        try {
            Call<Matches> call = apiService.getMatches(id, LEAGUE);
            call.enqueue(new Callback<Matches>() {
                @Override
                public void onResponse(@NonNull Call<Matches> call, @NonNull Response<Matches> response) {
                    if(!response.raw().isSuccessful()){
                        matchesCallback.onError();
                    }

                    List<Fixture> lf = response.body().getFixtures();
                    Collections.reverse(lf);
                    matchesCallback.onSuccess(lf);
                }

                @Override
                public void onFailure(@NonNull Call<Matches> call, @NonNull Throwable t) {
                    matchesCallback.onError();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getTeam(final TeamCallback teamCallback, final String teamLink) {
        footballEndpoints apiService = setRetrofit();

        try {
            Call<Team> call = apiService.getTeam(teamLink, LEAGUE);
            call.enqueue(new Callback<Team>() {
                @Override
                public void onResponse(@NonNull Call<Team> call, @NonNull Response<Team> response) {
                    teamCallback.onSuccess(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<Team> call, @NonNull Throwable t) {
                    teamCallback.onError();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}