import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter.*;
import java.awt.event.MouseEvent.*;
import java.applet.Applet;
import java.util.Scanner;

public class Chess extends JPanel {
	
	private static Piece[][] board;
	public static final JFrame frame = new JFrame("Joseph and Kyle's 4-Player Chess");
	private static final String imagePath = "./Images/";
	private static Piece[] list;
	private static int[] coordinates;
	private static int turn;
	private static int[] kings;
	private static int players;
	private static boolean switch_pawns;
	private static int number_of_dead_teams;

	/*
	 * 	Default constructor for the Chess class
	 *  Creates a Chess object
	 *  Written by Joseph
	 */
	public Chess() {
		number_of_dead_teams = 0;
		switch_pawns = false;
		turn = 0;
		list = new Piece[1];
		list[0] = null;
		coordinates = new int[2];
		kings = new int[]{0, 0, 0, 0};

		// Create a mouselistener for recording user mouse actions
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			System.out.println();
			System.out.println("X-Board_Position: " + e.getX() / 50);
			System.out.println("Y-Board_Position: " + e.getY() / 50);
			choosePiece(e.getX() / 50, e.getY() / 50);
			}
		});
	}

	/*
	 *  Main method for the Chess game
	 *  Creates the game and sets up the board
	 *  Prompts user to decide 2/4 player game
	 *  Written by Joseph
	 */
	public static void main(String[] args){
		createNewGame();
		printBoard();

		System.out.println();
		System.out.println();
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Type 2 for 2 players, or 4 for 4 players");
		players = keyboard.nextInt();
		System.out.println("Team 0 starts the game, continuing in a clockwise fashion");

		if(players == 2) {
			System.out.println("One player will be Teams 0 + 2 and the other player will be Teams 1 + 3");
		}

		frame.setSize(800, 800);

		Chess gui = new Chess();
		frame.add(gui);
		frame.getContentPane().add(gui);
	}

	/*
	 *  Function that handles user mouse inputs
	 *  Checks if user clicks are valid, and outputs message
	 *  Written by Joseph
	 */
	public static void choosePiece(int x, int y) {
		if(isFilled(board[x][y]) && list[0] == null && board[x][y].getTeam() == turn) {
			list[0] = board[x][y];
			coordinates[0] = x;
			coordinates[1] = y;
		}
		else {
			if(list[0] == null && (board[x][y].getTeam() == 4 || board[x][y].getTeam() == 5)) {
				System.out.println("Choose a piece to move first.");
			}
			if(list[0] != null && list[0].isValidMove(coordinates[0], coordinates[1], x, y, list[0].getTeam(), board[x][y].getTeam(), board[coordinates[0]][coordinates[1]].getMoves(), board[x][y].getMoves(), switch_pawns) && board[x][y].getTeam() != 5) {
				int[][] filledspaces = new int[16][16];
				for(int i = 0; i < 16; i++) {
					for(int j = 0; j < 16; j++)
						if(board[i][j].getTeam() == 0 || board[i][j].getTeam() == 1 || board[i][j].getTeam() == 2 || board[i][j].getTeam() == 3)
							filledspaces[i][j] = 0;
						else
							filledspaces[i][j] = 1;
				}
				if(list[0].isClear(list[0], coordinates[0], coordinates[1], x, y, filledspaces, list[0].getTeam(), board[x][y].getTeam())) {				
					movePiece(list[0], coordinates[0], coordinates[1], x , y, filledspaces);
					if(number_of_dead_teams != 3) {
						System.out.println("The move is valid.");
					}
					turn = turn + 1;
					if(turn == 4) {
						turn = 0;
					}
					while(kings[turn] == 1) {
						turn++;
						if(turn == 4) {
							turn = 0;
						}
					}
					if(number_of_dead_teams != 3) {
						System.out.println("It is player " + turn + "'s move now");
					}
				}
				else
					System.out.println("There is a piece in the way.");
				list[0] = null;
			}
			else {
				System.out.println("The move is not valid. Try again.");
				list[0] = null;
			}
		}
	}

	/* 
	 *  Moves pieces on the global board
	 *  Checks if the king has been taken, if it has, that team has lost
	 *  Checks if a pawn has made it to the middle, where it can be upgraded
	 *  Written by Joseph
	 */
	public static void movePiece(Piece piece, int x1, int y1, int x2, int y2, int[][] tboard) {
		piece.setMoves(piece.getMoves() + 1);

		if(piece.validCastle(x1, y1, x2, y2, tboard)) {
			board[x2][y2].setMoves(board[x2][y2].getMoves() + 1);
			if(x2 - x1 == 4) {
				board[9][y2] = piece;
				board[8][y2] = board[x2][y2];
			}
			
			if(x2 - x1 == -3) {
				board[5][y2] = piece;
				board[6][y2] = board[x2][y2];
			}
			if(y2 - y1 == 4) {
				board[x2][9] = piece;
				board[x2][8] = board[x2][y2];
			}
			if(y2 - y1 == -3) {
				board[x2][5] = piece;
				board[x2][6] = board[x2][y2];
			}
			Piece empty1 = new Piece();
			board[x2][y2] = empty1;
		}
		else {
			int t = board[x2][y2].getTeam();
			if(board[x2][y2].toString().equals("K")) {
				board[x2][y2] = piece;
				boolean loser = true;
				for(int i = 0; i < 16; i ++) {
					for(int j = 0; j < 16; j++) {
						if(board[i][j].toString().equals("K") && board[i][j].getTeam() == t) {
							System.out.println("Team " + t + " is now down to one king");
							loser = false;
						}
					}
				}
				if(loser) {
					kings[t] = 1;
					System.out.println("Team " + t + " has lost the game because their king died");

					for(int r = 0; r < 16; r++) {
						for(int c = 0; c < 16; c++) {
							if(board[r][c].getTeam() == t) {
								board[r][c] = new Piece();
							}
						}
					}

					board[x2][y2] = piece;
					System.out.println(players);

					number_of_dead_teams = 0;
					int winner = -1;
					for(int a = 0; a < 4; a++) {
						number_of_dead_teams += kings[a];
							if(kings[a] == 0) {
								winner = a;
							}
						}
					if(number_of_dead_teams == 3) {
						System.out.println("Team " + winner + " is the winner!!!!");
					}
					else if(players == 2) {
						changeTeam(t);
						switch_pawns = true;
					}
				}
			}
			else {
				board[x2][y2] = piece;
			}
		}
		Piece empty = new Piece();
		board[x1][y1] = empty;

		if(piece.movesTwo(x1, y1, x2, y2)) {
			piece.setMoves(piece.getMoves() + 1);
		}

		if(piece.changeType(piece.getMoves())) {
			if(piece.getTeam() == 0) {
				Piece q0 = new Queen(0, loadImage("peach.png"), 0);
				board[x2][y2] = q0;
			}
			else if(piece.getTeam() == 1) {
				Piece q1 = new Queen(1, loadImage("sandy.png"), 0);
				board[x2][y2] = q1;
			}
			else if(piece.getTeam() == 2) {
				Piece q2 = new Queen(2, loadImage("piglet.png"), 0);
				board[x2][y2] = q2;
			}
			else {
				Piece q3 = new Queen(3, loadImage("minnie.png"), 0);
				board[x2][y2] = q3;
			}
		}

		frame.repaint();
	}

	/*
	 *  Changes the team for when there are two players
	 *  Used to prevent multiple consecutive moves by one player
	 *  Written by Joseph
	 */
	public static void changeTeam(int team) {
		System.out.println();
		if(team == 0 || team == 2) {
			turn = 1;
			if(team == 2) {
				System.out.print("Team 0 became Team 2. ");
				System.out.println("Teams 1 and 3 combined into Team 1");
				for(int r = 0; r < 16; r ++) {
					for(int c = 0; c < 16; c ++) {
						if(board[r][c].getTeam() == 0) {
							board[r][c].setTeam(2);
							kings[2] = 0;
							kings[0] = 1;
						}
					}
				}
			}
			for(int i = 0; i < 16; i ++) {
				for(int j = 0; j < 16; j ++) {
					if(board[i][j].getTeam() == 3) {
						board[i][j].setTeam(1);
						kings[3] = 1;
					}
				}
			}
		}
		else {
			turn = 0;
			if(team == 1) {
				System.out.print("Team 3 became Team 1. ");
				System.out.println("Teams 0 and 2 combined into Team 2");
				for(int r = 0; r < 16; r ++) {
					for(int c = 0; c < 16; c ++) {
						if(board[r][c].getTeam() == 3) {
							board[r][c].setTeam(1);
							kings[1] = 0;
							kings[3] = 1;
						}
					}
				}
			}
			for(int i = 0; i < 16; i ++) {
				for(int j = 0; j < 16; j ++) {
					if(board[i][j].getTeam() == 0) {
						board[i][j].setTeam(2);
						kings[0] = 1;
					}
				}
			}
		}
		System.out.println("NOW PAWNS CAN MOVE IN ALL DIRECTIONS and THEY WILL STILL CHANGE INTO QUEENS AFTER MOVING 6 BLOCKS");
	}

	/*
	 *  Used to determine whether or not a spot on the board
	 *  contains an actual piece or an empty tile
	 *  Written by Kyle
	 */
	public static boolean isFilled(Piece item) {
		if(item.getImage() != null)
			return true;
		return false;
	}

	/*
	 *  Prints the board to standard output
	 *  Each piece contains its own character representation,
	 *  while empty spots are represented by a '.'
	 *  Written by Kyle
	 */
	public static void printBoard() {
		for(int r = 0; r < board.length; ++r) {
			for(int c = 0; c < board[0].length; ++c) {
				if(board[r][c] == null) {
					System.out.print(". ");
				}
				else
					System.out.print(board[r][c] + " ");
			}
			System.out.println();
		}
	}

	/*
	 *  Loads in images given a string filename
	 *  Written by Kyle
	 */
	private static Image loadImage(String filename) {
		Image tempImage = null;
		if (filename != null && filename != "") {
			try {
				tempImage = Toolkit.getDefaultToolkit().getImage(imagePath + filename);
			}
			catch (Exception e) {
				System.out.println("error getImage with toolkit unable to load filename " + filename);
			}
		}
		return tempImage;
	}

	/*
	 *  Creates the board at the start of the game, with pieces
	 *  in their default locations
	 *  Creates piece objects and loads in default variables and images
	 *  Written by Joseph and Kyle
	 */
	public static void createNewGame() {
		board = new Piece[16][16];	// Create an empty board

		Piece empty = new Piece();
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
				board[i][j] = empty;
			}
		}

		for(int i = 0; i < 4; i++) {

			for(int j = 0; j < 4; j++) {
				Piece dead = new Dead(5, null, 0);
				board[i][j] = dead;
				board[15-i][j] = dead;
				board[i][15-j] = dead;
				board[15-i][15-j] = dead;
			}
		}

		for(int team = 0; team < 4; team++) {

			for(int i = 0; i < 8; i++) {

				if(team == 0) {
					Piece pawn = new Pawn(team, loadImage("toad.png"), 0);
					board[1][4+i] = pawn;
				}

				if(team == 3) {
					Piece pawn = new Pawn(team, loadImage("pluto.png"), 0);
					board[4+i][14] = pawn;
				}

				if(team == 2) {
					Piece pawn = new Pawn(team, loadImage("roo.png"), 0);
					board[14][4+i] = pawn;
				}

				if(team == 1) {
					Piece pawn = new Pawn(team, loadImage("gary.png"), 0);
					board[4+i][1] = pawn;
				}
			}
		}
				
		Piece king0 = new King(0, loadImage("mario.png"), 0);
		board[0][7] = king0;
		Piece queen0 = new Queen(0, loadImage("peach.png"), 0);
		board[0][8] = queen0;
		Piece bishop10 = new Bishop(0, loadImage("luigi.png"), 0);
		board[0][6] = bishop10;
		Piece bishop20 = new Bishop(0, loadImage("luigi.png"), 0);
		board[0][9] = bishop20;
		Piece knight10 = new Knight(0, loadImage("yoshi.png"), 0);
		board[0][5] = knight10;
		Piece knight20 = new Knight(0, loadImage("yoshi.png"), 0);
		board[0][10] = knight20;
		Piece rook10 = new Rook(0, loadImage("bowser.png"), 0);
		board[0][4] = rook10;
		Piece rook20 = new Rook(0, loadImage("bowser.png"), 0);
		board[0][11] = rook20;

		Piece king2 = new King(2, loadImage("pooh.png"), 0);
		board[15][7] = king2;
		Piece queen2 = new Queen(2, loadImage("piglet.png"), 0);
		board[15][8] = queen2;
		Piece bishop12 = new Bishop(2, loadImage("tigger.png"), 0);
		board[15][6] = bishop12;
		Piece bishop22 = new Bishop(2, loadImage("tigger.png"), 0);
		board[15][9] = bishop22;
		Piece knight12 = new Knight(2, loadImage("eeyore.png"), 0);
		board[15][5] = knight12;
		Piece knight22 = new Knight(2, loadImage("eeyore.png"), 0);
		board[15][10] = knight22;
		Piece rook12 = new Rook(2, loadImage("lumpy.gif"), 0);
		board[15][4] = rook12;
		Piece rook22 = new Rook(2, loadImage("lumpy.gif"), 0);
		board[15][11] = rook22;


		Piece king1 = new King(1, loadImage("spongebob.png"), 0);
		board[7][0] = king1;
		Piece queen1 = new Queen(1, loadImage("sandy.png"), 0);
		board[8][0] = queen1;
		Piece bishop11 = new Bishop(1, loadImage("squidward.png"), 0);
		board[6][0] = bishop11;
		Piece bishop21 = new Bishop(1, loadImage("squidward.png"), 0);
		board[9][0] = bishop21;
		Piece knight11 = new Knight(1, loadImage("patrick.png"), 0);
		board[10][0] = knight11;
		Piece knight21 = new Knight(1, loadImage("patrick.png"), 0);
		board[5][0] = knight21;
		Piece rook11 = new Rook(1, loadImage("larry.png"), 0);
		board[4][0] = rook11;
		Piece rook21 = new Rook(1, loadImage("larry.png"), 0);
		board[11][0] = rook21;

		Piece king3 = new King(3, loadImage("mickie.png"), 0);
		board[7][15] = king3;
		Piece queen3 = new Queen(3, loadImage("minnie.png"), 0);
		board[8][15] = queen3;
		Piece bishop13 = new Bishop(3, loadImage("donald.png"), 0);
		board[6][15] = bishop13;
		Piece bishop23 = new Bishop(3, loadImage("donald.png"), 0);
		board[9][15] = bishop23;
		Piece knight13 = new Knight(3, loadImage("daisy.png"), 0);
		board[10][15] = knight13;
		Piece knight23 = new Knight(3, loadImage("daisy.png"), 0);
		board[5][15] = knight23;
		Piece rook13 = new Rook(3, loadImage("goofy.png"), 0);
		board[4][15] = rook13;
		Piece rook23 = new Rook(3, loadImage("goofy.png"), 0);
		board[11][15] = rook23;
	}

	/*
	 *  Returns the board data structure
	 *  Written by Kyle
	 */
	public static Piece[][] getBoard() {
		return board;
	}

	/*
	 *  Draws the board and images on the JFrame
	 *  Provides instructions for what each piece represents
	 *  Gets called whenever repaint() is called
	 *  Written by Joseph and Kyle
	 */
	public void paintComponent(Graphics g) {
		Graphics2D gui = (Graphics2D) g;

		gui.setColor(Color.WHITE);
		for(int i = 200; i < 600;  i = i + 100) {
			for(int j = 0; j < 800; j = j + 100) {
				gui.fillRect(i,j,50,50);
			}
		}
		for(int i = 250; i < 600; i = i + 100) {
			for(int j = 50; j < 800; j = j + 100) {
				gui.fillRect(i,j,50,50);
			}
		}
		for(int i = 0; i < 200; i = i + 100) {
			for(int j = 200; j < 600; j = j + 100) {
				gui.fillRect(i,j,50,50);
			}
		}
		for(int i = 50; i < 200; i = i + 100) {
			for(int j = 250; j < 600; j = j + 100) {
				gui.fillRect(i,j,50,50);
			}
		}
		for(int i = 600; i < 800; i = i + 100) {
			for(int j = 200; j < 600; j = j + 100) {
				gui.fillRect(i,j,50,50);
			}
		}
		for(int i = 650; i < 800; i = i + 100) {
			for(int j = 250; j < 600; j = j + 100) {
				gui.fillRect(i,j,50,50);
			}
		}
		gui.setColor(Color.BLACK);
		for(int i = 250; i < 600; i = i + 100) {
			for(int j = 0; j < 800; j = j + 100) {
				gui.fillRect(i,j,50,50);
			}
		}
		for(int i = 200; i < 600; i = i + 100) {
			for(int j = 50; j < 800; j = j + 100) {
				gui.fillRect(i,j,50,50);
			}
		}
		for(int i = 50; i < 200; i = i + 100) {
			for(int j = 200; j < 600; j = j + 100) {
				gui.fillRect(i,j,50,50);
			}
		}
		for(int i = 0; i < 200; i = i + 100) {
			for(int j = 250; j < 600; j = j + 100) {
				gui.fillRect(i,j,50,50);
			}
		}
		for(int i = 650; i < 800; i = i + 100) {
			for(int j = 200; j < 600; j = j + 100) {
				gui.fillRect(i,j,50,50);
			}
		}
		for(int i = 600; i < 800; i = i + 100) {
			for(int j = 250; j < 600; j = j + 100) {
				gui.fillRect(i,j,50,50);
			}
		}
		for(int r = 0; r < 16; r++ ) {
			for(int c = 0; c < 16; c++) {
				gui.drawImage(board[r][c].getImage(), r*50, c*50, 50, 50, null);
			}
		}
		gui.setColor(Color.BLUE);
		gui.drawString("Team 0 are Mario Characters", 10, 20);
		gui.drawString("Pawns are Blue Toads", 10, 40);
		gui.drawString("Rooks are Bowser", 10, 60);
		gui.drawString("Knights are Yoshi", 10, 80);
		gui.drawString("Bishops are Luigi", 10, 100);
		gui.drawString("King is Mario", 10, 120);
		gui.drawString("Queen is Peach", 10, 140);

		gui.drawString("Team 1 are Spongebob Characters", 610, 20);
		gui.drawString("Pawns are Gary", 610, 40);
		gui.drawString("Rooks are Larry", 610, 60);
		gui.drawString("Knights are Patrick", 610, 80);
		gui.drawString("Bishops are Squidward", 610, 100);
		gui.drawString("King is Spongebob", 610, 120);
		gui.drawString("Queen is Sandy", 610, 140);

		gui.drawString("Team 2 are Pooh Bear Characters", 610, 630);
		gui.drawString("Pawns are Roo", 610, 650);
		gui.drawString("Rooks are Lumpy", 610, 670);
		gui.drawString("Knights are Eeyore", 610, 690);
		gui.drawString("Bishops are Tigger", 610, 710);
		gui.drawString("King is Pooh Bear", 610, 730);
		gui.drawString("Queen is Piglet", 610, 750);

		gui.drawString("Team 3 are Mickey Characters", 10, 630);
		gui.drawString("Pawns are Pluto", 10, 650);
		gui.drawString("Rooks are Goofy", 10, 670);
		gui.drawString("Knights are Daisy", 10, 690);
		gui.drawString("Bishops are Donald", 10, 710);
		gui.drawString("King is Mickey", 10, 730);
		gui.drawString("Queen is Minnie", 10, 750);
	}
}