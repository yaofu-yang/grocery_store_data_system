package dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.DataSet;
import model.Entry;
import model.HolidayType;
import model.Visit;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.net.UnknownHostException;
import java.util.List;

public final class MetaDao {

    private static Datastore datastore;

    static {
        try {
            datastore = new Morphia().mapPackage("model").createDatastore(new MongoClient(), "CS5500");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private MetaDao(){
    }

    public static void truncateData() {
        datastore.getCollection(Entry.class).drop();
        datastore.getCollection(Visit.class).drop();
        datastore.getCollection(DataSet.class).drop();
    }

    // Here we use addAllEntries instead to match Visit class of back-end
    public static void addAllEntries(List<Visit> visits) {
        //Entry class is corresponding to "Visit" class from back-end
        List<Entry> mongoObjs = new ArrayList<>();
        for(Visit v : visits) {
            String holidayType = v.getEntryTime().getHolidayType().toString();
            Entry entry = new Entry(
                    v.getVisitID(),
                    v.getEntryTime().getLocalDateTime(),
                    v.getLeaveTime().getLocalDateTime(),
                    v.getDuration(),
                    holidayType, v.getEntryTime().getLocalDateTime().getDayOfWeek());
            mongoObjs.add(entry);
        }
        // use ORM here
        datastore.save(mongoObjs);
    }

    private static void addAllVisits(List<Visit> visits) {
        // datastore.save(visits);  // Previous code.

        // (Todo: James please review) I brought back the code from your earlier commit in DayDao
        // Todo: Because storing all of the values as a String is much easier to work with in
        // Todo: Postman due to my limited knowledge. We can definitely change it back later if
        // Todo: That is preferred. - Andy
        List<DBObject> mongoObjs = new ArrayList<>();
        for(Visit v : visits) {
            DBObject visitObj = new BasicDBObject()
                .append("visitID", v.getVisitID())
                .append("entryTime",v.getEntryTime().getLocalDateTime().toString())
                .append("leaveTime", v.getLeaveTime().getLocalDateTime().toString())
                .append("duration", v.getDuration())
                .append("holiday", v.getEntryTime().getHolidayType().toString())
                .append("dayOfWeek", v.getEntryTime().getLocalDateTime().getDayOfWeek().toString());
            mongoObjs.add(visitObj);
        }
        datastore.getCollection(Visit.class).insert(mongoObjs);  // Insert the visits.
        System.out.printf("successfully insert %d visits\n", visits.size());
    }

    public static void addAllDataSets(List<DataSet> dataSets) {
        for(DataSet ds:dataSets) {
            addAllVisits(ds.getVisits());
        }
        datastore.save(dataSets);
        System.out.printf("successfully insert %d datasets\n", dataSets.size());
    }
}



