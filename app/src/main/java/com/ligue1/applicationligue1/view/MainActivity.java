package com.ligue1.applicationligue1.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.ligue1.applicationligue1.R;
import com.ligue1.applicationligue1.model.SportsClub;
import com.ligue1.applicationligue1.model.adapter.TeamListAdapter;
import com.ligue1.applicationligue1.model.api.leagueTable.LeagueTableCallBack;
import com.ligue1.applicationligue1.model.api.leagueTable.Standing;
import com.ligue1.applicationligue1.util.Display;
import com.ligue1.applicationligue1.util.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<SportsClub> sportsClubs = new ArrayList<>();
    Activity activity;
    ListView listView;
    LeagueTableCallBack callBack;


    private Parcelable mListState;
    private static final String LIST_STATE = "LIST_STATE";
    private static final String TEAM_ID = "TEAM_ID";
    private static final String SLASH = "/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        listView = findViewById(R.id.mainListView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(R.string.championshipName);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent matchActivity = new Intent(MainActivity.this, MatchActivity.class);
                matchActivity.putExtra(TEAM_ID, sportsClubs.get(position).getId());
                startActivity(matchActivity);
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeToRefreshMain);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if(listView.getCount() < 1)
                            JsonParser.getTeams(callBack);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );


        callBack = new LeagueTableCallBack() {
            @Override
            public void onSuccess(List<Standing> value) {
                for (Standing i : value){
                    sportsClubs.add(makeSportsClub(i));
                }
                populateListView();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError() {
                swipeRefreshLayout.setRefreshing(false);
                Display.displayToast(getString(R.string.errorMessageText), getApplicationContext());
            }
        };
        JsonParser.getTeams(callBack);
    }

    private SportsClub makeSportsClub(Standing i) {
        SportsClub tmp = new SportsClub();

        tmp.setPlayedGames(String.valueOf(i.getPlayedGames()));
        tmp.setGoals(String.valueOf(i.getGoals()));
        tmp.setGoalsAgainst(String.valueOf(i.getGoalsAgainst()));
        tmp.setGoalDifference(String.valueOf(i.getGoalDifference()));
        tmp.setWins(String.valueOf(i.getWins()));
        tmp.setLooses(String.valueOf(i.getLosses()));
        tmp.setDraws(String.valueOf(i.getDraws()));

        tmp.setImg(i.getCrestURI());
        tmp.setName(i.getTeamName());
        tmp.setPoints(String.valueOf(i.getPoints()));
        tmp.setRank(String.valueOf(i.getPosition()));

        String[] id = i.getLinks().getTeam().getHref().split(SLASH);
        tmp.setId(String.valueOf(Integer.parseInt(id[id.length-1])));

        return tmp;
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        SvgLoader.pluck().close();
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


    private void populateListView(){
        ArrayAdapter<SportsClub> adapter = new TeamListAdapter(MainActivity.this, sportsClubs, getResources());
        ListView listView = findViewById(R.id.mainListView);
        listView.setAdapter(adapter);
    }
}


