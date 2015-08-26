package com.example.tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;


public class Board extends View{
	
	int classHeight;
	int classWidth;
	
	Bitmap classBitmap;
	Canvas classCanvas;
	Paint classPaint;
	
	boolean _drawX;

	public Board(Context context){
		super(context);
		classPaint = new Paint();
		classPaint.setColor(Color.BLACK);
		classPaint.setStyle(Paint.Style.STROKE);
		
		_drawX = true;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		classHeight = View.MeasureSpec.getSize(heightMeasureSpec);
		classWidth = View.MeasureSpec.getSize(widthMeasureSpec);
		
		setMeasuredDimension(classWidth, classHeight);
		
		classBitmap = Bitmap.createBitmap(classWidth, classHeight, Bitmap.Config.ARGB_8888);
		classCanvas = new Canvas(classBitmap);
		
		calculateLinePlacements();
		drawBoard();
	}
	
	Point[] _firstHorizontalLine;
	Point[] _secondHorizontalLine;
	Point[] _firstVerticalLine;
	Point[] _secondVerticalLine;
	
	private LogicalBoard _logicalBoard;
	 
	private void calculateLinePlacements() {
	    int splitHeight = classHeight / 3;
	    int splitWidth = classWidth / 3;
	 
	    _firstHorizontalLine = new Point[2];
	    Point p1 = new Point(0, splitHeight);
	    Point p2 = new Point(classWidth, splitHeight);
	    _firstHorizontalLine[0] = p1;
	    _firstHorizontalLine[1] = p2;
	 
	    _secondHorizontalLine = new Point[2];
	    p1 = new Point(0, 2 * splitHeight);
	    p2 = new Point(classWidth, 2 * splitHeight);
	    _secondHorizontalLine[0] = p1;
	    _secondHorizontalLine[1] = p2;
	 
	    _firstVerticalLine = new Point[2];
	    p1 = new Point(splitWidth, 0);
	    p2 = new Point(splitWidth, classHeight);
	    _firstVerticalLine[0] = p1;
	    _firstVerticalLine[1] = p2;
	 
	    _secondVerticalLine = new Point[2];
	    p1 = new Point(2 * splitWidth, 0);
	    p2 = new Point(2 * splitWidth, classHeight);
	    _secondVerticalLine[0] = p1;
	    _secondVerticalLine[1] = p2;
	    
	    _logicalBoard = new LogicalBoard(splitWidth, splitHeight);
	}
	
	private void drawBoard() {
	    classCanvas.drawLine(_firstHorizontalLine[0].x, _firstHorizontalLine[0].y,
	            _firstHorizontalLine[1].x, _firstHorizontalLine[1].y, classPaint);
	 
	    classCanvas.drawLine(_secondHorizontalLine[0].x,
	            _secondHorizontalLine[0].y, _secondHorizontalLine[1].x,
	            _secondHorizontalLine[1].y, classPaint);
	 
	    classCanvas.drawLine(_firstVerticalLine[0].x, _firstVerticalLine[0].y,
	            _firstVerticalLine[1].x, _firstVerticalLine[1].y, classPaint);
	 
	    classCanvas.drawLine(_secondVerticalLine[0].x, _secondVerticalLine[0].y,
	            _secondVerticalLine[1].x, _secondVerticalLine[1].y, classPaint);
	 
	    invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
	    canvas.drawBitmap(classBitmap, 0, 0, classPaint);
	}
	
	//NEW
	private int[][] XOpositions = new int[3][3];
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    RectF position = _logicalBoard.getPositionToFill(event.getX(), event.getY());
	    if (position != null) {
	        // draw an X or an O here
	    	if (_drawX) {
                classCanvas.drawLine(position.left, position.top,
                        position.right, position.bottom, classPaint);
                classCanvas.drawLine(position.right, position.top,
                        position.left, position.bottom, classPaint);
            } else {
                classCanvas.drawOval(position, classPaint);
            }
            _drawX = !_drawX;
            
            
            //NEW 
            if(!_drawX){
            	// is X
            	XOpositions[(int) (position.centerX()/(classWidth/3))][(int) (position.centerY()/(classHeight/3))] = 1;
            } else{
            	// is O
            	XOpositions[(int) (position.centerX()/(classWidth/3))][(int) (position.centerY()/(classHeight/3))] = -1;
            }
            
            //NEW
            System.out.println(checkWin());
            if(checkWin() == 'x'){
            	System.out.println("X wins!!");
            	classCanvas.drawColor(Color.WHITE);
            	classCanvas.drawText("X wins!!", classWidth/2, classHeight/2, classPaint);
            } else if(checkWin() == 'o'){
            	System.out.println("O wins!!");
            	classCanvas.drawColor(Color.WHITE);
            	classCanvas.drawText("O wins!!", classWidth/2, classHeight/2, classPaint);
            }
            invalidate();
	    }
	    return true;
	}
	
	private char checkWin(){
		int total;
		
		for(int r = 0; r < 3; r++){
			
				total = XOpositions[r][0] + XOpositions[r][1] + XOpositions[r][2];
				if(total == 3){
					return 'x';
				} else if (total == -3){
					return 'o';
				}
				
		}
		
		total = XOpositions[0][0] + XOpositions[1][1] + XOpositions[2][2];
		
		if(total == 3){
			return 'x';
		} else if (total == -3){
			return 'o';
		}
		
		total = XOpositions[2][0] + XOpositions[1][1] + XOpositions[0][2];
		
		if(total == 3){
			return 'x';
		} else if (total == -3){
			return 'o';
		}
		
		return 'n';
	}
	
	
}
