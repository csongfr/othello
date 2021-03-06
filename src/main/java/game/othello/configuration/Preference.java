package game.othello.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import game.othello.model.Disk;

@ConfigurationProperties("game.othello.output")
public class Preference {

	private boolean showLeftCoordinate;
	private boolean showRightCoordinate;
	private boolean showTopCoordinate;
	private boolean showBottomCoordinate;
	private Map<Disk, String> diskDecorator = new HashMap<>();

	public boolean isShowLeftCoordinate() {
		return showLeftCoordinate;
	}

	public void setShowLeftCoordinate(boolean showLeftCoordinate) {
		this.showLeftCoordinate = showLeftCoordinate;
	}

	public boolean isShowRightCoordinate() {
		return showRightCoordinate;
	}

	public void setShowRightCoordinate(boolean showRightCoordinate) {
		this.showRightCoordinate = showRightCoordinate;
	}

	public boolean isShowTopCoordinate() {
		return showTopCoordinate;
	}

	public void setShowTopCoordinate(boolean showTopCoordinate) {
		this.showTopCoordinate = showTopCoordinate;
	}

	public boolean isShowBottomCoordinate() {
		return showBottomCoordinate;
	}

	public void setShowBottomCoordinate(boolean showBottomCoordinate) {
		this.showBottomCoordinate = showBottomCoordinate;
	}

	public Map<Disk, String> getDiskDecorator() {
		return diskDecorator;
	}

	public void setDiskDecorator(Map<Disk, String> diskDecorator) {
		this.diskDecorator = diskDecorator;
	}

	public void showAllCoordinate() {
		this.showLeftCoordinate = true;
		this.showRightCoordinate = true;
		this.showTopCoordinate = true;
		this.showBottomCoordinate = true;
	}

	public void hideAllCoordinate() {
		this.showLeftCoordinate = false;
		this.showRightCoordinate = false;
		this.showTopCoordinate = false;
		this.showBottomCoordinate = false;
	}

}
