package pt.up.fe.nuieee.whatsup.models;

public class TopItemModel {
	
	private String studentBranch;
	private int ranking;
	private int points;
	
	public String getStudenBranchName() {
		return studentBranch;
	}
	public void setStudentBranchName(String studentBranch) {
		this.studentBranch = studentBranch;
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
