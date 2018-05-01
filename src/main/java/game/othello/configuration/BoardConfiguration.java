package game.othello.configuration;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("game.othello.board")
@Valid
public class BoardConfiguration {

	@Min(2)
	private int height;
	@Min(2)
	@Max(value = 26, message = "Board longer than 26 is currently not supported.")
	private int length;

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
