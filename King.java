import java.lang.Math;
import java.awt.image.BufferedImage;
import java.awt.Image;
public class King extends Piece {

	private int team, moves;
	private Image image;

	/*
	 *  Initialization constructor for King objects
	 *  Written by Joseph
	 */
	public King(int team, Image image, int moves) {
		super(team, image, moves);
	}

	/*
	 *  Checks if the move of the king is valid given starting
	 *  and finishing coordinates
	 *  Written by Joseph
	 */
	public boolean isValidMove(int x1, int y1, int x2, int y2, int team1, int team2, int moves1, int moves2, boolean switch_pawn) {
		if(Math.abs(x2-x1) <= 1 && Math.abs(y2-y1) <= 1) {
			return true;
		}
		if(moves1 == 0 && moves2 == 0) {
			if(team1 == 0) {
				if(x2 == 0) {
					if(y2 == 4 || y2 == 11)
						return true;
				}
			}
			if(team1 == 1) {
				if(y2 == 0) {
					if(x2 == 4 || x2 == 11)
						return true;
				}
			}
			if(team1 == 2) {
				if(x2 == 15) {
					if(y2 == 4 || y2 == 11)
						return true;
				}
			}
			if(team1 == 3) {
				if(y2 == 15) {
					if(x2 == 4 || x2 == 11)
						return true;
				}
			}
		}
		return false;
	}

	/*
	 *  Checks if there is a piece in the way
	 *  Written by Joseph
	 */
	public boolean isClear(Piece piece, int x1, int y1, int x2, int y2, int[][] board, int t1, int t2) {
		if(t1 == t2) {
			if(piece.validCastle(x1, y1, x2, y2, board))
				return true;
			System.out.println("Your own piece is already there.");
			return false;
		}
		return true;
	}

	/*
	 *  Checks if the castle is valid
	 *  Written by Joseph
	 */
	public boolean validCastle(int x1, int y1, int x2, int y2, int[][] board) {
		if(x2 - x1 == 4) {
			for(int i = x1 + 1; i < x2; i ++) {
				if(board[i][y1] != 1)
					return false;
			}
			return true;
		}
		
		if(x2 - x1 == -3) {
			for(int i = x2 + 1; i < x1; i ++) {
				if(board[i][y1] != 1)
					return false;
			}
			return true;
		}
		if(y2 - y1 == 4) {
			for(int i = y1 + 1; i < y2; i ++) {
				if(board[x1][i] != 1)
					return false;
			}
			return true;
		}
		if(y2 - y1 == -3) {
			for(int i = y2 + 1; i < y1; i ++) {
				if(board[x1][i] != 1)
					return false;
			}
			return true;
		}
		return false;
	}

	/*
	 *  Return the character represetation of the given piece
	 *  Written by Kyle
	 */
	public String toString() {
		return "K";
	}
}