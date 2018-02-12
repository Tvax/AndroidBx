package com.ligue1.applicationligue1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.ligue1.applicationligue1.api.match.Fixture;
import com.ligue1.applicationligue1.api.match.MatchesCallback;
import com.ligue1.applicationligue1.model.Equipe;
import com.ligue1.applicationligue1.model.Match;
import com.ligue1.applicationligue1.util.GatewayDatabase;
import com.ligue1.applicationligue1.util.JsonParser;
import com.ligue1.applicationligue1.util.StringChecker;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MatchActivity extends AppCompatActivity{
    List<Match> matches = new ArrayList<>();
    Activity activity = this;
    ListView listView;
    ArrayAdapter<Match> adapter;
    Parcelable state;

    private static final String LIST_STATE = "listState";
    private Parcelable mListState = null;

    @SuppressLint({"WrongConstant", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        //listView = findViewById(R.id.matchListView);

        Toolbar toolbar = findViewById(R.id.toolbarMatch);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_titleMatch);
        setSupportActionBar(toolbar);
        mTitle.setText("Résultats et calendriers");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        MatchesCallback matchesCallback = new MatchesCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(List<Fixture> value) {
                for (Fixture i : value) {

                    if(i.getLinks().getCompetition().getHref().endsWith("450")){
                        Match match = new Match();
                        match.date = i.getDate().replace("T", " ").replace("Z","");

                        if(i.getResult().getGoalsHomeTeam() == null){
                            match.scoreEquipe1 = "";
                            match.scoreEquipe2 = "";
                        }else {
                            match.scoreEquipe1 = new DecimalFormat("##").format(i.getResult().getGoalsHomeTeam());
                            match.scoreEquipe2 = new DecimalFormat("##").format(i.getResult().getGoalsAwayTeam());
                        }

                        //check si dans db
                        Equipe homeTeam = GatewayDatabase.getTeam(i.getHomeTeamName(), getApplicationContext());
                        Equipe awayTeam = GatewayDatabase.getTeam(i.getAwayTeamName(), getApplicationContext());

                        if (homeTeam.img == null) {
                            homeTeam = getEquipeFromMatchesList(i.getHomeTeamName());

                            if (homeTeam == null) {
                                homeTeam = new Equipe(i.getHomeTeamName(), i.getLinks().getHomeTeam().getHref());
                            }
                            //GatewayDatabase.saveTeam(homeTeam, getApplicationContext());

                        }
                        if (awayTeam.img == null) {
                            awayTeam = getEquipeFromMatchesList(i.getAwayTeamName());

                            if (awayTeam == null) {
                                awayTeam = new Equipe(i.getAwayTeamName(), i.getLinks().getAwayTeam().getHref());
                            }

                            //GatewayDatabase.saveTeam(awayTeam, getApplicationContext());
                        }

                        match.homeTeam = homeTeam;
                        match.awayTeam = awayTeam;

                       // while (match.awayTeam.img == null && match.homeTeam.img == null){}

                        matches.add(match);
                        adapter.add(match);
                        //invalidateOptionsMenu();
                        adapter.notifyDataSetChanged();
//                       listView.getAdapter().notifyAll();
                    }

                }
            }

            @Override
            public void onError() {
                Intent mainActivity = new Intent(MatchActivity.this, MainActivity.class);
                mainActivity.putExtra("ErrorMessage", "Can't retrieve data, wait");
                startActivity(mainActivity);
            }
        };

        JsonParser.getMatches(matchesCallback, this.getIntent().getStringExtra("ID"));
        populateListView();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private Equipe getEquipeFromMatchesList(String name){
        for (Match i : matches){
            if(Objects.equals(i.awayTeam.name, name)){
                return i.awayTeam;
            }
            if(Objects.equals(i.homeTeam.name, name)){
                return i.homeTeam;
            }
        }
        return null;
    }

    @Override protected void onDestroy() {
        SvgLoader.pluck().close();
        for (Match i : matches){
            GatewayDatabase.saveTeam(i.awayTeam, getApplicationContext());
            GatewayDatabase.saveTeam(i.homeTeam, getApplicationContext());
        }
        super.onDestroy();
    }

    @Override protected void onStop() {
        state = listView.onSaveInstanceState();
        for (Match i : matches){
            GatewayDatabase.saveTeam(i.awayTeam, getApplicationContext());
            GatewayDatabase.saveTeam(i.homeTeam, getApplicationContext());
        }
        super.onStop();
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
//        mListState = state.getParcelable(LIST_STATE);
//        if (mListState != null)
//            listView.onRestoreInstanceState(mListState);

        SharedPreferences prefs = getSharedPreferences("List", 0);

        listView.setSelection(prefs.getInt("ListItemStart", 5));
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onResume() {
//        if (mListState != null)
//            listView.onRestoreInstanceState(mListState);
//        mListState = null;
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        SharedPreferences prefs = getSharedPreferences("List", 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("ListItemStart", listView.getFirstVisiblePosition());
        editor.apply();


//        mListState = listView.onSaveInstanceState();
//        state.putParcelable(LIST_STATE, mListState);
        super.onSaveInstanceState(state);

    }

    private void populateListView(){
        listView = findViewById(R.id.matchListView);
        adapter = new MatchListAdapter();
        listView.setAdapter(adapter);
    }

    private class MatchListAdapter extends ArrayAdapter<Match> {
        MatchListAdapter (){
            super(MatchActivity.this, R.layout.match_view, matches);
        }

        @SuppressLint({"ViewHolder", "SetTextI18n"})
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            getContext();
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.match_view, parent, false);

            TextView date = convertView.findViewById(R.id.matchDateText);
            date.setText(matches.get(position).date);

            TextView score = convertView.findViewById(R.id.matchScoreText);
            score.setText(matches.get(position).scoreEquipe1 + " - " + matches.get(position).scoreEquipe2);

            if(matches.get(position).homeTeam.img != null && matches.get(position).awayTeam.img != null) {

                TextView equipe1 = convertView.findViewById(R.id.matchHomeTeamNameText);
                equipe1.setText(matches.get(position).homeTeam.name);

                TextView equipe2 = convertView.findViewById(R.id.matchAwayTeamNameText);
                equipe2.setText(matches.get(position).awayTeam.name);

                ImageView img1 = convertView.findViewById(R.id.matchHomeTeamImage);
                img1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

                if (StringChecker.isSVG(matches.get(position).homeTeam.img)){
                    SvgLoader.pluck().with(activity)
                            .load(matches.get(position).homeTeam.img, img1);
                }
                else {
                    Picasso.with(getApplicationContext())
                            .load(matches.get(position).homeTeam.img)
                            .into(img1);
                }

                ImageView img2 = convertView.findViewById(R.id.matchAwayTeamImage);
                img2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

                if (StringChecker.isSVG(matches.get(position).awayTeam.img)){
                    SvgLoader.pluck().with(activity)
                            .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                            .load(matches.get(position).awayTeam.img, img2);
                }
                else {
                    Picasso.with(getApplicationContext())
                            .load(matches.get(position).awayTeam.img)
                            .into(img2);
                }
            }

            return convertView;
        }
    }
}
