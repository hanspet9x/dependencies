package tbs.io;

import java.util.List;

public class Example {


    public class Country{
        private int id;
        private String name;
        //@OneToOne
        President president;
        //OneToMany
        private List<States> states;
        //OneToMany
        private List<MobileCompany> companies;

    }

    public class States{
        private int id;
        private String name;
        Governor governor;
        private List<LocalGovernment> localGovernment;

    }

    public class LocalGovernment{
        private int id;
        private String name;
        private Chairman chairman;

    }

    public class President{
        private int id;
        private String name;

    }

    public class Governor{
        private int id;
        private String name;

    }

    public class Chairman{
        private int id;
        private String name;

    }

    public class WorldMeeting{
        private int id;
        private String meetingName;
        List<President> presidents;
    }

    public class MobileCompany{
        private int id;
        //@ManyToMany

    }

}