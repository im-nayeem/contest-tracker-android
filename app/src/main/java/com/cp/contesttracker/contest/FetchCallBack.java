package com.cp.contesttracker.contest;

import com.cp.contesttracker.contest.Contest;

import java.util.HashMap;
import java.util.List;
// Interface containing callback method to call after fetching contest data
public interface FetchCallBack {
    void onContestFetch(HashMap<String, List<Contest>> contestList);
}
