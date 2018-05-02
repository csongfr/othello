package game.othello.model;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import game.othello.configuration.BoardConfiguration;
import game.othello.configuration.Preference;

public class Board {

	private Disk[][] data;

	private static List<Point> directions = Stream.of(
			new Point(-1, -1), new Point(-1, 0), new Point(-1, 1),
			new Point(0, -1), new Point(0, 1), 
			new Point(1, -1), new Point(1, 0), new Point(1, 1))
			.collect(Collectors.toList());

	public Board(int height, int length) {
		if (height == 0 || length == 0) {
			throw new IllegalArgumentException("Border height or length cannot be 0");
		}
		data = new Disk[height][length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				data[i][j] = Disk.EMPTY;
			}
		}
	}

	public Board(Board from) {
		this.data = new Disk[from.getHeight()][from.getLength()];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				data[i][j] = from.data[i][j];
			}
		}
	}
	
	public Board(BoardConfiguration configuration) {
		this(configuration.getHeight(), configuration.getLength());
		for (Entry<Integer, Map<Character, Disk>> line : configuration.getStartingPosition().entrySet()) {
			for (Entry<Character, Disk> col : line.getValue().entrySet()) {
				init(col.getValue(), new Point(line.getKey() - 1, col.getKey() - 'a'));
			}
		}
	}

	/**
	 * Initialize board with a Disk. Used to customize the board before starting game.
	 * 
	 * @param p
	 * @param rowIndex
	 * @param colIndex
	 */
	public void init(Disk d, Point p) {
		if (!outOfBound(p)) {
			data[p.x][p.y] = d;
		}
	}
	
	public void init(Disk d, int rowIndex, int colIndex) {
		this.init(d, new Point(rowIndex, colIndex));
	}

	public int getHeight() {
		return data.length;
	}

	public int getLength() {
		// constructor ensure that row cannot be 0
		return data[0].length;
	}

	public Disk get(int rowIndex, int colIndex) {
		return data[rowIndex][colIndex];
	}

	/**
	 * Place a Disk to the board.
	 * 
	 * In order to reduce the time complexity, this method <b>assumes</b> that the
	 * move is legal. Caller should invoke {@link #canPlace(Disk, int, int)} to
	 * check if not sure of this point.
	 * 
	 * @param d
	 * @param p
	 */
	public void place(Disk d, Point p) {
		data[p.x][p.y] = d;
		Predicate<Point> continueCondition = c -> !outOfBound(c) && d.isOpponent(data[c.x][c.y]);
		for (Point direction : directions) {
			Point endPoint = walkOverOneDirection(p, direction, continueCondition, xy -> {});

			if (!outOfBound(endPoint) && data[endPoint.x][endPoint.y] == d) {
				// work over again to set Disks
				walkOverOneDirection(p, direction, continueCondition, xy -> {
					data[xy.x][xy.y] = d;
				});
			}
		}
	}

	private Point walkOverOneDirection(Point initialPoint, Point direction, Predicate<Point> continueCondition,
			Consumer<Point> doSth) {
		Point next = new Point(initialPoint.x + direction.x, initialPoint.y + direction.y);

		while (continueCondition.test(next)) {
			doSth.accept(next);
			next = new Point(next.x + direction.x, next.y + direction.y);
		}

		return next;
	}

	/**
	 * Check if the move obeys Othello Game's rules.
	 * 
	 * @param d
	 * @param p
	 * @return
	 */
	public boolean canPlace(Disk d, Point p) {
		if (d == Disk.EMPTY || outOfBound(p) || data[p.x][p.y] != Disk.EMPTY) {
			return false;
		}
		for (Point direction : directions) {
			Point endPoint = walkOverOneDirection(p, direction,
					pxy -> !outOfBound(pxy) && d.isOpponent(data[pxy.x][pxy.y]), 
					pxy -> {});

			if (!outOfBound(endPoint) && data[endPoint.x][endPoint.y] == d
					// end point is not neighbor of origin point
					&& !(p.x + direction.x == endPoint.x && p.y + direction.y == endPoint.y)) {
				return true;
			}
		}

		return false;
	}

	private boolean outOfBound(Point p) {
		return p.x < 0 || p.x >= data.length || p.y < 0 || p.y >= data[0].length;
	}

	/**
	 * Check if the board is full
	 * 
	 * @return
	 */
	public boolean isFull() {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				if (data[i][j] == Disk.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

	public Set<Point> getLegalMoves(Disk d) {
		Set<Point> result = new HashSet<>();
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				Point p = new Point(i, j);
				if (canPlace(d, p)) {
					result.add(p);
				}
			}
		}
		return result;
	}

	public String getEndStatus(Preference preference) {
		int blackCount = 0;
		int whiteCount = 0;
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				if (data[i][j] == Disk.DARK) {
					blackCount++;
				} else if (data[i][j] == Disk.LIGHT) {
					whiteCount++;
				}
			}
		}

		if (blackCount > whiteCount) {
			String decoratedBlack = preference.getDiskDecorator().containsKey(Disk.DARK)
					? preference.getDiskDecorator().get(Disk.DARK)
					: Disk.DARK.toString();
			return "Player '" + decoratedBlack + "' wins ( " + blackCount + " vs " + whiteCount + " )";
		} else if (whiteCount > blackCount) {
			String decoratedWhite = preference.getDiskDecorator().containsKey(Disk.LIGHT)
					? preference.getDiskDecorator().get(Disk.LIGHT)
					: Disk.LIGHT.toString();
			return "Player '" + decoratedWhite + "' wins ( " + whiteCount + " vs " + blackCount + " )";
		} else {
			return "It's a draw";
		}
	}
}
