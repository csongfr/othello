package game.othello.ui;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import game.othello.configuration.Preference;
import game.othello.model.Board;
import game.othello.model.Disk;

public class CommandLineGameOutputTest {

	private Board board;
	private Preference pref;
	private CommandLineGameOutput cli;
	private Map<Disk, String> diskDecorator;

	@Before
	public void setup() {
		board = new Board(8, 8);
		board.init(Disk.LIGHT, 3, 3);
		board.init(Disk.DARK, 3, 4);
		board.init(Disk.DARK, 4, 3);
		board.init(Disk.LIGHT, 4, 4);

		diskDecorator = new HashMap<>();
		diskDecorator.put(Disk.DARK, "X");
		diskDecorator.put(Disk.LIGHT, "O");
		diskDecorator.put(Disk.EMPTY, "-");

		pref = new Preference();
		pref.setDiskDecorator(diskDecorator);
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
		
		Map<Disk, String> anotherDiskDecorator = new HashMap<>();
		anotherDiskDecorator.put(Disk.DARK, "B");
		anotherDiskDecorator.put(Disk.LIGHT, "W");
		anotherDiskDecorator.put(Disk.EMPTY, ".");
		pref.setDiskDecorator(anotherDiskDecorator);
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
		pref.setDiskDecorator(diskDecorator);

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
