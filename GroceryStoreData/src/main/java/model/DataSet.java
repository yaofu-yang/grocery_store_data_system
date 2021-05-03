package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Entity("DataSets")
public class DataSet {

    @Id
    private ObjectId id = new ObjectId();
    private Month month;
    private LocalDateTime generatedTime;

    @Reference
    private List<Visit> visits;

    public DataSet() {
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public LocalDateTime getGeneratedTime() {
        return generatedTime;
    }

    public void setGeneratedTime(LocalDateTime generatedTime) {
        this.generatedTime = generatedTime;
    }
}
