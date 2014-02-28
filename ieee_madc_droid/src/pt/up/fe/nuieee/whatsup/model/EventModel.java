package pt.up.fe.nuieee.whatsup.model;

import java.util.Date;

public class EventModel {
	private String title;
	private String type;
	private String StudentBranch;
	private String[] tags;
	private Date date;
	
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
		return StudentBranch;
	}
	public void setStudentBranch(String studentBranch) {
		StudentBranch = studentBranch;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}

}
