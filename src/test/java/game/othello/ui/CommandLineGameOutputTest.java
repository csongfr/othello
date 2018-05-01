package game.othello.ui;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import game.othello.configuration.Preference;
import game.othello.model.Board;
import game.othello.model.Piece;

public class CommandLineGameOutputTest {

	private Board board;
	private Preference pref;
	private CommandLineGameOutput cli;
	private Map<Piece, String> pieceDecorator;

	@Before
	public void setup() {
		board = new Board(8, 8);
		board.place(Piece.WHITE, 3, 3);
		board.place(Piece.BLACK, 3, 4);
		board.place(Piece.BLACK, 4, 3);
		board.place(Piece.WHITE, 4, 4);

		pieceDecorator = new HashMap<>();
		pieceDecorator.put(Piece.BLACK, "X");
		pieceDecorator.put(Piece.WHITE, "O");
		pieceDecorator.put(Piece.EMPTY, "-");

		pref = new Preference();
		pref.setPieceDecorator(pieceDecorator);
		cli = new CommandLineGameOutput();
	}

	@Test
	public void testBoardToString() {
		String result = cli.boardToString(board, pref);
		String expected = 
				"--------\n" + 
				"--------\n" + 
				"--------\n" + 
				"---OX---\n" + 
				"---XO---\n" + 
				"--------\n" + 
				"--------\n" + 
				"--------\n";
		assertThat(result, is(expected));
		
		Map<Piece, String> anotherPieceDecorator = new HashMap<>();
		anotherPieceDecorator.put(Piece.BLACK, "B");
		anotherPieceDecorator.put(Piece.WHITE, "W");
		anotherPieceDecorator.put(Piece.EMPTY, ".");
		pref.setPieceDecorator(anotherPieceDecorator);
		result = cli.boardToString(board, pref);
		expected = 
				"........\n" + 
				"........\n" + 
				"........\n" + 
				"...WB...\n" + 
				"...BW...\n" + 
				"........\n" + 
				"........\n" + 
				"........\n";
		assertThat(result, is(expected));
		pref.setPieceDecorator(pieceDecorator);

		pref.hideAllCoordinate();
		pref.setShowLeftCoordinate(true);
		result = cli.boardToString(board, pref);
		expected = 
				"1 --------\n" + 
				"2 --------\n" + 
				"3 --------\n" + 
				"4 ---OX---\n" + 
				"5 ---XO---\n" + 
				"6 --------\n" + 
				"7 --------\n" + 
				"8 --------\n";
		assertThat(result, is(expected));
		
		pref.hideAllCoordinate();
		pref.setShowRightCoordinate(true);
		result = cli.boardToString(board, pref);
		expected = 
				"-------- 1\n" + 
				"-------- 2\n" + 
				"-------- 3\n" + 
				"---OX--- 4\n" + 
				"---XO--- 5\n" + 
				"-------- 6\n" + 
				"-------- 7\n" + 
				"-------- 8\n";
		assertThat(result, is(expected));
		
		pref.hideAllCoordinate();
		pref.setShowTopCoordinate(true);
		result = cli.boardToString(board, pref);
		expected = 
				"abcdefgh\n" +
				"--------\n" + 
				"--------\n" + 
				"--------\n" + 
				"---OX---\n" + 
				"---XO---\n" + 
				"--------\n" + 
				"--------\n" + 
				"--------\n";
		assertThat(result, is(expected));
		
		pref.hideAllCoordinate();
		pref.setShowBottomCoordinate(true);
		result = cli.boardToString(board, pref);
		expected = 
				"--------\n" + 
				"--------\n" + 
				"--------\n" + 
				"---OX---\n" + 
				"---XO---\n" + 
				"--------\n" + 
				"--------\n" + 
				"--------\n" +
				"abcdefgh\n";
		assertThat(result, is(expected));
		
		pref.showAllCoordinate();
		result = cli.boardToString(board, pref);
		expected = 
				"  abcdefgh\n" +
				"1 -------- 1\n" + 
				"2 -------- 2\n" + 
				"3 -------- 3\n" + 
				"4 ---OX--- 4\n" + 
				"5 ---XO--- 5\n" + 
				"6 -------- 6\n" + 
				"7 -------- 7\n" + 
				"8 -------- 8\n" +
				"  abcdefgh\n";
		assertThat(result, is(expected));
	}

}
