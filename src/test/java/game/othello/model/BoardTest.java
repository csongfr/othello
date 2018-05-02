package game.othello.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import game.othello.configuration.BoardConfiguration;
import game.othello.configuration.Preference;

public class BoardTest {

	private Board board;
	private Preference pref;

	@Before
	public void setup() {
		board = new Board(8, 8);
		pref = new Preference();
		Map<Disk, String> pieceDecorator = new HashMap<>();
		pieceDecorator.put(Disk.DARK, "X");
		pieceDecorator.put(Disk.LIGHT, "O");
		pieceDecorator.put(Disk.EMPTY, "-");
		pref.setDiskDecorator(pieceDecorator);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoardDimension() {
		new Board(0, 0);
	}

	@Test
	public void testInit() {
		Board origin = new Board(8, 8);
		assertThat(origin.getHeight(), is(8));
		assertThat(origin.getLength(), is(8));
		assertThat(origin.get(3, 2), is(Disk.EMPTY));

		board = new Board(origin);
		assertThat(origin.getHeight(), is(8));
		assertThat(origin.getLength(), is(8));
		assertThat(origin.get(3, 2), is(Disk.EMPTY));

		board.init(Disk.DARK, new Point(3, 4));
		assertThat(board.get(3, 4), is(Disk.DARK));
		assertThat(origin.get(3, 4), is(Disk.EMPTY));
		board.init(Disk.LIGHT, new Point(5, 2));
		assertThat(board.get(5, 2), is(Disk.LIGHT));
		assertThat(origin.get(5, 2), is(Disk.EMPTY));
		board.init(Disk.DARK, new Point(5, 2));
		assertThat(board.get(5, 2), is(Disk.DARK));
		board.init(Disk.EMPTY, new Point(5, 2));
		assertThat(board.get(5, 2), is(Disk.EMPTY));
	}

	@Test
	public void testInitFromConfiguration() {
		BoardConfiguration conf = new BoardConfiguration();
		conf.setHeight(8);
		conf.setLength(8);
		Map<Character, Disk> m1 = new HashMap<>();
		m1.put('d', Disk.LIGHT);
		Map<Character, Disk> m2 = new HashMap<>();
		m2.put('e', Disk.DARK);
		Map<Integer, Map<Character, Disk>> m = new HashMap<>();
		m.put(4, m1);
		m.put(5, m2);
		conf.setStartingPosition(m);
		board = new Board(conf);
		assertThat(board.getHeight(), is(8));
		assertThat(board.getLength(), is(8));
		assertThat(board.get(3, 3), is(Disk.LIGHT));
		assertThat(board.get(4, 4), is(Disk.DARK));
	}

	@Test
	public void testCanPlace() {
		/*
		 * 0 -------- 
		 * 1 -X------ 
		 * 2 --XX---- 
		 * 3 XXXOXXO- 
		 * 4 -XOXO--- 
		 * 5 ---X---- 
		 * 6 --------
		 * 7 -------- 
		 *   01234567
		 */
		board.init(Disk.LIGHT, 3, 3);
		board.init(Disk.LIGHT, 3, 6);
		board.init(Disk.LIGHT, 4, 2);
		board.init(Disk.LIGHT, 4, 4);
		board.init(Disk.DARK, 1, 1);
		board.init(Disk.DARK, 2, 2);
		board.init(Disk.DARK, 2, 3);
		board.init(Disk.DARK, 3, 0);
		board.init(Disk.DARK, 3, 1);
		board.init(Disk.DARK, 3, 2);
		board.init(Disk.DARK, 3, 4);
		board.init(Disk.DARK, 3, 5);
		board.init(Disk.DARK, 4, 1);
		board.init(Disk.DARK, 4, 3);
		board.init(Disk.DARK, 5, 3);
		assertThat(board.canPlace(Disk.LIGHT, new Point(1, 3)), is(true));
		assertThat(board.canPlace(Disk.LIGHT, new Point(0, 0)), is(true));
		assertThat(board.canPlace(Disk.LIGHT, new Point(3, 0)), is(false));
		assertThat(board.canPlace(Disk.LIGHT, new Point(3, 7)), is(false));
		assertThat(board.canPlace(Disk.LIGHT, new Point(3, 7)), is(false));
		assertThat(board.canPlace(Disk.LIGHT, new Point(4, 0)), is(true));
		assertThat(board.canPlace(Disk.LIGHT, new Point(5, 5)), is(false));
		assertThat(board.canPlace(Disk.LIGHT, new Point(7, 0)), is(false));
		assertThat(board.canPlace(Disk.DARK, new Point(5, 5)), is(true));
		assertThat(board.canPlace(Disk.EMPTY, new Point(5, 5)), is(false));
		assertThat(board.canPlace(Disk.LIGHT, new Point(100, 99)), is(false));
	}

	@Test
	public void testPlace() {
		Board origin = new Board(8, 8);
		/*
		 * 0 -------- 
		 * 1 -X------ 
		 * 2 --XX---- 
		 * 3 XXXOXXO- 
		 * 4 ---XO--- 
		 * 5 ---O---- 
		 * 6 ---X----
		 * 7 -------- 
		 *   01234567
		 */
		origin.init(Disk.LIGHT, 3, 3);
		origin.init(Disk.LIGHT, 3, 6);
		origin.init(Disk.LIGHT, 4, 4);
		origin.init(Disk.LIGHT, 5, 3);
		origin.init(Disk.DARK, 1, 1);
		origin.init(Disk.DARK, 2, 2);
		origin.init(Disk.DARK, 2, 3);
		origin.init(Disk.DARK, 3, 0);
		origin.init(Disk.DARK, 3, 1);
		origin.init(Disk.DARK, 3, 2);
		origin.init(Disk.DARK, 3, 4);
		origin.init(Disk.DARK, 3, 5);
		origin.init(Disk.DARK, 4, 3);
		origin.init(Disk.DARK, 6, 3);

		board = new Board(origin);
		Board expected = new Board(origin);

		board.place(Disk.LIGHT, new Point(0, 0));
		expected.init(Disk.LIGHT, 0, 0);
		expected.init(Disk.LIGHT, 1, 1);
		expected.init(Disk.LIGHT, 2, 2);
		assertThat(same(board, expected), is(true));

		board = new Board(origin);
		expected = new Board(origin);
		board.place(Disk.LIGHT, new Point(1, 3));
		expected.init(Disk.LIGHT, 1, 3);
		expected.init(Disk.LIGHT, 2, 3);
		assertThat(same(board, expected), is(true));

		board = new Board(origin);
		expected = new Board(origin);
		board.place(Disk.LIGHT, new Point(7, 3));
		expected.init(Disk.LIGHT, 7, 3);
		expected.init(Disk.LIGHT, 6, 3);
		assertThat(same(board, expected), is(true));
	}

	@Test
	public void testIsFull() {
		board = new Board(2, 2);
		assertThat(board.isFull(), is(false));
		board.init(Disk.DARK, 0, 0);
		board.init(Disk.DARK, 0, 1);
		board.init(Disk.DARK, 1, 0);
		assertThat(board.isFull(), is(false));
		board.init(Disk.DARK, 1, 1);
		assertThat(board.isFull(), is(true));
		board.init(Disk.LIGHT, 1, 1);
		assertThat(board.isFull(), is(true));
	}

	@Test
	public void testGetLegalMoves() {
		/*
		 * 0 -------- 
		 * 1 -------- 
		 * 2 -------- 
		 * 3 ---OX--- 
		 * 4 -------- 
		 * 5 -------- 
		 * 6 --------
		 * 7 -------- 
		 *   01234567
		 */
		board.init(Disk.LIGHT, 3, 3);
		board.init(Disk.DARK, 3, 4);
		assertThat(board.getLegalMoves(Disk.DARK).size(), is(1));
		assertThat(board.getLegalMoves(Disk.DARK).iterator().next(), is(new Point(3, 2)));
		assertThat(board.getLegalMoves(Disk.LIGHT).size(), is(1));
		assertThat(board.getLegalMoves(Disk.LIGHT).iterator().next(), is(new Point(3, 5)));
		/*
		 * 0 -------- 
		 * 1 -------- 
		 * 2 -------- 
		 * 3 ---OO--- 
		 * 4 -------- 
		 * 5 -------- 
		 * 6 --------
		 * 7 -------- 
		 *   01234567
		 */
		board.init(Disk.LIGHT, 3, 4);
		assertThat(board.getLegalMoves(Disk.DARK).size(), is(0));
		assertThat(board.getLegalMoves(Disk.LIGHT).size(), is(0));
		/*
		 * 0 -------- 
		 * 1 -------- 
		 * 2 -------- 
		 * 3 OOOOX--- 
		 * 4 -------- 
		 * 5 -------- 
		 * 6 --------
		 * 7 -------- 
		 *   01234567
		 */
		board = new Board(8, 8);
		board.init(Disk.LIGHT, 3, 0);
		board.init(Disk.LIGHT, 3, 1);
		board.init(Disk.LIGHT, 3, 2);
		board.init(Disk.LIGHT, 3, 3);
		board.init(Disk.DARK, 3, 4);
		assertThat(board.getLegalMoves(Disk.DARK).size(), is(0));
		assertThat(board.getLegalMoves(Disk.LIGHT).size(), is(1));
		assertThat(board.getLegalMoves(Disk.LIGHT).iterator().next(), is(new Point(3, 5)));
		/*
		 * 0 X------- 
		 * 1 -X------ 
		 * 2 --X----- 
		 * 3 ---O---- 
		 * 4 -------- 
		 * 5 -------- 
		 * 6 --------
		 * 7 -------- 
		 *   01234567
		 */
		board = new Board(8, 8);
		board.init(Disk.DARK, 0, 0);
		board.init(Disk.DARK, 1, 1);
		board.init(Disk.DARK, 2, 2);
		board.init(Disk.LIGHT, 3, 3);
		assertThat(board.getLegalMoves(Disk.LIGHT).size(), is(0));
		assertThat(board.getLegalMoves(Disk.DARK).size(), is(1));
		assertThat(board.getLegalMoves(Disk.DARK).iterator().next(), is(new Point(4, 4)));
		
		Board fullBoard = new Board(2, 2);
		fullBoard.init(Disk.LIGHT, 0, 0);
		fullBoard.init(Disk.LIGHT, 1, 1);
		fullBoard.init(Disk.DARK, 0, 1);
		fullBoard.init(Disk.DARK, 1, 0);
		assertThat(fullBoard.getLegalMoves(Disk.DARK).size(), is(0));
		assertThat(fullBoard.getLegalMoves(Disk.LIGHT).size(), is(0));
		/*
		 * 0 XOOO 
		 * 1 OXOO 
		 * 2 OOXO 
		 * 3 OOO- 
		 *   0123
		 */
		Board endBoard = new Board(4, 4);
		endBoard.init(Disk.DARK, 0, 0);
		endBoard.init(Disk.DARK, 1, 1);
		endBoard.init(Disk.DARK, 2, 2);
		endBoard.init(Disk.LIGHT, 0, 1);
		endBoard.init(Disk.LIGHT, 0, 2);
		endBoard.init(Disk.LIGHT, 0, 3);
		endBoard.init(Disk.LIGHT, 1, 0);
		endBoard.init(Disk.LIGHT, 1, 2);
		endBoard.init(Disk.LIGHT, 1, 3);
		endBoard.init(Disk.LIGHT, 2, 0);
		endBoard.init(Disk.LIGHT, 2, 1);
		endBoard.init(Disk.LIGHT, 2, 3);
		endBoard.init(Disk.LIGHT, 3, 0);
		endBoard.init(Disk.LIGHT, 3, 1);
		endBoard.init(Disk.LIGHT, 3, 2);
		assertThat(endBoard.getLegalMoves(Disk.DARK).size(), is(0));
		assertThat(endBoard.getLegalMoves(Disk.LIGHT).size(), is(0));
	}
	
	@Test
	public void testGetEndStatus() {
		Preference emptyPref = new Preference();
		/*
		 * 0 -------- 
		 * 1 -------- 
		 * 2 -------- 
		 * 3 ---OO--- 
		 * 4 -------- 
		 * 5 -------- 
		 * 6 --------
		 * 7 -------- 
		 *   01234567
		 */
		board.init(Disk.LIGHT, 3, 3);
		board.init(Disk.LIGHT, 3, 4);
		assertThat(board.getEndStatus(pref), is("Player 'O' wins ( 2 vs 0 )"));
		assertThat(board.getEndStatus(emptyPref), is("Player 'LIGHT' wins ( 2 vs 0 )"));

		board.init(Disk.DARK, 3, 4);
		// don't worry about the terminate rule
		assertThat(board.getEndStatus(pref), is("It's a draw"));
		assertThat(board.getEndStatus(emptyPref), is("It's a draw"));

		board.init(Disk.DARK, 3, 3);
		board.init(Disk.LIGHT, 4, 4);
		assertThat(board.getEndStatus(pref), is("Player 'X' wins ( 2 vs 1 )"));
		assertThat(board.getEndStatus(emptyPref), is("Player 'DARK' wins ( 2 vs 1 )"));
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
