package com.ligue1.applicationligue1.util;

import android.support.annotation.NonNull;

import com.ligue1.applicationligue1.api.endpoint.footballEndpoints;
import com.ligue1.applicationligue1.api.leagueTable.LeagueTable;
import com.ligue1.applicationligue1.api.leagueTable.LeagueTableCallBack;
import com.ligue1.applicationligue1.api.match.Fixture;
import com.ligue1.applicationligue1.api.match.MatchesCallback;
import com.ligue1.applicationligue1.api.match.Matches;
import com.ligue1.applicationligue1.api.match.TeamCallback;

import java.io.File;
import java.util.Collections;
import java.util.List;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JsonParser {
    private static final String BASE_URL = "http://api.football-data.org/v1/";

    private static int cacheSize = 100 * 1024 * 1024; // 10 MB
    private static File cacheFile = new File("cache");
    static Cache cache = new Cache(cacheFile, cacheSize);

    private static footballEndpoints setRetrofit(){
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .cache(cache)
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        request = request.newBuilder()
////                                .header("Cache-Control", "public, max-age=" + 600)
//                                .header("Cache-Control", "public, max-age=" +  60 * 60 * 24 * 28)
//                                .build();
//
////                        if (App.isNetworkAvailable()) {
////                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
////                        } else {
////                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
////                        }
//                        return chain.proceed(request);
//                    }
//                })
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        return retrofit.create(footballEndpoints.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(footballEndpoints.class);
    }

    //https://www.londonappdeveloper.com/consuming-a-json-rest-api-in-android/
    public static void getTeams(final LeagueTableCallBack callBack) {
        //final String BASE_URL = "http://api.football-data.org/v1/competitions/450/leagueTable/";
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        footballEndpoints apiService = retrofit.create(footballEndpoints.class);

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
        //final String BASE_URL = "http://api.football-data.org/v1/teams/" + id + "/fixtures/?league=FL1,450/";
        footballEndpoints apiService = setRetrofit();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        footballEndpoints apiService = retrofit.create(footballEndpoints.class);

        try {
            Call<Matches> call = apiService.getMatches(id, "FL1");
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
            Call<com.ligue1.applicationligue1.api.team.Team> call = apiService.getTeam(teamLink, "FL1", "p1");
            call.enqueue(new Callback<com.ligue1.applicationligue1.api.team.Team>() {
                @Override
                public void onResponse(Call<com.ligue1.applicationligue1.api.team.Team> call, Response<com.ligue1.applicationligue1.api.team.Team> response) {
                    teamCallback.onSuccess(response.body());
                }

                @Override
                public void onFailure(Call<com.ligue1.applicationligue1.api.team.Team> call, Throwable t) {
                    teamCallback.onError();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}