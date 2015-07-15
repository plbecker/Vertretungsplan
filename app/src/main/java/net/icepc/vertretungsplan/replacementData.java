package net.icepc.vertretungsplan;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class replacementData {

    public String hour;
    public String course;
    public String teacher;
    public String originalRoom;
    public String replacementRoom;
    public String additionalInfo;
    public String subject;
    public int canceled;

    public replacementData(String course, String teacher, String hour, String replacementRoom, String originalRoom, String subject, String additionalInfo, int canceled) {
        this.hour = hour;
        this.course = course;
        this.teacher = teacher;
        this.additionalInfo = additionalInfo;
        this.originalRoom = originalRoom;
        this.replacementRoom = replacementRoom;
        this.subject = subject;
        this.canceled = canceled;
    }

    @Override
    public boolean equals(Object o) {
        replacementData rD = (replacementData) o;
        if (rD.replacementRoom.equals(replacementRoom) && rD.course.equals(course) && rD.teacher.equals(teacher) && rD.subject.equals(subject) && rD.originalRoom.equals(originalRoom) && rD.canceled == canceled && rD.hour.equals(hour) && rD.additionalInfo.equals(additionalInfo)) return true;
        return false;
    }

    public String toString() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("hour", this.hour);
            obj.put("course", this.course);
            obj.put("teacher", this.teacher);
            obj.put("originalRoom", this.originalRoom);
            obj.put("replacementRoom", this.replacementRoom);
            obj.put("additionalInfo", this.additionalInfo);
            obj.put("subject", this.subject);
            obj.put("canceled", this.canceled);
            return obj.toString();
        }
        catch (JSONException j){
            Log.d("JSON Exc ",j+"");
            return null;
        }
    }
}
