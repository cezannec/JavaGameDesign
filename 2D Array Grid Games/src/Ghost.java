import acm.graphics.GOval;

public class Ghost extends GOval {
	
	private int ghostX;
	private int ghostY;
	private int boxSize;

	public Ghost(double x, double y, int boxSize){
		super(x, y, 10, 10);
		ghostX = (int) (x/boxSize);
		ghostY = (int) (y/boxSize);
		this.boxSize = boxSize;
	}
	
	
	// 0: up, 1: down, 2: right, 3: left
	public void move(int choice){
		if(choice == 0){
			move(0,-boxSize);
			ghostY--;
		}
		else if(choice == 1){
			move(0, boxSize);
			ghostY++;
		}
		else if(choice == 2){
			move(boxSize, 0);
			ghostX++;
		}
		else if(choice == 3){
			move(-boxSize, 0);
			ghostX--;
		}

	}
	
	public int getGhostX(){
		return ghostX;
	}
	
	public int getGhostY(){
		return ghostY;
	}
	
}
