import java.lang.Math;
import java.awt.image.BufferedImage;
import java.awt.Image;
public class Knight extends Piece {
	
	private int team, moves;
	private Image image;

	/*
	 *  Initialization constructor for Knight objects
	 *  Written by Kyle
	 */
	public Knight(int team, Image image, int moves) {
		super(team, image, moves);
	}

	/*
	 *  Checks if the move of the Knight is valid given starting
	 *  and finishing coordinates
	 *  Written by Kyle
	 */
	public boolean isValidMove(int x1, int y1, int x2, int y2, int team1, int team2, int moves1, int moves2, boolean switch_pawn) {
		if(Math.abs(x2 - x1) == 1 && Math.abs(y2 - y1) == 2)
			return true;
		if(Math.abs(x2 - x1) == 2 && Math.abs(y2 - y1) == 1)
			return true;
		return false;
	}

	/*
	 *  Checks if there is a piece in the way
	 *  Written by Kyle
	 */
	public boolean isClear(Piece piece, int x1, int y1, int x2, int y2, int[][] board, int t1, int t2) {
		if(t1 == t2){
			System.out.println("Your own piece is already there.");
			return false;
		}
		return true;
	}

	/*
	 *  Return the character representation of the given piece
	 *  Written by Kyle
	 */
	public String toString() {
		return "H";
	}
}