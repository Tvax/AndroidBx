package com.ligue1.applicationligue1.model.api.match;

import java.util.List;

public interface MatchesCallback {
    void onSuccess(List<Fixture> value);
    void onError();
}
