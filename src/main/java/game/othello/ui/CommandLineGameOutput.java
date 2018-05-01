package game.othello.ui;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import game.othello.configuration.Preference;
import game.othello.model.Board;
import game.othello.model.Piece;

public class CommandLineGameOutput implements GameOutput {

	@Override
	public void print(Board board, Preference pref) {
		System.out.print(boardToString(board, pref));
	}

	String boardToString(Board board, Preference pref) {
		StringBuilder sb = new StringBuilder();
		int indent = (int) (Math.log10(board.getHeight())) + 2; // digits + one space
		// first line
		if (pref.isShowTopCoordinate()) {
			if (pref.isShowLeftCoordinate()) {
				sb.append(StringUtils.repeat(' ', indent));
			}
			for (int i = 0; i < board.getLength(); i++) {
				sb.append((char) ('a' + i));
			}
			sb.append('\n');
		}

		for (int r = 0; r < board.getHeight(); r++) {
			if (pref.isShowLeftCoordinate()) {
				sb.append(StringUtils.rightPad(String.valueOf(r + 1), indent));
			}

			for (int c = 0; c < board.getLength(); c++) {
				Piece piece = board.get(r, c);
				String decorated;
				if (pref.getPieceDecorator().containsKey(piece)) {
					decorated = pref.getPieceDecorator().get(piece);
				} else {
					decorated = piece.toString();
				}
				sb.append(decorated);
			}

			if (pref.isShowRightCoordinate()) {
				sb.append(StringUtils.leftPad(String.valueOf(r + 1), indent));
			}

			sb.append('\n');
		}

		if (pref.isShowBottomCoordinate()) {
			if (pref.isShowLeftCoordinate()) {
				sb.append(StringUtils.repeat(' ', indent));
			}
			for (int i = 0; i < board.getLength(); i++) {
				sb.append((char) ('a' + i));
			}
			sb.append('\n');
		}

		return sb.toString();
	}

	@Override
	public void print(String message) {
		System.out.print(message);
	}

	@Override
	public void println(String message) {
		System.out.println(message);
	}

	@Override
	public void println() {
		System.out.println();
	}

	@Override
	public void close() throws IOException {
		System.out.close();
	}

}
