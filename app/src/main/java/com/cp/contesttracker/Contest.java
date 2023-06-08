package com.cp.contesttracker;

// Data Class(Model) to hold data for a single contest

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Contest {
    private String name = null;
    private String time = null;
    private String host = null;
    private String contestLink = null;

    private static String[] hostList = {"CodeForces", "CodeChef", "AtCoder", "TopCoder", "Toph", "LeetCode", "CodeJam", "HackerCup", "KickStart"};

    public Contest(String name, String time, String host, String contestLink) {
        this.name = name;
        this.time = time;
        this.host = host;
        this.contestLink = contestLink;
    }

    public Contest(String name, String time, String host) {
        this.name = name;
        this.time = time;
        this.host = host;
    }


    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getHost() {
        return host;
    }

    public String getContestLink() {
        return contestLink;
    }


    public static String[] getHostList(){
        return hostList;
    }

    public static HashMap<String,String> getHostNameToLink(){

        HashMap<String, String> hostNameToLink = new HashMap<>();

        hostNameToLink.put("CodeForces","codeforces.com");
        hostNameToLink.put("CodeChef", "codechef.com");
        hostNameToLink.put("TopCoder", "topcoder.com");
        hostNameToLink.put("Toph", "toph.co");
        hostNameToLink.put("LeetCode", "leetcode.com");
        hostNameToLink.put("CodeJam", "codingcompetitions.withgoogle.com/codejam");
        hostNameToLink.put("HackerCup", "facebook.com/hackercup");
        hostNameToLink.put("KickStart", "codingcompetitions.withgoogle.com/kickstart");
        hostNameToLink.put("AtCoder", "atcoder.jp");

        return hostNameToLink;
    }

    public static HashMap<String, String> getLinkToHostName(){

        HashMap<String, String> linkToHostName = new HashMap<>();

        HashMap<String, String> hostNametolink = getHostNameToLink();

        for (Map.Entry<String, String> entry : hostNametolink.entrySet()) {
            linkToHostName.put(entry.getValue(), entry.getKey());
        }

        return linkToHostName;
    }
}
