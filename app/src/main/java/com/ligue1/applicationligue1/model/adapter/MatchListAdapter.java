package com.ligue1.applicationligue1.model.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ligue1.applicationligue1.R;
import com.ligue1.applicationligue1.model.Match;
import com.ligue1.applicationligue1.util.Display;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * L'adpateur de la liste des matchs
 */
public class MatchListAdapter extends ArrayAdapter<Match> {
    private static final String DASH_SURROUNDED_BY_WHITESPACES = " - ";
    private List<Match> matches;
    private Activity activity;

    public MatchListAdapter(List<Match> matches, Activity activity){
        super(activity, R.layout.match_view, matches);
        this.activity = activity;
        this.matches = matches;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        getContext();
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        convertView = inflater.inflate(R.layout.match_view, parent, false);

        TextView date = convertView.findViewById(R.id.matchDateText);
        date.setText(matches.get(position).getDate());

        TextView score = convertView.findViewById(R.id.matchScoreText);
        score.setText(matches.get(position).getGoalsHomeTeam() + DASH_SURROUNDED_BY_WHITESPACES + matches.get(position).getGoalsAwayTeam());

        if(matches.get(position).getHomeTeam().getLogoUrl() != null && matches.get(position).getAwayTeam().getLogoUrl() != null) {

            TextView sportsClub1 = convertView.findViewById(R.id.matchHomeTeamNameText);
            sportsClub1.setText(matches.get(position).getHomeTeam().getShortName());

            TextView sportsClub2 = convertView.findViewById(R.id.matchAwayTeamNameText);
            sportsClub2.setText(matches.get(position).getAwayTeam().getShortName());


            ImageView homeTeamImage = convertView.findViewById(R.id.matchHomeTeamImage);
            homeTeamImage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

            ImageView awayTeamImage = convertView.findViewById(R.id.matchAwayTeamImage);
            awayTeamImage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

            Display.setImageViewToImage(matches.get(position).getHomeTeam().getLogoUrl(), activity, activity.getApplicationContext(), homeTeamImage);
            Display.setImageViewToImage(matches.get(position).getAwayTeam().getLogoUrl(), activity, activity.getApplicationContext(), awayTeamImage);
        }

        return convertView;
    }
}
