package game.othello.model;

import java.io.IOException;

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
		init(board);
		output.print(board, preference);
		output.println();

		Piece current = Piece.BLACK;
		int skips = 0;
		while (!board.isFull() && skips < 2) {
			String decoratedPlayer = preference.getPieceDecorator().containsKey(current)
					? preference.getPieceDecorator().get(current)
					: current.toString();
			if (board.canPlay(current)) {
				skips = 0;
				output.print("Player '" + decoratedPlayer + "' move: ");
				String inputString = input.next();
				Command cmd = Command.parse(inputString);
				if (cmd == null) {
					output.println("Invalid move. Please try again.");
				} else if (board.canPlace(current, cmd.getRowIndex(), cmd.getColIndex())) {
					board.place(current, cmd.getRowIndex(), cmd.getColIndex());
					output.print(board, preference);
					output.println();
					current = switchPlayer(current);
				} else {
					output.println("Invalid move. Please try again.");
				}
			} else {
				skips++;
				output.println("No availabel move for Player '" + decoratedPlayer + "'");
				current = switchPlayer(current);
			}
		}

		output.println("No further moves available");
		output.println(board.getEndStatus(preference));
		input.close();
		output.close();
	}

	private Piece switchPlayer(Piece current) {
		return current == Piece.BLACK ? Piece.WHITE : Piece.BLACK;
	}

	private void init(Board board) {
		// left upper index
		int h = board.getHeight() / 2 - 1;
		int l = board.getLength() / 2 - 1;

		board.init(Piece.WHITE, h, l);
		board.init(Piece.BLACK, h, l + 1);
		board.init(Piece.BLACK, h + 1, l);
		board.init(Piece.WHITE, h + 1, l + 1);
	}

}
