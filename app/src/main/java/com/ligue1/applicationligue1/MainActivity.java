package com.ligue1.applicationligue1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.svgloader.SvgLoader;
import com.ligue1.applicationligue1.api.leagueTable.LeagueTableCallBack;
import com.ligue1.applicationligue1.api.leagueTable.Standing;
import com.ligue1.applicationligue1.model.Equipe;
import com.ligue1.applicationligue1.util.JsonParser;
import com.ligue1.applicationligue1.util.StringChecker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Equipe> equipes = new ArrayList<>();
    Activity activity;
    ListView listView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        listView = findViewById(R.id.teamListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent matchActivity = new Intent(MainActivity.this, MatchActivity.class);
                matchActivity.putExtra("ID", equipes.get(position).id);
                startActivity(matchActivity);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        //mTitle.setText(toolbar.getTitle());
        mTitle.setText("Championnat Ligue 1");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(this.getIntent().getStringExtra("ErrorMessage") != null){
            Toast t = Toast.makeText(getApplicationContext(), this.getIntent().getStringExtra("ErrorMessage"), Toast.LENGTH_LONG);
            t.show();
        }

        LeagueTableCallBack callBack = new LeagueTableCallBack() {
            @Override
            public void onSuccess(List<Standing> value) {
                for (Standing i : value){

                    Equipe tmp = new Equipe();

                    tmp.img = i.getCrestURI();
                    tmp.name = i.getTeamName();
                    tmp.points = String.valueOf(i.getPoints());
                    tmp.rank = String.valueOf(i.getPosition());

                    String[] id = i.getLinks().getTeam().getHref().split("/");
                    tmp.id = String.valueOf(Integer.parseInt(id[id.length-1]));

                    equipes.add(tmp);
                }
                populateListView();
            }

            @Override
            public void onError() {}
        };

        JsonParser.getTeams(callBack);

    }



    @Override protected void onDestroy() {
        super.onDestroy();
        SvgLoader.pluck().close();
    }

    private void populateListView(){
        ArrayAdapter<Equipe> adapter = new TeamListAdapter();
        ListView listView = findViewById(R.id.teamListView);
        listView.setAdapter(adapter);
    }

    private class TeamListAdapter extends ArrayAdapter<Equipe>{
        TeamListAdapter(){
            super(MainActivity.this, R.layout.team_view, equipes);
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
            name.setText(equipes.get(position).name);

            TextView id = convertView.findViewById(R.id.rank);
            id.setText("N°" + String.valueOf(position + 1));

            TextView points = convertView.findViewById(R.id.points);
            points.setText(equipes.get(position).points + " PTS");

            ImageView img = convertView.findViewById(R.id.img);
            img.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

            if (StringChecker.isSVG(equipes.get(position).img)){
                SvgLoader.pluck().with(activity)
                        //.setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                        .load(equipes.get(position).img, img);
            }
            else {
                Picasso.with(getApplicationContext())
                        .load(equipes.get(position).img)
                        .into(img);
            }

            //listView.invalidate();
            //.resize(55,55)


//            AsyncTask<String, Integer, Bitmap> d  = new DownloadSVGTask(img).execute(equipes.get(position).img);


            //new DownloadSVGTask(img).execute(equipes.get(position).img);


//            try {
//                new DownloadSVGTask(img).execute(equipes.get(position).img).get();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            return convertView;
            //return super.getView(position, convertView, parent);
        }
    }
}


