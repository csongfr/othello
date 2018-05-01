package game.othello.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import game.othello.model.Board;
import game.othello.model.Game;
import game.othello.ui.CommandLineGameInput;
import game.othello.ui.CommandLineGameOutput;
import game.othello.ui.GameInput;
import game.othello.ui.GameOutput;

@Configuration
@EnableConfigurationProperties({ BoardConfiguration.class, Preference.class })
public class OthelloGameConfiguration {

	@Bean
	public GameInput commandLineGameInput() {
		return new CommandLineGameInput();
	}

	@Bean
	public GameOutput commandLineGameOutput() {
		return new CommandLineGameOutput();
	}

	@Bean
	public Board Board(BoardConfiguration configuration) {
		return new Board(configuration.getHeight(), configuration.getLength());
	}

	@Bean
	public Game newGame(Board board, GameInput input, GameOutput output, Preference preference) {
		return new Game(board, input, output, preference);
	}

	@Bean
	public CommandLineRunner startGame(Game game) {
		return args -> game.start();
	}

}
