package tp1.logic;

import tp1.exception.OffWorldException;
import tp1.view.Messages;

/**
 * 
 * Immutable class to encapsulate and manipulate positions in the game board
 * 
 */
public class Position {

	private int col;
	private int row;

	// Constructor
	public Position(int col, int row) {
		this.col = col;
		this.row = row;
	}

	public int getCol() {
		return (this.col);
	}

	public int getRow() {
		return (this.row);
	}

	public void setPos(int row, int col) {
		setCol(col);
		setRow(row);
	}
	
	public void setPos(Position pos) {
		this.col = pos.getCol();
		this.row = pos.getRow();
	}

	private void setCol(int col) {
		this.col = col;
	}

	private void setRow(int row) {
		this.row = row;
	}

	public void updatePos(Move move) {
		this.col += move.getX();
		this.row += move.getY();
	}

	public boolean equals(Position pos) {
		return (this.col == pos.getCol() && this.row == pos.getRow());
	}
	
	public boolean equals(int row, int col) {
		return (this.row == row && this.col == col);
	}
	
	/**
	 *Check if position is in board
	 * */
	public boolean inBoard() {	
		return (this.col >= 0 && this.col < Game.DIM_X && this.row >= 0 && this.row < Game.DIM_Y);
	}

	/**
	 * compare two positions (equal or crossing)
	 * */
	public boolean compBombLaser(Position posLaser) {	
		return ((posLaser.getCol() == this.col && (posLaser.getRow() + 1) == this.row) || (posLaser.getCol() == this.col && (posLaser.getRow()) == this.row)|| (posLaser.getCol() == this.col && (posLaser.getRow() - 1) == this.row));
	}
	
	/**
	 * move a position in a certain direction
	 * */
	public void move(Move move) throws OffWorldException {	
		int col = -1;
		
		col = this.col + move.getX();
			
			if (col >= 0 && col < Game.DIM_X) {
				this.setCol(col);
			}
			else 
				throw new OffWorldException(Messages.errorOffBoard(move.toString(), this.row, this.col));
	}
	
	public void moveAlien(Move move) {	
		if(move == Move.RIGHT || move == Move.LEFT) 
			this.setCol(this.col + move.getX());
		else if (move == Move.DOWN)
			this.setRow(this.row + move.getY());
	}
	
	public boolean onBorder() {
		return (this.col == 0 || this.col == Game.DIM_X - 1);
	}
	
	public boolean onLastRow() {
		return(this.row == Game.DIM_Y - 1);
	}
	
	public boolean secondLastRow() {
		return(this.row == Game.DIM_Y - 2);
	}
 }
