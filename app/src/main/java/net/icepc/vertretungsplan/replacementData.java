package net.icepc.vertretungsplan;


public class replacementData {

    public int hour;
    public String originalTeacher;
    public String replacementTeacher;
    public String additionalInfo;
    public String subject;
    public String newRoom;
    public boolean canceled;

    public replacementData(int hour, String originalTeacher, String replacementTeacher, String additionalInfo, String subject, String newRoom, boolean canceled) {
        this.hour = hour;
        this.originalTeacher = originalTeacher;
        this.replacementTeacher = replacementTeacher;
        this.additionalInfo = additionalInfo;
        this.subject = subject;
        this.newRoom = newRoom;
        this.canceled = canceled;
    }
}
