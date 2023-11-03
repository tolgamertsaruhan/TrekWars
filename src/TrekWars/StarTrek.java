package TrekWars;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;
import enigma.console.TextAttributes;
import enigma.core.Enigma;

public class StarTrek {
	int immune = 80;
	int immune2 = 0;
	int coordx = 0;
	int coordy = 0;
	double[][] scores = new double[23][55];
	public char[][] Computers = new char[23][55];
	enigma.console.Console cn = Enigma.getConsole("Game", 80, 30, 20, 2);
	public Treasure[] activeDevices = new Treasure[1000];
	TextAttributes attWall = new TextAttributes(Color.gray, Color.LIGHT_GRAY);
	TextAttributes attPlayer = new TextAttributes(Color.MAGENTA, Color.BLACK);
	TextAttributes attGround = new TextAttributes(Color.LIGHT_GRAY, Color.BLACK);
	TextAttributes attSpace = new TextAttributes(Color.RED,Color.RED);
	TextAttributes attSpaceBlue = new TextAttributes(Color.BLUE,Color.BLUE);
	TextAttributes attImmune = new TextAttributes(Color.GREEN,Color.BLACK);
	TextAttributes attBusted = new TextAttributes(Color.WHITE,Color.BLUE);
	Treasure treasure = new Treasure();
	public static KeyListener klis; 
	public static int keypr;   // key pressed?
	public static int rkey;    // key   (for press/release)
	Player computer = new Player();
	Player player = new Player();
	
