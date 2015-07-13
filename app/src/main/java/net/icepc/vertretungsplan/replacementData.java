package net.icepc.vertretungsplan;


public class replacementData {

    public String hour;
    public String course;
    public String teacher;
    public String originalRoom;
    public String replacementRoom;
    public String additionalInfo;
    public String subject;
    public boolean canceled;

    public replacementData(String course, String teacher, String hour, String replacementRoom, String originalRoom, String subject, String additionalInfo, boolean canceled) {
        this.hour = hour;
        this.course = course;
        this.teacher = teacher;
        this.additionalInfo = additionalInfo;
        this.originalRoom = originalRoom;
        this.replacementRoom = replacementRoom;
        this.subject = subject;
        this.canceled = canceled;
    }
}
