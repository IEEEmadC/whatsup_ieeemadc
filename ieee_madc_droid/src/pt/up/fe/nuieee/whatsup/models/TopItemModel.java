package pt.up.fe.nuieee.whatsup.models;

public class TopItemModel {
	
	private String studentBranchName;
	private int ranking;
	private int points;
	
	public String getStudenBranchName() {
		return studentBranchName;
	}
	public void setStudentBranchName(String studentBranchName) {
		this.studentBranchName = studentBranchName;
	}
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int score) {
		this.points = score;
	}
	

}
