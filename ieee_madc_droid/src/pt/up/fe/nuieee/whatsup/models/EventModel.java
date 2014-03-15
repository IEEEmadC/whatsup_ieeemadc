package pt.up.fe.nuieee.whatsup.models;

import java.util.Calendar;

import android.text.format.DateFormat;


public class EventModel {
	
	private String title;
	private String type;
	private String studentBranch;
	private String[] tags;
	private String date;
	private String description;
	
	public EventModel() {

        Calendar calendar = Calendar.getInstance();
        date = DateFormat.format("dd-MM-yyyy", calendar.getTime()).toString();
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	private int points;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStudentBranch() {
		return studentBranch;
	}
	public void setStudentBranch(String studentBranch) {
		this.studentBranch = studentBranch;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}

}
