import java.io.*;

public class Sudoku {
	/*
	 * Solves a 9x9 sudoku puzzle by completing an incomplete one given in a text file
	 * This program implements a Backtracking algorithm ////TODO with Forward Checking (BTFC)
	 * Note that interesting computing time results only start to appear for 'hard' puzzles (20 hints or fewer)
	 */
	
	public static int[] digits = {1,2,3,4,5,6,7,8,9};
	private static final int BOARD_SIZE = 9;
	private static int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
	public static int iter = 0;
	//private static int[][] possibleValues = new int[BOARD_SIZE][BOARD_SIZE];
	
	/*
	 * Checks if there's an input file given, then calls methods to solve puzzle
	 */
	public static void main(String[] args) throws Exception {
		if(args.length == 0) {
	    	System.out.println("Exécution : \"java Sudoku fichier.txt\"");
	        System.exit(0);
	    }
		else {
			readBoard(args[0]);
			System.out.println("Puzzle Initial : ");
			displayBoard();
			solve(0,0);
		}
	}
	
	/*
	 * Parses a text file and stores puzzle into board
	 */
	public static void readBoard(String filepath) throws FileNotFoundException {
		try {
			FileReader input = new FileReader(filepath);
			BufferedReader reader = new BufferedReader(input);
			String currentLine; int row = 0; int col = 0; 
			
			while((currentLine = reader.readLine()) != null && row < 9) {
				col = 0;
				
				if(currentLine.split(" ").length != 9)
					throw new Exception("Format du fichier invalide");

				for(String digit: currentLine.split(" ")) {
					board[row][col] = Integer.parseInt(digit);
					col++;
				}
				row++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/* 
	 * Completes the sudoku puzzle
	 */
	public static void solve(int row, int col) throws Exception {
		iter++;

	    if(row > 8) {
			System.out.println("Résolu en " + iter + " iterations");
			System.out.println("Puzzle Final : ");
			displayBoard();
			System.exit(0);
	    } else {
			// Moves on to next cell if already filled
			if(board[row][col] != 0)
				next(row, col);
		    else{
		    	// Fills in empty cell with valid value
		    	for(int val : digits) {
		        	if(isValid(row, col, val)) {
		               board[row][col] = val ;
				       // System.out.println("Added value " + val + " at [" + (row+1) + ", " + (col+1) + "]");
		               // Calls solve on next cell
		               next(row, col) ;
		            }
		         }
	
		         // Backtracks if no valid value was found
		         board[row][col] = 0 ;
		     }
	    }
	}
	
	/*
	 * Calls solve for the next cell
	 */
	public static void next(int row, int col) throws Exception {
		if(col < 8)
			solve(row, col+1);
	    else
	        solve(row+1, 0);
	}
	
	/*
	 * Checks whether a value is valid in a given cell
	 */
	public static boolean isValid(int row, int col, int val) {
		boolean present;
		
		present = (isPresentInRow(row, val) || isPresentInCol(col, val) || isPresentInBox(row, col, val));
		return !present;
	}
	
	/*
	 * Checks whether val is present in row
	 */
	private static boolean isPresentInRow(int row, int val) {
		boolean present = false;
		for(int col = 0; col < 9; col++)
			if(board[row][col] == val)
				present = true;
				
		return present;
	}

	/*
	 * Checks whether val is present in column
	 */
	private static boolean isPresentInCol(int col, int val) {
		boolean present = false;
		for(int row = 0; row < 9; row++)
			if(board[row][col] == val)
				present = true;
				
		return present;
	}

	/*
	 * Checks whether val is present in the box containing cell[row, col]
	 */
	private static boolean isPresentInBox(int row, int col, int val) {
		boolean present = false;
		
		// Integer division ensures we get the proper box offset
		row = (row / 3) * 3 ;
		col = (col / 3) * 3 ;

		for(int r = 0; r < 3; r++)
			for(int c = 0; c < 3; c++)
				if(board[row+r][col+c] == val)
					present = true;

	   return present;
	}
	   
	/*
	 * Displays content of current board
	 */
	public static void displayBoard() {
		int i = 0, j = 0;
		System.out.println(" +-----------------------+");
		for(int[] row : board) {
			if((i == 3) || (i == 6))
				System.out.println(" |-------+-------+-------|");
			j = 0;
			for(int col : row) {
				if(j % 3 == 0)
					System.out.print(" | ");
				else
					System.out.print(" ");
				System.out.print(col);
				j++;
			}
			System.out.println(" | ");
			i++;
		}
		System.out.println(" +-----------------------+");
		System.out.println();
	}

}
