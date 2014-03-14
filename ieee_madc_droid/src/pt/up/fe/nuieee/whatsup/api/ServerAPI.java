package pt.up.fe.nuieee.whatsup.api;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pt.up.fe.nuieee.whatsup.models.EventModel;
import pt.up.fe.nuieee.whatsup.models.TopItemModel;

import com.google.gson.Gson;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class ServerAPI {

	private static DBCollection dbCollectionEvents;
	private static DBCollection dbCollectionUsers;

	public static void Connect() throws UnknownHostException
	{
        Mongo mongo = new Mongo(new ServerAddress("ds033559.mongolab.com", 33559));
        DB db = mongo.getDB("ieeewhatsup");
        db.authenticate("apk", "ieeeporto".toCharArray());
        dbCollectionEvents = db.getCollection("events");
        dbCollectionUsers = db.getCollection("users");
	}
	
	private static void checkCollections() throws UnknownHostException 
	{
		if (dbCollectionEvents == null || dbCollectionUsers == null)
		{
			Connect();
		}
	}
	
	public static boolean authenticateStudentBranch(String username, String password) throws UnknownHostException 
	{
		checkCollections();
		BasicDBObject authenticationObject = new BasicDBObject();
		authenticationObject.put("username", username);
		authenticationObject.put("password", password);
		
		return dbCollectionUsers.findOne(authenticationObject) == null;
	}
	
	private static List<DBObject> queryEventsList() throws UnknownHostException 
	{
		checkCollections();
		ArrayList<DBObject> events = new ArrayList<DBObject>(); 
		DBCursor eventsCursor = dbCollectionEvents.find();
		
		while (eventsCursor.hasNext()) {
			events.add(eventsCursor.next());
		}
		return events;
	}
	
	public static List<EventModel> getEvents() throws UnknownHostException {
		
		ArrayList<EventModel> events = new ArrayList<EventModel>();
		for (DBObject element : queryEventsList()) {
			
			String elementJson = ((BasicDBObject) element).toString();
			
			EventModel eventModel = new Gson().fromJson(elementJson, EventModel.class);
			
			events.add(eventModel);
		}
		
		return events;
	}
	
	private static List<DBObject> queryTopSBList() throws UnknownHostException 
	{
		checkCollections();
		ArrayList<DBObject> events = new ArrayList<DBObject>(); 

		BasicDBObject group = new BasicDBObject("_id", "$studentBranch");
		group.put("points", new BasicDBObject("$sum", "$points"));
		DBObject groupObject = new BasicDBObject("$group", group);
		
		BasicDBObject sort = new BasicDBObject();
		sort.put("points", -1);
		
		DBObject sortObject = new BasicDBObject("$sort", sort);
		
		AggregationOutput aggregationOutput = dbCollectionEvents.aggregate(groupObject, sortObject);
		
		Iterator<DBObject> cursor = aggregationOutput.results().iterator();
		
		while (cursor.hasNext()) {
			events.add(cursor.next());
		}
		return events;
	}
	
	public static List<TopItemModel> getTopSBs() throws UnknownHostException {
		
		ArrayList<TopItemModel> events = new ArrayList<TopItemModel>();
		for (DBObject element : queryTopSBList()) {
			
			String elementJson = ((BasicDBObject) element).toString().replaceFirst("_id", "studentBranchName");
			
			TopItemModel eventModel = new Gson().fromJson(elementJson, TopItemModel.class);
			
			events.add(eventModel);
		}
		
		return events;
	}
}
