import java.awt.image.BufferedImage;
import java.awt.Image;

/*
 *  The Piece class is the parent class for all of the other
 *  objects.
 */
public class Piece {

	private int team, moves;
	private Image image;

	/*
	 *  Default constructor to create piece objects
	 *  Sets values to default values
	 *  Written by Joseph
	*/
	public Piece() {
		team = 4;
		image = null;
		moves = 0;
	}

	/*
	 *  Initialization constructor that takes in a team value,
	 *  an image object, and the number of moves the piece has
	 *  executed.
	 *  Written by Kyle
	 */
	public Piece(int team, Image image, int moves) {
		this.team = team;
		this.image = image;
		this.moves = moves;
	}

	/*
	 *  Return the team of the given piece
	 *  Written by Kyle
	 */
	public int getTeam() {
		return team;
	}

	/*
	 *  Sets the team of the given piece
	 *  Written by Kyle
	 */
	public void setTeam(int team) {
		this.team = team;
	}

	/*
	 *  Return the image of the given piece
	 *  Written by Kyle
	 */
	public Image getImage() {
		return image;
	}

	/*
	 *  Sets the number of moves a piece has executed
	 *  Written by Kyle
	 */
	public void setMoves(int moves) {
		this.moves = moves;
	}

	/*
	 *  Return the number of moves of the given piece
	 *  Written by Kyle
	 */
	public int getMoves() {
		return moves;
	}

	/*
	 *  Update the type of piece
	 *  Used when a pawn needs to be upgraded
	 *  Written by Kyle
	 */
	public boolean changeType(int moves) {
		return false;
	}

	/*
	 *  Checks if the piece moved two spaces
	 *  Used for pawns
	 *  Written by Kyle
	 */
	public boolean movesTwo(int x1, int y1, int x2, int y2) {
		return false;
	}

	/*
	 *  Checks if there are pieces in the way
	 *  Written by Joseph
	 */
	public boolean isClear(Piece piece, int x1, int y1, int x2, int y2, int[][] board, int t1, int t2) {
		return false;
	}

	/*
	 *  Check if a move is valid for the given piece
	 *  Written by Joseph
	 */
	public boolean isValidMove(int x1, int y1, int x2, int y2, int team1, int team2, int moves1, int moves2, boolean switch_pawn) {
		return false;		
	}

	/*
	 *  Check if a castle is allowed
	 *  Written by Joseph
	 */
	public boolean validCastle(int x1, int x2, int y1, int y2, int[][] board) {
		return false;
	}

	/*
	 *  Return the character representation of the given piece
	 *  Written by Jospeh
	 */
	public String toString() {
		return "x";
	}
}