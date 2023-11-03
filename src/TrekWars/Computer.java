package TrekWars;

public class Computer {
	private char computer;
	private int score;
	private int[] coords;
	private int c_score = 0;
	private boolean frozen;
	public char getComputer() {
		return computer;
	}

	public boolean isfrozen() {
		return frozen;
	}

	public void setfrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public int[] getCoords() {
		return coords;
	}

	public void setCoords(int[] coords) {
		this.coords = coords;
	}

	public int getC_score() {
		return c_score;
	}

	public void setC_score(int c_score) {
		this.c_score = c_score;
	}

	public void setComputer(char computer) {
		this.computer = computer;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void computerScore(int scoreValue) {
		score += scoreValue;
	}
	
}
