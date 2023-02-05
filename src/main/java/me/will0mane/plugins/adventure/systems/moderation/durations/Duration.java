package me.will0mane.plugins.adventure.systems.moderation.durations;

public record Duration(long startMillis, long duration) {

    public static final Duration PERMANENT = new Duration(-1, -1);

    public long millisEnd(){
        if(startMillis == -1 && duration == -1) return -1;
        return startMillis + getMillisDuration();
    }

    public long getMillisDuration(){
        return duration * 50;
    }

}
