package game.othello.ui;

import java.io.Closeable;

public interface GameInput extends Closeable {

	String next();

}
