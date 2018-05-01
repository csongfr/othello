package game.othello.model;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

import game.othello.configuration.Preference;

public class Board {

	private Piece[][] data;

	private static List<Pair<Integer, Integer>> directions = Stream.of(Pair.of(-1, -1), Pair.of(-1, 0), Pair.of(-1, 1),
			Pair.of(0, -1), Pair.of(0, 1), Pair.of(1, -1), Pair.of(1, 0), Pair.of(1, 1)).collect(Collectors.toList());

	public Board(int height, int length) {
		if (height == 0 || length == 0) {
			throw new IllegalArgumentException("Border height or length cannot be 0");
		}
		data = new Piece[height][length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				data[i][j] = Piece.EMPTY;
			}
		}
	}

	public Board(Board from) {
		this.data = new Piece[from.getHeight()][from.getLength()];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				data[i][j] = from.data[i][j];
			}
		}
	}

	/**
	 * Initialize board with a piece. Used internally in the package, so will assume
	 * index is not out of bound.
	 * 
	 * @param p
	 * @param rowIndex
	 * @param colIndex
	 */
	void init(Piece p, int rowIndex, int colIndex) {
		data[rowIndex][colIndex] = p;
	}

	public int getHeight() {
		return data.length;
	}

	public int getLength() {
		// constructor ensure that row cannot be 0
		return data[0].length;
	}

	public Piece get(int rowIndex, int colIndex) {
		return data[rowIndex][colIndex];
	}

	/**
	 * Place a piece to the board.
	 * 
	 * In order to reduce the time complexity, this method <b>assumes</b> that the
	 * move is legal. Caller should invoke {@link #canPlace(Piece, int, int)} to
	 * check if not sure of this point.
	 * 
	 * @param p
	 * @param rowIndex
	 * @param colIndex
	 */
	public void place(Piece p, int rowIndex, int colIndex) {
		data[rowIndex][colIndex] = p;
		Predicate<Pair<Integer, Integer>> continueCondition = xy -> !outOfBound(xy.getLeft(), xy.getRight())
				&& p.isOpponent(data[xy.getLeft()][xy.getRight()]);
		for (Pair<Integer, Integer> d : directions) {
			Pair<Integer, Integer> endPoint = walkOverOneDirection(Pair.of(rowIndex, colIndex), d, continueCondition,
					xy -> {});

			if (!outOfBound(endPoint.getLeft(), endPoint.getRight())
					&& data[endPoint.getLeft()][endPoint.getRight()] == p) {
				// work over again to set pieces
				walkOverOneDirection(Pair.of(rowIndex, colIndex), d, continueCondition, xy -> {
					data[xy.getLeft()][xy.getRight()] = p;
				});
			}
		}
	}

	private Pair<Integer, Integer> walkOverOneDirection(Pair<Integer, Integer> initialPoint,
			Pair<Integer, Integer> direction, Predicate<Pair<Integer, Integer>> continueCondition,
			Consumer<Pair<Integer, Integer>> doSth) {
		Pair<Integer, Integer> next = Pair.of(initialPoint.getLeft() + direction.getLeft(),
				initialPoint.getRight() + direction.getRight());

		while (continueCondition.test(next)) {
			doSth.accept(next);
			next = Pair.of(next.getLeft() + direction.getLeft(), next.getRight() + direction.getRight());
		}

		return next;
	}

	/**
	 * Check if the move obeys Othello Game's rules.
	 * 
	 * @param p
	 * @param rowIndex
	 * @param colIndex
	 * @return
	 */
	public boolean canPlace(Piece p, int rowIndex, int colIndex) {
		if (p == Piece.EMPTY || outOfBound(rowIndex, colIndex) || data[rowIndex][colIndex] != Piece.EMPTY) {
			return false;
		}
		for (Pair<Integer, Integer> d : directions) {
			Pair<Integer, Integer> endPoint = walkOverOneDirection(Pair.of(rowIndex, colIndex), d,
					xy -> !outOfBound(xy.getLeft(), xy.getRight()) && p.isOpponent(data[xy.getLeft()][xy.getRight()]),
					xy -> {});

			if (!outOfBound(endPoint.getLeft(), endPoint.getRight())
					&& data[endPoint.getLeft()][endPoint.getRight()] == p
					// end point is not neighbor of origin point
					&& !(rowIndex + d.getLeft() == endPoint.getLeft() && colIndex + d.getRight() == endPoint.getRight())) {
				return true;
			}
		}

		return false;
	}

	private boolean outOfBound(int rowIndex, int colIndex) {
		return rowIndex < 0 || rowIndex >= data.length || colIndex < 0 || colIndex >= data[0].length;
	}

	/**
	 * Check if the board is full
	 * 
	 * @return
	 */
	public boolean isFull() {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				if (data[i][j] == Piece.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Check if there is any place in the board to play the piece
	 * 
	 * @param p
	 * @return
	 */
	public boolean canPlay(Piece p) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				if (canPlace(p, i, j)) {
					return true;
				}
			}
		}
		return false;
	}

	public String getEndStatus(Preference preference) {
		int blackCount = 0;
		int whiteCount = 0;
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				if (data[i][j] == Piece.BLACK) {
					blackCount++;
				} else if (data[i][j] == Piece.WHITE) {
					whiteCount++;
				}
			}
		}

		if (blackCount > whiteCount) {
			String decoratedBlack = preference.getPieceDecorator().containsKey(Piece.BLACK)
					? preference.getPieceDecorator().get(Piece.BLACK)
					: Piece.BLACK.toString();
			return "Player '" + decoratedBlack + "' wins ( " + blackCount + " vs " + whiteCount + " )";
		} else if (whiteCount > blackCount) {
			String decoratedWhite = preference.getPieceDecorator().containsKey(Piece.WHITE)
					? preference.getPieceDecorator().get(Piece.WHITE)
					: Piece.WHITE.toString();
			return "Player '" + decoratedWhite + "' wins ( " + whiteCount + " vs " + blackCount + " )";
		} else {
			return "It's a draw";
		}
	}
}
