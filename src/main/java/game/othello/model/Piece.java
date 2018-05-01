package game.othello.model;

public enum Piece {
	BLACK, WHITE, EMPTY;

	public boolean isOpponent(Piece p) {
		return this == BLACK && p == WHITE || this == WHITE && p == BLACK;
	}
}
