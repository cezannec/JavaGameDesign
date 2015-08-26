package com.example.tictactoe;

import android.graphics.RectF;

public class LogicalBoard {

	private int _splitWidth;
    private int _splitHeight;
 
    public LogicalBoard(int splitWidth, int splitHeight) {
        _splitWidth = splitWidth;
        _splitHeight = splitHeight;
        
        SetupPositions();
    }
    
    private class BoardPosition extends RectF {
        public BoardPosition(float left, float top, float right, float bottom) {
            super(left, top, right, bottom);
     
            filled = false;
        }
     
        private boolean filled;
     
        public void setFilled(boolean filled) {
            this.filled = filled;
        }
     
        public boolean isFilled() {
            return filled;
        }
    }
    
    BoardPosition[] _positions;
    
    private void SetupPositions() {
        _positions = new BoardPosition[9];
        // first row
        _positions[0] = new BoardPosition(0, 0, _splitWidth, _splitHeight);
        _positions[1] = new BoardPosition(_splitWidth, 0, 2 * _splitWidth,
                _splitHeight);
        _positions[2] = new BoardPosition(2 * _splitWidth, 0, 3 * _splitWidth,
                _splitHeight);
        // second row
        _positions[3] = new BoardPosition(0, _splitHeight, _splitWidth,
                2 * _splitHeight);
        _positions[4] = new BoardPosition(_splitWidth, _splitHeight,
                2 * _splitWidth, 2 * _splitHeight);
        _positions[5] = new BoardPosition(2 * _splitWidth, _splitHeight,
                3 * _splitWidth, 2 * _splitHeight);
        // third row
        _positions[6] = new BoardPosition(0, 2 * _splitHeight, _splitWidth,
                3 * _splitHeight);
        _positions[7] = new BoardPosition(_splitWidth, 2 * _splitHeight,
                2 * _splitWidth, 3 * _splitHeight);
        _positions[8] = new BoardPosition(2 * _splitWidth, 2 * _splitHeight,
                3 * _splitWidth, 3 * _splitHeight);
    }
    
    public RectF getPositionToFill(float x, float y) {
        for (BoardPosition bp : _positions) {
            if (bp.contains(x, y) && !bp.filled) {
                RectF toReturn = new RectF(bp);
                bp.filled = true;
                return toReturn;
            }
        }
     
        return null;
    }
}
