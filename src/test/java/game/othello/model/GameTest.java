package game.othello.model;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import game.othello.ui.GameInput;
import game.othello.ui.GameOutput;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class GameTest {

	@MockBean
	private GameInput input;

	@MockBean
	private GameOutput output;

	@MockBean
	private CommandLineRunner cliRunner; // disable auto-run

	@Autowired
	private Game game;

	private String[] moveSuite;

	@Rule
	public Timeout globalTimeout = Timeout.seconds(5);

	@Test
	public void testGameRun() throws Exception {
		moveSuite = new String[] { "1b", "a1", "2b", "2a", "3a", "a", "a4", "2d", "3d", "4d", "4c", "4b", "1c", "1d" };
		when(input.next()).thenReturn("20z", moveSuite); // first is an invalid move
		game.start();
		// game is end here
		verify(output, times(3)).println("Invalid move. Please try again.");
		verify(output).println("No availabel move for Player 'X'");
		verify(output).println("No further moves available");
		verify(output).println("Player 'O' wins ( 12 vs 4 )");
	}

}
