package game.othello.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;

import game.othello.model.Disk;

@ConfigurationProperties("game.othello.board")
@Valid
public class BoardConfiguration {

	@Min(2)
	private int height;
	@Min(2)
	@Max(value = 26, message = "Board longer than 26 is currently not supported.")
	private int length;

	private Map<Integer, Map<Character, Disk>> startingPosition = new HashMap<>();
	
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

	public Map<Integer, Map<Character, Disk>> getStartingPosition() {
		return startingPosition;
	}

	public void setStartingPosition(Map<Integer, Map<Character, Disk>> startingPosition) {
		this.startingPosition = startingPosition;
	}

}
