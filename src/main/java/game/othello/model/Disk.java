package game.othello.model;

public enum Disk {
	DARK, 
	LIGHT, 
	EMPTY;

	public boolean isOpponent(Disk p) {
		return this == DARK && p == LIGHT || this == LIGHT && p == DARK;
	}
}
