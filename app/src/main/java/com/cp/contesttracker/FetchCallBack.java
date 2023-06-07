package com.cp.contesttracker;

import java.util.List;
// Interface containing callback method to call after fetching contest data
public interface FetchCallBack {
    void onContestFetch(List<Contest> contestList);
}
