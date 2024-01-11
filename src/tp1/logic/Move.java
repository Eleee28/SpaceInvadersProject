package tp1.logic;

/**
 * Represents the allowed movements in the game
 *
 */
public enum Move {
	LEFT(-1, 0), LLEFT(-2, 0), RIGHT(1, 0), RRIGHT(2, 0), DOWN(0, 1), UP(0, -1), NONE(0, 0);

	private int x;
	private int y;

	/**
	 * Constructor of the class
	 * 
	 */
	private Move(int x, int y) { 
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x coordinate
	 * 
	 * @return x coordinate
	 */
	public int getX() { 
		return x;
	}

	/**
	 * Returns the y coordinate
	 * 
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Update the position
	 * 
	 */
	public void setMove(int x, int y) {
		this.x = x;
		this.y = y;
	}
	

}