	//prints input queue, first 20 element, one player and one computer
	public char[][] printInputQueue(CircularQueue inputQueue, char[][] _map,int amount){
		Random rnd = new Random();
		if(inputQueue.isEmpty()) {
			for(int i = 0; i < 15; i++) 
			{
				inputQueue.enqueue(randomInput());
			}
		}

		cn.getTextWindow().setCursorPosition(60, 2);

		for(int i = 0; i < 15;i++) {
			if(!inputQueue.isEmpty()) {
				cn.setTextAttributes(treasure.attribute((char) inputQueue.peek()));
				System.out.print(inputQueue.peek());
			}
			inputQueue.enqueue(inputQueue.dequeue());
		}
		cn.setTextAttributes(treasure.attribute((char) inputQueue.peek()));
		cn.getTextWindow().setCursorPosition(60, 3);
		System.out.print("<<<<<<<<<<<<<<<");
		cn.getTextWindow().setCursorPosition(60, 1);
		System.out.print("<<<<<<<<<<<<<<<");
		cn.getTextWindow().setCursorPosition(0, 0);

		for(int i = 0; i < amount; i++) {
			char topElements = (char) inputQueue.peek();
			int rndPlaceY = rnd.nextInt(1, 22);
			int rndPlaceX = rnd.nextInt(1, 55);
			do {
				rndPlaceY = rnd.nextInt(1, 22);
				rndPlaceX = rnd.nextInt(1, 55);
				if(_map[rndPlaceY][rndPlaceX] == ' ') {
					if((char)inputQueue.peek() == 'C') {
						if(Computers[rndPlaceY][rndPlaceX] != 'C' && Computers[rndPlaceY][rndPlaceX] != 'F') {
							Computers[rndPlaceY][rndPlaceX] = 'C';
						}else {
							rndPlaceY = 0;
							rndPlaceX = 0;
						}
					}
					inputQueue.dequeue();
					char x = randomInput();
					inputQueue.enqueue(x);
					
				}
			}while(_map[rndPlaceY][rndPlaceX] != ' ');
			if(topElements != 'C')
				_map = cursorPrinter(rndPlaceX,rndPlaceY,_map,topElements,treasure.attribute(topElements),attWall);
			
		}
		return _map;
	}
	public char randomInput() 
	{
		Random rnd = new Random();
		int i = rnd.nextInt(40);		
			if(i < 12) 
				return '1';
			else if(i < 20)
				return '2';
			else if(i < 26)
				return '3';
			else if(i < 31) 
				return '4';
			else if(i < 35) 
				return '5';
			else if(i < 37) 
				return '=';
			else if(i < 38)
				return '*';
			else if(i < 40)
				return 'C';
		return 'a';
	}
	//main function
	public void gamePlay() throws IOException, InterruptedException {
		player.setlife(5);
		char adoptedChar = 'Y';
		int adoptedX =0;
		int adoptedY =0;
		Backpack backPack = new Backpack();
		Random rnd = new Random();
		Map map = new Map();
		CircularQueue inputQueue = new CircularQueue(15);
		char[][] _map = map.readMap();
		boolean again = true;
		boolean again2 = true;
		for(int i = 0; i < Computers.length; i++) {
			for(int j = 0; j < Computers[0].length; j++) {
				Computers[i][j] = ' ';
			}
		}
		backPack.setEnergy(50);

		int waitTime = 10;

		while(again) {
			coordx = rnd.nextInt(0,55);
			coordy = rnd.nextInt(0,23);
			if(_map[coordy][coordx] == ' ') {
				_map[coordy][coordx] = 'P';
				again = false;
			}
		}


		cn.setTextAttributes(attWall);
		
		
		int gameTime = 0;
		int playertime = 0;
		boolean time = true;
		while(again2) {
			int coordx2 = rnd.nextInt(0,55);
			int coordy2 = rnd.nextInt(0,23);
			if(_map[coordy2][coordx2] == ' ') {
				Computers[coordy2][coordx2] = 'C';
				again2 = false;
			}
		}
		_map = printInputQueue(inputQueue, _map,19);
		
		printMap(Computers);
		printMap(_map);
		scores = ScoreMap(_map);
		boolean switchImmune = false;
		while(player.getlife() >= 1) {
			if(gameTime %20 == 0 && backPack.getEnergy() > 0) {
				waitTime = 5;
				backPack.setEnergy(backPack.getEnergy()-1);
			}
			else if(backPack.getEnergy() <= 0){
				waitTime = 10;
			}
			if(immune > 0) {
				if(immune %4 == 0) {
					switchImmune = !switchImmune;

					if(switchImmune)
						_map = cursorPrinter(coordx,coordy,_map,'P',attPlayer,attWall);
					else
						_map = cursorPrinter(coordx,coordy,_map,'P',attImmune,attWall);
				}
			}
			if(gameTime %3 == 0)
			{
				immune--;
				immune2--;
			}

			if(gameTime %20 == 0) 
			{
				//about computer
				scores = ScoreMap(_map);

				for(int i = 0;i<activeDevices.length;i++) {
					if(activeDevices[i] != null && activeDevices[i].getMyTime() > 0) {
						activeDevices[i].setMyTime(activeDevices[i].getMyTime()-1);
						cursorPrinter(activeDevices[i].getCoordx(),activeDevices[i].getCoordy(),_map,activeDevices[i].getMyChar(),activeDevices[i].myCurrentColor(),attGround);
						for(int j = -1; j<2;j++) {
							for(int k = -1; k<2;k++) {
								if(_map[j+activeDevices[i].getCoordy()][activeDevices[i].getCoordx()+k] != '#' && _map[j+activeDevices[i].getCoordy()][activeDevices[i].getCoordx()+k] != 'P' &&  !(k == 0 && j == 0)) {
									if(activeDevices[i].getMyTime() %2 == 0 && activeDevices[i].getMyChar() == '*') {
										cursorPrinter(activeDevices[i].getCoordx()+k,activeDevices[i].getCoordy()+j,_map,' ',attSpace,attGround);
										if(Computers[activeDevices[i].getCoordy()+j][activeDevices[i].getCoordx()+k] == 'C') {
											Computers[activeDevices[i].getCoordy()+j][activeDevices[i].getCoordx()+k] = ' ';
										}
										
										_map[j+activeDevices[i].getCoordy()][activeDevices[i].getCoordx()+k] = ' ';

									}else if(activeDevices[i].getMyChar() == '*') {
										cursorPrinter(activeDevices[i].getCoordx()+k,activeDevices[i].getCoordy()+j,_map,' ',attGround,attGround);
										if(Computers[activeDevices[i].getCoordy()+j][activeDevices[i].getCoordx()+k] == 'C') {
											Computers[activeDevices[i].getCoordy()+j][activeDevices[i].getCoordx()+k] = ' ';
										}
										_map[j+activeDevices[i].getCoordy()][activeDevices[i].getCoordx()+k] = ' ';

									}
									else if(activeDevices[i].getMyTime() %2 == 0 && activeDevices[i].getMyChar() == '='){
										cursorPrinter(activeDevices[i].getCoordx()+k,activeDevices[i].getCoordy()+j,_map,' ',attSpaceBlue,attGround);
										if(Computers[activeDevices[i].getCoordy()+j][activeDevices[i].getCoordx()+k] == 'C') {
											Computers[activeDevices[i].getCoordy()+j][activeDevices[i].getCoordx()+k] = 'F';
											cursorPrinter(activeDevices[i].getCoordx()+k,activeDevices[i].getCoordy()+j,_map,'C',attBusted,attGround);
										}
										if(Computers[activeDevices[i].getCoordy()+j][activeDevices[i].getCoordx()+k] == 'F') 
											cursorPrinter(activeDevices[i].getCoordx()+k,activeDevices[i].getCoordy()+j,_map,'C',attBusted,attGround);
									}
									else {
										cursorPrinter(activeDevices[i].getCoordx()+k,activeDevices[i].getCoordy()+j,_map,_map[activeDevices[i].getCoordy()+j][activeDevices[i].getCoordx()+k],attGround,attGround);
										if(Computers[activeDevices[i].getCoordy()+j][activeDevices[i].getCoordx()+k] == 'C') {
											Computers[activeDevices[i].getCoordy()+j][activeDevices[i].getCoordx()+k] = 'F';
											cursorPrinter(activeDevices[i].getCoordx()+k,activeDevices[i].getCoordy()+j,_map,'C',attGround,attGround);
										}
										if(Computers[activeDevices[i].getCoordy()+j][activeDevices[i].getCoordx()+k] == 'F') 
											cursorPrinter(activeDevices[i].getCoordx()+k,activeDevices[i].getCoordy()+j,_map,'C',attBusted,attGround);
									}
									
								}
							}
						}
					}
					else if(activeDevices[i] != null && activeDevices[i].getMyTime() <= 0) {
						cursorPrinter(activeDevices[i].getCoordx(),activeDevices[i].getCoordy(),_map,' ',attGround,attGround);
						for(int j = -1; j<2;j++) {
							for(int k = -1; k<2;k++) {
								if(_map[j+activeDevices[i].getCoordy()][activeDevices[i].getCoordx()+k] == ' ' && !(k == 0 && j == 0)) {		
										cursorPrinter(activeDevices[i].getCoordx()+k,activeDevices[i].getCoordy()+j,_map,' ',attGround,attGround);
										
								}
								if(Computers[activeDevices[i].getCoordy()+j][activeDevices[i].getCoordx()+k] == 'F') {
									Computers[activeDevices[i].getCoordy()+j][activeDevices[i].getCoordx()+k] = 'C';
								}
							}
						}
						activeDevices[i] = null;
					}

				}
				
			}
			for(int i = 0; i < Computers.length;i ++) {
				for(int j = 0; j < Computers[0].length;j++) {
					if(Computers[i][j] == 'C') {

					if(_map[i][j] != 'P' && _map[i][j] != '#')
					{
						computer.setScore(computer.getScore()+treasure.scoreTreasure(_map[i][j]));
						_map[i][j] = ' ';
					}else if(_map[i][j] == 'P' && immune < 0) {
						player.setlife(player.getlife()-1);
						immune = 40;
					}
						for(int k = -1; k < 2; k++) {
							for(int g = -1; g < 2;g++) {
								if(_map[i+k][j+g] == 'P' && immune2 <= 0) {
									if(!backPack.backpack.isEmpty())
										backPack.backpack.pop();
									if(!backPack.backpack.isEmpty())
										backPack.backpack.pop();
									immune2 = 20;
								}
							}
						}
					}
					else if(Computers[i][j] == 'F') {
						cursorPrinter(j,i,_map,'C',attGround,attGround);
					}
				}
			}
			if(gameTime %10 == 0) {
				for(int i = 0; i < Computers.length;i ++) {
					for(int j = 0; j < Computers[0].length;j++) {
						if(Computers[i][j] == 'C') {
							double x1 = scores[i][j+1];
							double x2 = scores[i][j-1];
							double y1 = scores[i+1][j];
							double y2 = scores[i-1][j];
							
							if(x1 > x2 && x1 > y1 && x1 > y2 && x1 > 0 && Computers[i][j+1] == ' ') {
								cursorPrinter(j,i,_map,' ', attGround,attGround);
								int[] coord = {j+1,i};
								Computers[i][j] = ' ';
								Computers[coord[1]][coord[0]] = 'M';
								cursorPrinter(coord[0],coord[1],_map,'C', treasure.attribute('C'),attGround);
							}
							else if(x2 > y1 && x2 > y2 && x2 > 0&& Computers[i][j-1] == ' ') {
								cursorPrinter(j,i,_map,' ', attGround,attGround);
								int[] coord = {j-1,i};
								Computers[i][j] = ' ';
								Computers[coord[1]][coord[0]] = 'M';

								cursorPrinter(coord[0],coord[1],_map,'C', treasure.attribute('C'),attGround);
							}
							else if(y1 > y2 && y1 > 0 && Computers[i+1][j] == ' ') {
								cursorPrinter(j,i,_map,' ', attGround,attGround);
								int[] coord = {j,i+1};
								Computers[i][j] = ' ';
								Computers[coord[1]][coord[0]] = 'M';
								cursorPrinter(coord[0],coord[1],_map,'C', treasure.attribute('C'),attGround);
							}
							else if(y2 > 0 && Computers[i-1][j] == ' '){
								cursorPrinter(j,i,_map,' ', attGround,attGround);
								int[] coord = {j,i-1};
								Computers[i][j] = ' ';
								Computers[coord[1]][coord[0]] = 'M';
								cursorPrinter(coord[0],coord[1],_map,'C', treasure.attribute('C'),attGround);
							}

							
							
						}
					}
				}
				for(int i = 0; i < Computers.length;i ++) {
					for(int j = 0; j < Computers[0].length;j++) {
						if(Computers[i][j] == 'M') {
							Computers[i][j] = 'C';
						}
					}
				}
			}
			
			if(gameTime %60 == 0)
				_map = printInputQueue(inputQueue, _map,1);
				
			
			if(gameTime %3 == 0)
				printBackpack(backPack.backpack);

			klis=new KeyListener() {
		        public void keyTyped(KeyEvent e) {}
		        public void keyPressed(KeyEvent e) {
		           if(keypr==0) {
		              keypr=1;
		              rkey=e.getKeyCode();
		           }
		        }
		        public void keyReleased(KeyEvent e) {}
		     };
		     cn.getTextWindow().addKeyListener(klis);
		     klis=new KeyListener() {
		         public void keyTyped(KeyEvent e) {}
		         public void keyPressed(KeyEvent e) {
		            if(keypr==0) {
		               keypr=1;
		               rkey=e.getKeyCode();
		            }
		         }
		         public void keyReleased(KeyEvent e) {}
		      };
			gameTime++;
			playertime++;
			if(adoptedChar != 'Y' && (coordx != adoptedX || coordy != adoptedY)) {
				_map = cursorPrinter(adoptedX,adoptedY,_map,adoptedChar,treasure.attribute(adoptedChar),attWall);			
				adoptedChar = 'Y';
			}
			if(playertime >= waitTime) {
				playertime = 0;
				//for player movements
				if(keypr == 1) {
					switch(rkey) {
					case KeyEvent.VK_UP:
						if(_map[coordy-1][coordx] != '#' && _map[coordy-1][coordx] != 'C') {
							if(_map[coordy-1][coordx] != ' ') {
								if(!backPack.addItem(_map[coordy-1][coordx])) {
									adoptedChar = _map[coordy-1][coordx];
									adoptedX = coordx;
									adoptedY = coordy-1;
								}else {
									_map[coordy-1][coordx] = ' ';
								}
							}
							_map = cursorPrinter(coordx,coordy,_map,' ',attPlayer,attWall);
							coordy--;
							_map = cursorPrinter(coordx,coordy,_map,'P',attPlayer,attWall);
						}	
						break;
					case KeyEvent.VK_DOWN:
						if(_map[coordy+1][coordx] != '#'&& _map[coordy+1][coordx] != 'C') {
							if(_map[coordy+1][coordx] != ' ') {
								 if(!backPack.addItem(_map[coordy+1][coordx])) {
									adoptedChar = _map[coordy+1][coordx];
									adoptedX = coordx;
									adoptedY = coordy+1;
								}else {
									_map[coordy+1][coordx] = ' ';
								}									
								
							}
							_map = cursorPrinter(coordx,coordy,_map,' ',attPlayer,attWall);
							coordy++;
							_map = cursorPrinter(coordx,coordy,_map,'P',attPlayer,attWall);
						}	
						break;
					case KeyEvent.VK_RIGHT:
						if(_map[coordy][coordx+1] != '#'&& _map[coordy][coordx+1] != 'C') {
							if(_map[coordy][coordx+1] != ' ') {
								if(!backPack.addItem(_map[coordy][coordx+1])) {
									adoptedChar = _map[coordy][coordx+1];
									adoptedX = coordx+1;
									adoptedY = coordy;
								}else {
									_map[coordy][coordx+1] = ' ';
								}
							}
							_map = cursorPrinter(coordx,coordy,_map,' ',attPlayer,attWall);
							coordx++;
							_map = cursorPrinter(coordx,coordy,_map,'P',attPlayer,attWall);
						}	
						break;
					case KeyEvent.VK_LEFT:
						if(_map[coordy][coordx-1] != '#' && _map[coordy][coordx-1] != 'C') {
							if(_map[coordy][coordx-1] != ' ') {
								if(!backPack.addItem(_map[coordy][coordx-1])) {
									adoptedChar = _map[coordy][coordx-1];
									adoptedX = coordx-1;
									adoptedY = coordy;
								}else {
									_map[coordy][coordx-1] = ' ';
								}
							}
							_map = cursorPrinter(coordx,coordy,_map,' ',attPlayer,attWall);
							coordx--;
							_map = cursorPrinter(coordx,coordy,_map,'P',attPlayer,attWall);
						}	
						break;

					case KeyEvent.VK_W:
						if(backPack.backpack.isEmpty()) {
							break;
						}
						if(_map[coordy-1][coordx] == ' ') {
							if((char)backPack.backpack.peek() == '*'||(char)backPack.backpack.peek() =='=') {
								cursorPrinter(coordx,coordy-1,_map,(char)backPack.backpack.peek(),treasure.attribute((char) backPack.backpack.peek()),attWall);
								for(int i = 0; i<activeDevices.length;i++) {
									if(activeDevices[i] == null) {
										Treasure newTreasure = new Treasure();
										newTreasure.setMyChar((char) backPack.backpack.peek());
										newTreasure.setCoordx(coordx);
										newTreasure.setCoordy(coordy-1);
										newTreasure.setMyTime(25);
										activeDevices[i] = newTreasure;
										break;
									}
								}
							}
							backPack.backpack.pop();
						}	
						break;
					case KeyEvent.VK_S:
						if(backPack.backpack.isEmpty()) {
							break;
						}
						if(_map[coordy+1][coordx] == ' ') {
							if((char)backPack.backpack.peek() == '*'||(char)backPack.backpack.peek() =='=') {
								cursorPrinter(coordx,coordy+1,_map,(char)backPack.backpack.peek(),treasure.attribute((char) backPack.backpack.peek()),attWall);
								for(int i = 0; i<activeDevices.length;i++) {
									if(activeDevices[i] == null) {
										Treasure newTreasure = new Treasure();
										newTreasure.setMyChar((char) backPack.backpack.peek());
										newTreasure.setCoordx(coordx);
										newTreasure.setCoordy(coordy+1);
										newTreasure.setMyTime(25);
										activeDevices[i] = newTreasure;
										break;
									}
								}
							}
							backPack.backpack.pop();
						}	
						break;
						
					case KeyEvent.VK_D:
						if(backPack.backpack.isEmpty()) {
							break;
						}
						if(_map[coordy][coordx+1] == ' ') {
							if((char)backPack.backpack.peek() == '*'||(char)backPack.backpack.peek() =='=') {
								cursorPrinter(coordx+1,coordy,_map,(char)backPack.backpack.peek(),treasure.attribute((char) backPack.backpack.peek()),attWall);
								for(int i = 0; i<activeDevices.length;i++) {
									if(activeDevices[i] == null) {
										Treasure newTreasure = new Treasure();
										newTreasure.setMyChar((char) backPack.backpack.peek());
										newTreasure.setCoordx(coordx+1);
										newTreasure.setCoordy(coordy);
										newTreasure.setMyTime(25);
										activeDevices[i] = newTreasure;
										break;
									}
								}
							}
							backPack.backpack.pop();
						}	
						break;
					case KeyEvent.VK_A:
						if(backPack.backpack.isEmpty()) {
							break;
						}
						if(_map[coordy][coordx-1] == ' ') {
							if((char)backPack.backpack.peek() == '*'||(char)backPack.backpack.peek() =='=') {
								cursorPrinter(coordx-1,coordy,_map,(char)backPack.backpack.peek(),treasure.attribute((char) backPack.backpack.peek()),attWall);
								for(int i = 0; i<activeDevices.length;i++) {
									if(activeDevices[i] == null) {
										Treasure newTreasure = new Treasure();
										newTreasure.setMyChar((char) backPack.backpack.peek());
										newTreasure.setCoordx(coordx-1);
										newTreasure.setCoordy(coordy);
										newTreasure.setMyTime(25);
										activeDevices[i] = newTreasure;
										break;
									}
								}
							}
							backPack.backpack.pop();
						}	
						break;
					case KeyEvent.VK_F:
						scores = ScoreMap(_map);
						int a = computer.getScore();
						cn.getTextWindow().setCursorPosition(68, 24);
						System.out.println(a);
						break;
					}
					
				}
				keypr= 0;
			}

			//scores
			if(gameTime % 10 == 0) {
				_map = randomMovement(_map);
				cn.getTextWindow().setCursorPosition(62, 16);
				System.out.print("P. Score: ");
				if(!backPack.backpack.isEmpty()) {
					cn.setTextAttributes(treasure.attribute((char)backPack.backpack.peek()));

				}
				cn.setTextAttributes(attGround);
				System.out.print(backPack.player.getScore());
				cn.getTextWindow().setCursorPosition(62, 17);
				if(backPack.getEnergy() > 100)
					System.out.print("P. Power: " + backPack.getEnergy()+ "");
				else if(backPack.getEnergy() > 10)
					System.out.print("P. Power: " + backPack.getEnergy()+ " ");
				else
					System.out.print("P. Power: " + backPack.getEnergy()+ "  ");

				cn.getTextWindow().setCursorPosition(62, 18);
				System.out.print("P. Life : " + player.getlife());
				cn.getTextWindow().setCursorPosition(62, 20);
				System.out.print("C. Score: " + computer.getScore());
				cn.setTextAttributes(attGround);
			}
			
			Thread.sleep(50);
		}
		cn.getTextWindow().setCursorPosition(62, 17);

		System.out.print("GAME OVER");	
		cn.getTextWindow().setCursorPosition(62, 18);
		System.out.print("P. Life: DEAD");
		cn.getTextWindow().setCursorPosition(62, 20);
		System.out.print("End Score: " + (backPack.player.getScore()-computer.getScore()));
		cn.setTextAttributes(attGround);
	

	}   
	//it prints the current char to the maze
	public char[][] cursorPrinter(int coordx,int coordy, char[][] _map,char printChar,TextAttributes att1,TextAttributes att2) {
		cn.setTextAttributes(att1);
		cn.getTextWindow().setCursorPosition(coordx, coordy);
		char[][] newmap = new char[23][55];
		for(int i = 0; i < 23;i++) {
			for(int j = 0; j<55;j++) {
				newmap[i][j] = _map[i][j];
			}
		}
		newmap[coordy][coordx] = printChar;
		System.out.print(printChar);
		cn.setTextAttributes(att2);
		cn.getTextWindow().setCursorPosition(0, 0);
		return newmap;
	}
	
	
	
