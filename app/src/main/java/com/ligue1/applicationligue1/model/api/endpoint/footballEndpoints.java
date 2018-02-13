package com.ligue1.applicationligue1.model.api.endpoint;

import com.ligue1.applicationligue1.model.api.leagueTable.LeagueTable;
import com.ligue1.applicationligue1.model.api.match.Matches;
import com.ligue1.applicationligue1.model.api.team.Team;
//import com.ligue1.applicationligue1.model.FootballTeamData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface footballEndpoints {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter
//    @GET("http://api.football-data.org/v1/competitions/450/teams/")
//    Call<FootballTeamData> getCount(@Query("count") int count);

    //@GET("http://api.football-data.org/v1/teams/{id}/fixtures?league=FL1,450/")
    @Headers("X-Auth-Token: 8a493cb555514bf09265f9f048477fc2")
    @GET("http://api.football-data.org/v1/teams/{id}/fixtures")
    Call<Matches> getMatches(@Path("id") String id, @Query("league") String league);

    @Headers("X-Auth-Token: 8a493cb555514bf09265f9f048477fc2")
    @GET("http://api.football-data.org/v1/competitions/450/leagueTable/")
    Call<LeagueTable> getLeagueTable();

    @Headers("X-Auth-Token: 8a493cb555514bf09265f9f048477fc2")
    @GET("http://api.football-data.org/v1/teams/{id}")
    Call<Team> getTeam(@Path("id") String id, @Query("league") String league);
}
