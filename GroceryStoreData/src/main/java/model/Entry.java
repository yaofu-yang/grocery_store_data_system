package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

// we can determine the collection name later, which need exactly matching backend's.
@Entity(value="Visits", noClassnameStored = true)
public class Entry {

    @Id
    private ObjectId id = new ObjectId();

    private String visitID;

    private Date entryTime;
    private Date leaveTime;
    private int duration;
    private String holiday;
    private DayOfWeek dayOfWeek;

    private String _class = "com.grocery.sprint3.model.Visit";

    public Entry(String visitID, LocalDateTime entryTime, LocalDateTime leaveTime, int duration, String holiday, DayOfWeek dayOfWeek) {

        this.visitID = visitID;
        this.entryTime = Date.from(entryTime.atZone(ZoneId.systemDefault()).toInstant());
        this.leaveTime = Date.from(leaveTime.atZone(ZoneId.systemDefault()).toInstant());
        this.duration = duration;
        this.holiday = holiday;
        this.dayOfWeek = dayOfWeek;

    }
}
