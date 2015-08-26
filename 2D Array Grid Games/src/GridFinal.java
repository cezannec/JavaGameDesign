import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class GridFinal extends GraphicsProgram {
	
	public static final int APPLICATION_HEIGHT = 520;
	public static final int APPLICATION_WIDTH = 500;
	
	private int height = 20;
	private int width = 20;
	private int boxSize = 25;
	
	char[] array1 = new char[10];
	int[][] twoD = new int[width][height];
	
	char[][] char2D = new char[width][height];
	
	ArrayList food = new ArrayList();
	GOval player;
	private int playerX;
	private int playerY;
	
	Ghost ghost;
	
	private int ghostX;
	private int ghostY;
	
	private double timer;
	
	private boolean hasLost;
	
	ArrayList ghostList = new ArrayList();
	

	public static void main(String[] args) {
		new GridFinal().start(args);
	}
	
	public void run(){
		addKeyListeners();
		
		File boardFile = new File("largerboard.txt");
		try{
			Scanner sc = new Scanner(boardFile);
			int rowNum = 0;
			while(sc.hasNextLine()){
				String s = sc.nextLine();
				char[] nums = s.toCharArray();
				for(int i = 0; i < nums.length; i++){
					char2D[i][rowNum]= nums[i];
					System.out.print(char2D[i][rowNum] +" ");
				}
				System.out.println();
				rowNum++;
			}
		}
		catch(IOException e){
			System.out.println("File not found.");
		}
		

		
		GRect box;
		GOval o;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				//int rand = (int) (3*Math.random());
				//twoD[j][i] = rand;
				//System.out.print(twoD[j][i] + " ");
				
				box = new GRect(boxSize*j, boxSize*i, boxSize, boxSize);
				add(box);
				
				if(char2D[j][i] == '0'){
					box.setFilled(true);
					box.setColor(Color.BLACK);
					o = new GOval(boxSize*j+10, boxSize*i+10, 5, 5);
					o.setFilled(true);
					o.setColor(Color.YELLOW);
					add(o);
					food.add(o);
				}
				else if(char2D[j][i] == '1'){
					box.setFilled(true);
					box.setColor(Color.BLUE);
				}
			}
			//System.out.println();
		}
		// end of nested for loops
		player = new GOval(playerX+5, playerY+5, 15, 15);
		player.setFilled(true);
		player.setColor(Color.RED);
		add(player);
		
		
		addGhosts();
		
		
		while(true){
			
			// ghost movement
			// 0: up, 1: down, 2: right, 3: left
			for(int i = 0; i < ghostList.size(); i++){
				ghost = (Ghost) ghostList.get(i);
			if(timer%500==0){
				if(playerX < ghost.getGhostX()){
					ghost.move(3);
				}
				else if(playerY > ghost.getGhostY()){
					ghost.move(1);
				}
				else if(playerX > ghost.getGhostX()){
					ghost.move(2);
				}
				else if(playerY < ghost.getGhostY()){
					ghost.move(0);
				}
//				int rand =(int) (4*Math.random()); // between 0-3
//				if(rand == 2 && isCollidable(ghost.getGhostX()+1, ghost.getGhostY())){
//					ghost.move(rand);
//				}
//				else if(rand == 3 && isCollidable(ghost.getGhostX()-1, ghost.getGhostY())){
//					ghost.move(rand);
//				}
//				else if(rand == 1 && isCollidable(ghost.getGhostX(), ghost.getGhostY()+1)){
//					ghost.move(rand);
//				}
//				else if(rand == 0 && isCollidable(ghost.getGhostX(), ghost.getGhostY()-1)){
//					ghost.move(rand);
//				}
				//System.out.println(ghost.getGhostX() +" , "+ ghost.getGhostY() );
			}
			}
			
			if(collision(ghost, player)){
				remove(player);
				hasLost = true;
				break;
			}
			
			if(food.size() == 0){
				break;
			}
			
			for(int i = 0; i < food.size(); i++){
				GOval foodPiece = (GOval) food.get(i);
				if(collision(player, foodPiece)){
					remove(foodPiece);
					food.remove(i);
				}
			}
			
			
			pause(10);
			timer = timer + 10;
		}
		
		GLabel wonLost;
		
		if(hasLost){
			wonLost = new GLabel("YOU LOSE", 220, 250);
		}
		else{
			wonLost = new GLabel("YOU WON AND ARE AWESOME", 220, 250);
		}
		
		wonLost.setColor(Color.WHITE);
		add(wonLost);
	}
	
	public void addGhosts(){
		for(int i = 0; i < 2; i++){
			for(int j = 0; j <2; j++) {
				if(!(i == 0 && j == 0)){
					ghost = new Ghost(19*j*25+7.5, 19*i*25+7.5, boxSize);
					ghost.setFilled(true);
					ghost.setColor(Color.MAGENTA);
					add(ghost);
					ghostList.add(ghost);
				}
				
			}
		}
	}
	
	public void keyPressed(KeyEvent ke){
		if(ke.getKeyCode() == KeyEvent.VK_RIGHT && isCollidable(playerX+1, playerY)){
			player.move(25, 0);
			playerX = playerX+1;
		}
		else if(ke.getKeyCode() == KeyEvent.VK_LEFT && isCollidable(playerX-1, playerY)){
			player.move(-25, 0);
			playerX = playerX -1;
		}
		else if(ke.getKeyCode() == KeyEvent.VK_DOWN && isCollidable(playerX, playerY+1)){
			player.move(0, 25);
			playerY = playerY+1;
		}
		else if(ke.getKeyCode() == KeyEvent.VK_UP && isCollidable(playerX, playerY-1)){
			player.move(0, -25);
			playerY = playerY-1;
		}
		
	}
	
	public boolean collision(GObject a, GObject b){
		if(a.getBounds().intersects(b.getBounds())){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isCollidable(int x, int y){
		if(x >=0 && x <=19 && y >=0 && y <=19){
			if(char2D[x][y] == '0'){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
	}

}
