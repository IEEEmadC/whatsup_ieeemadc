package pt.up.fe.nuieee.whatsup.api;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pt.up.fe.nuieee.whatsup.models.EventModel;
import pt.up.fe.nuieee.whatsup.models.EventType;
import pt.up.fe.nuieee.whatsup.models.TopItemModel;

import com.google.gson.Gson;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;

public class ServerAPI {

	public static enum Actions {
		getEvents,
		getTopSBs,
		authenticateStudentBranch,
		newStudentBranch,
		newEvent,
		getEventsOfSB
	}

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

	public static Boolean authenticateStudentBranch(String username, String password) throws UnknownHostException 
	{
		checkCollections();
		BasicDBObject authenticationObject = new BasicDBObject();
		authenticationObject.put("name", username);
		authenticationObject.put("password", password);

		DBObject resultObject = dbCollectionUsers.findOne(authenticationObject); 
		return resultObject != null;
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
	
	private static List<DBObject> queryEventsOfSBList(String sb) throws UnknownHostException 
	{
		checkCollections();
		ArrayList<DBObject> events = new ArrayList<DBObject>();
		BasicDBObject doc = new BasicDBObject("studentBranch", sb);
		DBCursor eventsCursor = dbCollectionEvents.find(doc);

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
	
	public static List<EventModel> getEventsOfSB(String sb) throws UnknownHostException {

		ArrayList<EventModel> events = new ArrayList<EventModel>();
		for (DBObject element : queryEventsOfSBList(sb)) {

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

			String elementJson = ((BasicDBObject) element).toString().replaceFirst("_id", "studentBranch");
					
			TopItemModel eventModel = new Gson().fromJson(elementJson, TopItemModel.class);

			events.add(eventModel);
		}

		return events;
	}

	public static Boolean createSB(String name, String location, String email, String password) throws Exception {

		checkCollections();
		BasicDBObject authenticationObject = new BasicDBObject();
		authenticationObject.put("name", name);
		authenticationObject.put("password", password);

		DBObject resultObject = dbCollectionUsers.findOne(authenticationObject); 
		if (resultObject != null) {
			throw new Exception("SB already registered");
		}
		BasicDBObject doc = new BasicDBObject("name", name);
		doc.put("location", location);
		doc.put("email", email);
		doc.put("password", password);

		WriteResult result = dbCollectionUsers.insert(doc);
		return result.getError() == null;
	}

	public static Boolean newEvent(String title, String sb, String type, String tags, String date, String description) throws Exception {

		checkCollections();

		BasicDBObject doc = new BasicDBObject("title", title);
		doc.put("studentBranch", sb);
		doc.put("type", type);
		doc.put("tags", tags.split(","));
		doc.put("date", date);
		doc.put("description", description);
		doc.put("points", EventType.valueOf(type).points);


		WriteResult result = dbCollectionEvents.insert(doc);
		return result.getError() == null;
	}
}
