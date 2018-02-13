package com.ligue1.applicationligue1.model.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ligue1.applicationligue1.R;
import com.ligue1.applicationligue1.model.SportsClub;
import com.ligue1.applicationligue1.util.Display;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class TeamListAdapter extends ArrayAdapter<SportsClub> {
    private static final String WHITE_SPACE = " ";
    private static final String DOUBLE_WHITE_SPACE = WHITE_SPACE + WHITE_SPACE;
    private static final int EMULATE_ARRAY_STARTS_AT_ONE = 1;

    private List<SportsClub> sportsClubs;
    private Activity activity;
    private Resources resources;

    public TeamListAdapter(Activity activity, List<SportsClub> sportsClubs, Resources resources){
        super(activity, R.layout.team_view, sportsClubs);
        this.activity = activity;
        this.sportsClubs = sportsClubs;
        this.resources = resources;
    }

    @NonNull
    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        getContext();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        convertView = inflater.inflate(R.layout.team_view, parent, false);

        TextView name = convertView.findViewById(R.id.name);
        name.setText(sportsClubs.get(position).getName());

        TextView id = convertView.findViewById(R.id.rank);
        id.setText(getStringFromResource(R.string.rankNumber, resources)+ String.valueOf(position + EMULATE_ARRAY_STARTS_AT_ONE));

        TextView points = convertView.findViewById(R.id.points);
        points.setText(sportsClubs.get(position).getPoints() + WHITE_SPACE + getStringFromResource(R.string.shortPoints, resources));

        TextView stats = convertView.findViewById(R.id.stats);
        stats.setText(makeStatsString(sportsClubs.get(position)));

        ImageView img = convertView.findViewById(R.id.img);
        img.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Display.setImageViewToImage(sportsClubs.get(position).getImg(), activity, activity.getApplicationContext(), img);

        return convertView;
    }

    private String makeStatsString(SportsClub sportsClub) {
        String ans = sportsClub.getPlayedGames();
        ans += WHITE_SPACE + getStringFromResource(R.string.playedGames, resources);

        ans += DOUBLE_WHITE_SPACE + sportsClub.getWins();
        ans += WHITE_SPACE + getStringFromResource(R.string.wins, resources);

        ans += DOUBLE_WHITE_SPACE + sportsClub.getDraws();
        ans += WHITE_SPACE + getStringFromResource(R.string.draws, resources);

        ans += DOUBLE_WHITE_SPACE + sportsClub.getLooses();
        ans += WHITE_SPACE + getStringFromResource(R.string.looses, resources);

        ans += DOUBLE_WHITE_SPACE + sportsClub.getGoals();
        ans += WHITE_SPACE + getStringFromResource(R.string.goals, resources);

        ans += DOUBLE_WHITE_SPACE + sportsClub.getGoalsAgainst();
        ans += WHITE_SPACE + getStringFromResource(R.string.goalsAgainst, resources);

        ans += DOUBLE_WHITE_SPACE + sportsClub.getGoalDifference();
        ans += WHITE_SPACE + getStringFromResource(R.string.goalsDifference, resources);

        return ans;
    }

    private String getStringFromResource(int id, Resources resources){
        return resources.getString(id);
    }
}
