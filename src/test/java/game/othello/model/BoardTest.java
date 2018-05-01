package game.othello.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import game.othello.configuration.Preference;

public class BoardTest {

	private Board board;
	private Preference pref;

	@Before
	public void setup() {
		board = new Board(8, 8);
		pref = new Preference();
		Map<Piece, String> pieceDecorator = new HashMap<>();
		pieceDecorator.put(Piece.BLACK, "X");
		pieceDecorator.put(Piece.WHITE, "O");
		pieceDecorator.put(Piece.EMPTY, "-");
		pref.setPieceDecorator(pieceDecorator);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBoardDimension() {
		new Board(0, 0);
	}

	@Test
	public void testInit() {
		board = new Board(8, 8);
		assertThat(board.getHeight(), is(8));
		assertThat(board.getLength(), is(8));
		assertThat(board.get(3, 2), is(Piece.EMPTY));
		
		board.init(Piece.BLACK, 3, 4);
		assertThat(board.get(3, 4), is(Piece.BLACK));
		board.init(Piece.WHITE, 5, 2);
		assertThat(board.get(5, 2), is(Piece.WHITE));
		board.init(Piece.BLACK, 5, 2);
		assertThat(board.get(5, 2), is(Piece.BLACK));
		board.init(Piece.EMPTY, 5, 2);
		assertThat(board.get(5, 2), is(Piece.EMPTY));
	}

	@Test
	public void testCanPlace() {
		/*
		 *0 -------- 
		 *1 -X------  
		 *2 --XX----  
		 *3 XXXOXXO-  
		 *4 -XOXO--- 
		 *5 ---X----  
		 *6 --------  
		 *7 --------
		 *  01234567
		 */
		board.init(Piece.WHITE, 3, 3);
		board.init(Piece.WHITE, 3, 6);
		board.init(Piece.WHITE, 4, 2);
		board.init(Piece.WHITE, 4, 4);
		board.init(Piece.BLACK, 1, 1);
		board.init(Piece.BLACK, 2, 2);
		board.init(Piece.BLACK, 2, 3);
		board.init(Piece.BLACK, 3, 0);
		board.init(Piece.BLACK, 3, 1);
		board.init(Piece.BLACK, 3, 2);
		board.init(Piece.BLACK, 3, 4);
		board.init(Piece.BLACK, 3, 5);
		board.init(Piece.BLACK, 4, 1);
		board.init(Piece.BLACK, 4, 3);
		board.init(Piece.BLACK, 5, 3);
		assertThat(board.canPlace(Piece.WHITE, 1, 3), is(true));
		assertThat(board.canPlace(Piece.WHITE, 0, 0), is(true));
		assertThat(board.canPlace(Piece.WHITE, 3, 0), is(false));
		assertThat(board.canPlace(Piece.WHITE, 3, 7), is(false));
		assertThat(board.canPlace(Piece.WHITE, 3, 7), is(false));
		assertThat(board.canPlace(Piece.WHITE, 4, 0), is(true));
		assertThat(board.canPlace(Piece.WHITE, 5, 5), is(false));
		assertThat(board.canPlace(Piece.WHITE, 7, 0), is(false));
		assertThat(board.canPlace(Piece.BLACK, 5, 5), is(true));
		assertThat(board.canPlace(Piece.EMPTY, 5, 5), is(false));
		assertThat(board.canPlace(Piece.WHITE, 100, 99), is(false));
	}
	
	@Test
	public void testPlace() {
		Board origin = new Board(8, 8);
		/*
		 *0 -------- 
		 *1 -X------  
		 *2 --XX----  
		 *3 XXXOXXO-  
		 *4 ---XO--- 
		 *5 ---O----  
		 *6 ---X----  
		 *7 --------
		 *  01234567
		 */
		origin.init(Piece.WHITE, 3, 3);
		origin.init(Piece.WHITE, 3, 6);
		origin.init(Piece.WHITE, 4, 4);
		origin.init(Piece.WHITE, 5, 3);
		origin.init(Piece.BLACK, 1, 1);
		origin.init(Piece.BLACK, 2, 2);
		origin.init(Piece.BLACK, 2, 3);
		origin.init(Piece.BLACK, 3, 0);
		origin.init(Piece.BLACK, 3, 1);
		origin.init(Piece.BLACK, 3, 2);
		origin.init(Piece.BLACK, 3, 4);
		origin.init(Piece.BLACK, 3, 5);
		origin.init(Piece.BLACK, 4, 3);
		origin.init(Piece.BLACK, 6, 3);
		
		board = new Board(origin);
		Board expected = new Board(origin);
		
		board.place(Piece.WHITE, 0, 0);
		expected.init(Piece.WHITE, 0, 0);
		expected.init(Piece.WHITE, 1, 1);
		expected.init(Piece.WHITE, 2, 2);
		assertThat(same(board, expected), is(true));
		
		board = new Board(origin);
		expected = new Board(origin);
		board.place(Piece.WHITE, 1, 3);
		expected.init(Piece.WHITE, 1, 3);
		expected.init(Piece.WHITE, 2, 3);
		assertThat(same(board, expected), is(true));
		
		board = new Board(origin);
		expected = new Board(origin);
		board.place(Piece.WHITE, 7, 3);
		expected.init(Piece.WHITE, 7, 3);
		expected.init(Piece.WHITE, 6, 3);
		assertThat(same(board, expected), is(true));
	}
	
	@Test
	public void testIsFull() {
		board = new Board(2, 2);
		assertThat(board.isFull(), is(false));
		board.init(Piece.BLACK, 0, 0);
		board.init(Piece.BLACK, 0, 1);
		board.init(Piece.BLACK, 1, 0);
		assertThat(board.isFull(), is(false));
		board.init(Piece.BLACK, 1, 1);
		assertThat(board.isFull(), is(true));
		board.init(Piece.WHITE, 1, 1);
		assertThat(board.isFull(), is(true));
	}
	
	@Test
	public void testCanPlay() {
		/*
		 *0 -------- 
		 *1 --------  
		 *2 --------  
		 *3 ---OX---  
		 *4 -------- 
		 *5 --------  
		 *6 --------  
		 *7 --------
		 *  01234567
		 */
		board.init(Piece.WHITE, 3, 3);
		board.init(Piece.BLACK, 3, 4);
		assertThat(board.canPlay(Piece.BLACK), is(true));
		assertThat(board.canPlay(Piece.WHITE), is(true));
		/*
		 *0 -------- 
		 *1 --------  
		 *2 --------  
		 *3 ---OO---  
		 *4 -------- 
		 *5 --------  
		 *6 --------  
		 *7 --------
		 *  01234567
		 */
		board.init(Piece.WHITE, 3, 4);
		assertThat(board.canPlay(Piece.BLACK), is(false));
		assertThat(board.canPlay(Piece.WHITE), is(false));
		/*
		 *0 -------- 
		 *1 --------  
		 *2 --------  
		 *3 OOOOX---  
		 *4 -------- 
		 *5 --------  
		 *6 --------  
		 *7 --------
		 *  01234567
		 */
		board = new Board(8, 8);
		board.init(Piece.WHITE, 3, 0);
		board.init(Piece.WHITE, 3, 1);
		board.init(Piece.WHITE, 3, 2);
		board.init(Piece.WHITE, 3, 3);
		board.init(Piece.BLACK, 3, 4);
		assertThat(board.canPlay(Piece.BLACK), is(false));
		assertThat(board.canPlay(Piece.WHITE), is(true));
		/*
		 *0 X------- 
		 *1 -X------  
		 *2 --X-----  
		 *3 ---O----  
		 *4 -------- 
		 *5 --------  
		 *6 --------  
		 *7 --------
		 *  01234567
		 */
		board = new Board(8, 8);
		board.init(Piece.BLACK, 0, 0);
		board.init(Piece.BLACK, 1, 1);
		board.init(Piece.BLACK, 2, 2);
		board.init(Piece.WHITE, 3, 3);
		assertThat(board.canPlay(Piece.BLACK), is(true));
		assertThat(board.canPlay(Piece.WHITE), is(false));
		
		Board fullBoard = new Board(2, 2);
		fullBoard.init(Piece.WHITE, 0, 0);
		fullBoard.init(Piece.WHITE, 1, 1);
		fullBoard.init(Piece.BLACK, 0, 1);
		fullBoard.init(Piece.BLACK, 1, 0);
		assertThat(fullBoard.canPlay(Piece.BLACK), is(false));
		assertThat(fullBoard.canPlay(Piece.WHITE), is(false));
		/*
		 *0 XOOO 
		 *1 OXOO 
		 *2 OOXO
		 *3 OOO- 
		 *  0123
		 */
		Board endBoard = new Board(4, 4);
		endBoard.init(Piece.BLACK, 0, 0);
		endBoard.init(Piece.BLACK, 1, 1);
		endBoard.init(Piece.BLACK, 2, 2);
		endBoard.init(Piece.WHITE, 0, 1);
		endBoard.init(Piece.WHITE, 0, 2);
		endBoard.init(Piece.WHITE, 0, 3);
		endBoard.init(Piece.WHITE, 1, 0);
		endBoard.init(Piece.WHITE, 1, 2);
		endBoard.init(Piece.WHITE, 1, 3);
		endBoard.init(Piece.WHITE, 2, 0);
		endBoard.init(Piece.WHITE, 2, 1);
		endBoard.init(Piece.WHITE, 2, 3);
		endBoard.init(Piece.WHITE, 3, 0);
		endBoard.init(Piece.WHITE, 3, 1);
		endBoard.init(Piece.WHITE, 3, 2);
		assertThat(endBoard.canPlay(Piece.BLACK), is(false));
		assertThat(endBoard.canPlay(Piece.WHITE), is(false));
	}
	
	@Test
	public void testGetEndStatus() {
		Preference emptyPref = new Preference();
		/*
		 *0 -------- 
		 *1 --------  
		 *2 --------  
		 *3 ---OO---  
		 *4 -------- 
		 *5 --------  
		 *6 --------  
		 *7 --------
		 *  01234567
		 */
		board.init(Piece.WHITE, 3, 3);
		board.init(Piece.WHITE, 3, 4);
		assertThat(board.getEndStatus(pref), is("Player 'O' wins ( 2 vs 0 )"));
		assertThat(board.getEndStatus(emptyPref), is("Player 'WHITE' wins ( 2 vs 0 )"));
		
		board.init(Piece.BLACK, 3, 4);
		// don't worry about the terminate rule
		assertThat(board.getEndStatus(pref), is("It's a draw"));
		assertThat(board.getEndStatus(emptyPref), is("It's a draw"));
		
		board.init(Piece.BLACK, 3, 3);
		board.init(Piece.WHITE, 4, 4);
		assertThat(board.getEndStatus(pref), is("Player 'X' wins ( 2 vs 1 )"));
		assertThat(board.getEndStatus(emptyPref), is("Player 'BLACK' wins ( 2 vs 1 )"));
	}
	
	
	private static boolean same(Board b1, Board b2) {
		if (b1.getHeight() != b2.getHeight() || b1.getLength() != b2.getLength()) {
			return false;
		}
		for (int i = 0; i < b1.getHeight(); i++) {
			for (int j = 0; j < b1.getLength(); j++) {
				if (b1.get(i, j) != b2.get(i, j)) {
					return false;
				}
			}
		}
		return true;
	}
}
