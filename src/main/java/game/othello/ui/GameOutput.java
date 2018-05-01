package game.othello.ui;

import java.io.Closeable;

import game.othello.configuration.Preference;
import game.othello.model.Board;

public interface GameOutput extends Closeable {

	void print(Board board, Preference pref);

	void print(String message);

	void println(String message);

	void println();

}
