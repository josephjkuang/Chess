import java.awt.image.BufferedImage;
import java.awt.Image;
/*
 *  Used for parts on the screen that are not part of the board
 */
public class Dead extends Piece {

	private int team, moves;
	private Image image;

	/*
	 *  Default constructor to create piece objects
	 *  Sets values to default values
	 *  Written by Joseph
	*/
	public Dead() {
		team = 5;
		image = null;
		moves = 0;
	}

	/*
	 *  Initialization constructor for Dead objects
	 *  Written by Kyle
	 */
	public Dead(int team, Image image, int moves) {
		super(team, image, moves);
	}

	/*
	 *  Return the character representation of the given piece
	 *  Written by Kyle
	 */
	public String toString() {
		return "O";
	}
}