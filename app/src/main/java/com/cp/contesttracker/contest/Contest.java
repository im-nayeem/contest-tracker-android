package com.cp.contesttracker.contest;

// Data Class(Model) to hold data for a single contest


import com.cp.contesttracker.Utility;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Contest implements Serializable {
    private String id;
    private String name = null;
    private Date timeStamp = null;
    private String host = null;
    private long duration = 0;
    private String contestLink = null;
    private boolean isToday = false;

    private static String[] hostList =
            {"Codeforces", "Codechef", "Atcoder", "Topcoder", "Toph", "Leetcode", "Hackercup"};

    public Contest(String name, Date timeStamp, long duration, String host, String contestLink, String id) {
        this.name = name;
        this.timeStamp = timeStamp;
        this.duration = duration;
        this.host = host;
        this.contestLink = contestLink;
        this.isToday = Utility.isToday(timeStamp);
        this.id = id;
    }

    public Contest(String name, Date timeStamp, String host) {
        this.name = name;
        this.timeStamp = timeStamp;
        this.host = host;
    }


    public String getName() {
        return name;
    }

    public String getTimeString() {
        return Utility.formatTimeStamp(timeStamp);
    }

    public Date getTime() {
        return timeStamp;
    }

    public String getHost() {
        return host;
    }

    public String getContestLink() {
        return contestLink;
    }

    public boolean isToday() {
        return this.isToday;
    }

    public String getDuration() {
        long totalMinutes = this.duration / 60;
        long days = totalMinutes / (60 * 24);
        long hours = (totalMinutes % (60 * 24)) / 60;
        long minutes = (totalMinutes % (60 * 24)) % 60;

        StringBuilder durationString = new StringBuilder();
        if (days != 0) {
            durationString.append(days + " days, ");
        }
        if (hours != 0) {
            durationString.append(hours + " hours, ");
        }
        durationString.append(minutes + " minutes");

        return durationString.toString();
    }

    public String getId() {
        return id;
    }

    public static String[] getHostList() {
        return hostList;
    }

    public static HashMap<String, String> getHostNameToLink() {

        HashMap<String, String> hostNameToLink = new HashMap<>();

        hostNameToLink.put("Codeforces", "codeforces.com");
        hostNameToLink.put("Codechef", "codechef.com");
        hostNameToLink.put("Topcoder", "topcoder.com");
        hostNameToLink.put("Toph", "toph.co");
        hostNameToLink.put("Leetcode", "leetcode.com");
//        hostNameToLink.put("Codejam", "codingcompetitions.withgoogle.com/codejam");
        hostNameToLink.put("Hackercup", "facebook.com/hackercup");
//        hostNameToLink.put("Kickstart", "codingcompetitions.withgoogle.com/kickstart");
        hostNameToLink.put("Atcoder", "atcoder.jp");

        return hostNameToLink;
    }

    public static HashMap<String, String> getLinkToHostName() {

        HashMap<String, String> linkToHostName = new HashMap<>();

        HashMap<String, String> hostNametolink = getHostNameToLink();

        for (Map.Entry<String, String> entry : hostNametolink.entrySet()) {
            linkToHostName.put(entry.getValue(), entry.getKey());
        }

        return linkToHostName;
    }
}
