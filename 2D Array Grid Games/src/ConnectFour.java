import java.awt.Color;
import java.util.Scanner;

import acm.graphics.GOval;
import acm.program.GraphicsProgram;


public class ConnectFour extends GraphicsProgram{
	public static final int APPLICATION_HEIGHT = 620;
	public static final int APPLICATION_WIDTH = 700;
	
	private int width = 7;
	private int height = 6;
	private int boxSize = 100;
	
	private boolean player1Turn = true;
	
	
	private int[][] grid = new int[width][height]; // 7 x 6 grid

	public static void main(String[] args) {
		
		new ConnectFour().start(args);
	}
	
	public void run(){
		setBackground(Color.blue);
		initGrid();
		drawGrid(grid);
		
		Scanner sc = new Scanner(System.in);
		
		while(true){
			System.out.println("Pick a column number: ");
			int column = sc.nextInt();
			if(player1Turn){
				for(int i = height-1; i >= 0; i--){
					if(grid[column][i]== 0){
						grid[column][i] = 1;
						break;
					}
				}
			}
			else{
				// player 2
				for(int i = height-1; i >= 0; i--){
					if(grid[column][i]== 0){
						grid[column][i] = -1;
						break;
					}
				}
			}
			removeAll();
			player1Turn = !player1Turn;
			drawGrid(grid);
			if(checkBoard(grid) == 1 || checkBoard(grid) == 2){
				System.out.println("WIN");
				break;
			}
			pause(500);
		}
	}
	
	
	public void initGrid(){
		for(int j = 0; j < height; j++){
			for(int i = 0; i < width; i++){
				grid[i][j] = 0;
			}
		}
	}
	public void drawGrid(int[][] grid){
		GOval piece;
		Color c;
		for(int j = 0; j < height; j++){
			for(int i = 0; i < width; i++){
				if(grid[i][j] == 1){
					// draw player 1 move, red
					c = Color.red;
				}
				else if(grid[i][j] == -1){
					// draw player 1 move, black
					c = Color.black;
				}
				else{
					c = Color.white;
				}
				piece = new GOval(boxSize*i, boxSize*j, boxSize, boxSize);
				add(piece);
				piece.setFilled(true);
				piece.setColor(c);
			}
		}
	}
	
	public boolean isEmpty(int x, int y){
		return grid[x][y] == 0;
	}
	
	private int checkBoard(int[][] grid) {
		// Row Wise
		for (int r = width - 1; r >=0; r--) {
			int total;
			for (int c = 0; c < height - 3; c++) {
				total = grid[r][c] + grid[r][c + 1] + grid[r][c + 2]
						+ grid[r][c + 3];
				if (total == 4) {
					return 1;
				}
				if (total == -4) {
					return 2;
				}
			}
		}
		// Column Wise
 
		for (int c = 0;c < height - 1; c++) {
			int total;
			for (int r = width - 1 - 3; r >= 0; r--) {
				total = grid[r][c] + grid[r + 1][c] + grid[r + 2][c]
						+ grid[r + 3][c];
				if (total == 4) {
					return 1;
				}
				if (total == -4) {
					return 2;
				}
			}
		}
		// Diagonals
		for (int r = width - 1 - 3; r >= 3; r--) {
			int total;
			for (int c = 0; c < height - 1 - 3; c++) {
				total = grid[r][c] + grid[r + 1][c + 1] + grid[r + 2][c + 2]
						+ grid[r + 3][c + 3];
				if (total == 4) {
					return 1;
				}
				if (total == -4) {
					return 2;
				}
			}
		}
		for (int r = width - 1 - 3; r >= 3; r--) {
			int total;
			for (int c = 3; c < height - 1; c++) {
				total = grid[r][c] + grid[r - 1][c - 1] + grid[r - 2][c - 2]
						+ grid[r - 3][c - 3];
				if (total == 4) {
					return 1;
				}
				if (total == -4) {
					return 2;
				}
			}
		}
		for (int r = width - 1 - 3; r >= 3; r--) {
			int total;
			for (int c = 0; c < height - 3; c++) {
				total = grid[r][c] + grid[r - 1][c + 1] + grid[r - 2][c + 2]
						+ grid[r - 3][c + 3];
				if (total == 4) {
					return 1;
				}
				if (total == -4) {
					return 2;
				}
			}
		}
		for (int r = width - 1 - 3; r >= 3; r--) {
			int total;
			for (int c = 3; c < height - 3; c++) {
				total = grid[r][c] + grid[r + 1][c - 1] + grid[r + 2][c - 2]
						+ grid[r + 3][c - 3];
				if (total == 4) {
					return 1;
				}
				if (total == -4) {
					return 2;
				}
			}
		}
		return 0;
	}

}
