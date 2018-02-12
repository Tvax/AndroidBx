package com.ligue1.applicationligue1.api.leagueTable;

import java.util.List;

public interface LeagueTableCallBack {
    void onSuccess(List<Standing> value);
    void onError();
}