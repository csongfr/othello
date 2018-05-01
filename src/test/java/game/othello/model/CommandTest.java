package game.othello.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CommandTest {

	@Test
	public void testParseCommand() throws Exception {
		Command parsed = Command.parse("3d");
		assertThat(parsed.getRowIndex(), is(2));
		assertThat(parsed.getColIndex(), is(3));

		parsed = Command.parse("d3");
		assertThat(parsed.getRowIndex(), is(2));
		assertThat(parsed.getColIndex(), is(3));

		parsed = Command.parse("3");
		assertThat(parsed, is(nullValue()));

		parsed = Command.parse("d");
		assertThat(parsed, is(nullValue()));

		parsed = Command.parse("3d3");
		assertThat(parsed, is(nullValue()));

		parsed = Command.parse("d3d");
		assertThat(parsed, is(nullValue()));
	}
}
