import java.awt.image.BufferedImage;
import java.awt.Image;
import java.lang.Math;
public class Pawn extends Piece {

	private int team, moves;
	private Image image;

	/*
	 *  Initialization constructor for Pawn objects
	 *  Written by Kyle
	 */
	public Pawn(int team, Image image, int moves) {
		super(team, image, moves);
	}

	/*
	 *  Checks if the move of the Pawn is valid given starting
	 *  and finishing coordinates
	 *  Also has a change in pawn use for two players versus four players
	 *  Written by Joseph
	 */
	public boolean isValidMove(int x1, int y1, int x2, int y2, int team1, int team2, int moves1, int moves2, boolean switch_pawn) {
		if(switch_pawn) {
			if(Math.abs(x2-x1) <= 1 && Math.abs(y2-y1) <= 1) {
				return true;
			}
			else
				return false;
		}
		else {
			if(team1 == 1) {
				if(y1 == 1) {
					if(y2 == y1 + 2 && (x2 - x1) == 0)
						return true;
				}
				if(y2 == y1 + 1 && (x2 - x1) == 0)
					return true;
				if(y2 == y1 + 1 && Math.abs(x2-x1) == 1 && team2 != 4 && team2 != team1)
					return true;
			}

			else if(team1 == 2) {
				if(x1 == 14) {
					if(x2 == x1 - 2 && (y2 - y1) == 0)
						return true;
				}
				if(x2 == x1 - 1 && (y2 - y1) == 0)
					return true;
				if(x2 == x1 - 1 && Math.abs(y2-y1) == 1 && team2 != 4 && team2 != team1)
					return true;
			}

			else if(team1 == 3) {
				if(y1 == 14) {
					if(y2 == y1 - 2 && (x2 - x1) == 0)
						return true;
				}
				if(y2 == y1 - 1 && (x2 - x1) == 0)
					return true;
				if(y2 == y1 - 1 && Math.abs(x2-x1) == 1 && team2 != 4 && team2 != team1)
					return true;
			}
			else {
				if(x1 == 1) {
					if(x2 == x1 + 2 && (y2 - y1) == 0)
						return true;
				}
				if(x2 == x1 + 1 && (y2 - y1) == 0)
					return true;
				if(x2 == x1 + 1 && Math.abs(y2-y1) == 1 && team2 != 4 && team2 != team1)
					return true;
			}
			return false;
		}
	}

	/*
	 *  Checks if there is a piece in the way
	 *  Written by Joseph
	 */
	public boolean isClear(Piece piece, int x1, int y1, int x2, int y2, int[][] board, int t1, int t2) {
		if(x1 > x2 && y1 == y2) {
			for(int r = x1 - 1; r >= x2; r--) {
				if(board[r][y2] != 1)
					return false;
			}
		}
		if(y1 > y2 && x1 == x2) {
			for(int c = y1 - 1; c >= y2; c--) {
				if(board[x1][c] != 1)
					return false;
			}
		}
		if(x1 < x2 && y1 == y2) {
			for(int r = x1 + 1; r <= x2; r++) {
				if(board[r][y2] != 1)
					return false;
			}
		}
		if(y1 < y2 && x1 == x2) {
			for(int c = y1 + 1; c <= y2; c++) {
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
	 *  Checks if a Pawn is capable of changing to another piece given its moves
	 *  Written by Joseph
	 */
	public boolean changeType(int x) {
		if(x == 6)
			return true;
		return false;
	}

	/*
	 *  Checks if the pawn has moved twice given start and end positions
	 *  Written by Joseph
	 */
	public boolean movesTwo(int x1, int y1, int x2, int y2) {
		if(Math.abs(x1 - x2) == 2 || Math.abs(y1 - y2) == 2) {
			return true;
		}
		return false;
	}

	/*
	 *  Return the character representation of the given piece
	 *  Written by Kyle
	 */
	public String toString() {
		return "P";
	}
}