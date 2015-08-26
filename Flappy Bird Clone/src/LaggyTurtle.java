import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GRect;
import acm.graphics.GTurtle;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;


public class LaggyTurtle extends GraphicsProgram {
	
	public static final int APPLICATION_WIDTH = 500;
	public static final int APPLICATION_HEIGHT = 500;
	
	RandomGenerator rand = new RandomGenerator();
	
	GTurtle kanye;
	
	//y-velocity and gravity of kanye
	private double kvy = -12; //negative moves it up initially
	private double g = 3;
	
	//x-velocity of pipes
	private double pvx = -5;
	
	ArrayList<GRect> pipes = new ArrayList<GRect>();
	

	public static void main(String[] args){
		new LaggyTurtle().start(args);
	}
	
	public void run(){
		addMouseListeners();
		addPipes();
		
		kanye = new GTurtle(100, APPLICATION_HEIGHT/2);
		add(kanye);
		
		while(true){
			kanye.move(0,  kvy);
			kvy = kvy + g;
			pause(1);
			System.out.println(kanye.getY());
			
			for(int i = 0; i< pipes.size(); i++){
				pipes.get(i).move(pvx,  0);
			}
			
			if(pipes.get(0).getX() <=0){
				remove(pipes.remove(0));
				remove(pipes.remove(0));
				addOnePipe();
			}
			
		}	
		
	}
	
	public void addPipes(){
		for(int i = 0; i < 3; i++){
			int height = rand.nextInt(200, 300);
			GRect bottom = new GRect(300+200*i, height, 50, APPLICATION_HEIGHT - height);
			GRect top = new GRect(300+200*i,-1, 50, height - 100);
			add(bottom);
			add(top);
			pipes.add(bottom);
			pipes.add(top);
		}
		
	}
	
	public void addOnePipe(){
		
		int height = rand.nextInt(200, 300);
		GRect bottom = new GRect(600, height, 50, APPLICATION_HEIGHT - height);
		GRect top = new GRect(600,-1, 50, height - 100);
		add(bottom);
		add(top);
		pipes.add(4, bottom);
		pipes.add(5, top);
		
	}
	
	public void mouseClicked(MouseEvent me){
		kvy = -12;
	}
}
