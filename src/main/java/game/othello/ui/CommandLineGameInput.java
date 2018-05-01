package game.othello.ui;

import java.io.IOException;
import java.util.Scanner;

public class CommandLineGameInput implements GameInput {

	private Scanner scanner;

	public CommandLineGameInput() {
		scanner = new Scanner(System.in);
	}

	@Override
	public String next() {
		return scanner.next();
	}

	@Override
	public void close() throws IOException {
		if (scanner != null) {
			scanner.close();
		}
	}

}
