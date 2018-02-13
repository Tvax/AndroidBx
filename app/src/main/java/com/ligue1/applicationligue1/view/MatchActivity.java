package com.ligue1.applicationligue1.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.ligue1.applicationligue1.R;
import com.ligue1.applicationligue1.model.Match;
import com.ligue1.applicationligue1.model.SportsClub;
import com.ligue1.applicationligue1.model.adapter.MatchListAdapter;
import com.ligue1.applicationligue1.model.api.match.Fixture;
import com.ligue1.applicationligue1.model.api.match.MatchesCallback;
import com.ligue1.applicationligue1.util.Display;
import com.ligue1.applicationligue1.util.GatewayDatabase;
import com.ligue1.applicationligue1.util.JsonParser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MatchActivity extends AppCompatActivity{
    List<Match> matches = new ArrayList<>();
    ListView listView;
    ArrayAdapter<Match> adapter;
    Parcelable state;
    String teamID;
    MatchesCallback matchesCallback;

    private Parcelable mListState;
    private static final String LIST_STATE = "LIST_STATE";
    private static final String L1_ID = "450";
    private static final String TEAM_ID = "TEAM_ID";
    private static final String WHITE_SPACE = " ";
    private static final String EMPTY_SPACE = "";
    private static final String REMOVE_DECIMALS = "##";
    private static final String T_LETTER = "T";
    private static final String Z_LETTER = "Z";


    @SuppressLint({"WrongConstant", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(R.string.resultsAndSchedules);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        teamID = this.getIntent().getStringExtra(TEAM_ID);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeToRefreshMain);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if(listView.getCount() < 1)
                            JsonParser.getMatches(matchesCallback, teamID);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });


        matchesCallback = new MatchesCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(List<Fixture> value) {
                for (Fixture i : value) {

                    if(i.getLinks().getCompetition().getHref().endsWith(L1_ID)){
                        adapter.add(makeMatch(i));
                    }

                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError() {
                swipeRefreshLayout.setRefreshing(false);
                Display.displayToast(getString(R.string.errorMessageText), getApplicationContext());
            }
        };
        JsonParser.getMatches(matchesCallback, this.getIntent().getStringExtra(TEAM_ID));
        populateListView();
    }

    private void populateListView(){
        listView = findViewById(R.id.mainListView);
        adapter = new MatchListAdapter(matches, MatchActivity.this);
        listView.setAdapter(adapter);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private SportsClub getSportsClubFromMatchesList(String name){
        for (Match i : matches){
            if(Objects.equals(i.getAwayTeam().getName(), name)){
                return i.getAwayTeam();
            }
            if(Objects.equals(i.getHomeTeam().getName(), name)){
                return i.getHomeTeam();
            }
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private Match makeMatch(Fixture i){
        Match match = new Match();
        match.setDate(i.getDate().replace(T_LETTER, WHITE_SPACE).replace(Z_LETTER, EMPTY_SPACE));

        if(i.getResult().getGoalsHomeTeam() == null){
            match.setGoalsTeam1(EMPTY_SPACE);
            match.setGoalsTeam2(EMPTY_SPACE);
        }else {
            match.setGoalsTeam1(new DecimalFormat(REMOVE_DECIMALS).format(i.getResult().getGoalsHomeTeam()));
            match.setGoalsTeam2(new DecimalFormat(REMOVE_DECIMALS).format(i.getResult().getGoalsAwayTeam()));
        }

        //check si dans db
        SportsClub homeTeam = GatewayDatabase.getTeam(i.getHomeTeamName(), getApplicationContext());
        SportsClub awayTeam = GatewayDatabase.getTeam(i.getAwayTeamName(), getApplicationContext());

        if (homeTeam.getImg() == null) {
            homeTeam = getSportsClubFromMatchesList(i.getHomeTeamName());

            if (homeTeam == null) {
                homeTeam = new SportsClub(i.getHomeTeamName(), i.getLinks().getHomeTeam().getHref());
            }
        }
        if (awayTeam.getImg() == null) {
            awayTeam = getSportsClubFromMatchesList(i.getAwayTeamName());

            if (awayTeam == null) {
                awayTeam = new SportsClub(i.getAwayTeamName(), i.getLinks().getAwayTeam().getHref());
            }
        }

        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);

        return match;
    }

    @Override protected void onDestroy() {
        SvgLoader.pluck().close();
        super.onDestroy();
    }

    @Override protected void onStop() {
        state = listView.onSaveInstanceState();
        for (Match i : matches){
            GatewayDatabase.saveTeam(i.getAwayTeam(), getApplicationContext());
            GatewayDatabase.saveTeam(i.getHomeTeam(), getApplicationContext());
        }
        super.onStop();
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        mListState = state.getParcelable(LIST_STATE);
        if (mListState != null)
            listView.onRestoreInstanceState(mListState);
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        mListState = listView.onSaveInstanceState();
        state.putParcelable(LIST_STATE, mListState);
        super.onSaveInstanceState(state);
    }
}
