import java.awt.image.BufferedImage;
import java.awt.Image;
public class Rook extends Piece {

	private int team, moves;
	private Image image;

	/*
	 *  Initialization constructor for Rook objects
	 *  Written by Kyle
	 */
	public Rook(int team, Image image, int moves) {
		super(team, image, moves);
	}

	/*
	 *  Checks if the move of the Rook is valid given starting
	 *  and finishing coordinates
	 *  Written by Kyle
	 */
	public boolean isValidMove(int x1, int y1, int x2, int y2, int team1, int team2, int moves1, int moves2, boolean switch_pawn) {
		if((x2 - x1) != 0 && (y2 - y1) == 0)
			return true;
		if((y2 - y1) != 0 && (x2 - x1) == 0)
			return true;
		return false;		
	}

	/*
	 *  Checks if there is a piece in the way
	 *  Written by Joseph and Kyle
	 */
	public boolean isClear(Piece piece, int x1, int y1, int x2, int y2, int[][] board, int t1, int t2) {
		if(x1 > x2 && y1 == y2) {
			for(int r = x1 - 1; r > x2; r--) {
				if(board[r][y2] != 1)
					return false;
			}
		}
		if(y1 > y2 && x1 == x2) {
			for(int c = y1 - 1; c > y2; c--) {
				if(board[x1][c] != 1)
					return false;
			}
		}
		if(x1 < x2 && y1 == y2) {
			for(int r = x1 + 1; r < x2; r++) {
				if(board[r][y2] != 1)
					return false;
			}
		}
		if(y1 < y2 && x1 == x2) {
			for(int c = y1 + 1; c < y2; c++) {
				if(board[x1][c] != 1)
					return false;
			}
		}

		if(t1 == t2) {
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
		return "R";
	}
}