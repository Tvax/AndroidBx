package com.ligue1.applicationligue1.model.api.match;

public interface TeamCallback {
    void onSuccess(com.ligue1.applicationligue1.model.api.team.Team value);
    void onError();
}
