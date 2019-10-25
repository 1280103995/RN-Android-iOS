package com.rn.entity;

import java.util.List;

public class MeiZiResponse {
    private boolean error;
    private List<MeiZi> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<MeiZi> getResults() {
        return results;
    }

    public void setResults(List<MeiZi> results) {
        this.results = results;
    }
}
