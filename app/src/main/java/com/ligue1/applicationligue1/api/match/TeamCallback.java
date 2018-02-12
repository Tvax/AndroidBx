package com.ligue1.applicationligue1.api.match;

public interface TeamCallback {
    void onSuccess(com.ligue1.applicationligue1.api.team.Team value);
    void onError();
}
