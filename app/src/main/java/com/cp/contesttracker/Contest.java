package com.cp.contesttracker;

// Data Class(Model) to hold data for a single contest

import java.util.ArrayList;
import java.util.List;

public class Contest {
    private String name = null;
    private String time = null;
    private String onlineJudge = null;
    private String contestLink = null;

    public Contest(String name, String time, String onlineJudge, String contestLink) {
        this.name = name;
        this.time = time;
        this.onlineJudge = onlineJudge;
        this.contestLink = contestLink;
    }

    public Contest(String name, String time, String onlineJudge) {
        this.name = name;
        this.time = time;
        this.onlineJudge = onlineJudge;
    }


    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getOnlineJudge() {
        return onlineJudge;
    }

    public String getContestLink() {
        return contestLink;
    }


    public static List<Contest> getContestList(){
        List<Contest> temp = new ArrayList<>();

        temp.add(new Contest("Codeforces Round #566","06/06/23 | 8:30 PM","Codeforces"));
        temp.add(new Contest("Codeforces DIV-2 #567","07/06/23 | 8:30 PM","Codeforces"));
        temp.add(new Contest("Codeforces DIV-3 #568","08/06/23 | 8:30 PM","Codeforces"));
        temp.add(new Contest("AtCoder Beginner Contest","09/06/23 | 6:00 PM","AtCoder"));

        temp.add(new Contest("Codeforces Round #566","06/06/23 | 8:30 PM","Codeforces"));
        temp.add(new Contest("Codeforces DIV-2 #567","07/06/23 | 8:30 PM","Codeforces"));
        temp.add(new Contest("Codeforces DIV-3 #568","08/06/23 | 8:30 PM","Codeforces"));
        temp.add(new Contest("AtCoder Beginner Contest","09/06/23 | 6:00 PM","AtCoder"));

        temp.add(new Contest("Codeforces Round #566","06/06/23 | 8:30 PM","Codeforces"));
        temp.add(new Contest("Codeforces DIV-2 #567","07/06/23 | 8:30 PM","Codeforces"));
        temp.add(new Contest("Codeforces DIV-3 #568","08/06/23 | 8:30 PM","Codeforces"));
        temp.add(new Contest("AtCoder Beginner Contest","09/06/23 | 6:00 PM","AtCoder"));

        temp.add(new Contest("Codeforces Round #566","06/06/23 | 8:30 PM","Codeforces"));
        temp.add(new Contest("Codeforces DIV-2 #567","07/06/23 | 8:30 PM","Codeforces"));
        temp.add(new Contest("Codeforces DIV-3 #568","08/06/23 | 8:30 PM","Codeforces"));
        temp.add(new Contest("AtCoder Beginner Contest","09/06/23 | 6:00 PM","AtCoder"));

        return temp;
    }
}