	public void printMap(char[][] _map) {
		cn.getTextWindow().setCursorPosition(0, 0);
		for(int i = 0; i < 23;i++) {
			for(int j = 0; j < 55; j++) {
				if(_map[i][j] == 'P') {
					cn.setTextAttributes(attPlayer);
					System.out.print(_map[i][j]);
					cn.setTextAttributes(attWall);
				}
				else if(_map[i][j] == '#'){
					cn.setTextAttributes(attWall);
					System.out.print(_map[i][j]);
					cn.setTextAttributes(attWall);
				}
				else{
					cn.setTextAttributes(treasure.attribute(_map[i][j]));
					System.out.print(_map[i][j]);
				}
			}
			System.out.print('\n');
		}
	}
	
	
	
	public void printBackpack(Stack backpack) {
		cn.setTextAttributes(attGround);
		cn.getTextWindow().setCursorPosition(58, 0);
		System.out.print("+-----------------+");
		for(int i = 1; i < 22;i++) {
			cn.getTextWindow().setCursorPosition(58, i);
			System.out.print("|");
			cn.getTextWindow().setCursorPosition(76, i);
			System.out.print("|");

		}
		cn.getTextWindow().setCursorPosition(58, 22);
		System.out.print("+-----------------+");
		
		
		Stack tempStack = new Stack(8);
		while(!backpack.isEmpty()) {
			tempStack.push(backpack.pop());
		}
		String backpackname = "KCAPKCAB";
		for (int i = 0; i <8;i++) {
			cn.getTextWindow().setCursorPosition(65, 13-i);
			System.out.print("| ");
			if(!tempStack.isEmpty()) {
				cn.setTextAttributes(treasure.attribute((char) tempStack.peek()));
				System.out.print(tempStack.peek());
				backpack.push(tempStack.pop());
				cn.setTextAttributes(attGround);
			}else 
			{
				System.out.print(" ");
			}
			System.out.print(" |");
			cn.setTextAttributes(treasure.attribute((char) i));
			System.out.print(backpackname.charAt(i));
			cn.setTextAttributes(attGround);

			
			
		}
		cn.getTextWindow().setCursorPosition(65, 14);
		System.out.print("+---+");
		
	}
	
	
	
