package TrekWars;

public class Backpack {
	public Stack backpack = new Stack(8);
	///////////////////////////77
	Player player = new Player();
	Computer computer = new Computer();
	public int playerScore = 0;
	public int computerScore = 0;
	private int energy;
	
	public int getEnergy() {
		return energy;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	//adds items into the backpack
	boolean addItem(char item) {
		if(backpack.isFull()) {
			if((char) backpack.peek() == item && item != '=' && item != '*') {
				char addedItem = itemMatch(item);
				backpack.pop();
				if(addedItem != 'x') {
					backpack.push(addedItem);
				}
				return true;
			}
			return false;
	}
		if(backpack.isEmpty()) {
			if(item == '2' || item == '3' ||item == '4' ||item == '5' ||item == '*' ||item == '=') {
				backpack.push(item);
				getPoint(item, playerScore, computerScore);
			}
		}
		else
		{
			if(item == '*' || item == '=') {
				backpack.push(item);

			}
			else if((char) backpack.peek() == item) {
				char addedItem = itemMatch(item);
				backpack.pop();
				if(addedItem != 'x') {
					backpack.push(addedItem);
				}
			}
			else if(item == '2' || item == '3' ||item == '4' ||item == '5' ||item == '*' ||item == '=') {
				backpack.push(item);
			}	
			getPoint(item, playerScore, computerScore);
		}
		return true;
	}
	//For players energy
	char itemMatch(char item) {
		switch(item) {
			case '2':
				this.energy += 30;
				return 'x';
			case '3':
				return '=';
			case '4':
				this.energy += 240;
				return 'x';
			case '5':
				return '*';
		}
		return 'x';
	}
	//Sets the player's score
	void getPoint(char c, int playerScore, int computerScore) {
		switch(c) {
			case '1':
				player.setScore(player.getScore() + 1);
				break;
			case '2':
				player.setScore(player.getScore() + 5);
				break;
			case '3':
				player.setScore(player.getScore() + 15);
				break;
			case '4':
				player.setScore(player.getScore() + 50);
				break;
			case '5':
				player.setScore(player.getScore() + 150);
				computer.setScore(computerScore + 300);
				break;
			case '=':
				player.setScore(player.getScore());
				break;
			case '*':
				player.setScore(player.getScore());
				break;
			case 'C':
				player.setScore(player.getScore() + 300);
				computer.setScore(computerScore);
				break;

		}
	}
}
