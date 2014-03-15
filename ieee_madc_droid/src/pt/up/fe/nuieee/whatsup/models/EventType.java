package pt.up.fe.nuieee.whatsup.models;

public enum EventType {

	Workshop(250),
	Conference(2000),
	Talk(700),
	Generic(100);
	
	public int points;
	
	EventType(int points) {
		this.points = points;
	}
}