	public char[][] randomMovement(char[][] _map)
	{
		int[][] coordarray = new int[0][2];
		int coordx = 0;
		int coordy = 0;
		int counter = 0;
		for(int i = 0; i<23;i++) {
			for(int j = 0;j<55;j++) {
				
				if((_map[i][j] == '4'||_map[i][j] == '5')) {
					boolean flag = true;
					for(int k = 0;k<activeDevices.length;k++) {
						if(activeDevices[k] != null && activeDevices[k].getMyTime() > 0) {
							
							if(j == activeDevices[k].getCoordx() &&(i == activeDevices[k].getCoordy() || i == activeDevices[k].getCoordy() + 1||i == activeDevices[k].getCoordy()-1))
								flag = false;
							if(i == activeDevices[k].getCoordy() &&(j == activeDevices[k].getCoordx() || j == activeDevices[k].getCoordx() + 1||j == activeDevices[k].getCoordx()-1))
								flag = false;
							if((i == activeDevices[k].getCoordy()-1 && j == activeDevices[k].getCoordx()-1) || (i == activeDevices[k].getCoordy()+1 && j == activeDevices[k].getCoordx()+1) || (i == activeDevices[k].getCoordy()+1 && j == activeDevices[k].getCoordx()-1) ||
									(i == activeDevices[k].getCoordy()-1 && j == activeDevices[k].getCoordx()+1))
								flag = false;
						}
					}
					if(flag) {
						coordx = j;
						coordy = i;
						int[] newCoord = {j,i};
						coordarray = dynamicArray(coordarray,newCoord);
					}
					cursorPrinter(j,i,_map,_map[i][j],attGround,attGround);
						
				}
			}
		}
		for(int g = 0; g<coordarray.length;g++) {
			if(coordarray[g][0] != 0) 
			{
				coordy = coordarray[g][1];
				coordx = coordarray[g][0];
				int i = coordy;
				int j = coordx;
				do {
					int randomplace = new Random().nextInt(4);
					coordy = coordarray[g][1];
					coordx = coordarray[g][0];
					switch(randomplace) {
					case 0:
						coordy -= 1;
						break;
					case 1:
						coordy += 1;
						break;
					case 2:
						coordx += 1;
						break;
					case 3:
						coordx -= 1;
						break;
					}
					counter ++;
					
				}while(counter < 10 && _map[coordy][coordx] != ' ');
				counter = 0;
				if(_map[coordy][coordx] == ' ') {
					_map[coordy][coordx] = _map[i][j];
					cursorPrinter(coordx,coordy,_map,_map[i][j],treasure.attribute(_map[i][j]),attGround);
					_map[i][j] = ' ';
					cursorPrinter(j,i,_map,_map[i][j],attGround,attGround);
				
				}
				
				
			}
		}
		
		return _map;
	}
	public int[][] dynamicArray(int[][] arrayInput, int[] newVariable){
		int[][] tempArray = new int[arrayInput.length+1][2];
		for(int i = 0; i < arrayInput.length; i++) {
			tempArray[i] = arrayInput[i];
		}
		tempArray[arrayInput.length] = newVariable;
		return tempArray;
		
	}
	public double[][] ScoreMap(char[][] _map){
		double[][] currentScoreMap = new double[23][55];
		for(int i = 0;i<23;i++) {
			for(int j = 0;j<55;j++) {
				if(_map[i][j] == '1' ||_map[i][j] == '2' ||_map[i][j] == '3' ||_map[i][j] == '4' ||_map[i][j] == '5' ||_map[i][j] == '*' ||_map[i][j] == '=') {
					double[][] newScoreMap = new double[23][55];
					newScoreMap[i][j] = 10000*Treasure.scoreTreasure(_map[i][j]);
					newScoreMap = ScoreCalculator(j,i,Treasure.scoreTreasure(_map[i][j])*5000,newScoreMap,_map);
					
					for(int k = 0;k<23;k++) {
						for(int g = 0;g<55;g++) {
							currentScoreMap[k][g] += newScoreMap[k][g];
						}
					}	
			}else if(coordx == j && coordy == i && immune < 0) {
				double[][] newScoreMap = new double[23][55];
				newScoreMap[i][j] = 10000*400;
				newScoreMap = ScoreCalculator(j,i,400*5000,newScoreMap,_map);
				
				for(int k = 0;k<23;k++) {
					for(int g = 0;g<55;g++) {
						currentScoreMap[k][g] += newScoreMap[k][g];
					}
				}	
			}
		}
		
	}
		return currentScoreMap;
	
}
	public double[][] ScoreCalculator(int coordx,int coordy,double score,double[][] ScoreMap,char[][] _map){
		boolean flag = true;		
		if(coordy < 22 && ScoreMap[coordy+1][coordx] < score && _map[coordy+1][coordx] != '#') {
			
		}
		else if(coordy > 0 && ScoreMap[coordy-1][coordx] < score  && _map[coordy-1][coordx] != '#') {
			
		}
		else if(coordx > 0 && ScoreMap[coordy][coordx-1] < score  && _map[coordy][coordx-1] != '#') {
			
		}
		else if(coordx < 54 && ScoreMap[coordy][coordx+1] < score  && _map[coordy][coordx+1] != '#') {
			
		}else {
			flag = false;
		}
		if (flag) {
			if(coordy < 22 && ScoreMap[coordy+1][coordx] < score && _map[coordy+1][coordx] != '#') {
				ScoreMap[coordy+1][coordx] = score;
				ScoreMap = ScoreCalculator(coordx,coordy+1,score/2,ScoreMap,_map);
				
			}else if (coordy < 22 && ScoreMap[coordy+1][coordx] < score && _map[coordy+1][coordx] == '#'){
				ScoreMap[coordy+1][coordx] = 0;
			}
			if(coordy > 0 && ScoreMap[coordy-1][coordx] < score && _map[coordy-1][coordx] != '#') {
				ScoreMap[coordy-1][coordx] = score;
				ScoreMap = ScoreCalculator(coordx,coordy-1,score/2,ScoreMap,_map);


			}else if(coordy > 0 && ScoreMap[coordy-1][coordx] < score && _map[coordy-1][coordx] == '#') {
				ScoreMap[coordy-1][coordx] = 0;

			}
			if(coordx > 0 &&ScoreMap[coordy][coordx-1] < score && _map[coordy][coordx-1] != '#') {
				ScoreMap[coordy][coordx-1] = score;
				ScoreMap = ScoreCalculator(coordx-1,coordy,score/2,ScoreMap,_map);

			}else if(coordx > 0 &&ScoreMap[coordy][coordx-1] < score && _map[coordy][coordx-1] == '#') {
				ScoreMap[coordy][coordx-1] = 0;
			}
			if(coordx < 54 && ScoreMap[coordy][coordx+1] < score&& _map[coordy][coordx+1] != '#') {
				ScoreMap[coordy][coordx+1] = score;
				ScoreMap = ScoreCalculator(coordx+1,coordy,score/2,ScoreMap,_map);


				
			}else if(coordx < 54 && ScoreMap[coordy][coordx+1] < score&& _map[coordy][coordx+1] == '#') {
				ScoreMap[coordy][coordx+1] = 0;

				
			}


		}
		return ScoreMap;

	}
}