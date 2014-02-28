package pt.up.fe.nuieee.whatsup.api;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class ServerAPI {

	private static DBCollection dbCollectionActivities;
	private static DBCollection dbCollectionUsers;

	public static void Connect() throws UnknownHostException
	{
        Mongo mongo = new Mongo(new ServerAddress("ds033559.mongolab.com", 33559));
        DB db = mongo.getDB("ieeewhatsup");
        db.authenticate("admin", "ieeeporto".toCharArray());
        dbCollectionActivities = db.getCollection("activities");
        dbCollectionUsers = db.getCollection("users");
	}
	
	private static void checkCollections() throws UnknownHostException 
	{
		if (dbCollectionActivities == null || dbCollectionUsers == null)
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
	
	public static ArrayList<DBObject> queryActivitiesList() throws UnknownHostException 
	{
		checkCollections();
		ArrayList<DBObject> activities = new ArrayList<DBObject>(); 
		DBCursor activitiesCursor = dbCollectionActivities.find();
		
		while (activitiesCursor.hasNext()) {
			activities.add(activitiesCursor.next());
		}
		return activities;
	}
}
