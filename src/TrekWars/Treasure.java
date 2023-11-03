package TrekWars;

import java.awt.Color;
import java.util.Random;

import enigma.console.TextAttributes;

public class Treasure {
	private int myTime;
	private char myChar;
	private int coordx;
	private int coordy;
	

	private char numberOne;
	private char numberTwo;
	private char numberThree;
	private char numberFour;
	private char numberFive;
	private char warpDevice;
	private char trapDevice;
	//colors for each element of the game
	TextAttributes att1 = new TextAttributes(Color.GREEN, Color.BLACK);
	TextAttributes att2 = new TextAttributes(Color.BLUE, Color.BLACK);
	TextAttributes att3 = new TextAttributes(Color.PINK, Color.BLACK);
	TextAttributes att4 = new TextAttributes(Color.CYAN, Color.BLACK);
	TextAttributes att5 = new TextAttributes(Color.YELLOW, Color.BLACK);
	TextAttributes attTrap = new TextAttributes(Color.ORANGE, Color.BLACK);
	TextAttributes attWarp = new TextAttributes(Color.WHITE, Color.BLACK);
	TextAttributes attTrapActive = new TextAttributes(Color.ORANGE, Color.BLUE);
	TextAttributes attWarpActive = new TextAttributes(Color.WHITE, Color.RED);
	TextAttributes attTrap2 = new TextAttributes(Color.BLUE, Color.BLACK);
	TextAttributes attWarp2 = new TextAttributes(Color.RED, Color.BLACK);
	TextAttributes attSpace = new TextAttributes(Color.RED, Color.BLACK);	
	TextAttributes attComputer = new TextAttributes(Color.RED,Color.BLACK);
	//sets score for the computer
	public static int scoreTreasure(char c) {
		switch(c) {
		case '1':
			return 2;
		case '2':
			return 10;
		case '3':
			return 30;
		case '4':
			return 100;
		case '5':
			return 300;
		case '=':
			return 300;
		case '*':
			return 300;
		}
		return 0;
	}
	//for printing input queue and backpack with random colors
	public TextAttributes attribute(char c) {
		switch(c) {
		case '1':
			return att1;
		case '2':
			return att2;
		case '3':
			return att3;
		case '4':
			return att4;
		case '5':
			return att5;
		case '6':
			return att1;
		case '7':
			return att2;
		case '8':
			return att3;
		case '=':
			return attTrap;
		case '*':
			return attWarp;
		case ' ':
			return attSpace;
		case 'C':
			return attComputer;
		}
		switch(randomInput()) {
		case '1':
			return att1;
		case '2':
			return att2;
		case '3':
			return att3;
		case '4':
			return att4;
		case '5':
			return att5;
		case '6':
			return att1;
		case '7':
			return att2;
		case '8':
			return att3;
		case '=':
			return attTrap;
		case '*':
			return attWarp;
		case ' ':
			return attSpace;
		case 'C':
			return attComputer;
		}
		return att3;
	}
	public char getNumberOne() {
		return numberOne;
	}
	public char getNumberTwo() {
		return numberTwo;
	}
	public char getNumberThree() {
		return numberThree;
	}
	public char getNumberFour() {
		return numberFour;
	}
	public char getNumberFive() {
		return numberFive;
	}
	public char getWarpDevice() {
		return warpDevice;
	}
	public char getTrapDevice() {
		return trapDevice;
	}
	public void setNumberOne(char numberOne) {
		this.numberOne = numberOne;
	}
	public void setNumberTwo(char numberTwo) {
		this.numberTwo = numberTwo;
	}
	public void setNumberThree(char numberThree) {
		this.numberThree = numberThree;
	}
	public void setNumberFour(char numberFour) {
		this.numberFour = numberFour;
	}
	public void setNumberFive(char numberFive) {
		this.numberFive = numberFive;
	}
	public void setWarpDevice(char warpDevice) {
		this.warpDevice = warpDevice;
	}
	public void setTrapDevice(char trapDevice) {
		this.trapDevice = trapDevice;
	}
	//for printing input queue and backpack with random colors
	public char randomInput() 
	{
		Random rnd = new Random();
		int i = rnd.nextInt(8);		
			if(i == 0) 
				return '1';
			else if(i < 2)
				return '2';
			else if(i < 3)
				return '3';
			else if(i < 4) 
				return '4';
			else if(i < 5) 
				return '5';
			else if(i < 6) 
				return '=';
			else if(i < 7)
				return '*';
			else if(i < 8)
				return 'C';
		return '1';
	}
	public void setMyChar(char c) {
		myChar = c;
	}
	public char getMyChar() {
		return myChar;
	}
	public void setMyTime(int i) {
		myTime = i;
	}
	public int getMyTime() {
		return myTime;
	}
	//for flashing devices
	public TextAttributes myCurrentColor() {
		if(myTime %2 == 0) {
			if(myChar == '=')
				return attTrapActive;
			else
				return attWarpActive;
		}else {
			if(myChar == '=')
				return attTrap2;
			else
				return attWarp2;
		}
	}
	public int getCoordx() {
		return coordx;
	}
	public void setCoordx(int coordx) {
		this.coordx = coordx;
	}
	public int getCoordy() {
		return coordy;
	}
	public void setCoordy(int coordy) {
		this.coordy = coordy;
	}
}

