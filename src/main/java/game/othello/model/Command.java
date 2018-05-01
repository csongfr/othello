package game.othello.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(Command.class);
	
	private int rowIndex;
	private int colIndex;

	/**
	 * Parse the input to a command
	 * 
	 * @param input
	 * @return	null if fail to parse
	 */
	public static Command parse(String input) {
		if (input == null || input.isEmpty()) {
			return null;
		}

		Command command = new Command();
		// head or tail must be a letter
		String remaining = input;
		if (input.charAt(0) >= 'a' && input.charAt(0) <= 'z') {
			command.colIndex = input.charAt(0) - 'a';
			LOGGER.info("column index found : " + command.colIndex);
			remaining = input.substring(1, input.length());
		} else if (input.charAt(input.length() - 1) >= 'a' && input.charAt(input.length() - 1) <= 'z') {
			command.colIndex = input.charAt(input.length() - 1) - 'a';
			LOGGER.info("column index found : " + command.colIndex);
			remaining = input.substring(0, input.length() - 1);
		} else {
			return null;
		}
		
		// remaining must be a number
		try {
			command.rowIndex = Integer.parseInt(remaining) - 1;
			LOGGER.info("row index found : " + command.rowIndex);
		} catch (Exception e) {
			return null;
		}

		return command;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}

	public int getColIndex() {
		return colIndex;
	}

}
