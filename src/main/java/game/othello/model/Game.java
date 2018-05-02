package game.othello.model;

import java.awt.Point;
import java.io.IOException;
import java.util.Set;

import game.othello.configuration.Preference;
import game.othello.ui.GameInput;
import game.othello.ui.GameOutput;

public class Game {

	private Board board;
	private GameInput input;
	private GameOutput output;
	private Preference preference;

	public Game(Board board, GameInput input, GameOutput output, Preference preference) {
		this.board = board;
		this.input = input;
		this.output = output;
		this.preference = preference;
	}

	public void start() throws IOException {
		output.print(board, preference);
		output.println();

		Disk current = Disk.DARK;
		int skips = 0;
		while (!board.isFull() && skips < 2) {
			String decoratedPlayer = preference.getDiskDecorator().containsKey(current)
					? preference.getDiskDecorator().get(current)
					: current.toString();
					
			Set<Point> moves = board.getLegalMoves(current);
			if (moves.isEmpty()) {
				skips++;
				output.println("No availabel move for Player '" + decoratedPlayer + "'");
				current = switchPlayer(current);
			} else {
				skips = 0;
				output.print("Player '" + decoratedPlayer + "' move: ");
				Command cmd = Command.parse(input.next());
				while (cmd == null || !moves.contains(cmd.getPoint())) {
					output.println("Invalid move. Please try again.");
					output.print("Player '" + decoratedPlayer + "' move: ");
					cmd = Command.parse(input.next());
				}
				// ensure valid move
				board.place(current, cmd.getPoint());
				output.print(board, preference);
				output.println();
				current = switchPlayer(current);
			}
		}

		output.println("No further moves available");
		output.println(board.getEndStatus(preference));
		input.close();
		output.close();
	}

	private Disk switchPlayer(Disk current) {
		return current == Disk.DARK ? Disk.LIGHT : Disk.DARK;
	}

}
